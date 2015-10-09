import java.util.Date;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String venue, priority, description;
	private COMMANDS command;
	private int ID;
	private boolean hasSearchTIme;
	
	public enum COMMANDS {
		ADD, DELETE, SEARCH, UPDATE, DONE, UNDO, REDO, HELP,DISPLAY
	}

	//constructor
	public CommandDetails(COMMANDS command, String description, String venue, Date startDate, Date deadline,
			String priority, int ID, boolean containSearchTime) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.venue = venue;
		this.priority = priority;
		this.description = description;
		this.command = command;
		this.ID = ID;
		this.hasSearchTIme = containSearchTime;
	}
	
	public COMMANDS getCommand()	{
		return this.command;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public String getVenue() {
		return this.venue;
	}

	public String getPriority() {
		return this.priority;
	}

	public String getDescription() {
		return this.description;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public boolean getHasSearchTime(){
		return this.hasSearchTIme;
	}
	
	public String toString(){
		String result = "";
		result= "command = " + command + "\n" + 
				"ID = " + ID + "\n" +
				"description = " + description + "\n" +
				"venue = " + venue + "\n" +
				"startDate = " + startDate + "\n" +
				"deadline = " + deadline + "\n" +
				"priority = " + priority +"\n" +
				"Search time = " + hasSearchTIme;
		return result;
	}
}