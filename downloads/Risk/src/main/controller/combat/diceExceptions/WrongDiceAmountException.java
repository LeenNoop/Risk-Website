package main.controller.combat.diceExceptions;

/**
 *
 * @author Daniel Parekh
 *
 */
public class WrongDiceAmountException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public WrongDiceAmountException(String msg) {
		super(msg);
	}
}