package sam.bee.stock.loader.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class YahooHistoryLoaderTest {


	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		String code = "600000";
		List<Map<String,String>> list = new YahooHistoryLoader("2016-01-01", "2016-07-31").execute(code, "sz");

			Collections.sort(list, new Comparator<Map<String, String>>() {

				@Override
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					String code1 = o1.get("DATE");
					String code2 = o2.get("DATE");
					return code1.compareTo(code2);
				}

			});

		System.out.println(list);
	}

}
