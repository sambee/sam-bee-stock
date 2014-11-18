package sam.bee.stock.loader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sam.bee.stock.loader.impl.GetStockNameAndFileCacheTest;
import sam.bee.stock.loader.impl.QQHttpLoader;
import sam.bee.stock.loader.impl.QQRealTimeDataApapter;
import sam.bee.cache.AccessDatabaseCache;

public abstract class BaseTest {

	protected final static String STOCK_TABLE_NAME = "Stocks";
	protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
	
	@SuppressWarnings("serial")
	protected	AccessDatabaseCache ensureDatabase() throws Exception{
		File dbFile = new File("stock.mdb");
		if(dbFile.exists()){
			dbFile.delete();
		}
		assert(!dbFile.exists());
		//create access file.
		AccessDatabaseCache asscssDatabase = new AccessDatabaseCache(dbFile);

		assert(dbFile.exists());
		
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
			
		}};
				
				;
		asscssDatabase.createAccessTable(STOCK_TABLE_NAME, fieldsInfo);
		return asscssDatabase;
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	void loadStock(AccessDatabase db, String tableName, List<String> codes) throws Exception{
//		QQHttpLoader loader = new QQHttpLoader();
//		List<String> rawInfo = loader.get(codes);
//		QQRealTimeDataApapter adapter = new QQRealTimeDataApapter();
//		List<Map<String, Object>> realInfo =adapter.parse(rawInfo);
//		for(Map<?, ?> map : realInfo){
//			log.info("Stock info " + map.get("STOCK_CODE") + " "+ map.get("STOCK_NAME") );
//			db.updateRowFromMap(STOCK_TABLE_NAME, "STOCK_CODE", (String)map.get("STOCK_CODE"), (Map)map);	
//		}
//	}
	
	AccessDatabaseCache createAccessDatabase(AccessDatabaseCache asscssDatabase) throws IOException, SQLException{
	
		
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
		asscssDatabase.createAccessTable(STOCK_TABLE_NAME, fieldsInfo);
		return asscssDatabase;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
