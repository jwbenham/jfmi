package jfmi.control;

import java.util.ArrayList;
import java.util.Arrays;

import jfmi.database.FileRecord;
import jfmi.database.TaggingRecord;
import jfmi.util.StringUtil;

/** A composite class of a FileRecord and a collection of TaggingRecords. An
  instance of this class represents all information that the JFMP app has
  stored about a file. This class does not represent any single table in the
  application's database.
  */
public class TaggedFile {

	private FileRecord fileRecord;
	private ArrayList<TaggingRecord> taggings;

	/** Constructs a TaggedFile with fields initialized to null.
	  */
	public TaggedFile()
	{
		fileRecord = null;
		taggings = null;
	}

	/** Constructs a TaggedFile with the specified FileRecord and list of 
	  tag/comment (tagging) combinations.
	  @param fileRecord_ The file this instance refers to.
	  @param tags_ A list of taggings associated with the file.
	  */
	public TaggedFile(FileRecord fileRecord_, ArrayList<TaggingRecord> tags_)
	{
		setFileRecord(fileRecord_);
		setTaggings(tags_);
	}

	/** Constructs a TaggedFile with the specified FileRecord and array of
	  tag/comment (tagging) combinations.
	  @param fileRecord_ The file this instance refers to.
	  @param tags_ An array of taggings associated with the file.
	  */
	public TaggedFile(FileRecord fileRecord_, TaggingRecord[] tags_)
	{
		setFileRecord(fileRecord_);
		setTaggings(tags_);
	}

	/** Returns the name of the file as a String.
	  @return If the fileRecord.path field is null, it returns the empty string.
	  */
	public String getFileName()
	{
		String path = fileRecord.getPath();

		if (path == null) {
			return "";
		} else {
			return StringUtil.parseNameFromPath(path);
		}
	}

	/** Returns the path of the fileRecord.
	  @return If the fileRecord.path field is null, the empty string is returned.
	  */
	public String getFilePath()
	{
		return fileRecord.getPath();
	}

	/** Returns a String of the tags in the taggings field.
	  @return String of tags contained in the TaggingRecord objects in
	  the taggings ArrayList.
	  */
	public String getTagsAsString()
	{
		StringBuilder tags = new StringBuilder("");

		for (TaggingRecord tr : taggings) {
			tags.append(" [" + tr.getTag() + "] ");
		}

		return tags.toString();
	}

	/** Mutator for fileRecord field.
	  @param fileRecord_ Non-null reference to FileRecord.
	  */
	public final void setFileRecord(FileRecord fileRecord_)
	{
		if (fileRecord_ == null) {
		   throw new IllegalArgumentException("error: fileRecord_ is null");
		}	   

		fileRecord = fileRecord_;
	}

	/** Mutator for taggings field.
	  @param taggings_ Non-null reference to an ArrayList<TaggingRecord>.
	  */
	public final void setTaggings(ArrayList<TaggingRecord> taggings_)
	{
		if (taggings_ == null) {
		   throw new IllegalArgumentException("error: taggings_ is null");
		}

		taggings = taggings_;
	}

	/** Mutator for taggings field.
	  @param taggings_ Non-null reference to a TaggingRecord[].
	  */
	public final void setTaggings(TaggingRecord[] taggings_)
	{
		if (taggings_ == null) {
		   throw new IllegalArgumentException("error: taggings_ is null");
		}

		taggings = new ArrayList<TaggingRecord>(Arrays.asList(taggings_));	
	}

	/** Acessor for taggings field.
	  @return This instance's list of taggings.
	  */
	public ArrayList<TaggingRecord> getTaggings()
	{
		return taggings;
	}

}
