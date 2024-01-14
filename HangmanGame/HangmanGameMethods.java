/*
 * Rye Stahle-Smith
 * Personal Projects: Hangman Game Methods
 * 10/6/23
 * 
 * Objective: This class allows for various methods and variables to be utilized inside the HangmanGame program.
 */
package HangmanGame;

import java.util.Random;
import java.util.Scanner;
public class HangmanGameMethods {
	// Global variables
	private final String[] hangmanFigures = new String[] {
			"  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
		    "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
		    "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
		    "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
		    "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
		    "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
		    "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="};
	private final String[] wordBank = new String[] { "apple", "banana", "cherry", "date", "elderberry",
			"fig", "grape", "honeydew", "kiwi", "lemon", "mango", "nectarine", "orange", 
			"pear", "quince", "raspberry", "strawberry", "tangerine", "uvaia", "watermelon",
			"ximenia", "zucchini", "car", "bicycle", "motorcycle", "train", "bus", "truck", 
			"scooter", "helicopter", "submarine", "tractor", "ambulance", "airplane", "canoe", "sailboat", 
			"rocket", "skateboard", "ambulance", "forklift", "jeep", "hovercraft", "segway", "cheesecake", 
			"doughnut", "eclair", "fruitcake", "gingerbread", "jellybeans", "marshmallow", "nutella", 
			"tiramisu", "cookies", "Argentina", "Brazil", "Canada", "Denmark", "Egypt",
			"France", "Germany", "Hungary", "Ireland", "Japan", "Kenya", "Luxembourg", "Mexico", 
			"Norway", "Oman", "Peru", "Qatar", "Russia", "Sweden", "Thailand",
			"Ukraine", "Vietnam", "Wales", "Yemen", "Zimbabwe" };
	private Random rand = new Random();
	private Scanner keyboard = new Scanner(System.in);
	private int incorrectAns = 0;
	private int correctAns = 0;
	private String currWord;
	private char[] guessedLetters = new char[26];
	private int numLettersGuessed = 0;
	private int matchingLetters = 0;
	
	public void run() {
		// Local variables
		char userInput;
		
		// System prints welcome message
		System.out.println("Welcome to Virtual Hangman!");
		
		while (true) {
			System.out.print(">>>Press ENTER to begin a new game...<<<");
			
			if (keyboard.nextLine().isBlank()) break;
			else System.out.println("***Invalid input detected...***");
		}
		
		while (true) {
			System.out.println("***Starting a new game...***");
			gameSetup();
			
			for (int i = 0; winLoseCheck() == 0; i++) {
				mapSetup();
				guessLetter(i);
			}
			
			if (winLoseCheck() == 1) {
				mapSetup();
				System.out.println("You solved the word!");
			}
			else if (winLoseCheck() == -1) System.out.println("Darn! It seems you've lost this time around...");
			
			System.out.print("Want to play again? y/n: ");
			
			userInput = keyboard.next().charAt(0);
			
			if (Character.toLowerCase(userInput) == 'n') {
				System.out.println("***Quitting game...***");
				System.exit(0);
			} else {
				incorrectAns = 0;
				correctAns = 0;
				guessedLetters = new char[26];
				numLettersGuessed = 0;
			}
		}
	}
	
	public void gameSetup() {
		// Local variables
		int wordLocation = rand.nextInt(wordBank.length);
		currWord = wordBank[wordLocation];
	}
	
	public void mapSetup() {
		// Local variables
		boolean charFound = false;
		
		// This section sets up the hangman figure
		System.out.println(hangmanFigures[incorrectAns]);
		
		// This section sets up the unsolved word
		for (int i = 0; i < currWord.length(); i++) {
			for (int j = 0; j < guessedLetters.length; j++) {
				if (currWord.charAt(i) == guessedLetters[j]) {
					System.out.print(currWord.charAt(i));
					charFound = true;
					break;
				}
			}
			
			if (charFound == false) System.out.print("_");
			else {
				charFound = false;
			}
			
			if (i != currWord.length() - 1) System.out.print(" ");
		}
		
		System.out.println("\n");
	}
	
	public void guessLetter(int numOfGuesses) {
		// Local variables
		char userInput;
		boolean newLetter = true;
		
		if (numOfGuesses == 0) System.out.print("Enter a letter that you would like to guess: ");
		else System.out.print("Enter another letter that you would like to guess: ");
		
		while (true) {
			userInput = keyboard.next().charAt(0);
			
			for (int i = 0; i < guessedLetters.length; i++) {
				if (Character.toLowerCase(userInput) == guessedLetters[i]) {
					System.out.println("You've already guessed this letter!");
					System.out.print("Please enter a new letter that you would like to guess: ");
					newLetter = false;
					break;
				}
			}
			
			if (newLetter) {
				answerCheck(userInput);
				break;
			} else newLetter = true;
		}
	}
	
	public void answerCheck(char letter) {
		// Local variables
		boolean charIncorrect = true;
		
		for (int i = 0; i < currWord.length(); i++) {
			if (Character.toLowerCase(currWord.charAt(i)) == letter) {
				matchingLetters++;
				charIncorrect = false;
			}	
		}
		
		guessedLetters[numLettersGuessed] = letter;
		
		if (charIncorrect) {
			System.out.println("***The letter \"" + letter + "\" does not exist in this word.***");
			incorrectAns++;
		} else {
			System.out.println("***The letter \"" + letter + "\" exists in this word.***");
			for (int i = 0; i < matchingLetters; i++) {
				correctAns++;
			}
			matchingLetters = 0;
		}
		
		numLettersGuessed++;
	}
	
	public int winLoseCheck() {
		if (correctAns == currWord.length()) return 1;
		else if (incorrectAns == hangmanFigures.length) return -1; 
		else return 0;
	}
}
