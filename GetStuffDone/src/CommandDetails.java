import java.util.Date;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String description;
	private COMMANDS command;
	private int ID;
	private String recurring;
	private Date originalStartDate;
	private Date originalDeadline;
	private Date endingDate;

	public enum COMMANDS {
		ADD, DELETE, SEARCH, UPDATE, COMPLETE, INCOMPLETE, UNDO, REDO, HELP, ALL, FLOATING, EVENTS, DEADLINES, RECURRING, EXIT, INVALID, SET,
	}

	// constructor

	public CommandDetails(COMMANDS command, String description, Date startDate, Date deadline, int ID, String recurring,
			Date originalStartDate, Date originalDeadline, Date endingDate) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.description = description;
		this.command = command;
		this.ID = ID;
		this.recurring = recurring;
		this.originalStartDate = originalStartDate;
		this.originalDeadline = originalDeadline;
		this.endingDate = endingDate;
	}

	public CommandDetails(COMMANDS command, int ID) {
		this.deadline = null;
		this.startDate = null;
		this.description = null;
		this.command = command;
		this.ID = ID;
		this.recurring = null;
		this.endingDate = null;
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

	public String getRecurring() {
		return this.recurring;
	}

	public Date getOriginalStartDate() {
		return this.originalStartDate;
	}

	public Date getOriginalDeadline() {
		return this.originalDeadline;
	}

	public Date getEndingDate() {
		return this.endingDate;
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

	public void setOriginalStartDate(Date originalStartDate) {
		this.originalStartDate = originalStartDate;
	}

	public void setOriginalDeadline(Date originalDeadline) {
		this.originalDeadline = originalDeadline;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	// Overriding methods
	@Override
	public String toString() {
		String result = "";
		result = "command = " + command + "\nID = " + ID + "\ndescription = " + description + "\nstartDate = "
				+ startDate + "\ndeadline = " + deadline + "\nRecurring = " + recurring + "\noriginalStartDate = "
				+ originalStartDate + "\noriginalDeadline = " + originalDeadline + "\nEnding Date = " + endingDate
				+ "\n";
		return result;
	}
}