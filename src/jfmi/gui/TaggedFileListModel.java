package jfmi.gui;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

import jfmi.app.TaggedFile;

/** A TaggedFileListModel acts as a data model for a Swing JList.
  */
public class TaggedFileListModel extends AbstractListModel<TaggedFile> {

	// Private Instance Fields
	private ArrayList<TaggedFile> listData;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs a default instance.
	  */
	public TaggedFileListModel()
	{
		listData = new ArrayList<TaggedFile>();
	}
	

	/** Returns a reference to the value at the specified index.
	  @param index the index of the value to access
	  @return a reference to the indexed value
	  @throws IndexOutOfBoundsException if the index is out of bounds
	  */
	public TaggedFile getElementAt(int index)
	{
		return listData.get(index);
	}

	/** Gets the size of the list data model.
	  @return the size of the list data model
	  */
	public int getSize()
	{
		return listData.size();
	}

	//************************************************************
	// PRIVATE Instance Methods
	//************************************************************



}
