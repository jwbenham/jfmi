package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/** A class whose fields mirror the columns of the 'tags' table in the
  database. Behaviour is provided for interacting with all records of this type
  (static methods), and for interacting with the record represented by a 
  particular class instance (instance methods).
  */
public class TagRecord implements DatabaseRecord {

	// Instance fields
	private String tag;

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	/** Get an SQL statement that can be used to select the values
	  of every tag stored in the database.
	  @return A SELECT statement to get all table records.
	  */
	public static String getAllTagsSQL()
	{
		return "SELECT tag FROM " + SQLiteDatabase.TBL_TAGS;
	}

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a tag record with empty tag value ("").
	  */
	public TagRecord()
	{
		tag = "";
	}

	/** Constructs a TagRecord with the specified tag value.
	  @param tag_ The value for this tag record.
	  */
	public TagRecord(String tag_)
	{
		setTag(tag_);
	}

	/** Return the name of a column whose values are unique for the table,
	  or null if no such column exists.
	  @return The name of a column with unique values if it exists, else null.
	  */
	public String getUniqueColumnName() {
		return "tag";
	}

	/** An implementation of the DatabaseRecord method. 
	  */
	public String getPSCheckExistsSQL()
	{
		return "SELECT * FROM " + SQLiteDatabase.TBL_TAGS + " WHERE tag = ?";
	}

	/** An implementation of the DatabaseRecord method.
	  */
	public void setPSCheckExists(PreparedStatement checkExists) throws
		SQLException
	{
		checkExists.setString(1, tag);
	}

	/** Return a String representation of this object.
	  @return This object's string value.
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
	  @return The object's tag field.
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
