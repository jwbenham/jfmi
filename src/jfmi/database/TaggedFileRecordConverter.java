package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import jfmi.control.TaggedFile;

/** An implementer of the jfmi.database.RecordConverter interface for
  the TaggedFile class.
  */
public class TaggedFileRecordConverter implements RecordConverter {

	/** Convert a row in a result set to a TaggedFile.
	  */
	public Object convertToObject(ResultSet rs)
		throws SQLException
	{
		TaggedFile convert = new FileRecord();	
		convert.setFileid(rs.getInt("fileid"));
		convert.setPath(rs.getString("path"));
		return convert;
	}


}
