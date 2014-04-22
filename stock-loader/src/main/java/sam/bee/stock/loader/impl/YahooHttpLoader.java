package sam.bee.stock.loader.impl;

import java.util.List;

import sam.bee.stock.loader.util.FreeMarkerUtils;

public class YahooHttpLoader extends BaseLoader{

	private final static String URL = "http://table.finance.yahoo.com/table.csv?s=<#if code?starts_with(\"0\") >${code}.sz<#else>${code}.sh</#if>";

	@Override
	protected List<String> get(Object... params) throws Exception {
		String url = FreeMarkerUtils.convert(URL, "code", (String)params[0]);		
		return getResponse(url);
	}

}
