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

	private JFrame frame = new JFrame(TITLE);
	private TextView displayBox = new TextView();
	private TextView feedbackBox = new TextView();
	private JTextField commandBar = new JTextField();

	public static void main(String[] args) {
		new UI();
	}

	public UI() {

		GSDControl gsd = new GSDControl();

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

				String input = commandBar.getText();
				commandBar.setText("");

				Feedback feedback = gsd.processInput(input);

				feedbackBox.display(input);

				showInFeedbackBox(feedback.getFeedbackString());
				showInDisplayBox(feedback.getDisplayString());
			}
		});

		frame.pack();

		// Set the window to be center on the screen
		frame.setLocationRelativeTo(null);

		commandBar.requestFocusInWindow();

		frame.setVisible(true);
	}

	private void showInFeedbackBox(String string) {

		if (isValidString(string)) {
			feedbackBox.display(string);
		}
	}

	private void showInDisplayBox(String string) {

		if (isValidString(string)) {
			displayBox.clearText();
			displayBox.display(string);
		}
	}

	private static boolean isValidString(String string) {
		return (string != null && !string.isEmpty());
	}
}
