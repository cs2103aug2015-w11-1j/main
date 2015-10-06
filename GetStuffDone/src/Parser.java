
/*
	keywords demarked by (case sensitive):
	Start date and time - FROM
	End date and time - BY/TO
	Priority - PRIORITY
	Venue - AT
	
	eg. add do homework AT School of Computing FROM 12.30pm 6/10/15 TO 6pm 6/10/15 PRIORITY high
	
	Date and time format - <time><date>
	time format - HHmm		2359
				- HH.mm		23.59
				- HH:mm		23:59
				- hhmma		1159pm
				- hha		11pm
				- hh.mma	11.59pm
				- hh:mma	11:59pm
				
	date format - dd MMMM yy	01 April 15		01 Apr 15
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
				
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Parser {
	private final static String[] DATE_FORMAT = { "HHmm", "HHmm dd MMMM yy", "HH.mm dd MMMM yy", "HH:mm dd MMMM yy",
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
			"hh:mma dd-MM", "dd-MM",

	};
	
	private static ArrayList<String> keyWords = new ArrayList<String>() {
		{
			add("AT");
			add("BY");
			add("FROM");
			add("TO");
			add("PRIORITY");
		}
	};

	/*public enum COMMANDS {
		ADD, DELETE, DONE, UNDO, REDO, SEARCH, HELP,DISPLAY
	}*/

	private final static int NO_NEXT_KEYWORD = -2;
	private final static int NO_KEYWORD = -1;
	private final static int NO_YEAR_INPUT = 1970;

	private static Date parseStartDate(ArrayList<String> input) {
		String result = "";
		int indexOfKeyWord = input.lastIndexOf("FROM");
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;

		if (containsKeword(indexOfKeyWord)) {
			//System.out.print("no start date, default set as today ");
			return createDate(result);
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

		if (containsKeword(indexOfKeyWordTO) && containsKeword(indexOfKeyWordBY)) {
			return null;
		} else if (containsKeword(indexOfKeyWordTO) && !containsKeword(indexOfKeyWordBY)) {
			indexOfNextKeyWord = NO_NEXT_KEYWORD;
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordBY, indexOfNextKeyWord);
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		// end date is FROM
		if (containsKeword(indexOfKeyWordBY)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		// end date is BY
		if (containsKeword(indexOfKeyWordTO)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordBY, indexOfNextKeyWord);
		}
		
		System.out.println(result);
		return createDate(result);
	}

	private static boolean isKeyWord(String input) {
		for (int i = 0; i < keyWords.size(); i++) {
			if (keyWords.get(i).equals(input)) {
				return true;
			}
		}
		return false;
	}

	private static String parseVenue(ArrayList<String> input) {
		int indexOfKeyWord = input.lastIndexOf("AT");
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		if (containsKeword(indexOfKeyWord)) {
			return null;
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWord, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		String result = getInputBetweenArrayList(input, indexOfKeyWord, indexOfNextKeyWord);
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

	private static boolean containsKeword(int indexOfKeyWord) {
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
			// System.out.println("please include a description for task");
			return result;
		}

		while (!input.isEmpty()) {
			result = result + " " + input.remove(0);
		}

		return result.substring(1, result.length());
	}

	private static String parsePriority(ArrayList<String> input) {

		if (input.lastIndexOf("PRIORITY") == NO_KEYWORD) {
			//System.out.println("No Priority found");
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
		case "DELETE":
			System.out.println("DELETE command");
			input.remove(0);
			return CommandDetails.COMMANDS.DELETE;
		case "DISPLAY":
			System.out.println("DISPLAY command");
			input.remove(0);
			return CommandDetails.COMMANDS.DISPLAY;
		case "DONE":
			System.out.println("DONE command");
			input.remove(0);
			return CommandDetails.COMMANDS.DONE;
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
		case "UNDO":
			System.out.println("UNDO command");
			input.remove(0);
			return CommandDetails.COMMANDS.UNDO;
		}
		return null;
	}

	// creating and sending commandDetails object to logic class
	//static Logic commandDetailsObject = null;

	/*Parser(Logic obj) {
		this.commandDetailsObject = obj;
	}*/

	/*
	public static void sendToLogic(CommandDetails cmdDetails) {
		commandDetailsObject.setCmdDetailsObj(cmdDetails);
	}
	*/
	
	static Date createDate(String input) {
		for (String temp : DATE_FORMAT) {
			try {
				SimpleDateFormat possibleFormats = new SimpleDateFormat(temp);
				possibleFormats.setLenient(false);
				Date mydate = possibleFormats.parse(input);
				Calendar cal = Calendar.getInstance();
				
				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT) {
					cal.setTime(mydate);
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
					mydate = cal.getTime();
					return mydate;
				}
				
				if(input.toUpperCase().contains("TODAY")){
					//System.out.println(mydate + " mydate");
					cal.setTime(mydate);
					cal.set(Calendar.DATE,Calendar.getInstance().get(Calendar.DATE));
					cal.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH));
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
					mydate = cal.getTime();
					return mydate;
					//System.out.println(mydate + " new mydate");
				}
				
				if(input.toUpperCase().contains("TOMORROW")){
					cal.setTime(mydate);
					cal.set(Calendar.DATE,Calendar.getInstance().get(Calendar.DATE));
					cal.add(Calendar.DATE, 1);
					cal.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH));
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
					mydate = cal.getTime();
					return mydate;
				}
				
				if(input.toUpperCase().contains("NEXT WEEK")){
					cal.setTime(mydate);
					cal.set(Calendar.DATE,Calendar.getInstance().get(Calendar.DATE));
					cal.add(Calendar.DATE, 7);
					cal.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH));
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
					mydate = cal.getTime();
					return mydate;
				}
				
				//System.out.println(temp +" match "+ input);
				return mydate;
			} catch (ParseException e) {
				// System.out.println(temp +" dont match, next!");
			}catch (NullPointerException f){
				// System.out.println("no date given");
			}
		}
		return null;
	}

	public static CommandDetails parse(String input) {
		String venue;
		String priority;
		String description;
		Date start;
		Date end;

		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		CommandDetails.COMMANDS command = parseCommandType(strTokens);
		priority = parsePriority(strTokens);
		System.out.println("Priority= " + priority);
		venue = parseVenue(strTokens);
		System.out.println("Venue= " + venue);
		start = parseStartDate(strTokens);
		System.out.println("Start= " + start);
		end = parseEndDate(strTokens);
		System.out.println("End= " + end);
		description = parseDescription(strTokens);
		System.out.println("description= " + description);
		return new CommandDetails(command, description, venue, start, end, priority);
	}
/*
	public static void main(String[] args) {

		String deadLine = null;
		String startDate = null;
		String venue = null;
		String priority;
		String description;

		Scanner sc = new Scanner(System.in);

		String input = sc.nextLine();
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));

		System.out.println(input);

		COMMANDS command = parseCommandType(strTokens);
		// System.out.println(strTokens);

		priority = parsePriority(strTokens);
		System.out.println("Priority= " + priority);
		// System.out.println(strTokens);

		venue = parseVenue(strTokens);
		System.out.println("Venue= " + venue);
		// System.out.println(strTokens);

		startDate = parseStartDate(strTokens);
		// System.out.println("Start= "+ startDate);
		// System.out.println(strTokens);
		Date start = createDate(startDate);
		System.out.println("Start= " + start);

		deadLine = parseEndDate(strTokens);
		System.out.println("End= " + deadLine);
		// System.out.println(strTokens);
		Date end = createDate(deadLine);
		System.out.println("End= " + end);

		description = parseDescription(strTokens);
		System.out.println("Description= " + description);

		// create commandDetailsObject
		// CommandDetails cmdDetails = new CommandDetails(end, start, venue,
		// priority, description);
		// sendToLogic(cmdDetails);

	}
	
	*/
}