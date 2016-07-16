package sam.bee.stock.loader.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.stock.loader.util.FreeMarkerUtils;

/**
 * 获取最新行情，访问数据接口
 *
 * QQHttpLoader.java
 *
 * @author Sam Wong
 *  QQ: 1557299538
 * @create: 2014年4月23日  
 * 
 * Modification
 * -------------------------------------------
 */
public class QQHttpLoader extends BaseLoader{

	//private final static String URL_TEMPLATE = "http://qt.gtimg.cn/r=0.7938921226847172q=<#list list as l><#if l?starts_with(\"0\") >s_sz${l}<#else>s_sh${l}</#if></#list>";
	private final static String URL_TEMPLATE = "http://qt.gtimg.cn/r=0.7938921226847172q=<#list list as l><#if l?starts_with(\"0\") >sz${l}<#else>sh${l}</#if>,</#list>";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public  List<String> get(Object... params) throws Exception {
		List<String> list =(List<String>)params[0];
		Map root = new HashMap();
		root.put("list", list);
		String request = FreeMarkerUtils.convert(URL_TEMPLATE, root);
		//logger.debug(request);
		return getResponse(request);
	}


}
