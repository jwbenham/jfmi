package jfmi.gui;

import java.io.File;
import javax.swing.JFileChooser;

/** Provides an interface between a TaggedFileHandler and the user.
  */
public class TaggedFileHandlerGUI {

	// PRIVATE INSTANCE Fields
	private JFileChooser fileChooser;
	private JFMIFrame jfmiGUI;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a TaggedFileHandlerGUI that will use the specified
	  JFMIFrame as a parent component.
	  */
	public TaggedFileHandlerGUI(JFMIFrame jfmiGUI_)
	{
		init(jfmiGUI_);
		initFileChooser();
	}

	/** Displays a window for the user to select one or more files and/or
	  directories.
	  @return an array of selected files if the user indicated approval, else
	  		null if the user cancelled
	  */
	public File[] displayFileChooser()
	{
		int returnVal = fileChooser.showDialog(jfmiGUI, "Choose File");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFiles();
		} else {
			return null;
		}
	}

	/** Prompts a user to confirm an action with the specified message, and 
	  returns the user's decision.
	  @param confirmMsg the message to display to the user
	  @return true if the user confirmed
	  */
	public boolean getConfirmation(String confirmMsg)
	{
		return jfmiGUI.getConfirmation(confirmMsg);	
	}
	
	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Initialize the instance.
	  @param jfmiGUI_ the JFMIFrame to use as a parent component
	  @throws IllegalArgumentException if jfmiGUI is null
	  */
	private final void init(JFMIFrame jfmiGUI_)
	{
		if (jfmiGUI_ == null) {
			throw new IllegalArgumentException("jfmiGUI_ cannot be null");
		}

		jfmiGUI = jfmiGUI_;
	}

	/** Initialize the file chooser.
	  */
	private final void initFileChooser()
	{
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Add a New File");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

}
