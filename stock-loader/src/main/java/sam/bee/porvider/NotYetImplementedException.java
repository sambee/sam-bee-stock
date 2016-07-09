package sam.bee.porvider;

public class NotYetImplementedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public NotYetImplementedException(java.lang.Class clazz) {
		super(clazz.getName() + " not yet implemented.");

	}

}
