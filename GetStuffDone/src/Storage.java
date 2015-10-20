import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Storage {

	private static final String DEFAULT_FILENAME = "GetStuffDone.sav";

	// Indicate that an attribute of a Task is null
	private static final String NULL_FIELD = "\\";

	private static final String DATE_FORMAT = "dd MMMM yy HH:mm:ss";

	// Indicate the number of lines required to store a task
	private static final int LINES_PER_TASK = 5;

	// Indicate the relative positions of the attributes of a Task
	private static final int OFFSET_DESCRIPTION = 0;
	private static final int OFFSET_DATE_START = 1;
	private static final int OFFSET_DATE_END = 2;

	private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

	/**
	 * Save the ArrayList of Task(s) into a file
	 * 
	 * @param tasks
	 *            is the ArrayList of Task(s)
	 */
	public void save(ArrayList<Task> tasks) {

		if (tasks == null) {
			return;
		}

		try {

			PrintWriter writer = new PrintWriter(new File(DEFAULT_FILENAME));

			for (Task task : tasks) {
				writeString(writer, task.getDescription());
				writeDate(writer, task.getStartDate());
				writeDate(writer, task.getDeadline());
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

		File savedFile = new File(DEFAULT_FILENAME);

		List<String> lines = Files.readAllLines(savedFile.toPath());

		ArrayList<Task> tasks = new ArrayList<Task>();

		if (lines.size() % LINES_PER_TASK != 0) {
			return null;
		}

		for (int i = 0; i < lines.size() / LINES_PER_TASK; i++) {

			Task task = new Task();

			task.setDescription(parseString(lines.get(i * LINES_PER_TASK + OFFSET_DESCRIPTION)));
			task.setStartDate(parseDate(lines.get(i * LINES_PER_TASK + OFFSET_DATE_START)));
			task.setDeadline(parseDate(lines.get(i * LINES_PER_TASK + OFFSET_DATE_END)));
			tasks.add(task);
		}

		return tasks;
	}

	private void writeString(PrintWriter writer, String string) {

		if (string == null) {
			writer.println(NULL_FIELD);
		} else {
			writer.println(string);
		}
	}

	private void writeDate(PrintWriter writer, Date date) {

		if (date == null) {
			writer.println(NULL_FIELD);
		} else {
			writer.println(formatter.format(date));
		}
	}

	private String parseString(String string) {

		if (string.equals(NULL_FIELD)) {
			return null;
		} else {
			return string;
		}
	}

	private Date parseDate(String string) throws ParseException {

		if (string.equals(NULL_FIELD)) {
			return null;
		} else {
			return formatter.parse(string);
		}
	}
}