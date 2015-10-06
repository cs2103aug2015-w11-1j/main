import java.util.ArrayList;

public class Storage {
	
	public Storage(String fileName)	{
		//create file
	}
	
	public void save(ArrayList<Task> tasks)	{
		
		for(int i = 0; i<tasks.size(); i++)	{
			tasks.get(i).toString();
			//write to file
		}
		
	}
	
	public ArrayList<Task> load(){
		ArrayList<Task> tasks = new ArrayList<Task();
		while(file has nextline)	{
				Task task = new task();
				task.setDescription() = sc.nextLine();
				task.setStartDate() = sc.nextLine();
				task.setDeadline() = sc.nextLine();
				task.setVenue() = sc.nextLine();
				task.setPriority() = sc.nextLine();
				tasks.add(task);
		}
	}

}
