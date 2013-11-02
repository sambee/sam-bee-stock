package sam.bee.stock.loader.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.stock.loader.util.FreeMarkerUtils;

public class QQHttpLoader extends BaseLoader{

	//private final static String URL_TEMPLATE = "http://qt.gtimg.cn/r=0.7938921226847172q=<#list list as l><#if l?starts_with(\"0\") >s_sz${l}<#else>s_sh${l}</#if></#list>";
	private final static String URL_TEMPLATE = "http://qt.gtimg.cn/r=0.7938921226847172q=<#list list as l><#if l?starts_with(\"0\") >sz${l}<#else>sh${l}</#if>,</#list>";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object load(Object... params) throws Exception {
		List<String> list =(List<String>)params[0];
		Map root = new HashMap();
		root.put("list", list);
		String request = FreeMarkerUtils.convert(URL_TEMPLATE, root);
		//System.out.println(request);
		return getResponse(request);
	}


}
