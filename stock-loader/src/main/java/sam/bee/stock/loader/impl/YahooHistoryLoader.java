package sam.bee.stock.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.stock.loader.util.FreeMarkerUtils;

import java.util.*;

public class YahooHistoryLoader extends BaseLoader implements ILoader {

	protected static final Logger logger = LoggerFactory.getLogger(sam.bee.stock.loader.impl.YahooHistoryLoader.class);

	String code;

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
	private final static String URL = "http://table.finance.yahoo.com/table.csv?s=<#if code?starts_with(\"0\")  || code?starts_with(\"3\")  >${code}.sz<#else>${code}.SS</#if>";
	protected String get(String code) throws Exception {
			String url = FreeMarkerUtils.convert(URL, "code", code);
			return getResponse(url);
		}

		public List<Map<String,String>> parse(String dataStr) {
			Vector<Map<String,String>> v = new Vector<>();
			String[] data = dataStr.split("\n");

			String[] columns = (data[0]).split(",");
			for (int i = 0; i < columns.length; i++) {
				columns[i] = columns[i].toUpperCase().replaceAll(" ", "_");
			}

			for(int i=1;i<data.length; i++) {
				String[] stocks = data[i].split(",");
				Map map = new HashMap();
				for (int ii = 0; ii < columns.length; ii++) {
					map.put(columns[ii], stocks[ii]);
				}
				v.add(map);
			}
			return v;
		}


	public YahooHistoryLoader(String code) {
		this.code = code;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,String>>  execute() throws Exception {
		String data = get(code);
		return parse(data);
	}
}