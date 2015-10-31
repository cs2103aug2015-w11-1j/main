package parser;

public class InvalidTimeDateInputException extends Exception {

	/**
	 * Throws when Time/Date input is not supported
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTimeDateInputException() {
	}

	public InvalidTimeDateInputException(String message) {
		super(message);
	}
}
