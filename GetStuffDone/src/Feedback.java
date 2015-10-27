
public class Feedback {
	private String displayString = null;
	private String feedbackString = null;
	private String infoString = null;
	private String helpCommandString = null;
	private String helpSyntaxString = null;

	public Feedback(String displayString, String feedbackString, String infoString) {
		this.displayString = displayString;
		this.feedbackString = feedbackString;
		this.infoString = infoString;
	}
	
	public Feedback(String displayString, String feedbackString, String infoString, String helpCommandString, String helpSyntaxString)	{
		this.displayString = displayString;
		this.feedbackString = feedbackString;
		this.infoString = infoString;
		this.helpCommandString = helpCommandString;
		this.helpSyntaxString = helpSyntaxString;
	}

	// Mutators

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}

	public void setFeedbackString(String feedbackString) {
		this.feedbackString = feedbackString;
	}

	public void setInfoString(String infoString) {
		this.infoString = infoString;
	}

	// Accessors

	public String getDisplayString() {
		return displayString;
	}

	public String getFeedbackString() {
		return feedbackString;
	}

	public String getInfoString() {
		return infoString;
	}
	
	public String getHelpSyntaxString() {
		return helpSyntaxString;
	}

	public String getHelpCommandString() {
		return helpCommandString;
	}
}
