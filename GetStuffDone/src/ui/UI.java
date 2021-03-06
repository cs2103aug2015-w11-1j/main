package ui;

//@@author A0126561J

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import control.GSDControl;

/**
 * UI provides the graphical user interface for GetStuffDone.
 */

public class UI {

	private static final String TITLE_MAIN = "GetStuffDone";
	private static final String TITLE_HELP = "GetStuffDone Help";

	private static final String KEYWORD_ERROR = "ERROR";

	private static final int DISPLAY_BOX_WIDTH = 400;
	private static final int DISPLAY_BOX_HEIGHT = 420;
	private static final int FEEDBACK_BOX_WIDTH = 300;
	private static final int FEEDBACK_BOX_HEIGHT = 300;
	private static final int INFO_BOX_WIDTH = 250;
	private static final int INFO_BOX_HEIGHT = 120;
	private static final int SYNTAX_BOX_WIDTH = 600;
	private static final int SYNTAX_BOX_HEIGHT = 350;
	private static final int COMMAND_BOX_WIDTH = 200;
	private static final int COMMAND_BOX_HEIGHT = 350;

	private static final int NUM_MESSAGES = 11;

	private TextView displayBox = new TextView(DISPLAY_BOX_WIDTH, DISPLAY_BOX_HEIGHT);
	private TextView feedbackBox = new TextView(FEEDBACK_BOX_WIDTH, FEEDBACK_BOX_HEIGHT);
	private TextView infoBox = new TextView(INFO_BOX_WIDTH, INFO_BOX_HEIGHT);
	private JTextField commandBar = new JTextField();

	private ArrayList<String> messages = new ArrayList<>(NUM_MESSAGES);

	public static void main(String[] args) {
		new UI();
	}

	public UI() {

		GSDControl gsd = new GSDControl();

		// Initialize the main window
		JFrame frame = new JFrame(TITLE_MAIN);

		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Attach the UI components
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(feedbackBox);
		panel.add(infoBox);

		frame.add(displayBox, BorderLayout.LINE_START);
		frame.add(panel, BorderLayout.LINE_END);
		frame.add(commandBar, BorderLayout.PAGE_END);

		// Process the input when Enter is pressed
		commandBar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				String input = commandBar.getText();
				commandBar.setText("");

				addFeedback(input + "\n");

				Feedback feedback = gsd.processInput(input);

				if (feedback == null) {
					frame.dispose();
				} else {
					show(feedback);
				}
			}
		});

		Feedback feedback = gsd.loadFromFile();
		show(feedback);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		commandBar.requestFocusInWindow();
	}

	private void show(Feedback feedback) {

		assert(feedback != null);

		showInFeedbackBox(feedback.getFeedbackString());
		showInDisplayBox(feedback.getDisplayString());
		showInInfoBox(feedback.getInfoString());
		showHelpBox(feedback.getHelpCommandString(), feedback.getHelpSyntaxString());
	}

	private void showInFeedbackBox(String string) {

		if (!isValidString(string)) {
			return;
		}

		addFeedback(string);

		feedbackBox.clear();

		for (int i = 0; i < messages.size(); i++) {

			String message = messages.get(i);

			if (i % 2 == 0) {

				if (isErrorMessage(message)) {
					feedbackBox.display(message, TextView.STYLE_ERROR);
				} else {
					feedbackBox.display(message, TextView.STYLE_FEEDBACK);
				}

			} else {
				feedbackBox.display(message, TextView.STYLE_NORMAL);
			}
		}

		feedbackBox.scrollToBottom();
	}

	private void showInDisplayBox(String string) {

		if (isValidString(string)) {
			displayBox.clear();
			displayBox.display(string, TextView.STYLE_NORMAL);
			displayBox.scrollToTop();
		}
	}

	private void showInInfoBox(String string) {

		if (isValidString(string)) {
			infoBox.clear();
			infoBox.display(string, TextView.STYLE_NORMAL);
		}
	}

	private void showHelpBox(String commands, String syntax) {

		if (isValidString(commands) && isValidString(syntax)) {

			JFrame frame = new JFrame(TITLE_HELP);

			frame.setLayout(new BorderLayout());
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);

			TextView commandBox = new TextView(COMMAND_BOX_WIDTH, COMMAND_BOX_HEIGHT);
			TextView syntaxBox = new TextView(SYNTAX_BOX_WIDTH, SYNTAX_BOX_HEIGHT);

			commandBox.display(commands, TextView.STYLE_NORMAL);
			syntaxBox.display(syntax, TextView.STYLE_NORMAL);

			frame.add(syntaxBox, BorderLayout.LINE_END);
			frame.add(commandBox, BorderLayout.LINE_START);

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}

	private void addFeedback(String feedback) {

		messages.add(feedback);

		if (messages.size() > NUM_MESSAGES) {
			messages.remove(0);
		}
	}

	private boolean isErrorMessage(String string) {
		return string.contains(KEYWORD_ERROR);
	}

	private static boolean isValidString(String string) {
		return (string != null && !string.isEmpty());
	}
}