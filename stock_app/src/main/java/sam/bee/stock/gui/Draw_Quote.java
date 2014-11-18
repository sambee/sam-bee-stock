// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Draw_Quote.java

package sam.bee.stock.gui;

import java.awt.*;
import java.util.Hashtable;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            HQApplet, RHColor, CodeTable, ProductData, 
//            Common

public class Draw_Quote {

    public Draw_Quote() {
    }

    static void Paint(Graphics g, Rectangle rc, ProductData product, String code, int iShowBuySellNum, Application applet) {
        g.setFont(new Font("楷体_GB2312", 1, 26));
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Application.rhColor.clProductName);
        int iAsecent = fm.getAscent();
        int iDescent = fm.getHeight() - iAsecent;
        int x = rc.x;
        int y = rc.y + iAsecent;
        CodeTable s = null;
        if(applet.m_ProductByHttp != null && applet.m_ProductByHttp.get(code) != null)
            s = (CodeTable)applet.m_ProductByHttp.get(code);
        String str;
        if(s != null)
            str = s.sName;
        else
            str = "————";
        if(str.equals(code))
            code = "";
        FontMetrics oldFm = fm;
        int fontSize = 26;
        for(; fm.stringWidth(str + code) > rc.width; fm = g.getFontMetrics()) {
            fontSize--;
            g.setFont(new Font("楷体_GB2312", 1, fontSize));
        }

        if(y + iDescent < rc.y + rc.height)
            g.drawString(str, x, y);
        g.setColor(Application.rhColor.clItem);
        str = code;
        x = (rc.x + rc.width) - fm.stringWidth(str);
        if(y + iDescent < rc.y + rc.height)
            g.drawString(str, x, y);
        y += iDescent;
        fm = oldFm;
        g.setColor(Application.rhColor.clGrid);
        if(fm.getHeight() < rc.height)
            g.drawRect(rc.x, rc.y + fm.getHeight(), rc.width - 1, rc.height - fm.getHeight());
        g.setColor(Application.rhColor.clItem);
        g.setFont(new Font("宋体", 0, 16));
        fm = g.getFontMetrics();
        iAsecent = fm.getAscent();
        iDescent = fm.getHeight() - iAsecent;
        x = rc.x + 1;
        y += iAsecent;
        int y1 = y;
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("ConsignRate"), x, y);
            g.drawString(applet.getShowString("ConsignDiff"), x + rc.width / 2, y);
            g.setColor(Application.rhColor.clGrid);
            y += iDescent;
            g.drawLine(rc.x, y, rc.x + rc.width, y);
        }
        g.setColor(Application.rhColor.clItem);
        y += iAsecent;
        for(int i = iShowBuySellNum - 1; i >= 0; i--) {
            if(y + iDescent < rc.y + rc.height)
                g.drawString(applet.getShowString("Sell") + applet.getShowString((new StringBuffer(String.valueOf(i + 1))).toString()), x, y);
            y += fm.getHeight();
        }

        y -= fm.getHeight();
        g.setColor(Application.rhColor.clGrid);
        y += iDescent;
        if(y < rc.y + rc.height)
            g.drawLine(rc.x, y, rc.x + rc.width, y);
        g.setColor(Application.rhColor.clItem);
        x = rc.x + 1;
        y += iAsecent;
        for(int i = 0; i < iShowBuySellNum; i++) {
            if(y + iDescent < rc.y + rc.height)
                g.drawString(applet.getShowString("Buy") + applet.getShowString((new StringBuffer(String.valueOf(i + 1))).toString()), x, y);
            y += fm.getHeight();
        }

        y -= fm.getHeight();
        g.setColor(Application.rhColor.clGrid);
        y += iDescent;
        if(y < rc.y + rc.height)
            g.drawLine(rc.x, y, rc.x + rc.width, y);
        g.setColor(Application.rhColor.clItem);
        x = rc.x + 1;
        y += iAsecent;
        if(y + iDescent < rc.y + rc.height)
            g.drawString(applet.getShowString("Newly"), x, y);
        y += fm.getHeight();
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("ChangeValue"), x, y);
            g.drawString(applet.getShowString("Open"), x + rc.width / 2, y);
        }
        y += fm.getHeight();
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("ChangeRate"), x, y);
            g.drawString(applet.getShowString("High"), x + rc.width / 2, y);
        }
        y += fm.getHeight();
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("CurVol"), x, y);
            g.drawString(applet.getShowString("Low"), x + rc.width / 2, y);
        }
        y += fm.getHeight();
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("TotalVolume"), x, y);
            g.drawString(applet.getShowString("VolRate"), x + rc.width / 2, y);
        }
        y += fm.getHeight();
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("Balance"), x, y);
            g.drawString(applet.getShowString("PreBalance1"), x + rc.width / 2, y);
        }
        y += fm.getHeight();
        if(y + iDescent < rc.y + rc.height) {
            g.drawString(applet.getShowString("Order1"), x, y);
            g.drawString(applet.getShowString("OrderChange"), x + rc.width / 2, y);
        }
        g.setColor(Application.rhColor.clGrid);
        y += iDescent;
        if(y < rc.y + rc.height)
            g.drawLine(rc.x, y, rc.x + rc.width, y);
        g.setColor(Application.rhColor.clItem);
        y += iAsecent;
        if(y + iDescent < rc.y + rc.height + 1) {
            g.drawString(applet.getShowString("AskVolume"), x, y);
            g.drawString(applet.getShowString("BidVolume"), x + rc.width / 2, y);
        }
        if(product == null)
            return;
        int iStockType = applet.getProductType(product.sCode);
        int iPrecision = applet.getPrecision(product.sCode);
        y = y1;
        if(y + iDescent > rc.y + rc.height)
            return;
        float fBuyP[] = new float[5];
        float fBuyV[] = new float[5];
        float fSellP[] = new float[5];
        float fSellV[] = new float[5];
        String str1;
        if(product != null && product.realData != null) {
            fBuyP[0] = product.realData.buyPrice[0];
            fBuyP[1] = product.realData.buyPrice[1];
            fBuyP[2] = product.realData.buyPrice[2];
            fBuyP[3] = product.realData.buyPrice[3];
            fBuyP[4] = product.realData.buyPrice[4];
            fBuyV[0] = product.realData.buyAmount[0];
            fBuyV[1] = product.realData.buyAmount[1];
            fBuyV[2] = product.realData.buyAmount[2];
            fBuyV[3] = product.realData.buyAmount[3];
            fBuyV[4] = product.realData.buyAmount[4];
            fSellP[0] = product.realData.sellPrice[0];
            fSellP[1] = product.realData.sellPrice[1];
            fSellP[2] = product.realData.sellPrice[2];
            fSellP[3] = product.realData.sellPrice[3];
            fSellP[4] = product.realData.sellPrice[4];
            fSellV[0] = product.realData.sellAmount[0];
            fSellV[1] = product.realData.sellAmount[1];
            fSellV[2] = product.realData.sellAmount[2];
            fSellV[3] = product.realData.sellAmount[3];
            fSellV[4] = product.realData.sellAmount[4];
            float fSellVol = fSellV[0] + fSellV[1] + fSellV[2];
            float fBuyVol = fBuyV[0] + fBuyV[1] + fBuyV[2];
            float fDiff = fBuyVol - fSellVol;
            if((double)(fBuyVol + fSellVol) < 0.001D)
                str = "—";
            else
                str = Common.FloatToString((fDiff / (fBuyVol + fSellVol)) * 100F, 2) + "%";
            if(fDiff > 0.0F) {
                str = "+" + str;
                str1 = String.valueOf((int)fDiff);
                g.setColor(Application.rhColor.clIncrease);
            } else
            if(fDiff < 0.0F) {
                str1 = String.valueOf(-(int)fDiff);
                g.setColor(Application.rhColor.clDecrease);
            } else {
                str1 = "0";
                g.setColor(Application.rhColor.clEqual);
            }
        } else {
            str = "—";
            str1 = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        x = (rc.x + rc.width) - fm.stringWidth(str1);
        g.drawString(str1, x, y);
        for(int i = iShowBuySellNum - 1; i >= 0; i--) {
            y += fm.getHeight();
            if(y + iDescent > rc.y + rc.height)
                return;
            if(product != null && fSellP[i] > 0.0F) {
                g.setColor(GetPriceColor(fSellP[i], product.realData.yesterBalancePrice));
                str = Common.FloatToString(fSellP[i], iPrecision);
            } else {
                str = "—";
                g.setColor(Application.rhColor.clEqual);
            }
            x = (rc.x + rc.width / 2) - fm.stringWidth(str);
            g.drawString(str, x, y);
            if(product != null && fSellV[i] > 0.0F)
                str = String.valueOf((int)fSellV[i]);
            else
                str = "—";
            g.setColor(Application.rhColor.clVolume);
            x = (rc.x + rc.width) - fm.stringWidth(str);
            g.drawString(str, x, y);
        }

        for(int i = 0; i < iShowBuySellNum; i++) {
            y += fm.getHeight();
            if(y + iDescent > rc.y + rc.height)
                return;
            if(product != null && fBuyP[i] > 0.0F) {
                g.setColor(GetPriceColor(fBuyP[i], product.realData.yesterBalancePrice));
                str = Common.FloatToString(fBuyP[i], iPrecision);
            } else {
                str = "—";
                g.setColor(Application.rhColor.clEqual);
            }
            x = (rc.x + rc.width / 2) - fm.stringWidth(str);
            g.drawString(str, x, y);
            if(product != null && fBuyV[i] > 0.0F)
                str = String.valueOf((int)fBuyV[i]);
            else
                str = "—";
            g.setColor(Application.rhColor.clVolume);
            x = (rc.x + rc.width) - fm.stringWidth(str);
            g.drawString(str, x, y);
        }

        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.curPrice > 0.0F) {
            str = Common.FloatToString(product.realData.curPrice, iPrecision);
            g.setColor(GetPriceColor(product.realData.curPrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.curPrice > 0.0F && product.realData.yesterBalancePrice > 0.0F) {
            str = Common.FloatToString(product.realData.curPrice - product.realData.yesterBalancePrice, iPrecision);
            g.setColor(GetPriceColor(product.realData.curPrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product.realData != null && product.realData.openPrice > 0.0F) {
            str = Common.FloatToString(product.realData.openPrice, iPrecision);
            g.setColor(GetPriceColor(product.realData.openPrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.curPrice > 0.0F && product.realData.yesterBalancePrice > 0.0F) {
            str = Common.FloatToString(((product.realData.curPrice - product.realData.yesterBalancePrice) / product.realData.yesterBalancePrice) * 100F, 2) + "%";
            g.setColor(GetPriceColor(product.realData.curPrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product.realData != null && product.realData.highPrice > 0.0F) {
            str = Common.FloatToString(product.realData.highPrice, iPrecision);
            g.setColor(GetPriceColor(product.realData.highPrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.curAmount > 0)
            str = String.valueOf(product.realData.curAmount);
        else
            str = "—";
        g.setColor(Application.rhColor.clVolume);
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product.realData != null && product.realData.lowPrice > 0.0F) {
            str = Common.FloatToString(product.realData.lowPrice, iPrecision);
            g.setColor(GetPriceColor(product.realData.lowPrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.totalAmount > 0L)
            str = String.valueOf((int)product.realData.totalAmount);
        else
            str = "—";
        g.setColor(Application.rhColor.clVolume);
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product.realData != null && product.realData.amountRate > 0.0F)
            str = Common.FloatToString(product.realData.amountRate, 2);
        else
            str = "—";
        g.setColor(Application.rhColor.clVolume);
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.balancePrice > 0.0F) {
            str = Common.FloatToString(product.realData.balancePrice, iPrecision);
            g.setColor(GetPriceColor(product.realData.balancePrice, product.realData.yesterBalancePrice));
        } else {
            str = "—";
            g.setColor(Application.rhColor.clEqual);
        }
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product.realData != null && product.realData.yesterBalancePrice > 0.0F)
            str = Common.FloatToString(product.realData.yesterBalancePrice, iPrecision);
        else
            str = "—";
        g.setColor(Application.rhColor.clEqual);
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product.realData != null && product.realData.reserveCount > 0)
            str = String.valueOf(product.realData.reserveCount);
        else
            str = "—";
        g.setColor(Application.rhColor.clReserve);
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product.realData != null)
            str = String.valueOf(product.realData.reserveChange);
        else
            str ="—";
        g.setColor(Application.rhColor.clVolume);
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
        y += fm.getHeight();
        if(y + iDescent > rc.y + rc.height)
            return;
        if(product != null && product.realData != null && product.realData.outAmount > 0)
            str = String.valueOf(product.realData.outAmount);
        else
            str = "—";
        g.setColor(Application.rhColor.clVolume);
        x = (rc.x + rc.width / 2) - fm.stringWidth(str);
        g.drawString(str, x, y);
        if(product != null && product.realData != null && product.realData.inAmount > 0)
            str = String.valueOf(product.realData.inAmount);
        else
            str = "—";
        g.setColor(Application.rhColor.clVolume);
        x = (rc.x + rc.width) - fm.stringWidth(str);
        g.drawString(str, x, y);
    }

    static Color GetPriceColor(float fPrice, float fBenchMark) {
        if(fPrice > fBenchMark)
            return Application.rhColor.clIncrease;
        if(fPrice < fBenchMark)
            return Application.rhColor.clDecrease;
        else
            return Application.rhColor.clEqual;
    }
}
