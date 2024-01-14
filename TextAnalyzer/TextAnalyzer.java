/*
 * Rye Stahle-Smith
 * Personal Projects: Text Analyzer
 * 11/3/23
 * 
 * Objective: This program analyzes a ".txt" file and provides various statistics about the text. 
 * The program should read a text file, analyze its contents, and provide the following information:
 * 1. Total word count
 * 2. Total character count (including spaces and punctuation)
 * 3. Total line count
 * 4. The frequency of each word in the text (case-insensitive)
 * 5. The average word length
 * 6. The most common word and its frequency
 */

package TextAnalyzer;

public class TextAnalyzer {

	public static void main(String[] args) {
		// Creates a new instance of TextAnalyzerMethods
		TextAnalyzerMethods instance = new TextAnalyzerMethods();
		
		// Runs the program
		instance.run();

	}

}
