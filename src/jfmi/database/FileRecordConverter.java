package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** An implementer of the jfmi.database.RecordConverter interface for
  the FileRecord class.
  */
public class FileRecordConverter implements RecordConverter {

	/** Convert a row in a result set to a FileRecord. This implementation
	  maintains the convention that the ResultSet rs has already been set
	  to or past the first row in the results.
	  */
	public Object convertToObject(ResultSet rs)
		throws SQLException
	{
		FileRecord convert = new FileRecord();	
		convert.setFileid(rs.getInt("fileid"));
		convert.setPath(rs.getString("path"));
		return convert;
	}


}
