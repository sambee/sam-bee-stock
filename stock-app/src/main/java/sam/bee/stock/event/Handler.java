package sam.bee.stock.event;

public class Handler {

	Looper mLooper;
	public Handler(Looper looper)
	{
		mLooper = looper;
	}
	
	public Message obtain(){
		return Message.obtain(this);
	}
	
	public void handleMessage(Message msg){}
	
	public void sendMessage(Message msg){
		mLooper.mQueue.send(msg);
	}
}
