/*
 * Rye Stahle-Smith
 * Personal Projects: Maze Solver
 * 10/6/23
 * 
 * Objective: This program will request a maze to be entered by the user. The user will have the option to utilize a 
 * file or input the maze directly inside the console. If the user decides to use a file, then the program will 
 * request for the user to input a filename. The file must be a .txt and the program will verify this. Upon solving the maze,
 * the program will display the solution in the console and compile a solution file for the user.
 * 
 * The first and last row of each maze must contain a complete line of hashtags (#). The first and last characters of the 
 * inner walls of the maze must also begin with a hashtag (#). The maze paths must be marked with periods (.). The start 
 * space of the maze should be marked with a 'S' (there should only be one), while the finish should be marked with a 'F'
 * (there should only be one). The program will find the solution path and mark it using underscores (_). Each column 
 * should use the SAME row length (no jagged arrays)! If any row lengths differ, then the program MUST inform the user why 
 * the file can't be imported!
 * 
 */
package MazeSolver;

public class MazeSolver {

	public static void main(String[] args) {
		// Local variables
		MazeSolverMethods instance = new MazeSolverMethods();
		
		// Runs the run() method for the maze solver
		instance.run(); 
	}

}
