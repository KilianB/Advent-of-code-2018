package utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import day1.Day1;

/**
 * @author Kilian
 *
 */
public class FileHelper {
	
	public static BufferedReader readFile(String fileName) {
		return new BufferedReader(new InputStreamReader(FileHelper.class.getClassLoader().getResourceAsStream(fileName)));
	}
	
}
