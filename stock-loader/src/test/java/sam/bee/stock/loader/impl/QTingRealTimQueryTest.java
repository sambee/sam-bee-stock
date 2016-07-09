package sam.bee.stock.loader.impl;

import java.io.IOException;
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

import freemarker.template.TemplateException;

import sam.bee.stock.loader.BaseTest;
import sam.bee.stock.loader.impl.QQRealTimQuery;
import sam.bee.stock.loader.util.FreeMarkerUtils;


public class QTingRealTimQueryTest extends BaseTest {

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		String temp = "${STOCK_CODE}|${STOCK_NAME}|${BUY1_PRICE}|${BUY1_VOLUMN}|${OPEN}|${总市值}";
		
		//股票代号列表
		List<String> codeList = new ArrayList<String>();
		
		codeList.add("000713");
		codeList.add("600828");
		codeList.add("002008");
		codeList.add("000157");
		codeList.add("000001");
		
		QQRealTimQuery stockQuery = new QQRealTimQuery(codeList);
		List<Map<String, String>> list  = (List<Map<String, String>>)stockQuery.execute();
		
		
		System.out.println(String.format("%6sCode|%6s名字|　当前价格|成交量(手)|   　 今开|    总市值","",""));
		
		for(Map<String,String> map :list){
			echo(temp, map);
		}		
	}

	
	private void echo(String template, Map<String,String> map) throws Exception{
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
			//FreeMarkerUtils.convert(template, map);
		}
		System.out.println(template);
		
	}
}