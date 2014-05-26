package sam.bee.stock.loader.impl;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
public class QQHttpLoaderTest {


	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		String code1 = "000713";
		String code2 = "600016";
		
		QQHttpLoader loader = new QQHttpLoader();
		List<String> list = new ArrayList<String>(1);
		list.add(code1);
		list.add(code2);
		List<String> actual = loader.get(list);
		
		System.out.println(actual.get(0));
		System.out.println(actual.get(1));
		
		assertThat(actual.get(0), containsString(code1));
		assertThat(actual.get(1), containsString(code2));
		
	}

}
