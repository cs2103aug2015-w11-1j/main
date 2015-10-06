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
	private static final String FEEDBACK_SEARCH = "";
	private static final String FEEDBACK_UPDATE = "";
	private static final String FEEDBACK_DELETE = "";
	public ArrayList<Task> tasks = new ArrayList<Task>();
	public static ArrayList<Integer> tasksInAction = new ArrayList<Integer>();
	public CommandDetails commandDetails;
	private Scanner sc;
	private Parser parser = new Parser();
	private Storage storage = new Storage(DEFAULT_FILE_NAME);
	private History history = new History();
	
	public Feedback processInput(String input)	{
		this.commandDetails = Parser.parse(input);
		Feedback feedback;
		switch (this.commandDetails.getCommand()) {
		case ADD:
			return feedback = new Feedback(createTask(), FEEDBACK_ADD + commandDetails.getDescription());
			break;
		case DELETE:
			deleteTask();
			
		case SEARCH:
			return feedback = new Feedback(searchTask(), FEEDBACK_SEARCH + commandDetails.getDescription());
			break;
		case DONE:
			
		case HELP:
			
		case REDO:
			
		case UNDO:
			
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

	private void updateTask()	{

		this.searchTask();
		int i = 0;

		if(tasksInAction.isEmpty())	{	
			System.out.println(TASK_NOT_FOUND);
		}
		else	{
			tasks.get(tasksInAction.get(i)).updateDetails(commandDetails);
		}
	}

	private void deleteTask()	{

		this.searchTask();
		int i = 0;

		if(tasksInAction.isEmpty())	{	
			System.out.println(TASK_NOT_FOUND);
		}
		else	{
			tasks.remove(tasksInAction.get(i));
		}
	}

}