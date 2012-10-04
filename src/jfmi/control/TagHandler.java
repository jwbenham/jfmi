package jfmi.control;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

import jfmi.database.TagRecord;
import jfmi.gui.TagHandlerDialog;

/** A TagHandler handles application logic concerned with adding, removing,
  and updating tag records in the database. A TagHandler uses its parent
  JFMIApp's database to save tag changes, and a TagHandlerDialog to interface
  with a user.
  */
public class TagHandler {

	private JFMIApp jfmiApp;
	private TagHandlerDialog tagHandlerDialog;

	private List<TagRecord> tagRecordList;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Construct a TagHandler with the specified JFMIApp as its parent.
	  @param jfmiApp_ JFMIApp reference used as this instance's parent.
	  */
	public TagHandler(JFMIApp jfmiApp_) 
	{
		setJFMIApp(jfmiApp_);

		tagHandlerDialog = new TagHandlerDialog(jfmiApp.getJFMIGUI(), this);
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

	/** Updates the list of tags from the database. 
	  @throws SQLException If an SQL error occurs.
	 */
	public void updateTagRecordList() throws SQLException
	{
		tagRecordList = jfmiApp.getJFMIDatabase().getAllTagRecords();	
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


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

}
