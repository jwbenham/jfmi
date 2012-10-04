package jfmi.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;

public final class Styles {
	public static final Font DEFAULT_BUTTON_FONT;
	public static final Color DEFAULT_BUTTON_FONT_COLOR;
	public static final Dimension DEFAULT_BUTTON_SIZE;

	public static final Font H3_FONT;
	public static final Font H4_FONT;

	public static final Font SS_PLAIN_12;

	public static final Color DANGER_COLOR = Color.RED;
	public static final Color DARKER_GRAY;

	public static final Color H3_COLOR;
	public static final Color H4_COLOR;
   
	static {
		DEFAULT_BUTTON_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		DEFAULT_BUTTON_FONT_COLOR = Color.DARK_GRAY;
		DEFAULT_BUTTON_SIZE = new Dimension(100, 30);

		H3_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		H4_FONT = new Font(Font.SANS_SERIF, Font.ITALIC, 11);

		SS_PLAIN_12 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		DARKER_GRAY = new Color(30, 30, 30);

		H3_COLOR = Color.CYAN;
		H4_COLOR = Color.MAGENTA;
	}

	/** Sets the visual properties of a Component.
	  These include the font, background, foreground, and border,
	  and size.
	  */
	public static void setComponentStyles(
		Component com, Font f, Color fc, Dimension d
	)
	{
		if (com == null) {
			return;
		}	

		if (f != null) {
			com.setFont(f);	
		}

		if (fc != null) {
			com.setForeground(fc);
		}

		if (d != null) {
			setAllSizes(com, d);
		}
	}

	/** Sets the visual properties of a JButton to defaults.
	  */
	public static void setDefaultJButtonStyles(JButton b)
	{
		if (b == null) {
			return;
		}

		b.setFont(DEFAULT_BUTTON_FONT);
		b.setForeground(DEFAULT_BUTTON_FONT_COLOR);
		b.setSize(DEFAULT_BUTTON_SIZE);
	}

	/** Sets the font and foreground properties of a Component considered
	  to be a "header".
	  */
	public static void setHeaderStyles(Component com, int hlevel)
	{
		if (com == null) {
			return;
		}

		switch (hlevel) {
		case 3:
			setHeaderStylesAux(com, H3_FONT, H3_COLOR);
			break;
		case 4:
			setHeaderStylesAux(com, H4_FONT, H4_COLOR);
			break;
		default:
			return;
		}
	}

	/** Auxiliary function for setHeaderStyles().
	  */
	private static void setHeaderStylesAux(Component com, Font f, Color c)
	{
		com.setFont(f);
		com.setForeground(c);
	}

	/** Sets all of the size properties of a Component instance.
	  */
	public static void setAllSizes(Component com, Dimension dim)
	{
		com.setMinimumSize(dim);
		com.setMaximumSize(dim);
		com.setPreferredSize(dim);
		com.setSize(dim);
	}
}
