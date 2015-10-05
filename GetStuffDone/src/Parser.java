import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Parser{
	private final static String[] DATE_FORMAT = {
			"HHmm dd MMMM yy",
			"HH.mm dd MMMM yy",
			"HH:mm dd MMMM yy",
			"hhmma dd MMMM yy",
			"hha dd MMMM yy",
			"hmma dd MMMM yy",
			"hh.mma dd MMMM yy",
			"hh:mma dd MMMM yy",
			"dd MMMM yy",

			"HHmm dd MM yy",
			"HH.mm dd MM yy",
			"HH:mm dd MM yy",
			"hhmma dd MM yy",
			"hha dd MM yy",
			"hmma dd MM yy",
			"hh.mma dd MM yy",
			"hh:mma dd MM yy",
			"dd MM yy",

			"HHmm dd MMMM",
			"HH.mm dd MMMM",
			"HH:mm dd MMMM",
			"hhmma dd MMMM",
			"hha dd MMMM",
			"hmma dd MMMM",
			"hh.mma dd MMMM",
			"hh:mma dd MMMM",
			"dd MMMM",
			
			"HHmm dd MM",
			"HH.mm dd MM",
			"HH:mm dd MM",
			"hhmma dd MM",
			"hha dd MM",
			"hmma dd MM",
			"hh.mma dd MM",
			"hh:mma dd MM",
			"dd MM",
			
			"HHmm dd/MMMM/yy",
			"HH.mm dd/MMMMMM/yy",
			"HH:mm dd/MM/yy",
			"hhmma dd/MMMM/yy",
			"hha dd/MMMM/yy",
			"hmma dd/MMMM/yy",
			"hh.mma dd/MMMM/yy",
			"hh:mma dd/MMMM/yy",
			"dd/MMMM/yy",
			
			"HHmm dd/MM/yy",
			"HH.mm dd/MM/yy",
			"HH:mm dd/MM/yy",
			"hhmma dd/MM/yy",
			"hha dd/MM/yy",
			"hmma dd/MM/yy",
			"hh.mma dd/MM/yy",
			"hh:mma dd/MM/yy",
			"dd/MM/yy",
			
			
			"HHmm dd/MM",
			"HH.mm dd/MM",
			"HH:mm dd/MM",
			"hhmma dd/MM",
			"hha dd/MM",
			"hmma dd/MM",
			"hh.mma dd/MM",
			"hh:mma dd/MM",
			"dd/MM",
			
			
			"HHmm dd-MMMM-yy",
			"HH.mm dd-MMMM-yy",
			"HH:mm dd-MMMM-yy",
			"hhmma dd-MMMM-yy",
			"hha dd-MMMM-yy",
			"hmma dd-MMMM-yy",
			"hh.mma dd-MMMM-yy",
			"hh:mma dd-MMMM-yy",
			"dd-MM-yy",
			
			
			"HHmm dd-MM",
			"HH.mm dd-MM",
			"HH:mm dd-MM",
			"hhmma dd-MM",
			"hha dd-MM",
			"hmma dd-MM",
			"hh.mma dd-MM",
			"hh:mma dd-MM",
			"dd-MM",
			
			
			"HHmm dd-MMMM",
			"HH.mm dd-MMMM",
			"HH:mm dd-MMMM",
			"hhmma dd-MMMM",
			"hha dd-MMMM",
			"hmma dd-MMMM",
			"hh.mma dd-MMMM",
			"hh:mma dd-MMMM",
			"dd-MM",

	};
	private static ArrayList<String> keyWords = new ArrayList<String>() {{
	    add("AT");
	    add("BY");
	    add("FROM");
	    add("TO");
	    add("PRIORITY");
	}};
	
	public enum COMMANDS{
		ADD,DELETE,DONE,UNDO,REDO,SEARCH,HELP,
	}
	
	private static String parseStartDate(ArrayList<String> input){
		String result = "";
		int indexOfKeyWord = input.lastIndexOf("FROM");
		int indexOfNextKeyWord;
		if(indexOfKeyWord == -1){
			System.out.print("no start date, default set as today ");
			return null;
		}else{
			indexOfNextKeyWord=-2;
			for(int i=indexOfKeyWord+1; i<input.size();i++){
				if(isKeyWord(input.get(i))){
					indexOfNextKeyWord=i;
					break;
				}
			}
		}
		
		if(indexOfNextKeyWord == -2){
			indexOfNextKeyWord = input.size();
		}
		
		result = getInputBetweenArrayList(input, indexOfKeyWord, indexOfNextKeyWord);
		return result;
	}
		

	
	private static String parseEndDate(ArrayList<String> input){
		String result = "";
		int indexOfNextKeyWord;
		
		int indexOfKeyWordTO = input.lastIndexOf("TO");
		int indexOfKeyWordBY = input.lastIndexOf("BY");
		
		if(indexOfKeyWordTO == -1 && indexOfKeyWordBY == -1){
			return "NO End Date found";
		}else if(indexOfKeyWordTO == -1 && !(indexOfKeyWordBY == -1)){
			indexOfNextKeyWord=-2;
			for(int i=indexOfKeyWordBY+1; i<input.size();i++){
				if(isKeyWord(input.get(i))){
					indexOfNextKeyWord=i;
					break;
				}
			}
		}else{
			indexOfNextKeyWord=-2;
			for(int i=indexOfKeyWordTO+1; i<input.size();i++){
				if(isKeyWord(input.get(i))){
					indexOfNextKeyWord=i;
					break;
				}
			}
		}
		
		if(indexOfNextKeyWord == -2){
			indexOfNextKeyWord = input.size();
		}
		//end date is FROM
		if(indexOfKeyWordBY == -1){
			result = getInputBetweenArrayList(input, indexOfKeyWordTO, indexOfNextKeyWord);
		}
		//end date is BY
		if(indexOfKeyWordTO == -1){
			result = getInputBetweenArrayList(input, indexOfKeyWordBY, indexOfNextKeyWord);
		}
		return result;
	}
	
	private static boolean isKeyWord(String input){

		for(int i=0;i<keyWords.size();i++){
			if(keyWords.get(i).equals(input)){
				return true;
			}
		}
		return false;
	}
	
	
	
	private static String parseVenue(ArrayList<String> input){
		int indexOfKeyWord = input.lastIndexOf("AT");
		int indexOfNextKeyWord;
		if(indexOfKeyWord == -1){
			return null;
		}else{
			indexOfNextKeyWord=-2;
			for(int i=indexOfKeyWord+1; i<input.size();i++){
				if(isKeyWord(input.get(i))){
					indexOfNextKeyWord=i;
					break;
				}
			}
		}
		if(indexOfNextKeyWord == -2){
			indexOfNextKeyWord = input.size();
		}
		String result = getInputBetweenArrayList(input, indexOfKeyWord, indexOfNextKeyWord);
		return result;
	}


	private static String getInputBetweenArrayList(ArrayList<String> input, int indexOfKeyWord, int indexOfNextKeyWord) {
		String result = "";
		for(int i = 0;i<  indexOfNextKeyWord - indexOfKeyWord - 1;i++){
			result = result + " " + input.remove(indexOfKeyWord + 1);
		}
		result = result.substring(1);
		input.remove(indexOfKeyWord);
		return result;
	}
	
	private static String parseDescription(ArrayList<String> input){
		String result= "";

		if(input.isEmpty()){
			return "please include a description for task";
		}
		
		while(!input.isEmpty()){
			result = result+" " +input.remove(0);
		}
		
		return result.substring(1,result.length());
	}

	
	private static String parsePriority(ArrayList<String> input){
		
		if(input.lastIndexOf("PRIORITY") == -1){
			System.out.println("No Priority found");
			return null;
		}else{
			int keyWordLocation = input.lastIndexOf("PRIORITY");
			int priorityLocate = keyWordLocation + 1;
			String priority = input.get(priorityLocate).toUpperCase();
			
			switch(priority){
				case "HIGH":
					input.remove(priorityLocate);
					input.remove(keyWordLocation);
					return "HIGH";
				case "MEDIUM":
					input.remove(priorityLocate);
					input.remove(keyWordLocation);
					return "MEDIUM";
				case "LOW":
					input.remove(priorityLocate);
					input.remove(keyWordLocation);
					return "LOW";
				default:
					return null;
			}
		}
	}
	
	private static COMMANDS parseCommandType(ArrayList<String> input){

		switch(input.get(0).toUpperCase()){
		case "ADD":
			System.out.println("ADD command");
			input.remove(0);
			return COMMANDS.ADD;
		case "DELETE":
			System.out.println("DELETE command");
			input.remove(0);
			return COMMANDS.DELETE;
		case "DONE":
			System.out.println("DONE command");
			input.remove(0);
			return COMMANDS.DONE;
		case "HELP":
			System.out.println("HELP command");
			input.remove(0);
			return COMMANDS.HELP;
		case "REDO":
			System.out.println("add command");
			input.remove(0);
			return COMMANDS.ADD;
		case "SEARCH":
			System.out.println("SEARCH command");
			input.remove(0);
			return COMMANDS.SEARCH;
		case "UNDO":
			System.out.println("UNDO command");
			input.remove(0);
			return COMMANDS.UNDO;
		}
		return null;
	}
	
	//creating and sending commandDetails object to logic class
	static Logic commandDetailsObject = null;
	Parser (Logic obj) {
		this.commandDetailsObject = obj;
	}
	
	public static void sendToLogic (CommandDetails cmdDetails){
		commandDetailsObject.setCmdDetailsObj(cmdDetails);
	}
	
	private static Date createDate(String input) {
		for(String temp: DATE_FORMAT){
			try{
			SimpleDateFormat possibleFormats = new SimpleDateFormat(temp);
			possibleFormats.setLenient(false);
			Date mydate = possibleFormats.parse(input);
			Calendar cal = Calendar.getInstance();
			cal.setTime(mydate);
			if(cal.get(Calendar.YEAR) == 1970){
				cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				mydate= cal.getTime();
			}
			
			System.out.println(temp +" match");
			return mydate;
			} catch(Exception e){
				//System.out.println(temp +" dont match, next!");
			}
		}
		return null;
	}
	
	
	public static void main(String[] args){
		
		String deadLine = null;
		String startDate = null;
		String venue = null;
		String priority;
		String description;
		
		Scanner sc = new Scanner(System.in);
		
		String input = sc.nextLine();
		ArrayList<String> strTokens = new ArrayList<String> (Arrays.asList(input.split(" ")));
		
		System.out.println(input);
		
		COMMANDS command = parseCommandType(strTokens);
		//System.out.println(strTokens);
		
		priority = parsePriority(strTokens);
		System.out.println("Priority= " + priority);
		//System.out.println(strTokens);
		
		venue = parseVenue(strTokens);
		System.out.println("Venue= " + venue);
		//System.out.println(strTokens);
		
		
		
		startDate = parseStartDate(strTokens);
		//System.out.println("Start= "+ startDate);
		//System.out.println(strTokens);
		Date start = createDate(startDate);
		System.out.println("Start= "+ start);
		
		
		
		deadLine = parseEndDate(strTokens);
		System.out.println("End= "+ deadLine);
		//System.out.println(strTokens);
		Date end = createDate(deadLine);
		System.out.println("End= "+ end);
		
		description = parseDescription(strTokens);
		System.out.println("Description= " + description);
		
		
		
		
		//create commandDetailsObject
		CommandDetails cmdDetails = new CommandDetails(end, start, venue, priority, description);
		//sendToLogic(cmdDetails);
		
		
	}
}