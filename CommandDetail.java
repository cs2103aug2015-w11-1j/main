import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandDetail{
	private static String deadLine = null;
	private static String startDate = null;
	private static String Venue = null;
	private static int priority = 0;
	private static String currentDay = getCurrentDay();
	
	public enum COMMANDS{
		ADD,DELETE,DONE,UNDO,REDO,SEARCH,HELP,
	}
	
	private static String getCurrentDay(){
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
		return ft.format(dNow);
		
	}
	
	
	public CommandDetail(String input){
		
		
	}
	
	
	
	public static void main(String args[]){
		currentDay = getCurrentDay();
		System.out.println(currentDay);
	}
}