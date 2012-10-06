package jfmi.control;

/** Represents a tag that the JFMI application can apply to a file.
  */
public class FileTag {

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


	/** Retrieves the FileTag's value.
	  @return the value of the tag field
	  */
	public String getTag()
	{
		return tag;
	}

	/** Sets the value of this instance's tag.
	  @param newTag the new value for the tag field
	  */
	public void setTag(String newTag)
	{
		tag = newTag;
	}
}
