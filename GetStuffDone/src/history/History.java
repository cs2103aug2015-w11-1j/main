package history;

//@@author A0121834M

import java.util.Stack;

import commandDetail.CommandDetails;

/**
 * History returns first CommandDetails object when undo or redo is called
 * GSDControl works on the returned CommandDetails objects accordingly
 */


public class History {

	private Stack<CommandDetails> undoStack = new Stack<CommandDetails>();
	private Stack<CommandDetails> redoStack = new Stack<CommandDetails>();

	// Constructor
	public History() {
		this.undoStack = new Stack<CommandDetails>();
		this.redoStack = new Stack<CommandDetails>();
	}

	/**
	 * Inserts a CommandDetails object into the undoStack and clears Redo stack
	 * of obsolete commands. Returns 1 if successful, 0 if unsuccessful
	 */
	public int insert(CommandDetails cmdDetObj) {
		try {
			undoStack.push(cmdDetObj);
			redoStack.clear();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean clear() {
		try {
			undoStack.clear();
			redoStack.clear();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/** 
	 * Returns the last action, returns null if undoStack is empty
	 */
	public CommandDetails undo() {
		if (undoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails latest = undoStack.pop();
			System.out.println(latest.toString());
			redoStack.push(latest);
			return latest;
		}
	}
	
	/** 
	 * Returns the last undo action, returns null if redoStack is empty
	 */
	public CommandDetails redo() {
		if (redoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails old = redoStack.pop();
			undoStack.push(old);
			System.out.println(old.toString());
			return old;
		}
	}

	public Stack<CommandDetails> getUndoStack() {
		return this.undoStack;
	}

	public Stack<CommandDetails> getRedoStack() {
		return this.redoStack;
	}
}