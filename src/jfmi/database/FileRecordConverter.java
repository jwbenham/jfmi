package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** An implementer of the jfmi.database.RecordConverter interface for
  the FileRecord class.
  */
public class FileRecordConverter implements RecordConverter {

	/** Convert a row in a result set to a FileRecord.
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
