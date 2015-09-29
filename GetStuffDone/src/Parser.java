import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Parser{
	private static Date deadLine = null;
	private static Date startDate = null;
	private static String Venue = null;
	private static String priority;
	private static String description;
	private static String currentDay = getCurrentDay();
	final static String DATE_FORMAT = "dd/MM/yyyy";
	
	public enum COMMANDS{
		ADD,DELETE,DONE,UNDO,REDO,SEARCH,HELP,
	}
	
	private static Date parseStartDate(String input){
		Date result = new Date();
		return result;
		
	}
	
	
	private static Date parseEndDate(String input){
		Date result= new Date();
		return result;
		
	}
	
	
	///////////////////////////////////////////////////
	
	private static String parseLocation(String input){
		String result= "";
		
		ArrayList<String> strTokens = new ArrayList<String> (Arrays.asList(input.toUpperCase().split(" ")));
		if(strTokens.lastIndexOf("/AT") == -1){
			return "NONE";
		}
		
		
		
		
		return result;
	}
	
	private static String parseDescription(String input){
		String result= "";
		return result;
	}
	///////////////////////////////////////////////////////
	
	private static String parsePriority(String input){
		ArrayList<String> strTokens = new ArrayList<String> (Arrays.asList(input.toUpperCase().split(" ")));
		if(strTokens.lastIndexOf("PRIORITY") == -1){
			return "NONE";
		}else{
			int priorityLocate = strTokens.lastIndexOf("PRIORITY") + 1;
			String priority = strTokens.get(priorityLocate);
			
			switch(priority){
				case "HIGH":
					return "HIGH";
				case "MEDIUM":
					return "MEDIUM";
				case "LOW":
					return "LOW";
				default:
					return "Invalid priority number"; //Invalid priority number
			}
		}
	}
	
	private static COMMANDS parseCommandType(String input){
		
		String[] temp = input.split(" ");
		
		switch(temp[0].toUpperCase()){
		case "ADD":
			System.out.println("ADD command");
			return COMMANDS.ADD;
		case "DELETE":
			System.out.println("DELETE command");
			return COMMANDS.DELETE;
		case "DONE":
			System.out.println("DONE command");
			return COMMANDS.DONE;
		case "HELP":
			System.out.println("HELP command");
			return COMMANDS.HELP;
		case "REDO":
			System.out.println("add command");
			return COMMANDS.ADD;
		case "SEARCH":
			System.out.println("SEARCH command");
			return COMMANDS.SEARCH;
		case "UNDO":
			System.out.println("UNDO command");
			return COMMANDS.UNDO;
		}
		return null;
	}
	
	private static String getCurrentDay(){
		Date now = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
		return ft.format(now);
	}
	
	
	
	//creating and sending commandDetails object to logic class
	static Logic commandDetailsObject = null;
	Parser (Logic obj) {
		this.commandDetailsObject = obj;
	}
	
	public static void sendToLogic (CommandDetails cmdDetails){
		commandDetailsObject.setCmdDetailsObj(cmdDetails);
	}
	
	
	public static void main(String args[]){
		String input = "add do code buddy /at home from 29 sept 2015 7pm to 29 sept 2015 11.59pm priority high";
		COMMANDS command = parseCommandType(input);
		System.out.println(parsePriority(input));
		
		//create commandDetailsObject
		CommandDetails cmdDetails = new CommandDetails(deadLine, startDate, Venue, priority, description);
		sendToLogic(cmdDetails);
		
		
	}
}


class CommandDetails {
	private Date deadline;
	private Date startDate;
	private String venue, priority, description;
	
	public CommandDetails() {
		this.deadline = null;
		this.startDate = null;
		this.venue = null;
		this.priority = null;
		this.description = null;
	}
	
	public CommandDetails(Date deadline, Date startDate, String venue, String priority, String description) {
		this.deadline = deadline;
		this.startDate = startDate;
		this.venue = venue;
		this.priority = priority;
		this.description = description;
	}
	
	public Date getDeadline() {
		return this.deadline;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public String getVenue() {
		return this.venue;
	}
	
	public String getPriority() {
		return this.priority;
	}
	
	public String getDescription() {
		return this.description;
	}
}