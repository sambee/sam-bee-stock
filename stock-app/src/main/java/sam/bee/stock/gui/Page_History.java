// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   Page_History.java

package sam.bee.stock.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

// Referenced classes of package gnnt.MEBS.HQApplet:
//            Page_Main, MenuListener, HQApplet, CodeTable, 
//            RHColor

public class Page_History extends BasicPage {

    private final int GAP = 3;
    private int iTitleHeight;
    Vector m_vCode;
    int m_iRows;
    int m_iCols;
    int m_iWidth;
    int m_iTotalPage;
    int m_iCurPage;
    int m_iHighlightRow;
    int m_iHighlightCol;
    final Font font = new Font("宋体", 0, 16);
    final Font fontTitle = new Font("宋体", 1, 20);
    FontMetrics fm;
    MenuItem menuQuote;
    MenuItem menuMarket;
    MenuItem menuKLine;

    public Page_History(Rectangle _rc, Application applet) {
        super(_rc, applet);
        iTitleHeight = 30;
        m_iHighlightRow = 0;
        m_iHighlightCol = 0;
        super.application.iCurrentPage = 6;
        m_vCode = new Vector();
        for(int i = 0; i < super.application.m_codeList.size(); i++) {
            CodeTable codeTable = (CodeTable)super.application.m_ProductByHttp.get(super.application.m_codeList.elementAt(i));
            if(codeTable != null && (codeTable.status == 1 || codeTable.status == 6))
                m_vCode.addElement(super.application.m_codeList.elementAt(i));
        }

        makeMenus();
    }

    void paint(Graphics g) {
        paintTitle(g);
        g.setFont(font);
        fm = g.getFontMetrics(font);
        if(m_vCode.size() == 0) {
            paintPromptMessage(g);
            return;
        } else {
            calculateSize();
            paintProduct(g);
            super.application.EndPaint();
            paintHighlight(-1, -1);
            return;
        }
    }

    void paintTitle(Graphics g) {
        int x = super.m_rc.x;
        int y = super.m_rc.y;
        g.setFont(fontTitle);
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Application.rhColor.clProductName);
        String strTitle = super.application.getShowString("History");
        x += (super.m_rc.width - fm.stringWidth(strTitle)) / 2;
        if(x < 0)
            x = 0;
        g.drawString(strTitle, x, y + fm.getAscent());
        x = super.m_rc.x;
        y = super.m_rc.y + fm.getHeight();
        g.setColor(Application.rhColor.clGrid);
        g.drawRect(x, y, (x + super.m_rc.width) - 1, super.m_rc.height - fm.getHeight());
        iTitleHeight = fm.getHeight();
    }

    private void paintPromptMessage(Graphics g) {
        String prompt = super.application.getShowString("HistoryPrompt");
        int promptWidth = fm.stringWidth(prompt);
        int lines = promptWidth / (super.m_rc.width - 8);
        g.setFont(font);
        g.setColor(Application.rhColor.clProductName);
        if(promptWidth % (super.m_rc.width - 8) > 0)
            lines++;
        int y = (super.m_rc.height - fm.getHeight() * lines - 20) / 2 + 20 + fm.getAscent();
        int x = 4;
        int beginIndex = 0;
        int lineChars = (super.m_rc.width - 8) / 16;
        while(beginIndex < prompt.length())  {
            int endIndex = beginIndex + lineChars;
            String strLine = "";
            if(endIndex > prompt.length()) {
                strLine = prompt.substring(beginIndex);
                beginIndex = prompt.length();
            } else {
                strLine = prompt.substring(beginIndex, endIndex);
                beginIndex = endIndex;
            }
            x = (super.m_rc.width - 8 - fm.stringWidth(strLine)) / 2 + 4;
            g.drawString(strLine, x, y);
            y += fm.getHeight();
        }
    }

    void calculateSize() {
        m_iRows = (super.m_rc.height - iTitleHeight) / (fm.getHeight() + 3);
        m_iWidth = fm.stringWidth("  大蒜十月  AB0210  ");
        m_iCols = super.m_rc.width / m_iWidth;
        if(m_iRows == 0 || m_iCols == 0)
            return;
        int iTotalPage = m_vCode.size() / (m_iRows * m_iCols);
        if(m_vCode.size() % (m_iRows * m_iCols) > 0)
            iTotalPage++;
        if(iTotalPage != m_iTotalPage) {
            m_iCurPage = 0;
            m_iHighlightRow = 0;
            m_iHighlightCol = 0;
            m_iTotalPage = iTotalPage;
        }
        if(m_iHighlightRow >= m_iRows)
            m_iHighlightRow = 0;
        if(m_iHighlightCol >= m_iCols)
            m_iHighlightCol = 0;
        if(m_iCurPage * (m_iRows * m_iCols) + m_iHighlightRow * m_iCols + m_iHighlightCol >= m_vCode.size()) {
            m_iHighlightRow = 0;
            m_iHighlightCol = 0;
        }
    }

    void paintProduct(Graphics g) {
        String strText = super.application.getShowString("PagePrefix") + (m_iCurPage + 1) + super.application.getShowString("PageSuffix") + " " + super.application.getShowString("TotalPagePrefix") + m_iTotalPage + super.application.getShowString("TotalPageSuffix");
        g.setColor(Application.rhColor.clGrid);
        g.drawString(strText, (super.m_rc.x + super.m_rc.width) - fm.stringWidth(strText), (super.m_rc.y + iTitleHeight) - fm.getDescent());
        int iIndex = m_iCurPage * (m_iRows * m_iCols);
        for(int i = 0; i < m_iRows; i++) {
            for(int j = 0; j < m_iCols; j++) {
                if(iIndex >= m_vCode.size())
                    break;
                CodeTable codeTable = (CodeTable)super.application.m_ProductByHttp.get(m_vCode.elementAt(iIndex));
                paintOneProduct(g, i, j, codeTable.sName, (String)m_vCode.elementAt(iIndex));
                iIndex++;
            }

            if(iIndex >= m_vCode.size())
                break;
        }

    }

    private void paintOneProduct(Graphics g, int iRow, int iCol, String name, String code) {
        int x = super.m_rc.x + iCol * m_iWidth;
        int y = super.m_rc.y + iTitleHeight + iRow * (fm.getHeight() + 3);
        g.setColor(Application.rhColor.clProductName);
        String strText = name + " " + code + "  ";
        if(name.equals(code))
            strText = code;
        g.drawString(strText, (x + m_iWidth) - fm.stringWidth(strText), y + fm.getAscent() + 1);
    }

    void paintHighlight(int newRow, int newCol) {
        Graphics g = super.application.getGraphics();
        g.setColor(Application.rhColor.clBackGround);
        g.setXORMode(Application.rhColor.clHighlight);
        if(m_iHighlightRow != -1)
            paintCurHighlight(g, m_iHighlightRow, m_iHighlightCol);
        if(newRow != -1 && (m_iHighlightRow != newRow || m_iHighlightCol != newCol)) {
            paintCurHighlight(g, newRow, newCol);
            m_iHighlightRow = newRow;
            m_iHighlightCol = newCol;
        }
        g.setPaintMode();
    }

    void paintCurHighlight(Graphics g, int iRow, int iCol) {
        int x = super.m_rc.x + iCol * m_iWidth;
        int y = super.m_rc.y + iTitleHeight + iRow * (fm.getHeight() + 3);
        g.fillRect(x, y, m_iWidth, fm.getHeight() + 3);
    }

    boolean keyPressed(KeyEvent e) {
        int iKeyCode = e.getKeyCode();
        switch(iKeyCode) {
        default:
            break;

        case 38: // '&'
            if(m_iHighlightRow > 0) {
                paintHighlight(m_iHighlightRow - 1, m_iHighlightCol);
                break;
            }
            if(m_iCurPage == 0) {
                return false;
            } else {
                m_iCurPage--;
                m_iHighlightRow = m_iRows - 1;
                return true;
            }

        case 40: // '('
            if(m_iHighlightRow == m_iRows - 1) {
                if(m_iCurPage >= m_iTotalPage - 1)
                    break;
                m_iCurPage++;
                m_iHighlightRow = 0;
                if(m_iCurPage * (m_iRows * m_iCols) + m_iHighlightCol > m_vCode.size() - 1)
                    m_iHighlightCol = m_vCode.size() - m_iCurPage * (m_iRows * m_iCols) - 1;
                return true;
            }
            if(m_iCurPage * (m_iRows * m_iCols) + (m_iHighlightRow + 1) * m_iCols + m_iHighlightCol <= m_vCode.size() - 1)
                paintHighlight(m_iHighlightRow + 1, m_iHighlightCol);
            break;

        case 37: // '%'
            if(m_iHighlightCol > 0) {
                paintHighlight(m_iHighlightRow, m_iHighlightCol - 1);
                break;
            }
            if(m_iHighlightRow > 0) {
                paintHighlight(m_iHighlightRow - 1, m_iCols - 1);
                break;
            }
            if(m_iCurPage > 0) {
                m_iCurPage--;
                m_iHighlightRow = m_iRows - 1;
                m_iHighlightCol = m_iCols - 1;
                return true;
            }
            break;

        case 39: // '\''
            if(m_iHighlightCol < m_iCols - 1) {
                if(m_iCurPage * (m_iRows * m_iCols) + m_iHighlightRow * m_iCols + m_iHighlightCol < m_vCode.size() - 1)
                    paintHighlight(m_iHighlightRow, m_iHighlightCol + 1);
                break;
            }
            if(m_iHighlightRow < m_iRows - 1) {
                if(m_iCurPage * (m_iRows * m_iCols) + (m_iHighlightRow + 1) * m_iCols < m_vCode.size())
                    paintHighlight(m_iHighlightRow + 1, 0);
                break;
            }
            if(m_iCurPage < m_iTotalPage - 1) {
                m_iCurPage++;
                m_iHighlightRow = m_iHighlightCol = 0;
                return true;
            }
            break;

        case 33: // '!'
            if(m_iCurPage > 0) {
                m_iCurPage--;
                m_iHighlightRow = m_iHighlightCol = 0;
                return true;
            }
            break;

        case 34: // '"'
            if(m_iCurPage < m_iTotalPage - 1) {
                m_iCurPage++;
                m_iHighlightRow = m_iHighlightCol = 0;
                return true;
            }
            break;

        case 10: // '\n'
            int iIndex = m_iCurPage * (m_iRows * m_iCols) + m_iHighlightRow * m_iCols + m_iHighlightCol;
            if(iIndex < m_vCode.size())
                super.application.showPageKLine((String)m_vCode.elementAt(iIndex));
            break;
        }
        return false;
    }

    boolean mouseLeftClicked(int x, int y) {
        selectProduct(x, y);
        return false;
    }

    private boolean selectProduct(int x, int y) {
        int iCol = (x - super.m_rc.x) / m_iWidth;
        int iRow = (y - super.m_rc.y - iTitleHeight) / (fm.getHeight() + 3);
        if(iCol >= m_iCols || iRow >= m_iRows)
            return false;
        if(m_iCurPage * (m_iRows * m_iCols) + iRow * m_iCols + iCol >= m_vCode.size())
            return false;
        if(iRow == m_iHighlightRow && iCol == m_iHighlightCol) {
            return true;
        } else {
            paintHighlight(iRow, iCol);
            return true;
        }
    }

    boolean mouseLeftDblClicked(int x, int y) {
        if(!selectProduct(x, y))
            return false;
        int iIndex = m_iCurPage * (m_iRows * m_iCols) + m_iHighlightRow * m_iCols + m_iHighlightCol;
        if(iIndex < m_vCode.size())
            super.application.showPageKLine((String)m_vCode.elementAt(iIndex));
        return false;
    }

    void makeMenus() {
        menuQuote = new MenuItem(super.application.getShowString("MultiQuote") + "  F2");
        menuQuote.setActionCommand("cmd_60");
        menuQuote.addActionListener(this);
        menuMarket = new MenuItem(super.application.getShowString("ClassedList") + "  F4");
        menuMarket.setActionCommand("cmd_80");
        menuMarket.addActionListener(this);
        menuKLine = new MenuItem(super.application.getShowString("Analysis") + "  F5");
        menuKLine.setActionCommand("kline");
        menuKLine.addActionListener(this);
    }

    void processMenuEvent(PopupMenu popupMenu, int x, int y) {
        selectProduct(x, y);
        popupMenu.removeAll();
        popupMenu.add(menuKLine);
        popupMenu.addSeparator();
        popupMenu.add(menuQuote);
        popupMenu.add(menuMarket);
        processCommonMenuEvent(popupMenu, this);
        popupMenu.show(super.application, x, y);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.indexOf("cmd_") >= 0)
            super.application.userCommand(cmd.substring(4));
        else
        if(cmd.equals("kline")) {
            int iIndex = m_iCurPage * (m_iRows * m_iCols) + m_iHighlightRow * m_iCols + m_iHighlightCol;
            if(iIndex < m_vCode.size())
                super.application.showPageKLine((String)m_vCode.elementAt(iIndex));
        } else {
            super.actionPerformed(e);
        }
    }
}
