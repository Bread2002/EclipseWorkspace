/*
 * Rye Stahle-Smith
 * Personal Projects: Text Analyzer
 * 11/3/23
 * 
 * Objective: This class provides various methods and variables utilized in the TextAnalyzer program.
 */

package TextAnalyzer;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;
public class TextAnalyzerMethods {
	// Instance variables
	private Scanner keyboard = new Scanner(System.in);
	private int wordCount = 0;
	private int charCount = 0;
	private int lineCount = 0;
	private double avgWordLength = 0;
	private static final int MAX_FREQUENT_WORDS = 100;
	private String[][] freqWords = new String[MAX_FREQUENT_WORDS][2];
	
	
	public void run() {
		// Local variables
		String file;
		String userInput;
		
		// System displays welcome message
		System.out.println("Welcome to the Text Analyzer Program!");
		
		while (true) {
			file = requestFilename();
			importFile(file);
			getWordCount(file);
			getCharAndLineCount(file);
			getFreqWords(file);
			sortFreqWords();
			
			System.out.println("\nText Analysis for \"" + file + "\":");
			System.out.println("Total Word Count: " + wordCount);
			System.out.println("Total Character Count: " + charCount);
			System.out.println("Total Line Count: " + lineCount);
			System.out.println("Average Word Length: " + String.format("%.2f", avgWordLength));
			System.out.println("Most Common Word: \"" + freqWords[0][0] + "\"");
			System.out.println("Word Frequency:");
			for (int i = 0; i < 5; i++) {
				System.out.println("- \"" + freqWords[i][0] + "\": " + freqWords[i][1]);
			}
			System.out.println("- ...\n");
			
			while (true) {
				System.out.print("Would you like to import another file? y/n: ");
				
				userInput = keyboard.nextLine();
				
				if (userInput.equals("y")) {
					wordCount = 0;
					charCount = 0;
					lineCount = 0;
					avgWordLength = 0;
					freqWords = new String[MAX_FREQUENT_WORDS][2];
					break;
				} else if (userInput.equals("n")) {
					System.out.print("***Quitting program...***");
					System.exit(0);
				} else System.out.println("***Invalid input detected...***");
			}
		}
	}
	
	public String requestFilename() {
		// Local variables
		String userInput;
					
		while (true) {
			System.out.print("Please specify the filename that you'd like to import, including the file extension (.txt): ");
			
			userInput = keyboard.nextLine();
			
			if (userInput.length() > 3 && userInput.substring(userInput.length() - 4, userInput.length()).equals(".txt")) return userInput;
			else System.out.println("***Invalid input detected...*** \nFile extension must be '.txt'!");
		}
	}
	
	public void importFile(String file) {
		// Local variables
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("src/TextAnalyzer/" + file));
			System.out.println("***File \"" + file + "\" successfully imported...***");
		} catch (IOException e) {
			System.out.print("***[ERROR] ");
			if (e instanceof FileNotFoundException) System.out.println("File \"" + file + "\" not found: " + e.getMessage() + "...***");
		    else if (e instanceof AccessDeniedException) System.out.println("Access denied for file \"" + file + "\": " + e.getMessage() + "...***");
		    else System.out.println("An unknown IOException occurred: " + e.getMessage() + "...***");
			e.printStackTrace();
			System.out.println("***Quitting system...***");
			System.exit(0);
		}
	}
	
	public void getWordCount(String file) {
		// Local variables
		BufferedReader reader;
		int currChar;
		boolean insideWord = false;
		
		try {
			reader = new BufferedReader(new FileReader("src/TextAnalyzer/" + file));
			while ((currChar = reader.read()) != -1) {
				if (!insideWord && (((char)currChar >= 'a' && (char)currChar <= 'z') 
						|| ((char)currChar >= 'A' && (char)currChar <= 'Z') || ((char)currChar >= '0' && (char)currChar <= '9') || (char)currChar == '\'' || (char)currChar == '-')) {
					insideWord = true;
				} else if (insideWord && (((char)currChar < 'a' || (char)currChar > 'z') && ((char)currChar < 'A' 
						|| (char)currChar > 'Z') && ((char)currChar < '0' || (char)currChar > '9') && (char)currChar != '\'' && (char)currChar != '-')) {
					insideWord = false;
					wordCount++;
				}
			}
			
			if (insideWord) wordCount++;
		} catch (IOException e) {
		    System.out.println("***[ERROR] An unknown IOException occurred: " + e.getMessage() + "...***");
			e.printStackTrace();
			System.out.println("***Quitting system...***");
			System.exit(0);
		}
	}
	
	public void getCharAndLineCount(String file) {
		// Local variables
		BufferedReader reader;
		int currChar;
		
		try {
			reader = new BufferedReader(new FileReader("src/TextAnalyzer/" + file));
			while ((currChar = reader.read()) != -1) {
				if (lineCount == 0) lineCount = 1;
				if ((((char)currChar >= 'a' && (char)currChar <= 'z') || ((char)currChar >= 'A' && (char)currChar <= 'Z') 
						|| ((char)currChar >= '0' && (char)currChar <= '9') || (char)currChar == '\'' || (char)currChar == '-')) avgWordLength++;
				if ((char)currChar != '\n' && (char)currChar != '\r') charCount++;
				else lineCount++;
			}
			
			avgWordLength /= wordCount;
		} catch (IOException e) {
		    System.out.println("***[ERROR] An unknown IOException occurred: " + e.getMessage() + "...***");
			e.printStackTrace();
			System.out.println("***Quitting system...***");
			System.exit(0);
		}
	}
	
	public void getFreqWords(String file) {
		// Local variables
		BufferedReader reader;
		String line;
		String currWord = "";
		
		try {
			reader = new BufferedReader(new FileReader("src/TextAnalyzer/" + file));
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < freqWords.length && i < line.length(); i++) {
					if (((line.charAt(i) >= 'a' && line.charAt(i) <= 'z') || (line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') 
							|| (line.charAt(i) >= '0' && line.charAt(i) <= '9'))) {
						currWord += line.charAt(i);
					} else if (!currWord.equals("") && ((line.charAt(i) < 'a' || line.charAt(i) > 'z') && (line.charAt(i) < 'A' 
							|| line.charAt(i) > 'Z') && (line.charAt(i) < '0' || line.charAt(i) > '9'))) {
						for (int j = 0; j < freqWords.length; j++) {
							if (freqWords[j][0] != null && freqWords[j][0].equals(currWord.toLowerCase())) {
								int newFrequency = Integer.parseInt(freqWords[j][1]) + 1;
								freqWords[j][1] = Integer.toString(newFrequency);
								break;
							} else if (freqWords[j][0] == null) {
								freqWords[j][0] = currWord.toLowerCase();
								freqWords[j][1] = "1";
								break;
							}
						}
						
						currWord = "";
					}
				}
			}
		} catch (IOException e) {
		    System.out.println("***[ERROR] An unknown IOException occurred: " + e.getMessage() + "...***");
			e.printStackTrace();
			System.out.println("***Quitting system...***");
			System.exit(0);
		}
	}
	
	public void sortFreqWords() {
		if (freqWords[0][0] != null) {
			for (int i = 0; i < freqWords.length; i++) {
	            for (int j = 0; j < freqWords.length - 1 - i; j++) {
	                if (freqWords[j][1] != null && freqWords[j + 1][1] != null && Integer.parseInt(freqWords[j][1]) < Integer.parseInt(freqWords[j + 1][1])) {
	                    // Swap the entire sub-arrays
	                    String[] temp = freqWords[j];
	                    freqWords[j] = freqWords[j + 1];
	                    freqWords[j + 1] = temp;
	                }
	            }
	        }
		}
	}
}
