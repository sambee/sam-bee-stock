package sam.bee.cache;

public class NotImplementedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public NotImplementedException(java.lang.Class clazz) {
		super(clazz.getName() + " not yet implemented.");

	}

}
