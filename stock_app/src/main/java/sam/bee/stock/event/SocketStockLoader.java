package sam.bee.stock.event;

import java.util.Date;

import sam.bee.stock.gui.Application;
import sam.bee.stock.gui.ReceiveThread;
import sam.bee.stock.gui.SendThread;
import sam.bee.stock.service.vo.CMDVO;

public class SocketStockLoader implements IStockLoader{

	SendThread sendThread;
	ReceiveThread receiveThread;
	public Application m_AppMain;
	ReceiveCommand m_cmdReceiver;
	public SocketStockLoader(Application app){
		m_AppMain = app;
		sendThread = new SendThread(m_AppMain);
		sendThread.start();
//		receiveThread = new ReceiveThread(this);
//		receiveThread.start();
		m_cmdReceiver = new ReceiveCommand(this);
		m_cmdReceiver.start();
	}
	
	public void start(){
//		sendThread = new SendThread(m_AppMain);
//		sendThread.start();
//		receiveThread = new ReceiveThread(this);
//		receiveThread.start();
	}
	
	public void stop(){
		sendThread.askForData(null);
		receiveThread = null;
	}
	
	public void askForData(CMDVO packet){
		sendThread.askForData(packet);
	}
	
	public void AskForRealQuote(String code, Date time){
		sendThread.askForRealQuote(code, time);
	}
}
