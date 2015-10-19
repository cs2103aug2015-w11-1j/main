import java.text.ParseException;
import java.util.Scanner;

public class runParser{
	
	public static void main(String[] args) throws Exception{
		Parser parser = new Parser();
		Scanner sc = new Scanner(System.in);
		
		while(true){
		
		String input = sc.nextLine();
		CommandDetails details = parser.parse(input);
		}
	}
}