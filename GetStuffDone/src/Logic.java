import java.util.*;


/*
 * Logic deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * Logic knows the existence of Parser, Task, History and Storage
 * Logic does not know the existence of UI
 */

public class Logic {

	private static final String TASK_NOT_FOUND = "Task was not found";
	private static final String DEFAULT_FILE_NAME = "mytextfile.txt";
	private static final String FEEDBACK_ADD = "ADDED ";
	private static final String FEEDBACK_SEARCH = "SEARCH for ";
	private static final String FEEDBACK_UPDATE = "UPDATED ";
	private static final String FEEDBACK_DELETE = "DELETED ";
	public ArrayList<Task> tasks = new ArrayList<Task>();
	public static ArrayList<Integer> tasksInAction = new ArrayList<Integer>();
	public CommandDetails commandDetails;
	private Scanner sc;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private History history = new History();
	
	public Feedback processInput(String input)	{
		this.commandDetails = Parser.parse(input);
		Feedback feedback;
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return feedback = new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription());
		case DELETE:
			return feedback = new Feedback(deleteTask(), FEEDBACK_DELETE + commandDetails.getDescription());
		case SEARCH:
			return feedback = new Feedback(searchTask(), FEEDBACK_SEARCH + commandDetails.getDescription());
		case UPDATE:
			return feedback = new Feedback(updateTask(), FEEDBACK_UPDATE + commandDetails.getDescription());
		case DONE:
			
		case HELP:
			
		case REDO:
			
		case UNDO:
			
		default:
			return feedback = new Feedback(null, null);
			
		}
	}

	//Constructor

	public Logic()	{
		
	}

	//Behavioural Methods

	private String createTask()	{
		Task task = new Task(this.commandDetails);
		tasks.add(task);
		return tasks.size() + " " + task.toString();
	}

	//Print task according to the given commandDetails
	private String searchTask()	{
		String display=null;

		for(int i = 0; i < tasks.size(); i++)	{
			if(tasks.get(i).contains(commandDetails))	{
				display.concat(i + tasks.get(i).toString());
			}
		}

		if(display == null)	{	
			display = TASK_NOT_FOUND;
		}
		return display;
	}

	private String updateTask()	{
		int index= 0;
		for(int i = 0; i < tasks.size(); i++)	{
			if(tasks.get(i).contains(commandDetails))	{
				index = i;
				break;
			}
		}
		Task task = new Task(this.commandDetails);
		tasks.set(index, task);
		return index+1 + " " + task.toString();
	}

	private String deleteTask()	{
		for(int i = 0; i < tasks.size(); i++)	{
			if(tasks.get(i).contains(commandDetails))	{
				tasks.remove(i);
				}
		}
		return null;
	}

}