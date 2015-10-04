import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * User interface for GetStuffDone. Only interacts with the Logic component.
 */
public class UI {

	private static final String TITLE = "GetStuffDone";

	private static final String START = "    START\t";
	private static final String END = "    END\t";
	private static final String VENUE = "    VENUE\t";
	private static final String PRIORITY = "    PRIORITY\t";

	private JFrame frame = new JFrame(TITLE);
	private TextView displayBox = new TextView();
	private TextView feedbackBox = new TextView();
	private JTextField commandBar = new JTextField();

	public UI() {

		// Initialize the main window
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Attach the UI componenets
		frame.add(new JScrollPane(displayBox), BorderLayout.LINE_START);
		frame.add(new JScrollPane(feedbackBox), BorderLayout.LINE_END);
		frame.add(commandBar, BorderLayout.PAGE_END);

		// Process the input when Enter is pressed
		commandBar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				/**
				 * TODO process input
				 * 
				 * Ideal code:
				 * 
				 * String input = commandBar.getText();
				 * logic.processInput(input);
				 */
			}
		});

		frame.pack();

		// Set the window to be center on the screen
		frame.setLocationRelativeTo(null);

		commandBar.requestFocusInWindow();

		frame.setVisible(true);
	}

	/**
	 * Display the feedback in the next line in the feedback box
	 * 
	 * @param feedback
	 *            is the string to be displayed
	 */
	public void displayFeedback(String feedback) {

		if (isValidString(feedback)) {
			feedbackBox.display(feedback);
		}
	}

	/**
	 * Display the specified list of tasks in the display box
	 * 
	 * @param tasks
	 *            is the ArrayList of Tasks
	 */
	public void displayTasks(ArrayList<Task> tasks) {

		if (tasks.isEmpty() || tasks == null) {
			return;
		}

		displayBox.clearText();

		for (Task task : tasks) {
			displayTask(task);
		}
	}

	private void displayTask(Task task) {

		assert(task != null);

		if (isValidString(task.getDescription())) {
			displayBox.display(task.getDescription());
		} else {
			return;
		}

		if (task.getStartDate() != null) {
			displayBox.display(START + task.getStartDate().toString());
		}

		if (task.getdeadline() != null) {
			displayBox.display(END + task.getdeadline().toString());
		}

		if (isValidString(task.getVenue())) {
			displayBox.display(VENUE + task.getVenue());
		}

		if (isValidString(task.getPriority())) {
			displayBox.display(PRIORITY + task.getPriority());
		}
	}

	private static boolean isValidString(String string) {
		return (string != null && !string.isEmpty());
	}
}
