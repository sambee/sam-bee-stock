// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   HQApplet.java

package sam.bee.stock.gui;

import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

//import sam.bee.stock.event.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.stock.event.SendCommand;
import sam.bee.stock.trade.Market;
import sam.bee.stock.vo.TradeTimeVO;

// www.trinnion.com/javacodeviewer


// Referenced classes of package gnnt.MEBS.HQApplet:
// ProductData, HttpThread, SendThread, ReceiveThread,
// RHColor, Page_Bottom, InputDialog, Page_KLine,
// Draw_KLine, Page_Main, Common, Page_MarketStatus,
// Page_MultiQuote, CodeTable, Page_MinLine, AboutDialog,
// Page_Bill, Page_History

public class Application extends Applet implements FocusListener {

	private static final long serialVersionUID = 0x5ba23793a3c0f861L;
	protected static final Logger logger = LoggerFactory.getLogger(Application.class);
	private boolean isStandalone;
    private static Application application;
    /** */ public Market market;
	
	/** 列表图　*/static final int PAGE_MULTIQUOTE = 0;
	/** 分钟线索引号 */ static final int PAGE_MINLINE = 1;
	/**　Ｋ线号　*/static final int PAGE_KLINE = 2;
	/**　*/ //private static final int PAGE_F10 = 3;
	/**　交易　*/static final int PAGE_BILL = 4;
	/**　市场状态　*/static final int PAGE_MARKETSTATUS = 5;
	/**　历史索引号　*/static final int PAGE_HISTORY = 6;
	
	
	public int iCurrentPage;
	public String strCurrentCode;
	public String indexMainCode;
	int m_iKLineCycle;
	String m_strIndicator;
	
	/**
	 * 当前更新日期
	 */
	public  int m_iCodeDate;
	
	/**
	 * 当前更新时间
	 */
	public int m_iCodeTime;
	
	int m_iDate;
	int m_iTime;
	// TradeTimeVO m_timeRange[];
	public TradeTimeVO m_timeRange[];
	public  int m_iMinLineInterval;
	
	/**
	 * 码表
	 */
	@SuppressWarnings("rawtypes")	public Vector m_codeList;
	
	/**
	 * 码表
	 */
	@SuppressWarnings("rawtypes")	public Hashtable m_ProductByHttp;
	
	/**
	 * 成交明细
	 */
	@SuppressWarnings("rawtypes")	public  Vector vProductData;

	private Rectangle m_rcMain;
	private Rectangle m_rcBottom;
	public BasicPage mainGraph;
	Page_Bottom bottomGraph;
	public static RHColor rhColor = null;


//	Socket socket;
//	SendThread sendThread;
//	ReceiveThread receiveThread;
	SendCommand borker;
	//HttpThread httpThread;
	//HttpProxy httpThread;
	
	public boolean bRunning;
	
	PopupMenu popupMenu;
	
	public static boolean bDebug = true;
	
	int iShowBuySellPrice;
	
	String m_strMarketName;
	
	public int m_bShowIndexAtBottom;
	
	int m_bShowIndexKLine;
	
	/** 保存小数位 */ int m_iPrecision;
	
	int m_iPrecisionIndex;
	
	/** 系统资源，　配置放在.\/rc目录下 */ ResourceBundle m_resourceBundle;
	
	/** 系统语言，默认是中文　*/ String strLanguageName;
	
	boolean bInputDlgShow;
	boolean bAboutDlgShow;
	private boolean m_bEndPaint;
	private Image m_img;

	public String getParameter(String key, String def) {
		return isStandalone ? System.getProperty(key, def)
				: getParameter(key) == null ? def : getParameter(key);
	}

    /**
     *
     * @return
     */
    public static Application getInstance(){
        if(application==null){
            try {
                application = new Application();
                application.market = new Market();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return  application;
    }

    /**
     *
     * @param args
     * @throws Exception
     */
	public static void main(String args[]) throws Exception {

		final Application application = getInstance();

		application.isStandalone = true;
		Frame frame = new Frame(){
            private static final long serialVersionUID = 1L;
			protected void processWindowEvent(WindowEvent e) {
			super.processWindowEvent(e);
				if (e.getID() == 201) {
					application.destroy();
					System.exit(0);
				}
			}

			public synchronized void setTitle(String title) {
				super.setTitle(title);
				enableEvents(AWTEvent.WINDOW_EVENT_MASK);
			}
		};
		frame.setTitle("Applet Frame");
		frame.add(application, "Center");
		frame.setSize(800, 600);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMaximumSize(d);
		frame.setAlwaysOnTop(true);
		//frame.setSize(d.width, d.height);
		frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
		frame.setVisible(true);
		application.init();
		application.start();
		logger.info("------ " + application.getAppletInfo());
	}

	public ProductData getProductData(String code) {
		for (int i = 0; i < vProductData.size(); i++) {
			logger.info("getProductData" + ((ProductData) vProductData.elementAt(i)));
			if (((ProductData) vProductData.elementAt(i)).sCode.equals(code))
				return (ProductData) vProductData.elementAt(i);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Application() throws Exception {
		isStandalone = false;
		iCurrentPage = -1;
		strCurrentCode = "";
		indexMainCode = "";
		m_iKLineCycle = 1;
		m_strIndicator = "ORDER";
		m_iDate = 0;
		m_iTime = 0;
		m_timeRange = null;
		m_iMinLineInterval = 60;
		m_codeList = new Vector();
		m_ProductByHttp = new Hashtable();
		vProductData = new Vector();
		m_rcMain = null;
		m_rcBottom = null;
		mainGraph = null;
		bottomGraph = null;
//		socket = null;
		bRunning = true;
		popupMenu = new PopupMenu();
		m_strMarketName = "";
		m_bShowIndexAtBottom = 1;
		m_bShowIndexKLine = 0;
		m_iPrecision = 0;
		m_iPrecisionIndex = 2;
		strLanguageName = "cn";
		bInputDlgShow = false;
		bAboutDlgShow = false;
	}

	public void init() {
		bRunning = true;
		try {
			jbInit();
		} catch (Exception e) {
			if (bDebug)
				e.printStackTrace();
		}
		if (bDebug){
			logger.info("Application starting... ");
		}
	}

	private void jbInit() throws Exception {
		
		//run by main
		if (isStandalone) {

			bDebug = true;
			iShowBuySellPrice = 1;
		} else {
		//run by applet
//			URL url = getDocumentBase();

			bDebug = Integer.parseInt(getParameter("Debug", "0"))==1;
			iShowBuySellPrice = Integer.parseInt(getParameter("ShowBuySell","3"));
			if (iShowBuySellPrice > 5 || iShowBuySellPrice <= 0){
				iShowBuySellPrice = 3;
			}
			m_strMarketName = getParameter("MarketName", "");
			m_bShowIndexAtBottom = Integer.parseInt(getParameter("ShowIndexAtBottom", "1"));
			strLanguageName = getParameter("Language", "cn");
			m_iPrecision = Integer.parseInt(getParameter("Precision", "0"));
			m_iPrecisionIndex = Integer.parseInt(getParameter("IndexPrecision", "2"));
			m_bShowIndexKLine = Integer.parseInt(getParameter("ShowIndexKLine", "0"));
		}
		try {
			m_resourceBundle = ResourceBundle.getBundle("rc/string", new Locale(strLanguageName, ""));
		} catch (Exception e) {
			logger.info("Language resource loaded failed !");
			e.printStackTrace();
		}
		m_rcMain = null;
		
//		(new HttpThread(GET_CODE_LIST, this)).start();
//		sendThread = new SendThread(this);
//		sendThread.start();
//		receiveThread = new ReceiveThread(this);
//		receiveThread.start();
		borker = new SendCommand(this);
		borker.start();
		//httpThread = new HttpThread(1, this);
		//httpThread.start();
		//httpThread = HttpProxy.getInstance(this);
		
		String strColorStyle = getParameter("ColorStyle", "0");
		rhColor = new RHColor(Integer.parseInt(strColorStyle));
		setBackground(rhColor.clBackGround);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Application.this.this_componentResized(e);
			}
		}
);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int iKey = e.getKeyCode();
				if (iKey != 33 && iKey != 34)
					Application.this.this_keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				int iKey = e.getKeyCode();
				if (iKey == 33 || iKey == 34)
					Application.this.this_keyPressed(e);
			}
		});
		
		addMouseListener(new  MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getModifiers() != 4 && e.getClickCount() == 1)
					Application.this.this_mouseLeftPressed(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getModifiers() == 4)
					Application.this.this_mouseRightReleased(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getModifiers() != 4 && e.getClickCount() > 1)
					Application.this.this_mouseLeftDblClicked(e);
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				Application.this.this_mouseMoved(e);
			}

			@Override
			public void mouseDragged(MouseEvent e){
				if (e.getModifiers() != 4)
					Application.this.this_mouseDragged(e);
			}
		});
		addFocusListener(this);
		this_componentResized(null);
		bottomGraph = new Page_Bottom(getGraphics(), m_rcBottom, this);
		requestFocus();
		add(popupMenu);
		popupMenu.addActionListener(mainGraph);
		strCurrentCode = getParameter("CurrentCode", "");
		if (strCurrentCode.length() == 0) {
			userCommand("60");
		} else {
			String strPage = getParameter("CurrentPage", "MinLinePage");
			if (strPage.equalsIgnoreCase("MinLine"))
				showPageMinLine();
			else if (strPage.equalsIgnoreCase("KLine")) {
				showPageMinLine();
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException interruptedexception) {
				}
				showPageKLine();
			} else {
				userCommand("60");
			}
		}
	}

	public String getAppletInfo() {
		return m_resourceBundle.getString("AppletName") + m_resourceBundle.getString("Version") ;
	}

	public String[][] getParameterInfo() {
		return null;
	}

	public Frame getParentFrame(Component c) {
		Frame mainFrame = null;
		Container dad;
		for (dad = c.getParent(); !(dad instanceof Frame) && dad != null; dad = dad
				.getParent())
			;
		if (dad instanceof Frame)
			mainFrame = (Frame) dad;
		return mainFrame;
	}

	@SuppressWarnings("deprecation")
	void this_keyPressed(KeyEvent e) {
		if (bInputDlgShow)
			return;
		char ch = e.getKeyChar();
		if (Character.isLetterOrDigit(ch)) {
			Frame f = getParentFrame(this);
			Rectangle rc = getBounds();
			Point p = getLocationOnScreen();
			int x = (p.x + rc.width) - 125;
			int y = (p.y + rc.height) - 160;
			InputDialog dlg = new InputDialog(f, ch, this);
			dlg.setBounds(x, y, 125, 160);
			bInputDlgShow = true;
			dlg.show();
			bInputDlgShow = false;
			String str = dlg.strCmd;
			if (str == null || str.length() == 0)
				return;
			switch (str.charAt(0)) {
			case 65: // 'A'
				userCommand(str.substring(1));
				break;

			case 84: // 'T'
				m_strIndicator = str.substring(1);
				((Page_KLine) mainGraph).draw_KLine.CreateIndicator();
				repaint();
				break;

			case 67: // 'C'
				rhColor = new RHColor(str.charAt(1) - 48);
				setBackground(rhColor.clBackGround);
				break;

			case 80: // 'P'
				queryStock(str.substring(1));
				break;
			}
			repaint();
			return;
		}
		boolean bNeedRepaint = true;
		int iKey = e.getKeyCode();
		switch (iKey) {
		case 27: // '\033'
			if (mainGraph != null) {
				bNeedRepaint = mainGraph.keyPressed(e);
				if (!bNeedRepaint && iCurrentPage != 0) {
					byte type = (byte) Common.GetProductType(strCurrentCode);
					if (iCurrentPage == 5){
						type = ((Page_MarketStatus) mainGraph).currentStockType;
					}
						
					mainGraph = new Page_MultiQuote(m_rcMain, this, type);
					bNeedRepaint = true;
				}
			}
			break;

		case 112: // 'p'
			if (iCurrentPage == 1 || iCurrentPage == 2)
				userCommand("01");
			break;

		case 113: // 'q'
			userCommand("60");
			break;

		case 114: // 'r'
			if (indexMainCode.length() > 0)
				userCommand("INDEX_" + indexMainCode);
			break;

		case 115: // 's'
			userCommand("80");
			break;

		case 116: // 't'
			onF5();
			break;

		case 118: // 'v'
			userCommand("70");
			break;

		default:
			if (mainGraph != null)
				bNeedRepaint = mainGraph.keyPressed(e);
			break;
		}
		if (bNeedRepaint)
			repaint();
	}

	void ChangeStock(boolean bUp, boolean bIgnoreStatus) {
		int iIndex = -1;
		for (int i = 0; i < m_codeList.size(); i++) {
			if (!strCurrentCode.equals(m_codeList.elementAt(i)))
				continue;
			iIndex = i;
			break;
		}

		if (iIndex == -1) {
			if (m_codeList.size() > 0)
				strCurrentCode = (String) m_codeList.elementAt(0);
		} else {
			if (bUp) {
				if (--iIndex < 0)
					iIndex = m_codeList.size() - 1;
			} else if (++iIndex >= m_codeList.size())
				iIndex = 0;
			strCurrentCode = (String) m_codeList.elementAt(iIndex);
		}
		if (!bIgnoreStatus) {
			CodeTable s = (CodeTable) m_ProductByHttp.get(strCurrentCode);
			if (s.status == 1 || s.status == 4) {
				ChangeStock(bUp, bIgnoreStatus);
				return;
			}
		}
		if (1 == iCurrentPage)
			mainGraph = new Page_MinLine(m_rcMain, this);
		else if (2 == iCurrentPage)
			mainGraph = new Page_KLine(m_rcMain, this);
	}

	void this_mouseLeftPressed(MouseEvent e) {
		if (mainGraph == null)
			return;
		if (mainGraph.mouseLeftClicked(e.getX(), e.getY()))
			repaint();
	}

	void this_mouseRightReleased(MouseEvent e) {
		if (mainGraph == null) {
			return;
		} else {
			mainGraph.processMenuEvent(popupMenu, e.getX(), e.getY());
			return;
		}
	}

	void this_mouseLeftDblClicked(MouseEvent e) {
		if (mainGraph == null)
			return;
		if (mainGraph.mouseLeftDblClicked(e.getX(), e.getY()))
			repaint();
	}

	void this_mouseMoved(MouseEvent e) {
		if (mainGraph == null)
			return;
		if (mainGraph.mouseMoved(e.getX(), e.getY()))
			repaint();
	}

	void this_mouseDragged(MouseEvent e) {
		if (mainGraph == null)
			return;
		if (mainGraph.mouseDragged(e.getX(), e.getY()))
			repaint();
	}

	void this_componentResized(ComponentEvent e) {
		Dimension d = getSize();
		m_rcMain = new Rectangle(d);
		m_rcBottom = new Rectangle(d);
		m_rcMain.height -= 20;
		m_rcBottom.y = m_rcMain.y + m_rcMain.height;
		m_rcBottom.height = 20;
		if (mainGraph != null)
			mainGraph.m_rc = m_rcMain;
		if (bottomGraph != null)
			bottomGraph.rc = m_rcBottom;
	}

	void queryStock(String sCode) {
		strCurrentCode = sCode;
		if (2 == iCurrentPage)
			mainGraph = new Page_KLine(m_rcMain, this);
		else
			mainGraph = new Page_MinLine(m_rcMain, this);
	}

	@SuppressWarnings("deprecation")
	void userCommand(String sCmd) {
		logger.info("cmd=" + sCmd);
		try{
		if (sCmd.equals("about") && !bAboutDlgShow) {
			Frame f = getParentFrame(this);
			Rectangle rc = getBounds();
			Point p = getLocationOnScreen();
			int x = (p.x + rc.width / 2) - 110;
			int y = (p.y + rc.height / 2) - 60;
			AboutDialog dlg = new AboutDialog(f, this);
			dlg.setBounds(x, y, 220, 120);
			bAboutDlgShow = true;
			dlg.show();
			bAboutDlgShow = false;
			return;
		}
		if (sCmd.startsWith("INDEX_")) {
			strCurrentCode = sCmd.substring(6);
			mainGraph = new Page_KLine(m_rcMain, this);
			return;
		}
		if (sCmd.startsWith("SERIES_")) {
			strCurrentCode = sCmd.substring(7);
			mainGraph = new Page_KLine(m_rcMain, this);
			return;
		}
		if (sCmd.equals("page_history")) {
			userCommand("70");
			return;
		}
		int iCmd = Integer.parseInt(sCmd);
		if (bDebug)
			logger.info("sCmd = ==" + iCmd);
		switch (iCmd) {
		case 1: // '\001'
			mainGraph = new Page_Bill(m_rcMain, this);
			break;

		case 5: // '\005'
			onF5();
			break;

		case 60: // '<'
			mainGraph = new Page_MultiQuote(m_rcMain, this, (byte) 1);
			break;

		case 80: // 'P'
			mainGraph = new Page_MarketStatus(m_rcMain, this, (byte) 1);
			break;

		case 70: // 'F'
			mainGraph = new Page_History(m_rcMain, this);
			break;
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	void showMutilQuote(byte stockType) {
		mainGraph = new Page_MultiQuote(m_rcMain, this, stockType);
		repaint();
	}

	void onF5() {
		if (strCurrentCode.length() == 0)
			return;
		if (1 == iCurrentPage) {
			mainGraph = new Page_KLine(m_rcMain, this);
			iCurrentPage = 2;
		} else {
			mainGraph = new Page_MinLine(m_rcMain, this);
			iCurrentPage = 1;
		}
	}

	void showPageMinLine(String stockCode) {
		strCurrentCode = stockCode;
		mainGraph = new Page_MinLine(m_rcMain, this);
		iCurrentPage = 1;
		repaint();
	}

	void showPageKLine(String stockCode) {
		strCurrentCode = stockCode;
		mainGraph = new Page_KLine(m_rcMain, this);
		iCurrentPage = 2;
		repaint();
	}

	void showPageMinLine() {
		mainGraph = new Page_MinLine(m_rcMain, this);
		iCurrentPage = 1;
		repaint();
	}

	void showPageKLine() {
		mainGraph = new Page_KLine(m_rcMain, this);
		iCurrentPage = 2;
		repaint();
	}

	public void repaintBottom() {
		if (bottomGraph != null)
			bottomGraph.paint();
	}

	void Repaint(Rectangle rc) {
		repaint(rc.x, rc.y, rc.width + 1, rc.height + 1);
	}

	public void focusLost(FocusEvent focusevent) {
	}

	public void focusGained(FocusEvent event) {
		repaint();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		if (m_rcMain == null)
			return;
		m_bEndPaint = false;
		try {
			m_img = createImage(m_rcMain.width, m_rcMain.height);
		} catch (Exception exception) {
		}
		if (mainGraph != null) {
			Graphics myG = m_img.getGraphics();
			if (myG != null) {
				myG.clearRect(0, 0, m_rcMain.width, m_rcMain.height);
				mainGraph.paint(myG);
			}
		}
		EndPaint();
		if (bottomGraph != null)
			bottomGraph.paint();
	}

	public void EndPaint() {
		if (!m_bEndPaint) {
			getGraphics().setPaintMode();
			getGraphics().drawImage(m_img, m_rcMain.x, m_rcMain.y, this);
		}
		m_bEndPaint = true;
	}

	public void destroy() {
		mainGraph.stopFlag = true;
		mainGraph = null;
		bRunning = false;
//		httpThread.askForData(null);
//		sendThread.AskForData(null);
		borker.stop();
//		try {
//			if (socket != null)
//				socket.close();
//		} catch (IOException e) {
//			logger.info("eroo111");
//			e.printStackTrace();
//		}
//		socket = null;
//		httpThread = null;
//		receiveThread = null;
		if (bDebug)
			logger.info("destroy HQApplet ");
	}

	int getPrecision(String sCode) {
		int iType = getProductType(sCode);
		switch (iType) {
		case 2: // '\002'
		case 3: // '\003'
			return m_iPrecisionIndex;
		}
		return m_iPrecision;
	}

	int getProductType(String code) {
		CodeTable codeTable = (CodeTable) m_ProductByHttp.get(code);
		if (codeTable == null)
			return -1;
		else
			return codeTable.status;
	}

	boolean isIndex(String code) {
		int iType = getProductType(code);
		return iType == 2 || iType == 3;
	}

	/**
	 * @param key
	 * @return
	 */
	String getShowString(String key) {
		String strShow = "";
		try {
			String s = m_resourceBundle.getString(key);
			strShow = new String(s.getBytes("8859_1"), "GB2312");
		} catch (Exception exception) {
		}
		return strShow;
	}

}
