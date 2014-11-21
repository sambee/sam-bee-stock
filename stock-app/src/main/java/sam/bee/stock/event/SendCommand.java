package sam.bee.stock.event;

import java.util.Date;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.ReceiveThread;
import sam.bee.stock.gui.SendThread;
import sam.bee.stock.service.vo.Req;

@SuppressWarnings("deprecation")
public class SendCommand implements Runnable, IStockLoader{

	//SendThread sendThread;
	ReceiveThread receiveThread;
	public Application m_AppMain;
	ReceiveCommand m_cmdReceiver;
	Thread sendCmd ;
	public SendCommand(Application app){
		m_AppMain = app;
		//sendThread = new SendThread(m_AppMain);
		//sendThread.start();
//		receiveThread = new ReceiveThread(this);
//		receiveThread.start();
		
		sendCmd = new Thread(this);
		sendCmd.start();
		m_cmdReceiver = new ReceiveCommand(this);
		m_cmdReceiver.start();
	}
	
	public void start(){
//		sendThread = new SendThread(m_AppMain);
//		sendThread.start();
//		receiveThread = new ReceiveThread(this);
//		receiveThread.start();
		run();
	}
	
	public void stop(){
		//sendThread.askForData(null);	
		sendCmd.interrupt();
		sendCmd = null;
		receiveThread = null;
	}
	
	public void askForData(Req packet){
		System.out.println("[askForData] code=" + packet.getCmd() +  " time ="  );
		//sendThread.askForData(packet);
	}
	
	public void askForRealQuote(String code, Date time){
		System.out.println("[askForRealQuote] code=" + code +  " time =" + time );
		//sendThread.askForRealQuote(code, time);
	}

	public void run() {
		
		
	}
}
