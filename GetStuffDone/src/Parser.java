import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Parser{
	private static Date deadLine = null;
	private static Date startDate = null;
	private static String Venue = null;
	private static int priority;
	private static String currentDay = getCurrentDay();
	final static String DATE_FORMAT = "dd/MM/yyyy";
	
	public enum COMMANDS{
		ADD,DELETE,DONE,UNDO,REDO,SEARCH,HELP,
	}
	
	
	public Parser(String input){
		
		
	}
	
	
	private static Date getStartDate(String input){
		Date result = null;
		
		ArrayList<String> strTokens = new ArrayList<String> (Arrays.asList(input.toUpperCase().split(" ")));

		
		return result;
		
	}
	
	
	private static Date getEndDate(String input){
		Date result= null;
		return result;
		
	}
	
	
	private static int getPriority(String input){
		ArrayList<String> strTokens = new ArrayList<String> (Arrays.asList(input.toUpperCase().split(" ")));
		if(strTokens.lastIndexOf("PRIORITY") == -1){
			return 0;
		}else{
			int priorityLocate = strTokens.lastIndexOf("PRIORITY") + 1;
			String priority = strTokens.get(priorityLocate);
			
			switch(priority){
				case "HIGH":
					return 3;
				case "MEDIUM":
					return 2;
				case "LOW":
					return 1;
				default:
					return 100; //Invalid priority number
			}
		}
	}
	
	private static COMMANDS getCommandType(String input){
		
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
	
	public static void main(String args[]){
		String input = "add dinner with mum PRiORiTy low";
		COMMANDS command = getCommandType(input);
		System.out.println(getPriority(input));
	}
}