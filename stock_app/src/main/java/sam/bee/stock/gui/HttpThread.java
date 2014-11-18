// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   HttpThread.java

package sam.bee.stock.gui;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import sam.bee.stock.service.vo.ProductInfoListVO;
import sam.bee.stock.service.vo.RequestUtil;
import sam.bee.stock.vo.ProductInfoVO;
import static sam.bee.stock.gui.Application.bDebug;


//NMI's Java Code Viewer 6.0a
//www.trinnion.com/javacodeviewer

//Registered to Evaluation Copy                                      
//Generated PGFZKD AyTB 14 2007 15:44:19 

//source File Name:   ProductData.java
class Packet_HttpRequest {

    public static final byte TYPE_DAYLINE = 0;
    static final byte TYPE_5MINLINE = 1;
    static final byte TYPE_F10 = 2;
    String sCode;
    byte type;

}

//Referenced classes of package gnnt.MEBS.HQApplet:
//HQApplet, CodeTable, Packet_HttpRequest, KLineData, 
//ProductData
public class HttpThread extends Thread {

    Application m_application;
    private static final int TYPE_CODELIST = 0;
    private static final int TYPE_OTHER = 1;
    private int iType;
    @SuppressWarnings("rawtypes")
	private Vector vPacket;

    @SuppressWarnings("rawtypes")
	public HttpThread(int type, Application applet) {
        m_application = applet;
        iType = type;
        if(iType == TYPE_OTHER)
            vPacket = new Vector();
    }

    @SuppressWarnings("unchecked")
	synchronized void askForData(Packet_HttpRequest packet) {
        if(packet != null) {
            vPacket.addElement(packet);
            int iSize = vPacket.size();
         
            if(iSize > 1) {
                for(int i = 0; i < iSize - 1; i++)
                    vPacket.removeElementAt(i);

            }
        }
        notify();
    }

    public void run() {
        if(iType == TYPE_CODELIST)
            getCodeList();
        else
            getHttpData();
    }


    @SuppressWarnings({ "unchecked"})
	private void getCodeList() {
        for(boolean bSucceed = false; m_application != null && m_application.bRunning && !bSucceed;) {
            try {
                ProductInfoListVO list = RequestUtil.getStockInfoList(m_application.strURLPath + "data/productinfo.dat");
             
                if(bDebug){
                    System.out.println("码表时间:" + list.date + " " + list.time);
                }
                m_application.m_iCodeDate = list.date;
                m_application.m_iCodeTime = list.time;
                ProductInfoVO products[] = list.productInfos;
                m_application.m_codeList.removeAllElements();
                m_application.m_ProductByHttp.clear();
                for(int i = 0; i < products.length; i++) {
                    m_application.m_codeList.addElement(products[i].code);
                    CodeTable data = new CodeTable();
                    data.sName = products[i].name;
                    data.sPinyin = products[i].pinyin;
                    data.status = products[i].status;
                    data.tradeSecNo = products[i].tradeSecNo;
                    data.fUnit = products[i].fUnit;
                    m_application.m_ProductByHttp.put(products[i].code, data);
                    if(data.status == 3 && m_application.indexMainCode.length() == 0){
                        m_application.indexMainCode = products[i].code;
                    }
                }

                bSucceed = true;
                m_application.repaint();
            }
            catch(MalformedURLException malformedurlexception) { }
            catch(IOException ex) {                
                if(bDebug){
                    ex.printStackTrace();
                }
            }
            catch(Exception ex) {
                if(bDebug){
                    ex.printStackTrace();
                }
            }
            if(!bSucceed){
                try {Thread.sleep(1000L);}catch(InterruptedException interruptedexception) { }
            }
        }

    }

    private void getHttpData() {
        while(m_application != null && m_application.bRunning)  {
            try {
                Thread.sleep(300L);
            }
            catch(InterruptedException interruptedexception) { }
            int iSize = vPacket.size();
            if(iSize > 0) {
                Packet_HttpRequest request = (Packet_HttpRequest)vPacket.elementAt(iSize - 1);
                vPacket.removeElementAt(iSize - 1);
                switch(request.type) {
                case 0: // '\0'
                    getDayLine(request);
                    break;

                case 1: // '\001'
                    get5MinLine(request);
                    break;
                }
            } else {
                synchronized(this) {
                    try {
                        wait();
                    }
                    catch(InterruptedException interruptedexception1) { }
                }
            }
        }
    }

    private static byte[] getRepoent(String url) throws MalformedURLException, IOException {
        URL page = null;
        page = new URL(url);
        URLConnection urlc = page.openConnection();
        urlc.connect();
        int filesize = urlc.getContentLength();
        BufferedInputStream inputs = new BufferedInputStream(urlc.getInputStream());
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        for(byte by[] = new byte[1]; inputs.read(by) > 0; array.write(by));
        inputs.close();
        if(array.toByteArray().length != filesize)
            throw new MalformedURLException();
        else
            return array.toByteArray();
    }

    
    /**
     * 历史数据
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static KLineData[] getHistoryData(String url) throws MalformedURLException, IOException {
        ByteArrayInputStream arrayInput = new ByteArrayInputStream(getRepoent(url));
        GZIPInputStream gzin = new GZIPInputStream(arrayInput);
        DataInputStream input = new DataInputStream(gzin);
        KLineData hisStatus[] = new KLineData[input.readInt()];
        for(int i = 0; i < hisStatus.length; i++) {
            hisStatus[i] = new KLineData();
            int date = input.readInt();
           // if(date.length() > 6)
            if(date>6)
                hisStatus[i].date = 199700000000L + (long)date;
            else
                hisStatus[i].date = date + 0x130b7d0;
            hisStatus[i].openPrice = input.readFloat();
            hisStatus[i].highPrice = input.readFloat();
            hisStatus[i].lowPrice = input.readFloat();
            hisStatus[i].closePrice = input.readFloat();
            hisStatus[i].balancePrice = input.readFloat();
            hisStatus[i].totalAmount = input.readLong();
            hisStatus[i].totalMoney = input.readFloat();
            hisStatus[i].reserveCount = input.readInt();
        }

        return hisStatus;
    }

    /**
     * @param request
     */
    @SuppressWarnings("unchecked")
	private void getDayLine(Packet_HttpRequest request) {
        try {
            String strURL = m_application.strURLPath + "data/day/" + request.sCode.trim() + ".day.zip";

            if(bDebug)
                System.out.println("Get Day : " + strURL);
            KLineData hisStatus[] = getHistoryData(strURL);
            ProductData product = m_application.getProductData(request.sCode);
            if(product == null) {
                if(m_application.vProductData.size() > 50){
                    m_application.vProductData.removeElementAt(50);
                }
                product = new ProductData();
                product.sCode = request.sCode;
                product.dayKLine = hisStatus;
                m_application.vProductData.insertElementAt(product, 0);
            } else {
                product.dayKLine = hisStatus;
            }
            if(hisStatus.length > 0) {
          
                if(2 == m_application.iCurrentPage && m_application.strCurrentCode.equals(request.sCode))
                    m_application.repaint();
            }
        }
        catch(MalformedURLException ex) {       
            if(bDebug){
                System.err.print(ex.toString());
            }
        }
        catch(IOException ex) {     
            if(bDebug){
                ex.printStackTrace();
            }
        }
        catch(Exception ex) {          
            if(bDebug){
                ex.printStackTrace();
            }
        }
    }

    
    /**
     * @param request
     */
    @SuppressWarnings("unchecked")
	private void get5MinLine(Packet_HttpRequest request) {
        try {
            KLineData hisStatus[] = getHistoryData(m_application.strURLPath + "data/5min/" + request.sCode + ".5min.zip");
            ProductData stock = m_application.getProductData(request.sCode);
            if(stock == null) {
                if(m_application.vProductData.size() > 50)
                    m_application.vProductData.removeElementAt(50);
                stock = new ProductData();
                stock.sCode = request.sCode;
                stock.min5KLine = hisStatus;
                m_application.vProductData.insertElementAt(stock, 0);
            } else {
                stock.min5KLine = hisStatus;
            }
            for(int iIndex = 0; iIndex < stock.min5KLine.length; iIndex++)
                if(stock.min5KLine[iIndex].balancePrice <= 0.0F)
                    if(stock.min5KLine[iIndex].totalAmount > 0L)
                        stock.min5KLine[iIndex].balancePrice = (float)(stock.min5KLine[iIndex].totalMoney / (double)stock.min5KLine[iIndex].totalAmount);
                    else
                    if(iIndex > 0)
                        stock.min5KLine[iIndex].balancePrice = stock.min5KLine[iIndex - 1].balancePrice;
                    else
                        stock.min5KLine[iIndex].balancePrice = stock.min5KLine[iIndex].closePrice;

            if(hisStatus.length > 0 && 2 == m_application.iCurrentPage && m_application.strCurrentCode.equals(request.sCode))
                m_application.repaint();
        }
        catch(MalformedURLException ex) {
          
            if(bDebug)
                ex.printStackTrace();
        }
        catch(IOException ex) {
      
            if(bDebug)
                ex.printStackTrace();
        }
        catch(Exception ex) {
          
            if(bDebug)
                ex.printStackTrace();
        }
    }

    /**
     * 排序
     * @param order
     * @param left
     * @param right
     */
    public static void quickSort(ProductInfoVO order[], int left, int right) {
        if(left < right) {
            ProductInfoVO tmp = order[left];
            int i = left;
            for(int j = right; i < j;) {
                while(i < j && order[j].code.compareTo(tmp.code) > 0) {
                	j--;
                }
                    
                if(i < j){
                    order[i++] = order[j];
                }
                for(; i < j && order[i].code.compareTo(tmp.code) <= 0; i++){
                	
                }
                if(i < j){
                	 order[j--] = order[i]; 
                }
            }

            order[i] = tmp;
            quickSort(order, left, i - 1);
            quickSort(order, i + 1, right);
        }
    }
}
