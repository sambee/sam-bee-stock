package sam.bee.stock.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.stock.loader.util.FreeMarkerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YahooHistoryQuery extends BaseQuery implements ICommand {

	protected static final Logger logger = LoggerFactory.getLogger(YahooHistoryQuery.class);

	String code;
	IDataAdapter adapter;
	YahooHistoryLoader loader;

	class YahooHistoryLoader extends BaseLoader{

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

		@Override
		protected List<String> get(Object... params) throws Exception {
			String url = FreeMarkerUtils.convert(URL, "code", (String)params[0]);
			logger.info(url);
			return getResponse(url);
		}

	}

	class YahooHistoryDataAdapter implements IDataAdapter {

		@Override
		public Object parse(List<?> list) {
			List<Map<String, String>> ret = new ArrayList<Map<String,String>>();

			String[] columns = ((String)list.remove(0)).split(",");
			for(int i=0; i< columns.length; i++){
				columns[i] = columns[i].toUpperCase().replaceAll(" ", "_");
			}
			for(Object obj : list){
				String tmp = (String)obj;
				String[] stocks = tmp.split(",");
				Map map = new HashMap();
				for(int i=0; i< columns.length; i++){
					map.put(columns[i], stocks[i]);
				}


				ret.add(map);
			}
			return ret;
		}

	}

	public YahooHistoryQuery(String code) {
		this.code = code;
		this.adapter =  new YahooHistoryDataAdapter();
		this.loader = new YahooHistoryLoader();
	}
	
	public YahooHistoryQuery(YahooHistoryLoader loader, String code, IDataAdapter adapter) {
		this.code = code;
		this.adapter =  adapter;
		this.loader = loader;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute() throws Exception {		
		List<String> list = (List<String>)loader.get(code);		
		return adapter.parse(list);
	}
}