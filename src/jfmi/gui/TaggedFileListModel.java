package jfmi.gui;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

	/** Adds a TaggedFile to the end of the list data.
	  @param file the TaggedFile to add to the list
	  */
	public void add(TaggedFile file)
	{
		listData.add(file);
		int end = listData.size() - 1;
		fireIntervalAdded(this, end, end);
	}

	/** Adds a TaggedFile to the list data at the specified index.
	  @param index the index at which to insert the TaggedFile
	  @param file the TaggedFile to add
	  */
	public void add(int index, TaggedFile file)
	{
		listData.add(index, file);
		fireIntervalAdded(this, index, index); 
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

	/** Returns true if the list data is empty.
	  */
	public boolean isEmpty()
	{
		return listData.isEmpty();
	}

	/** Removes the element at the specified index.
	  @param index the index of the element to remove
	  @return the removed element
	  */
	public TaggedFile remove(int index)
	{
		TaggedFile file = listData.remove(index);
		fireIntervalRemoved(this, index, index);
		return file;
	}

	/** Removes an element from the end of the list of data.
	  @return the removed element
	  */
	public TaggedFile removeFromEnd()
	{
		TaggedFile file = listData.remove(listData.size() - 1);
		int index = listData.size();
		fireIntervalRemoved(this, index, index); 
		return file;
	}

	/** Reverses the order of the elements in the list. */
	public void reverse()
	{
		Collections.reverse(listData);
	}

	/** Sorts the list data according to the natural ordering of its elements.
	  */
	public void sort()
	{
		Collections.sort(listData);
	}

	/** Sorts the list data according to the specified comparator.
	  @param comp the comparator used to sort the list data
	  */
	public void sort(Comparator<TaggedFile> comp)
	{
		Collections.sort(listData, comp);
	}

}
