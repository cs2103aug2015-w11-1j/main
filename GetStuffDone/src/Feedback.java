
public class Feedback {
	private String displayString = null;
	private String feedbackString = null;
	private String infoString = null;

	public Feedback(String displayString, String feedbackString, String infoString) {
		this.displayString = displayString;
		this.feedbackString = feedbackString;
		this.infoString = infoString;
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
}
