package day5;

import java.io.BufferedReader;
import java.io.IOException;

import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day5 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader br = FileHelper.readFile("inputDay5.txt")) {
			
			String base = br.readLine();
			System.out.println("Original lenght: " + base.length());
			base = reduce(base);
			System.out.println("After lenght: " + base.length());
		}
	}
	
	public static String reduce(String base) {
		StringBuilder poly = new StringBuilder(base);
		for (int i = 0; i < poly.length()-1; i++) {
		char a = poly.charAt(i);
			char b = poly.charAt(i+1);
			if (Math.abs(a-b) == 32) {
				poly.deleteCharAt(i);
				poly.deleteCharAt(i);
				i -=2;
				if(i < -1) {
					i = -1;
				}
			}
		}
		return poly.toString();
	}
}

//65 97