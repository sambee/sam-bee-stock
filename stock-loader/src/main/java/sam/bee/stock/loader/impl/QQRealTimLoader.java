package sam.bee.stock.loader.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static sam.bee.stock.Const.*;

/**
 * QQSimpleStockInfoLoader.java
 *
 * @author Sam Wong
 *         QQ: 1557299538
 * @create: 2014年4月23日
 * <p>
 * Modification
 * -------------------------------------------
 * <p>
 * 获取简要信息：
 * [html]
 * http://qt.gtimg.cn/q=s_sz000858
 * 返回数据：
 * [html]
 * v_s_sz000858="51~五 粮 液~000858~27.78~0.18~0.65~417909~116339~~1054.52";
 * 以 ~ 分割字符串中内容，下标从0开始，依次为：
 * [html]
 * 0: 未知
 * 1: 名字
 * 2: 代码
 * 3: 当前价格
 * 4: 涨跌
 * 5: 涨跌%
 * 6: 成交量（手）
 * 7: 成交额（万）
 * 8:
 * 9: 总市值
 */
public class QQRealTimLoader extends BaseLoader implements ILoader {



    public QQRealTimLoader() {
    }

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

    public List<Map<String, String>> parse(String dataStr) {

        List<Map<String, String>> ret = new Vector<Map<String, String>>();
        Map<String, String> map;

        String tmp = (String) dataStr;
        String endChar = tmp.substring(tmp.length() - 1);
        if ("\n".equals(endChar)) {
            tmp = tmp.substring(0, tmp.length() - 1);
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

        String createdTime = map.get("CREATED_TIME");
        if(createdTime==null){
            return null;
        }
        String date =  createdTime.substring(0,4)+"-"+createdTime.substring(4,6)+"-"+createdTime.substring(6,8);
        map.put(DATE, date);
        map.put(CLOSE, map.get(STOCK_PRICE));
        ret.add(map);
        return ret;
    }

    //private final static String URL_TEMPLATE = "http://qt.gtimg.cn/r=0.7938921226847172q=<#list list as l><#if l?starts_with(\"0\") >s_sz${l}<#else>s_sh${l}</#if></#list>";
    private final static String URL_TEMPLATE = "http://qt.gtimg.cn/r=0.7938921226847172q=${STOCK_TYPE}${STOCK_CODE},";

    @SuppressWarnings({"unchecked", "rawtypes"})
    public String get(String code, String type) throws Exception {
//        Map root = new HashMap();
//        root.put(STOCK_CODE, code);
//        root.put(STOCK_TYPE, type);
        //String request = FreeMarkerUtils.convert(URL_TEMPLATE, root);
        String temp = replaceValue(URL_TEMPLATE, STOCK_CODE, code);
        temp =  replaceValue(temp, STOCK_TYPE, type);
        //logger.debug(request);
        return getResponse(temp);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> execute(String code, String type) throws Exception {
        {
            String str = get(code, type);
            return parse(str);
        }

    }


}
