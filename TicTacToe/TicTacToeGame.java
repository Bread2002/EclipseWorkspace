/*
 * Rye Stahle-Smith
 * Personal Projects: Tic-Tac-Toe Game
 * 10/25/23
 * 
 * Objective: This program is a simple text-based Tic-Tac-Toe game that allows for up to two players can play.
 * The game is also playable with one user, as the user will play the computer.
 */

package TicTacToe;

public class TicTacToeGame {

	public static void main(String[] args) {
		// Creates a new instance of the TTTMethods class
		TTTMethods instance = new TTTMethods();
		
		// System runs game
		instance.run();
	}

}
