package task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import commandDetail.CommandDetails;

/*
 * Task is an object used in GetStuffDone
 * Task does not know the existence of UI, GSDControl, Parser, History and Storage
 * Task knows the existence of commandDetails object
 */

public class Task implements Comparable<Task> {

	private String description;
	private Date startDate;
	private Date deadline;
	private Boolean isComplete;

	// Constructors

	// Default
	public Task() {
		description = null;
		startDate = null;
		deadline = null;
		isComplete = new Boolean(false);

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

	public Boolean isComplete() {
		return this.isComplete;
	}

	public boolean isEvent() {
		return (this.startDate != null && this.deadline != null);
	}

	public boolean isDeadline() {
		return (this.startDate == null && this.deadline != null);
	}

	public boolean isFloating() {
		return (this.startDate == null && this.deadline == null);
	}

	// Behavioural methods

	public void updateDetails(CommandDetails details) {

		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.deadline = details.getDeadline();
	}

	public void setAs(Task task) {

		this.description = task.getDescription();
		this.startDate = task.getStartDate();
		this.deadline = task.getDeadline();
	}

	public void markAsComplete() {
		isComplete = Boolean.TRUE;
	}

	public void markAsIncomplete() {
		isComplete = Boolean.FALSE;
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

		System.out.println("Start: " + taskStartDateCal.getTime() + "\nDeadline: " + taskDeadlineCal.getTime());
		System.out.println("Details : " + details.getStartDate() + "\n" + details.getDeadline());

		if (details.getDescription() != null && this.description.contains(details.getDescription())) {
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
		if (isFloating()) {
			return (description + "\nStart Date: -\nDeadline: -\n");
		} else if (isEvent()) {
			return (description + "\nStart Date: " + start + "\nDeadline: " + end + "\n");
		} else if (isDeadline()) {
			return description + "\nStart Date: -\nDeadline: " + end + "\n";
		}
		return null;
	}

	@Override
	public int compareTo(Task o) {
		if (isFloating() == true && o.isFloating() == true) {
			return 0;
		}
		if (isFloating() == true && o.isFloating() == false) {
			return 1;
		}
		if (isFloating() == false && o.isFloating() == true) {
			return -1;
		}
		return getDeadline().compareTo(o.getDeadline());
	}

	public boolean matches(Task o) {
		return (getDescription() == o.getDescription() && getStartDate() == o.getStartDate()
				&& getDeadline() == o.getDeadline() && isComplete() == o.isComplete());
	}
}
