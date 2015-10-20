import java.text.ParseException;
import java.util.*;

/*
 * GSDControl deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * GSDControl knows the existence of Parser, Task, History and Storage
 * GSDControl does not know the existence of UI wutfaceS
 */

public class GSDControl {

	private static final String TASK_NOT_FOUND = ">> Task was not found";
	private static final String NO_TASKS = ">> No tasks recorded";
	private static final String FEEDBACK_ADD = ">> ADDED ";
	private static final String FEEDBACK_SEARCH = ">> SEARCH for ";
	private static final String FEEDBACK_UPDATE = ">> UPDATED ";
	private static final String FEEDBACK_DELETE = ">> DELETED ";
	private static final String FEEDBACK_COMPLETE = ">> COMPLETED ";
	private static final String FEEDBACK_INCOMPLETE = ">> INCOMPLETE ";
	private static final String FEEDBACK_UNDO = ">> Last action undone";
	private static final String FEEDBACK_REDO = ">> Last action redone";
	private static final String FEEDBACK_ALL = ">> All tasks displayed";
	private static final String FEEDBACK_FLOATING = ">> Floating Tasks displayed";
	private static final String FEEDBACK_EVENTS = ">> Events displayed";
	private static final String FEEDBACK_DEADLINES = ">> Deadlines displayed";
	private static final String FEEDBACK_HELP = ">> Called for help!";
	private static final String FEEDBACK_SET = ">> File path set to ";
	private static final String FEEDBACK_INVALID_COMMAND = ">> ERROR : INVALID COMMAND";
	private static final String FEEDBACK_INVALID_TASK_NUMBER = ">> ERROR : INVALID TASK NUMBER";
	private static final String FEEDBACK_UNDO_ERROR = ">> ERROR : NOTHING TO UNDO";
	private static final String FEEDBACK_REDO_ERROR = ">> ERROR: NOTHING TO REDO";
	private static final String FEEDBACK_INVALID_DATE_FORMAT = ">> ERROR : INVALID DATE/TIME FORMAT";
	private static final String FEEDBACK_INVALID_TIME_DATE_INPUT = ">> ERROR : INVALID DATE/TIME INPUT";
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private Scanner sc = new Scanner(System.in);
	private CommandDetails commandDetails;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private History history = new History();
	private boolean isValidTaskNo = true;

	public Feedback processInput(String input) throws IndexOutOfBoundsException {
		Feedback feedback;
		try {
			this.commandDetails = parser.parse(input);
			
		} catch (ParseException e) {	//Invalid Date Format
			return feedback = new Feedback(null, FEEDBACK_INVALID_DATE_FORMAT);
		} catch (NumberFormatException f) {
			return feedback = new Feedback(null, FEEDBACK_INVALID_TASK_NUMBER);
		} catch(invalidTimeDateInput g){	//Not in the form [Time] [Date]
			return feedback = new Feedback(null, FEEDBACK_INVALID_TIME_DATE_INPUT);
		}catch (invalidCommand h){
			return feedback = new Feedback(null, FEEDBACK_INVALID_COMMAND);
		}
		switch (this.commandDetails.getCommand()) {
		case ADD:
			this.commandDetails.setID(tasks.size());
			history.insert(this.commandDetails);
			// history.insert(reverseCommandDetails(this.commandDetails.getID()));
			return feedback = new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription());
		case DELETE:
			try {
				Task taskToDelete = tasks.get(this.commandDetails.getID() - 1);
				CommandDetails deletedDetails = new CommandDetails(CommandDetails.COMMANDS.DELETE,
						taskToDelete.getDescription(), taskToDelete.getStartDate(), taskToDelete.getDeadline(),
						this.commandDetails.getID() - 1);
				history.insert(deletedDetails);
				String taskDescription = tasks.get(commandDetails.getID() - 1).getDescription();
				return feedback = new Feedback(deleteTask(commandDetails.getID() - 1),
						FEEDBACK_DELETE + taskDescription);
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
				}
			}
		case SEARCH:
			return feedback = new Feedback(searchTask(), FEEDBACK_SEARCH + commandDetails.getDescription());
		case UPDATE:
			try {
				Task oldTask = tasks.get(this.commandDetails.getID() - 1);
				CommandDetails oldDetails = new CommandDetails(CommandDetails.COMMANDS.UPDATE,
						oldTask.getDescription(), oldTask.getStartDate(), oldTask.getDeadline(),
						this.commandDetails.getID() - 1);
				System.out.println(oldDetails.getDescription());
				history.insert(oldDetails);
				// history.insert(reverseCommandDetails(this.commandDetails.getID()-1));
				return feedback = new Feedback(updateTask(commandDetails.getID() - 1),
						FEEDBACK_UPDATE + commandDetails.getDescription());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
				}
			}
		case COMPLETE:
			try {
				history.insert(this.commandDetails);
				return feedback = new Feedback(completeTask(commandDetails.getID() - 1),
						FEEDBACK_COMPLETE + tasks.get(this.commandDetails.getID() - 1).getDescription());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
				}
			}
		case INCOMPLETE:
			try {
				history.insert(this.commandDetails);
				return feedback = new Feedback(incompleteTask(commandDetails.getID() - 1),
						FEEDBACK_INCOMPLETE + tasks.get(this.commandDetails.getID() - 1).getDescription());
			} catch (IndexOutOfBoundsException e) {
				isValidTaskNo = false;
				throw new IndexOutOfBoundsException();
			} finally {
				if (!isValidTaskNo) {
					isValidTaskNo = true;
					return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
				}
			}
		case REDO:
			this.commandDetails = history.redo();
			if (this.commandDetails == null) {
				return feedback = new Feedback(displayAllTasks(), FEEDBACK_REDO_ERROR);
			}
			return feedback = new Feedback(redoLastAction(), FEEDBACK_REDO);
		case UNDO:
			this.commandDetails = history.undo();
			if (this.commandDetails == null) {
				return feedback = new Feedback(displayAllTasks(), FEEDBACK_UNDO_ERROR);
			}
			return feedback = new Feedback(undoLastAction(), FEEDBACK_UNDO);
		case ALL:
			return feedback = new Feedback(displayAllTasks(), FEEDBACK_ALL);
		case FLOATING:
			//return feedback = new Feedback(displayFloatingTasks(), FEEDBACK_FLOATING);
		case EVENTS:
			//return feedback = new Feedback(displayEvents(), FEEDBACK_EVENTS);
		case DEADLINES:
			//return feedback = new Feedback(displayDeadlines(), FEEDBACK_DEADLINES);
		case HELP:
			return feedback = new Feedback(help(), FEEDBACK_HELP);
		case EXIT:

		case SET:
			// return feedback = new Feedback(setFilePath(), FEEDBACK_SET +
			// this.commandDetails.getDescription());
		default:
			return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_COMMAND);

		}
	}

	// Constructor

	public GSDControl() {

	}

	// Behavioural Methods

	private String createTask() {
		Task task = new Task(this.commandDetails);
		tasks.add(task);
		storage.save(tasks);
		return displayAllTasks();
		// return tasks.size() + " " + task.toString();
	}

	// Print task according to the given commandDetails
	private String searchTask() {
		String search = "";

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getDescription().contains(commandDetails.getDescription())) {
				search += i + 1 + ". " + tasks.get(i).toString();
			}
		}

		if (search.isEmpty()) {
			search = TASK_NOT_FOUND;
		}
		return search;
	}

	private String updateTask(int ID) {
		tasks.get(ID).updateDetails(commandDetails);
		Task updatedTask = tasks.get(ID);
		CommandDetails updatedDetails = new CommandDetails(CommandDetails.COMMANDS.UPDATE,
				updatedTask.getDescription(), updatedTask.getStartDate(), updatedTask.getDeadline(),
				ID);
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
		;
		return displayAllTasks();
	}

	private String undoLastAction() {
		System.out.println(commandDetails.toString());
		this.commandDetails = reverseCommandDetails(this.commandDetails.getID());
		System.out.println(commandDetails.toString());
		return executeHistoryCommand();
	}

	private String redoLastAction() {
		System.out.println(commandDetails.toString());
		return executeHistoryCommand();
	}

	/*
	 * private String setFilePath() {
	 * storage.setFilePath(this.commandDetails.getDescription()); 
	 * return null;
	 *  }
	 */

	private String undoRedoCreateTask() {
		Task task = new Task(this.commandDetails);
		tasks.add(this.commandDetails.getID(), task);
		storage.save(tasks);
		return displayAllTasks();
		// return tasks.size() + " " + task.toString();
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

	private String displayAllTasks() {
		String display = "";

		for (int i = 0; i < tasks.size(); i++) {
			display += i + 1 + ". " + tasks.get(i).toString();
		}

		if (display.isEmpty()) {
			display = NO_TASKS;
		}
		return display;
	}

	private String help() {
		return "Add a floating task - add <description> AT <venue> PRIORITY <priority>\n"
				+ "Add a deadline task - add <description> BY <deadline> AT <venue> PRIORITY <priority>\n"
				+ "Add an event - add <description> FROM <start date/time> TO <end date/time> AT <venue> PRIORITY <priority>\n"
				+ "Search for task - search <keyword/day/date>\n"
				+ "Update a task - update <ID> <description> FROM <start date/time> TO <end date/time> AT <venue> PRIORITY <priority>\n"
				+ "Delete a task - delete <ID>\n" + "Mark a task as complete - complete <ID>\n"
				+ "Mark a task as incomplete - incomplete <ID>\n" + "Undo last action - undo\n"
				+ "Display all tasks - display\n" + "Display floating tasks - floating\n" + "Exit GSD - exit\n";
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
		CommandDetails addToDelete;
		return addToDelete = new CommandDetails(CommandDetails.COMMANDS.DELETE, tasks.size() - 1);
	}

	private CommandDetails reverseDelete(int ID) {
		// this.commandDetails.setCommand(CommandDetails.COMMANDS.ADD);
		// return this.commandDetails;
		CommandDetails taskDelete = new CommandDetails(CommandDetails.COMMANDS.ADD,
				this.commandDetails.getDescription(), this.commandDetails.getStartDate(),
				this.commandDetails.getDeadline(), this.commandDetails.getID());
		return taskDelete;
	}

	private CommandDetails reverseUpdate(int ID) {
		Task taskToUpdate = tasks.get(ID);
		CommandDetails unUpdate;
		return unUpdate = new CommandDetails(CommandDetails.COMMANDS.UPDATE, taskToUpdate.getDescription(),
				taskToUpdate.getStartDate(), taskToUpdate.getDeadline(), ID);
	}

	private CommandDetails reverseComplete(int ID) {
		CommandDetails completeToIncomplete;
		return completeToIncomplete = new CommandDetails(CommandDetails.COMMANDS.INCOMPLETE, tasks.size());
	}

	private CommandDetails reverseIncomplete(int ID) {
		CommandDetails incompleteToComplete;
		return incompleteToComplete = new CommandDetails(CommandDetails.COMMANDS.COMPLETE, tasks.size());
	}

}