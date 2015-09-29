import java.util.Date;

/*
 * Task is an object used in GetStuffDone
 * Task does not know the existence of UI, Logic, Parser, History and Storage
 * Task knows the existence of commandDetails object
 */

public class Task {
	
	private String description;
	private Date startDate;
	private Date endDate;
	private String venue;
	private String priority;
	
	//Constructors
	
	//Default
	public Task()	{
		description = null;
		startDate = null;
		endDate = null;
		venue = null;
		priority = null;
	}
	
	public Task(commandDetails details)	{
		this.description = details.getDescription();
		this.startDate = details.getStartDate();
		this.endDate = details.getEndDate();
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
	
	public void setEndDate(Date endDate)	{
		this.endDate = endDate;
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
	
	public Date getEndDate()	{
		return this.endDate;
	}
	
	public String getVenue()	{
		return this.venue;
	}
	
	public String getPriority()	{
		return this.priority;
	}
	
	//Behavioural methods
	
	public void updateDetails(commandDetails details)	{
		
		if(details.getDescription() != null)	{
			this.description = details.getDescription();
		}
		
		if(details.getStartDate() != null)	{
			this.startDate = details.getStartDate();
		}
		
		if(details.getEndDate() != null)	{
			this.endDate = details.getEndDate();
		}
		
		if(details.getVenue() != null)	{
			this.venue = details.getVenue();
		}
		
		if(details.getPriority() != null){
			this.priority = details.getPriority();
		}
	}
	
	
	//Overriding methods
	
	public Task contains(commandDetails details)	{
		
		if(details.getDescription().equals(this.description))	{
			return this;
		}
		
		if(details.getStartDate().equals(this.startDate))	{
			return this;
		}
		
		if(details.getEndDate().equals(this.endDate))	{
			return this;
		}
		
		if(details.getVenue().equals(this.venue))	{
			return this;
		}
		
		if(details.getPriority().equals(this.priority))	{
			return this;
		}
		
		return null;
	}
	
	public String toString()	{
		return (description + " " + startDate + " " + endDate + " " + venue + " " + priority);
	}
}

