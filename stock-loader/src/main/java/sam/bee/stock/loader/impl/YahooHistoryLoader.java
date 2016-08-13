package sam.bee.stock.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.stock.loader.util.FreeMarkerUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class YahooHistoryLoader extends BaseLoader implements ILoader {

	protected static final Logger logger = LoggerFactory.getLogger(sam.bee.stock.loader.impl.YahooHistoryLoader.class);

	//http://ichart.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2014&g=d
		//•http://ichart.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2010&g=d
		//•http://table.finance.yahoo.com/table.csv?s=600000.SS&a=08&b=25&c=2010&d=09&e=8&f=2010&g=d
//	http://ichart.yahoo.com/table.csv?s=string&a=int&b=int&c=int&d=int&e=int&f=int&g=d&ignore=.csv


//	1、通过API获取实时数据
//
//			请求地址
//
//	http://finance.yahoo.com/d/quotes.csv?s=<股票名称>&f=<数据列选项>
//
//	参数
//
//	s –表示股票名称，多个股票之间使用英文加号分隔，如”XOM+BBDb.TO+JNJ+MSFT”，罗列了四个公司的股票：XOM,BBDb.TO, JNJ, MSFT。
//
//	f – 表示返回数据列，如”snd1l1yr”。更详细的参见雅虎股票 API f参数对照表。
//
//			2、通过API获取历史数据
//
//			请求地址
//
//	http://ichart.yahoo.com/table.csv?s=<string>&a=<int>&b=<int>&c=<int>&d=<int>&e=<int>&f=<int>&g=d&ignore=.csv
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
//	一定注意月份参数，其值比真实数据-1。如需要9月数据，则写为08。

//	http://heipark.iteye.com/blog/1423812
//	private final static String URL = "http://table.finance.yahoo.com/table.csv?s=<#if code?starts_with(\"0\")  || code?starts_with(\"3\")  >${code}.sz<#else>${code}.SS</#if>";

	private final static String URL_TEMP_2 = "http://table.finance.yahoo.com/table.csv?s=${code}&a=${startMonth}&b=${startDay}&c=${startYear}&d=${toMonth}&e=${toDay}&f=${toYear}&g=d&ignore=.csv";
	private final static String URL_TEMP_1 = "http://table.finance.yahoo.com/table.csv?s=${code}";
	private String URL_TEMP;
	private String parseCode(String code){
		if(code.startsWith("0") || code.startsWith("3")){
			return code +".sz";
		}
		else{
			return code +".SS";
		}
	}
	protected String get(String code, String start,String end) throws Exception {
//			String url = FreeMarkerUtils.convert(URL, "code", code);

		String mCode = parseCode(code);
		String startYear = start.substring(0,4);
		String startMonth = (Integer.valueOf(start.substring(5, 7))-1)+"";
		String startDay = start.substring(8, 10);

		String toYear = end.substring(0,4);
		String toMonth = (Integer.valueOf(end.substring(5, 7))-1)+"";
		String toDay = end.substring(8, 10);

		String url = replaceValue(URL_TEMP, "code", mCode);
		url = replaceValue(url, "startYear", startYear);
		url = replaceValue(url, "startMonth", startMonth);
		url = replaceValue(url, "startDay", startDay);

		url = replaceValue(url, "toYear", toYear);
		url = replaceValue(url, "toMonth", toMonth);
		url = replaceValue(url, "toDay", toDay);

		logger.info(url);
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
			if(v!=null) {
				Collections.sort(v, new Comparator<Map<String, String>>() {

					@Override
					public int compare(Map<String, String> o1, Map<String, String> o2) {
						String code1 = o1.get("DATE");
						String code2 = o2.get("DATE");
						if (code1 != null && code2 != null) {
							return code1.compareTo(code2);
						}
						logger.error(o1 + " " + o2);
						throw new NullPointerException("Not found date");
					}

				});
			}
			return v;
		}
	private static final SimpleDateFormat  f  = new SimpleDateFormat("yyyy-MM-dd");
	private String startDate;
	private String toDate;
	public YahooHistoryLoader() {
		this.URL_TEMP = URL_TEMP_1;
		this.startDate = "1990-01-01";
		this.toDate = f.format(new Date());
	}

	public YahooHistoryLoader(String startDate, String toDate) {
		this.startDate = startDate;
		this.toDate = toDate;
		this.URL_TEMP = URL_TEMP_2;
	}

	public YahooHistoryLoader(String startDate) {
		this.startDate = startDate;
		this.toDate = f.format(new Date());
		this.URL_TEMP = URL_TEMP_2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,String>>  execute(String code) throws Exception {
		String data = get(code, startDate, toDate);
		if(data!=null) {
			return parse(data);
		}
		return null;
	}
}