package jfmi.control;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

import jfmi.database.TagRecord;
import jfmi.gui.TagHandlerDialog;

public class TagHandler {

	private JFMIApp jfmiApp;
	private TagHandlerDialog tagHandlerDialog;

	private List<TagRecord> tagRecordList;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Ctor: JFMIApp.
	  */
	public TagHandler(JFMIApp mapp_) 
	{
		setMapp(mapp_);

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
	 */
	public void updateTagRecordList() throws SQLException
	{
		tagRecordList = jfmiApp.getJFMIDatabase().getAllTagRecords();	
	}

	/** Accessor for the tagRecordList field. This may be null. The
	  updateTagRecordList() method should be called first to guarantee
	  the tagRecordList is up to date.
	  @return List<TagRecord>, may be null.
	  */
	public List<TagRecord> getTagRecordList()
	{
		return tagRecordList;
	}

	/** Mutator for the mapp field.
	  */
	public final void setMapp(JFMIApp mapp_)
	{
		if (mapp_ == null) {
			throw new IllegalArgumentException("error: mapp_ is null");
		}

		jfmiApp = mapp_;
	}


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

}
