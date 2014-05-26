package sam.bee.stock.loader.impl;

import static org.junit.Assert.*;

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
import org.junit.Test;

public class GetAllShangHaiStockListTest {

	private final static String TABLE = "Stocks";
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		List<Map<String,String>> list =  new GetAllShangHaiStockList().list();
		AccessDatabase db = initialDatabase();
		for(Map<String,String> stock : list){
			stock.put("STOCK_TYPE", "ss");
			db.addRowFromMap(TABLE, (Map<String,Object>)((Object)stock));
		}

		List<Map<String, Object>> data = db.list(TABLE);
		assert(data.size() == list.size());
		System.out.println(data);
	}
	
	
	private AccessDatabase initialDatabase() throws Exception{
		File dbFile = new File("stock.mdb");
		if(dbFile.exists()){
			dbFile.delete();
		}
		assert(!dbFile.exists());
		//create access file.
		AccessDatabase asscssDatabase = new AccessDatabase(dbFile);

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
		asscssDatabase.createAccessTable(TABLE, fieldsInfo);
		return asscssDatabase;
	}
	


}
