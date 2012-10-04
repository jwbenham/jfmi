package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;


/** A class whose fields mirror the columns of the 'taggings' table in the
  database. Behaviour is provided for interacting with all records of this type
  (static methods), and for interacting with the record represented by a 
  particular class instance (instance methods).
  */
public class TaggingRecord implements DatabaseRecord {

	// Instance fields
	private int fileid;
	private String tag;
	private String comment;
	
	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Construct a tagging record with default values.
	  */
	public TaggingRecord()
	{
		fileid = -1;
		tag = "";
		comment = "";
	}

	/** Construct a tagging record with the specified file id, tag, and comment.
	  @param fileid_ the file id to use
	  @param tag_ the tag value to use
	  @param comment_ the comment for this record
	  */
	public TaggingRecord(Integer fileid_, String tag_, String comment_)
	{
		setFileid(fileid_);
		setTag(tag_);
		setComment(comment_);
	}

	/** An implementation of the DatabaseRecord method.
	  */
	public String getUniqueColumnName() {
		return null;
	}

	/** An implementation of the DatabaseRecord method.
	  */
	public String getPSCheckExistsSQL()
	{
		String sql = "SELECT * FROM " + SQLiteDatabase.TBL_TAGGINGS 
			+ " WHERE fileid = ? AND tag = ?";
		return sql;
	}

	/** An implementation of the DatabaseRecord method.
	  */
	public void setPSCheckExists(PreparedStatement checkExists) throws
		SQLException
	{
		checkExists.setInt(1, fileid);
		checkExists.setString(2, tag);
	}

	/** Return a String representation of this object.
	  @return This object's string value.
	  */
	public String toString()
	{
		StringBuilder str = new StringBuilder("");
		str.append("TaggingRecord(");
		str.append("fileid: " + Integer.toString(fileid) + ", ");
		str.append("tag: " + tag + ", ");
		str.append("comment: " + comment);
		str.append(")");

		return str.toString();
	}		

	/** Accessor for the fileid field.
	  @return The value of the fileid field.
	  */
	public int getFileid()
	{
		return fileid;
	}

	/** Accessor for the tag field.
	  @return The value of the tag field.
	  */
	public String getTag()
	{
		return tag;
	}

	/** Accessor for the comment field.
	  @return The value of the comment field.
	  */
	public String getComment()
	{
		return comment;
	}

	/** Mutator for the fileid field.
	  @param fileid_ The value to set the field to.
	  */
	public void setFileid(int fileid_)
	{
		fileid = fileid_;
	}

	/** Mutator for the  field.
	  @param tag_ The value to set the field to.
	  */
	public void setTag(String tag_)
	{
		tag = tag_;
	}

	/** Mutator for the  field.
	  @param comment_ The value to set the field to.
	  */
	public void setComment(String comment_)
	{
		comment = comment_;
	}

}
