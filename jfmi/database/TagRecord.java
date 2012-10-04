package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagRecord implements DatabaseRecord {

	// Instance fields
	private String tag;

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	/** Returns an SQL statment that can be used to select the values
	  of every tag stored in the database.
	  */
	public static String getAllTagsSQL()
	{
		return "SELECT tag FROM " + SQLiteDatabase.TBL_TAGS;
	}

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Ctor: default.
	  */
	public TagRecord()
	{
		tag = "";
	}

	/** Ctor: tag.
	  */
	public TagRecord(String tag_)
	{
		setTag(tag_);
	}

	/** Return the name of a column whose values are unique for the table,
	  or null if no such column exists.
	  @return String The name of a column with unique values; else null.
	  */
	public String getUniqueColumnName() {
		return "tag";
	}

	/** Returns an SQL SELECT statement which can be used to create a
	  prepared statement for checking whether an instance of a record
	  exists.
	  */
	public String getPSCheckExistsSQL()
	{
		return "SELECT * FROM " + SQLiteDatabase.TBL_TAGS + " WHERE tag = ?";
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
		checkExists.setString(1, tag);
	}

	/** Return a String representation of this object.
	  @return String This object's string value.
	  */
	public String toString()
	{
		StringBuilder str = new StringBuilder("");
		str.append("TagRecord(");
		str.append("tag: " + tag);
		str.append(")");

		return str.toString();
	}

	/** Access the tag field.
	  @return String The object's tag field.
	  */
	public String getTag()
	{
		return tag;
	}

	/** Mutate the tag field.
	  @param tag_ The value to set the tag field to.
	  */
	public final void setTag(String tag_)
	{
		tag = tag_;
	}
}
