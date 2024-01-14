/*
 * Rye Stahle-Smith
 * Personal Projects: Maze Solver Methods
 * 10/6/23
 * 
 * Objective: The purpose of this class is to provide methods for the Maze Solver Program. It allows users to input maze 
 * data directly in the console or load maze data from a text file. The class verifies that the input is a valid maze, 
 * finds a path from the start to the finish, displays the solution in the console, and creates a solution file.
 */
package MazeSolver;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Scanner; // Imports keyboard scanner
public class MazeSolverMethods {
	// Global variables
	private Scanner keyboard = new Scanner(System.in);
	private char[][] mazeArray;
	private int startX = 0, startY = 0;
	
	public void run() {
		// System displays welcome message
		System.out.println("Welcome to the Maze Solver Program!");
		
		while (true) {
			System.out.print("Press 'f' to import a maze using a file or press 'q' to quit: ");
			
			String userInput = keyboard.nextLine();
				
			if (userInput.equals("f")) {
				while (true) {
					requestFilename();
					startFinder();
					
					System.out.print("Would you like to solve another maze? y/n: ");
					
					userInput = keyboard.nextLine();
					
					if (userInput.toLowerCase().equals("n")) {
						System.out.println("***Quitting system...***");
						System.exit(0);
					}
				}
			} else if (userInput.equals("q")) {
				System.out.println("***Quitting system...***");
				System.exit(0);
			} else System.out.println("***Invalid input detected...***");			
		}
	}
	
	public void requestFilename() {
		while (true) {			
			boolean filenameIsValid = false;
			
			System.out.print("Please enter a filename with a .txt file extension: ");
			
			String userInput = keyboard.nextLine();
			
			for (int i = 0; i < userInput.length(); i++) {
				if (userInput.charAt(i) == '.') {
					if (userInput.substring(i, userInput.length()).equals(".txt")) filenameIsValid = true;
				}
			}
			
			if (filenameIsValid) {
				System.out.println("***Filename \"" + userInput + "\" is valid...***");
				importMaze(userInput);
				break;
			} else System.out.println("***Filename \"" + userInput + "\" is invalid...***");	
		}
	}
	
	public void importMazeDim(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/" + filename));
			
			int currChar;
			double i = 0, j = 1;
			
		    while (true) {
		    	currChar = reader.read();
		    	i++;
		    	
		    	if ((char)currChar == '\n') {
		    		i--;
		    		j++;
		    	}
		    	if (currChar == -1) {
		    		i--;
		    		if ((i / j) == ((int)i / (int)j)) {
		    			mazeArray = new char[(int)j][(int)(i / j)];
		    			System.out.println("***Successfully imported \"" + filename + "\" dimensions...***");
			    		break;
		    		} else {
		    			System.out.println("***Error: Could not import \"" + filename + "\" dimensions...***");
		    			System.out.println("Please ensure that the each column is using the same row length!");
		    			System.out.println("***Quitting system...***");
		    			System.exit(0);
		    		}
		    		
			    }
		    }
		    
			reader.close();
		} catch (IOException e){
			System.out.print("***[ERROR] ");
			if (e instanceof FileNotFoundException) {
		        System.out.println("File \"" + filename + "\" not found: " + e.getMessage() + "...***");
		    } else if (e instanceof AccessDeniedException) {
		        System.out.println("***Access denied for file \"" + filename + "\": " + e.getMessage() + "...***");
		    } else {
		        System.out.println("***An unknown IOException occurred: " + e.getMessage() + "...***");
		    }
			e.printStackTrace();
			System.out.println("***Quitting system...***");
			System.exit(0);
		}
	}
	
	public void importMaze(String filename) {
		importMazeDim(filename);
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/" + filename));
			
			System.out.println("***Importing \"" + filename + "\" below...***");
			
			int currChar;
			int i = 0, j = 0;
			boolean startFound = false, endFound = false;
			
			while ((currChar = reader.read()) != -1) {
				if ((char)currChar == '#' || (char)currChar == '.' || (char)currChar == 'S' || (char)currChar == 'F') {
		    		if (i == 0 && (char)currChar != '#') {
		    			if (j != 0) System.out.println();
	    				System.out.println("***Error: Couldn't complete import for \"" + filename + "\"...***");
			    		System.out.println("First row incorrectly setup. Please ensure you're using the specified instructions for your maze.");
						System.out.println("***Quitting system...***");
						System.exit(0);
		    		} else if (i == mazeArray.length - 1 && (char)currChar != '#') {
	    				System.out.println("\n***Error: Couldn't complete import for \"" + filename + "\"...***");
			    		System.out.println("Last row incorrectly setup. Please ensure you're using the specified instructions for your maze.");
						System.out.println("***Quitting system...***");
						System.exit(0);
		    		} else if ((j == 0 || j == mazeArray[0].length - 1) && (char)currChar != '#') {
		    			if (j != 0) System.out.println();
	    				System.out.println("***Error: Couldn't complete import for \"" + filename + "\"...***");
			    		System.out.println("Wall incorrectly setup. Please ensure you're using the specified instructions for your maze.");
						System.out.println("***Quitting system...***");
						System.exit(0);
		    		} else if ((char)currChar == 'S') {
		    			if (startFound == false) startFound = true;
		    			else {
		    				System.out.println("\n***Error: Couldn't complete import for \"" + filename + "\"...***");
				    		System.out.println("Start already initialized. Please ensure you're using the specified instructions for your maze.");
							System.out.println("***Quitting system...***");
							System.exit(0);
		    			}
		    		} else if ((char)currChar == 'F') {
		    			if (endFound == false) endFound = true;
		    			else {
		    				System.out.println("\n***Error: Couldn't complete import for \"" + filename + "\"...***");
				    		System.out.println("Finish already initialized. Please ensure you're using the specified instructions for your maze.");
							System.out.println("***Quitting system...***");
							System.exit(0);
		    			}
		    		}
		    		mazeArray[i][j] = (char)currChar;
		    		System.out.print(mazeArray[i][j]);
		    		j++;
				} else {
					if ((char)currChar == '\n') {
						System.out.println();
			    		i++;
			    		j = 0;
			    	} else {
			    		if (i != 0) System.out.println();
			    		System.out.println("***Error: Couldn't complete import for \"" + filename + "\"...***");
			    		System.out.println("Character unrecognized. Please ensure you're using the specified characters for your maze.");
						System.out.println("***Quitting system...***");
						System.exit(0);
			    	}
				}
		    }
			System.out.println("\n***Maze successfully imported...***");

			reader.close();
		} catch (IOException e) {
			System.out.println("***Error: Could not import \"" + filename + "\"...***");
			e.printStackTrace();
			System.out.println("***Quitting system...***");
			System.exit(0);
		}
	}
	
	public boolean pathFinder(int x, int y) {
	    if (x < 0 || x >= mazeArray.length || y < 0 || y >= mazeArray[0].length) {
	        return false; // Out of bounds
	    }

	    char cell = mazeArray[x][y];
	    if (cell == 'F') {
	        return true; // Reached the finish
	    }

	    if (cell == '#' || cell == '/') {
	        return false; // Wall or already visited cell
	    }

	    mazeArray[x][y] = '/'; // Mark the cell as part of the path

        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (pathFinder(newX, newY)) {
                return true;
            }
        }

        // Unmark the cell during backtracking
        mazeArray[x][y] = '.';

	    return false;
	}
	
	public void startFinder() {
		char currChar = 0;
		int i, j;
		
		for (i = 0; i < mazeArray.length; i++) {
			for (j = 0; j < mazeArray[0].length; j++) {
				currChar = mazeArray[i][j];
				if (currChar == 'S') {
					startX = i;
					startY = j;
					break;
				}
			}
			if (currChar == 'S') break;
		}
		
		if (pathFinder(startX, startY)) {
			mazeArray[startX][startY] = 'S';
	        System.out.println("A valid path from start to finish was found.");
	        solMazePrinter();
	    } else {
	        System.out.println("Unfortunately, no path from start to finish exists.");
	    }
	}
	
	public void solMazePrinter() {
    	System.out.println("***Printing solved maze below...***");
    	
		for (int i = 0; i < mazeArray.length; i++) {
			for (int j = 0; j < mazeArray[0].length; j++) {
				System.out.print(mazeArray[i][j]);
			}
			System.out.println();
		}
		
	}
}
