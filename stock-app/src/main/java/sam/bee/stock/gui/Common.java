// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Common.java

package sam.bee.stock.gui;

import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import sam.bee.stock.vo.TradeTimeVO;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            HQApplet, CodeTable

/**
 * 
 * @author Administrator
 *
 */
public class Common {

    static final int TYPE_INVALID = -1;
    static final int TYPE_COMMON = 0;
    static final int TYPE_CANCEL = 1;
    static final int TYPE_INDEX = 2;
    static final int TYPE_INDEX_MAIN = 3;
    static final int TYPE_SERIES = 4;
    static final int TYPE_PAUSE = 5;
    static final int TYPE_FINISHIED = 6;
    static final int PRODUCT_CACHENUM = 50;

    public Common() {
    }

    public static int GetProductType(String strCode) {
        return 1;
    }

    /**
     * 格式化浮点
     * @param f
     * @param iPrecision
     * @return
     */
    public static String FloatToString(double f, int iPrecision) {
        if(iPrecision == 0)
            return String.valueOf((int)f);
        String strTarget = "";
        String strFormat = "0.";
        for(int i = 0; i < iPrecision; i++)
            strFormat = strFormat + "0";

        DecimalFormat dFormat = new DecimalFormat(strFormat);
        strTarget = dFormat.format(f);
        return strTarget;
    }

    public static void DrawDotLine(Graphics g, int x1, int y1, int x2, int y2) {
        int iSpace = 3;
        int iLen = (int)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        int num = iLen / 3;
        if(num <= 0)
            return;
        for(int i = 0; i < num; i++)
            if(i % 2 == 1)
                g.drawLine(x1 + ((x2 - x1) * i) / num, y1 + ((y2 - y1) * i) / num, x1 + ((x2 - x1) * (i + 1)) / num, y1 + ((y2 - y1) * (i + 1)) / num);

    }

    static String getProductTypeName(int type) {
        switch(type) {
        case 1: // '\001'
            return "普通商品";
        }
        return "";
    }

    static int GetTimeFromTimeIndex(int index, TradeTimeVO timeRange[]) {
        if(timeRange == null)
            return 0;
        else
            return getTimeFromIndex(index, timeRange);
    }

    static int GetTimeIndexFromTime(int hhmm, TradeTimeVO timeRange[]) {
        if(timeRange == null)
            return 0;
        else
            return getIndexFromTime(hhmm, timeRange);
    }

    static int GetTimeFromMinLineIndex(int index, TradeTimeVO timeRange[], int iMinLineInterval) {
        if(timeRange == null)
            return 0;
        int iNumPerMin = 60 / iMinLineInterval;
        int hhmm = getTimeFromIndex(index / iNumPerMin, timeRange);
        int ss = (iNumPerMin - 1 - index % iNumPerMin) * iMinLineInterval;
        if(ss > 0) {
            int iMins = ((hhmm / 100) * 60 + hhmm % 100) - 1;
            hhmm = (iMins / 60) * 100 + iMins % 60;
            return hhmm * 100 + (60 - ss);
        } else {
            return hhmm * 100 + ss;
        }
    }

    public static int GetMinLineIndexFromTime(int hhmmss, TradeTimeVO timeRange[], int iMinLineInterval) {
        if(timeRange == null)
            return 0;
        int hhmm = hhmmss / 100;
        int ss = hhmmss % 100;
        if(ss > 0) {
            int iMins = (hhmm / 100) * 60 + hhmm % 100;
            hhmm = (++iMins / 60) * 100 + iMins % 60;
        }
        int iIndex = getIndexFromTime(hhmm, timeRange);
        iIndex *= 60 / iMinLineInterval;
        if(ss == 0)
            ss = 60;
        iIndex += (ss - 1) / iMinLineInterval;
        if(iIndex < 0)
            iIndex = 0;
        return iIndex;
    }

    public static int strlen(Object temp[]) {
        int i;
        for(i = 0; temp[i] != null && i < temp.length; i++);
        return i;
    }

    public static TradeTimeVO[] getTimeRange(String code, Application applet) {
        if(applet.m_timeRange == null)
            return new TradeTimeVO[0];
        if(applet.isIndex(code))
            return applet.m_timeRange;
        CodeTable codeTable = (CodeTable)applet.m_ProductByHttp.get(code);
        if(codeTable == null)
            return new TradeTimeVO[0];
        Vector v = new Vector();
        for(int i = 0; i < codeTable.tradeSecNo.length; i++) {
            for(int j = 0; j < applet.m_timeRange.length; j++)
                if(codeTable.tradeSecNo[i] == applet.m_timeRange[j].orderID)
                    v.addElement(applet.m_timeRange[j]);

        }

        TradeTimeVO dataList[] = new TradeTimeVO[v.size()];
        for(int i = 0; i < dataList.length; i++)
            dataList[i] = (TradeTimeVO)v.elementAt(i);

        return dataList;
    }

    public static int getCurrent5MinTime(int hhmmss) {
        int newMin = (((hhmmss / 10000) * 60 + (hhmmss / 100) % 100) / 5) * 5;
        if(hhmmss % 500 >= 0)
            newMin += 5;
        int newTime = (newMin / 60) * 10000 + (newMin % 60) * 100;
        return newTime;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str, int ch) {
        Vector v = new Vector();
        int i = 0;
        do {
            int j = str.indexOf(ch, i);
            if(j == -1) {
                v.addElement(str.substring(i));
                break;
            }
            v.addElement(str.substring(i, j));
            i = j + 1;
        } while(true);
        String result[] = new String[v.size()];
        for(i = 0; i < v.size(); i++)
            result[i] = (String)v.elementAt(i);

        return result;
    }

//    public static void main(String args[]) {
//        ResourceBundle rb = null;
//        String s2 = "";
//        try {
//            rb = ResourceBundle.getBundle("rc/string", new Locale("zh"));
//            String s1 = rb.getString("Volume");
//            if(s1 != null)
//                s2 = new String(s1.getBytes("8859_1"), "GBK");
//            else
//                System.out.println("null");
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("rb:" + rb);
//        System.out.println(s2);
//    }
    
    public static String timeIntToString(int iTime) {
        return iTime / 100 + ":" + iTime % 100;
    }

    public static int timeStringToInt(String strTime) {
        strTime.replaceAll(":", "");
        return Integer.parseInt(strTime);
    }

    public static int getTotalMinute(TradeTimeVO timeRange[]) {
        int iMin = 0;
        for(int i = 0; i < timeRange.length; i++)
            iMin += ((timeRange[i].endTime / 100) * 60 + timeRange[i].endTime % 100) - ((timeRange[i].beginTime / 100) * 60 + timeRange[i].beginTime % 100);

        return iMin;
    }

    public static int getTimeFromIndex(int iIndex, TradeTimeVO timeRange[]) {
        int iIndexCur = iIndex + 1;
        for(int i = 0; i < timeRange.length; i++) {
            int iRange = ((timeRange[i].endTime / 100) * 60 + timeRange[i].endTime % 100) - ((timeRange[i].beginTime / 100) * 60 + timeRange[i].beginTime % 100);
            if(iRange < iIndexCur) {
                iIndexCur -= iRange;
            } else {
                int iTime = (timeRange[i].beginTime / 100) * 60 + timeRange[i].beginTime % 100 + iIndexCur;
                iTime = (iTime / 60) * 100 + iTime % 60;
                return iTime;
            }
        }

        return -1;
    }

    public static int getIndexFromTime(int iTime, TradeTimeVO timeRange[]) {
        int iIndex = -1;
        for(int i = 0; i < timeRange.length; i++) {
            if(iTime < timeRange[i].beginTime)
                return iIndex;
            if(timeRange[i].endTime >= iTime && iTime >= timeRange[i].beginTime)
                iIndex += ((iTime / 100) * 60 + iTime % 100) - ((timeRange[i].beginTime / 100) * 60 + timeRange[i].beginTime % 100);
            else
                iIndex += ((timeRange[i].endTime / 100) * 60 + timeRange[i].endTime % 100) - ((timeRange[i].beginTime / 100) * 60 + timeRange[i].beginTime % 100);
        }

        if(iIndex < 0)
            iIndex = 0;
        return iIndex;
    }

//    public static void main(String arg[]) {
//        TradeTimeVO vo[] = new TradeTimeVO[1];
//        vo[0] = new TradeTimeVO();
//        vo[0].beginTime = 930;
//        vo[0].endTime = 1800;
//        vo[0].orderID = 0;
//        System.out.println(getIndexFromTime(931, vo));
//        System.out.println(getTimeFromIndex(0, vo));
//   
//    }
}
