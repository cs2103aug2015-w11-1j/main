import java.text.ParseException;
import java.util.Scanner;

public class runParser {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {

			String input = sc.nextLine();

			try {
				CommandDetails details = Parser.parse(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}