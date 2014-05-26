package sam.bee.stock.loader.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class QTingRealTimQueryTest {

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

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		String temp = "${名字}|${当前价格}|${成交量（手）}|${今开}|${总市值}";
		

		
		
		//股票代号列表
		List<String> codeList = new ArrayList<String>();
		
		codeList.add("000713");
		codeList.add("600828");
		codeList.add("002008");
		codeList.add("000157");
		codeList.add("000001");
		
		QQRealTimQuery stockQuery = new QQRealTimQuery(codeList);
		List<Map<String, String>> list  = (List<Map<String, String>>)stockQuery.execute();
		
		
		System.out.println("　　　    名字|　当前价格|成交量(手)|   　 今开|    总市值");
		
		for(Map<String,String> map :list){
			echo(temp, map);
		}		
	}

	
	private void echo(String template, Map<String,String> map){
		for(String key : map.keySet()){
			StringBuffer sb = new StringBuffer();
			Matcher matcher = Pattern.compile("\\$\\{" + key + "\\}").matcher(template);
			boolean result = matcher.find();
			if(result){
				while(result) {					
					String value = String.format("%1$10s", map.get(key));
					matcher.appendReplacement(sb, value);
					result = matcher.find();
				}
			}
			matcher.appendTail(sb);
			template = sb.toString();
		}
		System.out.println(template);
		
	}
}
