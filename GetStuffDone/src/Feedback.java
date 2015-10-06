
public class Feedback {
	private String displayString = null;
	private String feedbackString = null;
	
	public Feedback(String displayString, String feedbackString)	{
		this.displayString = displayString;
		this.feedbackString = feedbackString;
	}
	
	public String getDisplayString()	{
		return displayString;
	}
	
	public String getFeedbackString()	{
		return feedbackString;
	}
}
