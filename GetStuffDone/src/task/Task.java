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
	private String recurring;
	private Date originalStartDate;
	private Date originalDeadline;
	private Date endingDate;
	private int recurringCount;
	private Boolean isComplete;

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
		isComplete = new Boolean(false);

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

	public void incrementRecurringCount() {
		this.recurringCount++;
	}

	public void resetRecurringCount() {
		this.recurringCount = 1;
	}

	public void setRecurringCount(int recurringCount) {
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

	public int getRecurringCount() {
		return this.recurringCount;
	}

	public Boolean isComplete() {
		return this.isComplete;
	}

	public boolean isEvent() {
		return (this.startDate != null && this.deadline != null && this.endingDate == null);
	}

	public boolean isDeadline() {
		return (this.startDate == null && this.deadline != null && this.endingDate == null);
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

	public void setAs(Task task) {

		this.description = task.getDescription();
		this.startDate = task.getStartDate();
		this.deadline = task.getDeadline();
		this.recurring = task.getRecurring();
		this.originalStartDate = task.getOriginalStartDate();
		this.originalDeadline = task.getOriginalDeadline();
		this.endingDate = task.getEndingDate();
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
		if (isFloating()) {
			return (description + "\nStart Date: -\nDeadline: -\n");
		} else if (isEvent()) {
			return (description + "\nStart Date: " + start + "\nDeadline: " + end + "\n");
		} else if (isDeadline()) {
			return description + "\nStart Date: -\nDeadline: " + end + "\n";
		} else if (isRecurring()) {
			return (description + "\nStart Date: " + start + "\nDeadline: " + end + "\n" + "Recurring " + recurring
					+ "\nEnding Date: " + ending + "\n");
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
				&& getDeadline() == o.getDeadline() && getRecurring() == o.getRecurring()
				&& getOriginalStartDate() == o.getOriginalStartDate()
				&& getOriginalDeadline() == o.getOriginalDeadline() && getEndingDate() == o.getEndingDate()
				&& isComplete() == o.isComplete());
	}
}
