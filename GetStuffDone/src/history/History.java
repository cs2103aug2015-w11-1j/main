package history;
import java.util.Stack;

import commandDetail.CommandDetails;

public class History {

	private Stack<CommandDetails> undoStack = new Stack<CommandDetails>();
	private Stack<CommandDetails> redoStack = new Stack<CommandDetails>();

	/**
	 *	history returns first CommandDetails object when undo or redo is called
	 *	logic works on these CommandDetails objects accordingly
	 **/

	// constructor
	public History() {
		this.undoStack = new Stack<CommandDetails>();
		this.redoStack = new Stack<CommandDetails>();
	}

	/**
	 *	this method inserts a CommandDetails object into the undoStack and clears
	 *	redo stack of obsolete commands returns 1 if successful, 0 if
	 *	unsuccessful
	 **/
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

	// this method returns the last action, returns null if undoStack is empty
	public CommandDetails undo() {
		if (undoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails latest = undoStack.pop();
			System.out.println(latest.toString());
			switch (latest.getCommand()) {
			case UPDATE:
				CommandDetails old = undoStack.pop();
				redoStack.push(latest);
				redoStack.push(old);
				return old;

			default:
				redoStack.push(latest);
				return latest;
			}
		}
	}

	public CommandDetails redo() {
		if (redoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails old = redoStack.pop();
			if (!redoStack.isEmpty()) {
				System.out.println(old.toString());
				switch (old.getCommand()) {
				case UPDATE:
					CommandDetails latest = redoStack.pop();
					undoStack.push(old);
					undoStack.push(latest);
					return latest;

				default:
					undoStack.push(old);
					return old;
				}
			} else {
				undoStack.push(old);
				return old;
			}
		}
	}

	public Stack<CommandDetails> getUndoStack() {
		return this.undoStack;
	}

	public Stack<CommandDetails> getRedoStack() {
		return this.redoStack;
	}
}
