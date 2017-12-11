package main.controller.combat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Dice class. Emulates dice.
 *
 * @author Daniel Parekh
 *
 */
public class Dice {

	private Random rand;

	public Dice() {
		rand = new Random();
	}

	/**
	 * emulates rolling a number of D6. 
	 *
	 * @param numDice number of dice you want to roll.
	 * @return Results returned in an int[].
	 */
	public int[] rollMultiple(int numDice) {

		int[] arr = new int[numDice];
		for(int i = 0; i < numDice; i++) {
			arr[i] = nextRoll();
		}
		Arrays.sort(arr);
		Collections.reverse(Arrays.asList(arr));
		return arr;
	}

	/**
	 * emulates rolling a single D6 dice
	 * @return int between 1->6
	 */
	private int nextRoll(){
		return rand.nextInt(5) + 1;
	}
}

