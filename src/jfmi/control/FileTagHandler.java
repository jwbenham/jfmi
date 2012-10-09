package jfmi.control;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

import jfmi.control.FileTag;
import jfmi.gui.FileTagHandlerDialog;

/** A FileTagHandler handles application logic concerned with adding, removing,
  and updating tag records in the database. A FileTagHandler uses its parent
  JFMIApp's database to save tag changes, and a FileTagHandlerDialog to interface
  with a user.
  */
public class FileTagHandler {

	private JFMIApp jfmiApp;
	private FileTagHandlerDialog tagHandlerDialog;

	private List<FileTag> fileTagList;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Construct a FileTagHandler with the specified JFMIApp as its parent.
	  @param jfmiApp_ JFMIApp reference used as this instance's parent.
	  */
	public FileTagHandler(JFMIApp jfmiApp_) 
	{
		setJFMIApp(jfmiApp_);

		tagHandlerDialog = new FileTagHandlerDialog(jfmiApp.getJFMIGUI(), this);
		tagHandlerDialog.setVisible(false);

		fileTagList = null;
	}

	/** When called, displays an interface to allow the user to
	  add/remove tags.
	  */
	public void manageTags()
	{
		tagHandlerDialog.setVisible(true);
	}

	/** Accessor for the fileTagList field. This may be null. The
	  updateFileTagList() method should be called first to guarantee
	  the fileTagList is up to date.
	  @return Access to the list of tag records this instance has retrieved
	  		from the application database.
	  */
	public List<FileTag> getFileTagList()
	{
		return fileTagList;
	}

	/** Mutator for the jfmiApp field.
	  */
	public final void setJFMIApp(JFMIApp jfmiApp_)
	{
		if (jfmiApp_ == null) {
			throw new IllegalArgumentException("error: jfmiApp_ is null");
		}

		jfmiApp = jfmiApp_;
	}


}
