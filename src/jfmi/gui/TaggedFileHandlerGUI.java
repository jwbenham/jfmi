package jfmi.gui;

import java.io.File;
import javax.swing.JFileChooser;

import jfmi.app.TaggedFile;
import jfmi.control.JFMIApp;
import jfmi.control.TaggedFileHandler;

/** Provides an interface between a TaggedFileHandler and the user.
  */
public class TaggedFileHandlerGUI {

	// PRIVATE INSTANCE Fields
	private TaggedFileHandler fileHandler;

	private JFileChooser fileChooser;
	private TaggedFileEditDialog fileViewer;
	private JFMIFrame jfmiGUI;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a TaggedFileHandlerGUI that will use the specified JFMIApp
	  for determining which GUIs and handlers it is associated with.
	  @param jfmiGUI_ frame to be used for a component parent
	  @param handler_ TaggedFileHandler to be associated with this object
	  @throws IllegalArgumentException if jfmiGUI_ or handler_ is null
	  */
	public TaggedFileHandlerGUI(JFMIFrame jfmiGUI_, TaggedFileHandler handler_)
	{
		init(jfmiGUI_, handler_);
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
	public TaggedFileEditDialog getFileViewer()
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
	  @param jfmiGUI_ frame to be used for a component parent
	  @param handler_ TaggedFileHandler to be associated with this object
	  @throws IllegalArgumentException if jfmiGUI_ or fileHandler_ is null
	  */
	private final void init(JFMIFrame jfmiGUI_, TaggedFileHandler fileHandler_)
	{
		setFileHandler(fileHandler_);
		setJFMIGUI(jfmiGUI_);

		fileViewer = new TaggedFileEditDialog(jfmiGUI, fileHandler);
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
