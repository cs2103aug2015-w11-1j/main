import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * TextView is an UI component for displaying text in different styles e.g.
 * font, size, text colour, background colour etc.
 */
public class TextView extends JTextPane {

	// Auto-generated code
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 400;
	private static final int HEIGHT = 500;
	
	private StyledDocument document = getStyledDocument();

	public TextView() {
		setEditable(false);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	/**
	 * Display the specified text with default style in the next line 
	 * @param text is the text to be displayed
	 */
	public void display(String text) {

		Style style = document.addStyle(null, null);
		StyleConstants.setForeground(style, Color.BLACK);

		try {
			document.insertString(document.getLength(), text + "\n", style);
		} catch (BadLocationException e) {
			System.out.println("Error displaying text: " + e);
			return;
		}
	}
	
	/**
	 * Clear all the displayed text in TextView
	 */
	public void clearText(){
		
		try {
			document.remove(0, document.getLength());
		} catch (BadLocationException e) {
			return;
		}
	}
	
	/**
	 * TODO display text in different styles
	 */
}
