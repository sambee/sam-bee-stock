package sam.bee.stock.event;

public class Message {

	private static final Object sPoolSync = new Object();
	private static Message sPool;
	private static int sPoolSize = 0;
	private static final int MAX_POOL_SIZE = 10;

	Message next;
	int what;
	Object arg1;
	Object arg2;
	Object obj;
	Handler target;
	long when;
	Object data;
	Runnable callback;
	Messenger replyTo;
	
	/**
	 * Return a new Message instance from the global pool. Allows us to avoid
	 * allocating new objects in many cases.
	 */
	public static Message obtain() {
		synchronized (sPoolSync) {
			if (sPool != null) {
				Message m = sPool;
				sPool = m.next;
				m.next = null;
				sPoolSize--;
				return m;
			}
		}
		return new Message();
	}

    /**
     * Same as {@link #obtain()}, but sets the value for the <em>target</em> member on the Message returned.
     * @param h  Handler to assign to the returned Message object's <em>target</em> member.
     * @return A Message object from the global pool.
     */
    public static Message obtain(Handler h) {
        Message m = obtain();
        m.target = h;

        return m;
    }
    
	public void recycle() { 
    synchronized (sPoolSync) { 
        if (sPoolSize < MAX_POOL_SIZE) { 
            clearForRecycle(); 
            next = sPool; 
            sPool = this; 
            sPoolSize++; 
        } 
    } 
}	void clearForRecycle() {
		what = 0;
		arg1 = 0;
		arg2 = 0;
		obj = null;
		replyTo = null;
		when = 0;
		target = null;
		callback = null;
		data = null;
	}

}
