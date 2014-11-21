package sam.bee.stock.event;

import java.util.Vector;

public final class MessageQueue {

	// Message mMessages;
	 @SuppressWarnings("rawtypes")
	private Vector mMessages = new Vector();
	private boolean quitAllowed;
	 
	MessageQueue(boolean quitAllowed) {
		this.quitAllowed = quitAllowed;
	}
	
	@SuppressWarnings("unchecked")
	public void send(Message msg){
		synchronized (mMessages) {
			mMessages.add(msg);
			mMessages.notify();
		}		
		
	}
	
	
	public Message next() {
		   for (;;) {
//			   
//			   try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			   if(quitAllowed){
//				  return msg;
//			   }
//			   if(mMessages!=null){
//				   Message msg = mMessages;
//				   mMessages =null;
//				   return msg;
//				   
//			   }


			if(quitAllowed && mMessages.isEmpty() ){
				return null;
			}
			
			if(mMessages.isEmpty()){
				   try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			else{
				return (Message) mMessages.remove(0);
			}	
			
			
			}


		
	 }
}
