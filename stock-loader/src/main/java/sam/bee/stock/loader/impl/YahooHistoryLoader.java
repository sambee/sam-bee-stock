package sam.bee.stock.loader.impl;

import java.util.List;

import sam.bee.stock.loader.util.FreeMarkerUtils;

public class YahooHistoryLoader extends BaseLoader{

	//http://ichart.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2014&g=d
	//•http://ichart.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2010&g=d
	//•http://table.finance.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2010&g=d
//	http://ichart.yahoo.com/table.csv?s=string&a=int&b=int&c=int&d=int&e=int&f=int&g=d&ignore=.csv
//
//		s — 股票名称
//
//		a — 起始时间，月
//
//		b — 起始时间，日
//
//		c — 起始时间，年
//
//		d — 结束时间，月
//
//		e — 结束时间，日
//
//		f — 结束时间，年
//
//		g — 时间周期。
//
//		Ø  参数g的取值范围：d->‘日’(day), w->‘周’(week)，m->‘月’(mouth)，v->‘dividends only’
//
//		Ø  月份是从0开始的，如9月数据，则写为08。
//	http://heipark.iteye.com/blog/1423812
	private final static String URL = "http://table.finance.yahoo.com/table.csv?s=<#if code?starts_with(\"0\") >${code}.sz<#else>${code}.SS</#if>";

	@Override
	protected List<String> get(Object... params) throws Exception {
		String url = FreeMarkerUtils.convert(URL, "code", (String)params[0]);
		return getResponse(url);
	}

}
