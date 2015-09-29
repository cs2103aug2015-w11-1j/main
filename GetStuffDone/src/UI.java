import java.util.Iterator;
import java.util.Scanner;

/**
 * User interface for GetStuffDone. Only interacts with the Logic component.
 */
public class UI {

	private Scanner scanner = new Scanner(System.in);

	public String readInput() {

		if (scanner.hasNextLine()) {
			return scanner.nextLine();
		} else {
			return null;
		}
	}

	public void displayFeedback(String feedback) {
		System.out.println(feedback);
	}

	public void displayTasks(Iterator<Task> tasks) {
		
		while (tasks.hasNext()) {
			System.out.println(tasks.next().toString());
		}
	}
}
