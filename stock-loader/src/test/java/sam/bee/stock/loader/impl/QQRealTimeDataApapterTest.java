package sam.bee.stock.loader.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class QQRealTimeDataApapterTest {

	@Test
	public void test() throws Exception {
		
		String code1 = "000713";
		String code2 = "600016";
		
		QQHttpLoader loader = new QQHttpLoader();
		List<String> list = new ArrayList<String>(1);
		list.add(code1);
		list.add(code2);
		List<String> actual = loader.get(list);
		
		QQRealTimeDataApapter adapter = new QQRealTimeDataApapter();
		List<Map<String, Object>> realInfo =adapter.parse(actual);
		
		System.out.println(realInfo);
	}
	
	protected void createDatabase() throws ClassNotFoundException{
		
	}

}
