// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Page_Bottom.java

package sam.bee.stock.gui;

import java.awt.*;
import java.util.Hashtable;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            HQApplet, RHColor, CodeTable, ProductData, 
//            Common, Draw_Quote

public class Page_Bottom {

    Application m_applet;
    Graphics g;
    Rectangle rc;
    Rectangle m_rcIndex;
    Rectangle m_rcTime;
    Font font;

    public Page_Bottom(Graphics _g, Rectangle _rc, Application applet) {
        font = new Font("宋体", 0, 16);
        g = _g;
        rc = _rc;
        m_applet = applet;
    }

    void paint() {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int iWidth = fm.stringWidth("2005-12-24 09:30") + 2;
        m_rcIndex = new Rectangle(rc.x, rc.y, rc.width - iWidth, rc.height);
        m_rcTime = new Rectangle((rc.x + rc.width) - iWidth, rc.y, iWidth, rc.height);
        g.setColor(Application.rhColor.clBackGround);
        g.fillRect(rc.x, rc.y, rc.x + rc.width, rc.y + rc.height);
        g.setColor(Application.rhColor.clGrid);
        g.drawLine(rc.x, rc.y, rc.width, rc.y);
        g.drawLine(m_rcTime.x - 1, rc.y, m_rcTime.x - 1, rc.y + rc.height);
        if(rc.height < fm.getHeight() / 2) {
            return;
        } else {
            paintIndex();
            paintCurTime();
            return;
        }
    }

    
    private void paintIndex() {
        if(m_applet.indexMainCode.length() == 0)
            return;
        if(m_applet.m_bShowIndexAtBottom == 0)
            return;
        CodeTable s = null;
        if(m_applet.m_ProductByHttp != null && m_applet.m_ProductByHttp.get(m_applet.indexMainCode) != null)
            s = (CodeTable)m_applet.m_ProductByHttp.get(m_applet.indexMainCode);
        String strName;
        if(s != null)
            strName = s.sName;
        else
            strName = "  指数  ";
        ProductData product = m_applet.getProductData(m_applet.indexMainCode);
        if(product == null || product.realData == null)
            return;
        FontMetrics fm = g.getFontMetrics();
        int x = m_rcIndex.x + 2;
        if((m_rcIndex.x + m_rcIndex.width) - x < fm.stringWidth(strName))
            return;
        g.setColor(Application.rhColor.clProductName);
        g.drawString(strName, x, m_rcIndex.y + fm.getAscent());
        x += fm.stringWidth(strName) + 10;
        String str;
        if(product.realData.curPrice > 0.0F)
            str = Common.FloatToString(product.realData.curPrice, m_applet.m_iPrecisionIndex);
        else
            str = "\u2014\u2014";
        if((m_rcIndex.x + m_rcIndex.width) - x < fm.stringWidth(str))
            return;
        g.setColor(Draw_Quote.GetPriceColor(product.realData.curPrice, product.realData.yesterBalancePrice));
        g.drawString(str, x, m_rcIndex.y + fm.getAscent());
        x += fm.stringWidth(str) + 10;
        if(product.realData.curPrice > 0.0F && product.realData.yesterBalancePrice > 0.0F)
            str = Common.FloatToString(product.realData.curPrice - product.realData.yesterBalancePrice, m_applet.m_iPrecisionIndex);
        else
            str = "\u2014\u2014";
        if(product.realData.curPrice > product.realData.yesterBalancePrice)
            str = "+" + str;
        else
        if(product.realData.curPrice * 100F == product.realData.yesterBalancePrice * 100F)
            str = "\u2014\u2014";
        if((m_rcIndex.x + m_rcIndex.width) - x < fm.stringWidth(str))
            return;
        g.setColor(Draw_Quote.GetPriceColor(product.realData.curPrice, product.realData.yesterBalancePrice));
        g.drawString(str, x, m_rcIndex.y + fm.getAscent());
        x += fm.stringWidth(str) + 10;
        str = m_applet.getShowString("Volume");
        if(product.realData.totalAmount > 0L)
            str = str + String.valueOf((int)product.realData.totalAmount);
        else
            str = str + "\u2014\u2014";
        if((m_rcIndex.x + m_rcIndex.width) - x < fm.stringWidth(str))
            return;
        g.setColor(Application.rhColor.clVolume);
        g.drawString(str, x, m_rcIndex.y + fm.getAscent());
        x += fm.stringWidth(str) + 10;
        str = m_applet.getShowString("Order");
        if(product.realData.reserveCount > 0)
            str = str + String.valueOf(product.realData.reserveCount);
        else
            str = str + "\u2014\u2014";
        if((m_rcIndex.x + m_rcIndex.width) - x < fm.stringWidth(str)) {
            return;
        } else {
            g.setColor(Application.rhColor.clReserve);
            g.drawString(str, x, m_rcIndex.y + fm.getAscent());
            return;
        }
    }

    private void paintCurTime() {
        if(m_applet.m_iDate == 0 || m_applet.m_iDate == 0)
            return;
        g.setColor(Application.rhColor.clItem);
        String strTime;
        for(strTime = String.valueOf(m_applet.m_iTime / 100); strTime.length() < 4; strTime = "0" + strTime);
        strTime = strTime.substring(0, 2) + ":" + strTime.substring(2);
        String strText = m_applet.m_iDate / 10000 + "-" + (m_applet.m_iDate / 100) % 100 + "-" + m_applet.m_iDate % 100 + " " + strTime;
        FontMetrics fm = g.getFontMetrics();
        g.drawString(strText, m_rcTime.x, m_rcTime.y + fm.getAscent());
    }
}
