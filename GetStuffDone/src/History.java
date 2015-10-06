import java.util.Stack;

public class History {
	
	private Stack<CommandDetails> undoStack = new Stack<CommandDetails>();
	private Stack<CommandDetails> redoStack = new Stack<CommandDetails>();
	
	//constructor
	public History () {
		this.undoStack = new Stack<CommandDetails>();
		this.redoStack = new Stack<CommandDetails>();
	}

	//this method inserts a CommandDetails object into the undoStack
	//returns 1 if successful, 0 if unsuccessful
	public int insert(CommandDetails cmdDetObj) {
		try {
			undoStack.push(cmdDetObj);
			return 1;
		}
		
		catch (Exception e) {
			return 0;
		}
	}
	
	//this method returns the last action, returns null of undoStack is empty
	public CommandDetails undo() {
		if (undoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails temp = undoStack.pop();
			redoStack.push(temp);
			return temp;
		}
	}
	
	//this method returns the last action undid, returns null of redoStack is empty
	public CommandDetails redo() {
		if (redoStack.isEmpty()) {
			return null;
		} else {
			CommandDetails temp = redoStack.pop();
			undoStack.push(temp);
			return temp;
		}
	}
}
