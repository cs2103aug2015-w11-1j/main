package ui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GSDControl;

/**
 * User interface for GetStuffDone. Only interacts with the Logic component.
 */
public class UI {

	private static final String TITLE_MAIN = "GetStuffDone";
	private static final String TITLE_HELP = "GetStuffDone Help";

	private static final int DISPLAY_BOX_WIDTH = 400;
	private static final int DISPLAY_BOX_HEIGHT = 420;
	private static final int FEEDBACK_BOX_WIDTH = 300;
	private static final int FEEDBACK_BOX_HEIGHT = 300;
	private static final int INFO_BOX_WIDTH = 250;
	private static final int INFO_BOX_HEIGHT = 120;
	private static final int SYNTAX_BOX_WIDTH = 600;
	private static final int SYNTAX_BOX_HEIGHT = 300;
	private static final int COMMAND_BOX_WIDTH = 200;
	private static final int COMMAND_BOX_HEIGHT = 300;

	private TextView displayBox = new TextView(DISPLAY_BOX_WIDTH, DISPLAY_BOX_HEIGHT);
	private TextView feedbackBox = new TextView(FEEDBACK_BOX_WIDTH, FEEDBACK_BOX_HEIGHT);
	private TextView infoBox = new TextView(INFO_BOX_WIDTH, INFO_BOX_HEIGHT);
	private JTextField commandBar = new JTextField();

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

				showInFeedbackBox(input + "\n", TextView.STYLE_NORMAL);

				Feedback feedback = null;
				
					feedback = gsd.processInput(input);

				show(feedback);
			}
		});

		Feedback feedback = gsd.loadFromFile();
		show(feedback);

		frame.pack();

		// Set the window to be center on the screen
		frame.setLocationRelativeTo(null);

		commandBar.requestFocusInWindow();

		frame.setVisible(true);
	}

	private void show(Feedback feedback) {

		showInFeedbackBox(feedback.getFeedbackString(), TextView.STYLE_FEEDBACK);
		showInDisplayBox(feedback.getDisplayString(), TextView.STYLE_NORMAL);
		showInInfoBox(feedback.getInfoString(), TextView.STYLE_NORMAL);
		showHelpBox(feedback.getHelpCommandString(), feedback.getHelpSyntaxString());
	}

	private void showInFeedbackBox(String string, String style) {

		if (isValidString(string)) {
			feedbackBox.display(string, style);
			feedbackBox.scrollToBottom();
		}
	}

	private void showInDisplayBox(String string, String style) {

		if (isValidString(string)) {
			displayBox.clear();
			displayBox.display(string, style);
			displayBox.scrollToTop();
		}
	}

	private void showInInfoBox(String string, String style) {

		if (isValidString(string)) {
			infoBox.clear();
			infoBox.display(string, style);
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

	private static boolean isValidString(String string) {
		return (string != null && !string.isEmpty());
	}
}