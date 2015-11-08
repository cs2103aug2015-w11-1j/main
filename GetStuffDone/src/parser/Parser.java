package parser;

//@@author A0124472L

/*
 	All keywords are case insensitive
	<----------------------------------------------------------------------->
	Command keywords demarked by
	Adding of task(default command)				add			-a
	Deleting of task							delete		-d
	Marking task as complete					complete	done
	Marking task as incomplete					incomplete	undone
	Displaying help manual						help
	Redo last action							redo
	Search task									search		find		-s
	Undo last action							undo
	Editing an existing task					update		update		-u
	Displaying of task							all
	Displaying floating task					floatings	floating	float
	Displaying deadline task					deadlines	deadline	due
	Displaying event task						events		event
	Set file path								set
	
	<----------------------------------------------------------------------->
	Event task 				<From> <To> 
							<At>
	
	Floating task			No time specified
	
	Deadline task			<By>/<On>
	
	Date and time format  	<time><date>
	
	
	If description contains time keyword, use '/' as escape character
	
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
	
	Supported date keywords	
	
				- today			today		tdy
				- tomorrow		tomorrow	tmr	
				- monday		monday		mon
				- tuesday		tuesday		tue
				- wednesday		wednesday	wed
				- thursday		thursday	thur
				- friday		friday		fri
				- saturday		saturday	sat
				- sunday		sunday		sun
				
				
		<Command> <Description> <TaskID (if applicable)> <Time task>
	eg. -U 2 dinner /at gardens /by the bay FROM 12.30pm 6/10/15 TO 6pm 6/10/15 
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

	private final static String[] TIME_KEYWORDS = { "MONDAY", "MON", "TUESDAY", "TUE", "WEDNESDAY", "WED", "THURSDAY",
			"THUR", "FRIDAY", "FRI", "SATURDAY", "SAT", "SUNDAY", "SUN", "TODAY", "TDY", "TOMORROW", "TMR" };

	private final static String[] KEYWORD = { "BY", "FROM", "TO", "AT", "ON" };
	private final static int DAYS_IN_A_WEEK = 7;
	private final static int NO_NEXT_KEYWORD = -2;
	private final static int NO_ID = -10;
	private final static int NO_KEYWORD = -1;
	private final static int NO_YEAR_INPUT = 1970;
	private final static int FIRST_IN_ARRAYLIST = 0;

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
		for (int i = 0; i < KEYWORD.length; i++) {
			if (KEYWORD[i].equalsIgnoreCase(input)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Takes in 2 index of a arrayList Remove the Strings in between the index
	 * and append them as a string Returns the result String
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
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

	/**
	 * Returns a string with consecutive white spaces removed
	 */
	private static String formatString(String input) {
		try {
			input = input.trim().replaceAll(" +", " ");
		} catch (NullPointerException e) {
			// No parameters
		}
		return input;
	}

	/*************************************************************************************************
	 ************************************* TIME AND DATE PARSING *************************************
	 *************************************************************************************************/

	/**
	 * Parse user input determines the start date
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */
	private static Date parseStartDate(ArrayList<String> input)
			throws InvalidParametersException, InvalidTimeDateInputException {
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
	 * Parse user input determines the end date
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 * 
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */

	private static Date parseEndDate(ArrayList<String> input)
			throws InvalidTimeDateInputException, InvalidParametersException {
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
	 * Creates the Date object with the parsed date string if method is set to
	 * START, default time is set as 0000 if method is set to END, default time
	 * is set as 2359
	 * 
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */
	private static Date createDate(String input, String method) throws InvalidTimeDateInputException {
		Date myDate = null;
		Calendar cal = Calendar.getInstance();

		for (String formatParsed : DATE_FORMAT) {
			try {
				SimpleDateFormat possibleFormats = new SimpleDateFormat(formatParsed);
				possibleFormats.setLenient(false);
				myDate = possibleFormats.parse(input);
				cal.setTime(myDate);

				checkKeywords(input, formatParsed);
				checkInput(input, formatParsed);
				for (String days : TIME_KEYWORDS) {
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

		String defaultTime = null;

		for (int i = 0; i < TIME_KEYWORDS.length; i++) {
			if (input.toUpperCase().equals(TIME_KEYWORDS[i])) {
				SimpleDateFormat format = new SimpleDateFormat("HHmm");
				try {
					if (method.equals("START")) {
						defaultTime = "0000";
					} else {
						defaultTime = "2359";
					}
					myDate = format.parse(defaultTime);
					myDate = addDefaultDate(input, myDate, cal);
					return myDate;
				} catch (ParseException e) {
					// self set time format, ignore
				}
			}
		}
		throw new InvalidTimeDateInputException(input);
	}

	/**
	 * Checks if input have extra parameters eg. time is specific after date
	 * 
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */
	private static void checkInput(String input, String formatParsed) throws InvalidTimeDateInputException {
		int numOfTokInput = new ArrayList<String>(Arrays.asList(input.split(" "))).size();
		int numOfTokformat;

		for (String format : DATE_ONLY_FORMAT) {
			if (format.equals(formatParsed)) {
				numOfTokformat = new ArrayList<String>(Arrays.asList(formatParsed.split(" "))).size();
				if (numOfTokInput != numOfTokformat) {
					throw new InvalidTimeDateInputException(input);
				}
			}
		}
	}

	/**
	 * Checks if input contains multiple Time keywords
	 * 
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */
	private static void checkKeywords(String input, String formatParsed) throws InvalidTimeDateInputException {
		int count = 0;
		for (int i = 0; i < TIME_KEYWORDS.length; i++) {
			if (input.toUpperCase().equals(TIME_KEYWORDS[i])) {
				count++;
			}
			if (count > 1) {
				throw new InvalidTimeDateInputException(input);
			}
		}

		for (String format : NOT_TIME_ONLY_FORMAT) {
			for (int i = 0; i < TIME_KEYWORDS.length; i++) {
				if (input.toUpperCase().contains(TIME_KEYWORDS[i]) && formatParsed.equals(format)) {
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
				int lastHourInDay = 23;
				int lastMinuteInDay = 59;
				cal.set(Calendar.HOUR_OF_DAY, lastHourInDay);
				cal.set(Calendar.MINUTE, lastMinuteInDay);
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
		boolean isBeforeCurrentTime = checkBeforeCurrentTIme(HOUR_OF_DAY, MINUTE, NOW_HOUR_OF_DAY, NOW_MINUTE);

		int i;
		for (i = 0; i < TIME_KEYWORDS.length; i++) {
			if (input.toUpperCase().contains(TIME_KEYWORDS[i])) {
				break;
			}
		}

		switch (TIME_KEYWORDS[i]) {
		case "TOMORROW":
		case "TMR":
			cal.add(Calendar.DAY_OF_YEAR, 1);
			break;
		case "MONDAY":
		case "MON":
			if (weekday != Calendar.MONDAY) {
				int days = (Calendar.MONDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		case "TUESDAY":
		case "TUE":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.TUESDAY) {
				int days = (Calendar.TUESDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		case "WEDNESDAY":
		case "WED":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.WEDNESDAY) {
				int days = (Calendar.WEDNESDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		case "THURSDAY":
		case "THUR":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.THURSDAY) {
				int days = (Calendar.THURSDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		case "FRIDAY":
		case "FRI":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.FRIDAY) {
				int days = (Calendar.FRIDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		case "SATURDAY":
		case "SAT":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SATURDAY) {
				int days = (Calendar.SATURDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		case "SUNDAY":
		case "SUN":
			weekday = cal.get(Calendar.DAY_OF_WEEK);
			if (weekday != Calendar.SUNDAY) {
				int days = (Calendar.SUNDAY - weekday) % DAYS_IN_A_WEEK;
				incrementDay(cal, days);
			} else if (isBeforeCurrentTime) {
				cal.add(Calendar.DAY_OF_YEAR, DAYS_IN_A_WEEK);
			}
			break;
		}

	}

	/**
	 * Returns true if before current time
	 */
	private static boolean checkBeforeCurrentTIme(int HOUR_OF_DAY, int MINUTE, int NOW_HOUR_OF_DAY, int NOW_MINUTE) {
		return HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE);
	}

	/**
	 * Increment day to the following week
	 */
	private static void incrementDay(Calendar cal, int days) {
		if (days < 0) {
			days = days + DAYS_IN_A_WEEK;
		}
		cal.add(Calendar.DAY_OF_YEAR, days);
	}

	/*************************************************************************************************
	 ************************************* COMMAND PARSING *******************************************
	 *************************************************************************************************/

	/**
	 * Parse Type of command returns as CommandDetails.COMMANDS returns as an
	 * ADD command type if not stated
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 * 
	 */
	private static CommandDetails.COMMANDS parseCommandType(ArrayList<String> input) throws InvalidParametersException {

		try {
			switch (input.get(FIRST_IN_ARRAYLIST).toUpperCase()) {
			case "ADD":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.ADD;
			case "-A":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.ADD;
			case "DELETE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DELETE;
			case "DEL":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DELETE;
			case "REMOVE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DELETE;
			case "-D":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DELETE;
			case "COMPLETE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.COMPLETE;
			case "DONE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.COMPLETE;
			case "INCOMPLETE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.INCOMPLETE;
			case "UNDONE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.INCOMPLETE;
			case "HELP":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.HELP;
			case "REDO":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.REDO;
			case "SEARCH":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.SEARCH;
			case "-S":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.SEARCH;
			case "FIND":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.SEARCH;
			case "UNDO":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.UNDO;
			case "ALL":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.ALL;
			case "UPDATE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.UPDATE;
			case "EDIT":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.UPDATE;
			case "-U":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.UPDATE;
			case "SET":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.SET;
			case "FLOATING":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.FLOATING;
			case "FLOAT":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.FLOATING;
			case "EVENTS":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.EVENTS;
			case "EVENT":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.EVENTS;
			case "DEADLINES":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DEADLINES;
			case "DEADLINE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DEADLINES;
			case "DUE":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.DEADLINES;
			case "EXIT":
				input.remove(FIRST_IN_ARRAYLIST);
				return CommandDetails.COMMANDS.EXIT;
			default:
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
	 * 
	 * @throws InvalidParametersException
	 *             When ID is not a integer
	 */
	private static int parseID(ArrayList<String> input) throws InvalidParametersException {
		int ID = NO_ID;

		try {
			ID = Integer.parseInt(input.remove(FIRST_IN_ARRAYLIST));
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidParametersException();
		}
		return ID;
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
		try {
			while (!input.isEmpty()) {
				if (input.get(FIRST_IN_ARRAYLIST).charAt(0) == '/') {
					// Removes escape character
					result = result + " " + input.remove(FIRST_IN_ARRAYLIST).substring(1);
				} else {
					result = result + " " + input.remove(FIRST_IN_ARRAYLIST);
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			// empty description
		}
		result = formatString(result);
		return result;
	}

	/*************************************************************************************************
	 ************************************* USER INPUT PARSING ****************************************
	 *************************************************************************************************/

	/**
	 * Parse user input from control and validates the input before returning a
	 * Command Detail object to control with each parameter parsed
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */
	public static CommandDetails parse(String input) throws InvalidParametersException, InvalidTimeDateInputException {
		String description;
		Date start;
		Date end;
		int ID = NO_ID;
		boolean isKeywordAt = false;

		input = formatString(input);
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		validateInput(strTokens, input);

		CommandDetails.COMMANDS command = parseCommandType(strTokens);
		if (requiresTaskID(command)) {
			ID = parseID(strTokens);
		}
		end = parseEndDate(strTokens);
		isKeywordAt = isEventTaskofAT(strTokens);
		start = parseStartDate(strTokens);
		if (isKeywordAt) {
			// end = start;
			end = setEndDate(start);
		}
		description = parseDescription(strTokens);
		validateCommandDetails(command, ID, description, start, end, input);
		return new CommandDetails(command, description, start, end, ID);
	}

	/**
	 * Set end time as 2359 for AT event task
	 */

	private static Date setEndDate(Date start) {
		Calendar cal = Calendar.getInstance();
		int lastHourInDay = 23;
		int lastMinuteInDay = 59;
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, lastHourInDay);
		cal.set(Calendar.MINUTE, lastMinuteInDay);
		return cal.getTime();
	}

	/**
	 * Return True if keyword is AT
	 */
	private static boolean isEventTaskofAT(ArrayList<String> strTokens) {
		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("AT")) {
				return true;
			}
		}
		return false;
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
			Date end, String input) throws InvalidParametersException, InvalidTimeDateInputException {
		validateCommand(command, ID, description, start, end, input);
		validateDateTime(start, end);
	}

	/**
	 * Validates if start and end time/date are correct start date is before end
	 * date start date have not yet past end date have not yet past
	 * 
	 * @throws InvalidTimeDateInputException
	 *             when time/date format is not supported
	 */
	private static void validateDateTime(Date start, Date end) throws InvalidTimeDateInputException {
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
	 * Validates if parameters are correct given its corresponding Command Type
	 *
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 */
	private static void validateCommand(CommandDetails.COMMANDS command, int ID, String description, Date start,
			Date end, String input) throws InvalidParametersException {
		String checkDescription = description;
		try {
			checkDescription = checkDescription.replaceAll(" ", "");
		} catch (NullPointerException e) {
			// no descriptions
		}

		if (command == CommandDetails.COMMANDS.HELP || command == CommandDetails.COMMANDS.REDO
				|| command == CommandDetails.COMMANDS.UNDO || command == CommandDetails.COMMANDS.ALL
				|| command == CommandDetails.COMMANDS.FLOATING || command == CommandDetails.COMMANDS.EVENTS
				|| command == CommandDetails.COMMANDS.DEADLINES || command == CommandDetails.COMMANDS.EXIT) {
			if (description != null || start != null || end != null || ID != NO_ID) {
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

	/**
	 * Validates the user string input from control
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 */
	private static void validateInput(ArrayList<String> strTokens, String input) throws InvalidParametersException {
		validateDates(input);
		validateStartDate(strTokens);
		validateEndDate(strTokens);

	}

	/**
	 * Validates if there are multiple start time
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 */
	private static void validateStartDate(ArrayList<String> strTokens) throws InvalidParametersException {
		int count;
		count = 0;
		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("FROM") || tokens.equalsIgnoreCase("AT")) {
				count++;
			}
		}
		if (count > 1) {
			throw new InvalidParametersException("multiple start date");
		}
	}

	/**
	 * Validates if there are multiple end time/date
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 */
	private static void validateEndDate(ArrayList<String> strTokens) throws InvalidParametersException {
		int count;
		count = 0;

		for (String tokens : strTokens) {
			if (tokens.equalsIgnoreCase("BY") || tokens.equalsIgnoreCase("TO") || tokens.equalsIgnoreCase("ON")) {
				count++;
			}
		}
		if (count > 1) {
			throw new InvalidParametersException("multiple end date");
		}
	}

	/**
	 * Validates if there are Start and End that does not match
	 * 
	 * @throws InvalidParametersException
	 *             when required parameters are missing
	 */
	private static void validateDates(String input) throws InvalidParametersException {

		input = input.trim().replaceAll(" +", " ").toUpperCase();
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));

		if (strTokens.contains("FROM") && (strTokens.contains("ON") || strTokens.contains("BY"))) {
			throw new InvalidParametersException("FROM not TO");
		}

		if (strTokens.contains("AT")
				&& (strTokens.contains("ON") || strTokens.contains("BY") || strTokens.contains("TO"))) {
			throw new InvalidParametersException("AT matched with end date");
		}

	}

}