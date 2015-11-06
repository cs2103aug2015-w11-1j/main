package commandDetail;

import org.junit.Test;

import task.Task;

import static org.junit.Assert.assertEquals;
import java.util.Date;

public class testCommandDetails {

	long sampleDate = 999;
	long sampleDeadline = 9999;
	Date testDate = new Date(sampleDate);
	Date testDeadline = new Date(sampleDeadline);
	String testDescription = "test";
	String testNewDescription = "test2";
	int testID = 99;
	CommandDetails.COMMANDS command = CommandDetails.COMMANDS.ADD;
	CommandDetails cmdDet = new CommandDetails(command, testDescription, testDate, testDeadline, testID);
	CommandDetails cmdDetNew = new CommandDetails(command, testNewDescription, testDate, testDeadline, testID);
	Task task = new Task(cmdDet);
	Task task2 = new Task(cmdDetNew);
	CommandDetails cmdDetforTasks = new CommandDetails(command, testDescription, testDate, testDeadline, testID,
			testDate, testDeadline, testDeadline, task, task2);
	
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
	public void testGetNewTask() {
		assertEquals(cmdDetforTasks.getNewTask(), task2);
	}
	
	@Test
	public void testGetOldTask() {
		assertEquals(cmdDetforTasks.getOldTask(), task);
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
	
	@Test
	public void testSetNewTask() {
		CommandDetails cmdDetTemp = cmdDetforTasks;
		cmdDetTemp.setNewTask(task);
		assertEquals(cmdDetTemp.getNewTask(), task);
	}
	
	@Test
	public void testSetOldTask() {
		CommandDetails cmdDetTemp = cmdDetforTasks;
		cmdDetTemp.setOldTask(task2);
		assertEquals(cmdDetTemp.getOldTask(), task2);
	}
	
	@Test
	public void testToString() {
		String result = "command = " + command + "\nID = " + testID + "\ndescription = " + testDescription + "\nstartDate = "
				+ testDate + "\ndeadline = " + testDeadline + "\n";;
		System.out.println(cmdDet.toString());
		assertEquals(cmdDet.toString(), result);
	}

}
