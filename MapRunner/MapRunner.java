/*
 * Rye Stahle-Smith
 * Personal Projects: Map Runner
 * 10/6/23
 * 
 * Objective: This program will utilize a 3D array. The user will be able to select a difficulty of easy, normal or hard.
 * The user will begin on the first 2D layer of the 3D array. Along the way, the user will be able to obtain achievements.
 * The user can check out their achievements at the main menu; However, it should be noted that these achievements are
 * strictly same session-based (meaning once the game is terminated, any obtained achievements will lock again).
 * 
 * Easy Mode: Utilizes a 5x5x3 array. The first layer contains no enemies, the second layer contains one static enemy,
 * and the third layer contains one static enemy and one mobile enemy.
 * Normal Mode: Utilizes a 10x10x5 array. The first layer contains no enemies, the second layer contains one static enemy, 
 * the third layer contains two static enemies, the fourth layer contains two static enemies and one mobile enemy, 
 * the fifth layer contains two mobile enemies.
 * Hard Mode: Utilizes a 15x15x7 array. The first layer contains two static enemies, the second layer contains two static
 * enemies and one mobile enemy, the third layer contains four static enemies and two mobile enemies, the fourth layer
 * contains five static enemies and three mobile enemies, the fifth layer contains seven static enemies and four mobile 
 * enemies, the sixth layer contains eight static enemies and five mobile enemies, the seventh layer contains nine 
 * static enemies and seven mobile enemies.
 */
package MapRunner;

public class MapRunner {
	
	public static void main(String[] args) {
		// Creates a new instance of MapRunnerMethods class
		MapRunnerMethods instance = new MapRunnerMethods();
		
		// System displays welcome message
		System.out.println("Welcome to Map Runner: The Lost Catacombs!");
		
		instance.run();
	}

}
