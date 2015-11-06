package task;

//@@author A0110616W

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import commandDetail.CommandDetails;

/**
 * Task is a stand-alone object used in GetStuffDone
 * Task does not know the existence of UI, GSDControl, Parser, History and Storage
 * Task knows the existence of CommandDetails
 * 
 * INTERACTIONS OF Task WITH OTHER CLASSES:
 * 
 * GSDControl: An ArrayList of Tasks are stored in GSDControl
 * Storage: The ArrayList of Tasks from GSDControl is passed to Storage for saving and loading purposes
 * CommandDetails: A Task is created from a CommandDetails object
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

	public boolean matches(Task o) {
		return (getDescription() == o.getDescription() && getStartDate() == o.getStartDate()
				&& getDeadline() == o.getDeadline() && isComplete() == o.isComplete());
	}

	public boolean contains(CommandDetails details) {

		Calendar taskStartDateCal = null;
		if (this.startDate != null) {
			taskStartDateCal = Calendar.getInstance();
			taskStartDateCal.setTime(this.startDate);
		}

		Calendar taskDeadlineCal = null;
		if (this.deadline != null) {
			taskDeadlineCal = Calendar.getInstance();
			taskDeadlineCal.setTime(this.deadline);
		}

		Calendar detailsStartDateCal = null;
		if (details.getStartDate() != null) {
			detailsStartDateCal = Calendar.getInstance();
			detailsStartDateCal.setTime(details.getStartDate());
		}

		Calendar detailsDeadlineCal = null;
		if (details.getDeadline() != null) {
			detailsDeadlineCal = Calendar.getInstance();
			detailsDeadlineCal.setTime(details.getDeadline());
		}

		if (details.getDescription() != null && this.description.contains(details.getDescription())) {
			return true;
		}

		if (details.getStartDate() != null && taskStartDateCal != null) {
			if (taskStartDateCal.get(Calendar.DAY_OF_YEAR) == (detailsStartDateCal.get(Calendar.DAY_OF_YEAR))) {
				return true;
			}
		}

		if (details.getDeadline() != null && taskDeadlineCal != null) {
			if (taskDeadlineCal.get(Calendar.DAY_OF_YEAR) == (detailsDeadlineCal.get(Calendar.DAY_OF_YEAR))) {
				return true;
			}
		}
		return false;
	}

	// Overriding methods

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
}
