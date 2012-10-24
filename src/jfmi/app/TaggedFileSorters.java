package jfmi.app;

import java.io.Serializable;
import java.util.Comparator;

public class TaggedFileSorters {

	/** A SQLPrimaryKeySorter compares TaggedFile objects according to
	  their primary keys in the an SQL database. Non-primary key columns
	  are not considered when comparing objects. Note that this comparator
	  imposes orderings that are inconsistent with equals.
	  */
	public static class SQLPrimaryKeySorter implements
		Comparator<TaggedFile>, 
		Serializable {

		public static final long serialVersionUID = 12102012L;	//dd/mm/yyyy

		/** Compares the specified TaggedFile instances for order, using the
		  fields which correspond to their primary keys in the SQLiteRepository.
		  This function essentially replicates the natural ordering of the
		  TaggedFile primary key.
		  @param o1 a first object to be compared
		  @param o2 a second object to be compared
		  @return a negative integer, zero, or a positive integer, as the first
				argument is less than, equal to, or greater than the second
		  */
		public int compare(TaggedFile o1, TaggedFile o2) {
			return o1.getFileId() - o2.getFileId();
		}
	}

	/* Private to keep class from being instantiated. */
	private TaggedFileSorters() {
	}
	
}
