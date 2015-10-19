import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import CommandDetails.COMMANDS;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class testCommandDetails {
	
	//constructor
	//public CommandDetails(COMMANDS command, String description, String venue, 
	//						Date startDate, Date deadline, String priority, int ID) {
	
	public enum COMMANDS {
		ADD, DELETE, SEARCH, UPDATE, COMPLETE, INCOMPLETE, UNDO, REDO, HELP, DISPLAY, FLOATING, EXIT, INVALID
	}

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	Date testDate = sdf.parse("19/10/2015");
	Date testDeadline = sdf.parse("19/11/2015");
	String testDescription = "test";
	int testID = 99;
	CommandDetails.COMMANDS command = CommandDetails.COMMANDS.ADD;	
	
	CommandDetails cmdDet = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
	

//	sample test case:
//	@Test
//	public void testAdd() {
//		ArrayList<String> lst = new ArrayList<String>();
//		String[] cmds = {"add",testMsg};
//		TextBuddy.add(cmds, fileName , lst);
//		assertEquals(testMsg, lst.get(0));
//	}
	
	@Test
	public void testCreate() {
		CommandDetails testCmdDet = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
		assertEquals(testCmdDet, cmdDet);	
	}
	
	@Test
	public void testGetCommand() {
		assertEquals(cmdDet.getCommand(), command);	
	}
	
	@Test
	public void testGetStartDate() {
		assertEquals(cmdDet.getStartDate(), testDate);	
	}
	
	@Test
	public void testGetDeadline() {
		assertEquals(cmdDet.getDeadline(), testDeadline);	
	}
	
	@Test
	public void testGetDescription() {
		assertEquals(cmdDet.getDescription(), testDescription);	
	}
	
	@Test
	public void testGetID() {
		assertEquals(cmdDet.getID(), testID);	
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
