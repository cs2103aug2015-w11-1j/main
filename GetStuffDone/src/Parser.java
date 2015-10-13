
/*
	<------------------------------------------------------->
	Command keywords demarked by (case insensitive)
	Adding of task				add			-a
	Deleting of task			delete		-d
	Displaying of task			display
	Marking task as complete	complete
	Displaying help manual		help
	Redo last action			redo
	Search task					search		-s
	Undo last action			undo
	Editing an existing task	update		-u
	
	<------------------------------------------------------->
	Venue keywords demarked by (case sensitive)
	Specifying venue			AT
	
	<------------------------------------------------------->
	Priority keywords demarked by (case sensitive)
	Specifying priority
	
	<------------------------------------------------------->
	Time keywords demarked by (case sensitive):
	Start date and time - FROM
	End date and time - BY/TO
	Priority - PRIORITY
	Venue - AT
	
	Date and time format - <time><date>
	
	Supported time format 
	
				- HHmm		2359
				- HH.mm		23.59
				- HH:mm		23:59
				- hhmma		1159pm
				- hha		11pm
				- hh.mma	11.59pm
				- hh:mma	11:59pm
				
	Supported date format 
	
				- dd MMMM yy	01 April 15		01 Apr 15
				- dd MM yy		01 04 15
				- dd MMMM yyyy	01 April 2015	01 Apr 2015
				- dd MM yyyy	01 04 2015
				- dd MMMM		01 April		01 Apr
				- dd MM			01 04
				
				- dd-MMMM-yy	01-April-15		01-Apr-15
				- dd-MM-yy		01-04-15
				- dd-MMMM-yyyy	01-April-2015	01-Apr-2015
				- dd-MM-yyyy	01-04-2015
				- dd-MMMM		01-April		01-Apr
				- dd-MM			01-04
				
				- dd/MMMM/yy	01/April/15		01/Apr/15
				- dd/MM/yy		01/04/15
				- dd/MMMM/yyyy	01/April/2015	01/Apr/2015
				- dd/MM/yyyy	01/04/2015
				- dd/MMMM		01/April		01/Apr
				- dd/MM			01/04
	
	Supported NLI date	
	
				- today			today			<time> today
				- tomorrow		tomorrow		<time> tomorrow
				
				
		<Command> <TaskID (if applicable)> <Time, Venue, Priority, description of task>
	eg. -U 2 do homework AT School of Computing FROM 12.30pm 6/10/15 TO 6pm 6/10/15 PRIORITY high
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Parser {
	private final static String[] DATE_FORMAT = { "HHmm dd MMMM yy", "HH.mm dd MMMM yy", "HH:mm dd MMMM yy",
			"hhmma dd MMMM yy", "hha dd MMMM yy", "hmma dd MMMM yy", "hh.mma dd MMMM yy", "hh:mma dd MMMM yy",
			"dd MMMM yy",

			"HHmm dd MM yy", "HH.mm dd MM yy", "HH:mm dd MM yy", "hhmma dd MM yy", "hha dd MM yy", "hmma dd MM yy",
			"hh.mma dd MM yy", "hh:mma dd MM yy", "dd MM yy",

			"HHmm dd MMMM", "HH.mm dd MMMM", "HH:mm dd MMMM", "hhmma dd MMMM", "hha dd MMMM", "hmma dd MMMM",
			"hh.mma dd MMMM", "hh:mma dd MMMM", "dd MMMM",

			"HHmm dd MM", "HH.mm dd MM", "HH:mm dd MM", "hhmma dd MM", "hha dd MM", "hmma dd MM", "hh.mma dd MM",
			"hh:mma dd MM", "dd MM",

			"HHmm dd/MMMM/yy", "HH.mm dd/MMMMM/yy", "HH:mm dd/MM/yy", "hhmma dd/MMMM/yy", "hha dd/MMMM/yy",
			"hmma dd/MMMM/yy", "hh.mma dd/MMMM/yy", "hh:mma dd/MMMM/yy", "dd/MMMM/yy",

			"HHmm dd/MM/yy", "HH.mm dd/MM/yy", "HH:mm dd/MM/yy", "hhmma dd/MM/yy", "hha dd/MM/yy", "hmma dd/MM/yy",
			"hh.mma dd/MM/yy", "hh:mma dd/MM/yy", "dd/MM/yy",

			"HHmm dd/MMMM", "HH.mm dd/MMMM", "HH:mm dd/MMMM", "hhmma dd/MMMM", "hha dd/MMMM", "hmma dd/MMMM",
			"hh.mma dd/MMMM", "hh:mma dd/MMMM", "dd/MMMM",

			"HHmm dd/MM", "HH.mm dd/MM", "HH:mm dd/MM", "hhmma dd/MM", "hha dd/MM", "hmma dd/MM", "hh.mma dd/MM",
			"hh:mma dd/MM", "dd/MM",

			"HHmm dd-MMMM-yy", "HH.mm dd-MMMM-yy", "HH:mm dd-MMMM-yy", "hhmma dd-MMMM-yy", "hha dd-MMMM-yy",
			"hmma dd-MMMM-yy", "hh.mma dd-MMMM-yy", "hh:mma dd-MMMM-yy", "dd-MMMM-yy",

			"HHmm dd-MM-yy", "HH.mm dd-MM-yy", "HH:mm dd-MM-yy", "hhmma dd-MM-yy", "hha dd-MM-yy", "hmma dd-MM-yy",
			"hh.mma dd-MM-yy", "hh:mma dd-MM-yy", "dd-MM-yy",

			"HHmm dd-MMMM", "HH.mm dd-MMMM", "HH:mm dd-MMMM", "hhmma dd-MMMM", "hha dd-MMMM", "hmma dd-MMMM",
			"hh.mma dd-MMMM", "hh:mma dd-MMMM", "dd-MMMM",

			"HHmm dd-MM", "HH.mm dd-MM", "HH:mm dd-MM", "hhmma dd-MM", "hha dd-MM", "hmma dd-MM", "hh.mma dd-MM",
			"hh:mma dd-MM", "dd-MM", "HHmm", "HH.mm", "HH:mm", "hhmma", "hha", "hmma", "hh.mma", "hh:mma",

	};

	private final static String[] keyWord = { "AT", "BY", "FROM", "TO", "PRIORITY" };

	private final static int NO_NEXT_KEYWORD = -2;
	private final static int NO_ID = -10;
	private final static int NO_KEYWORD = -1;
	private final static int NO_YEAR_INPUT = 1970;

	private static Date parseStartDate(ArrayList<String> input) {
		String result = "";
		int indexOfKeyWord = input.lastIndexOf("FROM");
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;

		if (notContainsKeyword(indexOfKeyWord)) {
			return new Date();
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWord, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		result = getInputBetweenArrayList(input, indexOfKeyWord, indexOfNextKeyWord);
		return createDate(result);
	}

	private static int checkLastIndex(ArrayList<String> input, int indexOfNextKeyWord) {
		if (indexOfNextKeyWord == NO_NEXT_KEYWORD) {
			indexOfNextKeyWord = input.size();
		}
		return indexOfNextKeyWord;
	}

	private static Date parseEndDate(ArrayList<String> input) {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int indexOfKeyWordTO = input.lastIndexOf("TO");
		int indexOfKeyWordBY = input.lastIndexOf("BY");
		if (notContainsKeyword(indexOfKeyWordTO) && notContainsKeyword(indexOfKeyWordBY)) {
			return null;
		} else if (notContainsKeyword(indexOfKeyWordTO) && !notContainsKeyword(indexOfKeyWordBY)) {
			indexOfNextKeyWord = NO_NEXT_KEYWORD;
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordBY, indexOfNextKeyWord);
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		// end date is FROM
		if (notContainsKeyword(indexOfKeyWordBY)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		// end date is BY
		if (notContainsKeyword(indexOfKeyWordTO)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordBY, indexOfNextKeyWord);
		}
		return createDate(result);
	}

	private static boolean isKeyWord(String input) {
		for (int i = 0; i < keyWord.length; i++) {
			if (keyWord[i].equals(input)) {
				return true;
			}
		}
		return false;
	}

	private static String parseVenue(ArrayList<String> input) {
		String result = "";
		int indexOfKeyWord = input.lastIndexOf("AT");
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		if (notContainsKeyword(indexOfKeyWord)) {
			return null;
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWord, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		result = getInputBetweenArrayList(input, indexOfKeyWord, indexOfNextKeyWord);
		return result;
	}

	private static int findNextKeyword(ArrayList<String> input, int indexOfKeyWord, int indexOfNextKeyWord) {
		for (int i = indexOfKeyWord + 1; i < input.size(); i++) {
			if (isKeyWord(input.get(i))) {
				indexOfNextKeyWord = i;
				break;
			}
		}
		return indexOfNextKeyWord;
	}

	private static boolean notContainsKeyword(int indexOfKeyWord) {
		return indexOfKeyWord == NO_KEYWORD;
	}

	private static String getInputBetweenArrayList(ArrayList<String> input, int indexOfKeyWord,
			int indexOfNextKeyWord) {
		String result = "";
		for (int i = 0; i < indexOfNextKeyWord - indexOfKeyWord - 1; i++) {
			result = result + " " + input.remove(indexOfKeyWord + 1);
		}
		result = result.substring(1);
		input.remove(indexOfKeyWord);
		return result;
	}

	private static String parseDescription(ArrayList<String> input) {
		String result = "";

		if (input.isEmpty()) {
			return null;
		}
		while (!input.isEmpty()) {
			result = result + " " + input.remove(0);
		}
		return result.substring(1, result.length());
	}

	private static String parsePriority(ArrayList<String> input) {

		if (input.lastIndexOf("PRIORITY") == NO_KEYWORD) {
			return null;
		} else {
			int keyWordLocation = input.lastIndexOf("PRIORITY");
			int priorityLocate = keyWordLocation + 1;
			String priority = input.get(priorityLocate).toUpperCase();

			switch (priority) {
			case "HIGH":
				input.remove(priorityLocate);
				input.remove(keyWordLocation);
				return "HIGH";
			case "MEDIUM":
				input.remove(priorityLocate);
				input.remove(keyWordLocation);
				return "MEDIUM";
			case "LOW":
				input.remove(priorityLocate);
				input.remove(keyWordLocation);
				return "LOW";
			default:
				return null;
			}
		}
	}

	private static CommandDetails.COMMANDS parseCommandType(ArrayList<String> input) {

		switch (input.get(0).toUpperCase()) {
		case "ADD":
			System.out.println("ADD command");
			input.remove(0);
			return CommandDetails.COMMANDS.ADD;
		case "a":
			System.out.println("ADD command");
			input.remove(0);
			return CommandDetails.COMMANDS.ADD;
		case "DELETE":
			System.out.println("DELETE command");
			input.remove(0);
			return CommandDetails.COMMANDS.DELETE;
		case "d":
			System.out.println("DELETE command");
			input.remove(0);
			return CommandDetails.COMMANDS.DELETE;
		case "COMPLETE":
			System.out.println("COMPLETE command");
			input.remove(0);
			return CommandDetails.COMMANDS.COMPLETE;
		case "INCOMPLETE":
			System.out.println("INCOMPLETE command");
			input.remove(0);
			return CommandDetails.COMMANDS.INCOMPLETE;
		case "HELP":
			System.out.println("HELP command");
			input.remove(0);
			return CommandDetails.COMMANDS.HELP;
		case "REDO":
			System.out.println("REDO command");
			input.remove(0);
			return CommandDetails.COMMANDS.REDO;
		case "SEARCH":
			System.out.println("SEARCH command");
			input.remove(0);
			return CommandDetails.COMMANDS.SEARCH;
		case "s":
			System.out.println("SEARCH command");
			input.remove(0);
			return CommandDetails.COMMANDS.SEARCH;
		case "UNDO":
			System.out.println("UNDO command");
			input.remove(0);
			return CommandDetails.COMMANDS.UNDO;
		case "DISPLAY":
			System.out.println("DISPLAY command");
			input.remove(0);
			return CommandDetails.COMMANDS.DISPLAY;
		case "UPDATE":
			System.out.println("UPDATE command");
			input.remove(0);
			return CommandDetails.COMMANDS.UPDATE;
		case "u":
			System.out.println("UPDATE command");
			input.remove(0);
			return CommandDetails.COMMANDS.UPDATE;
		case "FLOATING":
			System.out.println("FLOATING command");
			input.remove(0);
			return CommandDetails.COMMANDS.FLOATING;
		case "EXIT":
			System.out.println("EXIT command");
			input.remove(0);
			return CommandDetails.COMMANDS.EXIT;
		default:
			System.out.print("Invalid Command");
			input.remove(0);
			return CommandDetails.COMMANDS.INVALID;
		}
	}

	static Date createDate(String input) {
		for (String temp : DATE_FORMAT) {
			try {
				SimpleDateFormat possibleFormats = new SimpleDateFormat(temp);
				possibleFormats.setLenient(false);
				Date mydate = possibleFormats.parse(input);
				Calendar cal = Calendar.getInstance();
				cal.setTime(mydate);
				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT && cal.get(Calendar.MONTH) == 0
						&& cal.get(Calendar.DATE) == 1) {
					cal.setTime(mydate);
					cal.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
					cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
					if (input.toUpperCase().contains("TOMORROW")) {
						cal.add(Calendar.DATE, 1);
					}
					if (input.toUpperCase().contains("NEXT WEEK")) {
						cal.add(Calendar.DATE, 7);
					}

					mydate = cal.getTime();
					return mydate;
				}
				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT) {
					cal.setTime(mydate);
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
					mydate = cal.getTime();
					return mydate;
				}
				return mydate;
			} catch (ParseException e) {
				// Does not match format, proceed to compare next format
			}
		}
		// if today exist, time is default 2359 !!!!! need edit!!!! Split into 2
		// create date format to support more specific keywords
		if (input.toUpperCase().equals("TODAY") || input.toUpperCase().equals("TOMORROW")) {
			SimpleDateFormat format = new SimpleDateFormat("HHmm");
			try {
				Date mydate = specialDateKeyWords(input, format);
				return mydate;
			} catch (ParseException e) {
				// self set time format, ignore
			}
		}

		return null;
	}

	private static Date specialDateKeyWords(String input, SimpleDateFormat today) throws ParseException {
		String endTimeOfDay = "2359";
		Date mydate = today.parse(endTimeOfDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(mydate);
		cal.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		if (input.toUpperCase().contains("TOMORROW")) {
			cal.add(Calendar.DATE, 1);
		}
		mydate = cal.getTime();
		return mydate;
	}

	private static int parseID(ArrayList<String> input) {
		try {
			return Integer.parseInt(input.remove(0));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static CommandDetails parse(String input) {
		String venue;
		String priority;
		String description;
		Date start;
		Date end;
		//boolean containSearchTime = false;

		int ID = NO_ID;

		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		CommandDetails.COMMANDS command = parseCommandType(strTokens);

		if (command == CommandDetails.COMMANDS.DELETE || command == CommandDetails.COMMANDS.COMPLETE
				|| command == CommandDetails.COMMANDS.INCOMPLETE || command == CommandDetails.COMMANDS.UPDATE) {
			ID = parseID(strTokens);
		}

		/*if (command == CommandDetails.COMMANDS.SEARCH) {
			containSearchTime = parseSearchTime(strTokens);
		}*/

		priority = parsePriority(strTokens);
		venue = parseVenue(strTokens);
		start = parseStartDate(strTokens);
		end = parseEndDate(strTokens);
		description = parseDescription(strTokens);

		// to check if details correct
		CommandDetails details = new CommandDetails(command, description, venue, start, end, priority, ID);
		System.out.println(details);

		return new CommandDetails(command, description, venue, start, end, priority, ID);
	}

	private static boolean parseSearchTime(ArrayList<String> input) {
		String[] timeFormat = { "HHmm", "HH.mm", "HH:mm", "hhmma", "hha", "hmma", "hh.mma", "hh:mma" };

		if (input.indexOf("FROM") != -1) {
			String result = (input.get(input.indexOf("FROM") + 1));
			System.out.println("result here here " + result);
			
			for (String temp :timeFormat) {
				try {
					SimpleDateFormat possibleFormats = new SimpleDateFormat(temp);
					possibleFormats.setLenient(false);
					possibleFormats.parse(result);
					return true;
				} catch (ParseException e) {
					//// next;
				}
			}


		}
		return false;
	}
}