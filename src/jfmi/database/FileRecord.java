package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/** A class whose fields mirror the columns of the 'files' table in the
  database. Behaviour is provided for interacting with all records of this type
  (static methods), and for interacting with the record represented by a 
  particular class instance (instance methods).
  */
public class FileRecord implements DatabaseRecord {

	// Instance fields
	private int fileid;
	private String path;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a default FileRecord with its file id set to -1, and an
	  empty path.
	  */
	public FileRecord()
	{
		fileid = -1;
		path = "";
	}

	/** Constructs a FileRecord with the specified file id and path.
	  @param fid The file id for this record.
	  @param p The path for this file record.
	  */
	public FileRecord(int fid, String p)
	{
		setFileid(fid);
		setPath(p);
	}
	
	/** Return the name of a column whose values are unique for the table,
	  or null if no such column exists.
	  @return The name of a column with unique values if one exists, else null.
	  */
	public String getUniqueColumnName() {
		return "path";	
	}

	/** Returns an SQL SELECT statement which can be used to create a
	  prepared statement for checking whether an instance of a file record
	  exists.
	  @return The SQL to check if a file record exists.
	  */
	public String getPSCheckExistsSQL()
	{
		return "SELECT * FROM " + SQLiteDatabase.TBL_FILES + " WHERE fileid = ?";
	}

	/** An implementation of the DatabaseRecord method, where this
	 implementation applies to a FileRecord. 
	  */
	public void setPSCheckExists(PreparedStatement checkExists) throws
		SQLException
	{
		checkExists.setInt(1, fileid);
	}

	/** Return a String representation of this object.
	  @return String This object's string value.
	  */
	public String toString()
	{
		StringBuilder str = new StringBuilder("");
		str.append("FileRecord(");
		str.append("fileid: " + Integer.toString(fileid) + ", ");
		str.append("path: " + path);
		str.append(")");

		return str.toString();
	}

	/** Access the fileid field. 
	  @return The object's fileid field.
	  */
	public int getFileid()
	{
		return fileid;
	}

	/** Access the path field.
	  @return The object's path.
	  */
	public String getPath()
	{
		return path;
	}

	/** Mutate the fileid field.
	  @param fid The int to set fileid to.
	  */
	public final void setFileid(int fid)
	{
		fileid = fid;
	}

	/** Mutate the path field.
	  @param p The String to set path to.
	  */
	public final void setPath(String p)
	{
		path = p;
	}

}
