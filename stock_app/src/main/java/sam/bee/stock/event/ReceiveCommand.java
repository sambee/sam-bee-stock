package sam.bee.stock.event;

import static sam.bee.stock.gui.Application.bDebug;

import java.util.Date;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.CodeTable;
import sam.bee.stock.gui.Packet_MultiQuote;
import sam.bee.stock.gui.Page_MultiQuote;
import sam.bee.stock.gui.ProductData;
import sam.bee.stock.service.vo.ProductInfoListVO;
import sam.bee.stock.vo.ProductDataVO;
import sam.bee.stock.vo.ProductInfoVO;

public class ReceiveCommand extends Thread{

	Application m_applet;
	SocketStockLoader m_borker;
	
	public ReceiveCommand(SocketStockLoader borker) {
		m_applet= borker.m_AppMain;
		m_borker = borker;
		receiveCodeTable();
		receiveStockQuote();
	}
	
	@Override
	public void run() {
		while(true){

			receiveClassSort();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void receiveClassSort() {
		byte sortBy = 0;
		byte isDescend = 0;
		int totalCount = 100;
		int start =1;
		
        ProductDataVO values[] = new ProductDataVO[100];
        for(int i = 0; i < values.length; i++) {
            values[i] = new ProductDataVO();
            values[i].code = "xxx"+i;
            values[i].yesterBalancePrice = 1F;
            values[i].closePrice = 1F;
            values[i].openPrice = 1F;
            values[i].highPrice = 1F;
            values[i].lowPrice =1F;
            values[i].curPrice = 1F;
            values[i].totalAmount = 1;
            values[i].totalMoney = 1D;
            values[i].curAmount = 1;
            values[i].amountRate = 1F;
            values[i].balancePrice = 1F;
            values[i].reserveCount =1;
            values[i].buyAmount = new int[1];
            values[i].buyAmount[0] = 1;
            values[i].sellAmount = new int[1];
            values[i].sellAmount[0] = 1;
            values[i].buyPrice = new float[1];
            values[i].buyPrice[0] = 1F;
            values[i].sellPrice = new float[1];
            values[i].sellPrice[0] = 1F;
        }
        
		ProductDataVO data[] = values;
		if (m_applet.iCurrentPage != 0)
			return;
		Page_MultiQuote page = (Page_MultiQuote) m_applet.mainGraph;
		
		if (page.sortBy != sortBy 
				|| page.isDescend != isDescend
				|| page.iStart != start){
			return;
		}
			
		page.packetInfo = new Packet_MultiQuote();
		page.packetInfo.currentStockType = 1;
		page.packetInfo.sortBy = sortBy;
		page.packetInfo.isDescend = isDescend;
		page.packetInfo.iStart = start;
		page.packetInfo.iEnd = start + data.length;
		page.packetInfo.iCount = totalCount;

		if (bDebug)
			System.out.println("totalCount = " + totalCount);
		page.quoteData = data;
		m_applet.repaint();
	}
	
	/**
	 * 
	 */
	private void receiveCodeTable(){
		   ProductInfoListVO productInfoList = new ProductInfoListVO();
	        productInfoList.date = 2014;
	        productInfoList.time = 1200;
	        int length = 3;
	        ProductInfoVO productInfos[] = new ProductInfoVO[length];
	        for(int i = 0; i < productInfos.length; i++) {
	        	  productInfos[i] = new ProductInfoVO();
	              productInfos[i].code ="60000"+i;
	              productInfos[i].status = 1;
	              productInfos[i].fUnit = 2;
	              productInfos[i].name = "XXX"+i;
	              productInfos[i].pinyin = new String[4];
	              
	              for(int j = 0; j < productInfos[i].pinyin.length; j++){
	            	  productInfos[i].pinyin[j] =  productInfos[i].code ;
	              }
	              
	              productInfos[i].tradeSecNo = new int[4];
	              for(int j = 0; j < productInfos[i].tradeSecNo.length; j++)
	                  productInfos[i].tradeSecNo[j] =  i;

	              if(productInfos[i].fUnit <= 0.0F)
	                  productInfos[i].fUnit = 1.0F;
	        }
	        
	        /////////////////////////////////////////////////////////////////
	        
	        ProductInfoListVO list = productInfoList;
			for (int i = 0; i < list.productInfos.length; i++) {
				boolean bFound = false;
				for (int j = 0; j < m_applet.m_codeList.size(); j++) {
					if (!list.productInfos[i].code
							.equalsIgnoreCase((String) m_applet.m_codeList
									.elementAt(j)))
						continue;
					bFound = true;
					break;
				}

				if (!bFound)
					m_applet.m_codeList.addElement(list.productInfos[i].code);
				CodeTable codeTable = new CodeTable();
				codeTable.sName = list.productInfos[i].name;
				codeTable.sPinyin = list.productInfos[i].pinyin;
				codeTable.status = list.productInfos[i].status;
				codeTable.fUnit = list.productInfos[i].fUnit;
				codeTable.tradeSecNo = list.productInfos[i].tradeSecNo;
				m_applet.m_ProductByHttp.put(list.productInfos[i].code, codeTable);
				m_applet.m_iCodeDate = list.date;
				m_applet.m_iCodeTime = list.time;
	}
	}
	
	/**
	 *  个股行情
	 */
	@SuppressWarnings("unchecked")
	private void receiveStockQuote()  {
		
		  
	        ProductDataVO values[] = new ProductDataVO[100];
	        for(int i = 0; i < values.length; i++) {
	            values[i] = new ProductDataVO();
	            values[i].code = "6" + i;
	            values[i].time = new Date();
	            values[i].closePrice = 0.0F;
	            values[i].openPrice =  0.0F;
	            values[i].highPrice =  0.0F;
	            values[i].lowPrice =  0.0F;
	            values[i].curPrice =  0.0F;
	            values[i].totalAmount = 0L;
	            values[i].totalMoney =  0.0D;
	            values[i].curAmount = 0;
	            values[i].amountRate =  0.0F;
	            values[i].balancePrice =  0.0F;
	            values[i].reserveCount = 0;
	            values[i].yesterBalancePrice =  0.0F;
	            values[i].reserveChange = 0;
	            if(true) {
	                values[i].inAmount = 0;
	                values[i].outAmount = 0;
	                values[i].buyAmount = new int[1];
	                for(int j = 0; j < values[i].buyAmount.length; j++)
	                    values[i].buyAmount[j] = 0;

	                values[i].sellAmount = new int[1];
	                for(int j = 0; j < values[i].sellAmount.length; j++)
	                    values[i].sellAmount[j] = 0;

	                values[i].buyPrice = new float[1];
	                for(int j = 0; j < values[i].buyPrice.length; j++)
	                    values[i].buyPrice[j] = 0;

	                values[i].sellPrice = new float[1];
	                for(int j = 0; j < values[i].sellPrice.length; j++)
	                    values[i].sellPrice[j] = 0;

	            }
	        }
	        
		ProductDataVO data[] = values;
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
				&& m_applet.strCurrentCode.equals(sCode)){
			m_applet.repaint();
		}
		if (data.length > 0 && m_applet.m_bShowIndexAtBottom == 1
				&& m_applet.indexMainCode.length() > 0
				&& data[0].code.equalsIgnoreCase(m_applet.indexMainCode)){
			m_applet.repaintBottom();
		}
			
	}
			
}
