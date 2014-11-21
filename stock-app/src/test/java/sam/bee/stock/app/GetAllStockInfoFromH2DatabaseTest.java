package sam.bee.stock.app;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.cache.H2DatabaseCache;
import sam.bee.stock.loader.impl.GetAllShangHaiStockList;
import sam.bee.stock.loader.impl.GetAllShendZhenStockList;

public class GetAllStockInfoFromH2DatabaseTest {

	private final static String DATABASE = "stocks";
	private final static String TABLE_NAME= "STOCK_INFO";
	@Test
	public void test() throws Exception {
		H2DatabaseCache h2 = new H2DatabaseCache();
		List<Map<String,String>> list = h2.getList(DATABASE, TABLE_NAME);

		for(Map<String,String> stock : list){
			System.out.println(stock);
		}
	}
}
