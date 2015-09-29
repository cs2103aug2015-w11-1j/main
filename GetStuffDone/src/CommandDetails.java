import java.util.Date;

public class CommandDetails {
		private Date deadline;
		private Date startDate;
		private String venue, priority, description;
		
		public CommandDetails() {
			this.deadline = null;
			this.startDate = null;
			this.venue = null;
			this.priority = null;
			this.description = null;
		}
		
		public CommandDetails(Date deadline, Date startDate, String venue, String priority, String description) {
			this.deadline = deadline;
			this.startDate = startDate;
			this.venue = venue;
			this.priority = priority;
			this.description = description;
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