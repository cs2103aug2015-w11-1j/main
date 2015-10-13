import java.util.*;

/*
 * GSDControl deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * GSDControl knows the existence of Parser, Task, History and Storage
 * GSDControl does not know the existence of UI
 */

public class GSDControl {

	private static final String TASK_NOT_FOUND = "Task was not found";
	private static final String DEFAULT_FILE_NAME = "mytextfile.txt";
	private static final String FEEDBACK_ADD = "ADDED ";
	private static final String FEEDBACK_SEARCH = "SEARCH for ";
	private static final String FEEDBACK_UPDATE = "UPDATED ";
	private static final String FEEDBACK_DELETE = "DELETED ";
	private static final String FEEDBACK_DONE = "DONE ";
	private static final String FEEDBACK_UNDO = "Last action undone";
	public ArrayList<Task> tasks = new ArrayList<Task>();
	public CommandDetails commandDetails;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private History history = new History();
	
	public Feedback processInput(String input)	{
		this.commandDetails = parser.parse(input);
		Feedback feedback;
		switch (this.commandDetails.getCommand()) {
		case ADD:
			history.insert(reverseCommandDetails(this.commandDetails.getID()));
			return feedback = new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription());
		case DELETE:
			history.insert(reverseCommandDetails(this.commandDetails.getID()-1));
			return feedback = new Feedback(deleteTask(commandDetails.getID()-1), FEEDBACK_DELETE + commandDetails.getDescription());
		case SEARCH:
			return feedback = new Feedback(searchTask(), FEEDBACK_SEARCH + commandDetails.getDescription());
		case UPDATE:
			history.insert(reverseCommandDetails(this.commandDetails.getID()-1));
			return feedback = new Feedback(updateTask(commandDetails.getID()-1), FEEDBACK_UPDATE + commandDetails.getDescription());
		case DONE:
			history.insert(reverseCommandDetails(this.commandDetails.getID()-1));
			return feedback = new Feedback(doneTask(commandDetails.getID()-1), FEEDBACK_DONE + commandDetails.getDescription());
		case HELP:
			
		case REDO:
			
		case UNDO:
			return feedback = new Feedback(undoLastAction(), FEEDBACK_UNDO);
		default:
			return feedback = new Feedback(null, null);
			
		}
	}

	//Constructor

	public GSDControl()	{
		
	}

	//Behavioural Methods

	private String createTask()	{
		Task task = new Task(this.commandDetails);
		tasks.add(task);
		storage.save(tasks);
		return tasks.size() + " " + task.toString();
	}

	//Print task according to the given commandDetails
	private String searchTask()	{
		String display="";

		for(int i = 0; i < tasks.size(); i++)	{
			if(tasks.get(i).getDescription().contains(commandDetails.getDescription()))	{
				display += i+1 + " " + tasks.get(i).toString();
			}
		}

		if(display.isEmpty())	{	
			display = TASK_NOT_FOUND;
		}
		return display;
	}

	private String updateTask(int ID)	{
		tasks.get(ID).updateDetails(commandDetails);
		storage.save(tasks);
		return ID+1 + " " + tasks.get(ID).toString();
	}

	private String deleteTask(int ID)	{
		tasks.remove(ID);
		storage.save(tasks);
		return null;
	}
	
	private String doneTask(int ID)	{
		tasks.get(ID).markAsDone();
		storage.save(tasks);
		return null;
	}
	
	private String undoneTask(int ID)	{
		tasks.get(ID).markAsUndone();
		storage.save(tasks);;
		return null;
	}
	
	private String undoLastAction()	{
		this.commandDetails = history.undo();
		switch (this.commandDetails.getCommand())	{
		case ADD:
			return createTask();
		case DELETE:
			return deleteTask(commandDetails.getID()-1);
		case UPDATE:
			return updateTask(commandDetails.getID()-1);
		case DONE:
			return doneTask(commandDetails.getID()-1);
		case UNDONE:
			return undoneTask(commandDetails.getID()-1);
		default:
			return null;
		}
	}
	
	private CommandDetails reverseCommandDetails(int ID)	{
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return reverseAdd();
		case DELETE:
			return reverseDelete(ID);
		case UPDATE:
			return reverseUpdate(ID);
		case DONE:
			return reverseDone(ID);
		default:
			return commandDetails;
			
		}
	}
	 
	private CommandDetails reverseAdd()	{
		CommandDetails addToDelete;
		return addToDelete = new CommandDetails(CommandDetails.COMMANDS.DELETE, tasks.size());
	}
	
	private CommandDetails reverseDelete(int ID)	{
		Task taskToDelete = tasks.get(ID);
		int dummyID = (Integer) null;
		CommandDetails deleteToAdd;
		return deleteToAdd = new CommandDetails(CommandDetails.COMMANDS.ADD, taskToDelete.getDescription(), 
												taskToDelete.getVenue(), taskToDelete.getStartDate(),
												taskToDelete.getdeadline(), taskToDelete.getPriority(),
												dummyID);
	}
	
	private CommandDetails reverseUpdate(int ID)	{
		Task taskToUpdate = tasks.get(ID);
		CommandDetails unUpdate;
		return unUpdate = new CommandDetails(CommandDetails.COMMANDS.UPDATE, taskToUpdate.getDescription(),
											taskToUpdate.getVenue(), taskToUpdate.getStartDate(),
											taskToUpdate.getdeadline(), taskToUpdate.getPriority(),
											ID);
	}
	
	private CommandDetails reverseDone(int ID)	{
		CommandDetails doneToUndone;
		return doneToUndone = new CommandDetails(CommandDetails.COMMANDS.UNDONE, tasks.size());
	}
	
}