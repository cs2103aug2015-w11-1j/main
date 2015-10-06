import java.util.Date;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String venue, priority, description;
	private COMMANDS commandType;

	public enum COMMANDS {
		ADD, DELETE, DONE, UNDO, REDO, SEARCH, HELP,
	}

	public CommandDetails(Parser.COMMANDS command, String description2, String venue2, Date start, Date end, String priority2) {
		this.deadline = null;
		this.startDate = null;
		this.venue = null;
		this.priority = null;
		this.description = null;
		this.commandType = null;
	}

	public CommandDetails(COMMANDS command, String description, String venue, Date startDate, Date deadline,
			String priority) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.venue = venue;
		this.priority = priority;
		this.description = description;
		this.commandType = command;
	}
	
	public COMMANDS getCommand()	{
		return this.commandType;
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
}