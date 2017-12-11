package main.controller.mapBuilder.mapBuilderExceptions;

/**
 *
 * @author Ben Owen 
 *
 */
public class ToManyTerritoriesException extends Exception{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ToManyTerritoriesException(String msg) {
		super(msg);
	}
}
