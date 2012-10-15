package jfmi.app;

import java.util.Comparator;
import java.io.Serializable;


/** A FileTaggingTagComparator compares FileTagging objects according to the
  value of their tag field.
  */
public class FileTaggingTagComparator implements
	Comparator<FileTagging>, 
	Serializable {

	public static final long serialVersionUID = 12102012L;	//dd/mm/yyyy

	/** Compares the specified FileTagging instances for order, using the value
	  of their tags.
	  @param o1 a first object to be compared
	  @param o2 a second object to be compared
	  @return a negative integer, zero, or a positive integer, as the first
			argument is less than, equal to, or greater than the second
	  */
	public int compare(FileTagging o1, FileTagging o2) {
		return o1.getTag().compareTo(o2.getTag());
	}	
}

