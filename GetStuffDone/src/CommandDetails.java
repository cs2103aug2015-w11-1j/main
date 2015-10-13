import java.util.Date;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String venue, priority, description;
	private COMMANDS command;
	private int ID;
	
	public enum COMMANDS {
		ADD, DELETE, SEARCH, UPDATE, COMPLETE, INCOMPLETE, UNDO, REDO, HELP, DISPLAY, FLOATING, EXIT, INVALID
	}

	//constructor
	public CommandDetails(COMMANDS command, String description, String venue, Date startDate, Date deadline,
			String priority, int ID) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.venue = venue;
		this.priority = priority;
		this.description = description;
		this.command = command;
		this.ID = ID;
	}
	
	public CommandDetails(COMMANDS command, int ID)	{
		this.deadline = null;
		this.startDate = null;
		this.venue = null;
		this.priority = null;
		this.description = null;
		this.command = command;
		this.ID = ID;
	}
	
	//Accessors
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
	
	//Mutators
	
	public void setCommand(COMMANDS command)	{
		this.command = command;
	}
	
	
	//Overriding methods
	public String toString(){
		String result = "";
		result= "command = " + command + "\n" + 
				"ID = " + ID + "\n" +
				"description = " + description + "\n" +
				"venue = " + venue + "\n" +
				"startDate = " + startDate + "\n" +
				"deadline = " + deadline + "\n" +
				"priority = " + priority +"\n";
		return result;
	}
}