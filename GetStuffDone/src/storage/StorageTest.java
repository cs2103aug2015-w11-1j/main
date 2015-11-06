package storage;

//@@author A0126561J

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import task.Task;



public class StorageTest {

	private static final String PATH_CURRENT = System.getProperty("user.dir") + File.separatorChar;
	private static final String FILENAME = "saveFile.txt";

	private Storage storage = new Storage();
	private ArrayList<Task> tasks = null;

	private Task taskFloating = null;
	private Task taskEvent = null;
	private Task taskDeadline = null;

	Date dateStart = new Date(0);
	Date dateEnd = new Date();

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
		taskEvent.setStartDate(dateStart);
		taskEvent.setDeadline(dateEnd);
		taskEvent.setIsComplete(true);

		taskDeadline = new Task();
		taskDeadline.setDescription("Deadline Task");
		taskDeadline.setDeadline(dateEnd);
		taskDeadline.setIsComplete(false);
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

		assertNotNull(tasks);
		assertEquals(tasks.size(), 0);
	}

	@Test
	public void saveNullAttributes() throws IOException, ParseException {

		tasks.add(new Task());

		storage.save(tasks);
		tasks = storage.load();

		assertNotNull(tasks);
		assertEquals(tasks.remove(0).toString(), (new Task()).toString());
	}

	@Test
	public void saveFourTasks() throws IOException, ParseException {

		tasks.add(taskFloating);
		tasks.add(taskEvent);
		tasks.add(taskDeadline);

		storage.save(tasks);
		tasks = storage.load();

		assertNotNull(tasks);
		assertEquals(taskFloating.toString(), tasks.get(0).toString());
		assertEquals(taskEvent.toString(), tasks.get(1).toString());
		assertEquals(taskDeadline.toString(), tasks.get(2).toString());
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
	
	@Test
	public void setNonAbsolutePath() {
		assertFalse(storage.setFilePath("randomFolder" + File.separatorChar + "randomFile.txt"));
	}
	
	@Test
	public void setPathWithNonExistentFolder() {
		assertFalse(storage.setFilePath(PATH_CURRENT + "random" + File.separatorChar + FILENAME));
	}

	@After
	public void tearDown() {
		File file = new File(PATH_CURRENT + FILENAME);
		file.delete();
	}
}
