package sam.bee.stock.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sam.bee.stock.loader.ILoaderAPI;
import sam.bee.stock.loader.impl.LoaderApiImpl;

/**
 * 
 *
 */
public class EventTest 
{
	
	public Thread m_MainThread;
    public static void main( String[] args )
    { 
    	new EventTest().run();
    }
    
    public void run(){
    	
    	m_MainThread = mainThread();
    	
    	m_MainThread.start();
    	updatedStockThread();
    	Looper.loop();
    	m_MainThread = null;	
		System.err.println("Main thread has been exit.");
		System.exit(0);
    
    }
    
    public void search(Handler handler, String[] params){
    	final String CODE = "code";
    	final String HISTORY = "history";
    	final String EXCLUDE = "exclude";
    	final String SHANGHAI = "shanghai";
    	if(params.length>1){
    		//System.out.println("Search:" + params[1]);
    		final ILoaderAPI api = new LoaderApiImpl();
    		try {
				List<Map<String, String>> stockList = api.getShangHaiStockList();
				List<Map<String, String>> list = new ArrayList();
				for(Map<String,String> m : stockList){
					String stockCode = m.get("STOCK_CODE");
					String stockName = m.get("STOCK_NAME");
								
					
					Message msg = handler.obtain();
					msg.what = 123;
					msg.obj = list;		    						
					handler.sendMessage(msg);		    						
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else{
    		System.err.print("Not enough paraments, example find 60000");
    	}
    	
    }

    public String[] getCommand(String line){
    	return line.replaceAll("  ", " ").split(" ");
    }
    
    Handler m_Handler ;
    private Thread mainThread(){
    	//the main thread just wait for application exit.
    	Looper.prepareMainLooper();
    	m_Handler = new Handler(Looper.getMainLooper()){
    		public void handleMessage(Message msg){    			
    			if("quit".equals(msg.obj)){
    				//System.exit(0);
    				sendMessage(null);
    			}
    			if(String.valueOf(msg.obj).startsWith("find")){
    				search(m_Handler, getCommand(String.valueOf(msg.obj)));
    			}
    			else{
    				System.out.println( "system receive a command: '" 
    						+ msg.obj  
    						+ "', but not parse this command, " 
    						+ "example: find 60000 \n"
    						+ "         quit"
    						
    						);
    			}
    		}
    	};
    	
    	Thread th = new Thread(new Runnable() {

			@SuppressWarnings("resource")
			public void run() {
				while(m_MainThread!=null && m_MainThread.isAlive()){	
					System.out.println("Plean enter a command:");
			        Scanner sc = new Scanner(System.in);
			        String line = "";			  			        
			        
			        while((line = sc.nextLine()).length()==0);
			        
					Message msg = m_Handler.obtain();
					msg.what = 123;
					msg.obj = line;		    						
					m_Handler.sendMessage(msg);

			}
				
			}
    		
    	});
    	return th;
		
    }
    
    private void sleep(long millis){
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    private void updatedStockThread(){
    	new Thread(new Runnable() {
			
			public void run() {
				while(m_MainThread!=null){
		    		//System.out.print(".");
		    		sleep(300);
		    	}
		    	
		    	System.out.println("sub thread has bee stop.");
				
			}
		}).start();
    
    	
//    	final FileCache cache = new FileCache("../stock-data",1800000);
//    	final String CODE = "code";
//    	final String HISTORY = "history";
//    	final String EXCLUDE = "exclude";
//    	final String SHANGHAI = "shanghai";
//    	final ILoaderAPI api = new LoaderApiImpl();
//    	
//       	Thread th = new Thread(new Runnable() {
//  
//       		    
//    			public void run() {
//    				
//    				
//					Looper.prepare();
//					
//					final Handler handler = new Handler(Looper.myLooper()){
//						public void handleMessage(Message msg){
//							
//							//System.out.println(Thread.currentThread().getName()  +  "  " + msg.obj);
//							//System.exit(0);
//						}
//					};
//					
//					 new Thread(new Runnable() {
//
//						public void run() {
//			  				while(true){	
//			  					List<Map<String, String>> stockList;
//								try {
//									stockList = cache.getList(CODE, SHANGHAI);
//									if(stockList==null){
//										stockList = api.getShangHaiStockList();
//										cache.set(stockList, SHANGHAI);
//									}
//									if(stockList!=null){
//										for(Map<String,String> m : stockList){
//					  						String stockCode = m.get("STOCK_CODE");
//					  						String stockName = m.get("STOCK_NAME");
//					  						List<Map<String, String>> list = null;
//					  						if(!cache.exist(HISTORY, stockCode) && !cache.existIgnoreTime(EXCLUDE, stockCode)){	
//					  							list = (List<Map<String,String>>)api.getStockHistory(stockCode);			  							
//					  						}
//					  						
//					  						Message msg = handler.obtain();
//				    						msg.what = 123;
//				    						msg.obj = list;		    						
//				    						handler.sendMessage(msg);		    						
//					  					}	
//									}
//									else{
//										System.out.println("Can not load stock(s) from " + cache.getBasicCacheFilePath());
//									}
//									
//								} catch (Exception e) {
//									
//									e.printStackTrace();
//								}		  			        
//			  			        
//			  						    						
//
//		    				}
//							
//						}
//						 
//					 }).start();
//  
//    				Looper.loop();
//    				
//    			}
//    		});
//        	th.start();
    }
}
