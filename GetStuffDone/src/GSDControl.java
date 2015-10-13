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
	public ArrayList<Task> tasks = new ArrayList<Task>();
	public static ArrayList<Integer> tasksInAction = new ArrayList<Integer>();
	public CommandDetails commandDetails;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private History history = new History();
	
	public Feedback processInput(String input)	{
		this.commandDetails = parser.parse(input);
		Feedback feedback;
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return feedback = new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription());
		case DELETE:
			return feedback = new Feedback(deleteTask(commandDetails.getID()-1), FEEDBACK_DELETE + commandDetails.getDescription());
		case SEARCH:
			return feedback = new Feedback(searchTask(), FEEDBACK_SEARCH + commandDetails.getDescription());
		case UPDATE:
			return feedback = new Feedback(updateTask(commandDetails.getID()-1), FEEDBACK_UPDATE + commandDetails.getDescription());
		case DONE:
			
		case HELP:
			
		case REDO:
			
		case UNDO:
			
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

	private String updateTask(int index)	{
		//Task task = null;
		tasks.get(index).updateDetails(commandDetails);
		return index+1 + " " + tasks.get(index).toString();
	}

	private String deleteTask(int index)	{
		tasks.remove(index);
		return null;
	}

}