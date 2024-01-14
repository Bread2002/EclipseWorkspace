/*
 * Rye Stahle-Smith
 * Personal Projects: Tic-Tac-Toe Methods
 * 10/25/23
 * 
 * Objective: This class allows for various methods and variables to be utilized inside the TicTacToeGame program.
 */

package TicTacToe;

import java.util.Random; // Imports random generator
import java.util.Scanner; // Imports keyboard scanner
public class TTTMethods {
	// Global variables
	private Random rand = new Random(); // Initializes a new random generator.
	private Scanner keyboard = new Scanner(System.in); // Creates a new keyboard.
	private char aiSymbol; // Utilized to keep track of the selected symbol for the Computer.
	private char user1Symbol; // Utilized to keep track of the selected symbol for User 2.
	private char user2Symbol; // Utilized to keep track of the selected symbol for User 2.
	private int gameType = 0; // Contains the current gameType; Remains zero if match type is undefined.
	private char[][] mapArray = new char[16][16]; // Contains each character necessary in printing the map.
	private char[][] mapSpaces = new char[3][3]; // Contains each character ('X' or 'O') that is currently placed inside the map.
	private char[][] xChar = new char[][] { // Contains the characters utilized in placing the 'X' on the board.
	    {'\\', ' ', ' ', '/'},
	    {' ', '\\', '/', ' '},
	    {' ', '/', '\\', ' '},
	    {'/', ' ', ' ', '\\'}
	}; 
	private char[][] oChar = new char[][] { // Contains the characters utilized in placing the 'O' on the board.
	    {'|', '-', '-', '|'},
	    {'|', ' ', ' ', '|'},
	    {'|', ' ', ' ', '|'},
	    {'|', '-', '-', '|'}
	};
	
	public void run() {
		System.out.println("Welcome to Tic-Tac-Toe!");
		
		setGameType();
		startGame();
	}
	
	public void setGameType() {
		// Local variables
		String userInput;
		
		while (gameType == 0) {
			System.out.print("Press '1' to start a one player match against the computer, or press '2' for a local two player match: ");
			
			userInput = keyboard.nextLine();
			
			if (userInput.equals("1")) {
				System.out.println("***Beginning a new 1vsPC match...***");
				gameType = Integer.parseInt(userInput);
			} else if (userInput.equals("2")) {
				System.out.println("***Beginning a new local 1vs1 match...***");
				gameType = Integer.parseInt(userInput);
			} else System.out.println("***Invalid input detected...***");
		}
	}
	
	public void startGame() {
		// Local variables
		int playerTurn = 1;
		char userInput;
		
		while (true) {
			calcPlayerSymbols();
			setupMap();
			
			while (checkForWin() == 0) {
				printMap();
				if (playerTurn == 1) {
					requestTurn(playerTurn);
					playerTurn++;
				} else {
					requestTurn(playerTurn);
					playerTurn--;
				}
			}
			
			printMap();
			if (checkForWin() == 1) {
				if (gameType == 1) System.out.println("User wins this match!");
				else System.out.println("User 1 wins this match!");
			} else if (checkForWin() == 2) {
				if (gameType == 1) System.out.println("The computer wins this match!");
				else System.out.println("User 2 wins this match!");
			} else {
				System.out.println("It's a tie! No one wins this round...");
			}
			
			while (true) {
				System.out.print("Want to play again? y/n: ");
				
				userInput = keyboard.next().charAt(0);
				keyboard.nextLine();
				
				if (Character.toLowerCase(userInput) == 'n') {
					System.out.println("***Quitting program...***");
					System.exit(0);
				} else if (Character.toLowerCase(userInput) == 'y') {
					System.out.println("***Starting a new game...***");
					
					// Resets necessary variables
					mapArray = new char[16][16];
					mapSpaces = new char[3][3];
					playerTurn = 1;
					gameType = 0;
					setGameType();
					break;
				} else System.out.println("***Invalid input detected...***");
			}
		}
	}
	
	public void setupMap() {
		for (int i = 0; i < mapArray.length; i++) {
			for (int j = 0; j < mapArray[i].length; j++) {
				if (j == 4 || j == 5 || j == 10 || j == 11) {
					if (i == 4 || i == 5 || i == 10 || i == 11) mapArray[i][j] = '+';
					else mapArray[i][j] = '|';
				} else if (i == 4 || i == 5 || i == 10 || i == 11) {
					if (j == i || j + 1 == i + 1) {
						mapArray[i][j] = '+';
					} else mapArray[i][j] = '-';
				} else mapArray[i][j] = ' ';
			}
		}
	}
	
	public void printMap() {
		for (int i = 0; i < mapArray.length; i++) {
			for (int j = 0; j < mapArray[i].length; j++) {
				System.out.print(mapArray[i][j]);
			}
			System.out.println();
		}
	}
	
	public void calcPlayerSymbols() {
		// Local variables
		int coinFlip = rand.nextInt(2);
		
		if (gameType == 1) {
			if (coinFlip == 1) {
				user1Symbol = 'X';
				aiSymbol = 'O';
				System.out.println("User, you're 'X'! \nThe computer is 'O'!");
			} else {
				user1Symbol = 'O';
				aiSymbol = 'X';
				System.out.println("User, you're 'O'! \nThe computer is 'X'!");
			}
		} else {
			if (coinFlip == 1) {
				user1Symbol = 'X';
				user2Symbol = 'O';
				System.out.println("User 1, you're 'X'! \nUser 2, you're 'O'!");
			} else {
				user1Symbol = 'O';
				user2Symbol = 'X';
				System.out.println("User 1, you're 'O'! \nUser 2, you're 'X'!");
			}
		}
	}
	
	public void requestTurn(int currentTurn) {
		//Local variables
		int xAxis;
		int yAxis;
		char throwaway;
		
		if (currentTurn == 1) {
			while (true) {
				if (gameType == 1) System.out.print("Enter the position you'd like to place your '" + user1Symbol + "' using row and column number (R#, followed by a space, C#): ");
				else System.out.print("User 1: Enter the position you'd like to place your '" + user1Symbol + "' using row and column number (R#, followed by a space, C#): ");
				
				xAxis = keyboard.nextInt() - 1;
				yAxis = keyboard.nextInt() - 1;
				keyboard.nextLine();
				
				if ((xAxis >= 0 && xAxis < mapSpaces.length) && (yAxis >= 0 && yAxis < mapSpaces[0].length)) {
					if ((gameType == 1 && mapSpaces[xAxis][yAxis] != user1Symbol && mapSpaces[xAxis][yAxis] != aiSymbol) || (gameType == 2 && mapSpaces[xAxis][yAxis] != user1Symbol && mapSpaces[xAxis][yAxis] != user2Symbol)) {
						mapSpaces[xAxis][yAxis] = user2Symbol;
						break;
					} else System.out.println("***Invalid position referenced...***");
				} else System.out.println("***Invalid position referenced...***");
			}
			
			mapSpaces[xAxis][yAxis] = user1Symbol;
			if (user1Symbol == 'X') {
				for (int i = 6 * xAxis; (i - (6 * xAxis)) < xChar.length; i++) {
					for (int j = 6 * yAxis; (j - (6 * yAxis)) < xChar[0].length; j++) {
						mapArray[i][j] = xChar[i - (6 * xAxis)][j - (6 * yAxis)];
					}
				}
			} else {
				for (int i = 6 * xAxis; (i - (6 * xAxis)) < oChar.length; i++) {
					for (int j = 6 * yAxis; (j - (6 * yAxis)) < oChar[0].length; j++) {
						mapArray[i][j] = oChar[i - (6 * xAxis)][j - (6 * yAxis)];
					}
				}
			}
		} else if (currentTurn == 2 && gameType == 1) {
			while (true) {
				xAxis = rand.nextInt(3);
				yAxis = rand.nextInt(3);
				
				if (mapSpaces[xAxis][yAxis] != user1Symbol && mapSpaces[xAxis][yAxis] != aiSymbol) {
					mapSpaces[xAxis][yAxis] = aiSymbol;
					break;
				} 
			}
			
			System.out.println("The computer chose to place their " + aiSymbol + " at position '" + (xAxis + 1) + " " + (yAxis + 1) + "'.");
			
			if (aiSymbol == 'X') {
				for (int i = 6 * xAxis; (i - (6 * xAxis)) < xChar.length; i++) {
					for (int j = 6 * yAxis; (j - (6 * yAxis)) < xChar[0].length; j++) {
						mapArray[i][j] = xChar[i - (6 * xAxis)][j - (6 * yAxis)];
					}
				}
			} else {
				for (int i = 6 * xAxis; (i - (6 * xAxis)) < oChar.length; i++) {
					for (int j = 6 * yAxis; (j - (6 * yAxis)) < oChar[0].length; j++) {
						mapArray[i][j] = oChar[i - (6 * xAxis)][j - (6 * yAxis)];
					}
				}
			}
		} else {
			while (true) {
				System.out.print("User 2: Enter the position you'd like to place your '" + user2Symbol + "' using row and column number (R#, followed by a space, C#): ");
			
				xAxis = keyboard.nextInt() - 1;
				yAxis = keyboard.nextInt() - 1;
				keyboard.nextLine();
				
				if ((xAxis >= 0 && xAxis < mapSpaces.length) && (yAxis >= 0 && yAxis < mapSpaces[0].length)) {
					if (mapSpaces[xAxis][yAxis] != user1Symbol && mapSpaces[xAxis][yAxis] != user2Symbol) {
						mapSpaces[xAxis][yAxis] = user2Symbol;
						break;
					} 
				} else System.out.println("***Invalid position referenced...***");
			}
			
			mapSpaces[xAxis][yAxis] = user2Symbol;
			if (user2Symbol == 'X') {
				for (int i = 6 * xAxis; (i - (6 * xAxis)) < xChar.length; i++) {
					for (int j = 6 * yAxis; (j - (6 * yAxis)) < xChar[0].length; j++) {
						mapArray[i][j] = xChar[i - (6 * xAxis)][j - (6 * yAxis)];
					}
				}
			} else {
				for (int i = 6 * xAxis; (i - (6 * xAxis)) < oChar.length; i++) {
					for (int j = 6 * yAxis; (j - (6 * yAxis)) < oChar[0].length; j++) {
						mapArray[i][j] = oChar[i - (6 * xAxis)][j - (6 * yAxis)];
					}
				}
			}
		}
	}
	
	public int checkForWin() {
		if (gameType == 1) {
			if (mapSpaces[0][0] == aiSymbol && mapSpaces[1][1] == aiSymbol && mapSpaces[2][2] == aiSymbol) return 2;
			else if (mapSpaces[0][0] == user1Symbol && mapSpaces[1][1] == user1Symbol && mapSpaces[2][2] == user1Symbol) return 1;
			else if (mapSpaces[2][0] == aiSymbol && mapSpaces[1][1] == aiSymbol && mapSpaces[0][2] == aiSymbol) return 2;
			else if (mapSpaces[2][0] == user1Symbol && mapSpaces[1][1] == user1Symbol && mapSpaces[0][2] == user1Symbol) return 1;
			else {
				for (int i = 0; i < mapSpaces.length; i++) {
					if (mapSpaces[i][0] == aiSymbol && mapSpaces[i][1] == aiSymbol && mapSpaces[i][2] == aiSymbol) return 2;
					else if (mapSpaces[i][0] == user1Symbol && mapSpaces[i][1] == user1Symbol && mapSpaces[i][2] == user1Symbol) return 1;
					else if (mapSpaces[0][i] == aiSymbol && mapSpaces[1][i] == aiSymbol && mapSpaces[2][i] == aiSymbol) return 2;
					else if (mapSpaces[0][i] == user1Symbol && mapSpaces[1][i] == user1Symbol && mapSpaces[2][i] == user1Symbol) return 1;
				}
			}
		} else {
			if (mapSpaces[0][0] == user2Symbol && mapSpaces[1][1] == user2Symbol && mapSpaces[2][2] == user2Symbol) return 2;
			else if (mapSpaces[0][0] == user1Symbol && mapSpaces[1][1] == user1Symbol && mapSpaces[2][2] == user1Symbol) return 1;
			else if (mapSpaces[2][0] == user2Symbol && mapSpaces[1][1] == user2Symbol && mapSpaces[0][2] == user2Symbol) return 2;
			else if (mapSpaces[2][0] == user1Symbol && mapSpaces[1][1] == user1Symbol && mapSpaces[0][2] == user1Symbol) return 1;
			else {
				for (int i = 0; i < mapSpaces.length; i++) {
					if (mapSpaces[i][0] == user2Symbol && mapSpaces[i][1] == user2Symbol && mapSpaces[i][2] == user2Symbol) return 2;
					else if (mapSpaces[i][0] == user1Symbol && mapSpaces[i][1] == user1Symbol && mapSpaces[i][2] == user1Symbol) return 1;
					else if (mapSpaces[0][i] == user2Symbol && mapSpaces[1][i] == user2Symbol && mapSpaces[2][i] == user2Symbol) return 2;
					else if (mapSpaces[0][i] == user1Symbol && mapSpaces[1][i] == user1Symbol && mapSpaces[2][i] == user1Symbol) return 1;
				}
			}
		} 
		
		for (int i = 0; i < mapSpaces.length; i++) {
			for (int j = 0; j < mapSpaces[i].length; j++) {
				if (mapSpaces[i][j] != 'X' && mapSpaces[i][j] != 'O') return 0;
			}
		}
		
		return 3;
	}

}
