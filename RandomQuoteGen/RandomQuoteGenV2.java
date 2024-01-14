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
import com.google.gson.Gson;
import java.util.List;

public class RandomQuoteGenV2 {

	public static void main(String[] args) {
		// Local variables
		Scanner keyboard = new Scanner(System.in);
		String jsonResponse = null;
		String userInput = null;
		Gson gson = new Gson();
		
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
			
			Data data = gson.fromJson(jsonResponse, Data.class);
			
			System.out.println("---------------------------------------------------------------------------------------");	        
			System.out.println(data.toString());
			System.out.println("---------------------------------------------------------------------------------------");	        
	        
	        System.out.print("Press 'q' to quit or press ENTER to receive your next quote: ");
			
			userInput = keyboard.nextLine();
			
			if (userInput.trim().equals("q")) {
				System.out.println("***Quitting program...***");
				System.exit(0);
			} else if (!userInput.trim().isBlank()) System.out.println("***Invalid input received...***");
		}
	}

}

class Data {
	private String author;
    private String content;
    private List<String> tags;
    
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public List<String> getTags() { return tags; }

    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    public String toString() {
        return String.format("%s: %s\nTags: %s", author, content, tags.toString().replace("[", "").replace("]", ""));
    }
}
