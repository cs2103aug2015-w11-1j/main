package commandDetail;

import java.util.Date;

import task.Task;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String description;
	private COMMANDS command;
	private int ID;
	private Task oldTask;
	private Task newTask;

	public enum COMMANDS {
		ADD, DELETE, SEARCH, UPDATE, COMPLETE, INCOMPLETE, UNDO, REDO, HELP, ALL, FLOATING, EVENTS, DEADLINES, EXIT, INVALID, SET,
	}

	// constructors
	public CommandDetails(COMMANDS command, String description, Date startDate, Date deadline, int ID) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.description = description;
		this.command = command;
		this.ID = ID;

	}
	
	public CommandDetails(COMMANDS command, String description, Date startDate, Date deadline, int ID, Task newTask) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.description = description;
		this.command = command;
		this.ID = ID;
		this.newTask = newTask;
	}
	
	public CommandDetails(COMMANDS command, String description, Date startDate, Date deadline, int ID,
			Date originalStartDate, Date originalDeadline, Date endingDate, Task oldTask, Task newTask) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.description = description;
		this.command = command;
		this.ID = ID;
		this.oldTask = oldTask;
		this.newTask = newTask;
	}

	public CommandDetails(COMMANDS command, int ID) {
		this.deadline = null;
		this.startDate = null;
		this.description = null;
		this.command = command;
		this.ID = ID;
	}

	// Accessors
	
	public COMMANDS getCommand() {
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
	
	public Task getOldTask() {
		return this.oldTask;
	}
	
	public Task getNewTask() {
		return this.newTask;
	}

	// Mutators

	public void setCommand(COMMANDS command) {
		this.command = command;
	}

	public Date setDeadline(Date deadline) {
		return this.deadline = deadline;
	}

	public Date setStartDate(Date startDate) {
		return this.startDate = startDate;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setNewTask(Task newTask) {
		this.newTask = newTask;
	}
	
	public void setOldTask(Task oldTask)	{
		this.oldTask = oldTask;
	}

	// Overriding methods
	@Override
	public String toString() {
		String result = "";
		result = "command = " + command + "\nID = " + ID + "\ndescription = " + description + "\nstartDate = "
				+ startDate + "\ndeadline = " + deadline + "\n";
		return result;
	}
}