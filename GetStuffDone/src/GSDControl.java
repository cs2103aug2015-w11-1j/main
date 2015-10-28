import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.text.ParseException;
import java.util.*;

/*
 * GSDControl deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * GSDControl knows the existence of the main components Parser, Task, History and Storage
 * GSDControl knows the existence of stand-alone classes CommandDetails, Task and Feedback 
 * GSDControl does not know the existence of UI
 */

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
	private static final String FEEDBACK_INVALID_DATE_FORMAT = ">> ERROR : INVALID DATE/TIME FORMAT\n";
	private static final String FEEDBACK_INVALID_TIME_DATE_INPUT = ">> ERROR : INVALID DATE/TIME INPUT\n";

	private ArrayList<Task> tasks;
	private CommandDetails commandDetails;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private History history = new History();
	private boolean isValidTaskNo = true;

	public Feedback processInput(String input) {
		try {
			this.commandDetails = parser.parse(input);
		} catch (ParseException e) { // Invalid Date Format
			return new Feedback(null, FEEDBACK_INVALID_DATE_FORMAT, generateInfoBox());
		} catch (NumberFormatException f) {
			return new Feedback(null, FEEDBACK_INVALID_TASK_NUMBER, generateInfoBox());
		} catch (invalidTimeDateInput g) { // Not in the form [Time] [Date]
			return new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT, generateInfoBox());
		} catch (invalidCommand h) {
			return new Feedback(null, FEEDBACK_INVALID_COMMAND, generateInfoBox());
		} catch (invalidParameters i) {
			return new Feedback(null, FEEDBACK_INVALID_COMMAND_FORMAT, generateInfoBox());
			// Invalid parameters
			// eg delete 1 screw this up
			// eg All banananaanananananaa
		}
		switch (this.commandDetails.getCommand()) {
		case ADD:
			this.commandDetails.setID(tasks.size());
			history.insert(this.commandDetails);
			return new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription() + "\n", generateInfoBox());
		case DELETE:
			try {
				CommandDetails deletedDetails = generateDetails();
				history.insert(deletedDetails);
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
				CommandDetails oldDetails = generateDetails();
				history.insert(oldDetails);
				if (this.commandDetails.getDescription() == null) {
					return new Feedback(updateTask(commandDetails.getID() - 1),
							FEEDBACK_UPDATE + oldDetails.getDescription() + "\n", generateInfoBox());
				}
				return new Feedback(updateTask(commandDetails.getID() - 1), FEEDBACK_UPDATE
						+ oldDetails.getDescription() + " to " + this.commandDetails.getDescription() + "\n",
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
				history.insert(this.commandDetails);
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
				history.insert(this.commandDetails);
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
			return new Feedback(null, FEEDBACK_HELP, generateInfoBox(), helpCommands(), helpSyntax());
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

	// Constructor

	public GSDControl() {
	}

	// For UI

	public Feedback loadFromFile() {
		try {
			tasks = storage.load();
			if(tasks==null)	{
				tasks = new ArrayList<Task>();
				return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_NO_FILE, generateInfoBox());
			}
			return new Feedback(displayAllTasks(), FEEDBACK_WELCOME_MESSAGE, generateInfoBox());
		} catch (IOException e) {
			tasks = new ArrayList<Task>();
			return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_LOAD_ERROR, generateInfoBox());
		} catch (ParseException f) {
			tasks = new ArrayList<Task>();
			return new Feedback(DISPLAY_NO_TASKS, FEEDBACK_LOAD_ERROR, generateInfoBox());
		}
		

		
	}

	// Behavioural Methods

	private String createTask() {
		Task task = new Task(this.commandDetails);
		tasks.add(task);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String searchTask() {
		String search = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).contains(commandDetails)) {
				search += i + 1 + ". " + tasks.get(i).toString();
			}
		}

		if (search.isEmpty()) {
			search = DISPLAY_TASK_NOT_FOUND;
		}
		return search;
	}

	private String updateTask(int ID) {
		tasks.get(ID).updateDetails(commandDetails);
		Task updatedTask = tasks.get(ID);
		CommandDetails updatedDetails = new CommandDetails(CommandDetails.COMMANDS.UPDATE, updatedTask.getDescription(),
				updatedTask.getStartDate(), updatedTask.getDeadline(), ID, updatedTask.getRecurring(),
				updatedTask.getOriginalStartDate(), updatedTask.getOriginalDeadline(), updatedTask.getEndingDate());
		history.insert(updatedDetails);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String deleteTask(int ID) {
		tasks.remove(ID);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String completeTask(int ID) {
		tasks.get(ID).markAsComplete();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String incompleteTask(int ID) {
		tasks.get(ID).markAsIncomplete();
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoLastAction() {
		this.commandDetails = reverseCommandDetails(this.commandDetails.getID());
		return executeHistoryCommand();
	}

	private String redoLastAction() {
		return executeHistoryCommand();
	}

	private void recurringTaskUpdate() {

		Calendar currentDateCal = Calendar.getInstance();
		Calendar originalStartDateCal = Calendar.getInstance();
		Calendar originalDeadlineCal = Calendar.getInstance();
		Calendar startDateCal = Calendar.getInstance();
		Calendar deadlineCal = Calendar.getInstance();
		Calendar endingDateCal = Calendar.getInstance();

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isRecurring()) {
				System.out.println("i = " + i);
				originalStartDateCal.setTime(tasks.get(i).getOriginalStartDate());
				originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
				startDateCal.setTime(tasks.get(i).getStartDate());
				deadlineCal.setTime(tasks.get(i).getDeadline());
				endingDateCal.setTime(tasks.get(i).getEndingDate());
				if (currentDateCal.after(deadlineCal) || tasks.get(i).isComplete()) {
					System.out.println("PAST DEADLINE\n");
					{
						if (currentDateCal.before(endingDateCal)) {
							System.out.println("BEFORE END DATE\n");
							switch (tasks.get(i).getRecurring()) {
							case "DAILY":
								originalStartDateCal.add(Calendar.DAY_OF_YEAR, tasks.get(i).getRecurringCount());
								originalDeadlineCal.add(Calendar.DAY_OF_YEAR, tasks.get(i).getRecurringCount());
								tasks.get(i).setStartDate(originalStartDateCal.getTime());
								tasks.get(i).setDeadline(originalDeadlineCal.getTime());
								originalStartDateCal.setTime(tasks.get(i).getOriginalStartDate());
								originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
								tasks.get(i).setRecurringCount(tasks.get(i).getRecurringCount() + 1);
								break;
							case "WEEKLY":
								startDateCal.add(Calendar.DAY_OF_YEAR, 7 * tasks.get(i).getRecurringCount());
								deadlineCal.add(Calendar.DAY_OF_YEAR, 7 * tasks.get(i).getRecurringCount());
								tasks.get(i).setStartDate(originalStartDateCal.getTime());
								tasks.get(i).setDeadline(originalDeadlineCal.getTime());
								originalStartDateCal.setTime(tasks.get(i).getOriginalStartDate());
								originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
								tasks.get(i).setRecurringCount(tasks.get(i).getRecurringCount() + 1);
								break;
							case "MONTHLY":
								startDateCal.add(Calendar.MONTH, tasks.get(i).getRecurringCount());
								deadlineCal.add(Calendar.MONTH, tasks.get(i).getRecurringCount());
								tasks.get(i).setStartDate(originalStartDateCal.getTime());
								tasks.get(i).setDeadline(originalDeadlineCal.getTime());
								originalStartDateCal.setTime(tasks.get(i).getOriginalStartDate());
								originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
								tasks.get(i).setRecurringCount(tasks.get(i).getRecurringCount() + 1);
								break;
							case "YEARLY":
								startDateCal.add(Calendar.YEAR, tasks.get(i).getRecurringCount());
								deadlineCal.add(Calendar.YEAR, tasks.get(i).getRecurringCount());
								tasks.get(i).setStartDate(originalStartDateCal.getTime());
								tasks.get(i).setDeadline(originalDeadlineCal.getTime());
								originalStartDateCal.setTime(tasks.get(i).getOriginalStartDate());
								originalDeadlineCal.setTime(tasks.get(i).getOriginalDeadline());
								tasks.get(i).setRecurringCount(tasks.get(i).getRecurringCount() + 1);
								break;
							}
							// change to the next stated date according to the
							// RECURRING string
						} else {
							// if recurring task expired
							System.out.println("EXPIRED\n");
							tasks.get(i).setRecurringCount(1);
						}
					}
				}
			}
		}

	}

	private boolean setFilePath() {
		return storage.setFilePath(this.commandDetails.getDescription());
	}

	private String undoRedoCreateTask() {
		Task task = new Task(this.commandDetails);
		tasks.add(this.commandDetails.getID(), task);
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoRedoDeleteTask() {
		tasks.remove(this.commandDetails.getID());
		storage.save(tasks);
		return displayAllTasks();
	}

	private String undoRedoUpdateTask(int ID) {
		tasks.get(ID).updateDetails(commandDetails);
		storage.save(tasks);
		return displayAllTasks();
	}

	private CommandDetails generateDetails() {
		Task task = tasks.get(this.commandDetails.getID() - 1);
		switch (this.commandDetails.getCommand()) {
		case DELETE:
			CommandDetails deletedDetails = new CommandDetails(CommandDetails.COMMANDS.DELETE, task.getDescription(),
					task.getStartDate(), task.getDeadline(), this.commandDetails.getID() - 1, task.getRecurring(),
					task.getOriginalStartDate(), task.getOriginalDeadline(), task.getEndingDate());
			return deletedDetails;
		case UPDATE:
			CommandDetails oldDetails = new CommandDetails(CommandDetails.COMMANDS.UPDATE, task.getDescription(),
					task.getStartDate(), task.getDeadline(), this.commandDetails.getID() - 1, task.getRecurring(),
					task.getOriginalStartDate(), task.getOriginalDeadline(), task.getEndingDate());
			return oldDetails;
		default:
			return null;
		}
	}

	private String displayAllTasks() {
		recurringTaskUpdate();
		String displayAll = "";

		for (int i = 0; i < tasks.size(); i++) {
			displayAll += i + 1 + ". " + tasks.get(i).toString();
		}

		if (displayAll.isEmpty()) {
			displayAll = DISPLAY_NO_TASKS;
		}
		return displayAll;
	}

	private String displayFloatingTasks() {
		recurringTaskUpdate();
		String floating = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isFloating()) {
				floating += i + 1 + ". " + tasks.get(i).toString();
			}
		}

		if (floating.isEmpty()) {
			floating = DISPLAY_NO_FLOATING_TASKS;
		}
		return floating;
	}

	private String displayEvents() {
		recurringTaskUpdate();
		String events = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isEvent()) {
				events += i + 1 + ". " + tasks.get(i).toString();
			}
		}

		if (events.isEmpty()) {
			events = DISPLAY_NO_EVENTS;
		}
		return events;
	}

	private String displayDeadlines() {
		recurringTaskUpdate();
		String deadlines = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isDeadline()) {
				deadlines += i + 1 + ". " + tasks.get(i).toString();
			}
		}

		if (deadlines.isEmpty()) {
			deadlines = DISPLAY_NO_DEADLINES;
		}
		return deadlines;
	}

	private String displayRecurring() {
		recurringTaskUpdate();
		String recurring = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isRecurring()) {
				recurring += i + 1 + ". " + tasks.get(i).toString();
			}
		}
		if (recurring.isEmpty()) {
			recurring = DISPLAY_NO_RECURRING;
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

	private String helpCommands() {
		return "Add a floating task\n" + "Add a deadline task\n" + "Add an event\n" + "Add a recurring task\n"
				+ "Search for task\n" + "Update a task\n" + "Delete a task\n" + "Mark a task as complete\n"
				+ "Mark a task as incomplete\n" + "Undo last action\n" + "Redo last action\n" + "Display all tasks\n"
				+ "Display floating tasks\n" + "Display events\n" + "Display deadlines\n" + "Display recurring tasks\n"
				+ "Set file path\n" + "Exit GSD\n";
	}

	private String helpSyntax() {
		return "add <description>\n" + "add <description> BY <time> <date>\n"
				+ "add <description> FROM <start time> <start date> TO <end time> <end date>\n"
				+ "add [event/deadline] <frequency> ENDING <ending date>\n" + "search <keyword/day/date>\n"
				+ "update <ID> [floating/event/deadline]\n" + "delete <ID>\n" + "complete <ID>\n" + "incomplete <ID>\n"
				+ "undo\n" + "redo\n" + "all\n" + "floating\n" + "events\n" + "deadlines\n" + "recurring\n"
				+ "set <file path>\n" + "exit\n";
	}

	private String executeHistoryCommand() {
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return undoRedoCreateTask();
		case DELETE:
			return undoRedoDeleteTask();
		case UPDATE:
			return undoRedoUpdateTask(commandDetails.getID());
		case COMPLETE:
			return completeTask(commandDetails.getID() - 1);
		case INCOMPLETE:
			return incompleteTask(commandDetails.getID() - 1);
		default:
			return null;
		}
	}

	private CommandDetails reverseCommandDetails(int ID) {
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return reverseAdd();
		case DELETE:
			return reverseDelete(ID);
		case UPDATE:
			return this.commandDetails; // don't need to reverse engineer
		case COMPLETE:
			return reverseComplete(ID);
		case INCOMPLETE:
			return reverseIncomplete(ID);
		default:
			return commandDetails;

		}
	}

	private CommandDetails reverseAdd() {
		CommandDetails addToDelete = new CommandDetails(CommandDetails.COMMANDS.DELETE, tasks.size() - 1);
		return addToDelete;
	}

	private CommandDetails reverseDelete(int ID) {
		CommandDetails taskDelete = new CommandDetails(CommandDetails.COMMANDS.ADD,
				this.commandDetails.getDescription(), this.commandDetails.getStartDate(),
				this.commandDetails.getDeadline(), this.commandDetails.getID(), this.commandDetails.getRecurring(),
				this.commandDetails.getOriginalStartDate(), this.commandDetails.getOriginalDeadline(),
				this.commandDetails.getEndingDate());
		return taskDelete;
	}

	private CommandDetails reverseComplete(int ID) {
		CommandDetails completeToIncomplete = new CommandDetails(CommandDetails.COMMANDS.INCOMPLETE, tasks.size());
		return completeToIncomplete;
	}

	private CommandDetails reverseIncomplete(int ID) {
		CommandDetails incompleteToComplete = new CommandDetails(CommandDetails.COMMANDS.COMPLETE, tasks.size());
		return incompleteToComplete;
	}

}