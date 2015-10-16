import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * User interface for GetStuffDone. Only interacts with the Logic component.
 */
public class UI {

	private static final String TITLE = "GetStuffDone";

	private static final int DISPLAY_BOX_WIDTH = 400;
	private static final int DISPLAY_BOX_HEIGHT = 400;
	private static final int FEEDBACK_BOX_WIDTH = 200;	
	private static final int FEEDBACK_BOX_HEIGHT = 300;
	private static final int INFO_BOX_WIDTH = 200;
	private static final int INFO_BOX_HEIGHT = 100;

	private TextView displayBox = new TextView(DISPLAY_BOX_WIDTH, DISPLAY_BOX_HEIGHT);
	private TextView feedbackBox = new TextView(FEEDBACK_BOX_WIDTH, FEEDBACK_BOX_HEIGHT);
	private JLabel infoBox = new JLabel();
	private JTextField commandBar = new JTextField();

	public static void main(String[] args) {
		new UI();
	}

	public UI() {

		GSDControl gsd = new GSDControl();

		// Initialize the main window
		JFrame frame = new JFrame(TITLE);

		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Attach the UI componenets
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		infoBox.setPreferredSize(new Dimension(INFO_BOX_WIDTH, INFO_BOX_HEIGHT));

		panel.add(new JScrollPane(feedbackBox));
		panel.add(infoBox);

		frame.add(new JScrollPane(displayBox), BorderLayout.LINE_START);
		frame.add(panel, BorderLayout.LINE_END);
		frame.add(commandBar, BorderLayout.PAGE_END);

		// Process the input when Enter is pressed
		commandBar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				 String input = commandBar.getText();
				 commandBar.setText("");
				 
				 showInFeedbackBox(input + "\n", TextView.STYLE_NORMAL);
				
				 Feedback feedback = gsd.processInput(input);
				 
				 showInFeedbackBox(feedback.getFeedbackString(), TextView.STYLE_BOLD);
				 showInDisplayBox(feedback.getDisplayString(), TextView.STYLE_NORMAL);
			}
		});

		frame.pack();

		// Set the window to be center on the screen
		frame.setLocationRelativeTo(null);

		commandBar.requestFocusInWindow();

		frame.setVisible(true);
	}

	private void showInFeedbackBox(String string, String style) {

		if (isValidString(string)) {
			feedbackBox.display(string, style);
		}
	}

	private void showInDisplayBox(String string, String style) {

		if (isValidString(string)) {
			displayBox.clear();
			displayBox.display(string, style);
		}
	}
	
	private void showInInfoBox(String string){
		
		if(isValidString(string)) {
			infoBox.setText(string);
		}
	}

	private static boolean isValidString(String string) {
		return (string != null && !string.isEmpty());
	}
}
