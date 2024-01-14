/*
 * Rye Stahle-Smith
 * Personal Projects: Random Quote Generator
 * 10/6/23
 * 
 * Objective: This program fetches and displays random quotes from a public API.
 */
package RandomQuoteGen;

import java.util.Scanner;
import java.net.*;
import java.io.*;
public class RandomQuoteGen {

	public static void main(String[] args) {
		// Local variables
		Scanner keyboard = new Scanner(System.in);
		String jsonResponse = null;
		String userInput = null;
		
		// System displays welcome message
		System.out.println("Welcome to the Random Quote Generator Program!");
		while (true) {
			System.out.print("Press ENTER to receive your first quote: ");
			
			userInput = keyboard.nextLine();

			if (userInput.isBlank()) break;
			else System.out.println("***Invalid input received...***");
		}
		
		while (true) {
			try {
				URL url = new URL("https://api.quotable.io/random");
				try {
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					int responseCode = connection.getResponseCode();
					if (responseCode == 200) { // 200 indicates a successful request
					    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					    String line;

					    while ((line = reader.readLine()) != null) jsonResponse = line;

					    reader.close();
					} else {
						System.out.println("Failed to retrieve data. \nResponse code: " + responseCode);
					}
					connection.disconnect();
				} catch (IOException e) {
					System.out.println("***IOException encountered...***");
					e.printStackTrace();
					System.out.println("***Quitting program...***");
					System.exit(0);
				}
			} catch (MalformedURLException e) {
				System.out.println("***MalformedURLException encountered...***");
				e.printStackTrace();
				System.out.println("***Quitting program...***");
				System.exit(0);
			}
			
			String contentResponse = null, authorResponse = null;
			for (int i = 0; i < jsonResponse.length(); i++) {
				if (jsonResponse.charAt(i) == '"' && jsonResponse.charAt(i + 1) == 'c' && jsonResponse.charAt(i + 2) == 'o') {
					for (int j = 0; true; j++) {
						if (jsonResponse.charAt((i + 11) + j) == '\"') {
							contentResponse = jsonResponse.substring(i + 11, (i + 11) + j);
							break;
						}
					}
				} else if (jsonResponse.charAt(i) == '"' && jsonResponse.charAt(i + 1) == 'a' && jsonResponse.charAt(i + 2) == 'u') {
					for (int j = 0; true; j++) {
						if (jsonResponse.charAt((i + 10) + j) == '\"') {
							authorResponse = jsonResponse.substring(i + 10, (i + 10) + j);
							break;
						}
					}
				}
				if (contentResponse != null && authorResponse != null) break;
			}
			
			System.out.println(authorResponse + ": " + contentResponse);

			System.out.print("Press 'q' to quit or press ENTER to receive your next quote: ");
			
			userInput = keyboard.nextLine();
			
			if (userInput.trim().equals("q")) {
				System.out.println("***Quitting program...***");
				System.exit(0);
			} else if (!userInput.trim().isBlank()) System.out.println("***Invalid input received...***");
		}
		
		
	}

}
