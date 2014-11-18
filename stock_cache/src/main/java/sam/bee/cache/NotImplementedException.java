package sam.bee.cache;

public class NotImplementedException extends RuntimeException {

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public NotImplementedException(java.lang.Class clazz) {
		super(clazz.getName() + " not yet implemented.");

	}

}
