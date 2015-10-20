import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;


public class testHistory {

	//ensure history object is clear after each method test

	History history = new History();
	long sampleDate = 999;
	long sampleDeadline = 9999;
	Date testDate = new Date(sampleDate);
	Date testDeadline = new Date(sampleDeadline);
	String testDescription = "test";
	int testID = 99;
	CommandDetails.COMMANDS command = CommandDetails.COMMANDS.ADD;	
	CommandDetails cmdDet = new CommandDetails(command, testDescription, testDate, testDeadline, testID);


	@Test
	public void testClear() {
		boolean undoCleared = false;
		boolean redoCleared = false;
		history.insert(cmdDet);
		history.insert(cmdDet);
		history.undo();
		history.clear();
		undoCleared = history.getUndoStack().empty();
		redoCleared = history.getRedoStack().empty();
		assertEquals(undoCleared, true);
		assertEquals(redoCleared, true);
	}

	@Test
	public void testInsert() {
		CommandDetails cmdDetFail = new CommandDetails(null, testDescription, testDate, testDeadline, testID);
		int testFail = history.insert(cmdDetFail);
		assertEquals(testFail, 0);
		history.insert(cmdDet);
		assertEquals(history.getUndoStack().peek(), cmdDet);
		history.clear();
	}
	
	@Test
	public void testUndo() {
		CommandDetails.COMMANDS commandUpdate = CommandDetails.COMMANDS.UPDATE;
		CommandDetails cmdDetUpdateNew = new CommandDetails(commandUpdate, "new", testDate, testDeadline, testID);
		CommandDetails cmdDetUpdateOld = new CommandDetails(commandUpdate, testDescription, testDate, testDeadline, testID);
		//case 1: undoStack is empty
		assertEquals(history.undo(), null);
		
		//case 2: undoStack first element is not update
		history.insert(cmdDet);
		CommandDetails temp = history.undo();
		assertEquals(temp, cmdDet);
		assertEquals(history.getRedoStack().peek(), cmdDet);
		
		history.clear();
		temp = null;
		
		//case 3: undoStack first element is update
		history.insert(cmdDetUpdateOld);		
		history.insert(cmdDetUpdateNew);
		temp = history.undo();
		assertEquals(history.getUndoStack().empty(), true);
		assertEquals(history.getRedoStack().peek(), cmdDetUpdateOld);
		history.getRedoStack().pop();
		assertEquals(history.getRedoStack().peek(), cmdDetUpdateNew);
		assertEquals(temp, cmdDetUpdateOld);
		history.clear();
	}
	
	@Test
	public void testRedo() {
		CommandDetails.COMMANDS commandUpdate = CommandDetails.COMMANDS.UPDATE;
		CommandDetails cmdDetUpdateNew = new CommandDetails(commandUpdate, "new", testDate, testDeadline, testID);
		CommandDetails cmdDetUpdateOld = new CommandDetails(commandUpdate, testDescription, testDate, testDeadline, testID);
		//case 1: redoStack is empty
		assertEquals(history.redo(), null);
		
		//case 2: redoStack second element is not update
		history.insert(cmdDet);
		history.undo();
		CommandDetails temp = history.redo();
		assertEquals(temp, cmdDet);
		assertEquals(history.getUndoStack().peek(), cmdDet);
		
		history.clear();
		temp = null;
		
		//case 3: redoStack second element is update
		history.insert(cmdDetUpdateOld);		
		history.insert(cmdDetUpdateNew);
		history.undo();
		temp = history.redo();
		assertEquals(history.getRedoStack().empty(), true);
		assertEquals(history.getUndoStack().peek(), cmdDetUpdateNew);
		history.getUndoStack().pop();
		assertEquals(history.getUndoStack().peek(), cmdDetUpdateOld);
		assertEquals(temp, cmdDetUpdateNew);
		history.clear();
	}
	
}