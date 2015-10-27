import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testGSDControl {

	private static final String DISPLAY_TASK_NOT_FOUND = ">> Task was not found";
	private static final String DISPLAY_NO_TASKS = ">> No tasks recorded";
	private static final String DISPLAY_NO_FLOATING_TASKS = ">> No Floating Tasks";
	private static final String DISPLAY_NO_EVENTS = ">> No Events";
	private static final String DISPLAY_NO_DEADLINES = ">> No Deadlines";
	private static final String HELP = "Add a floating task - add <description> \n"
			+ "Add a deadline task - add <description> BY <time> <date>\n"
			+ "Add an event - add <description> FROM <start time> <start date> TO <end time> <end date>\n"
			+ "Search for task - search <keyword/day/date>\n"
			+ "Update a task - update <ID> <description> FROM <start time> <start date> TO <end time> <end date>\n"
			+ "Delete a task - delete <ID>\n" + "Mark a task as complete - complete <ID>\n"
			+ "Mark a task as incomplete - incomplete <ID>\n" + "Undo last action - undo\n"
			+ "Display all tasks - all\n" + "Display floating tasks - floating\n" + "Display events - events\n"
			+ "Display deadlines - deadlines\n" + "Set file path - set <file path>\n" + "Exit GSD - exit\n";

	private static final String FEEDBACK_WELCOME_MESSAGE = "WELCOME TO GSD!\n";
	private static final String FEEDBACK_ADD = ">> ADDED ";
	private static final String FEEDBACK_SEARCH = ">> SEARCH for ";
	private static final String FEEDBACK_UPDATE = ">> UPDATED ";
	private static final String FEEDBACK_DELETE = ">> DELETED ";
	private static final String FEEDBACK_COMPLETE = ">> COMPLETED ";
	private static final String FEEDBACK_INCOMPLETE = ">> INCOMPLETE ";
	private static final String FEEDBACK_UNDO = ">> Last action undone\n";
	private static final String FEEDBACK_REDO = ">> Last action redone\n";
	private static final String FEEDBACK_ALL = ">> All tasks displayed\n";
	private static final String FEEDBACK_FLOATING = ">> Floating Tasks displayed\n";
	private static final String FEEDBACK_EVENTS = ">> Events displayed\n";
	private static final String FEEDBACK_DEADLINES = ">> Deadlines displayed\n";
	private static final String FEEDBACK_HELP = ">> Called for help!\n";
	private static final String FEEDBACK_SET = ">> File path set to ";
	private static final String FEEDBACK_SET_ERROR = ">> ERROR : FILE PATH CAN'T BE SET";
	private static final String FEEDBACK_INVALID_FILE_PATH = ">> ERROR : INVALID FILE PATH\n";
	private static final String FEEDBACK_INVALID_COMMAND = ">> ERROR : INVALID COMMAND\n";
	private static final String FEEDBACK_INVALID_COMMAND_FORMAT = ">> ERROR : INVALID COMMAND FORMAT\n";
	private static final String FEEDBACK_INVALID_TASK_NUMBER = ">> ERROR : INVALID TASK NUMBER\n";
	private static final String FEEDBACK_UNDO_ERROR = ">> ERROR : NOTHING TO UNDO\n";
	private static final String FEEDBACK_REDO_ERROR = ">> ERROR: NOTHING TO REDO\n";
	private static final String FEEDBACK_INVALID_DATE_FORMAT = ">> ERROR : INVALID DATE/TIME FORMAT\n";
	private static final String FEEDBACK_INVALID_TIME_DATE_INPUT = ">> ERROR : INVALID DATE/TIME INPUT\n";

	private static final String PATH_CURRENT = System.getProperty("user.dir") + File.separatorChar;
	private static final String FILENAME = "saveFile.txt";

	private static final String DEFAULT_TASK = "1. TASK" + "\nStart Date: " + "\nDeadline: " + "\n";
	private static final String UPDATED_TASK = "1. NOTHING" + "\nStart Date: " + "\nDeadline: " + "\n";
	private static final String INFO_BOX = "Floating Tasks = 1" + "\nEvents = 0" + "\nDeadlines = 0"
			+ "\nTotal No. of Tasks = 1" + "\n";
	private static final String INFO_BOX_EVENTS = "Floating Tasks = 0" + "\nEvents = 1" + "\nDeadlines = 0"
			+ "\nTotal No. of Tasks = 1" + "\n";
	private static final String INFO_BOX_DEADLINES = "Floating Tasks = 0" + "\nEvents = 0" + "\nDeadlines = 1"
			+ "\nTotal No. of Tasks = 1" + "\n";

	String input;
	Feedback feedback;
	Feedback check;
	GSDControl gsd = new GSDControl();

	@Before
	public void setUp() throws Exception {
		input = "set " + PATH_CURRENT + FILENAME;
		gsd.processInput(input);

	}

	@Test
	public void testCreateTask() throws Exception {
		input = "add TASK";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_ADD + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testSearchTask() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "search TASK";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_SEARCH + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testSearchNothing() throws Exception {
		input = "search TASK";
		check = new Feedback(DISPLAY_TASK_NOT_FOUND, FEEDBACK_SEARCH + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testUpdateTask() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "update 1 NOTHING";
		check = new Feedback(UPDATED_TASK, FEEDBACK_UPDATE + "TASK to NOTHING\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testUpdateNothing() throws Exception {
		input = "update 1 NOTHING";
		gsd.processInput(input);
		input = "search TASK";
		check = new Feedback(DISPLAY_TASK_NOT_FOUND, FEEDBACK_SEARCH + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testDeleteTask() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "delete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_DELETE + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testDeleteNothing() throws Exception {
		input = "delete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_INVALID_TASK_NUMBER, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testComplete() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "complete 1";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_COMPLETE + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testCompleteNothing() throws Exception {
		input = "complete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_INVALID_TASK_NUMBER, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testIncomplete() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "incomplete 1";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_INCOMPLETE + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testIncompleteNothing() throws Exception {
		input = "incomplete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_INVALID_TASK_NUMBER, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testUndo() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "undo";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_UNDO, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testUndoNothing() throws Exception {
		input = "undo";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_UNDO_ERROR, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testRedo() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "undo";
		gsd.processInput(input);
		input = "redo";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_REDO, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testRedoNothing() throws Exception {
		input = "redo";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_REDO_ERROR, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testAll() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "all";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_ALL, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testNoAll() throws Exception {
		input = "all";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_ALL, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testNoFloating() throws Exception {
		input = "floating";
		check = new Feedback(DISPLAY_NO_FLOATING_TASKS, FEEDBACK_FLOATING, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testFloating() throws Exception {
		input = "add TASK";
		gsd.processInput(input);
		input = "floating";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_FLOATING, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testNoEvents() throws Exception {
		input = "events";
		check = new Feedback(DISPLAY_NO_EVENTS, FEEDBACK_EVENTS, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testEvents() throws Exception {
		input = "add TASK FROM today TO tomorrow";
		gsd.processInput(input);
		input = "events";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_EVENTS, INFO_BOX_EVENTS);
		feedback = gsd.processInput(input);
		assertNotEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testDeadlines() throws Exception {
		input = "add TASK BY tomorrow";
		gsd.processInput(input);
		input = "deadlines";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_DEADLINES, INFO_BOX_DEADLINES);
		feedback = gsd.processInput(input);
		assertNotEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testNoDeadlines() throws Exception {
		input = "deadlines";
		check = new Feedback(DISPLAY_NO_DEADLINES, FEEDBACK_DEADLINES, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testHelp() throws Exception {
		input = "help";
		check = new Feedback(HELP, FEEDBACK_HELP, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	
	@Test
	public void testSet() throws Exception {
		input = "set " + PATH_CURRENT + FILENAME;
		check = new Feedback(null, FEEDBACK_SET + PATH_CURRENT + FILENAME +  "\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testSetFail() throws Exception {
		input = "set LOLKAPPAPRIDE";
		check = new Feedback(null, FEEDBACK_SET_ERROR, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testInvalid() throws Exception {
		input = "LOLKAPPAPRIDE";
		check = new Feedback(null, FEEDBACK_INVALID_COMMAND, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testLoadFromFile() {
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_WELCOME_MESSAGE, INFO_BOX);
		feedback = gsd.loadFromFile();
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testInvalidCommandFormat() throws Exception {
		check = new Feedback(null, FEEDBACK_INVALID_COMMAND_FORMAT, INFO_BOX);
		input = "add";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "delete";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "search";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "update";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "complete";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "incomplete";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "undo LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "redo LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "all LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "floating LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "events LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "deadlines LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "help LOLKAPPAPRIDE";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "set";
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	

	@After
	public void tearDown() {
		File file = new File(PATH_CURRENT + FILENAME);
		file.delete();
	}

}
