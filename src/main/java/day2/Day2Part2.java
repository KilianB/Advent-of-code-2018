package day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day2Part2 {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = FileHelper.readFile("inputDay2.txt");
		List<String> ids = br.lines().collect(Collectors.toList());
		br.close();
		//Compute the levenstein distances of all strings
	
		//lets do it manually for the sake. for the sake of performance we could create 
		// a search tree to aggressively prune the results.
		
		//We assume all ids being the same length and only 2 matches.
		int currentlyBest = Integer.MAX_VALUE;
		String best0 = null;
		String best1 = null;
			
		Iterator<String> iter = ids.iterator();
		while(iter.hasNext()) {
			
			String base = iter.next();
			iter.remove();
			List<String> candidates = new ArrayList(ids);
			
			outer:
			for(String candidate : candidates) {
				int diffCount = 0;
				//Lets do a char by char compare
				for(int i = 0; i < candidate.length(); i++) {
					
					if(base.charAt(i) != candidate.charAt(i)) {
						diffCount++;
						if(diffCount > currentlyBest) {
							continue outer;
						}
					}
				}
				best0 = base;
				best1 = candidate;
				currentlyBest = diffCount;
			}
		}
		
		System.out.println(best0 + "\n" + best1 + "\n"+ currentlyBest);
		
		//Remove duplichars
		
		StringBuilder correctId = new StringBuilder(best0.length());
		for(int i = 0; i < best0.length(); i++) {
			char tChar = best0.charAt(i);
			if(tChar == best1.charAt(i)) {
				correctId.append(tChar);
			}
		}
		System.out.println("CorrectId: "  + correctId);
	}
	
	
}
