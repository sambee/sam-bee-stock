package sam.bee.stock.loader.impl;

import org.junit.Test;
import sam.bee.stock.loader.BaseTest;
import sam.bee.stock.loader.ILoaderAPI;

import java.util.List;
import java.util.Map;

public class GetShangHaiAndShenZhenStockListTest extends BaseTest{
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		
		ILoaderAPI api = new LoaderApiImpl();
		List<Map<String,String>> list =  api.getShangHaiStockList();
//		AccessDatabaseCache db = ensureDatabase();
//		for(Map<String,String> stock : list){
//			stock.put("STOCK_TYPE", "ss");
//			db.addRowFromMap(STOCK_TABLE_NAME, (Map<String,Object>)((Object)stock));
//		}
//
//		List<Map<String, Object>> data = db.list(STOCK_TABLE_NAME);
//		assert(data.size() == list.size());
//
//		list =  api.getShenZhenStockList();
//		for(Map<String,String> stock : list){
//			stock.put("STOCK_TYPE", "sz");
//			db.addRowFromMap(STOCK_TABLE_NAME, (Map<String,Object>)((Object)stock));
//		}
	}
}
