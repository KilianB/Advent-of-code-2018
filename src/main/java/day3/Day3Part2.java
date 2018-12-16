package day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;
import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day3Part2 {
	public static void main(String[] args) throws IOException {

		//Not efficient but will do
		Map<Point2D,Integer> pointMap = Day3.createMap();
		
		try (BufferedReader br = FileHelper.readFile("inputDay3.txt")) {

			for (String line : br.lines().toArray(String[]::new)) {
				//System.out.println(line);
				// parse
				String[] exploded = line.split(" ");

				int xEnd = exploded[2].indexOf(",");
				int x = Integer.parseInt(exploded[2].substring(0, xEnd));
				int y = Integer.parseInt(exploded[2].substring(xEnd+1,exploded[2].length()-1));

				int wEnd = exploded[3].indexOf("x");
				int w = Integer.parseInt(exploded[3].substring(0, wEnd));
				int h = Integer.parseInt(exploded[3].substring(wEnd+1,exploded[3].length()));

				//Rectangle rect = new Rectangle(x, y, w, h);
				//s = Shape.union(s,rect);
				
				//lets do it discrete
				if(checkClaim(pointMap,x,y,w,h)) {
					System.out.println("Non overlapping claim: " + exploded[0]);
					return;
				}
				
				
			}
			System.out.println("No valid claim found");
		}
	}
	
	private static boolean checkClaim(Map<Point2D,Integer> pointMap, int x, int y, int w, int h) {
		for(int i = 0,xCoord = x; i < w; i++) {
			for(int j = 0,yCoord = y; j < h; j++) {
				Point2D key = new Point2D(xCoord+i,yCoord+j);
				if(pointMap.get(key) > 1) {
					return false;
				}
			}
		}
		return true;
	}
	
}
