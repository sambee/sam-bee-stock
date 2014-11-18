// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:40:59 

//source File Name:   ProductDataVO.java

package sam.bee.stock.vo;

import java.util.Date;
import java.util.Vector;

public class ProductDataVO {

    public Date time;
    public String code;
    public String name;
    public float yesterBalancePrice;
    public float closePrice;
    public float openPrice;
    public float highPrice;
    public float lowPrice;
    public float curPrice;
    public float upRate;
    public float shakeRate;
    public int curAmount;
    public int openAmount;
    public int closeAmount;
    public int reserveCount;
    public int reserveChange;
    public float balancePrice;
    public double totalMoney;
    public long totalAmount;
    public float buyPrice[];
    public float sellPrice[];
    public int buyAmount[];
    public int sellAmount[];
    public int outAmount;
    public int inAmount;
    public int tradeCue;
    public int no;
    public long averAmount5;
    public boolean bUpdated;
    public float amountRate;
    public float consignRate;
    public float upRate5min;
    public Vector billData;

    public ProductDataVO() {
        buyPrice = new float[5];
        sellPrice = new float[5];
        buyAmount = new int[5];
        sellAmount = new int[5];
        bUpdated = true;
        billData = new Vector();
    }

    public Object clone() {
        ProductDataVO data = new ProductDataVO();
        data.amountRate = amountRate;
        data.averAmount5 = averAmount5;
        data.balancePrice = balancePrice;
        data.billData = billData;
        for(int i = 0; i < 5; i++) {
            data.buyAmount[i] = buyAmount[i];
            data.buyPrice[i] = buyPrice[i];
            data.sellAmount[i] = sellAmount[i];
            data.sellPrice[i] = sellPrice[i];
        }

        data.closeAmount = closeAmount;
        data.closePrice = closeAmount;
        data.code = code;
        data.consignRate = closeAmount;
        data.curAmount = curAmount;
        data.curPrice = curPrice;
        data.highPrice = highPrice;
        data.inAmount = inAmount;
        data.lowPrice = lowPrice;
        data.name = name;
        data.no = no;
        data.openAmount = openAmount;
        data.openPrice = openPrice;
        data.outAmount = outAmount;
        data.reserveChange = reserveChange;
        data.reserveCount = reserveCount;
        data.time = time;
        data.totalAmount = totalAmount;
        data.totalMoney = totalMoney;
        data.tradeCue = tradeCue;
        data.upRate = tradeCue;
        data.shakeRate = shakeRate;
        data.upRate5min = upRate5min;
        data.yesterBalancePrice = yesterBalancePrice;
        return data;
    }

    public String toString() {
        String sep = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("**" + getClass().getName() + "**" + sep);
        sb.append("Time:" + time + sep);
        sb.append("Code:" + code + sep);
        sb.append("Name:" + name + sep);
        sb.append("YesterPrice:" + yesterBalancePrice + sep);
        sb.append("ClosePrice:" + closePrice + sep);
        sb.append("OpenPrice:" + openPrice + sep);
        sb.append("HighPrice:" + highPrice + sep);
        sb.append("LowPrice:" + lowPrice + sep);
        sb.append("CurPrice:" + curPrice + sep);
        sb.append("CurAmount:" + curAmount + sep);
        sb.append("OpenAmount:" + openAmount + sep);
        sb.append("CloseAmount:" + closeAmount + sep);
        sb.append("ReserveCount:" + reserveCount + sep);
        sb.append("AverageValue:" + balancePrice + sep);
        sb.append("TotalMoney:" + totalMoney + sep);
        sb.append("TotalAmount:" + totalAmount + sep);
        for(int i = 0; i < 5; i++) {
            sb.append("BuyPrice" + (i + 1) + ":" + buyPrice[i] + sep);
            sb.append("SellPrice" + (i + 1) + ":" + sellPrice[i] + sep);
            sb.append("BuyAmount" + (i + 1) + ":" + buyAmount[i] + sep);
            sb.append("SellAmount" + (i + 1) + ":" + sellAmount[i] + sep);
        }

        sb.append("OutAmount:" + outAmount + sep);
        sb.append("InAmount:" + inAmount + sep);
        sb.append("TradeCue:" + tradeCue + sep);
        sb.append("NO:" + no + sep);
        sb.append("AverAmount5:" + averAmount5 + sep);
        sb.append("AmountRate:" + amountRate + sep);
        sb.append(sep);
        return sb.toString();
    }
}
