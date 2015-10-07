import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Task is an object used in GetStuffDone
 * Task does not know the existence of UI, Logic, Parser, History and Storage
 * Task knows the existence of commandDetails object
 */

public class Task {
	
	private String description;
	private Date startDate;
	private Date deadline;
	private String venue;
	private String priority;
	
	//Constructors
	
	//Default
	public Task()	{
		description = null;
		startDate = null;
		deadline = null;
		venue = null;
		priority = null;
	}
	
	public Task(CommandDetails details)	{
		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.deadline = details.getDeadline();
		this.venue = details.getVenue();
		this.priority = details.getPriority();
	}
	
	
	//Mutators
	
	public void setDescription(String description)	{
		this.description = description;
	}
	
	public void setStartDate(Date startDate)	{
		this.startDate = startDate;
	}
	
	public void setdeadline(Date deadline)	{
		this.deadline = deadline;
	}
	
	public void setVenue(String venue)	{
		this.venue = venue;
	}
	
	public void setPriority(String priority)	{
		this.priority = priority;
	}
	
	//Accessors
	
	public String getDescription()	{
		return this.description;
	}
	
	public Date getStartDate()	{
		return this.startDate;
	}
	
	public Date getdeadline()	{
		return this.deadline;
	}
	
	public String getVenue()	{
		return this.venue;
	}
	
	public String getPriority()	{
		return this.priority;
	}
	
	//Behavioural methods
	
	public void updateDetails(CommandDetails details)	{
		
		if(details.getDescription() != null)	{
			this.description = details.getDescription();
		}
		
		if(details.getStartDate() != null)	{
			this.startDate = details.getStartDate();
		}
		
		if(details.getDeadline() != null)	{
			this.deadline = details.getDeadline();
		}
		
		if(details.getVenue() != null)	{
			this.venue = details.getVenue();
		}
		
		if(details.getPriority() != null){
			this.priority = details.getPriority();
		}
	}
	
	
	//Overriding methods
	
	public boolean contains(CommandDetails details)	{
		
		if(details.getDescription().equals(this.description))	{
			return true;
		}
		
		if(details.getStartDate().equals(this.startDate))	{
			return true;
		}
		
		if(details.getDeadline().equals(this.deadline))	{
			return true;
		}
		
		if(details.getVenue().equals(this.venue))	{
			return true;
		}
		
		if(details.getPriority().equals(this.priority))	{
			return true;
		}
		
		return false;
	}
	
	public String toString()	{
		DateFormat df = new SimpleDateFormat("hh:mma dd/MMM/yyyy ");
		
		String start, end;
		
		if(startDate == null){
			start = "";
		}else {
			start = df.format(startDate);
		}
		
		if(deadline == null){
			end = "";
		}else{
			end = df.format(deadline);
		}
		return (description + "\n" + start + "\n" + end + "\n" + venue + "\n" + priority);
	}
}

