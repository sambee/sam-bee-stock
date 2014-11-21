package sam.bee.stock.app;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.cache.H2DatabaseCache;
import sam.bee.stock.loader.impl.GetAllShangHaiStockList;
import sam.bee.stock.loader.impl.GetAllShendZhenStockList;

public class SaveStockInfoToH2DatabaseTest {

	private final static String DATABASE = "stocks";
	private final static String TABLE_NAME= "STOCK_INFO";
	@Test
	public void test() throws Exception {
		H2DatabaseCache h2 = new H2DatabaseCache();
//		List<Map<String,String>> list =  new GetAllShangHaiStockList().list();
//		for(Map<String,String> stock : list){
//			//h2.set(value, keys);
//			h2.set(stock.get("STOCK_NAME"), DATABASE, TABLE_NAME , stock.get("STOCK_CODE"));
//		}
		List<Map<String,String>> list2 =  new GetAllShendZhenStockList().list();
		//System.out.println(list2);
		for(Map<String,String> stock : list2){
			h2.set(stock.get("STOCK_NAME"), DATABASE, TABLE_NAME , stock.get("STOCK_CODE"));
		}
	}
}
