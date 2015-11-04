package control;

import java.io.IOException;
import parser.Parser;
import java.nio.file.InvalidPathException;
import java.text.ParseException;
import java.util.*;
import commandDetail.CommandDetails;
import history.History;
import storage.Storage;
import task.Task;
import ui.Feedback;
import parser.InvalidCommandException;
import parser.InvalidParametersException;
/*
 * GSDControl deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * GSDControl knows the existence of the main components Parser, Task, History and Storage
 * GSDControl knows the existence of stand-alone classes CommandDetails, Task and Feedback 
 * GSDControl does not know the existence of UI
 * GSDControl returns a Feedback Object to UI for displaying
 */
import parser.InvalidTimeDateInputException;

public class GSDControl {

	private static final String DISPLAY_TASK_NOT_FOUND = ">> Task was not found";
	private static final String DISPLAY_NO_TASKS = ">> No tasks recorded";
	private static final String DISPLAY_NO_FLOATING_TASKS = ">> No Floating Tasks";
	private static final String DISPLAY_NO_EVENTS = ">> No Events";
	private static final String DISPLAY_NO_DEADLINES = ">> No Deadlines";
	private static final String DISPLAY_NO_RECURRING = ">> No Recurring Tasks";

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
	private static final String FEEDBACK_RECURRING = ">> Recurring Tasks displayed\n";
	private static final String FEEDBACK_HELP = ">> Called for help!\n";
	private static final String FEEDBACK_SET = ">> File path set to ";
	private static final String FEEDBACK_SET_ERROR = ">> ERROR : FILE PATH CAN'T BE SET";
	private static final String FEEDBACK_NO_FILE = ">> NO FILE WAS LOADED\n";
	private static final String FEEDBACK_INVALID_FILE_PATH = ">> ERROR : INVALID FILE PATH\n";
	private static final String FEEDBACK_INVALID_COMMAND = ">> ERROR : INVALID COMMAND\n";
	private static final String FEEDBACK_INVALID_COMMAND_FORMAT = ">> ERROR : INVALID COMMAND FORMAT\n";
	private static final String FEEDBACK_INVALID_TASK_NUMBER = ">> ERROR : INVALID TASK NUMBER\n";
	private static final String FEEDBACK_UNDO_ERROR = ">> ERROR : NOTHING TO UNDO\n";
	private static final String FEEDBACK_REDO_ERROR = ">> ERROR: NOTHING TO REDO\n";
	private static final String FEEDBACK_LOAD_ERROR = ">> ERROR: FAILED TO LOAD FROM FILE\n";
	private static final String FEEDBACK_INVALID_TIME_DATE_INPUT = ">> ERROR : INVALID DATE/TIME INPUT\n";

	private static final String HELP_COMMANDS = "Add a floating task\n" + "Add a deadline task\n" + "Add an event\n"
			+ "Add a recurring task\n" + "Search for task\n" + "Update a task\n" + "Delete a task\n"
			+ "Mark a task as complete\n" + "Mark a task as incomplete\n" + "Undo last action\n" + "Redo last action\n"
			+ "Display all tasks\n" + "Display floating tasks\n" + "Display events\n" + "Display deadlines\n"
			+ "Display recurring tasks\n" + "Set file path\n" + "Exit GSD\n";

	private static final String HELP_SYNTAX = "add <description>\n" + "add <description> BY <time> <date>\n"
			+ "add <description> FROM <start time> <start date> TO <end time> <end date>\n"
			+ "add [event/deadline] <frequency> ENDING <ending date>\n" + "search <keyword/day/date>\n"
			+ "update <ID> [floating/event/deadline]\n" + "delete <ID>\n" + "complete <ID>\n" + "incomplete <ID>\n"
			+ "undo\n" + "redo\n" + "all\n" + "floating\n" + "events\n" + "deadlines\n" + "recurring\n"
			+ "set <file path>\n" + "exit\n";

	private static final int SEVEN_DAYS = 7;
	private static final int ONE_DAY_BEFORE = -1;
	private static final int ONE_WEEK_BEFORE = -7;
	private static final int ONE_MONTH_BEFORE = -1;
	private static final int ONE_YEAR_BEFORE = -1;

	private ArrayList<Task> tasks;
	private CommandDetails commandDetails;
	private Storage storage = new Storage();
	private History history = new History();
	private boolean isValidTaskNo = true;

	public GSDControl() {
	}

	/*************************************************************************************************
	 ************************************* INPUT PROCESSING ******************************************
	 *************************************************************************************************/

	public Feedback processInput(String input) {
		try {
			this.commandDetails = Parser.parse(input);
			// this.commandDetails = parser.parse(input);
		} catch (NumberFormatException f) {
			return new Feedback(null, FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
		} catch (InvalidTimeDateInputException g) { // Not in the form [Time]
													// [Date]
			return new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT, generateInfoBox());
		} catch (InvalidCommandException h) {
			return new Feedback(null, FEEDBACK_INVALID_COMMAND, generateInfoBox());
		} catch (InvalidParametersException i) {
			return new Feedback(null, FEEDBACK_INVALID_COMMAND_FORMAT, generateInfoBox());
			// Invalid parameters
			// eg delete 1 screw this up
			// eg All banananaanananananaa
		}
		return executeCommand(input);
	}

	private Feedback executeCommand(String input) {
		switch (this.commandDetails.getCommand()) {
		case ADD:
			this.commandDetails.setID(tasks.size());
			return new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription() + "\n", generateInfoBox());
		case DELETE:
			try {
				String taskDescription = tasks.get(commandDetails.getID() - 1).getDescription();
				return new Feedback(deleteTask(commandDetails.getID() - 1), FEEDBACK_DELETE + taskDescription + "\n",
						generateInfoBox());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
				}
			}
		case SEARCH:
			String[] temp = input.split(" ");
			String feedbackString = "";
			for (int i = 1; i < temp.length; i++) {
				feedbackString += temp[i] + " ";
			}
			return new Feedback(searchTask(), FEEDBACK_SEARCH + feedbackString + "\n", generateInfoBox());
		case UPDATE:
			try {
				String taskDescription = tasks.get(commandDetails.getID() - 1).getDescription();
				if (this.commandDetails.getDescription() == null || this.commandDetails.getDescription()
						.equals(tasks.get(commandDetails.getID() - 1).getDescription())) {
					return new Feedback(updateTask(commandDetails.getID() - 1),
							FEEDBACK_UPDATE + taskDescription + "\n", generateInfoBox());
				}
				return new Feedback(updateTask(commandDetails.getID() - 1),
						FEEDBACK_UPDATE + taskDescription + " to " + this.commandDetails.getDescription() + "\n",
						generateInfoBox());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
				}
			}
		case COMPLETE:
			try {
				return new Feedback(completeTask(commandDetails.getID() - 1),
						FEEDBACK_COMPLETE + tasks.get(this.commandDetails.getID() - 1).getDescription() + "\n",
						generateInfoBox());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
				}
			}
		case INCOMPLETE:
			try {
				return new Feedback(incompleteTask(commandDetails.getID() - 1),
						FEEDBACK_INCOMPLETE + tasks.get(this.commandDetails.getID() - 1).getDescription() + "\n",
						generateInfoBox());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
				}
			}
		case REDO:
			this.commandDetails = history.redo();
			if (this.commandDetails == null) {
				return new Feedback(displayAllTasks(), FEEDBACK_REDO_ERROR, generateInfoBox());
			}
			return new Feedback(redoLastAction(), FEEDBACK_REDO, generateInfoBox());
		case UNDO:
			this.commandDetails = history.undo();
			if (this.commandDetails == null) {
				return new Feedback(displayAllTasks(), FEEDBACK_UNDO_ERROR, generateInfoBox());
			}
			return new Feedback(undoLastAction(), FEEDBACK_UNDO, generateInfoBox());
		case ALL:
			return new Feedback(displayAllTasks(), FEEDBACK_ALL, generateInfoBox());
		case FLOATING:
			return new Feedback(displayFloatingTasks(), FEEDBACK_FLOATING, generateInfoBox());
		case EVENTS:
			return new Feedback(displayEvents(), FEEDBACK_EVENTS, generateInfoBox());
		case DEADLINES:
			return new Feedback(displayDeadlines(), FEEDBACK_DEADLINES, generateInfoBox());
		case RECURRING:
			return new Feedback(displayRecurring(), FEEDBACK_RECURRING, generateInfoBox());
		case HELP:
			return new Feedback(null, FEEDBACK_HELP, generateInfoBox(), HELP_COMMANDS, HELP_SYNTAX);
		case EXIT:
			System.exit(0);
		case SET:
			try {
				boolean isValidFilePath = setFilePath();
				if (isValidFilePath) {
					return new Feedback(null, FEEDBACK_SET + this.commandDetails.getDescription() + "\n",
							generateInfoBox());
				} else {
					return new Feedback(null, FEEDBACK_SET_ERROR, generateInfoBox());
				}
			} catch (InvalidPathException q) {
				return new Feedback(null, FEEDBACK_INVALID_FILE_PATH, generateInfoBox());
			}
		default:
			return new Feedback(displayAllTasks(), FEEDBACK_INVALID_COMMAND, generateInfoBox());

		}
	}

	/*************************************************************************************************
	 ******************************************* UI **************************************************
	 *************************************************************************************************/

	public Feedback loadFromFile() {
		try {
			tasks = storage.load();
			if (tasks == null) {
				tasks = new ArrayList<Task>();
				return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_NO_FILE, generateInfoBox());
			}
			refreshRecurringTasks();
			return new Feedback(displayAllTasks(), FEEDBACK_WELCOME_MESSAGE, generateInfoBox());
		} catch (IOException e) {
			tasks = new ArrayList<Task>();
			return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_WELCOME_MESSAGE, generateInfoBox());
		} catch (ParseException f) {
			tasks = new ArrayList<Task>();
			return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_LOAD_ERROR, generateInfoBox());
		}

	}

	/*************************************************************************************************
	 ******************************************* CRUD ************************************************
	 *************************************************************************************************/

	private String createTask() {
		Task task = new Task(this.commandDetails);
		if (task.isRecurring()) {
			task.resetRecurringCount();
			tasks.add(task);
			this.commandDetails.setOldTask(task);
			sendToHistory();
			storage.save(tasks);
			return displayAllTasks();
		}
		tasks.add(task);
		this.commandDetails.setNewTask(task);
		sendToHistory();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String searchTask() {
		String search = "";
		String searchIncomplete = "";
		String searchComplete = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).contains(commandDetails) && !tasks.get(i).isComplete()) {
				searchIncomplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).contains(commandDetails) && tasks.get(i).isComplete()) {
				searchComplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		if (!searchIncomplete.isEmpty()) {
			searchIncomplete = "\t\tINCOMPLETED\n\n" + searchIncomplete;
		}

		if (!searchComplete.isEmpty()) {
			searchComplete = "\t\tCOMPLETED\n\n" + searchComplete;
		}

		search = searchIncomplete + searchComplete;

		if (search.isEmpty()) {
			search = DISPLAY_TASK_NOT_FOUND;
		}
		return search;
	}

	private String updateTask(int ID) {
		CommandDetails oldDetails = generateDetails();
		Task oldTask = new Task(oldDetails);
		oldDetails.setOldTask(oldTask);
		tasks.get(ID).updateDetails(commandDetails);
		Task newTask = new Task(generateDetails());
		oldDetails.setNewTask(newTask);
		//System.out.println("old = " + oldDetails.getOldTask().toString());
		//System.out.println("new = " + oldDetails.getNewTask().toString());
		this.commandDetails = oldDetails;
		sendToHistory();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String deleteTask(int ID) {
		sendToHistory();
		tasks.remove(ID);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String completeTask(int ID) {
		tasks.get(ID).markAsComplete();
		this.commandDetails.setNewTask(tasks.get(ID));
		sendToHistory();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String incompleteTask(int ID) {
		tasks.get(ID).markAsIncomplete();
		this.commandDetails.setNewTask(tasks.get(ID));
		sendToHistory();
		storage.save(tasks);
		return displayAllTasks();
	}

	private boolean setFilePath() {
		return storage.setFilePath(this.commandDetails.getDescription());
	}

	/*************************************************************************************************
	 ******************************************* UNDO/REDO *******************************************
	 *************************************************************************************************/

	private String undoLastAction() {
		return reverseAndExecuteHistoryCommand(this.commandDetails.getID());
	}

	private String redoLastAction() {
		return executeHistoryCommand();
	}

	private String executeHistoryCommand() {
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return undoRedoCreateTask(this.commandDetails.getID(), this.commandDetails.getNewTask());
		case DELETE:
			searchForID();
			return undoRedoDeleteTask(this.commandDetails.getID());
		case UPDATE:
			searchRedoID();
			return redoUpdateTask(this.commandDetails.getID(), this.commandDetails.getNewTask());
		case COMPLETE:
			searchForID();
			return undoRedoCompleteTask(commandDetails.getID());
		case INCOMPLETE:
			searchForID();
			return undoRedoIncompleteTask(commandDetails.getID());
		default:
			return null;
		}
	}

	private String reverseAndExecuteHistoryCommand(int ID) {
		switch (this.commandDetails.getCommand()) {
		case ADD:
			this.commandDetails = reverseAdd();
			return undoRedoDeleteTask(this.commandDetails.getID());
		case DELETE:
			this.commandDetails = reverseDelete();
			return undoRedoCreateTask(this.commandDetails.getID(), this.commandDetails.getNewTask());
		case UPDATE:
			searchForID();
			return undoUpdateTask(this.commandDetails.getID(), this.commandDetails.getOldTask());
		case COMPLETE:
			this.commandDetails = reverseComplete();
			return undoRedoIncompleteTask(this.commandDetails.getID());
		case INCOMPLETE:
			this.commandDetails = reverseIncomplete();
			return undoRedoCompleteTask(this.commandDetails.getID());
		default:
			return null;

		}
	}

	private String undoRedoCreateTask(int ID, Task task) {
		tasks.add(ID, task);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoRedoDeleteTask(int ID) {
		tasks.remove(ID);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoUpdateTask(int ID, Task oldTask) {
		tasks.get(ID).setAs(oldTask);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String redoUpdateTask(int ID, Task newTask) {
		tasks.get(ID).setAs(newTask);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoRedoCompleteTask(int ID) {
		if (tasks.get(ID).isRecurring()) {
			return displayAllTasks();
		}
		tasks.get(ID).markAsComplete();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoRedoIncompleteTask(int ID) {
		if (tasks.get(ID).isRecurring()) {
			return displayAllTasks();
		}
		tasks.get(ID).markAsIncomplete();
		storage.save(tasks);
		return displayAllTasks();
	}

	private CommandDetails reverseAdd() {
		searchForID();
		return new CommandDetails(CommandDetails.COMMANDS.DELETE, this.commandDetails.getID());
	}

	private CommandDetails reverseDelete() {
		return new CommandDetails(CommandDetails.COMMANDS.ADD, this.commandDetails.getDescription(),
				this.commandDetails.getStartDate(), this.commandDetails.getDeadline(), this.commandDetails.getID(),
				this.commandDetails.getRecurring(), this.commandDetails.getOriginalStartDate(),
				this.commandDetails.getOriginalDeadline(), this.commandDetails.getEndingDate(),
				this.commandDetails.getNewTask());
	}

	private CommandDetails reverseComplete() {
		searchForID();
		return new CommandDetails(CommandDetails.COMMANDS.INCOMPLETE, this.commandDetails.getID());
	}

	private CommandDetails reverseIncomplete() {
		searchForID();
		return new CommandDetails(CommandDetails.COMMANDS.COMPLETE, this.commandDetails.getID());
	}

	private void searchForID() {
		for (int i = 0; i < tasks.size(); i++) {
			if (this.commandDetails.getNewTask().matches(tasks.get(i))) {
				this.commandDetails.setID(i);
				//System.out.println("searchforid" + this.commandDetails.getID());
			}
		}
	}

	private void searchRedoID() {
		for (int i = 0; i < tasks.size(); i++) {
			if (this.commandDetails.getOldTask().matches(tasks.get(i))) {
				this.commandDetails.setID(i);
				//System.out.println("searchforid" + this.commandDetails.getID());
			}
		}
	}

	/*************************************************************************************************
	 ******************************************* HISTORY *********************************************
	 *************************************************************************************************/

	private void sendToHistory() {
		switch (this.commandDetails.getCommand()) {
		case ADD:
			history.insert(this.commandDetails);
			break;
		case DELETE:
			history.insert(generateDetails());
			break;
		case UPDATE:
			history.insert(this.commandDetails);
			break;
		case COMPLETE:
			history.insert(this.commandDetails);
			break;
		case INCOMPLETE:
			history.insert(this.commandDetails);
			break;
		default:
			break;
		}
	}

	/*
	 * Creates a CommandDetails object that matches the current CommandDetails
	 * object in GSDControl. This method is only used for DELETE and UPDATE
	 * Commands due to the nature of the Commands i.e. Requires history of both
	 * old and new versions of Tasks.
	 */
	private CommandDetails generateDetails() {
		Task task = tasks.get(this.commandDetails.getID() - 1);
		//System.out.println("generatedetails = " + task);
		switch (this.commandDetails.getCommand()) {
		case DELETE:
			return new CommandDetails(CommandDetails.COMMANDS.DELETE, task.getDescription(), task.getStartDate(),
					task.getDeadline(), this.commandDetails.getID() - 1, task.getRecurring(),
					task.getOriginalStartDate(), task.getOriginalDeadline(), task.getEndingDate(), task);
		case UPDATE:
			return new CommandDetails(CommandDetails.COMMANDS.UPDATE, task.getDescription(), task.getStartDate(),
					task.getDeadline(), this.commandDetails.getID() - 1, task.getRecurring(),
					task.getOriginalStartDate(), task.getOriginalDeadline(), task.getEndingDate());
		default:
			return null;
		}
	}

	/*************************************************************************************************
	 ******************************************* RECURRING *******************************************
	 *************************************************************************************************/

	private void refreshRecurringTasks() {

		try {
			if (commandDetails.getCommand() == CommandDetails.COMMANDS.UNDO
					|| commandDetails.getCommand() == CommandDetails.COMMANDS.REDO) {
				return;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		Calendar currentDateCal = Calendar.getInstance();
		Calendar originalStartDateCal = Calendar.getInstance();
		Calendar originalDeadlineCal = Calendar.getInstance();
		Calendar startDateCal = Calendar.getInstance();
		Calendar deadlineCal = Calendar.getInstance();
		Calendar endingDateCal = Calendar.getInstance();

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isRecurring() && tasks.get(i).getStartDate() != null) {
				originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
				originalStartDateCal.setTime(tasks.get(i).getOriginalStartDate());
				startDateCal.setTime(tasks.get(i).getStartDate());
				deadlineCal.setTime(tasks.get(i).getDeadline());
				endingDateCal.setTime(tasks.get(i).getEndingDate());
				Task recurringTask = tasks.get(i);
				updateRecurringEvent(currentDateCal, originalStartDateCal, originalDeadlineCal, startDateCal,
						deadlineCal, endingDateCal, recurringTask);
			} else if (tasks.get(i).isRecurring() && tasks.get(i).getStartDate() == null) {
				originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
				deadlineCal.setTime(tasks.get(i).getDeadline());
				endingDateCal.setTime(tasks.get(i).getEndingDate());
				Task recurringTask = tasks.get(i);
				updateRecurringDeadline(currentDateCal, originalDeadlineCal, deadlineCal, endingDateCal, recurringTask);
			}
		}

	}

	private void updateRecurringDeadline(Calendar currentDateCal, Calendar originalDeadlineCal, Calendar deadlineCal,
			Calendar endingDateCal, Task recurringTask) {

		while (isDueForUpdateAndNotExpired(currentDateCal, deadlineCal, endingDateCal, recurringTask)) {
			switch (recurringTask.getRecurring()) {
			case "DAILY":
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				deadlineCal.add(Calendar.DAY_OF_YEAR, recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, deadlineCal, endingDateCal)) {
					handleExpiredDeadline(deadlineCal, recurringTask);
					break;
				}
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				recurringTask.setIsComplete(false);
				break;
			case "WEEKLY":
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				deadlineCal.add(Calendar.DAY_OF_YEAR, SEVEN_DAYS * recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, deadlineCal, endingDateCal)) {
					handleExpiredDeadline(deadlineCal, recurringTask);
					break;
				}
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				recurringTask.setIsComplete(false);
				break;
			case "MONTHLY":
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				deadlineCal.add(Calendar.MONTH, recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, deadlineCal, endingDateCal)) {
					handleExpiredDeadline(deadlineCal, recurringTask);
					break;
				}
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				recurringTask.setIsComplete(false);
				break;
			case "YEARLY":
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				deadlineCal.add(Calendar.YEAR, recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, deadlineCal, endingDateCal)) {
					handleExpiredDeadline(deadlineCal, recurringTask);
					break;
				}
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				recurringTask.setIsComplete(false);
				break;
			}
		}
		if (isExpired(currentDateCal, deadlineCal, endingDateCal)) {
			handleExpiredDeadline(deadlineCal, recurringTask);
		}
	}

	private void updateRecurringEvent(Calendar currentDateCal, Calendar originalStartDateCal,
			Calendar originalDeadlineCal, Calendar startDateCal, Calendar deadlineCal, Calendar endingDateCal,
			Task recurringTask) {

		while (isDueForUpdateAndNotExpired(currentDateCal, deadlineCal, endingDateCal, recurringTask)) {
			switch (recurringTask.getRecurring()) {
			case "DAILY":
				startDateCal = (Calendar) originalStartDateCal.clone();
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				startDateCal.add(Calendar.DAY_OF_YEAR, recurringTask.getRecurringCount());
				deadlineCal.add(Calendar.DAY_OF_YEAR, recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, startDateCal, endingDateCal)) {
					handleExpiredEvent(startDateCal, deadlineCal, recurringTask);
					break;
				}
				recurringTask.setStartDate(startDateCal.getTime());
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				recurringTask.setIsComplete(false);
				break;
			case "WEEKLY":
				startDateCal = (Calendar) originalStartDateCal.clone();
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				startDateCal.add(Calendar.DAY_OF_YEAR, SEVEN_DAYS * recurringTask.getRecurringCount());
				deadlineCal.add(Calendar.DAY_OF_YEAR, SEVEN_DAYS * recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, startDateCal, endingDateCal)) {
					handleExpiredEvent(startDateCal, deadlineCal, recurringTask);
					break;
				}
				recurringTask.setStartDate(startDateCal.getTime());
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				break;
			case "MONTHLY":
				startDateCal = (Calendar) originalStartDateCal.clone();
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				startDateCal.add(Calendar.MONTH, recurringTask.getRecurringCount());
				deadlineCal.add(Calendar.MONTH, recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, startDateCal, endingDateCal)) {
					handleExpiredEvent(startDateCal, deadlineCal, recurringTask);
					break;
				}
				recurringTask.setStartDate(startDateCal.getTime());
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				break;
			case "YEARLY":
				startDateCal = (Calendar) originalStartDateCal.clone();
				deadlineCal = (Calendar) originalDeadlineCal.clone();
				startDateCal.add(Calendar.YEAR, recurringTask.getRecurringCount());
				deadlineCal.add(Calendar.YEAR, recurringTask.getRecurringCount());
				if (isExpired(currentDateCal, startDateCal, endingDateCal)) {
					handleExpiredEvent(startDateCal, deadlineCal, recurringTask);
					break;
				}
				recurringTask.setStartDate(startDateCal.getTime());
				recurringTask.setDeadline(deadlineCal.getTime());
				recurringTask.incrementRecurringCount();
				break;
			}
		}
	}

	private void handleExpiredDeadline(Calendar deadlineCal, Task recurringTask) {
		switch (recurringTask.getRecurring()) {
		case "DAILY":
			deadlineCal.add(Calendar.DAY_OF_YEAR, ONE_DAY_BEFORE);
			endRecurringTask(recurringTask);
			break;
		case "WEEKLY":
			deadlineCal.add(Calendar.DAY_OF_YEAR, ONE_WEEK_BEFORE);
			endRecurringTask(recurringTask);
			break;
		case "MONTHLY":
			deadlineCal.add(Calendar.MONTH, ONE_MONTH_BEFORE);
			endRecurringTask(recurringTask);
			break;
		case "YEARLY":
			deadlineCal.add(Calendar.YEAR, ONE_YEAR_BEFORE);
			endRecurringTask(recurringTask);
			break;
		}
	}

	private void handleExpiredEvent(Calendar startDateCal, Calendar deadlineCal, Task recurringTask) {
		switch (recurringTask.getRecurring()) {
		case "DAILY":
			startDateCal.add(Calendar.DAY_OF_YEAR, ONE_DAY_BEFORE);
			deadlineCal.add(Calendar.DAY_OF_YEAR, ONE_DAY_BEFORE);
			endRecurringTask(recurringTask);
			break;
		case "WEEKLY":
			startDateCal.add(Calendar.DAY_OF_YEAR, ONE_WEEK_BEFORE);
			deadlineCal.add(Calendar.DAY_OF_YEAR, ONE_WEEK_BEFORE);
			endRecurringTask(recurringTask);
			break;
		case "MONTHLY":
			startDateCal.add(Calendar.MONTH, ONE_MONTH_BEFORE);
			deadlineCal.add(Calendar.MONTH, ONE_MONTH_BEFORE);
			endRecurringTask(recurringTask);
			break;
		case "YEARLY":
			startDateCal.add(Calendar.YEAR, ONE_YEAR_BEFORE);
			deadlineCal.add(Calendar.YEAR, ONE_YEAR_BEFORE);
			endRecurringTask(recurringTask);
			break;
		}
	}

	private boolean isDueForUpdateAndNotExpired(Calendar currentDateCal, Calendar deadlineCal, Calendar endingDateCal,
			Task recurringTask) {
		return (currentDateCal.after(deadlineCal) || recurringTask.isComplete());
	}

	private boolean isExpired(Calendar currentDateCal, Calendar latestDateCal, Calendar endingDateCal) {
		return currentDateCal.after(endingDateCal) || latestDateCal.after(endingDateCal);
	}

	private void endRecurringTask(Task recurringTask) {
		recurringTask.resetRecurringCount();
		recurringTask.setIsComplete(true);
	}

	/*************************************************************************************************
	 ******************************************* DISPLAY *********************************************
	 *************************************************************************************************/

	private String displayAllTasks() {
		refreshRecurringTasks();
		Collections.sort(tasks);
		String displayAll = "";
		String displayIncomplete = "";
		String displayComplete = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (!tasks.get(i).isComplete())
				displayIncomplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isComplete())
				displayComplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
		}

		if (!displayIncomplete.isEmpty()) {
			displayIncomplete = "\t\tINCOMPLETED\n\n" + displayIncomplete;
		}

		if (!displayComplete.isEmpty()) {
			displayComplete = "\t\tCOMPLETED\n\n" + displayComplete;
		}

		displayAll = displayIncomplete + displayComplete;

		if (displayAll.isEmpty()) {
			return displayAll = DISPLAY_NO_TASKS;
		}

		return displayAll;
	}

	private String displayFloatingTasks() {
		refreshRecurringTasks();
		Collections.sort(tasks);
		String floating = "";
		String floatingIncomplete = "";
		String floatingComplete = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isFloating() && !tasks.get(i).isComplete()) {
				floatingIncomplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isFloating() && tasks.get(i).isComplete()) {
				floatingComplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		if (!floatingIncomplete.isEmpty()) {
			floatingIncomplete = "\t\tINCOMPLETED\n\n" + floatingIncomplete;
		}

		if (!floatingComplete.isEmpty()) {
			floatingComplete = "\t\tCOMPLETED\n\n" + floatingComplete;
		}

		floating = floatingIncomplete + floatingComplete;

		if (floating.isEmpty()) {
			return floating = DISPLAY_NO_FLOATING_TASKS;
		}
		return floating;
	}

	private String displayEvents() {
		refreshRecurringTasks();
		Collections.sort(tasks);
		String events = "";
		String eventsIncomplete = "";
		String eventsComplete = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isEvent() && !tasks.get(i).isComplete()) {
				eventsIncomplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isEvent() && tasks.get(i).isComplete()) {
				eventsComplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		if (!eventsIncomplete.isEmpty()) {
			eventsIncomplete = "\t\tINCOMPLETED\n\n" + eventsIncomplete;
		}

		if (!eventsComplete.isEmpty()) {
			eventsComplete = "\t\tCOMPLETED\n\n" + eventsComplete;
		}

		events = eventsIncomplete + eventsComplete;

		if (events.isEmpty()) {
			return events = DISPLAY_NO_EVENTS;
		}
		return events;
	}

	private String displayDeadlines() {
		refreshRecurringTasks();
		Collections.sort(tasks);
		String deadlines = "";
		String deadlinesIncomplete = "";
		String deadlinesComplete = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isDeadline() && !tasks.get(i).isComplete()) {
				deadlinesIncomplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isDeadline() && tasks.get(i).isComplete()) {
				deadlinesComplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		if (!deadlinesIncomplete.isEmpty()) {
			deadlinesIncomplete = "\t\tINCOMPLETED\n\n" + deadlinesIncomplete;
		}

		if (!deadlinesComplete.isEmpty()) {
			deadlinesComplete = "\t\tCOMPLETED\n\n" + deadlinesComplete;
		}

		deadlines = deadlinesIncomplete + deadlinesComplete;

		if (deadlines.isEmpty()) {
			return deadlines = DISPLAY_NO_DEADLINES;
		}
		return deadlines;
	}

	private String displayRecurring() {
		refreshRecurringTasks();
		Collections.sort(tasks);
		String recurring = "";
		String recurringIncomplete = "";
		String recurringComplete = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isRecurring() && !tasks.get(i).isComplete()) {
				recurringIncomplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isRecurring() && tasks.get(i).isComplete()) {
				recurringComplete += i + 1 + ". " + tasks.get(i).toString() + "\n";
			}
		}

		if (!recurringIncomplete.isEmpty()) {
			recurringIncomplete = "\t\tINCOMPLETED\n\n" + recurringIncomplete;
		}

		if (!recurringComplete.isEmpty()) {
			recurringComplete = "\t\tCOMPLETED\n\n" + recurringComplete;
		}

		recurring = recurringIncomplete + recurringComplete;

		if (recurring.isEmpty()) {
			return recurring = DISPLAY_NO_RECURRING;
		}
		return recurring;
	}

	private String generateInfoBox() {
		int floating = 0, events = 0, deadlines = 0, recurring = 0, totalTasks = tasks.size();

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isDeadline()) {
				deadlines++;
			}
			if (tasks.get(i).isEvent()) {
				events++;
			}
			if (tasks.get(i).isFloating()) {
				floating++;
			}
			if (tasks.get(i).isRecurring()) {
				recurring++;
			}
		}
		return "Floating Tasks = " + floating + "\nEvents = " + events + "\nDeadlines = " + deadlines
				+ "\nRecurring Tasks = " + recurring + "\nTotal No. of Tasks = " + totalTasks + "\n";
	}

}