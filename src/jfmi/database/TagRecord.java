package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class whose fields mirror the columns of the 'tags' table in the
  database. Behaviour is provided for interacting with all records of this type
  (static methods), and for interacting with the record represented by a 
  particular class instance (instance methods).
  */
public class TagRecord extends DatabaseRecord {
	// Instance fields
	private String tag;

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

	//************************************************************
	// EXTENSION DatabaseRecord
	//************************************************************
	static { 
		uniqueColumnLabel = "tag"; 
		matchesPSQL = "SELECT * FROM " + SQLiteDatabase.TBL_TAGS
							+ " WHERE tag = ? ";
		selectAllSQL = "SELECT * FROM " + SQLiteDatabase.TBL_TAGS;
	}

	/** An implementation of the DatabaseRecord method.
	  */
	public void setMatchesPS(PreparedStatement matches) throws
		SQLException
	{
		matches.setString(1, tag);
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
}
