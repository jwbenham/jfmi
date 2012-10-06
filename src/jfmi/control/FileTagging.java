package jfmi.control;


/** Represents an association between a file and a tag.
  */
public class FileTagging {

	// PRIVATE INSTANCE Fields
	int taggingId;
	int fileId;
	String tag;
	String comment;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a default FileTagging with negative tagging and file ids,
	  and empty tag and comment.
	  */
	public FileTagging()
	{
		this(-1, -1, "", "");
	}

	/** Constructs a new FileTagging with the specified tagging id, file id,
	  tag, and comment.
	  @param tid value for taggingId
	  @param fid value for fileId
	  @param tagVal value for tag
	  @param commentVal value for comment
	  */
	public FileTagging(int tid, int fid, String tagVal, String commentVal)
	{
		setTaggingId(tid);
		setFileId(fid);
		setTag(tagVal);
		setComment(commentVal);
	}

	/** Retrieves the tagging's id.
	  @return value of the taggingId field.
	  */
	public int getTaggingId()
	{
		return taggingId;
	}

	/** Retrieves the id of the tagging's associated file.
	  @return the value of the fileId field
	  */
	public int getFileId()
	{
		return fileId;
	}

	/** Retrieves the tag associated with this tagging.
	  @return the value of the tag field
	  */
	public String getTag()
	{
		return tag;
	}

	/** Retrieves the comment associated with this tagging.
	  @return the value of the comment field
	  */
	public String getComment()
	{
		return comment;
	}

	/** Sets the id of this tagging.
	  @param id the new value for the taggingId field
	  */
	public void setTaggingId(int id)
	{
		taggingId = id;
	}

	/** Sets the id of the file associated with this tagging.
	  @param id the new value for the fileId field
	  */
	public void setFileId(int id)
	{
		fileId = id;
	}

	/** Sets the value of the tag associated with this tagging.
	  @param newTag the new value for the tag field
	  */
	public void setTag(String newTag)
	{
		tag = newTag;
	}

	/** Sets the value of the comment associated with this tagging.
	  @param newComment the new value for the comment field
	  */
	public void setComment(String newComment)
	{
		comment = newComment;
	}
}
