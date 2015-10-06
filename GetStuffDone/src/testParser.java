import java.util.Scanner;

public class testParser{
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		while(true){
		
		String input = sc.nextLine();
		CommandDetails details = Parser.parse(input);
		}
	}
}