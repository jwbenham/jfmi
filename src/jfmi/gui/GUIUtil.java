package jfmi.gui;

import javax.swing.JOptionPane;

/** Provides a selection of utility methods for working with Java GUIs.
  */
public final class GUIUtil {

	/** Uses the JOptionPane.showMessageDialog() method to display the
	  specified message.
	  @param message The message to display.
	  */
	public static void showErrorDialog(String message)
	{
		// message type: ERROR_MESSAGE
		JOptionPane.showMessageDialog(
			null, 
			message,
			"JFMI Error",
			JOptionPane.ERROR_MESSAGE
		);
	}

}
