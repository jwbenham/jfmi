package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaggingRecord implements DatabaseRecord {

	// Instance fields
	private int fileid;
	private String tag;
	private String comment;
	
	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Ctor: default.
	  */
	public TaggingRecord()
	{
		fileid = -1;
		tag = "";
		comment = "";
	}

	/** Ctor: fileid, tag, comment.
	  */
	public TaggingRecord(Integer fileid_, String tag_, String comment_)
	{
		setFileid(fileid_);
		setTag(tag_);
		setComment(comment_);
	}

	/** Return the name of a column whose values are unique for the table,
	  or null if no such column exists.
	  @return String The name of a column with unique values; else null.
	  */
	public String getUniqueColumnName() {
		return null;
	}

	/** Returns an SQL SELECT statement which can be used to create a
	  prepared statement for checking whether an instance of a record
	  exists.
	  */
	public String getPSCheckExistsSQL()
	{
		String sql = "SELECT * FROM " + SQLiteDatabase.TBL_TAGGINGS 
			+ " WHERE fileid = ? AND tag = ?";
		return sql;
	}

	/** Given a properly constructed PreparedStatement, this method sets
	  the statement parameters to check if this particular object
	  exists in the database. The PreparedStatement should have been
	  constructed with the SQL resulting from a call to the method
	  "getPrepStmtCheckExistsSQL()".
	  */
	public void setPSCheckExists(PreparedStatement checkExists) throws
		SQLException
	{
		checkExists.setInt(1, fileid);
		checkExists.setString(2, tag);
	}

	/** Return a String representation of this object.
	  @return String This object's string value.
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
	  @return int The value of the fileid field.
	  */
	public int getFileid()
	{
		return fileid;
	}

	/** Accessor for the tag field.
	  @return String The value of the tag field.
	  */
	public String getTag()
	{
		return tag;
	}

	/** Accessor for the comment field.
	  @return String The value of the comment field.
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
