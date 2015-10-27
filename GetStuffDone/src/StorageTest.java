import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {

	private static final String PATH_CURRENT = System.getProperty("user.dir") + File.separatorChar;
	private static final String FILENAME = "saveFile.txt";

	private Storage storage = new Storage();
	private ArrayList<Task> tasks = null;

	private Task taskFloating = null;
	private Task taskEvent = null;
	private Task taskDeadline = null;
	private Task taskRecurring = null;

	Date date = new Date();

	@Before
	public void setUp() {

		storage.setFilePath(PATH_CURRENT + FILENAME);

		tasks = new ArrayList<>();

		// Sample Tasks
		taskFloating = new Task();
		taskFloating.setDescription("Floating Task");
		taskFloating.setIsComplete(false);

		taskEvent = new Task();
		taskEvent.setDescription("Event Task");
		taskEvent.setStartDate(new Date());
		taskEvent.setDeadline(new Date());
		taskEvent.setIsComplete(true);

		taskDeadline = new Task();
		taskDeadline.setDescription("Deadline Task");
		taskDeadline.setDeadline(new Date());
		taskDeadline.setIsComplete(false);
		
		taskRecurring = new Task();
		taskRecurring.setDescription("Recurring Task");
		taskRecurring.setEndingDate(date);
		taskRecurring.setRecurring("RECURRING");
	}

	@Test
	public void saveNull() throws IOException, ParseException {

		storage.save(null);

		assertFalse((new File(PATH_CURRENT + FILENAME)).exists());
	}

	@Test
	public void saveEmptyArrayList() throws ParseException, IOException {

		storage.save(new ArrayList<Task>());
		tasks = storage.load();

		assertEquals(tasks.size(), 0);
	}

	@Test
	public void saveNullAttributes() throws IOException, ParseException {

		tasks.add(new Task());

		storage.save(tasks);
		tasks = storage.load();

		Task task = tasks.remove(0);
		assertSameTask(task, new Task());
	}

	@Test
	public void saveThreeTasks() throws IOException, ParseException {

		tasks.add(taskFloating);
		tasks.add(taskEvent);
		tasks.add(taskDeadline);

		storage.save(tasks);
		tasks = storage.load();

		assertSameTask(taskFloating, tasks.get(0));
		assertSameTask(taskEvent, tasks.get(1));
		assertSameTask(taskDeadline, tasks.get(2));
	}

	@Test
	public void setPathWithNull() {
		assertFalse(storage.setFilePath(null));
	}

	@Test
	public void setPathWithEmptyString() {
		assertFalse(storage.setFilePath(""));
	}

	@Test
	public void setPathWithIllegalCharacters() {

		char[] illegalCharacters = new char[] { '\n', '\t', '?', '*', '<', '\\', '<', '>', '|', '\"', ':' };

		for (char c : illegalCharacters) {
			assertFalse(storage.setFilePath(PATH_CURRENT + "save" + c + "file.txt"));
		}
	}

	@Test
	public void setPathWithoutFilename() {
		assertFalse(storage.setFilePath(PATH_CURRENT));
	}

	@Test
	public void setPathWithOnlyFilename() {
		assertFalse(storage.setFilePath(FILENAME));
	}

	@After
	public void tearDown() {
		File file = new File(PATH_CURRENT + FILENAME);
		file.delete();
	}

	private static void assertSameTask(Task task1, Task task2) {

		assertTrue(isSame(task1.getDescription(), task2.getDescription()));
		assertTrue(isSame(task1.getStartDate(), task2.getStartDate()));
		assertTrue(isSame(task1.getDeadline(), task2.getDeadline()));
		assertTrue(isSame(task1.getEndingDate(), task2.getEndingDate()));
		assertTrue(isSame(task1.getRecurring(), task2.getRecurring()));
		assertEquals(task1.isComplete(), task2.isComplete());
	}

	private static boolean isSame(String string1, String string2) {

		if (string1 == null && string2 == null) {
			return true;
		}

		if (string1 != null && string1.equals(string2)) {
			return true;
		}

		return false;
	}

	private static boolean isSame(Date date1, Date date2) {

		if (date1 == null && date2 == null) {
			return true;
		}

		if (date1 != null && date1.toString().equals(date2.toString())) {
			return true;
		}

		return false;
	}
}
