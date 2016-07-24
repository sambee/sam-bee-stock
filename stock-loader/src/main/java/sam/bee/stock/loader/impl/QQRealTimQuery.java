package sam.bee.stock.loader.impl;

import java.util.*;

public class QQRealTimQuery extends BaseQuery implements ICommand{


	private List<String> stockCodeList;
	private IDataAdapter adapter;
	QQHttpLoader loader;


	class QQRealTimeDataApapter implements IDataAdapter {

//	private static final String[] FIELDS = {
//		"未知",	//		0: 未知
//		"名字",	//		1: 名字
//		"代码",	//		2: 代码
//		"当前价格",//		3: 当前价格
//		"涨跌",//		4: 涨跌
//		"涨跌%",//		5: 涨跌%
//		"成交量（手）",//		6: 成交量（手）
//		"成交额（万）",//		7: 成交额（万）
//		 "",		  //		8:
//		"总市值"     //9: 总市值
//	};

		private final String[] FIELDS = {
				"未知",        //0: 未知
				"STOCK_NAME",            //	 1: 名字
				"STOCK_CODE",            //	 2: 代码
				"STOCK_PRICE",        //	 3: 当前价格
				"PREVIOUS_CLOSE",            //	 4: 昨收
				"OPEN",            //	 5: 今开
				"VOLUMN",    //	 6: 成交量（手）
				"外盘",            //	 7: 外盘
				"内盘",            //	 8: 内盘
				"BUY1_PRICE",            //	 9: 买一
				"BUY1_VOLUMN",    //	10: 买一量（手）
				"BUY2_PRICE",            //	11
				"BUY2_VOLUMN",    //	12
				"BUY3_PRICE",            //	13
				"BUY3_VOLUMN",        //	14
				"BUY4_PRICE",            //	15
				"BUY4_VOLUMN",    //	16
				"BUY5_PRICE",            //	17
				"BUY5_VOLUMN", //18: 买五
				"SELL1_PRICE",    //	19: 卖一
				"SELL1_VOLUMN",    //	20: 卖一量
				"SELL2_PRICE",    //	21: 卖二 卖五
				"SELL2_VOLUMN",    //	22: 卖二 卖五
				"SELL3_PRICE",    //	23: 卖二 卖五
				"SELL3_VOLUMN",    //	24: 卖二 卖五
				"SELL4_PRICE",    //	25: 卖二 卖五
				"SELL4_VOLUMN",    //	26: 卖二 卖五
				"SELL5_PRICE",    //	27: 卖二 卖五
				"SELL5_VOLUMN",    //	28: 卖二 卖五
				"最近逐笔成交",    //	29: 最近逐笔成交
				"CREATED_TIME",    //	30: 时间
				"CHANGE",    //	31: 涨跌
				"CHANGE_PERCENT",    //	32: 涨跌%
				"HIGH",    //	33: 最高
				"LOW",    //	34: 最低
				"成交量",    //	35: 价格/成交量（手）/成交额
				"VOLUME",    //	36: 成交量（手）
				"成交额",    //	37: 成交额（万）
				"换手率",    //	38: 换手率
				"市盈率",    //	39: 市盈率
				"最高",    //	40:
				"HIGH",    //	41: 最高
				"LOW",    //	42: 最低
				"STOCK_AMPLITUDE",    //	43: 振幅
				"流通市值",    //	44: 流通市值
				"总市值",//	45: 总市值
				"市净率",//	46: 市净率
				"涨停价",//	47: 涨停价
				"跌停价"//	48: 跌停价
		};

		@Override
		public List<Map<String, String>> parse(List<?> list) {

			List<Map<String, String>> ret = new Vector<Map<String, String>>();
			Map<String, String> map;
			for (Object obj : list) {
				String tmp = (String) obj;
				String endChar = tmp.substring(tmp.length()-1);
				if("\n".equals(endChar)){
					tmp = tmp.substring(0, tmp.length()-1);
				}
				//logger.debug(tmp);

				String[] values = tmp.replaceAll("\"", "").replaceAll(";", "").split("=");
				String[] fields = values[1].split("~");
				map = new HashMap<String, String>(fields.length);
				for (int i = 0; i < fields.length; i++) {
					if (fields[i] != null && fields[i].length() > 0) {

						//logger.debug(String.format("%d%s%s", i, FIELDS[i], fields[i]));
						map.put(FIELDS[i], fields[i]);
					}

				}

				ret.add(map);
			}

			return ret;
		}
	}
	public QQRealTimQuery(List<String> stockCodeList)
	{
		this.stockCodeList = stockCodeList;
		this.adapter =  new QQRealTimeDataApapter();
		this.loader = new QQHttpLoader();
	}

	public QQRealTimQuery(String code){
		stockCodeList = new ArrayList<>();
		stockCodeList.add(code);
        this.adapter =  new QQRealTimeDataApapter();
        this.loader = new QQHttpLoader();
	}
	
	public QQRealTimQuery(QQHttpLoader loader, List<String> stockCodeList, IDataAdapter adapter)
	{
		this.stockCodeList = stockCodeList;
		this.adapter =  adapter;
		this.loader = loader;
	}
	
	
	@SuppressWarnings("unchecked")
	public Object execute() throws Exception
	{
		List<String> list = loader.get(stockCodeList);		
		return adapter.parse(list);
	}
	

	

}
