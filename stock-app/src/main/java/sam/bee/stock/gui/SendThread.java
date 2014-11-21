// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   SendThread.java

package sam.bee.stock.gui;

import static sam.bee.stock.gui.Application.bDebug;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import sam.bee.stock.service.vo.CMDDateVO;
import sam.bee.stock.service.vo.CMDMinLineIntervalVO;
import sam.bee.stock.service.vo.CMDProductInfoVO;
import sam.bee.stock.service.vo.CMDQuoteVO;
import sam.bee.stock.service.vo.SortReq;
import sam.bee.stock.service.vo.CMDTradeTimeVO;
import sam.bee.stock.service.vo.Req;
import sam.bee.stock.service.vo.RequestUtil;

// Referenced classes of package gnnt.MEBS.HQApplet:
//            HQApplet, ProductData
@Deprecated
public class SendThread extends Thread {

    @SuppressWarnings("rawtypes")
	private Vector vPacket;
    Calendar m_calCodeTable;
    Calendar m_calIndex;
    Application m_applet;

    /**
     * @param applet
     */
    @SuppressWarnings("rawtypes")
    @Deprecated
	public SendThread(Application applet) {
        vPacket = new Vector();
        m_calIndex = null;
        m_applet = applet;
    }

    /**
     * @param packet
     */
    @SuppressWarnings("unchecked")
    @Deprecated
	public
	synchronized void askForData(Req packet) {
        if(packet != null) {
            vPacket.addElement(packet);
            int iSize = vPacket.size();
            int iBuf = 3;
            if(iSize > iBuf) {
                for(int i = 0; i < iSize - iBuf; i++)
                    vPacket.removeElementAt(i);

            }
        }
        notify();
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Deprecated
    public void run() {
        while(m_applet != null && m_applet.bRunning) 
        	
        	//check this socket running status
            if(m_applet.socket == null) {
                if(!connectToServer())
                    try {
                        Thread.sleep(5000L);
                    }
                    catch(InterruptedException interruptedexception) { }
            } else {
                try {
                    Thread.sleep(100L);
                }
                catch(InterruptedException interruptedexception1) { }
                Calendar cal = Calendar.getInstance();
                if(m_applet.m_bShowIndexAtBottom == 1 && m_applet.indexMainCode.length() > 0) {
                    cal.add(13, -10);
                    if(m_calIndex == null || cal.after(m_calIndex)) {
                        askForIndex();
                        m_calIndex = Calendar.getInstance();
                    }
                    cal.add(13, -10);
                }
                cal.add(12, -1);
                if(cal.after(m_calCodeTable)) {
                    askForDateAndCodeTable();
                    m_calCodeTable = Calendar.getInstance();
                }
                int iSize = vPacket.size();
                if(iSize > 0) {
                    Req sendPacket = (Req)vPacket.elementAt(iSize - 1);
                    vPacket.removeElementAt(iSize - 1);
                    try {
                        if(bDebug && m_applet.socket != null)
                            switch(sendPacket.getCmd()) {
                            case 2: // '\002'
                                System.out.println("Send cmd:" + sendPacket.getCmd() + "-" + ((CMDQuoteVO)sendPacket).codeList[0][0] + "-" + ((CMDQuoteVO)sendPacket).codeList[0][1]);
                                break;

                            case 3: // '\003'
                                SortReq packet = (SortReq)sendPacket;
                                System.out.println("Send cmd:" + sendPacket.getCmd() + " sort by " + packet.sortBy + " start:" + packet.start + " end:" + packet.end);
                                break;

                            default:
                                System.out.println("Send cmd:" + sendPacket.getCmd());
                                break;
                            }
                        RequestUtil.sendRequest(sendPacket, m_applet.socket);
                    }
                    catch(IOException e) {
                        
                        if(bDebug) {
                            e.getMessage();
                            e.printStackTrace();
                        }
                        m_applet.socket = null;
                    }
                } else {
                    synchronized(this) {
                        try {
                            wait();
                        }
                        catch(InterruptedException e) {
                            e.getMessage();
                        }
                    }
                }
            }
        try {
            if(m_applet != null && m_applet.socket != null) {
                m_applet.socket.close();
                m_applet.socket = null;
            }
        }
        catch(IOException ioexception) { }
        System.out.println("SendThread Exit !");
    }

    /**
     * @return
     */
    @Deprecated
    boolean connectToServer() {
        try {
            m_applet.socket = new Socket(m_applet.strSocketIP, m_applet.iSocketPort);
        }
        catch(UnknownHostException eHost) {
            
            if(bDebug){
                eHost.printStackTrace();
            }
            return false;
        }
        catch(IOException eIO) {
           
            if(bDebug){
                //eIO.printStackTrace();
            	System.err.println("Connect server failure.");
            }
            return false;
        }
      
        if(bDebug)
            System.out.println("connected");
        if(m_applet.socket == null)
            System.out.println("socket is null");
        m_calCodeTable = Calendar.getInstance();
        try {
            CMDDateVO packet1 = new CMDDateVO();
            RequestUtil.sendRequest(packet1, m_applet.socket);
            CMDTradeTimeVO packet = new CMDTradeTimeVO();
            RequestUtil.sendRequest(packet, m_applet.socket);
            CMDMinLineIntervalVO packet2 = new CMDMinLineIntervalVO();
            RequestUtil.sendRequest(packet2, m_applet.socket);
        }
        catch(Exception e) {
          
            if(bDebug)
                e.printStackTrace();
            m_applet.socket = null;
            return false;
        }
        return true;
    }

    /**
     * 
     */
    @Deprecated
    private void askForDateAndCodeTable() {
        try {
            CMDDateVO packet1 = new CMDDateVO();
            RequestUtil.sendRequest(packet1, m_applet.socket);
            CMDProductInfoVO packet2 = new CMDProductInfoVO();
            packet2.date = m_applet.m_iCodeDate;
            packet2.time = m_applet.m_iCodeTime;
            RequestUtil.sendRequest(packet2, m_applet.socket);
        }
        catch(Exception e) {
           
            if(bDebug)
                e.printStackTrace();
            m_applet.socket = null;
        }
    }

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    @Deprecated
	private void askForIndex() {
        if(m_applet.indexMainCode.length() == 0)
            return;
        ProductData productData = m_applet.getProductData(m_applet.indexMainCode);
        Date time = null;
        if(productData != null && productData.realData != null){
            time = productData.realData.time;
        }
        CMDQuoteVO packet = new CMDQuoteVO();
        packet.codeList = new String[1][2];
        packet.codeList[0][0] = m_applet.indexMainCode;
        packet.isAll = 1;
        if(time != null)
            packet.codeList[0][1] = String.valueOf(time.getHours() * 10000 + time.getMinutes() * 100 + time.getSeconds());
        else
            packet.codeList[0][1] = "0";
        try {
            RequestUtil.sendRequest(packet, m_applet.socket);
        }
        catch(Exception e) {
         
            if(bDebug){
                e.printStackTrace();
            }
            m_applet.socket = null;
        }
    }

    @Deprecated
    public void askForRealQuote(String code, Date time){
    	askForRealQuote(code, time, this);
    }
    /**
     * @param code
     * @param time
     * @param sendThread
     */
    @SuppressWarnings("deprecation")
    @Deprecated
	private static void askForRealQuote(String code, Date time, SendThread sendThread) {
        CMDQuoteVO packet = new CMDQuoteVO();
        packet.codeList = new String[1][2];
        packet.codeList[0][0] = code;
        packet.isAll = 1;
        if(time != null)
            packet.codeList[0][1] = String.valueOf(time.getHours() * 10000 + time.getMinutes() * 100 + time.getSeconds());
        else
            packet.codeList[0][1] = "0";
        sendThread.askForData(packet);
    }
}
