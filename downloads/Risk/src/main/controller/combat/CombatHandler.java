package main.controller.combat;

import main.controller.combat.diceExceptions.WrongDiceAmountException;

/**
 * Combat Handler
 *
 * Used to simulate combat between specified numbers of units.
 * @author Daniel Parekh, Adnaan Hussain
 *
 */
public class CombatHandler {

	private Dice dice;
	private int[] points;

	public CombatHandler() {
		dice = new Dice();
	}

	/**
	 * method to simulate a fight.
	 *
	 * @param playerANumDice Number of Attacking units/dice
	 * @param playerBNumDice Number of Defending units/dice
	 * @return ???????????????????????????????????????
	 */
	public int[] fight(int playerANumDice, int playerBNumDice) throws WrongDiceAmountException {
		//checks if incoming player dice numbers are appropriate
		if (playerANumDice >= 1 && playerANumDice <= 3) {
			if (playerBNumDice >= 1 && playerBNumDice <= 2) {
				// Error not thrown - checks for null/empty/invalid input
			} else {

			}
		} else {
			throw new WrongDiceAmountException("Attacking player can only use between 1 to 3 die.");
		}

		int[] playerARolls = dice.rollMultiple(playerANumDice);
		int[] playerBRolls = dice.rollMultiple(playerBNumDice);

		//return array [0]A loss, [1]B loss
		this.points = new int[]{0, 0};
		int numDice = min(playerANumDice,playerBNumDice);
		//Compare Dice Results
		loopThoughForNum(playerARolls, playerBRolls, numDice);
		return points;
	}

	/**
	 * This replaces a duplicate loop: for the given criteria it will loop for NumDice and compare the index'd dice
	 * from playerARolls and playerBRolls
	 * @param playerARolls int[] array of rolls from player A, size(1->3)
	 * @param playerBRolls int[] array of rolls from player A, size(1->2)
	 * @param numDice lowest number of dice rolls
	 */
	private void loopThoughForNum(int[] playerARolls, int[] playerBRolls, int numDice) {
		for (int i = 0; i < numDice; i++) { //loops through common dice
			System.out.println("\tA Roll: " + playerARolls[i] + "\t B Rolls: " + playerBRolls[i]);
			final int A = 0, B = 1;
			if (playerARolls[i] > playerBRolls[i]) { // A beats B
				System.out.println("\t\tA Wins");
				points[B]--;
			} else { //B beats A
				System.out.println("\t\tB Wins");
				points[A]--;
			}
		}
	}

	/**calculates minimum of 2 input values
	 *
	 *return minimum value
	 */
	int min(int A, int B){
		return (A<=B) ? A : B;
	}
}