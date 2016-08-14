package sam.bee.stock.loader.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static sam.bee.stock.Const.*;


public class YahooRealTimeLoader extends BaseLoader implements ILoader{

	private final static String URL = "http://hq.sinajs.cn/list=${code}";

	private String parseCode(String code){
		if(code.startsWith("0") || code.startsWith("3")){
			return "sz" +code;
		}
		else{
			return "sh"+ code ;
		}
	}
	private String get(String code) throws Exception {
		String url = replaceValue(URL, "code", parseCode(code));
		return getResponse(url);
	}

	//这个字符串由许多数据拼接在一起，不同含义的数据用逗号隔开了，按照程序员的思路，顺序号从0开始。
//		0：”大秦铁路”，股票名字；
//		1：”27.55″，今日开盘价；
//		2：”27.25″，昨日收盘价；
//		3：”26.91″，当前价格；
//		4：”27.55″，今日最高价；
//		5：”26.20″，今日最低价；
//		6：”26.91″，竞买价，即“买一”报价；
//		7：”26.92″，竞卖价，即“卖一”报价；
//		8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
//		9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
//		10：”4695″，“买一”申请4695股，即47手；
//		11：”26.91″，“买一”报价；
//		12：”57590″，“买二”
//		13：”26.90″，“买二”
//		14：”14700″，“买三”
//		15：”26.89″，“买三”
//		16：”14300″，“买四”
//		17：”26.88″，“买四”
//		18：”15100″，“买五”
//		19：”26.87″，“买五”
//		20：”3100″，“卖一”申报3100股，即31手；
//		21：”26.92″，“卖一”报价
//		(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
//		30：”2008-01-11″，日期；
//		31：”15:05:32″，时间；
	public List<Map<String,String>> parse(String code, String dataStr) {
		Vector<Map<String,String>> v= new Vector<>();
		Map<String,String> map =  new HashMap();
		String[] data = dataStr.split(",");

		if(data.length<2){
			return null;
		}
		String name = data[0].split("=")[1].replace("\"", "");
		String type;
		if(code.startsWith("0") || code.startsWith("3")){
			type = "sz" ;
		}
		else{
			type = "sh" ;
		}

		map.put(STOCK_CODE, code);
		map.put(STOCK_NAME, name);
		map.put(STOCK_TYPE, type);
		map.put(DATE, data[30]);
		map.put(HIGH, data[4]);
		map.put(LOW, data[5]);
		map.put(OPEN, data[1]);
		map.put(CLOSE, data[3]);

		v.add(map);
		return v;
	}

	@Override
	public List<Map<String, String>> execute(String code, String type) throws Exception {
		String mCode = parseCode(code);
		String url = replaceValue(URL, "code", mCode);
		String dataStr = getResponse(url);
		//System.out.println(dataStr);
		return parse(code, dataStr);
	}
}
