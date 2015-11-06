package control;

//@@author A0110616W

import parser.Parser;
import parser.InvalidParametersException;
import parser.InvalidTimeDateInputException;
import java.util.*;
import commandDetail.CommandDetails;
import history.History;
import storage.Storage;
import task.Task;
import ui.Feedback;

/**
 * GSDControl deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * GSDControl knows the existence of the main components Parser, Task, History and Storage
 * GSDControl knows the existence of stand-alone classes CommandDetails, Task and Feedback 
 * GSDControl does not know the existence of UI
 * GSDControl returns a Feedback Object to UI for displaying
 * 
 * INTERACTIONS OF GSDControl WITH OTHER CLASSES:
 * 
 * Task: Stores an ArrayList of Tasks in GSDControl
 * Parser: Passes input from UI to Parser for parsing into a CommandDetails object which is returned to GSDControl
 * CommandDetails: GSDControl receives CommandDetails objects from Parser. GSDControl sends CommandDetails objects to History
 * History: GSDControl sends and receives CommandDetails objects to and from History respectively
 * Storage: GSDControl sends and receives an ArrayList of Tasks to and from Storage respectively
 * UI: GSDControl returns Feedback objects to UI for displaying 
 * Feedback: GSDControl creates Feedback objects for UI's usage
 */

public class GSDControl {

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
	private static final String FEEDBACK_INVALID_COMMAND = ">> ERROR : INVALID COMMAND\n";
	private static final String FEEDBACK_INVALID_COMMAND_FORMAT = ">> ERROR : INVALID COMMAND FORMAT\n";
	private static final String FEEDBACK_INVALID_TASK_NUMBER = ">> ERROR : INVALID TASK NUMBER\n";
	private static final String FEEDBACK_UNDO_ERROR = ">> ERROR : NOTHING TO UNDO\n";
	private static final String FEEDBACK_REDO_ERROR = ">> ERROR: NOTHING TO REDO\n";
	private static final String FEEDBACK_LOAD_ERROR = ">> ERROR: FAILED TO LOAD FROM FILE\n";
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
		} catch (NumberFormatException f) {
			//Task Number Invalid or not found
			return new Feedback(null, FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
		} catch (InvalidTimeDateInputException g) {
			// Not in the form [Time][Date]
			return new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT, generateInfoBox());
		} catch (InvalidParametersException i) {
			return new Feedback(null, FEEDBACK_INVALID_COMMAND_FORMAT, generateInfoBox());
			/*
			 *	EXAMPLES
			 *	delete 1 potato
			 *  complete 1 potato
			 *	incomplete 1 potato
			 *	undo potato	
			 *	redo potato
			 *	help potato
			 *	all potato
			 *	floating potato
			 *	events potato
			 *	deadlines potato
			 *	exit potato
			 */
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
		case HELP:
			return new Feedback(null, FEEDBACK_HELP, generateInfoBox(), HELP_COMMANDS, HELP_SYNTAX);
		case EXIT:
			return null;
		case SET:
			boolean isValidFilePath = setFilePath();
			if (isValidFilePath) {
				return new Feedback(null, FEEDBACK_SET + commandDetails.getDescription() + "\n", generateInfoBox());
			} else {
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

		tasks = storage.load();

		if (tasks == null) {
			tasks = new ArrayList<Task>();
			return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_LOAD_ERROR, generateInfoBox());
		}
		return new Feedback(displayAllTasks(), FEEDBACK_WELCOME_MESSAGE, generateInfoBox());
	}

	/*************************************************************************************************
	 ******************************************* CRUD ************************************************
	 *************************************************************************************************/

	private String createTask() {
		Task task = new Task(this.commandDetails);
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
		tasks.get(ID).markAsComplete();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoRedoIncompleteTask(int ID) {
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
			}
		}
	}

	private void searchRedoID() {
		for (int i = 0; i < tasks.size(); i++) {
			if (this.commandDetails.getOldTask().matches(tasks.get(i))) {
				this.commandDetails.setID(i);
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

	/**
	 * Creates a CommandDetails object that matches the current CommandDetails
	 * object in GSDControl. This method is only used for DELETE and UPDATE
	 * Commands due to the nature of the Commands i.e. Requires history of both
	 * old and new versions of Tasks.
	 */
	private CommandDetails generateDetails() {
		Task task = tasks.get(this.commandDetails.getID() - 1);
		switch (this.commandDetails.getCommand()) {
		case DELETE:
			return new CommandDetails(CommandDetails.COMMANDS.DELETE, task.getDescription(), task.getStartDate(),
					task.getDeadline(), this.commandDetails.getID() - 1, task);
		case UPDATE:
			return new CommandDetails(CommandDetails.COMMANDS.UPDATE, task.getDescription(), task.getStartDate(),
					task.getDeadline(), this.commandDetails.getID() - 1);
		default:
			return null;
		}
	}

	/*************************************************************************************************
	 ******************************************* DISPLAY *********************************************
	 *************************************************************************************************/

	private String displayAllTasks() {
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

	private String generateInfoBox() {
		int floating = 0, events = 0, deadlines = 0, totalTasks = tasks.size();

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

		}
		return "Floating Tasks = " + floating + "\nEvents = " + events + "\nDeadlines = " + deadlines
				+ "\nTotal No. of Tasks = " + totalTasks + "\n";
	}
}