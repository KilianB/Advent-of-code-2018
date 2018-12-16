package day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.kilianB.datastructures.CountHashCollection;

import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day2 {

	public static void main(String[] args) throws IOException {
		System.out.println(withMapEarlyTermination());
		System.out.println(withMap());
		System.out.println(withStream());
		System.out.println(withHashCount());
	}

	private static int withMap() throws IOException {

		BufferedReader br = FileHelper.readFile("inputDay2.txt");
		int two = 0;
		int three = 0;
		while (br.ready()) {

			String line = br.readLine();
			// While not efficient let us do some stream magic

			Map<Character, Integer> countMap = new HashMap<>();

			for (char c : line.toCharArray()) {
				countMap.merge(c, 1, (oldValue, value) -> oldValue + 1);
			}

			if (countMap.containsValue(2)) {
				two++;
			}
			if (countMap.containsValue(3)) {
				three++;
			}

		}
		return two * three;
	}

	private static int withMapEarlyTermination() throws IOException {
		BufferedReader br = FileHelper.readFile("inputDay2.txt");
		int two = 0;
		int three = 0;

		outer: while (br.ready()) {

			String line = br.readLine();
			// While not efficient let us do some stream magic

			Map<Character, Integer> countMap = new HashMap<>();
			Set<Character> twoCandidates = new HashSet<>();
			Set<Character> threeCandidates = new HashSet<>();

			for (char c : line.toCharArray()) {
				int val = countMap.merge(c, 1, (oldValue, value) -> oldValue + 1);

				if (val == 2) {
					twoCandidates.add(c);
				} else if (val == 3) {
					twoCandidates.remove(c);
					threeCandidates.add(c);
					if (!twoCandidates.isEmpty()) {
						two++;
						three++;
						continue outer;
					}
				}

			}
			if (!twoCandidates.isEmpty()) {
				two++;
			}
			if (!threeCandidates.isEmpty()) {
				three++;
			}
		}
		return two * three;
	}

	private static int withHashCount() throws IOException {
		BufferedReader br = FileHelper.readFile("inputDay2.txt");
		int two = 0;
		int three = 0;
		while (br.ready()) {
			String line = br.readLine();
			// While not efficient let us do some stream magic

			CountHashCollection<Character> countHash = new CountHashCollection<>();

			for (char c : line.toCharArray()) {
				countHash.add(c);
			}

			boolean twoFound = false;
			boolean threeFound = false;
			for (Character c : countHash.toArrayUnique(new Character[0])) {
				int occurances = countHash.containsCount(c);
				if (occurances == 2) {
					twoFound = true;
				} else if (occurances == 3) {
					threeFound = true;
				}
				if (twoFound && threeFound) {
					break;
				}
			}

			if (twoFound) {
				two++;
			}
			if (threeFound) {
				three++;
			}
		}
		br.close();
		return two * three;
	}

	private static int withStream() throws IOException {
		BufferedReader br = FileHelper.readFile("inputDay2.txt");

		int two = 0;
		int three = 0;
		while (br.ready()) {
			String line = br.readLine();
			// While not efficient let us do some stream magic
			Map<Integer, Long> counted = line.chars().boxed()
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			Map<Long, Long> css = counted.entrySet().stream()
					.filter(e -> (e.getValue().equals(2L) || e.getValue().equals(3L)))
					.collect(Collectors.groupingBy(e -> e.getValue(), Collectors.counting()));

			two += css.containsKey(2L) ? 1 : 0;
			three += css.containsKey(3L) ? 1 : 0;
		}

		br.close();
		return two * three;
	}
}
