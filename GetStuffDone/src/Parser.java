
/*
	<------------------------------------------------------->
	Command keywords demarked by (case insensitive)
	Adding of task				add			-a
	Deleting of task			delete		-d
	Displaying of task			all
	Marking task as complete	complete
	Displaying help manual		help
	Redo last action			redo
	Search task					search		-s
	Undo last action			undo
	Editing an existing task	update		-u
	
	<------------------------------------------------------->
	Time keywords demarked by (case sensitive):
	Start date and time - FROM
	End date and time - BY/TO
	
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
				
				
		<Command> <TaskID (if applicable)> <Time, description of task>
	eg. -U 2 do homework FROM 12.30pm 6/10/15 TO 6pm 6/10/15 
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

	private final static String[] keyWord = { "BY", "FROM", "TO", "AT" };
	private final static String[] TimekeyWord = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
			"SUNDAY", "TODAY", "TOMORROW" };

	private final static int NO_NEXT_KEYWORD = -2;
	private final static int NO_ID = -10;
	private final static int NO_KEYWORD = -1;
	private final static int NO_YEAR_INPUT = 1970;

	private static Date parseStartDate(ArrayList<String> input) throws ParseException, invalidDateFormat {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int indexOfKeyWordFROM = input.lastIndexOf("FROM");
		int indexOfKeyWordAT = input.lastIndexOf("AT");
		if (notContainsKeyword(indexOfKeyWordFROM) && notContainsKeyword(indexOfKeyWordAT)) {
			return null;
		} else if (notContainsKeyword(indexOfKeyWordFROM) && !notContainsKeyword(indexOfKeyWordAT)) {
			indexOfNextKeyWord = NO_NEXT_KEYWORD;
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordAT, indexOfNextKeyWord);
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordFROM, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		// end date is AT
		if (notContainsKeyword(indexOfKeyWordAT)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordFROM, indexOfNextKeyWord);
		}
		// end date is FROM
		if (notContainsKeyword(indexOfKeyWordFROM)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordAT, indexOfNextKeyWord);
		}
		return createStartDate(result);
	}

	private static int checkLastIndex(ArrayList<String> input, int indexOfNextKeyWord) {
		if (indexOfNextKeyWord == NO_NEXT_KEYWORD) {
			indexOfNextKeyWord = input.size();
		}
		return indexOfNextKeyWord;
	}

	private static Date parseEndDate(ArrayList<String> input) throws ParseException, invalidDateFormat {
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
		} ///////////
		return createEndDate(result);
	}

	private static boolean isKeyWord(String input) {
		for (int i = 0; i < keyWord.length; i++) {
			if (keyWord[i].equals(input)) {
				return true;
			}
		}
		return false;
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
		case "ALL":
			System.out.println("ALL command");
			input.remove(0);
			return CommandDetails.COMMANDS.ALL;
		case "UPDATE":
			System.out.println("UPDATE command");
			input.remove(0);
			return CommandDetails.COMMANDS.UPDATE;
		case "u":
			System.out.println("UPDATE command");
			input.remove(0);
			return CommandDetails.COMMANDS.UPDATE;
		case "SET":
			System.out.println("SET command");
			input.remove(0);
			return CommandDetails.COMMANDS.SET;
		case "FLOATING":
			System.out.println("FLOATING command");
			input.remove(0);
			return CommandDetails.COMMANDS.FLOATING;
		case "EVENTS":
			System.out.println("EVENTS command");
			input.remove(0);
			return CommandDetails.COMMANDS.EVENTS;
		case "DEADLINES":
			System.out.println("DEADLINES command");
			input.remove(0);
			return CommandDetails.COMMANDS.DEADLINES;
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

	static Date createStartDate(String input) throws ParseException, invalidDateFormat {
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
					////////////////////////////////////////////////////////////////////////////////////////////
					keyWords(input, cal);
					//////////////////////////////////////////////////////////////////////////////////////////////////
					mydate = cal.getTime();

					return mydate;
				}
				
				for (int i = 0; i < TimekeyWord.length; i++) {
					if (input.toUpperCase().contains(TimekeyWord[i])) {
						throw new invalidDateFormat(input);
					}
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

		for (int i = 0; i < TimekeyWord.length; i++) {

			if (input.toUpperCase().equals(TimekeyWord[i])) {
				// input.toUpperCase().equals("TOMORROW")) {
				SimpleDateFormat format = new SimpleDateFormat("HHmm");
				try {
					String endTimeOfDay = "0000";
					Date mydate = format.parse(endTimeOfDay);
					Calendar cal = Calendar.getInstance();
					cal.setTime(mydate);
					cal.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
					cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

					keyWords(input, cal);

					mydate = cal.getTime();
					return mydate;
				} catch (ParseException e) {
					// self set time format, ignore
				}
			}
		}
		throw new ParseException(input, 0);
	}

	private static void keyWords(String input, Calendar cal) {
		Calendar now = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		int NOW_HOUR_OF_DAY = now.get(Calendar.HOUR_OF_DAY);
		int NOW_MINUTE = now.get(Calendar.MINUTE);

		if (input.toUpperCase().contains("TOMORROW")) {
			cal.add(Calendar.DATE, 1);
		}

		if (input.toUpperCase().contains("MONDAY")) {
			if (weekday != Calendar.MONDAY) {
				int days = (Calendar.MONDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("TUESDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.TUESDAY) {
				int days = (Calendar.TUESDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("WEDNESDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.WEDNESDAY) {
				int days = (Calendar.WEDNESDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("THURSDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.THURSDAY) {
				int days = (Calendar.THURSDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("FRIDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.FRIDAY) {
				int days = (Calendar.FRIDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("SATURDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SATURDAY) {
				int days = (Calendar.SATURDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("SUNDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SUNDAY) {
				int days = (Calendar.SUNDAY - weekday) % 7;
				cal.add(Calendar.DAY_OF_YEAR, days + 7);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}
	}

	static Date createEndDate(String input) throws ParseException, invalidDateFormat {

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

					keyWords(input, cal);
					mydate = cal.getTime();
					return mydate;
				}

				for (int i = 0; i < TimekeyWord.length; i++) {
					if (input.toUpperCase().contains(TimekeyWord[i])) {
						throw new invalidDateFormat(input);
					}
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

		for (int i = 0; i < TimekeyWord.length; i++) {

			if (input.toUpperCase().equals(TimekeyWord[i])) {
				// input.toUpperCase().equals("TOMORROW")) {
				SimpleDateFormat format = new SimpleDateFormat("HHmm");
				try {
					String endTimeOfDay = "2359";
					Date mydate = format.parse(endTimeOfDay);
					Calendar cal = Calendar.getInstance();
					cal.setTime(mydate);
					cal.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
					cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

					keyWords(input, cal);

					mydate = cal.getTime();
					return mydate;
				} catch (ParseException e) {
					// self set time format, ignore
				}
			}
		}
		throw new ParseException(input, 0);
	}

	private static int parseID(ArrayList<String> input) {
		int ID;
		ID = Integer.parseInt(input.remove(0));
		return ID;
	}

	public static CommandDetails parse(String input) throws ParseException, NumberFormatException, invalidDateFormat {
		String description;
		Date start;
		Date end;
		boolean copy = false;

		int ID = NO_ID;

		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));

		validateInput(strTokens);

		CommandDetails.COMMANDS command = parseCommandType(strTokens);

		if (command == CommandDetails.COMMANDS.DELETE || command == CommandDetails.COMMANDS.COMPLETE
				|| command == CommandDetails.COMMANDS.INCOMPLETE || command == CommandDetails.COMMANDS.UPDATE) {
			ID = parseID(strTokens);
		}

		end = parseEndDate(strTokens);
		if (strTokens.contains("AT")) {
			copy = true;
		}
		start = parseStartDate(strTokens);
		if (copy) {
			end = start;
		}
		description = parseDescription(strTokens);

		
		
		// to check if details correct

		CommandDetails details = new CommandDetails(command, description, start, end, ID);

		System.out.println(details);

		return new CommandDetails(command, description, start, end, ID);
	}

	private static void validateInput(ArrayList<String> strTokens) throws ParseException {

		int count = 0;
		for (String temp : strTokens) {
			if (temp == "FROM" || temp == "AT") {
				count++;
			}
		}
		if (count > 2) {
			throw new ParseException(null, 0);
		}
		count = 0;
		for (String temp : strTokens) {
			if (temp == "BY" || temp == "TO") {
				count++;
			}
		}
		if (count > 2) {
			throw new ParseException(null, 0);
		}
	}
}

class invalidDateFormat extends Exception
{
      //Parameterless Constructor
      public invalidDateFormat() {}

      //Constructor that accepts a message
      public invalidDateFormat(String message)
      {
         super(message);
      }
 }
