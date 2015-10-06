import java.util.Date;

public class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String venue, priority, description;
	private Parser.COMMANDS commandType;

	public enum COMMANDS {
		ADD, DELETE, DONE, UNDO, REDO, SEARCH, HELP,
	}

	//constructor
	public CommandDetails(Parser.COMMANDS command, String description, String venue, Date startDate, Date deadline,
			String priority) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.venue = venue;
		this.priority = priority;
		this.description = description;
		this.commandType = command;
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