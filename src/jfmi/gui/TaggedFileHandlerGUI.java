package jfmi.gui;

import java.io.File;
import javax.swing.JFileChooser;

import jfmi.control.JFMIApp;
import jfmi.control.TaggedFileHandler;
import jfmi.control.TaggedFile;

/** Provides an interface between a TaggedFileHandler and the user.
  */
public class TaggedFileHandlerGUI {

	// PRIVATE INSTANCE Fields
	private TaggedFileHandler fileHandler;

	private JFileChooser fileChooser;
	private TaggedFileViewDialog fileViewer;
	private JFMIFrame jfmiGUI;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a TaggedFileHandlerGUI that will use the specified JFMIApp
	  for determining which GUIs and handlers it is associated with.
	  @param jfmiApp_ the JFMIApp that this instance should access other GUIs
	  				and handlers from
	  */
	public TaggedFileHandlerGUI(JFMIApp jfmiApp_)
	{
		init(jfmiApp_);
		initFileChooser();
	}

	/** Displays a window for the user to select one or more files and/or
	  directories.
	  @return an array of selected files if the user indicated approval, else
	  		null if the user cancelled
	  */
	public File[] displayFileChooser()
	{
		return displayFileChooser(null);
	}

	/** Displays a window for the user to select one or more files and/or
	  directories.
	  @param currentPath this file's path will be used as the current path
	  @return an array of selected files if the user indicated approval, else
	  		null if the user cancelled
	  */
	public File[] displayFileChooser(File currentPath)
	{
		if (currentPath != null) {
			fileChooser.setCurrentDirectory(currentPath);
		}

		int returnVal = fileChooser.showDialog(jfmiGUI, "Choose File");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFiles();
		} else {
			return null;
		}
	}

	/** Provides access to the file viewer.
	  @return a reference to the file viewer
	  */
	public TaggedFileViewDialog getFileViewer()
	{
		return fileViewer;
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

	/** Sets this object's associated TaggedFileHandler.
	  @param fileHandler_ the file handler to associate with this instance
	  @throws IllegalArgumentException if fileHandler_ is null
	  */
	public void setFileHandler(TaggedFileHandler fileHandler_)
	{
		if (fileHandler_ == null) {
			throw new IllegalArgumentException("fileHandler_ cannot be null");
		}

		fileHandler = fileHandler_;
	}

	/** Sets the JFMIFrame that this instance will use as the parent component
	  for some of the components it generates.
	  @param jfmiGUI_ the JFMIFrame to use as a parent component
	  @throws IllegalArgumentException if jfmiGUI_ is null
	  */
	public void setJFMIGUI(JFMIFrame jfmiGUI_)
	{
		if (jfmiGUI_ == null) {
			throw new IllegalArgumentException("jfmiGUI_ cannot be null");
		}

		jfmiGUI = jfmiGUI_;
	}

	
	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Initialize the instance.
	  @param jfmiApp_ the JFMIApp that this instance should access other GUIs
	  				and handlers from
	  @throws IllegalArgumentException if jfmiGUI_ or handler_ is null
	  */
	private final void init(JFMIApp jfmiApp_)
	{
		setFileHandler(jfmiApp_.getFileHandler());
		setJFMIGUI(jfmiApp_.getJFMIGUI());

		fileViewer = new TaggedFileViewDialog(jfmiGUI, 
											  fileHandler, 
											  jfmiApp_.getTagHandler());
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
