// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Page_MarketStatus.java

package sam.bee.stock.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import sam.bee.stock.service.vo.CMDMarketSortVO;
import sam.bee.stock.service.vo.MarketStatusVO;

// Referenced classes of package gnnt.MEBS.HQApplet:
//            Page_Main, MenuListener, HQApplet, SendThread, 
//            RHColor, Pos, CodeTable, Common, 
//            Packet_MarketStatus

public class Page_MarketStatus extends BasicPage {

    byte currentStockType;
    int iCount;
    int iHighlightRowIndex;
    int iHighlightColIndex;
    static Pos pos[][];
    Font font;
    FontMetrics fm;
    MenuItem menuQuote;
    MenuItem menuMinLine;
    MenuItem menuKLine;
    static final int RANK_RATE = 0;
    static final int RANK_AMOUNTRATE = 5;
    static final int RANK_CONSIGNRATE = 6;
    static final int RANK_TOTALMONEY = 8;
    static String STOCK_RANK_NAME[][];
    Packet_MarketStatus packetInfo;
    MarketStatusVO statusData[];

    void askForDataOnTimer() {
        CMDMarketSortVO packet = new CMDMarketSortVO();
        iCount = getICount();
        packet.num = iCount;
        super.application.borker.askForData(packet);
    }

    public Page_MarketStatus(Rectangle _rc, Application applet, byte currentStockType) {
        super(_rc, applet);
        iCount = 7;
        iHighlightRowIndex = 0;
        iHighlightColIndex = 0;
        font = new Font("宋体", 0, 16);
        statusData = null;
        super.application.iCurrentPage = 5;
        this.currentStockType = currentStockType;
        askForDataOnTimer();
        makeMenus();
        setMenuEnable(this.currentStockType, false);
        STOCK_RANK_NAME = new String[3][3];
        STOCK_RANK_NAME[0][0] = super.application.getShowString("UpRateList");
        STOCK_RANK_NAME[1][0] = super.application.getShowString("DownRateList");
        STOCK_RANK_NAME[2][0] = super.application.getShowString("SwingList");
        STOCK_RANK_NAME[0][1] = super.application.getShowString("5MinUpRateList");
        STOCK_RANK_NAME[1][1] = super.application.getShowString("5MinDownRateList");
        STOCK_RANK_NAME[2][1] = super.application.getShowString("VolRateList");
        STOCK_RANK_NAME[0][2] = super.application.getShowString("ConsignRateDesc");
        STOCK_RANK_NAME[1][2] = super.application.getShowString("ConsignRateAsce");
        STOCK_RANK_NAME[2][2] = super.application.getShowString("MoneyList");
    }

    void paint(Graphics g) {
        if(statusData != null) {          
            if(Application.bDebug) {
                System.out.println("this.iCount = " + iCount);
                System.out.println("statusData.length / 9 = " + statusData.length / 9);
            }
        }
        initilizer(g);
        paintGrid(g);
        paintTitle(g);
        paintStockData(g);
        super.application.EndPaint();
        paintHighlight(iHighlightRowIndex, iHighlightColIndex);
    }

    void paintGrid(Graphics g) {
        this.fm = g.getFontMetrics(this.font);
        Font font = new Font("宋体", 0, 16);
        FontMetrics fm = g.getFontMetrics(font);
        g.setColor(Application.rhColor.clEqual);
        g.drawRect(super.m_rc.x, super.m_rc.y, super.m_rc.width - 1, super.m_rc.height - 2);
        int fontHeight = fm.getHeight() + 2;
        g.drawLine(super.m_rc.x, super.m_rc.y + fontHeight, super.m_rc.x + super.m_rc.width, super.m_rc.y + fontHeight);
        g.drawLine(super.m_rc.x, super.m_rc.y + super.m_rc.height / 3, super.m_rc.x + super.m_rc.width, super.m_rc.y + super.m_rc.height / 3);
        g.drawLine(super.m_rc.x, super.m_rc.y + super.m_rc.height / 3 + fontHeight, super.m_rc.x + super.m_rc.width, super.m_rc.y + super.m_rc.height / 3 + fontHeight);
        g.drawLine(super.m_rc.x, super.m_rc.y + (super.m_rc.height / 3) * 2, super.m_rc.x + super.m_rc.width, super.m_rc.y + (super.m_rc.height / 3) * 2);
        g.drawLine(super.m_rc.x, super.m_rc.y + (super.m_rc.height / 3) * 2 + fontHeight, super.m_rc.x + super.m_rc.width, super.m_rc.y + (super.m_rc.height / 3) * 2 + fontHeight);
        g.drawLine(super.m_rc.x + super.m_rc.width / 3, super.m_rc.y, super.m_rc.x + super.m_rc.width / 3, (super.m_rc.y + super.m_rc.height) - 2);
        g.drawLine(super.m_rc.x + (super.m_rc.width / 3) * 2, super.m_rc.y, super.m_rc.x + (super.m_rc.width / 3) * 2, (super.m_rc.y + super.m_rc.height) - 2);
    }

    void paintTitle(Graphics g) {
        int x = super.m_rc.x;
        int y = super.m_rc.y;
        int blockWidth = super.m_rc.width / 3;
        int blockHeight = super.m_rc.height / 3;
        Font font = new Font("宋体", 0, 16);
        FontMetrics fm = g.getFontMetrics(font);
        g.setFont(font);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                String str = STOCK_RANK_NAME[i][j];
                int strWidth = fm.stringWidth(str);
                int hgap = 0;
                if(blockWidth > strWidth)
                    hgap = (blockWidth - strWidth) / 2;
                g.setColor(Application.rhColor.clMultiQuote_TitleBack);
                g.fillRect(x + 1, y + 1, super.m_rc.width / 3 - 1, fm.getHeight() + 1);
                g.setColor(Application.rhColor.clItem);
                g.drawString(str, x + hgap, y + fm.getAscent() + 2);
                x += super.m_rc.width / 3;
            }

            x = super.m_rc.x;
            y += super.m_rc.height / 3;
        }

    }

    void paintHighlight(int newRowIndex, int newColIndex) {
        if(newRowIndex >= iCount * 3)
            return;
        Graphics g = super.application.getGraphics();
        Font font = new Font("宋体", 0, 16);
        FontMetrics fm = g.getFontMetrics(font);
        if(iCount <= 0)
            return;
        Pos oldPos = null;
        if(iHighlightRowIndex < iCount * 3)
            oldPos = pos[iHighlightRowIndex][iHighlightColIndex];
        Pos newPos = null;
        if(newRowIndex < iCount * 3)
            newPos = pos[newRowIndex][newColIndex];
        g.setColor(Application.rhColor.clBackGround);
        g.setXORMode(Application.rhColor.clHighlight);
        if(oldPos != null)
            g.fillRect(oldPos.x, oldPos.y, super.m_rc.width / 3, fm.getHeight());
        if(iHighlightRowIndex != newRowIndex || iHighlightColIndex != newColIndex) {
            if(newPos != null)
                g.fillRect(newPos.x, newPos.y, super.m_rc.width / 3, fm.getHeight());
            iHighlightRowIndex = newRowIndex;
            iHighlightColIndex = newColIndex;
        }
        g.setPaintMode();
    }

    boolean keyPressed(KeyEvent e) {
        if(packetInfo == null || statusData == null)
            return false;
        int iKeyCode = e.getKeyCode();
        switch(iKeyCode) {

        case 38: // '&'
        {
            if(iHighlightRowIndex <= 0)
                break;
            int i;
            for(i = 1; iHighlightRowIndex - i >= 0 && getIndexOfStatusDataArray(iHighlightRowIndex - i, iHighlightColIndex) < 0; i++);
            paintHighlight(iHighlightRowIndex - i, iHighlightColIndex);
            break;
        }

        case 40: // '('
        {
            if(iHighlightRowIndex >= 3 * iCount - 1)
                break;
            int i;
            for(i = 1; iHighlightRowIndex + i <= 3 * iCount - 1 && getIndexOfStatusDataArray(iHighlightRowIndex + i, iHighlightColIndex) < 0; i++);
            paintHighlight(iHighlightRowIndex + i, iHighlightColIndex);
            break;
        }

        case 37: // '%'
        {
            if(iHighlightColIndex > 0)
                paintHighlight(iHighlightRowIndex, iHighlightColIndex - 1);
            break;
        }

        case 39: // '\''
        {
            if(iHighlightColIndex < 2)
                paintHighlight(iHighlightRowIndex, iHighlightColIndex + 1);
            break;
        }

        case 10: // '\n'
        {
            int index = getIndexOfStatusDataArray(iHighlightRowIndex, iHighlightColIndex);
            if(index >= 0)
                super.application.queryStock(statusData[index].code);
            break;
        }
        default:
            break;
        }
        return false;
    }

    void initilizer(Graphics g) {
        FontMetrics fm = g.getFontMetrics(font);
        int x = super.m_rc.x;
        int fontHeight = fm.getHeight() + 2;
        int y = super.m_rc.y + fontHeight + 2;
        int temp = (super.m_rc.height / 3 - (fontHeight + 2)) / fontHeight;
        if(iCount > temp)
            iCount = temp;
        if(iCount <= 0)
            return;
        pos = new Pos[iCount * 3][3];
        for(int i = 0; i < iCount * 3; i++) {
            for(int j = 0; j < 3; j++) {
                pos[i][j] = new Pos();
                pos[i][j].x = x;
                pos[i][j].y = y;
                x += super.m_rc.width / 3;
            }

            x = super.m_rc.x;
            if(i == iCount - 1)
                y = super.m_rc.y + super.m_rc.height / 3 + fontHeight + 2;
            else
            if(i == 2 * iCount - 1)
                y = super.m_rc.y + (super.m_rc.height / 3) * 2 + fontHeight + 2;
            else
                y += fontHeight;
        }

    }

    private int getICount() {
        Graphics g = super.application.getGraphics();
        fm = g.getFontMetrics(font);
        int fontHeight = fm.getHeight() + 2;
        int iCount = (super.m_rc.height / 3 - (fontHeight + 2)) / fontHeight;
        return iCount;
    }

    void paintStockData(Graphics g) {
        if(packetInfo != null && statusData != null) {
            for(int i = 0; i < iCount * 3; i++) {
                for(int j = 0; j < 3; j++) {
                    int index = getIndexOfStatusDataArray(i, j);
                    if(index >= 0 && index < statusData.length) {
                        MarketStatusVO value = statusData[index];
                        int x = pos[i][j].x;
                        int y = pos[i][j].y;
                        if(j == 0 || j == 1 && i >= 0 && i < 2 * iCount)
                            paintRankData(g, value, 0, x, y);
                        else
                        if(i >= 2 * iCount && i < 3 * iCount && j == 1)
                            paintRankData(g, value, 5, x, y);
                        else
                        if(i >= 2 * iCount && i < 3 * iCount && j == 2)
                            paintRankData(g, value, 8, x, y);
                        else
                            paintRankData(g, value, 6, x, y);
                    }
                }

            }

        }
    }

    void paintRankData(Graphics g, MarketStatusVO value, int rankType, int x, int y) {
		Font font = new Font("宋体", 0, 16);
        FontMetrics fm = g.getFontMetrics(font);
        g.setFont(font);
        String strCode = value.code;
        CodeTable stockTable = (CodeTable)super.application.m_ProductByHttp.get(strCode);
        String strName = "";
        if(stockTable != null){
            strName = stockTable.sName;
        }
        int iPrecision = super.application.getPrecision(strCode);
        boolean showPercent = true;
        if(rankType == 8 || rankType == 5)
            showPercent = false;
        String strCur = formatRankData(g, value.cur, iPrecision, false);
        String strValue = formatRankData(g, value.value, 2, showPercent);
        g.setColor(Application.rhColor.clProductName);
        g.drawString(strCode, x + 3, y + fm.getAscent());
        switch(value.status) {
        case 0: // '\0'
            g.setColor(Application.rhColor.clIncrease);
            break;

        case 1: // '\001'
            g.setColor(Application.rhColor.clDecrease);
            break;

        case 2: // '\002'
            g.setColor(Application.rhColor.clEqual);
            break;
        }
        int spare = super.m_rc.width / 3 - 128;
        if(spare > 64) {
            int gap = (spare - 64) / 2;
            g.drawString(strCur, (x + super.m_rc.width / 3) - 64 - gap - fm.stringWidth(strCur), y + fm.getAscent());
        } else {
            g.drawString(strCur, (x + 128) - fm.stringWidth(strCur), y + fm.getAscent());
        }
        switch(rankType) {
        case 5: // '\005'
            g.setColor(Application.rhColor.clVolume);
            break;

        case 6: // '\006'
            g.setColor(Application.rhColor.clNumber);
            break;

        case 8: // '\b'
            g.setColor(Application.rhColor.clNumber);
            break;

        case 7: // '\007'
        default:
            if(value.value > 0.0F) {
                g.setColor(Application.rhColor.clIncrease);
                break;
            }
            if(value.value == 0.0F)
                g.setColor(Application.rhColor.clEqual);
            else
                g.setColor(Application.rhColor.clDecrease);
            break;
        }
        g.drawString(strValue, (x + super.m_rc.width / 3) - fm.stringWidth(strValue) - 3, y + fm.getAscent());
    }

    String formatRankData(Graphics g, float num, int iPrecision, boolean isPercent) {
        StringBuffer buf = new StringBuffer();
        if(!isPercent) {
            buf.append(Common.FloatToString(num, iPrecision));
        } else {
            if(num >= 0.0F)
                buf.append("+");
            buf.append(Common.FloatToString(num, iPrecision));
            buf.append("%");
        }
        return buf.toString();
    }

    boolean mouseLeftClicked(int x, int y) {
        if(packetInfo == null || statusData == null) {
            return false;
        } else {
            selectStock(x, y);
            return false;
        }
    }

    private void selectStock(int x, int y) {
        int fontHeight = fm.getHeight();
        for(int i = 0; i < iCount * 3; i++) {
            for(int j = 0; j < 3; j++) {
                int left = pos[i][j].x;
                int top = pos[i][j].y;
                if(x > left && x < left + super.m_rc.width / 3 && y > top && y < top + fontHeight && (iHighlightRowIndex != i || iHighlightColIndex != j) && getIndexOfStatusDataArray(i, j) >= 0)
                    paintHighlight(i, j);
            }

        }

    }

    boolean mouseLeftDblClicked(int x, int y) {
        if(packetInfo == null || statusData == null)
            return false;
        for(int i = 0; i < iCount * 3; i++) {
            for(int j = 0; j < 3; j++) {
                int left = pos[i][j].x;
                int top = pos[i][j].y;
                int fontHeight = fm.getHeight();
                if(x > left && x < left + super.m_rc.width / 3 && y > top && y < top + fontHeight) {
                    int rows = statusData.length / 9;
                    int index = getIndexOfStatusDataArray(i, j);
                    if(index >= 0)
                        super.application.queryStock(statusData[index].code);
                    return true;
                }
            }

        }

        return false;
    }

    int getIndexOfStatusDataArray(int i, int j) {
        if(i % iCount >= packetInfo.iCount)
            return -1;
        int rows = statusData.length / 9;
        if(i >= 0 && i < iCount)
            return j * 3 * rows + i;
        if(i >= iCount && i < 2 * iCount)
            return ((j * 3 + 1) * rows + i) - iCount;
        if(i >= 2 * iCount && i < 3 * iCount)
            return ((j * 3 + 2) * rows + i) - 2 * iCount;
        else
            return -1;
    }

    void makeMenus() {
        menuQuote = new MenuItem(super.application.getShowString("MultiQuote") + "  F2");
        menuQuote.setActionCommand("cmd_XX");
        menuQuote.addActionListener(this);
        menuMinLine = new MenuItem(super.application.getShowString("MinLine") + "  F5");
        menuMinLine.setActionCommand("minline");
        menuMinLine.addActionListener(this);
        menuKLine = new MenuItem(super.application.getShowString("Analysis"));
        menuKLine.setActionCommand("kline");
        menuKLine.addActionListener(this);
    }

    void processMenuEvent(PopupMenu popupMenu, int x, int y) {
        selectStock(x, y);
        popupMenu.removeAll();
        popupMenu.add(menuMinLine);
        popupMenu.add(menuKLine);
        popupMenu.add(menuQuote);
        processCommonMenuEvent(popupMenu, this);
        popupMenu.show(super.application, x, y);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.indexOf("cmd_") >= 0)
            executeCommand();
        else
        if(cmd.indexOf("STOCK_") >= 0) {
            byte stockType = getStockType(cmd);
            changeStockType(stockType);
        } else
        if(cmd.equals("minline")) {
            int index = getIndexOfStatusDataArray(iHighlightRowIndex, iHighlightColIndex);
            if(index >= 0) {
                String code = statusData[index].code;
                super.application.showPageMinLine(code);
            }
        } else
        if(cmd.equals("kline")) {
            int index = getIndexOfStatusDataArray(iHighlightRowIndex, iHighlightColIndex);
            if(index >= 0) {
                String code = statusData[index].code;
                super.application.showPageKLine(code);
            }
        } else {
            super.actionPerformed(e);
        }
    }

    private void changeStockType(byte stockType) {
        if(stockType == currentStockType || stockType == -1) {
            return;
        } else {
            setMenuEnable(currentStockType, true);
            setMenuEnable(stockType, false);
            currentStockType = stockType;
            askForDataOnTimer();
            return;
        }
    }

    void setMenuEnable(byte byte0, boolean flag) {
    }

    private byte getStockType(String name) {
        return ((byte)(!name.equals("PRODUCT_COMMON") ? -1 : 1));
    }

    private void executeCommand() {
        switch(currentStockType) {
        case 1: // '\001'
            super.application.userCommand("60");
            break;
        }
    }
}
