package sam.bee.stock.loader.util;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.TemplateException;

public class FreeMarkerUtilsTest {

	private static final String template = "<#if code?starts_with(\"0\") >sz${code}<#else>sh${code}</#if>";
	
	@Test
	public void test2() throws IOException, TemplateException {
		String szCode = "000713";
		String actual = FreeMarkerUtils.convert(template, "code", szCode);
		assertThat(actual, equalTo("sz" + szCode));
	}
	

	@Test
	public void test1() throws IOException, TemplateException {
		
		String szCode = "000713";
		String shcode = "600000";
		
		Map<String, String> map = new HashMap<String, String>(2);
		
				
		map.put("code", szCode);
		String actual = FreeMarkerUtils.convert(template, map);
		assertThat(actual, equalTo("sz" + szCode));
		
		map.clear();
		map.put("code", shcode);
		actual = FreeMarkerUtils.convert(template, map);
		assertThat(actual, equalTo("sh" + shcode));
		
	}

}
