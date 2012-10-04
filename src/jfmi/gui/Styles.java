package jfmi.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;

/** A utility class that provides style settings to the rest of the
  application.
  */
public final class Styles {
	/** The default font for buttons. */
	public static final Font DEFAULT_BUTTON_FONT;
	/** The default font color for buttons. */
	public static final Color DEFAULT_BUTTON_FONT_COLOR;
	/** The default size for buttons. */
	public static final Dimension DEFAULT_BUTTON_SIZE;

	/** Font for a level 3 header. */
	public static final Font H3_FONT;
	/** Font for a level 4 header. */
	public static final Font H4_FONT;

	/** Sans-serif, plain, 12 pt font. */
	public static final Font SS_PLAIN_12;

	/** Color used to indicate a button does something dangerous. */
	public static final Color DANGER_COLOR = Color.RED;
	/** A gray darker than Color.DARK_GRAY. */
	public static final Color DARKER_GRAY;

	/** Color used for a level 3 header. */
	public static final Color H3_COLOR;
	/** Color used for a level 4 header. */
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
	  @param com The component to modify.
	  @param f This will be set as the component's Font.
	  @param fc This will be set as the component's foreground color.
	  @param d Dimension specifying the size of the component.
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
	  @param b The JButton to be modified.
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
	  @param com The component to give "header" styles.
	  @param hlevel specifies a "header level" - similar to HTML header levels,
	  		where there are 1-6, with lower numbers specifying more emphasis.
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
	  @param com The component to give header styles.
	  @param f The Font to be assigned to the component.
	  @param c The foreground color to be given to the component.
	  */
	private static void setHeaderStylesAux(Component com, Font f, Color c)
	{
		com.setFont(f);
		com.setForeground(c);
	}

	/** Sets all of the size properties of a Component instance.
	  @param com The component to modify.
	  @param dim Dimension indicating the size values to be used.
	  */
	public static void setAllSizes(Component com, Dimension dim)
	{
		com.setMinimumSize(dim);
		com.setMaximumSize(dim);
		com.setPreferredSize(dim);
		com.setSize(dim);
	}
}
