package sam.bee.stock.app;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.porvider.H2DatabaseCache;
import sam.bee.stock.loader.impl.YahooHistoryQuery;

public class SaveStockHistoryToH2DatabaseTest {


	private final static String DATABASE = "stocks";
	private final static String TABLE_NAME= "STOCK_DATA";
	
	@Test
	public void test() throws Exception{
		String code = "000713";
		YahooHistoryQuery query = new YahooHistoryQuery(code);
		List<Map<String,String>> list = (List<Map<String,String>>)query.execute();
		H2DatabaseCache h2 = new H2DatabaseCache();
		h2.set(String.valueOf(list), DATABASE, TABLE_NAME, code);
		for(Map<String,String> m : list){
			System.out.println(m);
			
		}
	}
}
