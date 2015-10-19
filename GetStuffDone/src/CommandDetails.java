import java.util.Date;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String description;
	private COMMANDS command;
	private int ID;
	
	public enum COMMANDS {
		ADD, DELETE, SEARCH, UPDATE, COMPLETE, INCOMPLETE, UNDO, REDO, HELP, ALL, FLOATING, EVENTS, DEADLINES,
		EXIT, INVALID, SET,
	}

	//constructor
	public CommandDetails(COMMANDS command, String description, Date startDate, Date deadline, int ID) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.description = description;
		this.command = command;
		this.ID = ID;
	}
	
	public CommandDetails(COMMANDS command, int ID)	{
		this.deadline = null;
		this.startDate = null;
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
				"startDate = " + startDate + "\n" +
				"deadline = " + deadline + "\n";
		
		return result;
	}
}