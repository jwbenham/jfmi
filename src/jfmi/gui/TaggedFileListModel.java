package jfmi.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

import jfmi.app.TaggedFile;

/** A TaggedFileListModel acts as a data model for a Swing JList. Unlike the
  DefaultListModel, a TaggedFileListModel allows a client to add/remove list
  elements, and sort the list of elements.
  */
public class TaggedFileListModel extends AbstractListModel<TaggedFile> {

	// Private Instance Fields
	private List<TaggedFile> listData;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs a default instance using an ArrayList<TaggedFile> as the
	  underlying Collection object.
	  */
	public TaggedFileListModel()
	{
		this(new ArrayList<TaggedFile>());
	}

	/** Constructs a new instance using the specified non-null List<TaggedFile>.
	  @param list the List to use as this instance's data
	  @throws NullPointerException if list is null
	  */
	public TaggedFileListModel(List<TaggedFile> list)
	{
		if (list == null) {
			throw new NullPointerException("list cannot be null");
		}

		listData = list;
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
