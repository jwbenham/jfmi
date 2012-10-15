package jfmi.app;

import java.util.Comparator;
import java.io.Serializable;


/** A FileTaggingComparator compares FileTagging objects according to
  their primary keys in the SQLiteRepository. Non-primary key columns
  are not considered when comparing objects. Note that this comparator
  imposes orderings that are inconsistent with equals.
  */
public class FileTaggingComparator implements
	Comparator<FileTagging>, 
	Serializable {

	public static final long serialVersionUID = 12102012L;	//dd/mm/yyyy

	/** Compares the specified FileTagging instances for order, using the
	  fields which correspond to their primary keys in the SQLiteRepository.
	  This function essentially replicates the natural ordering of the
	  FileTagging primary key.
	  @param o1 a first object to be compared
	  @param o2 a second object to be compared
	  @return a negative integer, zero, or a positive integer, as the first
			argument is less than, equal to, or greater than the second
	  */
	public int compare(FileTagging o1, FileTagging o2) {
		int o1TID = o1.getTaggingId();
		int o2TID = o2.getTaggingId();

		if (
			o1TID == FileTagging.DEFAULT_TAGGING_ID
			|| o2TID == FileTagging.DEFAULT_TAGGING_ID
		) {

			return o1.getTag().compareTo(o2.getTag());

		} else {
			return o1TID - o2TID;
		}
	}
	
}

