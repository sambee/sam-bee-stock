// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Draw_MinLine.java

package sam.bee.stock.gui;


import java.awt.*;
import java.util.Hashtable;
import java.util.Vector;

import sam.bee.stock.vo.MinDataVO;
import sam.bee.stock.vo.TradeTimeVO;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            HQApplet, ProductData, RHColor, Common, 
//            CodeTable

public class Draw_MinLine {

    Application m_applet;
    Graphics m_graphics;
    boolean bLarge;
    private int m_iTotalMinNum;
    private int m_iMinLineNum;
    private int m_iPos;
    private int iNum;
    int m_iPrecision;
    float m_maxPrice;
    float m_minPrice;
    long m_maxVolume;
    int m_maxReserveCount;
    int m_minReserveCount;
    private ProductData m_product;
    private int iHeight;
    private int iWidth;
    Rectangle m_rcPrice;
    Rectangle m_rcVolume;
    Rectangle m_rcLabel;
    private int m_iProductType;

    public Draw_MinLine(Application applet, boolean _bLarge) {
        m_iTotalMinNum = 0;
        m_iMinLineNum = 0;
        m_iPos = -1;
        iNum = 0;
        m_maxVolume = 0L;
        m_maxReserveCount = 0;
        m_minReserveCount = 0;
        m_applet = applet;
        bLarge = _bLarge;
        if(m_applet.m_timeRange != null) {
            m_iTotalMinNum = Common.getTotalMinute(m_applet.m_timeRange);
            m_iMinLineNum = m_iTotalMinNum * (60 / applet.m_iMinLineInterval);
        }
    }

    void Paint(Graphics g, Rectangle rc, ProductData product) {
        m_graphics = g;
        m_product = product;
        if(product != null) {
            m_iProductType = m_applet.getProductType(product.sCode);
            GetMaxMinPrice();
            if(product.vMinLine != null) {
                m_maxReserveCount = 0;
                m_minReserveCount = 0;
                for(int i = 0; i < product.vMinLine.size(); i++) {
                    MinDataVO minLine = (MinDataVO)product.vMinLine.elementAt(i);
                    float fCurVol;
                    if(i == 0)
                        fCurVol = minLine.totalAmount;
                    else
                        fCurVol = minLine.totalAmount - ((MinDataVO)m_product.vMinLine.elementAt(i - 1)).totalAmount;
                    if((float)m_maxVolume < fCurVol)
                        m_maxVolume = (long)fCurVol;
                    if(m_maxReserveCount < minLine.reserveCount)
                        m_maxReserveCount = minLine.reserveCount;
                    if(m_minReserveCount == 0 || m_minReserveCount > minLine.reserveCount)
                        m_minReserveCount = minLine.reserveCount;
                }

            }
        }
        DrawGrid(g, rc);
        DrawTrace(g);
        if(bLarge)
            m_applet.EndPaint();
        if(m_iPos >= 0) {
            DrawCursor(-1);
            DrawLabel();
        }
    }

    private void DrawGrid(Graphics g, Rectangle rc) {
        if(bLarge)
            g.setFont(new Font("\u5B8B\u4F53", 0, 14));
        else
            g.setFont(new Font("\u5B8B\u4F53", 0, 12));
        FontMetrics fm = g.getFontMetrics();
        iHeight = fm.getHeight();
        iWidth = fm.charWidth('A');
        int w1;
        int w2;
        int h1;
        int h2;
        if(bLarge) {
            w1 = iWidth * 9 - 1;
            w2 = rc.width - w1 - iWidth * 6;
            h1 = (rc.height * 7) / 10 - iHeight / 2;
            h2 = (rc.height * 3) / 10 - (iHeight * 5) / 2;
        } else {
            w1 = iWidth * 7 - 1;
            w2 = rc.width - w1 - iWidth * 1;
            h1 = (rc.height * 6) / 10 - iHeight / 2;
            h2 = (rc.height * 3) / 10 - iHeight / 2;
        }
        m_rcPrice = new Rectangle(rc.x + w1, rc.y + iHeight, w2, h1);
        m_rcVolume = new Rectangle(rc.x + w1, m_rcPrice.y + m_rcPrice.height + iHeight, w2, h2);
        m_rcLabel = new Rectangle(rc.x + 1, rc.y + iHeight * 2, w1, fm.getHeight() * 12);
        if(m_rcLabel.y + m_rcLabel.height > rc.y + rc.height)
            m_rcLabel.height = (rc.y + rc.height) - m_rcLabel.y;
        int iPriceGridNum = ((m_rcPrice.height / iHeight) * 2) / 3;
        if(iPriceGridNum % 2 == 1)
            iPriceGridNum++;
        if(iPriceGridNum <= 0)
            return;
        g.setColor(Application.rhColor.clGrid);
        if(!bLarge)
            g.drawLine(rc.x, rc.y, rc.x, rc.y + rc.height);
        for(int i = 0; i <= iPriceGridNum; i++)
            g.drawLine(m_rcPrice.x, m_rcPrice.y + (m_rcPrice.height * i) / iPriceGridNum, m_rcPrice.x + m_rcPrice.width, m_rcPrice.y + (m_rcPrice.height * i) / iPriceGridNum);

        int iVolGridNum = ((m_rcVolume.height / iHeight) * 2) / 3;
        if(iVolGridNum <= 0)
            iVolGridNum = 1;
        for(int i = 0; i <= iVolGridNum; i++)
            g.drawLine(m_rcVolume.x, m_rcVolume.y + (m_rcVolume.height * i) / iVolGridNum, m_rcVolume.x + m_rcVolume.width, m_rcVolume.y + (m_rcVolume.height * i) / iVolGridNum);

        if(m_iTotalMinNum == 0 && m_applet.m_timeRange != null)
            m_iTotalMinNum = Common.getTotalMinute(m_applet.m_timeRange);
        int iIndex = 0;
        int iOldX = 0;
        int iEndX = GetXFromTimeIndex(m_iTotalMinNum);
        if(m_applet.m_timeRange == null)
            return;
        int iRangeIndex[] = new int[m_applet.m_timeRange.length + 1];
        iRangeIndex[0] = 0;
        for(int i = 0; i < m_applet.m_timeRange.length; i++)
            iRangeIndex[i + 1] = Common.GetTimeIndexFromTime(m_applet.m_timeRange[i].endTime, m_applet.m_timeRange) + 1;

        String strLastTime = "09:00";
        while(iIndex <= m_iTotalMinNum)  {
            if(iIndex > 0 && m_iTotalMinNum - iIndex < 10)
                iIndex = m_iTotalMinNum;
            int x = GetXFromTimeIndex(iIndex <= 0 ? iIndex : iIndex - 1);
            boolean bDrawed = false;
            g.setColor(Application.rhColor.clGrid);
            String strNextStartTime = "";
            boolean isRangeTime = false;
            for(int i = 0; i < iRangeIndex.length; i++)
                if(iIndex == iRangeIndex[i]) {
                    isRangeTime = true;
                    if(i > 0 && i < m_applet.m_timeRange.length) {
                    	  //for(strNextStartTime =m_applet.m_timeRange[i].beginTime; strNextStartTime.length() < 4; strNextStartTime = "0" + strNextStartTime);
                        for(strNextStartTime =String.valueOf( m_applet.m_timeRange[i].beginTime); strNextStartTime.length() < 4; strNextStartTime = "0" + strNextStartTime);
                        strNextStartTime = strNextStartTime.substring(0, 2) + ":" + strNextStartTime.substring(2, 4);
                    }
                }

            if(isRangeTime) {
                if(iIndex == 0)
                    x--;
                else
                if(iIndex == m_iTotalMinNum)
                    x++;
                g.drawLine(x, m_rcPrice.y, x, m_rcPrice.y + m_rcPrice.height);
                g.drawLine(x, m_rcVolume.y, x, m_rcVolume.y + m_rcVolume.height);
                bDrawed = true;
            } else
            if(x - iOldX > (int)((double)fm.stringWidth("09:30") * 1.2D) && iEndX - x > fm.stringWidth("09:30")) {
                Common.DrawDotLine(g, x, m_rcPrice.y, x, m_rcPrice.y + m_rcPrice.height);
                Common.DrawDotLine(g, x, m_rcVolume.y, x, m_rcVolume.y + m_rcVolume.height);
                if(x - iOldX >= (int)((float)fm.stringWidth(strLastTime) * 0.8F))
                    bDrawed = true;
            }
            if(bDrawed && bLarge) {
                g.setColor(Application.rhColor.clNumber);
                int iTime;
                if(iIndex == 0)
                    iTime = m_applet.m_timeRange[0].beginTime;
                else
                if(iIndex == m_iTotalMinNum)
                    iTime = m_applet.m_timeRange[m_applet.m_timeRange.length - 1].endTime;
                else
                    iTime = Common.GetTimeFromTimeIndex(iIndex - 1, m_applet.m_timeRange);
                String str;
                for(str = String.valueOf(iTime); str.length() < 4; str = "0" + str);
                str = str.substring(0, 2) + ":" + str.substring(2, 4);
                if(strNextStartTime.length() > 0 && !str.equals(strNextStartTime))
                    str = str + "/" + strNextStartTime;
                int y = m_rcVolume.y + m_rcVolume.height + fm.getAscent();
                if(x - iOldX < (int)((float)fm.stringWidth(str) * 0.8F))
                    g.clearRect(iOldX - fm.stringWidth(strLastTime) / 2, m_rcVolume.y + m_rcVolume.height + 1, fm.stringWidth(strLastTime), fm.getHeight());
                g.drawString(str, x - fm.stringWidth(str) / 2, y);
                iOldX = x;
                strLastTime = str;
            } else {
                strLastTime = "";
            }
            if(iIndex >= m_iTotalMinNum)
                break;
            if((iIndex += 30) > m_iTotalMinNum)
                iIndex = m_iTotalMinNum;
        }
        if(m_product == null || m_product.realData == null)
            return;
        m_iPrecision = m_applet.getPrecision(m_product.sCode);
        float fDiff = m_maxPrice - m_minPrice;
        float fUnit = 1.0F;
        for(int i = 0; i < m_iPrecision; i++) {
            fDiff *= 10F;
            fUnit /= 10F;
        }

        int iDiff = (int)((fDiff + 1.0F) - fUnit);
        if(iDiff % iPriceGridNum > 0)
            iDiff = (iDiff / iPriceGridNum + 1) * iPriceGridNum;
        fDiff = iDiff;
        for(int i = 0; i < m_iPrecision; i++)
            fDiff /= 10F;

        m_maxPrice = m_product.realData.yesterBalancePrice + fDiff / 2.0F;
        m_minPrice = m_product.realData.yesterBalancePrice - fDiff / 2.0F;
        float preclose = m_product.realData.yesterBalancePrice;
        for(int i = 0; i <= iPriceGridNum; i++) {
            float price = m_maxPrice - ((m_maxPrice - m_minPrice) * (float)i) / (float)iPriceGridNum;
            if(price > preclose)
                g.setColor(Application.rhColor.clIncrease);
            else
            if(preclose > price) {
                g.setColor(Application.rhColor.clDecrease);
            } else {
                if(m_maxPrice > m_minPrice) {
                    g.setColor(Application.rhColor.clGrid);
                    g.drawLine(m_rcPrice.x, m_rcPrice.y + (m_rcPrice.height * i) / iPriceGridNum + 1, m_rcPrice.x + m_rcPrice.width, m_rcPrice.y + (m_rcPrice.height * i) / iPriceGridNum + 1);
                }
                g.setColor(Application.rhColor.clEqual);
            }
            String str = Common.FloatToString(price, m_iPrecision);
            int x = m_rcPrice.x - fm.stringWidth(str) - 1;
            int y = ((m_rcPrice.y + (m_rcPrice.height * i) / iPriceGridNum) - fm.getAscent()) + fm.getHeight();
            g.drawString(str, x, y);
            if(bLarge) {
                float percent;
                if(0.0F != preclose)
                    percent = ((price - preclose) * 100F) / preclose;
                else
                    percent = 0.0F;
                if(percent < 0.0F)
                    percent = -percent;
                str = Common.FloatToString(percent, 2);
                if(percent >= 100F)
                    str = str.substring(0, str.length() - 1);
                str = str + "%";
                x = m_rcPrice.x + m_rcPrice.width + 2;
                g.drawString(str, x, y);
            }
        }

        if(m_maxReserveCount == m_minReserveCount) {
            if(m_minReserveCount > 0) {
                m_maxReserveCount += iVolGridNum - 1;
                m_minReserveCount--;
            }
        } else {
            int change = (int)((double)(m_maxReserveCount - m_minReserveCount) * 0.10000000000000001D);
            if(change <= 0)
                change = 1;
            m_maxReserveCount += change;
            m_minReserveCount -= change;
        }
        g.setColor(Application.rhColor.clVolume);
        for(int i = 0; i < iVolGridNum; i++) {
            long volume = m_maxVolume - (m_maxVolume * (long)i) / (long)iVolGridNum;
            String str = String.valueOf(volume);
            int x = m_rcVolume.x - fm.stringWidth(str);
            int y = ((m_rcVolume.y + (m_rcVolume.height * i) / iVolGridNum) - fm.getAscent()) + fm.getHeight();
            g.drawString(str, x, y);
        }

        if(bLarge) {
            g.setColor(Application.rhColor.clReserve);
            for(int i = 0; i <= iVolGridNum; i++) {
                long reserveCount = m_maxReserveCount - ((m_maxReserveCount - m_minReserveCount) * i) / iVolGridNum;
                String str = String.valueOf(reserveCount);
                int x = m_rcVolume.x + m_rcVolume.width + 1;
                int y = ((m_rcVolume.y + (m_rcVolume.height * i) / iVolGridNum) - fm.getAscent()) + fm.getHeight();
                g.drawString(str, x, y);
            }

        }
    }

    void GetMaxMinPrice() {
        if(m_product.realData == null)
            return;
        if(m_product.realData != null && m_product.realData.highPrice < m_product.realData.yesterBalancePrice)
            m_maxPrice = m_product.realData.yesterBalancePrice;
        else
            m_maxPrice = m_product.realData.highPrice;
        if(m_maxPrice < 0.001F)
            return;
        if(m_product.realData.lowPrice > m_product.realData.yesterBalancePrice)
            m_minPrice = m_product.realData.yesterBalancePrice;
        else
            m_minPrice = m_product.realData.lowPrice;
        float scale;
        if(m_applet.getPrecision(m_product.sCode) == 3)
            scale = 0.0055F;
        else
            scale = m_product.realData.yesterBalancePrice / 1000F;
        if(m_product.realData.highPrice == 0.0F && m_product.realData.lowPrice == 0.0F) {
            m_minPrice = 0.0F;
            m_maxPrice = 2.0F * m_product.realData.yesterBalancePrice;
        } else {
            float higher = 0.0F;
            float lowwer = 0.0F;
            float price = m_product.realData.yesterBalancePrice;
            if(m_product.realData.highPrice - price >= scale) {
                m_maxPrice = m_product.realData.highPrice;
                higher = m_product.realData.highPrice - price;
            } else {
                m_maxPrice = price + scale;
            }
            if(price - m_product.realData.lowPrice >= scale) {
                m_minPrice = m_product.realData.lowPrice;
                lowwer = price - m_product.realData.lowPrice;
            } else {
                m_minPrice = price - scale;
            }
            if(higher > lowwer)
                m_minPrice = price - higher;
            if(higher < lowwer)
                m_maxPrice = price + lowwer;
        }
    }

    void GetMaxMinPriceIndex() {
        m_maxPrice = 0.0F;
        m_minPrice = 0.0F;
        if(m_product == null || m_product.realData == null || m_product.realData.closePrice < 0.001F)
            return;
        float high = m_product.realData.highPrice;
        float low = m_product.realData.lowPrice;
        float f = 0.0F;
        do {
            f += 0.01F;
            m_maxPrice = m_product.realData.closePrice * (1.0F + f);
            m_minPrice = m_product.realData.closePrice * (1.0F - f);
        } while(m_product.realData.highPrice > 0.0F && m_maxPrice < high || m_product.realData.lowPrice > 0.0F && m_minPrice > low);
    }

    private boolean inTrade(int minLineIndex, int tradeSecNo[]) {
        if(m_applet.m_timeRange == null || m_applet.m_timeRange.length == 0)
            return false;
        int hhmmss = Common.GetTimeFromMinLineIndex(minLineIndex, m_applet.m_timeRange, m_applet.m_iMinLineInterval);
        int hhmm = hhmmss / 100;
        for(int i = 0; i < tradeSecNo.length; i++) {
            if(tradeSecNo[i] > m_applet.m_timeRange.length)
                return true;
            for(int j = 0; j < m_applet.m_timeRange.length; j++)
                if(m_applet.m_timeRange[j].orderID == tradeSecNo[i] && hhmm >= m_applet.m_timeRange[j].beginTime && hhmm <= m_applet.m_timeRange[j].endTime)
                    return true;

        }

        return false;
    }

    private void DrawTrace(Graphics g) {
        if(m_product == null || m_product.realData == null || m_product.vMinLine == null || m_product.realData.yesterBalancePrice < 0.01F)
            return;
        int oldX = m_rcPrice.x;
        int oldAverY;
        int oldPriceY = oldAverY = GetYFromPrice(m_product.realData.yesterBalancePrice);
        iNum = m_product.vMinLine.size();
        MinDataVO data;
        if(iNum > 0) {
            data = (MinDataVO)m_product.vMinLine.elementAt(iNum - 1);
        } else {
            data = new MinDataVO();
            data.averPrice = m_product.realData.yesterBalancePrice;
            data.curPrice = m_product.realData.yesterBalancePrice;
            data.reserveCount = 0;
            data.totalAmount = 0L;
            data.totalMoney = 0.0D;
        }
        int iCurIndex = Common.GetMinLineIndexFromTime(m_applet.m_iTime, m_applet.m_timeRange, m_applet.m_iMinLineInterval);
        for(int i = iNum; i < iCurIndex + 1; i++) {
            MinDataVO newData = new MinDataVO();
            newData.averPrice = data.averPrice;
            newData.curPrice = data.curPrice;
            newData.reserveCount = data.reserveCount;
            newData.totalAmount = data.totalAmount;
            newData.totalMoney = data.totalMoney;
            m_product.vMinLine.addElement(newData);
        }

        iNum = m_product.vMinLine.size();
        CodeTable s = (CodeTable)m_applet.m_ProductByHttp.get(m_product.sCode);
        boolean bIsIndex = m_applet.isIndex(m_product.sCode);
        int oldReserveY = 0;
        for(int i = 0; i < iNum; i++) {
            data = (MinDataVO)m_product.vMinLine.elementAt(i);
            boolean bInTrade = bIsIndex || s == null || inTrade(i, s.tradeSecNo);
            if(data.curPrice == 0.0F)
                data.curPrice = data.averPrice = m_product.realData.yesterBalancePrice;
            int newX = GetXFromMinLineIndex(i);
            int newPriceY;
            if(data.curPrice < m_product.realData.lowPrice || data.curPrice > m_product.realData.highPrice)
                newPriceY = oldPriceY;
            else
                newPriceY = GetYFromPrice(data.curPrice);
            float fAver = 0.0F;
            fAver = data.averPrice;
            int newAverY;
            if(fAver < m_product.realData.lowPrice || fAver > m_product.realData.highPrice)
                newAverY = oldAverY;
            else
                newAverY = GetYFromPrice(fAver);
            float fCurVol;
            if(i == 0)
                fCurVol = data.totalAmount;
            else
                fCurVol = data.totalAmount - ((MinDataVO)m_product.vMinLine.elementAt(i - 1)).totalAmount;
            if(2 != m_iProductType && 3 != m_iProductType) {
                g.setColor(Application.rhColor.clVolume);
                if(bInTrade)
                    g.drawLine(oldX, oldAverY, newX, newAverY);
            }
            if(fCurVol > 0.0F && bInTrade) {
                int volumeY = GetYFromVolume((long)fCurVol);
                g.setColor(Application.rhColor.clVolume);
                g.drawLine(newX, (m_rcVolume.y + m_rcVolume.height) - 1, newX, volumeY);
            }
            int newReserveY = GetYFromReserve(data.reserveCount);
            if(bInTrade) {
                g.setColor(Application.rhColor.clMinLine);
                g.drawLine(oldX, oldPriceY, newX, newPriceY);
                if(data.reserveCount > 0 && oldReserveY > 0) {
                    g.setColor(Application.rhColor.clReserve);
                    g.drawLine(oldX, oldReserveY, newX, newReserveY);
                }
            }
            oldX = newX;
            oldPriceY = newPriceY;
            oldAverY = newAverY;
            oldReserveY = newReserveY;
        }

    }

    private int GetYFromPrice(float price) {
        if(m_maxPrice == m_minPrice) {
            return (m_rcPrice.y + m_rcPrice.height) - 1;
        } else {
            int tmp = (int)(((price - m_minPrice) * (float)m_rcPrice.height) / (m_maxPrice - m_minPrice));
            return (m_rcPrice.y + m_rcPrice.height) - tmp;
        }
    }

    private int GetYFromVolume(long volume) {
        if(0L >= m_maxVolume) {
            return (m_rcVolume.y + m_rcVolume.height) - 1;
        } else {
            int tmp = (int)(((double)volume * (double)(m_rcVolume.height - 1)) / (double)m_maxVolume);
            return (m_rcVolume.y + m_rcVolume.height) - 1 - tmp;
        }
    }

    private int GetYFromReserve(int reserveCount) {
        if(m_maxReserveCount <= 0 || reserveCount == 0) {
            return (m_rcVolume.y + m_rcVolume.height) - 1;
        } else {
            int tmp = (int)(((double)(reserveCount - m_minReserveCount) * (double)(m_rcVolume.height - 1)) / (double)(m_maxReserveCount - m_minReserveCount));
            return (m_rcVolume.y + m_rcVolume.height) - 1 - tmp;
        }
    }

    private int GetXFromTimeIndex(int index) {
        if(m_iTotalMinNum == 0)
            return 0;
        if(index >= m_iTotalMinNum)
            index = m_iTotalMinNum - 1;
        return m_rcPrice.x + 1 + (index * (m_rcPrice.width - 2)) / (m_iTotalMinNum - 1);
    }

    private int GetXFromMinLineIndex(int index) {
        if(m_iMinLineNum == 0)
            return 0;
        if(index >= m_iMinLineNum)
            index = m_iMinLineNum - 1;
        return m_rcPrice.x + 1 + (index * (m_rcPrice.width - 2)) / (m_iMinLineNum - 1);
    }

    private int GetTimeIndexFromX(int X) {
        if(m_iTotalMinNum == 0)
            return 0;
        else
            return ((X - m_rcPrice.x - 1) * (m_iTotalMinNum - 1)) / (m_rcPrice.width - 2);
    }

    private int GetMinLineIndexFromX(int X) {
        if(m_iMinLineNum == 0)
            return 0;
        else
            return ((X - m_rcPrice.x - 1) * (m_iMinLineNum - 1)) / (m_rcPrice.width - 2);
    }

    boolean KeyPressed(int iKeyCode) {
        boolean bResult = false;
        switch(iKeyCode) {
        default:
            break;

        case 37: // '%'
            if(m_iPos > 0) {
                DrawCursor(m_iPos - 1);
                DrawLabel();
                break;
            }
            if(m_iPos == -1 && iNum > 0) {
                DrawCursor(iNum - 1);
                DrawLabel();
            }
            break;

        case 39: // '\''
            if(m_iPos < m_iMinLineNum - 1 && m_iPos < iNum - 1) {
                DrawCursor(m_iPos + 1);
                DrawLabel();
            }
            break;

        case 27: // '\033'
            if(m_iPos != -1) {
                DrawCursor(-1);
                m_applet.Repaint(m_rcLabel);
                m_iPos = -1;
                bResult = true;
            }
            break;
        }
        return bResult;
    }

    boolean MouseLeftClicked(int x, int y) {
        if(m_rcPrice == null || m_rcVolume == null)
            return false;
        if(y < m_rcPrice.y || y > m_rcVolume.y + m_rcVolume.height)
            return false;
        int iNewPos = GetMinLineIndexFromX(x);
        if(iNewPos < 0 || iNewPos > m_iMinLineNum - 1)
            return false;
        if(iNewPos > iNum - 1) {
            iNewPos = iNum - 1;
            if(m_iPos != -1)
                return false;
        }
        DrawCursor(iNewPos);
        DrawLabel();
        return false;
    }

    boolean MouseDragged(int x, int y) {
        return MouseLeftClicked(x, y);
    }

    private void DrawCursor(int iNewPos) {
        Graphics g = m_applet.getGraphics();
        g.setColor(Application.rhColor.clBackGround);
        g.setXORMode(Application.rhColor.clCursor);
        if(m_iPos >= 0 && m_iPos <= m_iMinLineNum - 1) {
            int x = GetXFromMinLineIndex(m_iPos);
            g.drawLine(x, m_rcPrice.y + 1, x, (m_rcVolume.y + m_rcVolume.height) - 1);
        }
        if(iNewPos >= 0 && iNewPos <= m_iMinLineNum - 1) {
            m_iPos = iNewPos;
            int x = GetXFromMinLineIndex(m_iPos);
            g.drawLine(x, m_rcPrice.y + 1, x, (m_rcVolume.y + m_rcVolume.height) - 1);
        }
        g.setPaintMode();
    }

    private void DrawLabel() {
        if(m_iPos < 0 || m_iPos > m_iMinLineNum - 1)
            return;
        Graphics g = m_applet.getGraphics();
        g.clearRect(m_rcLabel.x, m_rcLabel.y, m_rcLabel.width, m_rcLabel.height);
        g.setColor(Application.rhColor.clNumber);
        g.drawRect(m_rcLabel.x, m_rcLabel.y, m_rcLabel.width, m_rcLabel.height);
        g.setFont(new Font("\u5B8B\u4F53", 0, 14));
        FontMetrics fm = m_graphics.getFontMetrics();
        int x = m_rcLabel.x + 1;
        int y = m_rcLabel.y + fm.getAscent() + 1;
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_applet.getShowString("Time"), x, y);
        y += fm.getHeight();
        String str = String.valueOf(Common.GetTimeFromMinLineIndex(m_iPos, m_applet.m_timeRange, m_applet.m_iMinLineInterval));
        if(str.length() != 6)
            str = "0" + str;
        str = str.substring(0, 2) + ":" + str.substring(2, 4) + ":" + str.substring(4);
        if(m_applet.m_iMinLineInterval == 60)
            str = str.substring(0, 5);
        x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
        g.setColor(Application.rhColor.clEqual);
        g.drawString(str, x, y);
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_applet.getShowString("Price"), x, y);
        y += fm.getHeight();
        if(m_product.vMinLine == null)
            return;
        MinDataVO data = (MinDataVO)m_product.vMinLine.elementAt(m_iPos);
        if(data != null) {
            str = Common.FloatToString(data.curPrice, m_iPrecision);
            if(data.curPrice > m_product.realData.yesterBalancePrice)
                g.setColor(Application.rhColor.clIncrease);
            else
            if(data.curPrice < m_product.realData.yesterBalancePrice)
                g.setColor(Application.rhColor.clDecrease);
            else
                g.setColor(Application.rhColor.clEqual);
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_applet.getShowString("ChangeValue"), x, y);
        y += fm.getHeight();
        if(data != null) {
            str = Common.FloatToString(data.curPrice - m_product.realData.yesterBalancePrice, m_iPrecision);
            if(data.curPrice > m_product.realData.yesterBalancePrice) {
                g.setColor(Application.rhColor.clIncrease);
                str = "+" + str;
            } else
            if(data.curPrice < m_product.realData.yesterBalancePrice)
                g.setColor(Application.rhColor.clDecrease);
            else
                g.setColor(Application.rhColor.clEqual);
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_applet.getShowString("Balance"), x, y);
        y += fm.getHeight();
        if(data != null) {
            str = Common.FloatToString(data.averPrice, m_iPrecision);
            if(data.averPrice > m_product.realData.yesterBalancePrice)
                g.setColor(Application.rhColor.clIncrease);
            else
            if(data.averPrice < m_product.realData.yesterBalancePrice)
                g.setColor(Application.rhColor.clDecrease);
            else
                g.setColor(Application.rhColor.clEqual);
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_applet.getShowString("Volume"), x, y);
        y += fm.getHeight();
        if(data != null) {
            float fCurVol;
            if(m_iPos == 0)
                fCurVol = data.totalAmount;
            else
                fCurVol = data.totalAmount - ((MinDataVO)m_product.vMinLine.elementAt(m_iPos - 1)).totalAmount;
            str = String.valueOf((int)fCurVol);
            g.setColor(Application.rhColor.clVolume);
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
        x = m_rcLabel.x + 1;
        y += fm.getHeight();
        g.setColor(Application.rhColor.clItem);
        g.drawString(m_applet.getShowString("Order"), x, y);
        y += fm.getHeight();
        if(data != null) {
            str = String.valueOf(data.reserveCount);
            g.setColor(Application.rhColor.clReserve);
            x = (m_rcLabel.x + m_rcLabel.width) - fm.stringWidth(str) - 1;
            g.drawString(str, x, y);
        }
    }
}
