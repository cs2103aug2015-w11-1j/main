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

	public static final String STYLE_NORMAL = "normal";
	public static final String STYLE_BOLD = "bold";
	public static final String STYLE_TITLE = "title";

	// Auto-generated code
	private static final long serialVersionUID = 1L;

	private static final int FONT_SIZE_NORMAL = 14;
	private static final int FONT_SIZE_TITLE = 16;
	
	private static final String DEFAULT_FONT = "calibri";

	private StyledDocument document = getStyledDocument();

	private Style styleNormal;
	private Style styleTitle;
	private Style styleBold;

	public TextView(int width, int height) {

		setEditable(false);
		setPreferredSize(new Dimension(width, height));

		initializeStyles();
	}

	/**
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
		case STYLE_TITLE:
			style = styleTitle;
			break;
		case STYLE_BOLD:
			style = styleBold;
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

	/**
	 * Clear all the displayed text in TextView
	 */
	public void clear() {

		try {
			document.remove(0, document.getLength());
		} catch (BadLocationException e) {
			return;
		}
	}
	
	private void initializeStyles(){
		
		styleNormal = createStyle(STYLE_NORMAL, FONT_SIZE_NORMAL, false);
		styleBold = createStyle(STYLE_BOLD, FONT_SIZE_NORMAL, true);
		styleTitle = createStyle(STYLE_TITLE, FONT_SIZE_TITLE, true);
	}
	
	private Style createStyle(String name, int size, boolean isBold) {
		
		Style style = document.addStyle(name, null);
		
		StyleConstants.setFontSize(style, size);
		StyleConstants.setBold(style, isBold);
		StyleConstants.setFontFamily(style, DEFAULT_FONT);
		
		return style;
	}
}