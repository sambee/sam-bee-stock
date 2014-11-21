package sam.bee.stock.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.junit.Test;

import sam.bee.cache.H2DatabaseCache;
import sam.bee.cache.JsonHelper;
import sam.bee.stock.loader.impl.YahooHistoryQuery;

public class GetStockHistoryFromH2DatabaseTest {


	private final static String DATABASE = "stocks";
	private final static String TABLE_NAME= "STOCK_DATA";
	
	/**
	 * 测试目的是从数据库中查找股票code=000713中查找所有的信息．
	 * 并将信息从新写入到数据库中．
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() throws Exception{
		String code = "000713";
	
	
		H2DatabaseCache h2 = new H2DatabaseCache();
		String data  = h2.getString(DATABASE, TABLE_NAME, code);
		System.out.println(data);
		List<Map<String,String>> list = JsonHelper.toList( new JSONArray(data));

		List<Map<String,String>> list2 = new ArrayList();
		for(Map<String,String> line : list){		
			list2.add(genMap(line));
		}
		h2.set(list2, DATABASE, TABLE_NAME, code);
	}
	
	public static  Map genMap(Map<String,String> m){	
		Map<String,String> m2 = new HashMap();
	    for(String a : m.keySet()){	
	    	m2.put(a.trim(), m.get(a));
	    }
		return m2;
	}
	

	
}
