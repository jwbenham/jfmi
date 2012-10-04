package jfmi.control;

import java.util.ArrayList;
import java.util.Arrays;

import jfmi.database.FileRecord;
import jfmi.database.TaggingRecord;
import jfmi.util.StringUtil;

public class TaggedFile {

	private FileRecord fileRecord;
	private ArrayList<TaggingRecord> taggings;

	/** Ctor: default.
	  */
	public TaggedFile()
	{
		fileRecord = null;
		taggings = null;
	}

	/** Ctor: FileRecord, ArrayList<TaggingRecord>.
	  */
	public TaggedFile(FileRecord fileRecord_, ArrayList<TaggingRecord> tags_)
	{
		setFileRecord(fileRecord_);
		setTaggings(tags_);
	}

	/** Ctor: FileRecord, TaggingRecord[].
	  */
	public TaggedFile(FileRecord fileRecord_, TaggingRecord[] tags_)
	{
		setFileRecord(fileRecord_);
		setTaggings(tags_);
	}

	/** Returns the name of the file as a String.
	  If the fileRecord.path field is null, it returns the empty string.
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
	  If the fileRecord.path field is null, the empty string is returned.
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
	  @return ArrayList<TaggingRecord>
	  */
	public ArrayList<TaggingRecord> getTaggings()
	{
		return taggings;
	}

}
