package storage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import task.Task;

public class Storage {

	private static final String KEY_PATH = "GetStuffDone.path";

	private static final String DEFAULT_PATH = "GetStuffDone.txt";

	private static final String DATE_FORMAT = "dd MMMM yy HH:mm:ss";

	private static final String STATUS_COMPLETED = "Completed";
	private static final String STATUS_NOT_COMPLETED = "Not completed";

	// Indicate the number of lines required to store a task
	private static final int LINES_PER_TASK = 9;

	// Indicate the relative positions of the attributes of a Task
	private static final int OFFSET_DESCRIPTION = 0;
	private static final int OFFSET_STATUS = 1;
	private static final int OFFSET_DATE_START = 2;
	private static final int OFFSET_DATE_END = 3;
	private static final int OFFSET_DATE_LAST = 4;
	private static final int OFFSET_INTERVAL = 5;
	private static final int OFFSET_DATE_START_ORIGINAL = 6;
	private static final int OFFSET_DATE_END_ORIGINAL = 7;
	private static final int OFFSET_COUNT = 8;

	private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

	private Preferences preferences;

	public Storage() {
		preferences = Preferences.userNodeForPackage(this.getClass());
	}

	/**
	 * Save the ArrayList of Task(s) into a file. If the ArrayList is null, do
	 * nothing. If the ArrayList is empty, create an empty file.
	 * 
	 * @param tasks
	 *            is the ArrayList of Task(s)
	 */
	public void save(ArrayList<Task> tasks) {

		if (tasks == null) {
			return;
		}

		try {

			String path = preferences.get(KEY_PATH, DEFAULT_PATH);

			PrintWriter writer = new PrintWriter(new File(path));

			for (Task task : tasks) {
				writeString(writer, task.getDescription());
				writeStatus(writer, task.isComplete());
				writeDate(writer, task.getStartDate());
				writeDate(writer, task.getDeadline());
				writeDate(writer, task.getEndingDate());
				writeString(writer, task.getRecurring());
				writeDate(writer, task.getOriginalStartDate());
				writeDate(writer, task.getOriginalDeadline());
				writeInt(writer, task.getRecurringCount());
			}

			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse the saved file into an ArrayList of Task(s)
	 * 
	 * @return an ArrayList of Task(s). If the saved file is empty, return an
	 *         empty ArrayList of size 0. If the saved file is incorrect in
	 *         format, return null.
	 * @throws IOException
	 *             if the saved file cannot be read
	 * @throws ParseException
	 *             if the date format is incorrect
	 */
	public ArrayList<Task> load() throws IOException, ParseException {

		String path = preferences.get(KEY_PATH, DEFAULT_PATH);

		File savedFile = new File(path);

		List<String> lines = Files.readAllLines(savedFile.toPath());

		ArrayList<Task> tasks = new ArrayList<Task>();

		if (lines.size() % LINES_PER_TASK != 0) {
			return null;
		}

		for (int i = 0; i < lines.size(); i += LINES_PER_TASK) {

			Task task = new Task();

			task.setDescription(parseString(lines.get(i + OFFSET_DESCRIPTION)));
			task.setIsComplete(parseStatus(lines.get(i + OFFSET_STATUS)));
			task.setStartDate(parseDate(lines.get(i + OFFSET_DATE_START)));
			task.setDeadline(parseDate(lines.get(i + OFFSET_DATE_END)));
			task.setEndingDate(parseDate(lines.get(i + OFFSET_DATE_LAST)));
			task.setRecurring(parseString(lines.get(i + OFFSET_INTERVAL)));
			task.setOriginalStartDate(parseDate(lines.get(i + OFFSET_DATE_START_ORIGINAL)));
			task.setOriginalDeadline(parseDate(lines.get(i + OFFSET_DATE_END_ORIGINAL)));
			task.setRecurringCount(Integer.parseInt(lines.get(i + OFFSET_COUNT)));

			tasks.add(task);
		}

		return tasks;
	}

	/**
	 * Set the path of the save file.
	 * 
	 * @param string
	 *            is the string indicating the complete path
	 * @return true if the path is valid and successfully set, else false
	 */
	public boolean setFilePath(String string) {

		if (string == null || string.isEmpty()) {
			return false;
		}

		int lastIndexOfSeparator = string.lastIndexOf(File.separatorChar);

		if (lastIndexOfSeparator == -1) {
			return false;
		}

		String folderName = string.substring(0, lastIndexOfSeparator + 1);
		String fileName = string.replace(folderName, "");

		if (folderName.isEmpty() || fileName.isEmpty()) {
			return false;
		}

		if (!(new File(folderName)).exists()) {
			return false;
		}

		try {
			Paths.get(string);
		} catch (InvalidPathException e) {
			return false;
		}

		preferences.put(KEY_PATH, string);

		return true;
	}

	private void writeString(PrintWriter writer, String string) {

		if (string == null) {
			writer.println();
		} else {
			writer.println(string);
		}
	}

	private void writeDate(PrintWriter writer, Date date) {

		if (date == null) {
			writer.println();
		} else {
			writer.println(formatter.format(date));
		}
	}

	private void writeStatus(PrintWriter writer, boolean isCompleted) {

		if (isCompleted) {
			writer.println(STATUS_COMPLETED);
		} else {
			writer.println(STATUS_NOT_COMPLETED);
		}
	}
	
	private void writeInt(PrintWriter writer, int num) {
		writer.println(Integer.toString(num));
	}

	private String parseString(String string) {

		if (string.isEmpty()) {
			return null;
		} else {
			return string;
		}
	}

	private Date parseDate(String string) throws ParseException {

		if (string.isEmpty()) {
			return null;
		} else {
			return formatter.parse(string);
		}
	}

	private boolean parseStatus(String string) {

		if (string.equalsIgnoreCase(STATUS_COMPLETED)) {
			return true;
		} else {
			return false;
		}
	}
}