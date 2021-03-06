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

	/** Constructs a default instance backed by a Vector<E>.
	  */
	public MutableListModel()
	{
		this(new Vector<E>());
	}

	/** Constructs an instance backed by the elements in the specified
	  Collection.
	  @param collection the non-null Collection that will be used to back the 
	  		list model by creating a new List from its elements
	  */
	public MutableListModel(Collection<E> collection)
	{
		if (collection == null) {
			throw new NullPointerException("collection cannot be null");
		}

		data = new Vector<E>(collection);
	}

	/** Constructs an instance backed by the specified non-null List.
	  @param list the list to use as this model's backing container
	  */
	public MutableListModel(List<E> list)
	{
		if (list == null) {
			throw new NullPointerException("list cannot be null");
		}

		data = list;
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

	/** Returns true if the list model contains the specified element.
	  @param element the element to search for
	  @return true if element is in the list model
	  */
	public boolean contains(E element)
	{
		return data.contains(element);
	}

	/** Returns a copy of the model's backing list.
	  @return a copy of the model's backing data list
	  */
	public List<E> getDataCopy()
	{
		return new Vector<E>(data);
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

	/** Removes the specified element from the list, if it is present.
	  @param element the element to be removed
	  @return true if the element was removed
	  */
	public boolean remove(E element)
	{
		int indexOf = data.indexOf(element);		
		
		if (indexOf == -1) {
			return false;
		} else {
			remove(indexOf);
			return true;
		}
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
