package day1;

import java.io.BufferedReader;
import java.io.IOException;

import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day1 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		int frequency = 0; 
		BufferedReader br = FileHelper.readFile("inputDay1.txt");
				
		String row = null;
		while( (row = br.readLine()) != null) {
			frequency += Integer.parseInt(row);
		}
		br.close();
		System.out.println(frequency);
	}

}
