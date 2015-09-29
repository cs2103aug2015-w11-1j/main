import java.util.*;

/*
 * Logic deals with handling of input commands, CRUD of tasks, update of History and update of Storage
 * Logic knows the existence of Parser, Task, History and Storage
 * Logic does not know the existence of UI
 */

public class Logic {
	
	private static final String TASK_NOT_FOUND = "Task was not found";
	public static int taskCount = 0;
	public static ArrayList<Task> tasks = new ArrayList<Task>();
	public static ArrayList<Integer> tasksInAction = new ArrayList<Integer>();
	public commandDetails;
	private String input;
	private Scanner sc;
	
	public void main()	{
		sc = new Scanner(System.in);
		Logic lg = new Logic();
		while(true)	{
			lg.readInput();
		}
	}
	
	//Constructor
	
	public Logic()	{
		this.input = null;
		this.commandDetails = getCommandDetails();
	}
	
	//Behavioural Methods
	
	public void readInput()	{
		this.input = sc.nextLine();
	}
	
	public void getCommandDetails()	{
		this.commandDetails = Parser.parse(); 
	}
	
	public void createTask()	{
		Task task = new Task(commandDetails);
		tasks.add(task);
		taskCount++;
	}
	
	//Print task according to the given commandDetails
	public void readTask()	{
		
		this.searchTask();
		
		if(tasksInAction.isEmpty())	{	
			return TASK_NOT_FOUND;
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
		for(int i = 0; i < taskCount; i++)	{
			if(tasks.get(i).contains(commandDetails))	{
				tasksInAction.add(i);
			}
		}
	}
	
	public void updateTask()	{
		
		this.searchTask();
		
		if(tasksInAction.isEmpty())	{	
			System.out.println(TASK_NOT_FOUND);
		}
		else	{
			tasks.get(tasksInAction.get(i)).updateDetails(commandDetails);
		}
	}
	
	public void deleteTask()	{
		
		this.searchTask();
		
		if(tasksInAction.isEmpty())	{	
			System.out.println(TASK_NOT_FOUND);
		}
		else	{
			tasks.remove(tasksInAction.get(i));
		}
	}

}