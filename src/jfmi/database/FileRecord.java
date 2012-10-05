package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class whose fields mirror the columns of the 'files' table in the
  database. Behaviour is provided for interacting with all records of this type
  (static methods), and for interacting with the record represented by a 
  particular class instance (instance methods).
  */
public class FileRecord extends DatabaseRecord {
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

	//************************************************************
	// EXTENSION DatabaseRecord
	//************************************************************
	static { 
		uniqueColumnLabel = "fileid"; 
		matchesPSQL = "SELECT * FROM " + SQLiteDatabase.TBL_FILES
							+ " WHERE fileid = ? ";
		selectAllSQL = "SELECT * FROM " + SQLiteDatabase.TBL_FILES;
	}

	/** An implementation of the DatabaseRecord method.
	  */
	public void setMatchesPS(PreparedStatement matches) throws 
		SQLException
	{
		matches.setInt(1, fileid);		
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

}
