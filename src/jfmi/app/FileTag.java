package jfmi.app;

/** Represents a tag that the JFMI application can apply to a file.
  */
public class FileTag implements Comparable<FileTag> {

	// PRIVATE INSTANCE Fields
	String tag;


	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a default FileTag with an empty tag value.
	  */
	public FileTag()
	{
		this("");
	}

	/** Constructs a FileTag with the specified tag value.
	  @param newTag the value for the tag field
	  */
	public FileTag(String newTag)
	{
		setTag(newTag);
	}

	/** Tests this instance for equality with another FileTag.
	  @param o a FileTag to test for equality against
	  @return true if this instance is equal to the parameter
	  */
	public boolean equals(FileTag o)
	{
		return tag.equals(o.tag);	
	}

	/** Retrieves the FileTag's value.
	  @return the value of the tag field
	  */
	public String getTag()
	{
		return tag;
	}

	/** Sets the value of this instance's tag. If the argument is null, the
	  tag value is set to the empty string.
	  @param newTag the new value for the tag field
	  */
	public void setTag(String newTag)
	{
		if (newTag == null) {
			tag = "";
		} else {
			tag = newTag;
		}
	}

	/** Returns a String representation of the FileTag instance.
	  @return this instance's tag value
	  */
	public String toString()
	{
		return getTag();
	}


	//************************************************************
	// IMPLEMENTATION of Comparable<FileTag>
	//************************************************************

	/** This function implements a natural ordering for instances of the
	  FileTag class.
	  @param o a FileTag to compare this instance against for ordering
	  @return -1, 0, 1 as this instance is less than, equal to, greater than
	  		the argument
	  */
	public int compareTo(FileTag o)
	{
		return tag.compareTo(o.tag);
	}


}
