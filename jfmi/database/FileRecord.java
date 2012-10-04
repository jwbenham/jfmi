package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileRecord implements DatabaseRecord {

	// Instance fields
	private int fileid;
	private String path;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Ctor: default
	  */
	public FileRecord()
	{
		fileid = -1;
		path = "";
	}

	/** Ctor: fileid, path.
	  */
	public FileRecord(int fid, String p)
	{
		setFileid(fid);
		setPath(p);
	}
	
	/** Return the name of a column whose values are unique for the table,
	  or null if no such column exists.
	  @return String The name of a column with unique values; else null.
	  */
	public String getUniqueColumnName() {
		return "path";	
	}

	/** Returns an SQL SELECT statement which can be used to create a
	  prepared statement for checking whether an instance of a record
	  exists.
	  */
	public String getPSCheckExistsSQL()
	{
		return "SELECT * FROM " + SQLiteDatabase.TBL_FILES + " WHERE fileid = ?";
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
	  @return int The object's fileid.
	  */
	public int getFileid()
	{
		return fileid;
	}

	/** Access the path field.
	  @return String The object's path.
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
