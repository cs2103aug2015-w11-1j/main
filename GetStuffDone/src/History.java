import java.util.Stack;

public class History {

	private Stack<CommandDetails> undoStack = new Stack<CommandDetails>();
	private Stack<CommandDetails> redoStack = new Stack<CommandDetails>();

	//history returns first CommandDetails object when undo or redo is called
	//logic works on these CommandDetails objects accordingly

	//constructor
	public History () {
		this.undoStack = new Stack<CommandDetails>();
		this.redoStack = new Stack<CommandDetails>();
	}

	/**	this method inserts a CommandDetails object into the undoStack
		and clears redo stack of obsolete commands
		returns 1 if successful, 0 if unsuccessful **/
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

	//this method returns the last action, returns null of undoStack is empty
	public CommandDetails undo() {
		if (undoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails temp = undoStack.pop();
			switch (temp.getCommand()) {
			case UPDATE:
				CommandDetails temp2 = undoStack.pop();
				redoStack.push(temp);
				redoStack.push(temp2);
				return temp;

			default: 
				redoStack.push(temp);
				return temp;

			}
		}
	}

	//this method returns the last action undid, returns null of redoStack is empty
	//	public CommandDetails redo() {
	//		if (redoStack.isEmpty()) {
	//			return null;
	//		} else {
	//			CommandDetails temp = redoStack.pop();
	//			switch (temp.getCommand()) {
	//			case UPDATE:
	//				CommandDetails temp2 = redoStack.pop();
	//				undoStack.push(temp);
	//				undoStack.push(temp2);
	//				return temp;
	//
	//			default: 
	//				undoStack.push(temp);
	//				return temp;
	//			}
	//		}
	//	}




	public CommandDetails redo() {
		if (redoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails temp = redoStack.pop();
			if (!redoStack.isEmpty()) {
				switch (redoStack.peek().getCommand()) {
				case UPDATE:
					CommandDetails temp2 = redoStack.pop();
					undoStack.push(temp);
					undoStack.push(temp2);
					return temp;

				default: 
					undoStack.push(temp);
					return temp;
				}
			}
			else {
				undoStack.push(temp);
				return temp;
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
