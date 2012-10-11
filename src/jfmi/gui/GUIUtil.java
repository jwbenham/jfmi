package jfmi.gui;

import javax.swing.JOptionPane;

/** Provides a selection of utility methods for working with Java GUIs.
  */
public final class GUIUtil {

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	/** Uses the JOptionPane.showMessageDialog() method to display the
	  specified alert message.
	  @param message The message to display.
	  */
	public static void showAlert(String message)
	{
		JOptionPane.showMessageDialog(
			null, 
			message,
			"JFMI Alert",
			JOptionPane.INFORMATION_MESSAGE
		);
	}

	/** Uses the JOptionPane.showMessageDialog() method to display the
	  specified error message.
	  @param message The message to display.
	  */
	public static void showErrorDialog(String message)
	{
		JOptionPane.showMessageDialog(
			null, 
			message,
			"JFMI Error",
			JOptionPane.ERROR_MESSAGE
		);
	}

	/** Uses the JOptionPane.showMessageDialog() method to display the
	  specified message and details.
	  @param message the message to display
	  @param details additional details 
	  */
	public static void showErrorDialog(String message, String details)
	{
		StringBuilder str = new StringBuilder(message);

		if (details != null) {
			str.append("\n----------------------\n");
			str.append("Details");
			str.append("\n----------------------\n");
			str.append(details);
		}

		JOptionPane.showMessageDialog(
			null, 
			str.toString(),
			"JFMI Error",
			JOptionPane.ERROR_MESSAGE
		);
	}


	/** Utility method that can be used to display a debug message.
	  @param message The message to display
	  */
	public static void debug(String message)
	{
		JOptionPane.showMessageDialog(null, message);
	}


	//************************************************************
	// PRIVATE CLASS Methods
	//************************************************************


}
