package jfmi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Classes which implement the RecordConverter interface are stating that
  they can perform conversion from a database table record to a 
  jfmi.database.DatabaseRecord object, and vice versa.
  */
public interface RecordConverter {

	/** Convert a row in a result set to an object of an appropriate subclass
	  of DatabaseRecord.
	  @param rs the ResultSet to convert a row from
	  @return the DatabaseRecord resulting from the conversion
	  @throws SQLException
	  */
	public DatabaseRecord convertToObject(ResultSet rs) throws 
		SQLException;

}
