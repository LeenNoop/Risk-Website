package main.controller.mapBuilder.mapBuilderExceptions;
/**
 *
 * @author Ben Owen
 *
 */
public class DifferentSizedImageException extends Exception{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DifferentSizedImageException() {

	}

	public DifferentSizedImageException(String msg) {
		super(msg);
	}
}
