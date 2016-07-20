package sam.bee.stock.loader.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.stock.loader.BasicTest;
import sam.bee.stock.loader.ILoaderAPI;

public class GetRealTimeStockTest extends BasicTest {


	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		ILoaderAPI api = new LoaderApiImpl();
		
		List<Map<String, String>> stocks = api.getShangHaiStockList();
		
		List<String> list = new ArrayList<String>(1);
		
		int loaded=0;
		String code;
		String name;
		while(loaded<stocks.size()){
			list.clear();
			int batch = loaded+64;
			for(;loaded<batch && loaded<stocks.size(); loaded++){
				code = stocks.get(loaded).get("STOCK_CODE");
				name = stocks.get(loaded).get("STOCK_NAME");
				Long.valueOf(code);
				list.add(code);
				
			}
			List<Map<String, String>> stockList = api.getRealTimeStockList(list);		
			for(Map<String,String> m :stockList){
				code = m.get("STOCK_CODE");
				name = m.get("STOCK_NAME");
				list.remove(code);				
			}		

			assertEquals(list.size(), 0);
		}



		
	}

}
