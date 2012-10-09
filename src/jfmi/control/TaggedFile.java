package jfmi.control;

import java.io.File;

import java.util.List;
import java.util.Arrays;


/** Represents a file in the file system, along with its associated taggings (if
  any).
  */
public class TaggedFile {

	// PRIVATE INSTANCE Fields
	private int fileId;
	private File file;
	private List<FileTagging> fileTaggings;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Construct a default TaggedFile with negative id, an empty path, "", and
	  null list of taggings.
	  */
	public TaggedFile()
	{
		this(-1, "", null);
	}

	/** Construct a TaggedFile with the specified id, path, and list of
	  taggings.
	  @param id the new object's id
	  @param path path of the new object
	  @param taggings list of the file's taggings
	  */
	public TaggedFile(int id, String path, List<FileTagging> taggings)
	{
		this(id, new File(path), taggings);	
	}

	/** Construct a TaggedFile with the specified id, path, and list of
	  taggings.
	  @param id the new object's id
	  @param file File which has the path of the new object
	  @param taggings list of the file's taggings
	  */
	public TaggedFile(int id, File file, List<FileTagging> taggings)
	{
		setFileId(id);
		setFile(file);
		setFileTaggings(taggings);
	}

	/** Access the file field.
	  @return the instance's file field
	  */
	public File getFile()
	{
		return file;
	}

	/** Access the fileId field.
	  @return the file's integer id
	  */
	public int getFileId()
	{
		return fileId;
	}

	/** Access the file name.
	  @return the String value of the file's name
	  */
	public String getFileName() 
	{
		return file.getName();
	}

	/** Access the file path.
	  @return the String value of the file's path
	  */
	public String getFilePath()
	{
		return file.getPath();
	}

	/** Returns this TaggedFile's associated tags as a String.
	  @return the tags associated with this file as a String
	  */
	public String getFileTagsAsString()
	{
		StringBuilder str = new StringBuilder("");

		for (FileTagging ftagging : fileTaggings) {
			str.append(" [");
			str.append(ftagging.getTag());
			str.append("] ");
		}

		return str.toString();
	}

	/** Sets this instance's file field.
	  @param file File to use as this instance's - can be null
	  */
	public void setFile(File file)
	{
		this.file = file;
	}

	/** Sets the file id.
	  @param id integer to use as this file's id
	  */
	public void setFileId(int id)
	{
		fileId = id;
	}

	/** Sets this instance's file path.
	  @param path String whose value is the desired path
	  */
	public void setFilePath(String path)
	{
		setFile(new File(path));
	}

	/** Sets the list of taggings for this file.
	  @param taggingList list of taggings for this file - can be null
	  */
	public void setFileTaggings(List<FileTagging> taggingList)
	{
		fileTaggings = taggingList;
	}

	/** Sets the list of taggings for this file from an array.
	  @param taggingArray an array of FileTagging objects - null is a no-op
	  */
	public void setFileTaggings(FileTagging[] taggingArray)
	{
		if (taggingArray == null) {
			return;
		} else {
			setFileTaggings(Arrays.asList(taggingArray));
		}
	}

	/**
	  @return a String representation of the TaggedFile
	  */
	public String toString()
	{
		return getFilePath();
	}
}

