// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Page_MinLine.java

package sam.bee.stock.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import sam.bee.stock.service.vo.CMDBillVO;
import sam.bee.stock.service.vo.CMDMinVO;
import sam.bee.stock.vo.BillDataVO;


// Referenced classes of package gnnt.MEBS.HQApplet:
//            Page_Main, MenuListener, HQApplet, ProductData, 
//            SendThread, Draw_MinLine, Draw_Quote, Draw_LastBill

class Page_MinLine extends BasicPage {

    int iProductType;
    MenuItem menuPrevStock;
    MenuItem menuPostStock;
    MenuItem menuPageKLine;
    MenuItem menuPageBill;
    MenuItem menuQuote;
    MenuItem menuMarket;
    Draw_MinLine draw_MinLine;
    Rectangle rcMinLine;
    int m_iQuoteH;

    void askForDataOnTimer() {
        ProductData stock = super.application.getProductData(super.application.strCurrentCode);
        java.util.Date time = null;
        if(stock != null && stock.realData != null)
            time = stock.realData.time;
        //SendThread.AskForRealQuote(super.m_applet.strCurrentCode, time, super.m_applet.sendThread);
        application.borker.AskForRealQuote(super.application.strCurrentCode, time);
        CMDBillVO packet = new CMDBillVO();
        packet.type = 0;
        packet.code = super.application.strCurrentCode;
        if(stock != null && stock.vBill != null && stock.vBill.size() > 0)
            packet.time = ((BillDataVO)stock.vBill.lastElement()).time;
        super.application.borker.askForData(packet);
    }

    void AskForDataOnce() {
        ProductData stock = super.application.getProductData(super.application.strCurrentCode);
        java.util.Date time = null;
        if(stock != null && stock.realData != null)
            time = stock.realData.time;
        //SendThread.AskForRealQuote(super.m_applet.strCurrentCode, time, super.m_applet.sendThread);
        application.borker.AskForRealQuote(super.application.strCurrentCode, time);
        CMDMinVO packet1 = new CMDMinVO();
        packet1.code = super.application.strCurrentCode;
        packet1.type = 0;
        packet1.time = 0;
        super.application.borker.askForData(packet1);
        CMDBillVO packet = new CMDBillVO();
        packet.type = 1;
        packet.code = super.application.strCurrentCode;
        packet.time = (super.m_rc.height - 320) / 16 + 1;
        super.application.borker.askForData(packet);
    }

    public Page_MinLine(Rectangle _rc, Application applet) {
        super(_rc, applet);
        m_iQuoteH = 380;
        AskForDataOnce();
        applet.iCurrentPage = 1;
        draw_MinLine = new Draw_MinLine(applet, true);
        makeMenus();
        iProductType = applet.getProductType(applet.strCurrentCode);
    }

    void paint(Graphics g) {
        ProductData stock = super.application.getProductData(super.application.strCurrentCode);
        g.setFont(new Font("\u6977\u4F53_GB2312", 1, 26));
        FontMetrics fm = g.getFontMetrics();
        m_iQuoteH = fm.getHeight();
        g.setFont(new Font("\u5B8B\u4F53", 0, 16));
        fm = g.getFontMetrics();
        int _tmp = iProductType;
        m_iQuoteH += 9 * fm.getHeight() + super.application.iShowBuySellPrice * 2 * fm.getHeight();
        Rectangle rcQuote = new Rectangle((super.m_rc.x + super.m_rc.width) - 200, super.m_rc.y, 200, m_iQuoteH);
        if(rcQuote.height > super.m_rc.height)
            rcQuote.height = super.m_rc.height;
        int _tmp1 = iProductType;
        Draw_Quote.Paint(g, rcQuote, stock, super.application.strCurrentCode, super.application.iShowBuySellPrice, super.application);
        Rectangle rcBill = new Rectangle((super.m_rc.x + super.m_rc.width) - 200, super.m_rc.y + m_iQuoteH, 200, (super.m_rc.height - m_iQuoteH) + 1);
        Draw_LastBill.Paint(g, rcBill, stock, super.application);
        rcMinLine = new Rectangle(super.m_rc.x, super.m_rc.y, super.m_rc.width - 200, super.m_rc.height);
        draw_MinLine.Paint(g, rcMinLine, stock);
    }

    boolean keyPressed(KeyEvent e) {
        int iKeyCode = e.getKeyCode();
        boolean bResult;
        switch(iKeyCode) {
        case 34: // '"'
            super.application.ChangeStock(false, false);
            bResult = true;
            break;

        case 33: // '!'
            super.application.ChangeStock(true, false);
            bResult = true;
            break;

        default:
            bResult = draw_MinLine.KeyPressed(iKeyCode);
            break;
        }
        return bResult;
    }

    boolean mouseLeftClicked(int x, int y) {
        if(rcMinLine != null && rcMinLine.contains(x, y) && draw_MinLine != null)
            return draw_MinLine.MouseLeftClicked(x, y);
        else
            return false;
    }

    boolean mouseDragged(int x, int y) {
        if(rcMinLine != null && rcMinLine.contains(x, y) && draw_MinLine != null)
            return draw_MinLine.MouseDragged(x, y);
        else
            return false;
    }

    void makeMenus() {
        menuPrevStock = new MenuItem(super.application.getShowString("PrevCommodity") + "  PageUp");
        menuPostStock = new MenuItem(super.application.getShowString("NextCommodity") + "  PageDown");
        menuPageKLine = new MenuItem(super.application.getShowString("Analysis") + "  F5");
        menuPageBill = new MenuItem(super.application.getShowString("TradeList") + "  F1");
        menuQuote = new MenuItem(super.application.getShowString("MultiQuote") + "  F2");
        menuMarket = new MenuItem(super.application.getShowString("ClassedList") + "  F4");
        menuPageKLine.setActionCommand("kline");
        menuPageKLine.addActionListener(this);
        menuPageBill.setActionCommand("bill");
        menuPageBill.addActionListener(this);
        menuPrevStock.setActionCommand("prevstock");
        menuPrevStock.addActionListener(this);
        menuPostStock.setActionCommand("poststock");
        menuPostStock.addActionListener(this);
        menuQuote.setActionCommand("cmd_quote");
        menuQuote.addActionListener(this);
        menuMarket.setActionCommand("cmd_market");
        menuMarket.addActionListener(this);
    }

    public void processMenuEvent(PopupMenu popupMenu, int x, int y) {
        popupMenu.removeAll();
        popupMenu.add(menuPageKLine);
        if(iProductType != 2 && iProductType != 3)
            popupMenu.add(menuPageBill);
        popupMenu.addSeparator();
        popupMenu.add(menuPrevStock);
        popupMenu.add(menuPostStock);
        popupMenu.addSeparator();
        popupMenu.add(menuQuote);
        popupMenu.add(menuMarket);
        processCommonMenuEvent(popupMenu, this);
        popupMenu.show(super.application, x, y);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.indexOf("cmd_") >= 0) {
            String requestType = cmd.substring(4);
            if(requestType.equals("quote"))
                executeQuoteCommand();
            else
            if(requestType.equals("market"))
                executeMarketCommand();
        } else
        if(cmd.equals("kline"))
            super.application.showPageKLine(super.application.strCurrentCode);
        else
        if(cmd.equals("bill"))
            super.application.userCommand("01");
        else
        if(cmd.equals("prevstock")) {
            super.application.ChangeStock(true, false);
            super.application.repaint();
        } else
        if(cmd.equals("poststock")) {
            super.application.ChangeStock(false, false);
            super.application.repaint();
        } else {
            super.actionPerformed(e);
        }
    }

    private void executeQuoteCommand() {
        super.application.userCommand("60");
    }

    private void executeMarketCommand() {
        super.application.userCommand("80");
    }
}
