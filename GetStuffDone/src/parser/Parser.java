package parser;

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
import commandDetail.CommandDetails;

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

			"hhmma", "hha", "hmma", "hh.mma", "hh:mma", "HH.mm", "HH:mm", "HHmm"

	};

	private final static String[] NOT_TIME_ONLY_FORMAT = { "HHmm dd MMMM yy", "HH.mm dd MMMM yy", "HH:mm dd MMMM yy",
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
			"hh:mma dd-MM", "dd-MM", };

	private final static String[] DATE_ONLY_FORMAT = { "dd/MM", "dd-MM", "dd-MMMM", "dd-MM-yy", "dd-MMMM-yy", "dd/MMMM",
			"dd/MM/yy", "dd/MMMM/yy", "dd MM", "dd MMMM", "dd MM yy", "dd MMMM yy" };

	private final static String[] TIME_ONLY_FORMAT = { "hhmma", "hha", "hmma", "hh.mma", "hh:mma", "HH.mm", "HH:mm",
			"HHmm" };

	private final static String[] TimekeyWord = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
			"SUNDAY", "TODAY", "TOMORROW" };

	private final static String[] RECURRING_KEY_WORD = { "DAILY", "MONTHLY", "YEARLY", "WEEKLY" };

	private final static String[] keyWord = { "BY", "FROM", "TO", "AT", "ON", "DAILY", "MONTHLY", "YEARLY", "WEEKLY",
			"ENDING" };

	private final static int NO_NEXT_KEYWORD = -2;
	private final static int NO_ID = -10;
	private final static int NO_KEYWORD = -1;
	private final static int NO_YEAR_INPUT = 1970;

	/*************************************************************************************************
	 ************************************* GENERAL USED METHODS **************************************
	 *************************************************************************************************/

	/**
	 * Returns the index of the keyword or -1 if not found
	 */
	private static int indexOfkeyWord(ArrayList<String> input, String keyword) {
		int arrayListIndex = 0;
		int indexOfKeyWord = -1;
		for (String tokens : input) {
			if (tokens.equalsIgnoreCase(keyword)) {
				indexOfKeyWord = arrayListIndex;
			}
			arrayListIndex++;
		}
		return indexOfKeyWord;
	}

	/**
	 * Returns true if arrayList contains keyword
	 */
	private static boolean containsKeyword(int indexOfKeyWord) {
		return indexOfKeyWord != NO_KEYWORD;
	}

	/**
	 * Returns index of next keyword
	 */
	private static int findNextKeyword(ArrayList<String> input, int indexOfKeyWord) {
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		for (int i = indexOfKeyWord + 1; i < input.size(); i++) {
			if (isKeyWord(input.get(i))) {
				indexOfNextKeyWord = i;
				break;
			}
		}
		return indexOfNextKeyWord;
	}

	/**
	 * Return true if string is a keyword
	 */
	private static boolean isKeyWord(String input) {
		for (int i = 0; i < keyWord.length; i++) {
			if (keyWord[i].equalsIgnoreCase(input)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Takes in 2 index of a arrayList Remove the Strings in between the index
	 * and append them as a string Returns the result String
	 */
	private static String getInputBetweenArrayList(ArrayList<String> input, int indexOfKeyWord, int indexOfNextKeyWord)
			throws InvalidParametersException {
		String result = "";
		for (int i = 0; i < indexOfNextKeyWord - indexOfKeyWord - 1; i++) {
			result = result + " " + input.remove(indexOfKeyWord + 1);
		}
		try {
			result = result.substring(1);
		} catch (StringIndexOutOfBoundsException e) {
			throw new InvalidParametersException();
		}
		input.remove(indexOfKeyWord);
		return result;
	}

	/**
	 * If Index of next Keyword is NO_NEXT_KEYWORD, returns the last index of
	 * arrayList
	 */
	private static int checkLastIndex(ArrayList<String> input, int indexOfNextKeyWord) {
		if (indexOfNextKeyWord == NO_NEXT_KEYWORD) {
			indexOfNextKeyWord = input.size();
		}
		return indexOfNextKeyWord;
	}

	/*************************************************************************************************
	 ************************************* TIME AND DATE PARSING *************************************
	 *************************************************************************************************/

	/**
	 * Parse user input determines the start date Throws invalidCommandException
	 * when command is invalid Throws InvalidParametersException when command
	 * details parameters are missing Throws InvalidTimeDateInputException when
	 * time/date format is not supported
	 */
	private static Date parseStartDate(ArrayList<String> input)
			throws ParseException, InvalidTimeDateInputException, InvalidParametersException {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int indexOfKeyWordFROM = indexOfkeyWord(input, "FROM");
		int indexOfKeyWordAT = indexOfkeyWord(input, "AT");

		if (!containsKeyword(indexOfKeyWordFROM) && !containsKeyword(indexOfKeyWordAT)) {
			return null;
		} else if (containsKeyword(indexOfKeyWordAT)) {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordAT);
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordFROM);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		// Start date is FROM
		if (containsKeyword(indexOfKeyWordFROM)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordFROM, indexOfNextKeyWord);
		}
		// end date is AT
		if (containsKeyword(indexOfKeyWordAT)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordAT, indexOfNextKeyWord);
		}
		// System.out.println(result);
		return createDate(result, "START");
	}

	/**
	 * Parse user input determines the end date Throws invalidCommandException
	 * when command is invalid Throws InvalidParametersException when command
	 * details parameters are missing Throws InvalidTimeDateInputException when
	 * time/date format is not supported
	 */
	private static Date parseEndDate(ArrayList<String> input)
			throws ParseException, InvalidTimeDateInputException, InvalidParametersException {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int indexOfKeyWordTO = indexOfkeyWord(input, "TO");
		int indexOfKeyWordBY = indexOfkeyWord(input, "BY");
		int indexOfKeyWordON = indexOfkeyWord(input, "ON");

		if (!containsKeyword(indexOfKeyWordTO) && !containsKeyword(indexOfKeyWordBY)
				&& !containsKeyword(indexOfKeyWordON)) {
			return null;
		} else if (containsKeyword(indexOfKeyWordBY)) {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordBY);
		} else if (containsKeyword(indexOfKeyWordON)) {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordON);
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordTO);
		}

		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		// end date is TO
		if (containsKeyword(indexOfKeyWordTO)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		// end date is BY
		if (containsKeyword(indexOfKeyWordBY)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordBY, indexOfNextKeyWord);
		}
		// end date is ON
		if (containsKeyword(indexOfKeyWordON)) {
			result = getInputBetweenArrayList(input, indexOfKeyWordON, indexOfNextKeyWord);
		}
		return createDate(result, "END");
	}

	/**
	 * Creates the Date object with the parsed date string Throws ParseException
	 * when time/date format is not found Throws InvalidTimeDateInputException
	 * when time/date format is not supported if method is set to START, default
	 * time is set as 0000 if method is set to END, default time is set as 2359
	 */
	private static Date createDate(String input, String method) throws InvalidTimeDateInputException, ParseException {
		Date myDate = null;
		Calendar cal = Calendar.getInstance();

		for (String formatParsed : DATE_FORMAT) {
			try {
				SimpleDateFormat possibleFormats = new SimpleDateFormat(formatParsed);
				possibleFormats.setLenient(false);
				myDate = possibleFormats.parse(input);
				cal.setTime(myDate);

				checkKeywords(input, formatParsed);

				for (String days : TimekeyWord) {
					if (input.toUpperCase().contains(days)) {
						myDate = addDefaultDate(input, myDate, cal);
						break;
					}
				}

				if (method.equals("END")) {
					addDefaultTime(cal, formatParsed);
				}

				for (String time : TIME_ONLY_FORMAT) {
					addDefaultDay(cal, formatParsed, time);
				}

				if (cal.get(Calendar.YEAR) == NO_YEAR_INPUT) {
					myDate = addDefaultYear(cal);
				}

				return myDate;
			} catch (ParseException e) {
				// Does not match format, proceed to compare next format
			}
		}

		String endTimeOfDay = null;

		for (int i = 0; i < TimekeyWord.length; i++) {
			if (input.toUpperCase().equals(TimekeyWord[i])) {
				SimpleDateFormat format = new SimpleDateFormat("HHmm");
				try {
					if (method.equals("START")) {
						endTimeOfDay = "0000";
					} else {
						endTimeOfDay = "2359";
					}
					myDate = format.parse(endTimeOfDay);
					myDate = addDefaultDate(input, myDate, cal);
					return myDate;
				} catch (ParseException e) {
					// self set time format, ignore
				}
			}
		}
		throw new ParseException(input, 0);
	}

	/**
	 * Checks if input contains multiple Time keywords and throw
	 * InvalidTimeDateInputException
	 */
	private static void checkKeywords(String input, String formatParsed) throws InvalidTimeDateInputException {
		int count = 0;
		for (int i = 0; i < TimekeyWord.length; i++) {
			if (input.toUpperCase().contains(TimekeyWord[i])) {
				count++;
			}
			if (count > 1) {
				throw new InvalidTimeDateInputException(input);
			}
		}

		for (String format : NOT_TIME_ONLY_FORMAT) {
			for (int i = 0; i < TimekeyWord.length; i++) {
				if (input.toUpperCase().contains(TimekeyWord[i]) && formatParsed.equals(format)) {
					throw new InvalidTimeDateInputException(input);
				}
			}
		}

	}

	/**
	 * Returns a calendar object with year set as current year if year is not
	 * specified
	 */
	private static Date addDefaultYear(Calendar cal) {
		Date myDate;
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		myDate = cal.getTime();
		return myDate;
	}

	/**
	 * Returns a calendar object with date set as today if date is not specified
	 */
	private static Date addDefaultDate(String input, Date myDate, Calendar cal) {
		cal.setTime(myDate);
		cal.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		keyWords(input, cal);
		myDate = cal.getTime();
		return myDate;
	}

	/**
	 * Returns a calendar object with day set as current day if day is not
	 * stated
	 */
	private static void addDefaultDay(Calendar cal, String formatParsed, String time) {
		if (formatParsed.equals(time)) {
			cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		}
	}

	/**
	 * Returns a calendar object with time set as 2359 if method is end and time
	 * is not stated
	 */
	private static void addDefaultTime(Calendar cal, String formatParsed) {
		for (String date : DATE_ONLY_FORMAT) {
			if (formatParsed.equals(date)) {
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
			}
		}
	}

	/**
	 * Add day accordingly to keywords stated in input
	 */
	private static void keyWords(String input, Calendar cal) {
		Calendar now = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		int NOW_HOUR_OF_DAY = now.get(Calendar.HOUR_OF_DAY);
		int NOW_MINUTE = now.get(Calendar.MINUTE);

		int i;
		for (i = 0; i < TimekeyWord.length; i++) {
			if (input.toUpperCase().contains(TimekeyWord[i])) {
				break;
			}
		}

		switch (TimekeyWord[i]) {
		case "TOMORROW":
			cal.add(Calendar.DAY_OF_YEAR, 1);
			break;
		case "MONDAY":
			if (weekday != Calendar.MONDAY) {
				int days = (Calendar.MONDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		case "TUESDAY":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.TUESDAY) {
				int days = (Calendar.TUESDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		case "WEDNESDAY":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.WEDNESDAY) {
				int days = (Calendar.WEDNESDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		case "THURSDAY":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.THURSDAY) {
				int days = (Calendar.THURSDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		case "FRIDAY":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.FRIDAY) {
				int days = (Calendar.FRIDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		case "SATURDAY":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SATURDAY) {
				int days = (Calendar.SATURDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		case "SUNDAY":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SUNDAY) {
				int days = (Calendar.SUNDAY - weekday) % 7;
				incrementDay(cal, days);
			} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
				cal.add(Calendar.DAY_OF_YEAR, 7);
			}
			break;
		}

	}

	/**
	 * Increment day to the following week
	 */
	private static void incrementDay(Calendar cal, int days) {
		if (days < 0) {
			days = days + 7;
		}
		cal.add(Calendar.DAY_OF_YEAR, days);
	}

	/*************************************************************************************************
	 ************************************* COMMAND PARSING *******************************************
	 *************************************************************************************************/

	/**
	 * Parse Type of command throws invalidCommandException when command is not
	 * supported returns as CommandDetails.COMMANDS returns as an ADD command
	 * type if not stated
	 * 
	 * @throws InvalidParametersException
	 */
	private static CommandDetails.COMMANDS parseCommandType(ArrayList<String> input)
			throws InvalidCommandException, InvalidParametersException {

		try {
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
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidParametersException();

		}
	}

	/*************************************************************************************************
	 ************************************* TASK ID PARSING *******************************************
	 *************************************************************************************************/

	/**
	 * Parse Task ID throws InvalidParametersException when ID is missing
	 * returns as integer
	 */
	private static int parseID(ArrayList<String> input) throws InvalidParametersException {
		int ID = -10;
		try {
			ID = Integer.parseInt(input.remove(0));
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidParametersException();
		}
		return ID;
	}

	/*************************************************************************************************
	 ************************************* RECURRING PARSING *****************************************
	 *************************************************************************************************/

	/**
	 * Parse Recurring task interval returns as a String
	 */
	private static String parseRecurring(ArrayList<String> strTokens) {
		int arrayListIndex = 0;
		for (String tokens : strTokens) {
			for (int i = 0; i < RECURRING_KEY_WORD.length; i++) {
				if (tokens.equalsIgnoreCase(RECURRING_KEY_WORD[i])) {
					return strTokens.remove(arrayListIndex).toUpperCase();
				}
			}
			arrayListIndex++;
		}
		return null;
	}

	/**
	 * Parse Recurring task ending date Throws ParseException when time/date
	 * format is not found Throws InvalidTimeDateInputException when time/date
	 * format is not supported Throws InvalidParametersException when ending
	 * time/date is not stated
	 */
	private static Date parseEndingDate(ArrayList<String> input)
			throws ParseException, InvalidTimeDateInputException, InvalidParametersException {
		String result = "";
		int indexOfNextKeyWord = NO_NEXT_KEYWORD;
		int indexOfKeyWordEnding = indexOfkeyWord(input, "ENDING");
		if (!containsKeyword(indexOfKeyWordEnding)) {
			return null;
		} else {
			indexOfNextKeyWord = findNextKeyword(input, indexOfKeyWordEnding);
		}
		indexOfNextKeyWord = checkLastIndex(input, indexOfNextKeyWord);
		result = getInputBetweenArrayList(input, indexOfKeyWordEnding, indexOfNextKeyWord);
		return createDate(result, "END");
	}

	/*************************************************************************************************
	 ************************************* DESCRIPTION PARSING ***************************************
	 *************************************************************************************************/

	/**
	 * Parse task description and remove escape character "/" Returns a String
	 */
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

	/*************************************************************************************************
	 ************************************* USER INPUT PARSING ****************************************
	 *************************************************************************************************/

	/**
	 * Parse user input from control and validates the input before returning a
	 * Command Detail object to control with each parameter parsed Throws
	 * invalidCommandException when command is invalid Throws
	 * InvalidParametersException when command details parameters are missing
	 * Throws InvalidTimeDateInputException when time/date format is not
	 * supported
	 */
	public static CommandDetails parse(String input)
			throws ParseException, InvalidCommandException, InvalidParametersException, InvalidTimeDateInputException {
		String description;
		String recurring = null;
		Date start;
		Date end;
		Date originalStart;
		Date originalEnd;
		Date endingDate = null;
		int ID = NO_ID;
		boolean copy = false;

		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		validateInput(strTokens);

		CommandDetails.COMMANDS command = parseCommandType(strTokens);
		if (requiresTaskID(command)) {
			ID = parseID(strTokens);
		}
		end = parseEndDate(strTokens);
		copy = isEventTaskofAT(copy, strTokens);
		start = parseStartDate(strTokens);
		if (copy) {
			end = start;
		}
		recurring = parseRecurring(strTokens);
		endingDate = parseEndingDate(strTokens);
		endingDate = defaultEndingDate(recurring, endingDate);
		description = parseDescription(strTokens);
		originalStart = start;
		originalEnd = end;
		validateCommandDetails(command, ID, description, start, end, input, recurring, originalStart, originalEnd,
				endingDate);
		return new CommandDetails(command, description, start, end, ID, recurring, originalStart, originalEnd,
				endingDate);
	}

	/**
	 * Set Recurring ending date to last possible date in java.util.Date if not
	 * stated
	 */
	private static Date defaultEndingDate(String recurring, Date endingDate) throws ParseException {
		if (endingDate == null && recurring != null) {
			SimpleDateFormat format = new SimpleDateFormat("HHmm dd/MM/yyyy");
			endingDate = format.parse("2359 31/12/8089");
		}
		return endingDate;
	}

	/**
	 * Return True if keyword is AT
	 */
	private static boolean isEventTaskofAT(boolean copy, ArrayList<String> strTokens) {
		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("AT")) {
				copy = true;
			}
		}
		return copy;
	}

	/**
	 * Return True Command Type requires a Task ID
	 */
	private static boolean requiresTaskID(CommandDetails.COMMANDS command) {
		return command == CommandDetails.COMMANDS.DELETE || command == CommandDetails.COMMANDS.COMPLETE
				|| command == CommandDetails.COMMANDS.INCOMPLETE || command == CommandDetails.COMMANDS.UPDATE;
	}

	/*************************************************************************************************
	 ************************************* VALIDATIONS **********************************************
	 *************************************************************************************************/

	/**
	 * Validates Command Details parameters before creating Command Details and
	 * return to Control
	 */
	private static void validateCommandDetails(CommandDetails.COMMANDS command, int ID, String description, Date start,
			Date end, String input, String recurring, Date originalStart, Date originalEnd, Date endingDate)
					throws InvalidParametersException, InvalidTimeDateInputException {
		validateCommand(command, ID, description, start, end, input);
		validateDateTime(start, end, recurring, endingDate);
		if (recurring != null || endingDate != null) {
			validateRecurringDate(start, end, recurring, endingDate);
		}
	}

	/**
	 * Validates if start and end time/date are correct start date is before end
	 * date start date have not yet past end date have not yet past
	 * 
	 * Throws InvalidTimeDateInputException
	 */
	private static void validateDateTime(Date start, Date end, String recurring, Date endingDate)
			throws InvalidTimeDateInputException {
		Calendar today = Calendar.getInstance();
		Date todayDate = today.getTime();

		if (end != null && start != null) {
			if (end.before(start)) {
				throw new InvalidTimeDateInputException("End Date before Start Date");
			}
		}

		if (end != null) {
			if (end.before(todayDate)) {
				throw new InvalidTimeDateInputException("event already ended");
			}
		}
	}

	/**
	 * Validates if recurring task date/time input are correct Recurring task
	 * have a recurring interval stated Task recurring end date is after task
	 * start date Task recurring end date is after task end date
	 */
	private static void validateRecurringDate(Date start, Date end, String recurring, Date endingDate)
			throws InvalidParametersException, InvalidTimeDateInputException {

		if (endingDate != null && recurring == null) {
			throw new InvalidParametersException("Recurring interval");
		}
		if (endingDate.before(end)) {
			throw new InvalidTimeDateInputException("Recurring end Date before end Date");
		}
		if (start != null) {
			if (endingDate.before(start)) {
				throw new InvalidTimeDateInputException("Recurring end Date before Start Date");
			}
		}
		if (end == null) {
			throw new InvalidParametersException("Date missing");
		}
	}

	/**
	 * Validates if parameters are correct given its corresponding Command Type
	 */
	private static void validateCommand(CommandDetails.COMMANDS command, int ID, String description, Date start,
			Date end, String input) throws InvalidParametersException {
		String checkDescription = description;
		try {
			checkDescription = checkDescription.replaceAll(" ", "");
		} catch (NullPointerException e) {
		}

		if (command == CommandDetails.COMMANDS.HELP || command == CommandDetails.COMMANDS.REDO
				|| command == CommandDetails.COMMANDS.UNDO || command == CommandDetails.COMMANDS.ALL
				|| command == CommandDetails.COMMANDS.FLOATING || command == CommandDetails.COMMANDS.EVENTS
				|| command == CommandDetails.COMMANDS.DEADLINES || command == CommandDetails.COMMANDS.EXIT
				|| command == CommandDetails.COMMANDS.RECURRING) {
			if (description != null || start != null || end != null || ID != -10) {
				throw new InvalidParametersException(input);
			}
		}
		if (command == CommandDetails.COMMANDS.DELETE || command == CommandDetails.COMMANDS.COMPLETE
				|| command == CommandDetails.COMMANDS.INCOMPLETE) {
			if (description != null || start != null || end != null) {
				throw new InvalidParametersException(input);
			}
		}
		if (command == CommandDetails.COMMANDS.ADD || command == CommandDetails.COMMANDS.UPDATE) {
			if (description == null) {
				throw new InvalidParametersException(input);
			}

			if (checkDescription.equals("")) {
				throw new InvalidParametersException(input);
			}
		}
		if (command == CommandDetails.COMMANDS.SET) {
			if (description == null || start != null || end != null) {
				throw new InvalidParametersException(input);
			}
		}
		if (command == CommandDetails.COMMANDS.SEARCH) {
			if (description == null && (start == null && end == null)) {
				throw new InvalidParametersException(input);
			}
			if (checkDescription != null) {
				if (checkDescription.equals("") && (start == null && end == null)) {
					throw new InvalidParametersException(input);
				}
			}
		}
	}

	/** Validates the user string input from control */
	private static void validateInput(ArrayList<String> strTokens) throws ParseException {
		validateStartDate(strTokens);
		validateEndDate(strTokens);
		validateRecurring(strTokens);

	}

	/** Validates if there are multiple start time and throws ParseException */
	private static void validateStartDate(ArrayList<String> strTokens) throws ParseException {
		int count;
		count = 0;
		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("FROM") || tokens.equalsIgnoreCase("AT")) {
				count++;
			}
		}
		if (count > 1) {
			throw new ParseException("multiple start date", 0);
		}
	}

	/**
	 * Validates if there are multiple end time/date and throws ParseException
	 */
	private static void validateEndDate(ArrayList<String> strTokens) throws ParseException {
		int count;
		count = 0;

		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("BY") || tokens.equalsIgnoreCase("TO")) {
				count++;
			}
		}
		if (count > 1) {
			throw new ParseException("multiple end date", 0);
		}
	}

	/**
	 * Validates if there are multiple recurring time/date and
	 * throwsParseException
	 */
	private static void validateRecurring(ArrayList<String> strTokens) throws ParseException {
		int count = 0;
		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("DAILY") || tokens.equalsIgnoreCase("MONTHLY")
					|| tokens.equalsIgnoreCase("YEARLY") || tokens.equalsIgnoreCase("WEEKLY")) {
				count++;
			}
		}
		if (count > 1) {
			throw new ParseException("multiple end date", 0);
		}
	}
}