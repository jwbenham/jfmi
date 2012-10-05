package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** An implementer of the jfmi.database.RecordConverter interface for
  the TagRecord class.
  */
public class TagRecordConverter implements RecordConverter {

	/** Convert a row in a result set to a TagRecord. This implementation
	  maintains the convention that the ResultSet rs has already been set
	  to or past the first row in the results.
	  */
	public Object convertToObject(ResultSet rs)
		throws SQLException
	{
		TagRecord convert = new TagRecord();	
		convert.setTag(rs.getString("tag"));
		return convert;
	}

}
