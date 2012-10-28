package jfmi.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.Vector;
import javax.swing.AbstractListModel;

/** Extends AbstractListModel to provide a mutable ListModel backed by a
  List. The elements in the ListModel must implement the Comparable interface.
  */
public class MutableListModel<E extends Comparable<E>> 
	extends AbstractListModel<E> {

	// Private Instance Fields
	private List<E> data;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs an instance backed by a Vector<E>.
	  */
	public MutableListModel()
	{
		this(new Vector<E>());
	}

	/** Constructs an instance backed by the specified List;
	  */
	public MutableListModel(List<E> c)
	{
		if (c == null) {
			throw new NullPointerException("c cannot be null");
		}

		data = c;
	}

	/** Adds an element to the end of the list.
	  @param e the element to add to the list
	  */
	public void add(E e)
	{
		data.add(e);
		int end = data.size() - 1;
		fireIntervalAdded(this, end, end);
	}

	/** Adds an element to the list at the specified index.
	  @param index the index at which to insert the element
	  @param e the element to be added
	  */
	public void add(int index, E e)
	{
		data.add(index, e);
		fireIntervalAdded(this, index, index); 
	}
	
	/** Accesses the element at the specified index.
	  @param index the index of the element to access
	  @return the element at the specified index
	  */
	public E getElementAt(int index)
	{
		return data.get(index);
	}

	/** Returns the number of elements in the backing List.
	  @return size of the backing List
	  */
	public int getSize()
	{
		return data.size();
	}

	/** Returns true if the list is empty.
	  */
	public boolean isEmpty()
	{
		return data.isEmpty();
	}

	/** Removes the element at the specified index.
	  @param index the index of the element to remove
	  @return the removed element
	  */
	public E remove(int index)
	{
		E element = data.remove(index);
		fireIntervalRemoved(this, index, index);
		return element;
	}

	/** Removes an element from the end of the list of data.
	  @return the removed element
	  */
	public E removeFromEnd()
	{
		E element = data.remove(data.size() - 1);
		int index = data.size();
		fireIntervalRemoved(this, index, index); 
		return element;
	}

	/** Reverses the order of the elements in the list. */
	public void reverse()
	{
		Collections.reverse(data);
		fireContentsChanged(this, 0, data.size());
	}

	/** Sorts the list data according to the natural ordering of its elements.
	  */
	public void sort()
	{
		Collections.sort(data);
		fireContentsChanged(this, 0, data.size());
	}

	/** Sorts the list according to the specified comparator.
	  @param comp the comparator used to sort the list data
	  */
	public void sort(Comparator<E> comp)
	{
		Collections.sort(data, comp);
		fireContentsChanged(this, 0, data.size());
	}

}
