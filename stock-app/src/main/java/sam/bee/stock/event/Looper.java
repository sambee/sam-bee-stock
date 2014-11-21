package sam.bee.stock.event;

public final class Looper {
 
	 static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
//	 private final int threadLocalHashCode = nextHashCode();  
//	 private static int nextHashCode = 0;  
//	 private static final int HASH_INCREMENT = 0x61c88647;  
	 
	 private static Looper sMainLooper;  // guarded by Looper.class
	 final MessageQueue mQueue;
	 final Thread mThread;
	    
    private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }
    
    /**
     * Return the Looper object associated with the current thread.  Returns
     * null if the calling thread is not associated with a Looper.
     */
    public static Looper myLooper() {
        return sThreadLocal.get();
    }
    
    /**
    * This gives you a chance to create handlers that then reference
    * this looper, before actually starting the loop. Be sure to call
    * {@link #loop()} after calling this method, and end it by calling
    * {@link #quit()}.
    */
  public static void prepare() {
      prepare(true);
  }

  private static void prepare(boolean quitAllowed) {
      if (sThreadLocal.get() != null) {
          throw new RuntimeException("Only one Looper may be created per thread");
      }
      sThreadLocal.set(new Looper(quitAllowed));
  }
	
//    private static synchronized int nextHashCode() {
//        int h = nextHashCode;
//        nextHashCode = h + HASH_INCREMENT;
//        return h;
//    }
//    
//    public static void main(String[] args){
//    	Looper looper = new Looper(new Thread());
//    	Looper looper2 = new Looper(new Thread());
//    	
//    }
  
  /**
   * Initialize the current thread as a looper, marking it as an
   * application's main looper. The main looper for your application
   * is created by the Android environment, so you should never need
   * to call this function yourself.  See also: {@link #prepare()}
   */
  public static void prepareMainLooper() {
      prepare(false);
      synchronized (Looper.class) {
          if (sMainLooper != null) {
              throw new IllegalStateException("The main Looper has already been prepared.");
          }
          sMainLooper = myLooper();
      }
  }

  /** Returns the application's main looper, which lives in the main thread of the application.
   */
  public static Looper getMainLooper() {
      synchronized (Looper.class) {
          return sMainLooper;
      }
  }
  
  /**
   * Run the message queue in this thread. Be sure to call
   * {@link #quit()} to end the loop.
   */
  public static void loop() {
	   final Looper me = myLooper();
       if (me == null) {
           throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
       }
       final MessageQueue queue = me.mQueue;
       for (;;) {
    
    	   Message msg = queue.next();
           if (msg == null) {
               // No message indicates that the message queue is quitting.
        	   System.out.println("msg is empty");
               return;
           }
		 
		   if (msg != null && msg.target != null) {
			   msg.target.handleMessage(msg);
		   }

       }
  }
}
