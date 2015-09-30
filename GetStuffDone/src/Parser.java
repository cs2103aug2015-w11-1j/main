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
			return currentDay;
		}else{
			indexOfNextKeyWord=0;
			for(int i=indexOfKeyWord+1; i<input.size();i++){
				if(isKeyWord(input.get(i))){
					indexOfNextKeyWord=i;
					break;
				}
			}
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
	
	
	
	private static String parseLocation(ArrayList<String> input){
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
	
	private static String getCurrentDay(){
		Date now = new Date();
		SimpleDateFormat ft = new SimpleDateFormat (DATE_FORMAT);
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
		String input = "add submit code to git hub AT my room at 4302 BY 2359 today PRIORITY HIGH";
		ArrayList<String> strTokens = new ArrayList<String> (Arrays.asList(input.split(" ")));
		
		
		COMMANDS command = parseCommandType(strTokens);
		System.out.println(strTokens);
		System.out.println("Priority= " + parsePriority(strTokens));
		System.out.println(strTokens);
		System.out.println("Location= " + parseLocation(strTokens));
		System.out.println(strTokens);
		
		System.out.println("Start= "+ parseStartDate(strTokens));
		System.out.println(strTokens);
		System.out.println("End= "+ parseEndDate(strTokens));
		System.out.println(strTokens);
		System.out.println("Dis= "+ parseDescription(strTokens));
		
		
		
		
		//create commandDetailsObject
		CommandDetails cmdDetails = new CommandDetails(deadLine, startDate, Venue, priority, description);
		sendToLogic(cmdDetails);
		
		
	}
}