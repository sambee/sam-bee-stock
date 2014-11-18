// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   KLineData.java

package sam.bee.stock.gui;


/**
 * 
 * @author Administrator
 *
 */
public class KLineData {

    /** 交易日期*/ public long date;
    /** 开盘价格 */ public float openPrice;
    /** 收盘价格*/public float closePrice;
    /** 当日最高价 */ public float highPrice;
    /** 当日最低价*/ public float lowPrice;
    /** 均价*/ public float balancePrice;
    /** 成交量　*/ public long totalAmount;
    /** 成交金额　*/public double totalMoney;
    /** 交易量*/public int reserveCount;

    public KLineData() {
    }

    public String toString() {
        return "\r\ndate:" + date + "\r\nopenPrice:" + openPrice + "\r\nhighPrice:" + highPrice + "\r\nlowPrice:" + lowPrice + "\r\nclosePrice:" + closePrice + "\r\ntotalAmount:" + totalAmount + "\r\ntotalMoney:" + totalMoney;
    }
}
