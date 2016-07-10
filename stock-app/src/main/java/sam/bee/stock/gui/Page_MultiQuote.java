package sam.bee.stock.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.stock.service.vo.SortReq;
import sam.bee.stock.vo.ProductDataVO;
import static sam.bee.stock.gui.Application.bDebug;

/**
 * 列表页
 * @author Administrator
 *
 */
public class Page_MultiQuote extends BasicPage {
    private static final Logger logger = LoggerFactory.getLogger(Page_MultiQuote.class);
    static byte flag = 0;
    ProductDataVO backQuoteData[];
    private int iDynamicIndex;
    String strSortItem;
    int iHighlightIndex;
    int iStockRows;
    Font font;
    Font fontTitle;
    FontMetrics fm;
    int fontHeight;

    boolean bCanMove;
    byte currentStockType;
    public byte sortBy;
    public byte isDescend;
    public int iStart;
    private int iEnd;
    boolean bShowUserStock;
    String iUserStockCode[];
    String backIUserStockCode[];
    public Packet_MultiQuote packetInfo;
    public ProductDataVO quoteData[];
    @SuppressWarnings("rawtypes")	private Hashtable m_htItemInfo;
    private String m_strItems[];
    private int m_iStaticIndex;
    Menu menuStockRank;
    MenuItem menuMarket;
    MenuItem menuPageMinLine;
    MenuItem menuPageKLine;
    boolean m_bShowSortTag;

    void askForDataOnTimer() {
        setSortStockRequestPacket();
    }

    /**
     * 在点击界面的列时，触发此事件
     */
    private void setSortStockRequestPacket() {
    	SortReq packet = new SortReq();
        packet.isDescend = isDescend;
        packet.sortBy = sortBy;
        if(iStart == iEnd){
            iEnd = iStart + 1;
        }
        packet.start = iStart;
        packet.end = iEnd;

        if(bDebug)
            System.out.println("[Page_MultiQuote]  requestClassSort Start = " + iStart + "  End = " + iEnd);
        super.application.borker.askForData(packet);
    }

    public Page_MultiQuote(Rectangle _rc, Application applet, byte currentStockType) {
        super(_rc, applet);
        strSortItem = "Code";
        iHighlightIndex = 1;
        font = new Font("宋体", 0, 16);
        fontTitle = new Font("宋体", 1, 16);
        bCanMove = true;
        sortBy = 0;
        isDescend = 0;
        iStart = 1;
        iEnd = 30;
        m_bShowSortTag = false;
        super.application.iCurrentPage = 0;
        this.currentStockType = currentStockType;
        initStockFieldInfo();
        calculateRowsOfPage();
        iStart = 1;
        iEnd = iStockRows;
        flag = 0;
        setUserStockCode();
        if(this.currentStockType == 0) {
            initUserStockArray();
            bShowUserStock = true;
        }
        askForDataOnTimer();
        makeMenus();
        setMenuEnable(this.currentStockType, false);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	void initStockFieldInfo() {
        m_htItemInfo = new Hashtable();
        m_htItemInfo.put("No", new MultiQuoteItemInfo("", 20, -1));
        m_htItemInfo.put("Name", new MultiQuoteItemInfo(super.application.getShowString("Name"), 80, -1));
        m_htItemInfo.put("Code", new MultiQuoteItemInfo(super.application.getShowString("Code"), 64, 0));
        m_htItemInfo.put("CurPrice", new MultiQuoteItemInfo(super.application.getShowString("Newly"), 60, 1));
        m_htItemInfo.put("CurAmount", new MultiQuoteItemInfo(super.application.getShowString("CurVol"), 60, -1));
        m_htItemInfo.put("SellPrice", new MultiQuoteItemInfo(super.application.getShowString("SellPrice"), 60, -1));
        m_htItemInfo.put("SellAmount", new MultiQuoteItemInfo(super.application.getShowString("SellVol"), 48, -1));
        m_htItemInfo.put("BuyPrice", new MultiQuoteItemInfo(super.application.getShowString("BuyPrice"), 60, -1));
        m_htItemInfo.put("BuyAmount", new MultiQuoteItemInfo(super.application.getShowString("BuyVol"), 48, -1));
        m_htItemInfo.put("TotalAmount", new MultiQuoteItemInfo(super.application.getShowString("Volume"), 75, 9));
        m_htItemInfo.put("UpValue", new MultiQuoteItemInfo(super.application.getShowString("ChangeValue"), 65, 2));
        m_htItemInfo.put("UpRate", new MultiQuoteItemInfo(super.application.getShowString("ChangeRate"), 65, 3));
        m_htItemInfo.put("ReverseCount", new MultiQuoteItemInfo(super.application.getShowString("Order"), 70, -1));
        m_htItemInfo.put("Balanc", new MultiQuoteItemInfo(super.application.getShowString("Balance"), 60, -1));
        m_htItemInfo.put("OpenPrice", new MultiQuoteItemInfo(super.application.getShowString("Open"), 55, -1));
        m_htItemInfo.put("HighPrice", new MultiQuoteItemInfo(super.application.getShowString("High"), 55, -1));
        m_htItemInfo.put("LowPrice", new MultiQuoteItemInfo(super.application.getShowString("Low"), 55, -1));
        m_htItemInfo.put("YesterBalance", new MultiQuoteItemInfo(super.application.getShowString("PreBalance"), 60, -1));
        m_htItemInfo.put("TotalMoney", new MultiQuoteItemInfo(super.application.getShowString("Money"), 80, 6));
        m_htItemInfo.put("AmountRate", new MultiQuoteItemInfo(super.application.getShowString("VolRate"), 50, 5));
        m_htItemInfo.put("ConsignRate", new MultiQuoteItemInfo(super.application.getShowString("ConsignRate"), 50, 7));
        m_htItemInfo.put("Unit", new MultiQuoteItemInfo(super.application.getShowString("Unit"), 40, -1));
        String strItemName = super.application.getParameter("MultiQuoteName", "Name:;Code:;CurPrice:;CurAmount:;SellPrice:;SellAmount:;BuyPrice:;BuyAmount:;TotalAmount:;UpValue:;UpRate:;ReverseCount:;Balance:;OpenPrice:;HighPrice:;LowPrice:;YesterBalance:;TotalMoney:;AmountRate:;ConsignRate:;Unit:;");
        String strItemNames[] = Common.split(strItemName, 59);
        for(int i = 0; i < strItemNames.length; i++) {
            String str[] = Common.split(strItemNames[i], 58);
            if(str.length == 2 && str[1].length() > 0) {
                MultiQuoteItemInfo itemInfo = (MultiQuoteItemInfo)m_htItemInfo.get(str[0]);
                if(itemInfo != null)
                    itemInfo.name = str[1];
            }
        }

        String strItems = super.application.getParameter("MultiQuoteItems", "No;Name;Code;CurPrice;CurAmount;SellPrice;SellAmount;BuyPrice;BuyAmount;TotalAmount;UpValue;ReverseCount;Balance;OpenPrice;HighPrice;LowPrice;YesterBalance;");
        m_strItems = Common.split(strItems, 59);
        m_iStaticIndex = Integer.parseInt(super.application.getParameter("MultiQuoteStaticIndex", "3"));
        iDynamicIndex = m_iStaticIndex + 1;
    }

    private void initGraph(Graphics g) {
        fm = g.getFontMetrics(font);
        fontHeight = fm.getHeight();
        g.setFont(font);
    }

    private void calculateRowsOfPage() {
        int gap = 2;
        Graphics g = super.application.getGraphics();
        fm = g.getFontMetrics(font);
        fontHeight = fm.getHeight();
        iStockRows = super.m_rc.height / (fontHeight + gap) - 1;
        if(iStockRows < 1)
            iStockRows = 25;
        iEnd = (iStart + iStockRows) - 1;
    }

    void paint(Graphics g) {
        bCanMove = true;
        initGraph(g);
        calculateRowsOfPage();
        paintTitleItems(g);
        paintQuoteData(g);
        super.application.EndPaint();
        paintHighlightBar();
    }

    void paintTitleItems(Graphics g) {
        int x = super.m_rc.x;
        int y = super.m_rc.y + fm.getAscent();
        g.setColor(Application.rhColor.clMultiQuote_TitleBack);
        g.fillRect(super.m_rc.x, super.m_rc.y, super.m_rc.width, fontHeight);
        for(int i = 0; i < m_strItems.length; i++) {
            if(i > m_iStaticIndex && i < iDynamicIndex)
                continue;
            MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(m_strItems[i]);
            if(info == null)
                continue;
            String strName = info.name;
            if(m_bShowSortTag && strSortItem.equals(m_strItems[i])) {
                if(isDescend == 1) {
                    g.setColor(Application.rhColor.clIncrease);
                    strName = strName + "\u2193";
                } else {
                    g.setColor(Application.rhColor.clHighlight);
                    strName = strName + "\u2191";
                }
            } else {
                g.setColor(Application.rhColor.clItem);
            }
            x += info.width;
            int strLen = fm.stringWidth(strName);
            g.drawString(strName, x - strLen, y);
            if(x > super.m_rc.x + super.m_rc.width)
                break;
        }

    }

    void paintQuoteData(Graphics g) {
        if(bShowUserStock && iUserStockCode.length == 0) {
            paintPromptMessage(g);
            return;
        }
        if(packetInfo == null || quoteData == null)
            return;
        g.setFont(font);
        if(!bShowUserStock && (packetInfo.iStart != iStart || packetInfo.isDescend != isDescend || packetInfo.sortBy != getSortByField(strSortItem) || packetInfo.currentStockType != currentStockType))
            return;
        if(bShowUserStock) {
            Arrays.sort(quoteData, strSortItem);
            if(isDescend == 0) {
                int size = quoteData.length;
                int count = size / 2;
                for(int i = 0; i < count; i++) {
                    ProductDataVO tmp = quoteData[i];
                    quoteData[i] = quoteData[size - i - 1];
                    quoteData[size - i - 1] = tmp;
                }

            }
        }
        int gap = 2;
        int x = super.m_rc.x;
        int y = super.m_rc.y + fontHeight + 2 + fm.getAscent();
        int count = iStockRows;
        if(quoteData.length < iStockRows)
            count = quoteData.length;
        for(int i = 0; i < count; i++) {
            int num = iStart + i;
            ProductDataVO data = quoteData[i];
            if(data.code == null) {
                logger.info("Code = null");
            } else {
                CodeTable stockTable = (CodeTable)super.application.m_ProductByHttp.get(data.code);
                String stockName = "-";
                if(stockTable != null) {
                    if(stockTable.sName != null)
                        stockName = stockTable.sName;
                    else
                        System.out.println("stockTable.sName = null");
                } else {
                    System.out.println(" stockTable = null ");
                }
                int iPrecision = super.application.getPrecision(data.code);
                float close = data.yesterBalancePrice;
                int strLen = 0;
                for(int j = 0; j < m_strItems.length; j++) {
                    if(j > m_iStaticIndex && j < iDynamicIndex)
                        continue;
                    MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(m_strItems[j]);
                    if(info == null)
                        continue;
                    x += info.width;
                    if(m_strItems[j].equals("No")) {
                        String strText = String.valueOf(num);
                        g.setColor(Application.rhColor.clNumber);
                        strLen = fm.stringWidth(strText);
                        g.drawString(strText, x - strLen, y);
                    } else
                    if(m_strItems[j].equals("Name")) {
                        String strText = stockName;
                        g.setColor(Application.rhColor.clProductName);
                        strLen = fm.stringWidth(strText);
                        g.drawString(strText, x - strLen, y);
                    } else
                    if(m_strItems[j].equals("Code")) {
                        String strText = data.code;
                        g.setColor(Application.rhColor.clProductName);
                        strLen = fm.stringWidth(strText);
                        g.drawString(strText, x - strLen, y);
                    } else
                    if(m_strItems[j].equals("CurPrice"))
                        paintNumber(g, data.curPrice, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("Balance"))
                        paintNumber(g, data.balancePrice, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("UpValue")) {
                        float up = close == 0.0F || data.curPrice == 0.0F ? 0.0F : data.curPrice - close;
                        paintNumber(g, up, "", m_strItems[j], 0, close, x, y);
                    } else
                    if(m_strItems[j].equals("UpRate")) {
                        float upRate = close <= 0.0F || data.curPrice <= 0.0F ? 0.0F : ((data.curPrice - close) / close) * 100F;
                        paintNumber(g, upRate, "", m_strItems[j], 2, 0.0F, x, y);
                    } else
                    if(m_strItems[j].equals("YesterBalance"))
                        paintNumber(g, data.yesterBalancePrice, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("OpenPrice"))
                        paintNumber(g, data.openPrice, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("BuyPrice"))
                        paintNumber(g, data.buyPrice[0], "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("SellPrice"))
                        paintNumber(g, data.sellPrice[0], "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("BuyAmount"))
                        paintNumber(g, data.buyAmount[0], "", m_strItems[j], 0, close, x, y);
                    else
                    if(m_strItems[j].equals("SellAmount"))
                        paintNumber(g, data.sellAmount[0], "", m_strItems[j], 0, close, x, y);
                    else
                    if(m_strItems[j].equals("HighPrice"))
                        paintNumber(g, data.highPrice, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("LowPrice"))
                        paintNumber(g, data.lowPrice, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("TotalAmount"))
                        paintNumber(g, data.totalAmount, "", m_strItems[j], 0, close, x, y);
                    else
                    if(m_strItems[j].equals("TotalMoney"))
                        paintNumber(g, data.totalMoney, "", m_strItems[j], iPrecision, close, x, y);
                    else
                    if(m_strItems[j].equals("ReverseCount"))
                        paintNumber(g, data.reserveCount, "", m_strItems[j], 0, close, x, y);
                    else
                    if(m_strItems[j].equals("CurAmount"))
                        paintNumber(g, data.curAmount, "", m_strItems[j], 0, close, x, y);
                    else
                    if(m_strItems[j].equals("AmountRate"))
                        paintNumber(g, data.amountRate, "", m_strItems[j], 2, close, x, y);
                    else
                    if(m_strItems[j].equals("ConsignRate"))
                        paintNumber(g, data.consignRate, "", m_strItems[j], 2, close, x, y);
                    else
                    if(m_strItems[j].equals("Unit") && super.application.m_strMarketName.equalsIgnoreCase("Anhui")) {
                        String strText;
                        if(data.code.startsWith("G"))
                            strText = "单";
                        else
                        if(data.code.startsWith("X"))
                            strText = "批";
                        else
                            strText = "";
                        g.setColor(Application.rhColor.clProductName);
                        strLen = fm.stringWidth(strText);
                        g.drawString(strText, x - strLen, y);
                    }
                    if(x > super.m_rc.x + super.m_rc.width)
                        break;
                }

                x = super.m_rc.x;
                y += fontHeight + gap;
            }
        }

    }
    
    private void paintPromptMessage(Graphics g) {
        String prompt = "请用注册账户登录后在页面中设定自选股";
        Font font = new Font("\u5B8B\u4F53", 0, 16);
        FontMetrics fm = g.getFontMetrics(font);
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

    void paintNumber(Graphics g, double num, String strSuffix, String itemName, int iPrecision, float close, 
            int x, int y) {
        StringBuffer buffer = new StringBuffer();
        if(itemName.equals("TotalAmount") || itemName.equals("CurAmount") || itemName.equals("BuyAmount") || itemName.equals("SellAmount") || itemName.equals("AmountRate"))
            g.setColor(Application.rhColor.clVolume);
        else
        if(itemName.equals("ReverseCount"))
            g.setColor(Application.rhColor.clReserve);
        else
        if(itemName.equals("TotalMoney"))
            g.setColor(Application.rhColor.clNumber);
        else
        if(itemName.equals("ConsignRate"))
            g.setColor(Application.rhColor.clNumber);
        else
        if(itemName.equals("YesterBalance"))
            g.setColor(Application.rhColor.clEqual);
        else
        if(itemName.equals("UpValue")) {
            if(num > 0.0D) {
                buffer.append("+");
                g.setColor(Application.rhColor.clIncrease);
            } else
            if(num == 0.0D)
                g.setColor(Application.rhColor.clEqual);
            else
                g.setColor(Application.rhColor.clDecrease);
        } else
        if(num > (double)close)
            g.setColor(Application.rhColor.clIncrease);
        else
        if(num == (double)close || num == 0.0D)
            g.setColor(Application.rhColor.clEqual);
        else
            g.setColor(Application.rhColor.clDecrease);
        if(itemName.equals("UpRate")) {
            if(num == -100D || num == 0.0D) {
                buffer.append("—");
            } else {
                buffer.append(Common.FloatToString(num, iPrecision));
                buffer.append("%");
            }
        } else
        if(num == 0.0D)
            buffer.append("—");
        else
            buffer.append(Common.FloatToString(num, iPrecision));
        buffer.append(strSuffix);
        int strLen = fm.stringWidth(buffer.toString());
        g.drawString(buffer.toString(), x - strLen, y);
    }

    void paintHighlightBar() {
        if(quoteData != null && iHighlightIndex > quoteData.length) {
            iHighlightIndex = quoteData.length;
            if(iHighlightIndex < 1)
                iHighlightIndex = 1;
        }
        Graphics g = super.application.getGraphics();
        int gap = 2;
        int oldY = super.m_rc.y + iHighlightIndex * (fontHeight + gap);
        g.setColor(Application.rhColor.clBackGround);
        g.setXORMode(Application.rhColor.clHighlight);
        g.fillRect(super.m_rc.x, oldY, super.m_rc.width, fontHeight);
        g.setPaintMode();
    }

    void repaintHighlightBar(int iNewPos) {
        Graphics m_graphics = super.application.getGraphics();
        int gap = 2;
        int oldY = super.m_rc.y + iHighlightIndex * (fontHeight + gap);
        int newY = super.m_rc.y + iNewPos * (fontHeight + gap);
        m_graphics.setColor(Application.rhColor.clBackGround);
        m_graphics.setXORMode(Application.rhColor.clHighlight);
        m_graphics.fillRect(super.m_rc.x, oldY, super.m_rc.width, fontHeight);
        m_graphics.fillRect(super.m_rc.x, newY, super.m_rc.width, fontHeight);
        iHighlightIndex = iNewPos;
        m_graphics.setPaintMode();
    }

    boolean keyPressed(KeyEvent e) {
        int iKeyCode = e.getKeyCode();
        boolean bNeedRepaint = false;
        switch(iKeyCode) {
        case 38: // '&'
            bNeedRepaint = Key_UP_Pressed();
            break;

        case 40: // '('
            bNeedRepaint = Key_DOWN_Pressed();
            break;

        case 37: // '%'
            bNeedRepaint = Key_LEFT_Pressed();
            break;

        case 39: // '\''
            bNeedRepaint = Key_RIGHT_Pressed();
            break;

        case 34: // '"'
            bNeedRepaint = Key_PAGEDOWN_Pressed();
            break;

        case 33: // '!'
            bNeedRepaint = Key_PAGEUP_Pressed();
            break;

        case 10: // '\n'
            bNeedRepaint = Key_ENTER_Pressed();
            break;
        }
        return bNeedRepaint;
    }

    boolean mouseLeftClicked(int x, int y) {
        if(y > fontHeight) {
            selectProduct(x, y);
        } else {
            int iLeft = super.m_rc.x;
            for(int i = 0; i < m_strItems.length; i++) {
                if(i > m_iStaticIndex && i < iDynamicIndex)
                    continue;
                MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(m_strItems[i]);
                if(info == null)
                    continue;
                if(x > iLeft && x < iLeft + info.width) {
                    changeSortField(m_strItems[i]);
                    break;
                }
                iLeft += info.width;
                if(iLeft > super.m_rc.x + super.m_rc.width)
                    break;
            }

        }
        return false;
    }

    private void selectProduct(int x, int y) {
        if(packetInfo != null && quoteData != null) {
            int gap = 2;
            int xx = super.m_rc.x;
            int yy = super.m_rc.y + fontHeight + gap;
            int count = quoteData.length;
            if(count > iStockRows)
                count = iStockRows;
            for(int i = 0; i < count; i++) {
                if(x > xx && x < xx + super.m_rc.width && y > yy && y < yy + fontHeight + gap) {
                    if(i + 1 != iHighlightIndex)
                        repaintHighlightBar(i + 1);
                    break;
                }
                yy += fontHeight + gap;
            }

        }
    }

    boolean mouseLeftDblClicked(int x, int y) {
        if(packetInfo != null && quoteData != null) {
            int gap = 2;
            int xx = super.m_rc.x;
            int yy = super.m_rc.y + fontHeight;
            int count = iStockRows;
            if(iStockRows > quoteData.length)
                count = quoteData.length;
            for(int i = 0; i < count; i++) {
                if(x > xx && x < xx + super.m_rc.width && y > yy && y < yy + fontHeight) {
                    String sCode = quoteData[i].code;
                    super.application.queryStock(sCode);
                    return true;
                }
                yy += fontHeight + gap;
            }

        }
        return false;
    }

    boolean mouseMoved(int x, int y) {
        if(y <= 0 || y >= fontHeight) {
            super.application.setCursor(new Cursor(0));
            return false;
        }
        int iLeft = super.m_rc.x;
        for(int i = 0; i < m_strItems.length; i++) {
            if(i > m_iStaticIndex && i < iDynamicIndex)
                continue;
            MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(m_strItems[i]);
            if(info == null)
                continue;
            if(x > iLeft && x < iLeft + info.width && info.sortID != -1) {
                super.application.setCursor(new Cursor(12));
                return false;
            }
            iLeft += info.width;
            if(iLeft > super.m_rc.x + super.m_rc.width)
                break;
        }

        super.application.setCursor(new Cursor(0));
        return false;
    }

    boolean Key_UP_Pressed() {
        if(!bCanMove)
            return false;
        if(currentStockType == 0) {
            iUserStockKey_Up();
            return false;
        }
        if(quoteData != null && packetInfo != null)
            if(iHighlightIndex > 1) {
                repaintHighlightBar(iHighlightIndex - 1);
            } else {
                int pageSize = iStockRows;
                if(quoteData.length < iStockRows)
                    pageSize = quoteData.length;
                if(iStart > 1) {
                    iEnd = iStart;
                    iStart = (iEnd - iStockRows) + 1;
                    if(iStart < 0)
                        iStart = 1;
                    iEnd = (iStart + iStockRows) - 1;
                    iHighlightIndex = iStockRows;
                    if(iEnd > packetInfo.iCount) {
                        iEnd = packetInfo.iCount;
                        iHighlightIndex = (iEnd - iStart) + 1;
                    }
                    askForDataOnTimer();
                    bCanMove = false;
                }
            }
        return false;
    }

    private void iUserStockKey_Up() {
        if(quoteData == null)
            return;
        if(flag == 0) {
            backQuoteData = quoteData;
            flag = 1;
        }
        if(iHighlightIndex > 1) {
            repaintHighlightBar(iHighlightIndex - 1);
            Application _tmp = super.application;
            if(bDebug)
                System.out.println("iHighlightIndex - 1 = " + (iHighlightIndex - 1));
            return;
        }
        int pageSize = iStockRows;
        if(backQuoteData.length < iStockRows)
            pageSize = backQuoteData.length;
        if(iStart > 1) {
            iEnd = iStart;
            if(pageSize == 1)
                iStart = iStart - pageSize;
            else
                iStart = (iStart - pageSize) + 1;
            if(iStart < 0)
                iStart = 1;
            iEnd = (iStart + iStockRows) - 1;
            iHighlightIndex = iStockRows;
            if(iEnd > backQuoteData.length) {
                iEnd = backQuoteData.length;
                iHighlightIndex = (iEnd - iStart) + 1;
            }
            int len = (iEnd - iStart) + 1;
            if(len > backQuoteData.length - iStart)
                len = backQuoteData.length - iStart;
            Application _tmp1 = super.application;
            if(bDebug)
                System.out.println(" len = " + len);
            String StockCode[] = new String[len];
            int i = 0;
            for(int j = iStart - 1; i < len; j++) {
                StockCode[i] = backQuoteData[j].code;
                i++;
            }

            iUserStockCode = StockCode;
            askForDataOnTimer();
            bCanMove = false;
        }
    }

    boolean Key_DOWN_Pressed() {
        if(!bCanMove)
            return false;
        if(currentStockType == 0) {
            iUserStockKey_DOWN();
            return false;
        }
        if(quoteData != null && packetInfo != null) {
            int lastStockNum = packetInfo.iCount;
            int firstStockNumPerPage = packetInfo.iStart;
            int pageSize = iStockRows;
            if(quoteData.length < iStockRows)
                pageSize = quoteData.length;
            if(iHighlightIndex < pageSize)
                repaintHighlightBar(iHighlightIndex + 1);
            else
            if((iStart + pageSize) - 1 < lastStockNum) {
                iStart += pageSize - 1;
                iEnd = (iStart + iStockRows) - 1;
                if(iEnd > lastStockNum)
                    iEnd = lastStockNum;
                iHighlightIndex = 1;
                askForDataOnTimer();
                bCanMove = false;
            }
        }
        return false;
    }

    private void iUserStockKey_DOWN() {
        if(quoteData == null)
            return;
        if(flag == 0) {
            backQuoteData = quoteData;
            flag = 1;
        }
        int lastStockNum = backQuoteData.length;
        int pageSize = iStockRows;
        if(backQuoteData.length < iStockRows)
            pageSize = quoteData.length;
        if(iHighlightIndex < pageSize)
            repaintHighlightBar(iHighlightIndex + 1);
        else
        if((iStart + pageSize) - 1 < lastStockNum) {
            if(pageSize == 1) {
                iStart = iStart + pageSize;
                iEnd = iStart + iStockRows;
            } else {
                iStart = (iStart + pageSize) - 1;
                iEnd = (iStart + iStockRows) - 1;
            }
            if(iEnd > lastStockNum)
                iEnd = lastStockNum;
            iHighlightIndex = 1;
            int len = (iEnd - iStart) + 1;
            Application _tmp = super.application;
            if(bDebug)
                System.out.println(" len = " + len);
            String StockCode[] = new String[len];
            int i = 0;
            for(int j = iStart - 1; i < len; j++) {
                StockCode[i] = backQuoteData[j].code;
                Application _tmp1 = super.application;
                if(bDebug)
                    System.out.println(" StockCode[i] = " + StockCode[i]);
                i++;
            }

            iUserStockCode = StockCode;
            askForDataOnTimer();
            bCanMove = false;
        }
    }

    boolean Key_PAGEUP_Pressed() {
        if(currentStockType == 0) {
            iUserStockPageUp();
            return false;
        }
        int pageSize = iStockRows;
        if(quoteData != null && packetInfo != null) {
            int lastStockNum = packetInfo.iCount;
            int firstStockNumPerPage = packetInfo.iStart;
            if(firstStockNumPerPage > 1) {
                iEnd = iStart;
                if(pageSize == 1)
                    iStart = iStart - pageSize;
                else
                    iStart = (iStart - pageSize) + 1;
                if(iStart < 0)
                    iStart = 1;
                iEnd = (iStart + iStockRows) - 1;
                askForDataOnTimer();
            }
        }
        return false;
    }

    private void iUserStockPageUp() {
        if(quoteData == null)
            return;
        int pageSize = iStockRows;
        if(iStart > 1) {
            iEnd = iStart;
            if(pageSize == 1)
                iStart = iStart - pageSize;
            else
                iStart = (iStart - pageSize) + 1;
            Application _tmp = super.application;
            if(bDebug) {
                System.out.println("this.iStart = " + iStart);
                System.out.println("this.iEnd = " + iEnd);
            }
            if(iStart < 0)
                iStart = 1;
            iEnd = (iStart + pageSize) - 1;
            int len = (iEnd - iStart) + 1;
            if(len > backQuoteData.length - iStart)
                len = backQuoteData.length - iStart;
            Application _tmp1 = super.application;
            if(bDebug)
                System.out.println(" len = " + len);
            String StockCode[] = new String[len];
            int i = 0;
            for(int j = iStart - 1; i < len; j++) {
                StockCode[i] = backQuoteData[j].code;
                i++;
            }

            iUserStockCode = StockCode;
            askForDataOnTimer();
        }
    }

    boolean Key_PAGEDOWN_Pressed() {
        if(currentStockType == 0) {
            iUserStockPageDown();
            return false;
        }
        if(quoteData != null && packetInfo != null) {
            int lastStockNum = packetInfo.iCount;
            int pageSize = iStockRows;
            if(iStockRows > quoteData.length)
                pageSize = quoteData.length;
            if((iStart + pageSize) - 1 < lastStockNum) {
                if(pageSize == 1) {
                    iStart = iStart + pageSize;
                    iEnd = iStart + iStockRows;
                } else {
                    iStart = (iStart + pageSize) - 1;
                    iEnd = (iStart + iStockRows) - 1;
                }
                if(iEnd > lastStockNum)
                    iEnd = lastStockNum;
                if(iHighlightIndex > (lastStockNum - iStart) + 1)
                    iHighlightIndex = 1;
                askForDataOnTimer();
            } else {
                System.out.println(" NO PAGE");
            }
        } else {
            System.out.println(" No data ");
        }
        return false;
    }

    private void iUserStockPageDown() {
        if(quoteData == null)
            return;
        if(flag == 0) {
            backQuoteData = quoteData;
            flag = 1;
        }
        int lastStockNum = backQuoteData.length;
        int pageSize = iStockRows;
        System.out.println(" backQuoteData.length = " + backQuoteData.length);
        if(iStockRows > backQuoteData.length)
            pageSize = backQuoteData.length;
        System.out.println(" pageSize = " + pageSize);
        if((iStart + pageSize) - 1 < lastStockNum) {
            if(pageSize == 1) {
                iStart = iStart + pageSize;
                iEnd = iStart + iStockRows;
            } else {
                iStart = (iStart + pageSize) - 1;
                iEnd = (iStart + iStockRows) - 1;
            }
            if(iEnd > lastStockNum)
                iEnd = lastStockNum;
            if(iHighlightIndex > (lastStockNum - iStart) + 1)
                iHighlightIndex = 1;
            int len = (iEnd - iStart) + 1;
            System.out.println(" len = " + len);
            String StockCode[] = new String[len];
            int i = 0;
            for(int j = iStart - 1; i < len; j++) {
                StockCode[i] = backQuoteData[j].code;
                System.out.println(" StockCode[i] = " + StockCode[i]);
                i++;
            }

            iUserStockCode = StockCode;
            askForDataOnTimer();
        }
    }

    boolean Key_LEFT_Pressed() {
        if(iDynamicIndex == m_iStaticIndex + 1) {
            return false;
        } else {
            iDynamicIndex--;
            return true;
        }
    }

    boolean Key_RIGHT_Pressed() {
        boolean bNeedRepaint = false;
        if(iDynamicIndex < m_strItems.length - 1) {
            iDynamicIndex++;
            bNeedRepaint = true;
        }
        return bNeedRepaint;
    }

    boolean Key_ENTER_Pressed() {
        if(quoteData != null && packetInfo != null && iHighlightIndex > 0 && iHighlightIndex <= quoteData.length) {
            String sCode = quoteData[iHighlightIndex - 1].code;
            super.application.queryStock(sCode);
            return true;
        } else {
            return false;
        }
    }

    byte getSortByField(String strSortItem) {
        MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(strSortItem);
        if(info == null)
            return 0;
        else
            return (byte)info.sortID;
    }

    void printQuoteData() {
        if(quoteData == null) {
            System.err.println("QuoteData is NULL!!!");
            return;
        }
        for(int i = 0; i < quoteData.length; i++) {
            ProductDataVO stock = quoteData[i];
            float uprate = ((stock.curPrice - stock.yesterBalancePrice) / stock.yesterBalancePrice) * 100F;
            System.err.println(i + "\t\u6DA8\u5E45" + "\t\u6700\u65B0" + "\t\u524D\u6536" + "\t\u5F00\u76D8" + "\t\u6700\u9AD8" + "\t\u6700\u4F4E" + "\t\u6210\u4EA4\u91CF" + "\t\u6210\u4EA4\u91D1\u989D" + "\t\u73B0\u624B" + "\t\u91CF\u6BD4" + "\t\u59D4\u6BD4");
            System.out.print("\t" + uprate + "%");
            System.out.print("\t" + stock.curPrice);
            System.out.print("\t" + stock.closePrice);
            System.out.print("\t" + stock.openPrice);
            System.out.print("\t" + stock.highPrice);
            System.out.print("\t" + stock.lowPrice);
            System.out.print("\t" + stock.totalAmount);
            System.out.print("\t" + stock.totalMoney);
            System.out.print("\t" + stock.curAmount);
            System.out.print("\t" + stock.amountRate);
            System.out.print("\t" + stock.consignRate);
        }

    }

    void printPacketInfo() {
        if(packetInfo == null) {
            System.err.println("PacketInfo is NULL!!!");
            return;
        } else {
            return;
        }
    }

    void processMenuEvent(PopupMenu popupMenu, int x, int y) {
        selectProduct(x, y);
        popupMenu.removeAll();
        popupMenu.add(menuStockRank);
        popupMenu.addSeparator();
        popupMenu.add(menuPageMinLine);
        popupMenu.add(menuPageKLine);
        popupMenu.addSeparator();
        popupMenu.add(menuMarket);
        processCommonMenuEvent(popupMenu, this);
        popupMenu.show(super.application, x, y);
    }

    void makeMenus() {
       menuStockRank = new Menu(super.application.getShowString("SortBy"));    
        menuMarket = new MenuItem(super.application.getShowString("ClassedList") + "  F4");
        menuPageMinLine = new MenuItem(super.application.getShowString("MinLine") + "  F5");
        menuPageKLine = new MenuItem(super.application.getShowString("Analysis"));
        menuMarket.setActionCommand("cmd_80");
        menuMarket.addActionListener(this);
        for(int i = 0; i < m_strItems.length; i++) {
            MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(m_strItems[i]);
            if(info != null && info.sortID != -1) {
                MenuItem menuItem = new MenuItem(info.name);
                menuItem.setActionCommand("Sort_" + m_strItems[i]);
                menuItem.addActionListener(this);
                menuStockRank.add(menuItem);
            }
        }

        menuPageMinLine.setActionCommand("minline");
        menuPageMinLine.addActionListener(this);
        menuPageKLine.setActionCommand("kline");
        menuPageKLine.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.indexOf("cmd_") >= 0)
            executeCommand();
        else
        if(cmd.indexOf("Sort_") >= 0) {
            changeSortField(cmd.substring(5));
            if(bDebug)
                System.out.println("cmd ====" + cmd);
        } else
        if(cmd.indexOf("STOCK_") >= 0)
            super.application.showMutilQuote(getStockType(cmd));
        else
        if(cmd.equals("minline")) {
            if(iHighlightIndex > 0 && iHighlightIndex <= quoteData.length) {
                ProductDataVO stockData = quoteData[iHighlightIndex - 1];
                String scode = String.valueOf(stockData.code);
                super.application.showPageMinLine(scode);
            }
        } else
        if(cmd.equals("kline")) {
            if(iHighlightIndex > 0 && iHighlightIndex <= quoteData.length) {
                ProductDataVO stockData = quoteData[iHighlightIndex - 1];
                String scode = String.valueOf(stockData.code);
                super.application.showPageKLine(scode);
            }
        } else
        if(cmd.equals("userstock"))
            super.application.showMutilQuote((byte)0);
        else
            super.actionPerformed(e);
    }

    private void changeSortField(String strSortItem) {
        MultiQuoteItemInfo info = (MultiQuoteItemInfo)m_htItemInfo.get(strSortItem);
        if(info == null)
            return;
        if(info.sortID == -1)
            return;
        m_bShowSortTag = true;
        if(this.strSortItem.equals(strSortItem)) {
            isDescend = ((byte)(isDescend != 1 ? 1 : 0));
        } else {
            isDescend = 0;
            sortBy = (byte)info.sortID;
            this.strSortItem = strSortItem;
        }
        quoteData = backQuoteData;
        askForDataOnTimer();
    }

    private byte getStockType(String name) {
        return ((byte)(!name.equals("PRODUCT_COMMON") ? -1 : 0));
    }

    void setMenuEnable(byte stockType, boolean b) {
        switch(stockType) {
        case 0: // '\0'
        default:
            return;
        }
    }

    private void setUserStockCode() {
    }

    private void initUserStockArray() {
        int size = iUserStockCode.length;
        quoteData = new ProductDataVO[size];
        for(int i = 0; i < size; i++) {
            quoteData[i] = new ProductDataVO();
            quoteData[i].code = iUserStockCode[i];
        }

    }

    private void executeCommand() {
        switch(currentStockType) {
        case 1: // '\001'
            super.application.userCommand("80");
            break;
        }
    }

}
