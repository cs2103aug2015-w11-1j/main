package parser;

public class InvalidCommandException extends Exception {

	/**
	 * Throws when Command is invalid
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCommandException() {
	}

	public InvalidCommandException(String message) {
		super(message);
	}

}
