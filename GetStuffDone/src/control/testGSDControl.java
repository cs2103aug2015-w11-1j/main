package control;

//@@author A0110616W

import static org.junit.Assert.*;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.Feedback;

public class testGSDControl {

	private static final String DISPLAY_TASK_NOT_FOUND = ">> Task was not found";
	private static final String DISPLAY_NO_TASKS = ">> No tasks recorded";
	private static final String DISPLAY_NO_FLOATING_TASKS = ">> No Floating Tasks";
	private static final String DISPLAY_NO_EVENTS = ">> No Events";
	private static final String DISPLAY_NO_DEADLINES = ">> No Deadlines";

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
	private static final String FEEDBACK_INVALID_FILE_PATH = ">> ERROR : INVALID FILE PATH\n";
	private static final String FEEDBACK_INVALID_COMMAND_FORMAT = ">> ERROR : INVALID COMMAND FORMAT\n";
	private static final String FEEDBACK_INVALID_TASK_NUMBER = ">> ERROR : INVALID TASK NUMBER\n";
	private static final String FEEDBACK_UNDO_ERROR = ">> ERROR : NOTHING TO UNDO\n";
	private static final String FEEDBACK_REDO_ERROR = ">> ERROR: NOTHING TO REDO\n";
	private static final String FEEDBACK_FILE_NOT_FOUND = ">> ERROR: FILE TO LOAD DOES NOT EXIST\n";
	private static final String FEEDBACK_INVALID_TIME_DATE_INPUT = ">> ERROR : INVALID DATE/TIME INPUT\n";

	private static final String HELP_COMMANDS = "Add a floating task\n" + "Add a deadline task\n" + "Add an event\n"
			+ "Search for task\n" + "Update a task\n" + "Delete a task\n" + "Mark a task as complete\n"
			+ "Mark a task as incomplete\n" + "Undo last action\n" + "Redo last action\n" + "Display all tasks\n"
			+ "Display floating tasks\n" + "Display events\n" + "Display deadlines\n" + "Set file path\n"
			+ "Exit GSD\n";

	private static final String HELP_SYNTAX = "add <description>\n" + "add <description> by <time AND/OR date>\n"
			+ "add <description> from <start time AND/OR start date> to <end time AND/OR end date>\n"
			+ "search <keyword/day/date>\n" + "update <ID> [Details of floating/event/deadline]\n" + "delete <ID>\n"
			+ "complete <ID>\n" + "incomplete <ID>\n" + "undo\n" + "redo\n" + "all\n" + "floating\n" + "events\n"
			+ "deadlines\n" + "set <file path>\n" + "exit\n";

	private static final String PATH_CURRENT = System.getProperty("user.dir") + File.separatorChar;
	private static final String FILENAME = "saveFile.txt";

	private static final String DEFAULT_TASK = "\t\tINCOMPLETED\n\n1. TASK" + "\nStart Date: -" + "\nDeadline: -" + "\n\n";
	private static final String DEFAULT_TASK_COMPLETED = "\t\tCOMPLETED\n\n1. TASK" + "\nStart Date: -" + "\nDeadline: -" + "\n\n";
	private static final String DEFAULT_TASK_DESCRIPTION = "TASK\n";
	private static final String UPDATED_TASK = "\t\tINCOMPLETED\n\n1. NOTHING" + "\nStart Date: -" + "\nDeadline: -" + "\n\n";
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
	public void setUp() {
		gsd.loadFromFile();
		input = "set " + PATH_CURRENT + FILENAME;
		gsd.processInput(input);

	}

	@Test
	public void testCreateTask() {
		input = "add TASK";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_ADD + DEFAULT_TASK_DESCRIPTION, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testSearchTask() {
		input = "add TASK";
		gsd.processInput(input);
		input = "search TASK";
		check = new Feedback(DEFAULT_TASK, FEEDBACK_SEARCH + DEFAULT_TASK_DESCRIPTION, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testSearchNothing() {
		input = "search TASK";
		check = new Feedback(DISPLAY_TASK_NOT_FOUND, FEEDBACK_SEARCH + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testUpdateTask() {
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
	public void testUpdateNothing() {
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
	public void testDeleteTask() {
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
	public void testDeleteNothing() {
		input = "delete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_INVALID_TASK_NUMBER, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testComplete() {
		input = "add TASK";
		gsd.processInput(input);
		input = "complete 1";
		check = new Feedback(DEFAULT_TASK_COMPLETED, FEEDBACK_COMPLETE + "TASK\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testCompleteNothing() {
		input = "complete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_INVALID_TASK_NUMBER, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testIncomplete() {
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
	public void testIncompleteNothing() {
		input = "incomplete 1";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_INVALID_TASK_NUMBER, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testUndo() {
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
	public void testUndoNothing() {
		input = "undo";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_UNDO_ERROR, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testRedo() {
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
	public void testRedoNothing() {
		input = "redo";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_REDO_ERROR, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testAll() {
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
	public void testNoAll() {
		input = "all";
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_ALL, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testNoFloating() {
		input = "floating";
		check = new Feedback(DISPLAY_NO_FLOATING_TASKS, FEEDBACK_FLOATING, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testFloating() {
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
	public void testNoEvents() {
		input = "events";
		check = new Feedback(DISPLAY_NO_EVENTS, FEEDBACK_EVENTS, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testEvents() {
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
	public void testDeadlines() {
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
	public void testNoDeadlines() {
		input = "deadlines";
		check = new Feedback(DISPLAY_NO_DEADLINES, FEEDBACK_DEADLINES, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testHelp() {
		input = "help";
		check = new Feedback(null, FEEDBACK_HELP, INFO_BOX, HELP_COMMANDS, HELP_SYNTAX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getHelpCommandString(), check.getHelpCommandString());
		assertEquals(feedback.getHelpSyntaxString(), check.getHelpSyntaxString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testSet() {
		input = "set " + PATH_CURRENT + FILENAME;
		check = new Feedback(null, FEEDBACK_SET + PATH_CURRENT + FILENAME + "\n", INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testSetFail() {
		input = "set LOLKAPPAPRIDE";
		check = new Feedback(null, FEEDBACK_INVALID_FILE_PATH, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}

	@Test
	public void testLoadFromFile() {
		input = "add TASK";
		gsd.processInput(input);
		check = new Feedback(DEFAULT_TASK, FEEDBACK_WELCOME_MESSAGE, INFO_BOX);
		feedback = gsd.loadFromFile();
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testLoadFromFileNothing() {
		check = new Feedback(DISPLAY_NO_TASKS, FEEDBACK_FILE_NOT_FOUND, INFO_BOX);
		feedback = gsd.loadFromFile();
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
	}
	
	@Test
	public void testInvalidTimeDateInput()	{
		input = "add TASK by today 7pm";
		check = new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		input = "add TASK";
		gsd.processInput(input);
		input = "update 1 TASK by today 7pm";
		check = new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertEquals(feedback.getInfoString(), check.getInfoString());
		input = "delete 1";
		gsd.processInput(input);
		input = "search TASK by today 7pm";
		check = new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT, INFO_BOX);
		feedback = gsd.processInput(input);
		assertEquals(feedback.getDisplayString(), check.getDisplayString());
		assertEquals(feedback.getFeedbackString(), check.getFeedbackString());
		assertNotEquals(feedback.getInfoString(), check.getInfoString());
		
	}

	@Test
	public void testInvalidCommandFormat() {
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
