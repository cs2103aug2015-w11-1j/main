
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

			"HHmm dd/MMMM/yy", "HH.mm dd/MMMMM/yy", "HH:mm dd/MMMM/yy", "hhmma dd/MMMM/yy", "hha dd/MMMM/yy",
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

			"hhmma", "hha", "hmma", "hh.mma", "hh:mma", "HH.mm", "HH:mm", "HHmm",

	};

	private final static String[] keyWord = { "BY", "FROM", "TO", "AT", "ON", "DAILY", "MONTHLY", "YEARLY", "WEEKLY" };
	private final static String[] TimekeyWord = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
			"SUNDAY", "TODAY", "TOMORROW" };
	private final static String[] RECURRING_KEY_WORD = { "DAILY", "MONTHLY", "YEARLY", "WEEKLY" };

	private final static int NO_NEXT_KEYWORD = -2;
	private final static int NO_ID = -10;
	private final static int NO_KEYWORD = -1;
	private final static int NO_YEAR_INPUT = 1970;

	static Date parseStartDate(ArrayList<String> input) throws ParseException, invalidTimeDateInput, invalidParameters {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int arrayListIndex = 0;

		int indexOfKeyWordFROM = -1;
		for (String temp : input) {
			if (temp.equalsIgnoreCase("FROM")) {
				indexOfKeyWordFROM = arrayListIndex;
			}
			arrayListIndex++;
		}

		arrayListIndex = 0;
		int indexOfKeyWordAT = -1;
		for (String temp : input) {
			if (temp.equalsIgnoreCase("AT")) {
				indexOfKeyWordAT = arrayListIndex;
			}
			arrayListIndex++;
		}

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
		// System.out.println(result);
		return createStartDate(result);
	}

	private static int checkLastIndex(ArrayList<String> input, int indexOfNextKeyWord) {
		if (indexOfNextKeyWord == NO_NEXT_KEYWORD) {
			indexOfNextKeyWord = input.size();
		}
		return indexOfNextKeyWord;
	}

	static Date parseEndDate(ArrayList<String> input) throws ParseException, invalidTimeDateInput, invalidParameters {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int arrayListIndex = 0;

		int indexOfKeyWordTO = -1;
		for (String temp : input) {
			if (temp.equalsIgnoreCase("TO")) {
				indexOfKeyWordTO = arrayListIndex;
			}
			arrayListIndex++;
		}

		arrayListIndex = 0;
		int indexOfKeyWordBY = -1;
		for (String temp : input) {
			if (temp.equalsIgnoreCase("BY")) {
				indexOfKeyWordTO = arrayListIndex;
			}
			arrayListIndex++;
		}

		arrayListIndex = 0;
		int indexOfKeyWordON = -1;
		for (String temp : input) {
			if (temp.equalsIgnoreCase("ON")) {
				indexOfKeyWordON = arrayListIndex;
			}
			arrayListIndex++;
		}

		if (notContainsKeyword(indexOfKeyWordTO) && notContainsKeyword(indexOfKeyWordBY)
				&& notContainsKeyword(indexOfKeyWordON)) {
			return null;
		} else if (notContainsKeyword(indexOfKeyWordTO) && !notContainsKeyword(indexOfKeyWordBY)
				&& notContainsKeyword(indexOfKeyWordON)) {
			indexOfNextKeyWord = NO_NEXT_KEYWORD;
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordBY, indexOfNextKeyWord);
		} else if (notContainsKeyword(indexOfKeyWordTO) && notContainsKeyword(indexOfKeyWordBY)
				&& !notContainsKeyword(indexOfKeyWordON)) {
			indexOfNextKeyWord = NO_NEXT_KEYWORD;
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordON, indexOfNextKeyWord);
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}

		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		// end date is TO
		if (notContainsKeyword(indexOfKeyWordBY) && notContainsKeyword(indexOfKeyWordON)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		// end date is BY
		if (notContainsKeyword(indexOfKeyWordTO) && notContainsKeyword(indexOfKeyWordON)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordBY, indexOfNextKeyWord);
		}
		// end date is ON
		if (notContainsKeyword(indexOfKeyWordTO) && notContainsKeyword(indexOfKeyWordBY)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordON, indexOfNextKeyWord);
		}

		///////////
		return createEndDate(result);
	}

	private static boolean isKeyWord(String input) {
		for (int i = 0; i < keyWord.length; i++) {
			if (keyWord[i].equalsIgnoreCase(input)) {
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

	private static String getInputBetweenArrayList(ArrayList<String> input, int indexOfKeyWord, int indexOfNextKeyWord)
			throws invalidParameters {
		String result = "";
		for (int i = 0; i < indexOfNextKeyWord - indexOfKeyWord - 1; i++) {
			result = result + " " + input.remove(indexOfKeyWord + 1);
		}
		try {
			result = result.substring(1);
		} catch (StringIndexOutOfBoundsException e) {
			throw new invalidParameters();
		}
		input.remove(indexOfKeyWord);
		return result;
	}

	static String parseDescription(ArrayList<String> input) {
		String result = "";

		if (input.isEmpty()) {
			return null;
		}

		while (!input.isEmpty()) {
			if (input.get(0).contains("/")) {
				result = result + " " + input.remove(0).substring(1);
			} else {
				result = result + " " + input.remove(0);
			}
		}
		return result.substring(1, result.length());
	}

	static CommandDetails.COMMANDS parseCommandType(ArrayList<String> input) throws invalidCommand {

		switch (input.get(0).toUpperCase()) {
		case "ADD":
			input.remove(0);
			return CommandDetails.COMMANDS.ADD;
		case "a":
			input.remove(0);
			return CommandDetails.COMMANDS.ADD;
		case "DELETE":
			input.remove(0);
			return CommandDetails.COMMANDS.DELETE;
		case "d":
			input.remove(0);
			return CommandDetails.COMMANDS.DELETE;
		case "COMPLETE":
			input.remove(0);
			return CommandDetails.COMMANDS.COMPLETE;
		case "INCOMPLETE":
			input.remove(0);
			return CommandDetails.COMMANDS.INCOMPLETE;
		case "HELP":
			input.remove(0);
			return CommandDetails.COMMANDS.HELP;
		case "REDO":
			input.remove(0);
			return CommandDetails.COMMANDS.REDO;
		case "SEARCH":
			input.remove(0);
			return CommandDetails.COMMANDS.SEARCH;
		case "s":
			input.remove(0);
			return CommandDetails.COMMANDS.SEARCH;
		case "UNDO":
			input.remove(0);
			return CommandDetails.COMMANDS.UNDO;
		case "ALL":
			input.remove(0);
			return CommandDetails.COMMANDS.ALL;
		case "UPDATE":
			input.remove(0);
			return CommandDetails.COMMANDS.UPDATE;
		case "u":
			input.remove(0);
			return CommandDetails.COMMANDS.UPDATE;
		case "SET":
			input.remove(0);
			return CommandDetails.COMMANDS.SET;
		case "FLOATING":
			input.remove(0);
			return CommandDetails.COMMANDS.FLOATING;
		case "EVENTS":
			input.remove(0);
			return CommandDetails.COMMANDS.EVENTS;
		case "DEADLINES":
			input.remove(0);
			return CommandDetails.COMMANDS.DEADLINES;
		case "RECURRING":
			input.remove(0);
			return CommandDetails.COMMANDS.RECURRING;
		case "EXIT":
			input.remove(0);
			return CommandDetails.COMMANDS.EXIT;
		default:
			// System.out.print("Invalid Command");
			// input.remove(0);
			// throw new invalidCommand(input.remove(0));
			// return CommandDetails.COMMANDS.INVALID;
			return CommandDetails.COMMANDS.ADD;
		}
	}

	static Date createStartDate(String input) throws ParseException, invalidTimeDateInput {
		for (String temp : DATE_FORMAT) {
			try {
				SimpleDateFormat possibleFormats = new SimpleDateFormat(temp);
				possibleFormats.setLenient(false);
				Date mydate = possibleFormats.parse(input);
				Calendar cal = Calendar.getInstance();
				cal.setTime(mydate);
				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT && (input.toUpperCase().contains("TODAY")
						|| input.toUpperCase().contains("TOMORROW") || input.toUpperCase().contains("MONDAY")
						|| input.toUpperCase().contains("TUESDAY") || input.toUpperCase().contains("WEDNESDAY")
						|| input.toUpperCase().contains("THURSDAY") || input.toUpperCase().contains("FRIDAY")
						|| input.toUpperCase().contains("SATURDAY") || input.toUpperCase().contains("SUNDAY"))) {
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

				Calendar currentTime = Calendar.getInstance();

				if (temp.equals("hhmma") || temp.equals("hha") || temp.equals("hmma") || temp.equals("hh.mma")
						|| temp.equals("hh:mma") || temp.equals("HH.mm") || temp.equals("HH:mm")
						|| temp.equals("HHmm")) {
					cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
					// cal.set(Calendar.DAY_OF_MONTH,
					// Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
					// System.out.println("format is " + cal);
				}

				for (int i = 0; i < TimekeyWord.length; i++) {
					if (input.toUpperCase().contains(TimekeyWord[i])) {
						throw new invalidTimeDateInput(input);
					}
				}

				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT) {
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
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("TUESDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.TUESDAY) {
				int days = (Calendar.TUESDAY - weekday) % 7;
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("WEDNESDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.WEDNESDAY) {
				int days = (Calendar.WEDNESDAY - weekday) % 7;
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("THURSDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.THURSDAY) {
				int days = (Calendar.THURSDAY - weekday) % 7;
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("FRIDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.FRIDAY) {
				int days = (Calendar.FRIDAY - weekday) % 7;
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("SATURDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SATURDAY) {
				int days = (Calendar.SATURDAY - weekday) % 7;
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		if (input.toUpperCase().contains("SUNDAY")) {
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SUNDAY) {
				int days = (Calendar.SUNDAY - weekday) % 7;
				if (days < 0) {
					days = days + 7;
				}
				cal.add(Calendar.DAY_OF_YEAR, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
		}
	}

	static Date createEndDate(String input) throws ParseException, invalidTimeDateInput {

		for (String temp : DATE_FORMAT) {
			try {
				SimpleDateFormat possibleFormats = new SimpleDateFormat(temp);
				possibleFormats.setLenient(false);
				Date mydate = possibleFormats.parse(input);
				Calendar cal = Calendar.getInstance();
				cal.setTime(mydate);
				/*
				 * if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT &&
				 * cal.get(Calendar.MONTH) == 0 && cal.get(Calendar.DATE) == 1)
				 * {
				 */

				// System.out.println("format is " + temp);
				if (temp.equals("dd/MM") || temp.equals("dd-MM") || temp.equals("dd-MMMM") || temp.equals("dd-MM-yy")
						|| temp.equals("dd-MMMM-yy") || temp.equals("dd/MMMM") || temp.equals("dd/MM/yy")
						|| temp.equals("dd/MMMM/yy") || temp.equals("dd MM") || temp.equals("dd MMMM")
						|| temp.equals("dd MM yy") || temp.equals("dd MMMM yy")) {
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					// System.out.println("format is " + cal);
				}

				if (temp.equals("hhmma") || temp.equals("hha") || temp.equals("hmma") || temp.equals("hh.mma")
						|| temp.equals("hh:mma") || temp.equals("HH.mm") || temp.equals("HH:mm")
						|| temp.equals("HHmm")) {
					cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
					// cal.set(Calendar.DAY_OF_MONTH,
					// Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
					// System.out.println("format is " + cal);
				}

				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT && (input.toUpperCase().contains("TODAY")
						|| input.toUpperCase().contains("TOMORROW") || input.toUpperCase().contains("MONDAY")
						|| input.toUpperCase().contains("TUESDAY") || input.toUpperCase().contains("WEDNESDAY")
						|| input.toUpperCase().contains("THURSDAY") || input.toUpperCase().contains("FRIDAY")
						|| input.toUpperCase().contains("SATURDAY") || input.toUpperCase().contains("SUNDAY"))) {

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
						throw new invalidTimeDateInput(input);
					}
				}

				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT) {
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

	static int parseID(ArrayList<String> input) throws invalidParameters {
		int ID = -10;
		try {
			ID = Integer.parseInt(input.remove(0));
		} catch (IndexOutOfBoundsException e) {
			throw new invalidParameters();
		}
		return ID;
	}

	public static CommandDetails parse(String input)
			throws ParseException, invalidCommand, invalidParameters, invalidTimeDateInput {
		String description;
		Date start;
		Date end;
		Date originalStart;
		Date originalEnd;
		boolean copy = false;
		String recurring = null;
		Date endingDate = null;

		int ID = NO_ID;

		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));

		validateInput(strTokens);

		CommandDetails.COMMANDS command = parseCommandType(strTokens);

		if (command == CommandDetails.COMMANDS.DELETE || command == CommandDetails.COMMANDS.COMPLETE
				|| command == CommandDetails.COMMANDS.INCOMPLETE || command == CommandDetails.COMMANDS.UPDATE) {
			ID = parseID(strTokens);
		}

		end = parseEndDate(strTokens);

		for (String temp : strTokens) {
			if (temp.equalsIgnoreCase("AT")) {
				copy = true;
			}
		}

		start = parseStartDate(strTokens);
		if (copy) {
			end = start;
		}

		//////////////////////////////////////
		recurring = parseRecurring(strTokens);
		endingDate = parseEndingDate(strTokens);
		if (endingDate == null && recurring != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			endingDate = format.parse("31/12/8089");
		}
		//////////////////////////////////////
		description = parseDescription(strTokens);

		originalStart = start;
		originalEnd = end;
		// to check if details correct

		CommandDetails details = new CommandDetails(command, description, start, end, ID, recurring, originalStart,
				originalEnd, endingDate);
		System.out.println(details);
		validateCommandDetails(command, ID, description, start, end, input, recurring, originalStart, originalEnd,
				endingDate);

		return new CommandDetails(command, description, start, end, ID, recurring, originalStart, originalEnd,
				endingDate);
	}

	private static Date parseEndingDate(ArrayList<String> input)
			throws ParseException, invalidTimeDateInput, invalidParameters {

		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;

		int arrayListIndex = 0;

		int indexOfKeyWordEnding = -1;
		for (String temp : input) {
			if (temp.equalsIgnoreCase("ENDING")) {
				indexOfKeyWordEnding = arrayListIndex;
			}
			arrayListIndex++;
		}

		if (notContainsKeyword(indexOfKeyWordEnding)) {
			return null;
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordEnding, indexOfNextKeyWord);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);

		result = getInputBetweenArrayList(input, indexOfKeyWordEnding, indexOfNextKeyWord);

		return createEndDate(result);

	}

	private static String parseRecurring(ArrayList<String> strTokens) {
		int arrayListIndex = 0;
		for (String temp : strTokens) {
			for (int i = 0; i < RECURRING_KEY_WORD.length; i++) {
				if (temp.equalsIgnoreCase(RECURRING_KEY_WORD[i])) {
					return strTokens.remove(arrayListIndex).toUpperCase();
				}
			}
			arrayListIndex++;
		}

		return null;
	}

	private static void validateCommandDetails(CommandDetails.COMMANDS command, int ID, String description, Date start,
			Date end, String input, String recurring, Date originalStart, Date originalEnd, Date endingDate)
					throws invalidParameters, invalidTimeDateInput {

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.MILLISECOND, 0);
		today.set(Calendar.SECOND, 0);
		Date todayDate = today.getTime();

		if (command == CommandDetails.COMMANDS.HELP || command == CommandDetails.COMMANDS.REDO
				|| command == CommandDetails.COMMANDS.UNDO || command == CommandDetails.COMMANDS.ALL
				|| command == CommandDetails.COMMANDS.FLOATING || command == CommandDetails.COMMANDS.EVENTS
				|| command == CommandDetails.COMMANDS.DEADLINES || command == CommandDetails.COMMANDS.EXIT
				|| command == CommandDetails.COMMANDS.RECURRING) {
			if (description != null || start != null || end != null || ID != -10) {
				throw new invalidParameters(input);
			}
		}

		if (command == CommandDetails.COMMANDS.DELETE || command == CommandDetails.COMMANDS.COMPLETE
				|| command == CommandDetails.COMMANDS.INCOMPLETE) {
			if (description != null || start != null || end != null) {
				throw new invalidParameters(input);
			}
		}

		if (command == CommandDetails.COMMANDS.ADD || command == CommandDetails.COMMANDS.UPDATE) {
			if (description == null) {
				throw new invalidParameters(input);
			}
		}

		if (command == CommandDetails.COMMANDS.SET) {
			if (description == null || start != null || end != null) {
				throw new invalidParameters(input);
			}
		}

		if (command == CommandDetails.COMMANDS.SEARCH) {
			if (description == null && (start == null && end == null)) {
				throw new invalidParameters(input);
			}
		}

		if (end != null && start != null) {
			if (end.before(start)) {
				throw new invalidTimeDateInput("End Date before Start Date");
			}
		}

		if (start != null) {
			if (start.before(todayDate)) {
				throw new invalidTimeDateInput("Start Date have past");
			}
		}

		if (recurring != null || endingDate != null) {
			if (endingDate != null && recurring == null) {
				throw new invalidParameters("Recurring interval");
			}

			if (endingDate.before(end)) {
				throw new invalidTimeDateInput("Recurring end Date before end Date");
			}

			if (start != null) {
				if (endingDate.before(start)) {
					throw new invalidTimeDateInput("Recurring end Date before Start Date");
				}
			}
		}

		if (end != null) {
			if (end.before(todayDate)) {
				throw new invalidTimeDateInput("event already ended");
			}
		}

	}

	private static void validateInput(ArrayList<String> strTokens) throws ParseException {

		int count = 0;
		for (String temp : strTokens) {
			if (temp.equalsIgnoreCase("FROM") || temp.equalsIgnoreCase("AT")) {
				count++;
			}
		}
		if (count > 1) {
			throw new ParseException("multiple start date", 0);
		}
		count = 0;

		for (String temp : strTokens) {
			if (temp.equalsIgnoreCase("BY") || temp.equalsIgnoreCase("TO")) {
				count++;
			}
		}
		if (count > 1) {
			throw new ParseException("multiple end date", 0);
		}

		count = 0;

		for (String temp : strTokens) {
			if (temp.equalsIgnoreCase("DAILY") || temp.equalsIgnoreCase("MONTHLY") || temp.equalsIgnoreCase("YEARLY")
					|| temp.equalsIgnoreCase("WEEKLY")) {
				count++;
			}
		}
		if (count > 1) {
			throw new ParseException("multiple end date", 0);
		}

	}
}

class invalidTimeDateInput extends Exception {
	// Parameterless Constructor
	public invalidTimeDateInput() {
	}

	// Constructor that accepts a message
	public invalidTimeDateInput(String message) {
		super(message);
	}
}

class invalidCommand extends Exception {

	public invalidCommand() {
	}

	public invalidCommand(String message) {
		super(message);
	}

}

class invalidParameters extends Exception {

	public invalidParameters() {
	}

	public invalidParameters(String message) {
		super(message);
	}

}