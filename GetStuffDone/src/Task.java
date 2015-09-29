import java.util.Date;

/*
 * Task is an object used in GetStuffDone
 * Task does not know the existence of UI, Logic, Parser, History and Storage
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
	
	//Fully initialised task
	public Task(String description, Date startDate, Date endDate, String venue, String priority)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venue = venue;
		this.priority = priority;
	}
	
	//Task without venue
	public Task(String description, Date startDate, Date endDate, String priority)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venue = null;
		this.priority = priority;
	}
	
	//Task without priority
	public Task(String description, Date startDate, Date endDate, String venue)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venue = venue;
		this.priority = null;
	}

	//Task without venue and priority
	public Task(String description, Date startDate, Date endDate)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venue = null;
		this.priority = null;
	}

	
	//Floating task
	public Task(String description, Date startDate, String venue, String priority)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = null;
		this.venue = venue;
		this.priority = priority;
	}
	
	//Floating Task with no venue
	public Task(String description, Date startDate, String priority)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = null;
		this.venue = null;
		this.priority = priority;
	}
	
	//Floating Task with no priority
	public Task(String description, Date startDate, String venue)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = null;
		this.venue = venue;
		this.priority = null;
	}
	
	//Floating Task with no venue and priority
	public Task(String description, Date startDate)	{
		this.description = description;
		this.startDate = startDate;
		this.endDate = null;
		this.venue = null;
		this.priority = null;
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

