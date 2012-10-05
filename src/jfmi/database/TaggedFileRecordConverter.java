package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jfmi.control.TaggedFile;

/** An implementer of the jfmi.database.RecordConverter interface for
  the TaggedFile class.
  */
public class TaggedFileRecordConverter implements RecordConverter {

	/** Converts a row in a result set to a TaggedFile. This implementation
	  maintains the convention that the ResultSet rs has already been set
	  to or past the first row in the results. In this implementation,
	  when the method returns, the ResultSet will have been set to the row
	  one past the last.
	  */
	public Object convertToObject(ResultSet rs)
		throws SQLException
	{
		// Get the FileRecord field's fields
		FileRecord fileRecord = new FileRecord();	
		fileRecord.setFileid(rs.getInt("fileid"));
		fileRecord.setPath(rs.getString("path"));

		// Get the TaggingRecord fields for the list	
		TaggingRecordConverter converter = new TaggingRecordConverter();
		ArrayList<TaggingRecord> list = new ArrayList<TaggingRecord>();

		do {
			list.add((TaggingRecord)converter.convertToObject(rs));
			
		} while (rs.next());

		// Set up the converted TaggedFile
		return new TaggedFile(fileRecord, list);
	}


}
