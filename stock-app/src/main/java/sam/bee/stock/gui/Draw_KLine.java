// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Draw_KLine.java

package sam.bee.stock.gui;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;

import sam.bee.stock.app.indicator.ASI;
import sam.bee.stock.app.indicator.BIAS;
import sam.bee.stock.app.indicator.BOLL;
import sam.bee.stock.app.indicator.BRAR;
import sam.bee.stock.app.indicator.CCI;
import sam.bee.stock.app.indicator.CR;
import sam.bee.stock.app.indicator.DMA;
import sam.bee.stock.app.indicator.DMI;
import sam.bee.stock.app.indicator.EMV;
import sam.bee.stock.app.indicator.EXPMA;
import sam.bee.stock.app.indicator.IndicatorBase;
import sam.bee.stock.app.indicator.IndicatorPos;
import sam.bee.stock.app.indicator.KDJ;
import sam.bee.stock.app.indicator.MA;
import sam.bee.stock.app.indicator.MACD;
import sam.bee.stock.app.indicator.MIKE;
import sam.bee.stock.app.indicator.OBV;
import sam.bee.stock.app.indicator.PSY;
import sam.bee.stock.app.indicator.ROC;
import sam.bee.stock.app.indicator.RSI;
import sam.bee.stock.app.indicator.Reserve;
import sam.bee.stock.app.indicator.SAR;
import sam.bee.stock.app.indicator.TRIX;
import sam.bee.stock.app.indicator.VOL;
import sam.bee.stock.app.indicator.VR;
import sam.bee.stock.app.indicator.WVAD;
import sam.bee.stock.app.indicator.W_R;
import sam.bee.stock.vo.BillDataVO;
import sam.bee.stock.vo.TradeTimeVO;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            MenuListener, HQApplet, Page_KLine, ProductData, 
//            RHColor, KLineData, CodeTable, Common

public class Draw_KLine {

    private static int cache_m_VirtualRatio = 15;
    Page_KLine parent;
    static final int CYCLE_DAY = 1;
    static final int CYCLE_WEEK = 2;
    static final int CYCLE_MONTH = 3;
    static final int CYCLE_MIN5 = 4;
    static final int CYCLE_MIN15 = 5;
    static final int CYCLE_MIN30 = 6;
    static final int CYCLE_MIN60 = 7;
    private ProductData m_product;
    KLineData m_kData[];
    Rectangle m_rcPane[];
    IndicatorBase m_indicator[];
    IndicatorPos m_pos;
    private int m_iPos;
    private Rectangle m_rcLabel;
    int m_iPrecision;

    public Draw_KLine(Page_KLine _parent) {
        m_rcPane = new Rectangle[3];
        m_indicator = new IndicatorBase[3];
        m_pos = new IndicatorPos();
        m_iPos = -1;
        m_pos.m_VirtualRatio = cache_m_VirtualRatio;
        parent = _parent;
        int iPrecision = ((MenuListener) (parent)).application.getPrecision(((MenuListener) (parent)).application.strCurrentCode);
        if(((MenuListener) (parent)).application.isIndex(((MenuListener) (parent)).application.strCurrentCode) && ((MenuListener) (parent)).application.m_bShowIndexKLine == 0)
            m_indicator[0] = new MA(m_pos, 2, iPrecision);
        else
            m_indicator[0] = new MA(m_pos, Page_KLine.m_iCurKLineType, iPrecision);
        m_indicator[1] = new VOL(m_pos, 0);
        CreateIndicator();
    }

    void Paint(Graphics g, Rectangle rc, ProductData product) {
        m_product = product;
        if(product != null)
            m_iPrecision = ((MenuListener) (parent)).application.getPrecision(m_product.sCode);
        MakeCycleData();
        GetScreen(g, rc);
        DrawCycle(g);
        if(m_rcPane[0].width < 0)
            return;
        DrawTimeCoordinate(g);
        for(int i = 0; i < 3; i++)
            m_indicator[i].Paint(g, m_rcPane[i], m_kData);

        ((MenuListener) (parent)).application.EndPaint();
        DrawCursor(-1);
        DrawLabel();
    }

    private void GetScreen(Graphics g, Rectangle rc) {
        g.setFont(new Font("\u5B8B\u4F53", 0, 14));
        FontMetrics fm = g.getFontMetrics();
        int iHeight = fm.getHeight();
        int x = rc.x + 4 * iHeight;
        int width = rc.width - 4 * iHeight - 2;
        m_rcPane[0] = new Rectangle(x, rc.y, width, (rc.height - iHeight) / 2);
        m_rcPane[1] = new Rectangle(x, rc.y + m_rcPane[0].height, width, (rc.height - iHeight) / 4);
        m_rcPane[2] = new Rectangle(x, m_rcPane[1].y + m_rcPane[1].height, width, (rc.height - iHeight) / 4);
        g.setColor(Application.rhColor.clGrid);
        g.drawRect(m_rcPane[0].x, m_rcPane[0].y, width, rc.height - iHeight);
        g.drawLine(m_rcPane[1].x, m_rcPane[1].y, m_rcPane[1].x + width, m_rcPane[1].y);
        g.drawLine(m_rcPane[2].x, m_rcPane[2].y, m_rcPane[1].x + width, m_rcPane[2].y);
        int iIndex = -1;
        if(m_iPos != -1)
            iIndex = m_pos.m_Begin + m_iPos;
        if(m_kData != null)
            m_pos.GetScreen(m_rcPane[0].width, m_kData.length);
        else
            m_pos.GetScreen(m_rcPane[0].width, 0);
        if(m_iPos != -1)
            if(iIndex >= m_pos.m_Begin && iIndex <= m_pos.m_End)
                m_iPos = iIndex - m_pos.m_Begin;
            else
                m_iPos = -1;
        if(((MenuListener) (parent)).application.m_iKLineCycle >= 4 && ((MenuListener) (parent)).application.m_iKLineCycle <= 7)
            m_rcLabel = new Rectangle(rc.x + 1, rc.y + iHeight, x - 1, iHeight * 19);
        else
            m_rcLabel = new Rectangle(rc.x + 1, rc.y + iHeight, x - 1, iHeight * 18);
    }

    private void DrawTimeCoordinate(Graphics g) {
        if(m_kData == null || m_kData.length == 0)
            return;
        g.setFont(new Font("\u5B8B\u4F53", 0, 14));
        FontMetrics fm = g.getFontMetrics();
        int iHeight = fm.getHeight();
        Rectangle rc = new Rectangle(m_rcPane[2].x, m_rcPane[2].y + m_rcPane[2].height, m_rcPane[2].width, iHeight);
        int iStringWidth;
        switch(((MenuListener) (parent)).application.m_iKLineCycle) {
        case 3: // '\003'
            iStringWidth = fm.stringWidth("2004-10");
            break;

        case 1: // '\001'
        case 2: // '\002'
            iStringWidth = fm.stringWidth("2004-10-10");
            break;

        default:
            iStringWidth = fm.stringWidth("10-30 09:40");
            break;
        }
        int step = (int)(((double)iStringWidth * 1.5D) / (double)m_pos.m_Ratio) + 1;
        int scrcount = (m_pos.m_End - m_pos.m_Begin) + 1;
        int count = (m_pos.m_End - m_pos.m_Begin) / step;
        int y = rc.y + fm.getAscent();
        int x = (int)((float)rc.x + m_pos.m_Ratio / 2.0F);
        g.setColor(Application.rhColor.clGrid);
        g.drawLine(x, rc.y, x, rc.y + 5);
        String str = TimeToString(((MenuListener) (parent)).application.m_iKLineCycle, m_kData[m_pos.m_Begin].date);
        g.setColor(Application.rhColor.clNumber);
        g.drawString(str, x, y);
        for(int i = 1; i < count; i++) {
            x = (int)((float)rc.x + (float)(i * step) * m_pos.m_Ratio + m_pos.m_Ratio / 2.0F);
            g.setColor(Application.rhColor.clGrid);
            g.drawLine(x, rc.y, x, rc.y + 5);
            str = TimeToString(((MenuListener) (parent)).application.m_iKLineCycle, m_kData[i * step + m_pos.m_Begin].date);
            x -= iStringWidth / 2;
            g.setColor(Application.rhColor.clNumber);
            g.drawString(str, x, y);
        }

        if(count > 0) {
            x = rc.x + (int)((float)scrcount * m_pos.m_Ratio - m_pos.m_Ratio / 2.0F);
            g.setColor(Application.rhColor.clGrid);
            g.drawLine(x, rc.y, x, rc.y + 5);
            str = TimeToString(((MenuListener) (parent)).application.m_iKLineCycle, m_kData[m_pos.m_End].date);
            if(count > 1 || x + iStringWidth > rc.x + rc.width)
                x -= iStringWidth;
            g.setColor(Application.rhColor.clNumber);
            g.drawString(str, x, y);
        }
    }

    private String TimeToString(int iCycle, long date) {
        String str;
        switch(iCycle) {
        case 1: // '\001'
        case 2: // '\002'
            str = String.valueOf(date);
            if(str.length() >= 8)
                str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
            break;

        case 3: // '\003'
            str = String.valueOf(date / 100L);
            if(str.length() >= 6)
                str = str.substring(0, 4) + "-" + str.substring(4, 6);
            break;

        default:
            str = String.valueOf(date);
            if(str.length() >= 12) {
                str = str.substring(4);
                str = str.substring(0, 2) + "-" + str.substring(2, 4) + " " + str.substring(4, 6) + ":" + str.substring(6, 8);
            }
            break;
        }
        return str;
    }

    private void MakeCycleData() {
        if(m_product == null)
            return;
        if(1 == ((MenuListener) (parent)).application.m_iKLineCycle || 2 == ((MenuListener) (parent)).application.m_iKLineCycle || 3 == ((MenuListener) (parent)).application.m_iKLineCycle)
            MakeTodayDayLine();
        else
            MakeToday5MinLine();
        if(m_kData == null)
            return;
        switch(((MenuListener) (parent)).application.m_iKLineCycle) {
        case 2: // '\002'
            MakeWeek();
            break;

        case 3: // '\003'
            MakeMonth();
            break;

        case 5: // '\005'
            MakeMinCycle(15);
            break;

        case 6: // '\006'
            MakeMinCycle(30);
            break;

        case 7: // '\007'
            MakeMinCycle(60);
            break;
        }
    }

    private void MakeTodayDayLine() {
        if(m_product.realData == null || m_product.realData.curPrice < 0.001F) {
            m_kData = m_product.dayKLine;
            return;
        }
        int iNum;
        if(m_product.dayKLine == null)
            iNum = 0;
        else
            iNum = m_product.dayKLine.length;
        if(iNum > 0 && m_product.dayKLine[iNum - 1].date > (long)((MenuListener) (parent)).application.m_iDate) {
            m_kData = m_product.dayKLine;
            return;
        }
        if(iNum > 0 && m_product.dayKLine[iNum - 1].date == (long)((MenuListener) (parent)).application.m_iDate) {
            if(m_product.realData.totalAmount <= 0L) {
                m_kData = m_product.dayKLine;
                return;
            }
            iNum--;
        }
        m_kData = new KLineData[iNum + 1];
        for(int i = 0; i < iNum; i++)
            m_kData[i] = m_product.dayKLine[i];

        m_kData[iNum] = new KLineData();
        m_kData[iNum].date = ((MenuListener) (parent)).application.m_iDate;
        m_kData[iNum].openPrice = m_product.realData.openPrice;
        m_kData[iNum].highPrice = m_product.realData.highPrice;
        m_kData[iNum].lowPrice = m_product.realData.lowPrice;
        m_kData[iNum].closePrice = m_product.realData.curPrice;
        m_kData[iNum].balancePrice = m_product.realData.balancePrice;
        m_kData[iNum].totalAmount = m_product.realData.totalAmount;
        m_kData[iNum].totalMoney = m_product.realData.totalMoney;
        m_kData[iNum].reserveCount = m_product.realData.reserveCount;
    }

    private void MakeToday5MinLine() {
        if(m_product.realData == null)
            return;
        KLineData today5Min[] = get5MinKLine(m_product.sCode, m_product.vBill, m_product.realData.yesterBalancePrice);
        if(m_product.min5KLine == null) {
            m_kData = today5Min;
            return;
        }
        if(today5Min.length == 0) {
            m_kData = m_product.min5KLine;
            return;
        }
        int iMax;
        for(iMax = m_product.min5KLine.length - 1; iMax >= 0; iMax--)
            if(m_product.min5KLine[iMax].date / 10000L < (long)((MenuListener) (parent)).application.m_iDate)
                break;

        iMax++;
        m_kData = new KLineData[iMax + today5Min.length];
        for(int i = 0; i < iMax; i++)
            m_kData[i] = m_product.min5KLine[i];

        for(int i = 0; i < today5Min.length; i++)
            m_kData[i + iMax] = today5Min[i];

    }

    /**
     * 获取5分钟线
     * @param code
     * @param vBillData
     * @param fPreClosePrice
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	KLineData[] get5MinKLine(String code, Vector vBillData, float fPreClosePrice) {
        if(vBillData == null)
            return new KLineData[0];
        CodeTable codeTable = (CodeTable)((MenuListener) (parent)).application.m_ProductByHttp.get(code);
        Vector vector = new Vector();
        KLineData data = null;
        int curTime = -1;
        float preMoney = 0.0F;
        long preAmount = 0L;
        for(int i = 0; i < vBillData.size(); i++) {
            BillDataVO bill = (BillDataVO)vBillData.elementAt(i);
            if(bill.curPrice > 0.0F) {
                int time = Common.getCurrent5MinTime(bill.time);
                if(time != curTime) {
                    if(data != null) {
                        if(data.totalAmount > 0L)
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
                        vector.addElement(data);
                    }
                    data = new KLineData();
                    data.date = (long)((MenuListener) (parent)).application.m_iDate * 10000L + (long)(time / 100);
                    data.openPrice = bill.curPrice;
                    data.highPrice = bill.curPrice;
                    data.lowPrice = bill.curPrice;
                    data.closePrice = bill.curPrice;
                    data.balancePrice = bill.balancePrice;
                    data.reserveCount = bill.reserveCount;
                    data.totalAmount += bill.totalAmount - preAmount;
                    data.totalMoney += bill.totalMoney - (double)preMoney;
                    curTime = time;
                } else {
                    if(bill.curPrice > data.highPrice)
                        data.highPrice = bill.curPrice;
                    if(bill.curPrice < data.lowPrice)
                        data.lowPrice = bill.curPrice;
                    data.closePrice = bill.curPrice;
                    data.balancePrice = bill.balancePrice;
                    data.reserveCount = bill.reserveCount;
                    data.totalAmount += bill.totalAmount - preAmount;
                    data.totalMoney += bill.totalMoney - (double)preMoney;
                }
                preAmount = bill.totalAmount;
                preMoney = (float)bill.totalMoney;
            }
        }

        if(data != null) {
            if(data.totalAmount > 0L)
                if(codeTable != null)
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                else
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
            vector.addElement(data);
        }
        KLineData dataList[] = new KLineData[0];
        if(vector.size() > 0) {
            TradeTimeVO timeRange[] = Common.getTimeRange(code, ((MenuListener) (parent)).application);
            int totalMin = Common.getIndexFromTime(((MenuListener) (parent)).application.m_iTime / 100, timeRange);
            int iLen = totalMin / 5;
            if(totalMin % 5 > 0){
                iLen++;
            }
            dataList = new KLineData[iLen];
            int iCur = 0;
            for(int i = 0; i < iLen; i++) {
                int hhmm = Common.getTimeFromIndex(i * 5 + 4, timeRange);
                long cTime = (long)((MenuListener) (parent)).application.m_iDate * 10000L + (long)hhmm;
                if(iCur < vector.size()) {
                    data = (KLineData)vector.elementAt(iCur);
                    if(cTime == data.date) {
                        dataList[i] = data;
                        iCur++;
                        continue;
                    }
                }
                dataList[i] = new KLineData();
                dataList[i].date = cTime;
                if(i == 0) {
                    dataList[i].balancePrice = fPreClosePrice;
                    dataList[i].openPrice = dataList[i].highPrice = dataList[i].lowPrice = dataList[i].closePrice = fPreClosePrice;
                    dataList[i].reserveCount = 0;
                    dataList[i].totalAmount = 0L;
                    dataList[i].totalMoney = 0.0D;
                } else {
                    dataList[i].balancePrice = dataList[i - 1].balancePrice;
                    dataList[i].openPrice = dataList[i].highPrice = dataList[i].lowPrice = dataList[i].closePrice = dataList[i - 1].closePrice;
                    dataList[i].reserveCount = dataList[i - 1].reserveCount;
                    dataList[i].totalAmount = 0L;
                    dataList[i].totalMoney = 0.0D;
                }
            }

        }
        return dataList;
    }

    private void MakeWeek() {
        CodeTable codeTable = (CodeTable)((MenuListener) (parent)).application.m_ProductByHttp.get(m_product.sCode);
        Vector vector = new Vector();
        KLineData data = null;
        for(int i = 0; i < m_kData.length; i++) {
            boolean bNewWeek;
            if(data != null) {
                Calendar c1 = Calendar.getInstance();
                c1.set((int)data.date / 10000, ((int)data.date / 100) % 100 - 1, (int)data.date % 100);
                int iWeek1 = c1.get(7);
                Calendar c2 = Calendar.getInstance();
                c2.set((int)m_kData[i].date / 10000, ((int)m_kData[i].date / 100) % 100 - 1, (int)m_kData[i].date % 100);
                int iWeek2 = c2.get(7);
                if(iWeek1 >= iWeek2) {
                    bNewWeek = true;
                } else {
                    c1.add(5, 7);
                    if(c1.before(c2))
                        bNewWeek = true;
                    else
                        bNewWeek = false;
                }
            } else {
                bNewWeek = true;
            }
            if(bNewWeek) {
                if(data != null) {
                    if(data.totalAmount > 0L)
                        if(codeTable != null)
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                        else
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
                    vector.addElement(data);
                }
                data = new KLineData();
                data.closePrice = m_kData[i].closePrice;
                data.date = m_kData[i].date;
                data.highPrice = m_kData[i].highPrice;
                data.lowPrice = m_kData[i].lowPrice;
                data.openPrice = m_kData[i].openPrice;
                data.balancePrice = m_kData[i].balancePrice;
                data.totalAmount = m_kData[i].totalAmount;
                data.totalMoney = m_kData[i].totalMoney;
                data.reserveCount = m_kData[i].reserveCount;
            } else {
                data.date = m_kData[i].date;
                if(m_kData[i].highPrice > data.highPrice)
                    data.highPrice = m_kData[i].highPrice;
                if(m_kData[i].lowPrice < data.lowPrice)
                    data.lowPrice = m_kData[i].lowPrice;
                data.closePrice = m_kData[i].closePrice;
                data.balancePrice = m_kData[i].balancePrice;
                data.totalAmount += m_kData[i].totalAmount;
                data.totalMoney += m_kData[i].totalMoney;
                data.reserveCount = m_kData[i].reserveCount;
            }
        }

        if(data != null) {
            if(data.totalAmount > 0L)
                if(codeTable != null)
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                else
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
            vector.addElement(data);
        }
        m_kData = new KLineData[vector.size()];
        for(int i = 0; i < vector.size(); i++)
            m_kData[i] = (KLineData)vector.elementAt(i);

    }

    private void MakeMonth() {
        Vector vector = new Vector();
        KLineData data = null;
        int iCurMonth = -1;
        CodeTable codeTable = (CodeTable)((MenuListener) (parent)).application.m_ProductByHttp.get(m_product.sCode);
        for(int i = 0; i < m_kData.length; i++) {
            int iMonth = (int)m_kData[i].date / 100;
            if(iMonth != iCurMonth) {
                if(data != null) {
                    if(data.totalAmount > 0L)
                        if(codeTable != null)
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                        else
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
                    vector.addElement(data);
                }
                data = new KLineData();
                data.closePrice = m_kData[i].closePrice;
                data.highPrice = m_kData[i].highPrice;
                data.lowPrice = m_kData[i].lowPrice;
                data.openPrice = m_kData[i].openPrice;
                data.balancePrice = m_kData[i].balancePrice;
                data.totalAmount = m_kData[i].totalAmount;
                data.totalMoney = m_kData[i].totalMoney;
                data.reserveCount = m_kData[i].reserveCount;
                data.date = iMonth * 100;
                iCurMonth = iMonth;
            } else {
                if(m_kData[i].highPrice > data.highPrice)
                    data.highPrice = m_kData[i].highPrice;
                if(m_kData[i].lowPrice < data.lowPrice)
                    data.lowPrice = m_kData[i].lowPrice;
                data.closePrice = m_kData[i].closePrice;
                data.balancePrice = m_kData[i].balancePrice;
                data.totalAmount += m_kData[i].totalAmount;
                data.totalMoney += m_kData[i].totalMoney;
                data.reserveCount = m_kData[i].reserveCount;
            }
        }

        if(data != null) {
            if(data.totalAmount > 0L)
                if(codeTable != null)
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                else
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
            vector.addElement(data);
        }
        m_kData = new KLineData[vector.size()];
        for(int i = 0; i < vector.size(); i++)
            m_kData[i] = (KLineData)vector.elementAt(i);

    }

    private void MakeMinCycle(int iMin) {
        Vector vector = new Vector();
        KLineData data = null;
        long curDate = -1L;
        CodeTable codeTable = (CodeTable)((MenuListener) (parent)).application.m_ProductByHttp.get(m_product.sCode);
        for(int i = 0; i < m_kData.length; i++) {
            long date = GetCurrentDateTime(m_kData[i].date, iMin);
            if(date != curDate) {
                if(data != null) {
                    if(data.totalAmount > 0L)
                        if(codeTable != null)
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                        else
                            data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
                    vector.addElement(data);
                }
                data = new KLineData();
                data.closePrice = m_kData[i].closePrice;
                data.highPrice = m_kData[i].highPrice;
                data.lowPrice = m_kData[i].lowPrice;
                data.openPrice = m_kData[i].openPrice;
                data.balancePrice = m_kData[i].balancePrice;
                data.totalAmount = m_kData[i].totalAmount;
                data.totalMoney = m_kData[i].totalMoney;
                data.reserveCount = m_kData[i].reserveCount;
                data.date = date;
                curDate = date;
            } else {
                if(m_kData[i].highPrice > data.highPrice)
                    data.highPrice = m_kData[i].highPrice;
                if(m_kData[i].lowPrice < data.lowPrice)
                    data.lowPrice = m_kData[i].lowPrice;
                data.closePrice = m_kData[i].closePrice;
                data.balancePrice = m_kData[i].balancePrice;
                data.totalAmount += m_kData[i].totalAmount;
                data.totalMoney += m_kData[i].totalMoney;
                data.reserveCount = m_kData[i].reserveCount;
            }
        }

        if(data != null) {
            if(data.totalAmount > 0L)
                if(codeTable != null)
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount / (double)codeTable.fUnit);
                else
                    data.balancePrice = (float)(data.totalMoney / (double)data.totalAmount);
            vector.addElement(data);
        }
        m_kData = new KLineData[vector.size()];
        for(int i = 0; i < vector.size(); i++)
            m_kData[i] = (KLineData)vector.elementAt(i);

    }

    private long GetCurrentDateTime(long date, int iMin) {
        int iIndex = Common.GetTimeIndexFromTime((int)(date % 10000L), ((MenuListener) (parent)).application.m_timeRange);
        iIndex = ((iIndex + 1) / iMin + 1) * iMin - 1;
        if(iIndex >= Common.getTotalMinute(((MenuListener) (parent)).application.m_timeRange))
            iIndex = Common.getTotalMinute(((MenuListener) (parent)).application.m_timeRange) - 1;
        int iTime = Common.GetTimeFromTimeIndex(iIndex, ((MenuListener) (parent)).application.m_timeRange);
        return (date / 10000L) * 10000L + (long)iTime;
    }

    boolean KeyPressed(KeyEvent e) {
        int iKeyCode = e.getKeyCode();
        boolean bResult = false;
        switch(iKeyCode) {
        default:
            break;

        case 119: // 'w'
            ChangeCycle();
            bResult = true;
            break;

        case 38: // '&'
            bResult = ChangeRatio(true);
            if(!bResult)
                break;
            if(!ChangeRatio(true))
                parent.setMenuEnable("zoomin", false);
            else
                ChangeRatio(false);
            parent.setMenuEnable("zoomout", true);
            break;

        case 40: // '('
            bResult = ChangeRatio(false);
            if(!bResult)
                break;
            if(!ChangeRatio(false))
                parent.setMenuEnable("zoomout", false);
            else
                ChangeRatio(true);
            parent.setMenuEnable("zoomin", true);
            break;

        case 37: // '%'
            if(e.isControlDown()) {
                m_pos.m_EndPos += m_pos.m_ScreenCount;
                m_iPos -= m_pos.m_ScreenCount;
                bResult = true;
                break;
            }
            if(e.isShiftDown()) {
                if(m_pos.m_Begin > 0) {
                    m_pos.m_EndPos++;
                    m_iPos--;
                    bResult = true;
                }
            } else {
                bResult = ChangePos(true);
            }
            break;

        case 39: // '\''
            if(e.isControlDown()) {
                m_pos.m_EndPos -= m_pos.m_ScreenCount;
                m_iPos += m_pos.m_ScreenCount;
                bResult = true;
                break;
            }
            if(e.isShiftDown()) {
                if(m_pos.m_Begin < m_pos.m_MaxPos) {
                    m_pos.m_EndPos--;
                    m_iPos++;
                    bResult = true;
                }
            } else {
                bResult = ChangePos(false);
            }
            break;

        case 27: // '\033'
            if(m_iPos != -1) {
                DrawCursor(-1);
                ((MenuListener) (parent)).application.Repaint(m_rcLabel);
                m_iPos = -1;
                bResult = true;
            }
            break;

        case 36: // '$'
            ChangeIndicator(false);
            bResult = true;
            break;

        case 35: // '#'
            ChangeIndicator(true);
            bResult = true;
            break;
        }
        return bResult;
    }

    void ChangeKLineType(int iType) {
        int iPrecision = ((MenuListener) (parent)).application.getPrecision(((MenuListener) (parent)).application.strCurrentCode);
        m_indicator[0] = new MA(m_pos, iType, iPrecision);
        ((MenuListener) (parent)).application.repaint();
    }

    void ChangeCycle() {
        if(((MenuListener) (parent)).application.m_iKLineCycle >= 7)
            ((MenuListener) (parent)).application.m_iKLineCycle = 1;
        else
            ((MenuListener) (parent)).application.m_iKLineCycle++;
        m_iPos = -1;
        m_pos.m_EndPos = 0;
        m_pos.m_MaxPos = 0;
        m_pos.m_End = 0;
        m_pos.m_Begin = 0;
        parent.AskForKLine();
    }

    boolean ChangeRatio(boolean b) {
        if(b) {
            if(m_pos.m_VirtualRatio >= 60)
                return false;
            m_pos.m_VirtualRatio = m_pos.m_VirtualRatio + 1;
        } else {
            if(m_pos.m_VirtualRatio <= 2)
                return false;
            m_pos.m_VirtualRatio = m_pos.m_VirtualRatio - 1;
        }
        cache_m_VirtualRatio = m_pos.m_VirtualRatio;
        return true;
    }

    private void DrawCycle(Graphics g) {
        String str = "";
        switch(((MenuListener) (parent)).application.m_iKLineCycle) {
        case 1: // '\001'
            str = ((MenuListener) (parent)).application.getShowString("DayLine");
            break;

        case 2: // '\002'
            str = ((MenuListener) (parent)).application.getShowString("WeekLine");
            break;

        case 3: // '\003'
            str = ((MenuListener) (parent)).application.getShowString("MonthLine");
            break;

        case 4: // '\004'
            str = ((MenuListener) (parent)).application.getShowString("5MinLine");
            break;

        case 5: // '\005'
            str = ((MenuListener) (parent)).application.getShowString("15MinLine");
            break;

        case 6: // '\006'
            str = ((MenuListener) (parent)).application.getShowString("30MinLine");
            break;

        case 7: // '\007'
            str = ((MenuListener) (parent)).application.getShowString("60MinLine");
            break;
        }
        g.setFont(new Font("\u5B8B\u4F53", 0, 14));
        FontMetrics fm = g.getFontMetrics();
        int x = m_rcPane[0].x - fm.stringWidth(str) - 1;
        int y = m_rcPane[0].y + fm.getAscent();
        g.setColor(Application.rhColor.clItem);
        g.drawString(str, x, y);
    }

    private boolean ChangePos(boolean bIsLeft) {
        boolean bNeedRepaint = false;
        if(m_kData.length == 0)
            return bNeedRepaint;
        if(m_iPos == -1) {
            if(bIsLeft)
                DrawCursor(m_pos.m_End - m_pos.m_Begin);
            else
                DrawCursor(0);
            DrawLabel();
        } else
        if(bIsLeft) {
            if(m_iPos == 0) {
                if(m_pos.m_Begin > 0) {
                    m_pos.m_EndPos++;
                    bNeedRepaint = true;
                } else {
                    return false;
                }
            } else {
                DrawCursor(m_iPos - 1);
                DrawLabel();
            }
        } else
        if(m_iPos == m_pos.m_End - m_pos.m_Begin) {
            if(m_pos.m_Begin < m_pos.m_MaxPos) {
                bNeedRepaint = true;
                m_pos.m_EndPos--;
            } else {
                return false;
            }
        } else {
            DrawCursor(m_iPos + 1);
            DrawLabel();
        }
        return bNeedRepaint;
    }

    boolean MouseLeftClicked(int x, int y) {
        if(m_rcPane[0] == null)
            return false;
        if(x < m_rcPane[0].x || x > m_rcPane[0].x + m_rcPane[0].width)
            return false;
        int iNewPos = (int)((float)(x - m_rcPane[0].x) / m_pos.m_Ratio);
        if(iNewPos != m_iPos && iNewPos >= 0 && iNewPos <= m_pos.m_End - m_pos.m_Begin) {
            DrawCursor(iNewPos);
            DrawLabel();
        }
        return false;
    }

    boolean MouseDragged(int x, int y) {
        return MouseLeftClicked(x, y);
    }

    private void DrawCursor(int iNewPos) {
        Graphics g = ((MenuListener) (parent)).application.getGraphics();
        g.setColor(Application.rhColor.clBackGround);
        g.setXORMode(Application.rhColor.clCursor);
        if(m_iPos != -1) {
            int x = m_rcPane[0].x + (int)(m_pos.m_Ratio / 2.0F + (float)m_iPos * m_pos.m_Ratio);
            g.drawLine(x, m_rcPane[0].y + 1, x, (m_rcPane[2].y + m_rcPane[2].height) - 1);
            m_indicator[0].DrawCursor(g, m_iPos);
        }
        if(iNewPos != -1) {
            m_iPos = iNewPos;
            int x = m_rcPane[0].x + (int)(m_pos.m_Ratio / 2.0F + (float)m_iPos * m_pos.m_Ratio);
            g.drawLine(x, m_rcPane[0].y + 1, x, (m_rcPane[2].y + m_rcPane[2].height) - 1);
            m_indicator[0].DrawCursor(g, m_iPos);
        }
        g.setPaintMode();
    }

    private void DrawLabel() {
        if(m_kData == null || m_kData.length == 0)
            return;
        Graphics g = ((MenuListener) (parent)).application.getGraphics();
        int iIndex;
        if(m_iPos < 0)
            iIndex = m_kData.length - 1;
        else
            iIndex = m_pos.m_Begin + m_iPos;
        for(int i = 0; i < 3; i++)
            m_indicator[i].DrawTitle(g, iIndex);

        if(m_iPos < 0)
            return;
        g.clearRect(m_rcLabel.x, m_rcLabel.y, m_rcLabel.width, m_rcLabel.height);
        g.setColor(Application.rhColor.clNumber);
        g.drawRect(m_rcLabel.x, m_rcLabel.y, m_rcLabel.width, m_rcLabel.height);
        g.setFont(new Font("\u5B8B\u4F53", 0, 14));
        FontMetrics fm = g.getFontMetrics();
        int x = m_rcLabel.x + 1;
        int y = m_rcLabel.y + fm.getAscent() + 1;
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Date"), x, y);
        y += fm.getHeight();
        String str = String.valueOf(m_kData[iIndex].date);
        switch(((MenuListener) (parent)).application.m_iKLineCycle) {
        case 1: // '\001'
        case 2: // '\002'
            break;

        case 3: // '\003'
            if(str.length() >= 6)
                str = str.substring(0, 6);
            break;

        default:
            if(str.length() >= 4)
                str = str.substring(0, 8);
            break;
        }
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.setColor(Application.rhColor.clEqual);
        g.drawString(str, x, y);
        if(((MenuListener) (parent)).application.m_iKLineCycle >= 4 && ((MenuListener) (parent)).application.m_iKLineCycle <= 7) {
            str = String.valueOf(m_kData[iIndex].date);
            str = str.substring(8, 10) + ":" + str.substring(10);
            y += fm.getHeight();
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Open"), x, y);
        float fPreClose;
        if(iIndex > 0)
            fPreClose = m_kData[iIndex - 1].balancePrice;
        else
            fPreClose = m_kData[iIndex].openPrice;
        y += fm.getHeight();
        str = Common.FloatToString(m_kData[iIndex].openPrice, m_iPrecision);
        if(m_kData[iIndex].openPrice > fPreClose)
            g.setColor(Application.rhColor.clIncrease);
        else
        if(m_kData[iIndex].openPrice < fPreClose)
            g.setColor(Application.rhColor.clDecrease);
        else
            g.setColor(Application.rhColor.clEqual);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("High"), x, y);
        y += fm.getHeight();
        str = Common.FloatToString(m_kData[iIndex].highPrice, m_iPrecision);
        if(m_kData[iIndex].highPrice > fPreClose)
            g.setColor(Application.rhColor.clIncrease);
        else
        if(m_kData[iIndex].highPrice < fPreClose)
            g.setColor(Application.rhColor.clDecrease);
        else
            g.setColor(Application.rhColor.clEqual);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Low"), x, y);
        y += fm.getHeight();
        str = Common.FloatToString(m_kData[iIndex].lowPrice, m_iPrecision);
        if(m_kData[iIndex].lowPrice > fPreClose)
            g.setColor(Application.rhColor.clIncrease);
        else
        if(m_kData[iIndex].lowPrice < fPreClose)
            g.setColor(Application.rhColor.clDecrease);
        else
            g.setColor(Application.rhColor.clEqual);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Close"), x, y);
        y += fm.getHeight();
        str = Common.FloatToString(m_kData[iIndex].closePrice, m_iPrecision);
        if(m_kData[iIndex].closePrice > fPreClose)
            g.setColor(Application.rhColor.clIncrease);
        else
        if(m_kData[iIndex].closePrice < fPreClose)
            g.setColor(Application.rhColor.clDecrease);
        else
            g.setColor(Application.rhColor.clEqual);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
        boolean bShowBalance = true;
        if(((MenuListener) (parent)).application.m_iKLineCycle != 1 && (((MenuListener) (parent)).application.getProductType(m_product.sCode) == 2 || ((MenuListener) (parent)).application.getProductType(m_product.sCode) == 3))
            bShowBalance = false;
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        if(bShowBalance) {
            g.setColor(Application.rhColor.clItem);
            g.drawString(((MenuListener) (parent)).application.getShowString("Balance"), x, y);
        }
        y += fm.getHeight();
        if(bShowBalance) {
            str = Common.FloatToString(m_kData[iIndex].balancePrice, m_iPrecision);
            if(m_kData[iIndex].balancePrice > fPreClose)
                g.setColor(Application.rhColor.clIncrease);
            else
            if(m_kData[iIndex].balancePrice < fPreClose)
                g.setColor(Application.rhColor.clDecrease);
            else
                g.setColor(Application.rhColor.clEqual);
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Volume"), x, y);
        y += fm.getHeight();
        str = String.valueOf(m_kData[iIndex].totalAmount);
        g.setColor(Application.rhColor.clVolume);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Money"), x, y);
        y += fm.getHeight();
        str = String.valueOf((long)(m_kData[iIndex].totalMoney / 100D));
        g.setColor(Application.rhColor.clVolume);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(((MenuListener) (parent)).application.getShowString("Order"), x, y);
        y += fm.getHeight();
        str = String.valueOf(m_kData[iIndex].reserveCount);
        g.setColor(Application.rhColor.clReserve);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.drawString(str, x, y);
    }

    void CreateIndicator() {
        int iPrecision = ((MenuListener) (parent)).application.getPrecision(((MenuListener) (parent)).application.strCurrentCode);
        if(((MenuListener) (parent)).application.m_strIndicator.equals("ASI"))
            m_indicator[2] = new ASI(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("BIAS"))
            m_indicator[2] = new BIAS(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("BRAR"))
            m_indicator[2] = new BRAR(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("BOLL"))
            m_indicator[2] = new BOLL(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("CCI"))
            m_indicator[2] = new CCI(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("CR"))
            m_indicator[2] = new CR(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("DMA"))
            m_indicator[2] = new DMA(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("DMI"))
            m_indicator[2] = new DMI(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("EMV"))
            m_indicator[2] = new EMV(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("EXPMA"))
            m_indicator[2] = new EXPMA(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("KDJ"))
            m_indicator[2] = new KDJ(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("MACD"))
            m_indicator[2] = new MACD(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("MIKE"))
            m_indicator[2] = new MIKE(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("OBV"))
            m_indicator[2] = new OBV(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("ORDER"))
            m_indicator[2] = new Reserve(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("PSY"))
            m_indicator[2] = new PSY(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("ROC"))
            m_indicator[2] = new ROC(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("RSI"))
            m_indicator[2] = new RSI(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("SAR"))
            m_indicator[2] = new SAR(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("TRIX"))
            m_indicator[2] = new TRIX(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("VR"))
            m_indicator[2] = new VR(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("W%R"))
            m_indicator[2] = new W_R(m_pos, iPrecision);
        else
        if(((MenuListener) (parent)).application.m_strIndicator.equals("WVAD"))
            m_indicator[2] = new WVAD(m_pos, iPrecision);
    }

    void ChangeIndicator(boolean bDown) {
        int iCur = -1;
        for(int i = 0; i < IndicatorBase.INDICATOR_NAME.length; i++) {
            if(!IndicatorBase.INDICATOR_NAME[i][0].equals(((MenuListener) (parent)).application.m_strIndicator))
                continue;
            iCur = i;
            break;
        }

        if(bDown)
            iCur = (iCur + 1) % IndicatorBase.INDICATOR_NAME.length;
        else
        if(iCur < 1)
            iCur = IndicatorBase.INDICATOR_NAME.length - 1;
        else
            iCur = (iCur - 1) % IndicatorBase.INDICATOR_NAME.length;
        ((MenuListener) (parent)).application.m_strIndicator = IndicatorBase.INDICATOR_NAME[iCur][0];
        CreateIndicator();
    }

}
