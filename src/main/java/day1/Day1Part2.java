package day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day1Part2 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = FileHelper.readFile("inputDay1Part2.txt");
		
		String row = null;
		List<Integer> fileInMemory = new ArrayList<>();
		
		
		//Populate list
		while( (row = br.readLine()) != null) {
			fileInMemory.add(Integer.parseInt(row));
		}
		
		br.close();
		
		System.out.println(findFrequency(fileInMemory));
	}
	
	private static int findFrequency(List<Integer> fileInMemory) {
		Set<Integer> knownFrequencies = new HashSet<Integer>();
		int i = 0;
		int frequency = 0; 
		
		while(true) {
			frequency += fileInMemory.get(i++);
			
			if(!knownFrequencies.add(frequency)) {
				return frequency;
			}
			//Wrap
			if(i >= fileInMemory.size()) {
				i = 0;
			}
		}
	}

}
