import java.util.*;

/*
 * Logic deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * Logic knows the existence of Parser, Task, History and Storage
 * Logic does not know the existence of UI
 */

public class Logic {

	private static final String TASK_NOT_FOUND = "Task was not found";
	private static final String DEFAULT_FILE_NAME = "mytextfile.txt";
	public static ArrayList<Task> tasks = new ArrayList<Task>();
	public static ArrayList<Integer> tasksInAction = new ArrayList<Integer>();
	public CommandDetails commandDetails;
	private Scanner sc;
	private Parser parser = new Parser();
	private Storage storage = new Storage(DEFAULT_FILE_NAME);
	private History history = new History();
	
	public String processInput(String input)	{
		CommandDetails commandDetails = Parser.parse(input);
		Feedback feedback = new Feedback();
	}

	//Constructor

	public Logic()	{
		
	}

	//Behavioural Methods

	public void createTask()	{
		Task task = new Task(this.commandDetails);
		tasks.add(task);
	}

	//Print task according to the given commandDetails
	public void readTask()	{

		this.searchTask();

		if(tasksInAction.isEmpty())	{	
			System.out.println(TASK_NOT_FOUND);
		}
		else	{
			for(int i = 0; i < tasksInAction.size(); i++)	{
				tasks.get(tasksInAction.get(i)).toString();
			}
		}
		tasksInAction.clear();
	}

	//Records the index of found tasks in tasksInAction
	public void searchTask()	{
		for(int i = 0; i < tasks.size(); i++)	{
			if(tasks.get(i).contains(commandDetails))	{
				tasksInAction.add(i);
			}
		}
	}

	public void updateTask()	{

		this.searchTask();
		int i = 0;

		if(tasksInAction.isEmpty())	{	
			System.out.println(TASK_NOT_FOUND);
		}
		else	{
			tasks.get(tasksInAction.get(i)).updateDetails(commandDetails);
		}
	}

	public void deleteTask()	{

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