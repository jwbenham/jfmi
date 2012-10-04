package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/** An interface which should be implemented by all classes that act as
  Java Object wrappers for table records in the application database. The
  interface provides client classes with a set of methods used for interacting
  with the DatabaseRecord implementors themselves, and with their corresponding
  database tables.
  */
public interface DatabaseRecord {

	/** Returns the name of a column which, for the table represented by the 
	  implementing class, is guaranteed to have unique values. This could be
	  a primary key column, or any other unique column.
	  @return A column name unique to the table in question.
	  */
	public String getUniqueColumnName();

	/** This method gets a String object which can be used to construct a 
	  PreparedStatement object that when executed will check if a specific
	  record exists in the database. A PreparedStatement constructed with the
	  String from this method can have its parameters set with the method
	  'setPSCheckExists()'.
	  @return A String containing the parameterized SQL statement used to
	  		check if a record of the table represented by this class exists.
	  */
	public String getPSCheckExistsSQL();

	/** Given a PreparedStatement object constructed with a String from the
	  'getPSCheckExistsSQL() method, this method sets the parameters in the
	  PreparedStatement to the values needed to check if the record represented
	  by this object exists.
	  @param checkExists The PreparedStatement to be set.
	  @throws SQLException If a problem occurs when setting the statement.
	  */
	public void setPSCheckExists(PreparedStatement checkExists) throws 
		SQLException;

	/** Converts an instance of this interface to a String.
	  @return A String representation of this object.
	  */
	public String toString();
}
