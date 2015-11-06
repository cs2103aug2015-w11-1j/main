package ui;

//@@author A0126561J

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/*
 * TextView is an UI component for displaying text in different styles e.g.
 * font, size, text colour, background colour etc.
 */

public class TextView extends JScrollPane {

	public static final String STYLE_NORMAL = "normal";
	public static final String STYLE_FEEDBACK = "feedback";
	public static final String STYLE_ERROR = "error";

	// Auto-generated code
	private static final long serialVersionUID = 1L;

	private static final int FONT_SIZE_NORMAL = 12;

	private static final String DEFAULT_FONT = "verdana";

	private JTextPane textPane;
	private StyledDocument document;

	private Style styleNormal;
	private Style styleFeedback;
	private Style styleError;

	public TextView(int width, int height) {

		super(new JTextPane());

		textPane = (JTextPane) this.getViewport().getView();
		textPane.setEditable(false);

		document = textPane.getStyledDocument();

		setPreferredSize(new Dimension(width, height));

		initializeStyles();
	}

	/*
	 * Display the specified text with normal style
	 * 
	 * @param text
	 *            is the text to be displayed
	 */
	public void display(String text, String styleType) {

		Style style;

		switch (styleType) {
		case STYLE_NORMAL:
			style = styleNormal;
			break;
		case STYLE_FEEDBACK:
			style = styleFeedback;
			break;
		case STYLE_ERROR:
			style = styleError;
			break;
		default:
			style = styleNormal;
		}

		try {
			document.insertString(document.getLength(), text, style);
		} catch (BadLocationException e) {
			System.out.println("Error displaying text: " + e);
			return;
		}
	}

	/*
	 * Clear all the displayed text in TextView
	 */
	public void clear() {

		try {
			document.remove(0, document.getLength());
		} catch (BadLocationException e) {
			return;
		}
	}

	public void scrollToTop() {
		textPane.setCaretPosition(0);
	}

	public void scrollToBottom() {
		textPane.setCaretPosition(document.getLength());
	}

	private void initializeStyles() {

		styleNormal = createStyle(STYLE_NORMAL, Color.BLACK, FONT_SIZE_NORMAL, false);
		styleFeedback = createStyle(STYLE_FEEDBACK, Color.BLUE, FONT_SIZE_NORMAL, true);
		styleError = createStyle(STYLE_ERROR, Color.RED, FONT_SIZE_NORMAL, true);
	}

	private Style createStyle(String name, Color color, int size, boolean isBold) {

		Style style = document.addStyle(name, null);

		StyleConstants.setFontSize(style, size);
		StyleConstants.setForeground(style, color);
		StyleConstants.setBold(style, isBold);
		StyleConstants.setFontFamily(style, DEFAULT_FONT);

		return style;
	}
}