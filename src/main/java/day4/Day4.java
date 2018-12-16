package day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.github.kilianB.ArrayUtil;

import day4.Day4.LogEntry.EntryType;
import utility.FileHelper;

/**
 * @author Kilian
 *
 */
public class Day4 {

	public static void main(String[] args) throws IOException {

		BufferedReader br = FileHelper.readFile("inputDay4.txt");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		TreeSet<LogEntry> sortedEvent = new TreeSet<>();

		while (br.ready()) {

			// Parse log entry
			String[] tokens = br.readLine().split(" ");
			LocalDateTime dt = LocalDateTime
					.parse(tokens[0].substring(1) + " " + tokens[1].substring(0, tokens[1].length() - 1), formatter);

			if (tokens[2].equals("Guard")) {
				int guardId = Integer.parseInt(tokens[3].substring(1));
				sortedEvent.add(LogEntry.startEvent(dt, guardId));
			} else if (tokens[2].equals("wakes")) {
				sortedEvent.add(LogEntry.wakeEvent(dt));
			} else if (tokens[2].equals("falls")) {
				sortedEvent.add(LogEntry.sleepEvent(dt));
			}
		}

		Guard curGuard = null;
		Map<Integer, Guard> guardsMap = new HashMap<Integer, Guard>();

		for (LogEntry l : sortedEvent) {

			System.out.println(l);
			if (l.type.equals(EntryType.StartShift)) {
				if (!guardsMap.containsKey(l.guardId)) {
					guardsMap.put(l.guardId, (new Guard(l.guardId)));
				}
				curGuard = guardsMap.get(l.guardId);
			}
			curGuard.addLogEntry(l);
		}
		
		List<Guard> rawGuards = new ArrayList<>(guardsMap.values());

		Collections.sort(rawGuards,(g0,g1) -> {return -Integer.compare(g0.spendTimeAsleep(),g1.spendTimeAsleep());});

		System.out.println(rawGuards.get(0).id * rawGuards.get(0).mostMinuteAsleep());
		
		for (Guard g : rawGuards) {
			System.out.println(g.id + " " + g.spendTimeAsleep() + " " + g.mostMinuteAsleep());
			//g.printLogs();
		}

	}

	static class LogEntry implements Comparable<LogEntry> {

		public enum EntryType {
			StartShift, FallsAsleep, WakesUp;
		}

		public static LogEntry wakeEvent(LocalDateTime time) {
			return new LogEntry(time, EntryType.WakesUp, 0);
		}

		public static LogEntry sleepEvent(LocalDateTime time) {
			return new LogEntry(time, EntryType.FallsAsleep, 0);
		}

		public static LogEntry startEvent(LocalDateTime time, int guardId) {
			return new LogEntry(time, EntryType.StartShift, guardId);
		}

		private LogEntry(LocalDateTime time, EntryType type, int guardId) {
			this.time = time;
			this.type = type;
			this.guardId = guardId;
		}

		LocalDateTime time;
		EntryType type;
		int guardId;

		@Override
		public int compareTo(LogEntry o) {
			return time.compareTo(o.time);
		}

		@Override
		public String toString() {
			return "LogEntry [time=" + time + ", type=" + type + ", guardId=" + guardId + "]";
		}

	}

	static class Guard {
		int id;
		List<LogEntry> log = new ArrayList();

		public Guard(int id) {
			this.id = id;
		}

		/**
		 * 
		 */
		public void printLogs() {
			for (var entry : log) {
				System.out.println(entry);
			}
		}

		public void addLogEntry(LogEntry entry) {
			log.add(entry);
		}

		// TODO we could put this into one file and cache ..
		public int spendTimeAsleep() {

			int asleep = 0;

			LocalDateTime startSleep = null;
			for (LogEntry entry : log) {
				if (entry.type.equals(EntryType.FallsAsleep)) {
					startSleep = entry.time;
				} else if (entry.type.equals(EntryType.WakesUp)) {
					//entry.time.until(startSleep,ChronoUnit.MINUTES);
					//We only care about minutes
					asleep += entry.time.getMinute() - startSleep.getMinute();
					
					startSleep = null;
				}
			}
			return asleep;
		}

		public int mostMinuteAsleep() {

			int[] sleepMinuteCount = new int[60];
			
			LocalDateTime startSleep = null;
			for (LogEntry entry : log) {
				if (entry.type.equals(EntryType.FallsAsleep)) {
					startSleep = entry.time;
				} else if (entry.type.equals(EntryType.WakesUp)) {
					// We are only working with a single hour. no reason to differentiate
					int startMinute = startSleep.getMinute();
					int endMinute = entry.time.getMinute();
					for(int i = startMinute; i < endMinute; i++) {
						sleepMinuteCount[i]++;
					}
				}
			}
			return ArrayUtil.maximumIndex(sleepMinuteCount);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Guard other = (Guard) obj;
			if (id != other.id)
				return false;
			return true;
		}

	}

}
