// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst noinners nonlb 
// S//source File Name:   RequestUtil.java

package sam.bee.stock.service.vo;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO, CMDProductInfoVO, CMDSortVO, CMDQuoteVO, 
//            CMDBillVO, CMDMinVO, CMDMarketSortVO, CMDByTimeVO, 
//            ProductInfoListVO

public class RequestUtil {

	/**
	 * 
	 */
	public static final byte CMD_TEST_CONNECT = 0;

	/**
	 * 
	 */
	public static final byte CMD_CODELIST = 1;

	/**
	 * 
	 */
	public static final byte CMD_QUOTE = 2;

	/**
	 * 
	 */
	public static final byte CMD_CLASS_SORT = 3;

	/**
	 * 
	 */
	public static final byte CMD_MIN_DATA = 4;

	/**
	 * 
	 */
	public static final byte CMD_BILL_DATA = 5;

	/**
	 * 
	 */
	public static final byte CMD_TRADETIME = 6;

	/**
	 * 
	 */
	public static final byte CMD_DATE = 7;

	/**
	 * 
	 */
	public static final byte CMD_MARKET_SORT = 8;

	/**
	 * 
	 */
	public static final byte CMD_BYTIME = 9;

	/**
	 * 
	 */
	public static final byte CMD_MIN_LINE_INTERVAL = 10;

	private RequestUtil() {
	}

	/**
	 * @param cmd
	 * @param socket
	 * @throws IOException
	 */
	public static void sendRequest(CMDVO cmd, Socket socket) throws IOException {
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		ByteArrayOutputStream array = new ByteArrayOutputStream();
		DataOutputStream outputArray = new DataOutputStream(array);
		switch (cmd.cmd) {

		case CMD_CODELIST: // '\001'
			CMDProductInfoVO cmdList = (CMDProductInfoVO) cmd;
			outputArray.writeByte(1);
			outputArray.writeInt(cmdList.date);
			outputArray.writeInt(cmdList.time);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;

		case CMD_QUOTE: // '\002'
			CMDQuoteVO cmdQuote = (CMDQuoteVO) cmd;
			outputArray.writeByte(2);
			outputArray.writeByte(cmdQuote.isAll);
			outputArray.writeInt(cmdQuote.codeList.length);
			for (int i = 0; i < cmdQuote.codeList.length; i++) {
				outputArray.writeUTF(cmdQuote.codeList[i][0]);
				outputArray.writeUTF(cmdQuote.codeList[i][1]);
			}

			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;

		case CMD_CLASS_SORT: // '\003'
			CMDSortVO cmdSort = (CMDSortVO) cmd;
			outputArray.writeByte(3);
			outputArray.writeByte(cmdSort.sortBy);
			outputArray.writeByte(cmdSort.isDescend);
			outputArray.writeInt(cmdSort.start);
			outputArray.writeInt(cmdSort.end);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;

		case CMD_MIN_DATA: // '\004'
			CMDMinVO min = (CMDMinVO) cmd;
			outputArray.writeByte(4);
			outputArray.writeUTF(min.code);
			outputArray.writeByte(min.type);
			outputArray.writeInt(min.time);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;

		case CMD_BILL_DATA: // '\005'
			CMDBillVO bill = (CMDBillVO) cmd;
			outputArray.writeByte(5);
			outputArray.writeUTF(bill.code);
			outputArray.writeByte(bill.type);
			outputArray.writeInt(bill.time);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;

		case CMD_TRADETIME: // '\006'
			outputArray.writeByte(6);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;
			
		case CMD_DATE: // '\007'
			outputArray.writeByte(7);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;
			
		case CMD_MARKET_SORT: // '\b'
			CMDMarketSortVO cmdMarketSort = (CMDMarketSortVO) cmd;
			outputArray.writeByte(8);
			outputArray.writeInt(cmdMarketSort.num);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;


		case CMD_BYTIME: // '\t'
			CMDByTimeVO cmdByTime = (CMDByTimeVO) cmd;
			outputArray.writeByte(9);
			outputArray.writeInt(cmdByTime.time);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;
			
		case CMD_MIN_LINE_INTERVAL: // '\n'
			outputArray.writeByte(10);
			outputArray.flush();
			output.write(array.toByteArray());
			output.flush();
			outputArray.close();
			break;		
			
		default:
			break;
		}
	}

	private static byte[] getRepoent(String url) throws MalformedURLException,
			IOException {
		URL page = null;
		page = new URL(url);
		URLConnection urlc = page.openConnection();
		urlc.connect();
		BufferedInputStream inputs = new BufferedInputStream(urlc
				.getInputStream());
		ByteArrayOutputStream array = new ByteArrayOutputStream();
		for (byte by[] = new byte[1024]; inputs.read(by) > 0; array.write(by))
			;
		inputs.close();
		return array.toByteArray();
	}

	/**
	 * get stock info list.
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static ProductInfoListVO getStockInfoList(String url)
			throws MalformedURLException, IOException {
		ByteArrayInputStream arrayInput = new ByteArrayInputStream(getRepoent(url));
		GZIPInputStream gzin = new GZIPInputStream(arrayInput);
		DataInputStream input = new DataInputStream(gzin);
		input.readByte();
		ProductInfoListVO productInfoList = CMDProductInfoVO.getObj(input);
//		 ProductInfoListVO productInfoList = new ProductInfoListVO();
//		ProductInfoVO productInfos[] = new ProductInfoVO[1];
//		  productInfos[0] = new ProductInfoVO();
//          productInfos[0].code ="";
//          productInfos[0].status =0;
//          productInfos[0].fUnit = 10;
//          productInfos[0].name = "1111";
//          productInfos[0].pinyin = new String[]{"S1","S2","S3"};
//          productInfoList.productInfos = productInfos;
		
//		input.close();
//		gzin.close();
		return productInfoList;
	}

	public static void main(String args[]) {
		try {
			Socket socket = new Socket("127.0.0.1", 888);
			CMDMinVO cmd = new CMDMinVO();
			cmd.code = "G0509";
			sendRequest(cmd, socket);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			System.out.println(input.readByte());
			System.out.println(input.readUTF());
			System.out.println(input.readByte());
			System.out.println(input.readInt());
			Object objs[] = CMDMinVO.getObj(input);
			for (int i = 0; i < objs.length; i++)
				System.out.println(objs[i]);

			System.out.println("2");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
