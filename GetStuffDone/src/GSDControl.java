import java.text.ParseException;
import java.util.*;

/*
 * GSDControl deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * GSDControl knows the existence of Parser, Task, History and Storage
 * GSDControl does not know the existence of UI
 */

public class GSDControl {

	private static final String TASK_NOT_FOUND = "Task was not found";
	private static final String NO_TASKS = "No tasks recorded";
	private static final String FEEDBACK_ADD = "ADDED ";
	private static final String FEEDBACK_SEARCH = "SEARCH for ";
	private static final String FEEDBACK_UPDATE = "UPDATED ";
	private static final String FEEDBACK_DELETE = "DELETED ";
	private static final String FEEDBACK_COMPLETE = "COMPLETED ";
	private static final String FEEDBACK_INCOMPLETE = "INCOMPLETE ";
	private static final String FEEDBACK_UNDO = "Last action undone";
	private static final String FEEDBACK_REDO = "Last action redone";
	private static final String FEEDBACK_DISPLAY = "All tasks displayed";
	private static final String FEEDBACK_HELP = "Called for help!";
	private static final String FEEDBACK_INVALID_COMMAND = "Invalid Command";
	private static final String FEEDBACK_INVALID_TASK_NUMBER = "Invalid Task Number";
	public ArrayList<Task> tasks = new ArrayList<Task>();
	private Scanner sc = new Scanner(System.in);
	public CommandDetails commandDetails;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private History history = new History();

	public Feedback processInput(String input) throws IndexOutOfBoundsException {
		try {
			this.commandDetails = parser.parse(input);
		} catch (NumberFormatException e) {
			//invalid task number
		} catch (ParseException f) {
			//invalid date
		}
		Feedback feedback;
		switch (this.commandDetails.getCommand()) {
		case ADD:
			history.insert(this.commandDetails);
			// history.insert(reverseCommandDetails(this.commandDetails.getID()));
			return feedback = new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription());
		case DELETE:
			try {
				Task taskToDelete = tasks.get(this.commandDetails.getID() - 1);
				CommandDetails deletedDetails = new CommandDetails(CommandDetails.COMMANDS.DELETE,
						taskToDelete.getDescription(), taskToDelete.getVenue(), taskToDelete.getStartDate(),
						taskToDelete.getdeadline(), taskToDelete.getPriority(), this.commandDetails.getID() - 1);
				history.insert(deletedDetails);
				String taskDescription = tasks.get(commandDetails.getID() - 1).getDescription();
				return feedback = new Feedback(deleteTask(commandDetails.getID() - 1),
						FEEDBACK_DELETE + taskDescription);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException();
			} finally {
				return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
			}
		case SEARCH:
			return feedback = new Feedback(searchTask(), FEEDBACK_SEARCH + commandDetails.getDescription());
		case UPDATE:
			try {
				history.insert(reverseCommandDetails(this.commandDetails.getID() - 1));
				return feedback = new Feedback(updateTask(commandDetails.getID() - 1),
						FEEDBACK_UPDATE + commandDetails.getDescription());
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException();
			} finally {
				return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
			}
		case COMPLETE:
			try {
				history.insert(reverseCommandDetails(this.commandDetails.getID() - 1));
				return feedback = new Feedback(completeTask(commandDetails.getID() - 1),
						FEEDBACK_COMPLETE + commandDetails.getDescription());
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException();
			} finally {
				return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
			}
		case INCOMPLETE:
			try {
				history.insert(reverseCommandDetails(this.commandDetails.getID() - 1));
				return feedback = new Feedback(incompleteTask(commandDetails.getID() - 1),
						FEEDBACK_INCOMPLETE + commandDetails.getDescription());
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException();
			} finally {
				return feedback = new Feedback(displayAllTasks(), FEEDBACK_INVALID_TASK_NUMBER);
			}
		case REDO:
			return feedback = new Feedback(redoLastAction(), FEEDBACK_REDO);
		case UNDO:
			return feedback = new Feedback(undoLastAction(), FEEDBACK_UNDO);
		case DISPLAY:
			return feedback = new Feedback(displayAllTasks(), FEEDBACK_DISPLAY);
		case FLOATING:

		case HELP:
			return feedback = new Feedback(help(), FEEDBACK_HELP);
		case EXIT:

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
		storage.save(tasks);
		return displayAllTasks();
		// return ID+1 + " " + tasks.get(ID).toString();
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
		this.commandDetails = history.undo();
		System.out.println(commandDetails.toString());
		this.commandDetails = reverseCommandDetails(this.commandDetails.getID());
		System.out.println(commandDetails.toString());
		return executeHistoryCommand();
	}

	private String redoLastAction() {
		this.commandDetails = history.redo();
		System.out.println(commandDetails.toString());
		return executeHistoryCommand();
	}

	private String redoDeleteTask() {
		tasks.remove(tasks.size() - 1);
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
			return createTask();
		case DELETE:
			return redoDeleteTask();
		case UPDATE:
			return updateTask(commandDetails.getID() - 1);
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
			return reverseUpdate(ID);
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
				this.commandDetails.getDescription(), this.commandDetails.getVenue(),
				this.commandDetails.getStartDate(), this.commandDetails.getDeadline(),
				this.commandDetails.getPriority(), this.commandDetails.getID());
		return taskDelete;
	}

	private CommandDetails reverseUpdate(int ID) {
		Task taskToUpdate = tasks.get(ID);
		CommandDetails unUpdate;
		return unUpdate = new CommandDetails(CommandDetails.COMMANDS.UPDATE, taskToUpdate.getDescription(),
				taskToUpdate.getVenue(), taskToUpdate.getStartDate(), taskToUpdate.getdeadline(),
				taskToUpdate.getPriority(), ID);
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