/*
 * Rye Stahle-Smith
 * Personal Projects: Hangman Game
 * 10/6/23
 * 
 * Objective: This program allows for the user to engage in a virtual game of hangman. The program will run using various methods and variables 
 * compiled inside the HangmanGameMethods class. The word bank and figures utilized to simulate the hangman are also stored in this class. Since 
 * there are only seven figures, after six incorrect guesses, the user will lose the current game. The user will also be able to play the game 
 * again if they so choose.
 */
package HangmanGame;

public class HangmanGame {

	public static void main(String[] args) {
		// Creates a new instance of HangmanGameMethods class
		HangmanGameMethods instance = new HangmanGameMethods();
		
		// Runs the program
		instance.run();
	}

}
