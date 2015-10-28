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
	private String recurring;
	private Date originalStartDate;
	private Date originalDeadline;
	private Date endingDate;
	private int recurringCount;
	private boolean isComplete;

	// Constructors

	// Default
	public Task() {
		description = null;
		startDate = null;
		deadline = null;
		recurring = null;
		originalStartDate = null;
		originalDeadline = null;
		endingDate = null;
		recurringCount = 1;
		isComplete = false;

	}

	public Task(CommandDetails details) {
		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.deadline = details.getDeadline();
		this.recurring = details.getRecurring();
		this.originalStartDate = details.getOriginalStartDate();
		this.originalDeadline = details.getOriginalDeadline();
		this.endingDate = details.getEndingDate();
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

	public void setRecurring(String recurring) {
		this.recurring = recurring;
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
	
	public void setRecurringCount(int recurringCount)	{
		this.recurringCount = recurringCount;
	}

	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
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
	
	public int getRecurringCount()	{
		return this.recurringCount;
	}

	public boolean isComplete() {
		return this.isComplete;
	}

	public boolean isEvent() {
		return (this.startDate != null && this.deadline != null && this.endingDate == null);
	}

	public boolean isDeadline() {
		return (this.startDate == null && this.deadline != null);
	}

	public boolean isFloating() {
		return (this.startDate == null && this.deadline == null);
	}

	public boolean isRecurring() {
		return (this.recurring != null && this.endingDate != null);
	}

	// Behavioural methods

	public void updateDetails(CommandDetails details) {

		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.deadline = details.getDeadline();
		this.recurring = details.getRecurring();
		this.originalStartDate = details.getOriginalStartDate();
		this.originalDeadline = details.getOriginalDeadline();
		this.endingDate = details.getEndingDate();

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
		if (this.startDate != null) {
			startDateCal.setTime(this.startDate);
		}

		Calendar deadlineCal = Calendar.getInstance();
		if (this.deadline != null) {
			deadlineCal.setTime(this.deadline);
		}

		Calendar taskStartDateCal = Calendar.getInstance();
		if (details.getStartDate() != null) {
			taskStartDateCal.setTime(details.getStartDate());
		}

		Calendar taskDeadlineCal = Calendar.getInstance();
		if (details.getDeadline() != null) {
			taskDeadlineCal.setTime(details.getDeadline());
		}

		if (details.getDescription() != null && details.getDescription().contains(this.description)) {
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

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("hh:mma dd/MMM/yyyy ");

		String start, end, ending;

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
		if (endingDate == null) {
			ending = "";
		} else {
			ending = df.format(endingDate);
		}
		if (!isRecurring()) {
			return (description + "\nStart Date: " + start + "\nDeadline: " + end + "\n");
		} else {
			return (description + "\nStart Date: " + start + "\nDeadline: " + end + "\n" + "Recurring " + recurring
					+ "\nEnding Date: " + ending + "\n");
		}
	}
}