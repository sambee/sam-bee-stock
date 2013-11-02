package sam.bee.stock.loader.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sam.bee.stock.loader.IDataAdapter;

public class QQRealTimeDataApapter implements IDataAdapter{

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
	
	private static final String[] FIELDS = {
	"未知", 		//0: 未知
	"名字",			//	 1: 名字
	"代码",			//	 2: 代码
	"当前价格",		//	 3: 当前价格
	"昨收",			//	 4: 昨收
	"今开",			//	 5: 今开
	"成交量（手）",	//	 6: 成交量（手）
	"外盘",			//	 7: 外盘
	"内盘",			//	 8: 内盘
	"买1",			//	 9: 买一
	"买1量（手）",	//	10: 买一量（手）
	"买2",			//	11
	"买2量（手",	//	12
	"买3",			//	13
	"买3量",		//	14
	"买4",			//	15
	"买4量（手）",	//	16
	"买5",			//	17
	"买5量（手）", //18: 买五
	"卖一",	//	19: 卖一
	"卖一量",	//	20: 卖一量
	"卖2",	//	21: 卖二 卖五
	"卖2量",	//	22: 卖二 卖五
	"卖3",	//	23: 卖二 卖五
	"卖3量",	//	24: 卖二 卖五
	"卖4",	//	25: 卖二 卖五
	"卖4量",	//	26: 卖二 卖五
	"卖5",	//	27: 卖二 卖五
	"卖5量",	//	28: 卖二 卖五
	"最近逐笔成交",	//	29: 最近逐笔成交
	"时间",	//	30: 时间
	"涨跌",	//	31: 涨跌
	"涨跌%",	//	32: 涨跌%
	"最高",	//	33: 最高
	"最低",	//	34: 最低
	"价格/成交量（手）/成交额",	//	35: 价格/成交量（手）/成交额
	"成交量（手）",	//	36: 成交量（手）
	"成交额（万）",	//	37: 成交额（万）
	"换手率",	//	38: 换手率
	"市盈率",	//	39: 市盈率
	"最高",	//	40: 
	"最高",	//	41: 最高
	"最低",	//	42: 最低
	"振幅",	//	43: 振幅
	"流通市值",	//	44: 流通市值
	"总市值",//	45: 总市值
	"市净率",//	46: 市净率
	"涨停价",//	47: 涨停价
	"跌停价"//	48: 跌停价
	};
	
	@Override
	public Object parse(List<?> list) {
		
		List<Map<String, String>> ret = new ArrayList<Map<String,String>>();
		Map<String, String> map ;
		for(Object obj : list){
			String tmp = (String)obj;
			
			//System.out.println(tmp);
			
			String[] values = tmp.replaceAll("\"", "").replaceAll(";", "").split("=");
			String[] fields = values[1].split("~");
			map = new HashMap<String, String>(fields.length);
			for(int i=0;i<fields.length;i++){	
				if(fields[i]!=null && fields[i].length()>0){
					
					//System.out.println(String.format("%d%s%s", i, FIELDS[i], fields[i]));
					map.put(FIELDS[i], fields[i]);
				}
				
			}
			ret.add(map);
		}
	
		return ret;
	}
			
}
