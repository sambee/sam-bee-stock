package sam.bee.stock.loader.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class YahooHistoryQueryTest {


	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		String code = "000713";
		YahooHistoryQuery query = new YahooHistoryQuery(code);
		List<Map<String,String>> list = (List<Map<String,String>>)query.execute();
		System.out.println(list);
		
	}

}
