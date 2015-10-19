import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Date;


public class testCommandDetails {
	
	long sampleDate = 999;
	long sampleDeadline = 9999;
	Date testDate = new Date(sampleDate);
	Date testDeadline = new Date(sampleDeadline);
	String testDescription = "test";
	int testID = 99;
	CommandDetails.COMMANDS command = CommandDetails.COMMANDS.ADD;
	CommandDetails cmdDet = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
	
	
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
	
	@Test
	public void testSetCommand() {
		CommandDetails.COMMANDS commandTemp = CommandDetails.COMMANDS.DELETE;	
		CommandDetails cmdDetTemp = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
		cmdDetTemp.setCommand(commandTemp);
		assertEquals(cmdDetTemp.getCommand(), commandTemp);
	}
	
	@Test
	public void testSetStartDate() {
		CommandDetails cmdDetTemp = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
		Date testDateTemp = new Date(9999999);
		cmdDetTemp.setStartDate(testDateTemp);
		assertEquals(cmdDetTemp.getStartDate(), testDateTemp);
	}
	
	@Test
	public void testSetDeadline() {
		CommandDetails cmdDetTemp = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
		Date testDeadlineTemp = new Date(123123123);
		cmdDetTemp.setDeadline(testDeadlineTemp);
		assertEquals(cmdDetTemp.getDeadline(), testDeadlineTemp);
	}
	
	@Test
	public void testSetDescription() {
		CommandDetails cmdDetTemp = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
		String testDescriptionTemp = "CS2103T Ironmen";
		cmdDetTemp.setDescription(testDescriptionTemp);
		assertEquals(cmdDetTemp.getDescription(), testDescriptionTemp);
	}
	
	@Test
	public void testSetID() {
		CommandDetails cmdDetTemp = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
		int testIDTemp = 1234;
		cmdDetTemp.setID(testIDTemp);
		assertEquals(cmdDetTemp.getID(), testIDTemp);
	}
	
}
