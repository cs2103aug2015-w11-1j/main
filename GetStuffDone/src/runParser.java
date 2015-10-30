import java.text.ParseException;
import java.util.Scanner;

public class runParser {

	public static void main(String[] args) {
		Parser parser = new Parser();
		Scanner sc = new Scanner(System.in);

		while (true) {

			String input = sc.nextLine();

			try {
				CommandDetails details = parser.parse(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}