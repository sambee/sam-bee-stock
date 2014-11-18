package sam.bee.stock.app;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import sam.bee.cache.AccessDatabaseCache;
import sam.bee.stock.loader.impl.GetAllShangHaiStockList;
import sam.bee.stock.loader.impl.GetAllShendZhenStockList;
import sam.bee.stock.loader.impl.QQHttpLoader;
import sam.bee.stock.loader.impl.QQRealTimeDataApapter;
import sam.bee.stock.loader.impl.YahooHistoryQuery;

public class AccessDatabaseTest {

	private final static String TABLE = "STOCKS";
	private static final Logger log = LoggerFactory.getLogger(AccessDatabaseTest.class);
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		AccessDatabaseTest m = new AccessDatabaseTest();
		
		File dbFile = new File("stock.mdb");
	    AccessDatabaseCache db = m.getDatabase(dbFile);
		db.emptyTable(TABLE);
//		m.initialDatabase(db);
		
		List<Map<String,String>> list =  new GetAllShangHaiStockList().list();
		for(Map<String,String> stock : list){
			stock.put("STOCK_TYPE", "上海");
			db.addRowFromMap(TABLE, (Map<String,Object>)((Object)stock));
		}

		list =  new GetAllShendZhenStockList().list();
		for(Map<String,String> stock : list){
			stock.put("STOCK_TYPE", "深圳");
			db.addRowFromMap(TABLE, (Map<String,Object>)((Object)stock));
		}
		
		List<Map<String, Object>> data = db.list(TABLE);
		
//		int start =0;
//		int limit = 25;
//		
//		log.info("COUNT:" + data.size());
//		do{
//			m.loadStock(db, TABLE, m.getStockCode(data, start, limit));			
//		}while((start = start+limit)< data.size());
		
	
		int start =0;
		int limit = 1;
		
		log.info("COUNT:" + data.size());
		while((start = start+limit)< data.size()){
			String stockCode = (String)data.get(0).get("STOCK_CODE");
			List<String> history  = (List<String>)new YahooHistoryQuery(stockCode).execute();	
			log.info(String.valueOf(history.size()));
		};
		
		db.close();
		
		log.info("------------- DONE ---------");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void loadStock(AccessDatabaseCache db, String tableName, List<String> codes) throws Exception{
		QQHttpLoader loader = new QQHttpLoader();
		List<String> rawInfo = loader.get(codes);
		QQRealTimeDataApapter adapter = new QQRealTimeDataApapter();
		List<Map<String, String>> realInfo =adapter.parse(rawInfo);
		for(Map<?, ?> map : realInfo){
			log.info("Stock info " + map.get("STOCK_CODE") + " "+ map.get("STOCK_NAME") );
			db.updateRowFromMap(TABLE, "STOCK_CODE", (String)map.get("STOCK_CODE"), (Map)map);	
		}
	}
	

	
	@SuppressWarnings("unused")
	private List<String> getStockCode(List<Map<String, Object>> stocks, int start, int limit){
		List<String> list = new ArrayList<String>(stocks.size());
		int end = start+limit;
		for(int i=start; i<end && (i<stocks.size()); i++){
			Map<String, Object> obj = stocks.get(i);			
			list.add((String)obj.get("STOCK_CODE"));
			//log.info("INDEX:" + i);
		}
		return list;
	}
	
	private AccessDatabaseCache getDatabase(File dbFile) throws Exception{
//		if(dbFile.exists()){
//			dbFile.delete();
//		}
		
		//create access file.
		return new AccessDatabaseCache(dbFile);
	}
	
	@SuppressWarnings({ "unused", "serial" })
	private AccessDatabaseCache createAccessDatabase(AccessDatabaseCache asscssDatabase) throws IOException, SQLException{
	
		
		List<Map<String, Object>> fieldsInfo = new ArrayList<Map<String, Object>>(){{
			add(new  HashMap<String,Object>(){{ 
				put("COL_NAME", "STOCK_CODE");
				put("COL_TYPE", "varchar");
			}});
			add(new  HashMap<String,Object>(){{ 
				put("COL_NAME", "STOCK_NAME");
				put("COL_TYPE", "varchar");
			}});
			
			add(new  HashMap<String,Object>(){{ 
				put("COL_NAME", "STOCK_TYPE");
				put("COL_TYPE", "varchar");
			}});
			
			add(new  HashMap<String,Object>(){{ 
				put("COL_NAME", "STOCK_PRICE");
				put("COL_TYPE", "varchar");
			}});
			
		}};				
				;
		asscssDatabase.createAccessTable(TABLE, fieldsInfo);
		return asscssDatabase;
	}

}
