package history;

import org.junit.Test;

import commandDetail.CommandDetails;

import static org.junit.Assert.assertEquals;
import java.util.Date;

public class testHistory {

	// ensure history object is clear after each method test

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
		history.insert(cmdDet);
		assertEquals(history.getUndoStack().peek(), cmdDet);
		history.clear();
	}

	@Test
	public void testUndo() {
		// case 1: undoStack is empty
		assertEquals(history.undo(), null);

		// case 2: undoStack is not empty
		history.insert(cmdDet);
		CommandDetails temp = history.undo();
		assertEquals(temp, cmdDet);
		assertEquals(history.getRedoStack().peek(), cmdDet);

		history.clear();
		temp = null;
	}

	@Test
	public void testRedo() {
		// case 1: redoStack is empty
		assertEquals(history.redo(), null);

		// case 2: redoStack is not empty
		history.insert(cmdDet);
		history.undo();
		CommandDetails temp = history.redo();
		assertEquals(temp, cmdDet);
		assertEquals(history.getUndoStack().peek(), cmdDet);

		history.clear();
		temp = null;
	}

}