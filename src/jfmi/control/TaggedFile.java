package jfmi.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jfmi.database.FileRecord;
import jfmi.database.TaggingRecord;
import jfmi.database.SQLiteDatabase;
import jfmi.util.StringUtil;

/** A composite class of a FileRecord and a collection of TaggingRecords. An
  instance of this class represents all information that the JFMP app has
  stored about a file. This class does not represent any single table in the
  application's database.
  */
public class TaggedFile {
	// Class fields
	private static final String TAGGED_FILE_SELECT_PSQL;

	static {
		SELECT_PSQL = "SELECT f.fileid, f.path, t.tag, t.comment "
			+ "FROM " + SQLiteDatabase.TBL_FILES + " f, "
			+ SQLiteDatabase.TBL_TAGGINGS + " t "
			+ "WHERE f.fileid = t.fileid "
			+ "AND f.fileid = ? ";
	}

	// Instance fields
	private FileRecord fileRecord;
	private ArrayList<TaggingRecord> taggings;

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	/** Returns a parameterized SQL SELECT statement which will return a table
	  of all the taggings for a particular file. The columns in the table 
	  include "fileid", "path", "tag", "comment". The "fileid" and "path"
	  column should have the same value in each result record.
	  @return an SQL SELECT statement to get tagging information for a file
	  */
	public static String getSelectPSQL()
	{
		return SELECT_PSQL;	
	}

	/** Sets the file id in a PreparedStatement compiled with the parameterized 
	  SQL statement returned from getSelectPSQL. 
	  @param ps the PrepareStatement whose parameter will be set
	  @param fileid the value to use for the file id parameter
	  @throws SQLException if setting the parameter fails
	  */
	public static void setSelectPS(PreparedStatement ps, int fileid)
		throws SQLException
	{
		ps.setInt(1, fileid);
	}

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

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
