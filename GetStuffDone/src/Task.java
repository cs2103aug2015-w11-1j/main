import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Task is an object used in GetStuffDone
 * Task does not know the existence of UI, GSDControl, Parser, History and Storage
 * Task knows the existence of commandDetails object
 */

public class Task {

	private String description;
	private Date startDate;
	private Date deadline;
	private boolean isComplete;
	private boolean isEvent;
	private boolean isDeadline;
	private boolean isFloating;
	// private boolean isRecurring;

	// Constructors

	// Default
	public Task() {
		description = null;
		startDate = null;
		deadline = null;
		isComplete = false;
		isEvent = false;
		isDeadline = false;
		isFloating = false;
	}

	public Task(CommandDetails details) {
		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.deadline = details.getDeadline();
		this.isComplete = false;
	}

	// Mutators

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public void setIsEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	public void setIsDeadline(boolean isDeadline) {
		this.isDeadline = isDeadline;
	}

	public void setIsFloating(boolean isFloating) {
		this.isFloating = isFloating;
	}

	// Accessors

	public String getDescription() {
		return this.description;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public boolean getIsComplete() {
		return this.isComplete;
	}

	public boolean getIsEvent() {
		return this.isEvent;
	}

	public boolean getIsDeadline() {
		return this.isDeadline;
	}

	public boolean getIsFloating() {
		return this.isFloating;
	}

	// Behavioural methods

	public void updateDetails(CommandDetails details) {

		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.deadline = details.getDeadline();

	}

	public void markAsComplete() {
		isComplete = true;
	}

	public void markAsIncomplete() {
		isComplete = false;
	}
	// Overriding methods

	public boolean contains(CommandDetails details) {

		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(this.startDate);

		Calendar deadlineCal = Calendar.getInstance();
		deadlineCal.setTime(this.deadline);

		Calendar taskStartDateCal = Calendar.getInstance();
		taskStartDateCal.setTime(details.getStartDate());

		Calendar taskDeadlineCal = Calendar.getInstance();
		taskDeadlineCal.setTime(details.getDeadline());

		if (details.getDescription().contains(this.description)) {
			return true;
		}

		if (details.getStartDate() != null) {
			if (taskStartDateCal.get(Calendar.DAY_OF_YEAR) == (startDateCal.get(Calendar.DAY_OF_YEAR))) {
				return true;
			}
		}

		if (details.getDeadline() != null) {
			if (taskDeadlineCal.get(Calendar.DAY_OF_YEAR) == (deadlineCal.get(Calendar.DAY_OF_YEAR))) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		DateFormat df = new SimpleDateFormat("hh:mma dd/MMM/yyyy ");

		String start, end;

		if (startDate == null) {
			start = "";
		} else {
			start = df.format(startDate);
		}

		if (deadline == null) {
			end = "";
		} else {
			end = df.format(deadline);
		}
		return (description + "\nStart Date: " + start + "\nDeadline: " + end + "\n");
	}
}