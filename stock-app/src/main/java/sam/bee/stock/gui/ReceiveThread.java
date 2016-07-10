package sam.bee.stock.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.stock.event.SendCommand;
import sam.bee.stock.service.vo.*;
import sam.bee.stock.vo.BillDataVO;
import sam.bee.stock.vo.MinDataVO;
import sam.bee.stock.vo.ProductDataVO;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Vector;

import static sam.bee.stock.gui.Application.bDebug;

@Deprecated
public class ReceiveThread extends Thread {
	protected static final Logger logger = LoggerFactory.getLogger(ReceiveThread.class);
	Application m_applet;
	SendCommand m_borker;
	
	public ReceiveThread(SendCommand borker) {
		m_applet= borker.m_AppMain;
		m_borker = borker;
		
	}

	@Deprecated
	@Override
	public void run() {
		throw new RuntimeException("Not used yet. ");
//		DataInputStream reader = null;
//		while (m_applet != null && m_applet.bRunning) {
//			if (m_applet.socket == null) {
//				reader = null;
//				try {
//					Thread.sleep(500L);
//				} catch (InterruptedException interruptedexception) {
//				}
//				continue;
//			}
//			try {
//				if (reader == null)
//					reader = new DataInputStream(new BufferedInputStream(
//							m_applet.socket.getInputStream()));
//				byte cmd = reader.readByte();
//				switch (cmd) {
//				case 0: // '\0'
//					break;
//
//				case 1: // '\001'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  更新码表");
//					receiveCodeTable(reader);
//					break;
//
//				case 2: // '\002'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  个股行情");
//					receiveStockQuote(reader);
//					break;
//
//				case 3: // '\003'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  报价排名");
//					receiveClassSort(reader);
//					break;
//
//				case 4: // '\004'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  分时数据");
//					receiveMinLineData(reader);
//					break;
//
//				case 5: // '\005'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  成交明细");
//					receiveBillData(reader);
//					break;
//
//				case 6: // '\006'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  交易时间");
//					m_applet.m_timeRange = CMDTradeTimeVO.getObj(reader);
//					break;
//
//				case 7: // '\007'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  市场日期");
//					int date = reader.readInt();
//					int time = reader.readInt();
//					int oldDate = m_applet.m_iDate;
//					int oldTime = m_applet.m_iTime;
//					if (m_applet.m_iDate == 0 || date != oldDate) {
//						m_applet.m_iDate = date;
//						m_applet.vProductData.removeAllElements();
//					}
//					m_applet.m_iTime = time;
//					m_applet.repaintBottom();
//					Application _tmp1 = m_applet;
//					if (bDebug)
//						System.out.println("Date:" + m_applet.m_iDate + " "	+ time);
//					if (oldDate != m_applet.m_iDate
//							|| oldTime != m_applet.m_iTime)
//						m_applet.repaint();
//					break;
//
//				case 8: // '\b'
//
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  综合排名");
//					receiveMarketSort(reader);
//					break;
//
//				case 9: // '\t'
//
//				case 10: // '\n'
//					m_applet.m_iMinLineInterval = reader.readInt();
//					Application _tmp9 = m_applet;
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  分时间隔:"
//								+ m_applet.m_iMinLineInterval);
//					if (m_applet.m_iMinLineInterval <= 0
//							|| m_applet.m_iMinLineInterval > 60)
//						m_applet.m_iMinLineInterval = 60;
//					break;
//
//				default:
//					Application _tmp10 = m_applet;
//					if (bDebug)
//						System.out.println("Receive cmd: " + cmd + "  非法数据");
//					m_applet.socket.close();
//					m_applet.socket = null;
//					m_borker.askForData(null);
//					break;
//				}
//
//			} catch (EOFException e) {
//				 {
//					if (m_applet != null) {
//						Application _tmp11 = m_applet;
//						if (bDebug){
//							e.printStackTrace();
//						}
//
//					}
//
//				}
//				try {
//					if (m_applet.socket != null)
//						m_applet.socket.close();
//					m_applet.socket = null;
//				} catch (Exception exception) {
//				}
//				if (m_applet != null && m_applet.bRunning)
//					m_borker.askForData(null);
//				continue;
//			} catch (Exception e) {
//
//				if (bDebug)
//				{
//					System.out.println("Socket error ");
//					e.printStackTrace();
//				}
//				if (m_applet != null && m_applet.bRunning) {
//					m_applet.socket = null;
//					m_borker.askForData(null);
//				}
//			}
//		}
//		System.out.println("ReceiveThread Exit !");
	}

	/**
	 * 个股行情
	 * 
	 * @param reader
	 * @throws IOException
	 */
	@Deprecated
	private void receiveStockQuote(DataInputStream reader) throws IOException {
		ProductDataVO data[] = CMDQuoteVO.getObj(reader);
		String sCode = "";
		for (int i = 0; i < data.length; i++) {
			sCode = data[i].code;
			ProductData stock = m_applet.getProductData(sCode);
			if (stock == null) {
				if (m_applet.vProductData.size() > 50)
					m_applet.vProductData.removeElementAt(50);
				stock = new ProductData();
				stock.sCode = sCode;
				stock.realData = data[i];
				m_applet.vProductData.insertElementAt(stock, 0);
			} else {
				stock.realData = data[i];
			}
		}

		if (data.length > 0
				&& (2 == m_applet.iCurrentPage || 1 == m_applet.iCurrentPage)
				&& m_applet.strCurrentCode.equals(sCode))
			m_applet.repaint();
		if (data.length > 0 && m_applet.m_bShowIndexAtBottom == 1
				&& m_applet.indexMainCode.length() > 0
				&& data[0].code.equalsIgnoreCase(m_applet.indexMainCode))
			m_applet.repaintBottom();
	}

	/**
	 * 报价排名
	 * @param reader
	 * @throws IOException
	 */
	@Deprecated
	private void receiveClassSort(DataInputStream reader) throws IOException {
//		byte sortBy = reader.readByte();
//		byte isDescend = reader.readByte();
//		int totalCount = reader.readInt();
//		int start = reader.readInt();
//		ProductDataVO data[] = SortCMD.getObj(reader);
//		if (m_applet.iCurrentPage != 0)
//			return;
//		Page_MultiQuote page = (Page_MultiQuote) m_applet.mainGraph;
//		if (page.sortBy != sortBy || page.isDescend != isDescend
//				|| page.iStart != start)
//			return;
//		page.packetInfo = new Packet_MultiQuote();
//		page.packetInfo.currentStockType = 1;
//		page.packetInfo.sortBy = sortBy;
//		page.packetInfo.isDescend = isDescend;
//		page.packetInfo.iStart = start;
//		page.packetInfo.iEnd = start + data.length;
//		page.packetInfo.iCount = totalCount;
//		
//		if (bDebug)
//			System.out.println("totalCount = " + totalCount);
//		page.quoteData = data;
//		m_applet.repaint();
	}

	/**
	 * 综合排名
	 * @param reader
	 * @throws IOException
	 */
	@Deprecated
	private void receiveMarketSort(DataInputStream reader) throws IOException {
		int num = reader.readInt();
		sam.bee.stock.service.vo.MarketStatusVO values[] = CMDMarketSortVO
				.getObj(reader);
		if (5 != m_applet.iCurrentPage) {
			return;
		} else {
			Page_MarketStatus page = (Page_MarketStatus) m_applet.mainGraph;
			page.packetInfo = new Packet_MarketStatus();
			page.packetInfo.iCount = num;
			page.statusData = values;
			m_applet.repaint();
			return;
		}
	}

	/**
	 * 成交明细
	 * @param reader
	 * @throws IOException
	 */
	@Deprecated
	private void receiveBillData(DataInputStream reader) throws IOException {
		String code = reader.readUTF();
		byte type = reader.readByte();
		int time = reader.readInt();
		BillDataVO data[] = CMDBillVO.getObj(reader);
		ProductData stock = m_applet.getProductData(code);
		if (stock == null) {
			if (m_applet.vProductData.size() > 50)
				m_applet.vProductData.removeElementAt(50);
			stock = new ProductData();
			stock.sCode = code;
			m_applet.vProductData.insertElementAt(stock, 0);
		}
		switch (type) {
		default:
			break;

		case 0: // '\0'
			makeLastBills(stock, time, data);
			break;

		case 1: // '\001'
			stock.vBill = new Vector();
			for (int i = 0; i < data.length; i++)
				stock.vBill.addElement(data[i]);

			if (data.length != 0 && 1 == m_applet.iCurrentPage
					&& m_applet.strCurrentCode.equals(code))
				m_applet.repaint();
			break;
		}
	}

	/**
	 * @param reader
	 * @throws IOException
	 */
	@Deprecated
	private void receiveMinLineData(DataInputStream reader) throws IOException {
		String code = reader.readUTF();
		byte type = reader.readByte();
		int time = reader.readInt();
		MinDataVO values[] = MinReq.getObj(reader);
		ProductData stock = m_applet.getProductData(code);
		if (stock == null) {
			if (m_applet.vProductData.size() > 50)
				m_applet.vProductData.removeElementAt(50);
			stock = new ProductData();
			stock.sCode = code;
			m_applet.vProductData.insertElementAt(stock, 0);
		}
		stock.vMinLine = new Vector();
		int jMin = 0;
		for (int i = 0; i < values.length; i++) {
			int iIndex = Common.GetMinLineIndexFromTime(values[i].time,
					m_applet.m_timeRange, m_applet.m_iMinLineInterval);
			for (int j = jMin; j < iIndex; j++) {
				MinDataVO min = new MinDataVO();
				if (j > 0) {
					min.curPrice = ((MinDataVO) stock.vMinLine.elementAt(j - 1)).curPrice;
					min.totalAmount = ((MinDataVO) stock.vMinLine
							.elementAt(j - 1)).totalAmount;
					min.totalMoney = ((MinDataVO) stock.vMinLine
							.elementAt(j - 1)).totalMoney;
					min.averPrice = ((MinDataVO) stock.vMinLine
							.elementAt(j - 1)).averPrice;
					min.reserveCount = ((MinDataVO) stock.vMinLine
							.elementAt(j - 1)).reserveCount;
				} else if (stock.realData != null) {
					min.curPrice = stock.realData.yesterBalancePrice;
					min.averPrice = stock.realData.yesterBalancePrice;
				}
				stock.vMinLine.addElement(min);
			}

			if (iIndex >= stock.vMinLine.size() - 1) {
				MinDataVO min = null;
				if (iIndex == stock.vMinLine.size() - 1) {
					min = (MinDataVO) stock.vMinLine.lastElement();
				} else {
					min = new MinDataVO();
					stock.vMinLine.addElement(min);
				}
				min.curPrice = values[i].curPrice;
				min.totalAmount = values[i].totalAmount;
				min.reserveCount = values[i].reserveCount;
				min.averPrice = values[i].averPrice;
				jMin = iIndex + 1;
			}
		}

		if ((2 == m_applet.iCurrentPage || 1 == m_applet.iCurrentPage)
				&& m_applet.strCurrentCode.equals(stock.sCode))
			m_applet.repaint();
	}

	/**
	 * @param stock
	 * @param time
	 * @param values
	 */
	@Deprecated
	private void makeLastBills(ProductData stock, int time, BillDataVO values[]) {
		if (values.length <= 0)
			return;
		if (time == 0) {
			stock.vBill = new Vector();
			for (int i = 0; i < values.length; i++)
				stock.vBill.addElement(values[i]);

			if (4 == m_applet.iCurrentPage)
				m_applet.repaint();
			return;
		}
		if (stock.vBill == null)
			stock.vBill = new Vector();
		if (stock.vBill.size() > 0
				&& ((BillDataVO) stock.vBill.lastElement()).time >= values[0].time)
			return;
		for (int i = 0; i < values.length; i++)
			stock.vBill.addElement(values[i]);

		if (stock.vMinLine == null)
			stock.vMinLine = new Vector();
		for (int i = 0; i < values.length; i++) {
			int iIndex = Common.GetMinLineIndexFromTime(values[i].time,
					m_applet.m_timeRange, m_applet.m_iMinLineInterval);
			if (iIndex < stock.vMinLine.size() - 1)
				break;
			MinDataVO min = null;
			if (iIndex == stock.vMinLine.size() - 1) {
				min = (MinDataVO) stock.vMinLine.elementAt(iIndex);
			} else {
				for (int j = stock.vMinLine.size(); j <= iIndex; j++) {
					min = new MinDataVO();
					if (j > 0) {
						min.curPrice = ((MinDataVO) stock.vMinLine
								.elementAt(j - 1)).curPrice;
						min.totalAmount = ((MinDataVO) stock.vMinLine
								.elementAt(j - 1)).totalAmount;
						min.totalMoney = ((MinDataVO) stock.vMinLine
								.elementAt(j - 1)).totalMoney;
						min.reserveCount = ((MinDataVO) stock.vMinLine
								.elementAt(j - 1)).reserveCount;
						min.averPrice = ((MinDataVO) stock.vMinLine
								.elementAt(j - 1)).averPrice;
					}
					stock.vMinLine.addElement(min);
				}

			}
			min.curPrice = values[i].curPrice;
			min.totalAmount = values[i].totalAmount;
			min.totalMoney = values[i].totalMoney;
			min.reserveCount = values[i].reserveCount;
			min.averPrice = values[i].balancePrice;
		}

		if (values[values.length - 1].time > m_applet.m_iTime)
			m_applet.m_iTime = values[values.length - 1].time;
		if ((2 == m_applet.iCurrentPage || 1 == m_applet.iCurrentPage)
				&& m_applet.strCurrentCode.equals(stock.sCode))
			m_applet.repaint();
	}

	/**
	 * 更新码表
	 * @param reader
	 * @throws IOException
	 */
	@Deprecated
	private void receiveCodeTable(DataInputStream reader) throws IOException {
		logger.info("更新码表");
//		ProductInfoListVO list = CMDProductInfoVO.getObj(reader);
//		for (int i = 0; i < list.productInfos.length; i++) {
//			boolean bFound = false;
//			for (int j = 0; j < m_applet.m_codeList.size(); j++) {
//				if (!list.productInfos[i].code.equalsIgnoreCase((String) m_applet.m_codeList.elementAt(j))){
//					continue;
//				}
//				bFound = true;
//				break;
//			}
//
//			if (!bFound){
//				m_applet.m_codeList.addElement(list.productInfos[i].code);
//			}
//
//			CodeTable codeTable = new CodeTable();
//			codeTable.sName = list.productInfos[i].name;
//			codeTable.sPinyin = list.productInfos[i].pinyin;
//			codeTable.status = list.productInfos[i].status;
//			codeTable.fUnit = list.productInfos[i].fUnit;
//			codeTable.tradeSecNo = list.productInfos[i].tradeSecNo;
//			m_applet.m_ProductByHttp.put(list.productInfos[i].code, codeTable);
//			m_applet.m_iCodeDate = list.date;
//			m_applet.m_iCodeTime = list.time;
//		}

	}
}
