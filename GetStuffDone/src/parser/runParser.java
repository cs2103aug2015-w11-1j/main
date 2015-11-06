package parser;
import java.util.Scanner;
import commandDetail.CommandDetails;

public class runParser {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);

		while (true) {

			String input = sc.nextLine();

			try {
				CommandDetails details = Parser.parse(input);
				System.out.println(details);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}