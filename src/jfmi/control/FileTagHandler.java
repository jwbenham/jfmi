package jfmi.control;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

import jfmi.database.TagRecord;
import jfmi.gui.FileTagHandlerDialog;

/** A FileTagHandler handles application logic concerned with adding, removing,
  and updating tag records in the database. A FileTagHandler uses its parent
  JFMIApp's database to save tag changes, and a FileTagHandlerDialog to interface
  with a user.
  */
public class FileTagHandler {

	private JFMIApp jfmiApp;
	private FileTagHandlerDialog tagHandlerDialog;

	private List<TagRecord> tagRecordList;

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

		tagRecordList = null;
	}

	/** When called, displays an interface to allow the user to
	  add/remove tags.
	  */
	public void manageTags()
	{
		tagHandlerDialog.setVisible(true);
	}

	/** Accessor for the tagRecordList field. This may be null. The
	  updateTagRecordList() method should be called first to guarantee
	  the tagRecordList is up to date.
	  @return Access to the list of tag records this instance has retrieved
	  		from the application database.
	  */
	public List<TagRecord> getTagRecordList()
	{
		return tagRecordList;
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
