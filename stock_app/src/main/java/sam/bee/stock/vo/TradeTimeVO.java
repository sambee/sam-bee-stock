// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:40:59 

//source File Name:   TradeTimeVO.java

package sam.bee.stock.vo;

import java.util.Date;

public class TradeTimeVO {

    public int orderID;
    public int beginTime;
    public int endTime;
    public int status;
    public Date modifytime;

    public TradeTimeVO() {
        modifytime = new Date();
    }

    public String toString() {
        String sep = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("**" + getClass().getName() + "**" + sep);
        sb.append("OrderID:" + orderID + sep);
        sb.append("BeginTime:" + beginTime + sep);
        sb.append("EndTime:" + endTime + sep);
        sb.append("Status:" + status + sep);
        sb.append("Modifytime:" + modifytime + sep);
        sb.append(sep);
        return sb.toString();
    }


}
