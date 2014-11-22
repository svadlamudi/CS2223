// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Traversals {

/* Properties */

	/**
	 * Regex Pattern to find all alphabetic characters in string seperated by a space
	 */
	private static final Pattern charFind = Pattern.compile("[A-Za-z]+");

/* Main Method */

	/**
	 * 
	 * Main loop, reads given lines, one or two, and performs traversals.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		// Testing
		Pair<Boolean, String[]> lineOnePrePost = findAllLetters("H B D E I J");
		
		Pair<Boolean, String[]> lineOnePreInPost = findAllLetters("A B D E C");
		Pair<Boolean, String[]> lineTwoPreInPost = findAllLetters("D E B A C");
		
		Pair<Boolean, String[]> lineOnePrePostIn = findAllLetters("A B C");
		Pair<Boolean, String[]> lineTwoPrePostIn = findAllLetters("B C A");

		Pair<Boolean, String[]> lineOnePrePostInTestTwo = findAllLetters("A B D E C F G");
		Pair<Boolean, String[]> lineTwoPrePostInTestTwo = findAllLetters("D E B F G C A");
		
		search_pre_to_post(lineOnePrePost.valueTwo);
		pre_in_to_post(lineOnePreInPost.valueTwo, lineTwoPreInPost.valueTwo);
		pre_post_to_in(lineOnePrePostIn.valueTwo, lineTwoPrePostIn.valueTwo);		
		pre_post_to_in(lineOnePrePostInTestTwo.valueTwo, lineTwoPrePostInTestTwo.valueTwo);		
		
		/*try {
			//Pair<Boolean, String[]> lineOne = findAllLetters(in.readLine());
			//Pair<Boolean, String[]> lineTwo = findAllLetters(in.readLine());
			//printArray(lineOne.valueTwo);
			//printArray(lineTwo.valueTwo);
		} catch (Exception e) {
			e.printStackTrace();
		}	*/	
	}

/* Traversal Methods */
	
	public static String[] search_pre_to_post(String[] preOrder) {
		return null;
	}
	
	public static String[] pre_in_to_post(String[] preOrder, String[] inOrder) {
		return null;
	}
	
	public static String[] pre_post_to_in(String[] preOrder, String[] postOrder) {
		return null;
	}
	
/* Helper Methods */
	
	/**
	 * 
	 * Assume: Each character is separated by a space in the input string.
	 * 	Ex: "H B D E I J" and not "HBDEIJ"
	 * 
	 * Find all alphabetic characters in the given string as an array of characters
	 * converted to string.
	 *
	 * @param inputString
	 * @return
	 */
	public static Pair<Boolean, String[]> findAllLetters(String inputString) {
		
		String[] letterArray = new String[100];
		Boolean readLettersInLine = false;		
		Matcher charMatcher = charFind.matcher(inputString);
		
		int loopCounter = 0;		
		while(charMatcher.find() && loopCounter < 101) {
			if(charMatcher.group().length() != 0) {
				letterArray[loopCounter] = charMatcher.group();
				loopCounter++;
				readLettersInLine = true;
			}
		}
		
		return new Pair<Boolean, String[]>(readLettersInLine, letterArray);
	}
	
	/**
	 * 
	 * Loop through till end of array or null element is reached
	 * and print to the output the strings in the array.
	 *
	 * @param stringArray
	 */
	public static void printArray(String[] stringArray) {
		
		for(int i = 0; i < stringArray.length; i++) {
			if (stringArray[i] != null) {
				System.out.println(stringArray[i]);
			} else {
				break;
			}
		}		
	}
	
}
