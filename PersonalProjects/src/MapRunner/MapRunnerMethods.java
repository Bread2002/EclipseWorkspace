/*
 * Rye Stahle-Smith
 * Personal Projects: Map Runner Methods
 * 10/6/23
 * 
 * Objective: This class communicates with the program 'MapRunner.java' by utilizing various methods.
 */
package MapRunner;

import java.util.Scanner; // Imports keyboard scanner
import java.util.Random; // Imports random generator
public class MapRunnerMethods {

	// Global variables
	private String currDifficulty = "Easy";
	private String[] currAchievements = new String[5];
	private char[][][] mapArray;
	private int[] charPosition = {0, 0, 0};
	private int[][] enemyPosition = new int[16][3];
	private String userInput;
	private Random rand = new Random();
	private Scanner keyboard = new Scanner(System.in);
	private final char CHARACTER = 'X';
	private final char ENEMY = '@';
	private final char STAIRS = '^';
	private final char GOAL = 'G';
	
	public void run() {
		mainMenu(true);
		mapViewer(true);
		
		while (true) {
			charMove();
			mapViewer(false);
		}
	}
	
	public String difficulty() {
		if (currDifficulty.equals("Easy")) currDifficulty = "Normal";
		else if (currDifficulty.equals("Normal")) currDifficulty = "Hard";
		else currDifficulty = "Easy";
		
		return currDifficulty;
	}
	
	public String achievChecker(int achievNum) {
		return currAchievements[achievNum];
	}
	
	public void mapViewer(boolean setMap) {
		if (setMap) {
			if (currDifficulty == "Easy") mapArray = new char[3][5][5];
			else if (currDifficulty == "Normal") mapArray = new char[5][10][10];
			else mapArray = new char[7][15][15];
			
			for (int i = 0; i < mapArray.length; i++) {
				for (int j = 0; j < mapArray[i].length; j++) {
					for (int k = 0; k < mapArray[i][j].length; k++) {
						mapArray[i][j][k] = '_';
					}
				}
			}
			
			for (int i = 0; i < mapArray.length; i++) {
				if (i == 0) mapArray[mapArray.length - 1][mapArray[0].length - 1][mapArray[0][0].length - 1] = GOAL;
				if (i < mapArray.length - 1) mapArray[i][mapArray[0].length - 1][mapArray[0][0].length - 1] = STAIRS;
				if (i > 0) {
					enemyPosition[i][1] = rand.nextInt(mapArray[0].length);
					enemyPosition[i][2] = rand.nextInt(mapArray[0][0].length);
					while (enemyPosition[i][1] == 0 && enemyPosition[i][2] == 0) {
						enemyPosition[i][1] = rand.nextInt(mapArray[0].length);
						enemyPosition[i][2] = rand.nextInt(mapArray[0][0].length);
					}
					mapArray[i][enemyPosition[i][1]][enemyPosition[i][2]] = ENEMY;
				}
			}
		}
		
		mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = CHARACTER;
		
		mapPrinter(charPosition[0]);
	}
	
	public void mapPrinter(int refLayer) {
		System.out.println("Current Floor: " + (refLayer + 1));
		for (int i = 0; i < mapArray[refLayer].length; i++) {
			for (int j = 0; j < mapArray[refLayer][i].length; j++) {
				System.out.print(mapArray[refLayer][i][j]);
			}
			System.out.println();
		}
	}
	
	public void mainMenu(boolean startGame) {
		while (true) {
			if (startGame) System.out.print("- Begin Game \n- Difficulty: " + currDifficulty + "\n- Achievements "
					+ "\nUse 'd' to change difficulty, 'a' to check out your earned achievements, or press ENTER to begin: ");
			else {
				System.out.println("***Game paused...***");
				System.out.println("------------------------------------------------------");
				System.out.print("- Continue Game \n- Difficulty: " + currDifficulty + "\n- Achievements "
						+ "\nUse 'd' to change difficulty, 'a' to check out your earned achievements, or press ENTER to continue: ");
			}	
		
			userInput = keyboard.nextLine();
			
			if (userInput.equals("d")) difficulty();
			else if (userInput.equals("a")) {
				System.out.println("Achievements:");
				for (int i = 0; i < 5; i++) {
					if (achievChecker(i) == null) System.out.println((i + 1) + ". ???");
					else System.out.println((i + 1) + ". " + achievChecker(i));
				}
			
				System.out.print(">>>Press ENTER to continue...<<<");
				keyboard.nextLine();
			} else if (userInput.isBlank()) {
				if (startGame) System.out.println("***Game starting...***");
				else System.out.println("***Game resuming...***");
				System.out.println("------------------------------------------------------");
				if (startGame) System.out.println("Your character, X, is marked below. Use stairs, ^, to ascend throughout the map.");
				break;
			}
			else System.out.println("***Invalid input detected***");
		}
		
	}
	
	public void charMove() {
		while (true) {
			System.out.print("Use 'w', 'a', 's', or 'd' to move across the map, or 'm' to return to the main menu: ");
			
			userInput = keyboard.nextLine();
			
			if (userInput.toLowerCase().equals("w")) {
				if (charPosition[1] != 0) {
					mapArray[charPosition[0]][charPosition[1] - 1][charPosition[2]] = mapArray[charPosition[0]][charPosition[1]][charPosition[2]];
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = '_';
					charPosition[1]--;
				} else {
					System.out.println("***Move not registered***");
				}
				break;
			} else if (userInput.toLowerCase().equals("s")) {
				if (charPosition[1] != mapArray[0].length - 1 && mapArray[charPosition[0]][charPosition[1] + 1][charPosition[2]] != '^') {
					mapArray[charPosition[0]][charPosition[1] + 1][charPosition[2]] = mapArray[charPosition[0]][charPosition[1]][charPosition[2]];
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = '_';
					charPosition[1]++;
				} else if (charPosition[1] == mapArray[0].length - 1) {
					System.out.println("***Move not registered***");
				} else {
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = '_';
					charPosition[0]++;
					charPosition[1] = 0;
					charPosition[2] = 0;
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = 'X';
				}
				break;
			} else if (userInput.toLowerCase().equals("a")) {
				if (charPosition[2] != 0) {
					mapArray[charPosition[0]][charPosition[1]][charPosition[2] - 1] = mapArray[charPosition[0]][charPosition[1]][charPosition[2]];
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = '_';
					charPosition[2]--;
				} else {
					System.out.println("***Move not registered***");
				}
				break;
			} else if (userInput.toLowerCase().equals("d")) {
				if (charPosition[2] != mapArray[0][0].length - 1 && mapArray[charPosition[0]][charPosition[1]][charPosition[2] + 1] != '^') {
					mapArray[charPosition[0]][charPosition[1]][charPosition[2] + 1] = mapArray[charPosition[0]][charPosition[1]][charPosition[2]];
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = '_';
					charPosition[2]++;
				} else if (charPosition[2] == mapArray[0][0].length - 1) {
					System.out.println("***Move not registered***");
				} else {
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = '_';
					charPosition[0]++;
					charPosition[1] = 0;
					charPosition[2] = 0;
					mapArray[charPosition[0]][charPosition[1]][charPosition[2]] = 'X';
				}
				break;
			} else if (userInput.toLowerCase().equals("m")) {
				mainMenu(false);
				mapViewer(false);
			} else System.out.println("***Invalid input detected***");
		}
	}
	
	public void enemyMove() {
		
	}

}
