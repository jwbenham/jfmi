package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** An implementer of the jfmi.database.RecordConverter interface for
  the TaggingRecord class.
  */
public class TaggingRecordConverter implements RecordConverter {

	/** Convert a row in a result set to a TaggingRecord.
	  */
	public Object convertToObject(ResultSet rs)
		throws SQLException
	{
		TaggingRecord convert = new TaggingRecord();	
		convert.setFileid(rs.getInt("fileid"));
		convert.setTag(rs.getString("tag"));
		convert.setComment(rs.getString("comment"));
		return convert;
	}

}
