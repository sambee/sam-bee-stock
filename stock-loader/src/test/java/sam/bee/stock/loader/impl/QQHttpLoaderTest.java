package sam.bee.stock.loader.impl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.Region;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
public class QQHttpLoaderTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		
		String code1 = "000713";
		String code2 = "600016";
		
		QQHttpLoader loader = new QQHttpLoader();
		List<String> list = new ArrayList<String>(1);
		list.add(code1);
		list.add(code2);
		List<String> actual = (List<String>)loader.load(list);
		
		System.out.println(actual.get(0));
		System.out.println(actual.get(1));
		
		assertThat(actual.get(0), containsString(code1));
		assertThat(actual.get(1), containsString(code2));
		
	}

}
