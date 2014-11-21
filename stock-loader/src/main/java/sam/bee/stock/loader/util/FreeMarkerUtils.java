package sam.bee.stock.loader.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {

	@SuppressWarnings("rawtypes")
	public static String convert(String template, Map map) throws IOException, TemplateException{
		 Configuration cfg = new Configuration();  
	     cfg.setObjectWrapper(new DefaultObjectWrapper());
	     StringTemplateLoader loader = new StringTemplateLoader();
	     cfg.setTemplateLoader(loader);  
	     
	     loader.putTemplate(template, template);
	     Template t = cfg.getTemplate(template);
	     StringWriter out = new StringWriter();
	     t.process(map, out);
	     return out.toString();
	}
	
	public static String convert(String template, String... keyAndValue) throws IOException, TemplateException{
		Map<String, String> params = new HashMap<String, String>(keyAndValue.length/2);
		for(int i=0; i<keyAndValue.length; i+=2){
			params.put(keyAndValue[i], keyAndValue[i+1]);
		}
		return convert(template, params);
	}
}
