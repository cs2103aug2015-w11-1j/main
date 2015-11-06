package parser;

//@@author A0124472L

public class InvalidParametersException extends Exception {

	/*
	 * Throws when parameters are missing Time of recurring task is missing Task
	 * ID is missing when Command type requires ID
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParametersException() {
	}

	public InvalidParametersException(String message) {
		super(message);
	}

}