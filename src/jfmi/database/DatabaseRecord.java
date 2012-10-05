package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** An interface which should be implemented by all classes that act as
  Java Object wrappers for table records in the application database. The
  interface provides client classes with a set of methods used for interacting
  with the DatabaseRecord implementors themselves, and with their corresponding
  database tables.
  */
public abstract class DatabaseRecord {
	/** The label of a column which, for the table represented by the 
	  implementing class, is guaranteed to have unique values. This could be
	  a primary key column, or any other unique column.
	  */ 
	protected static String uniqueColumnLabel;

	/** A String object which can be used to construct a PreparedStatement 
	  object that when executed will check if a specific record exists in the 
	  database. A PreparedStatement constructed with this String can have its 
	  parameters set with the method 'setPSCheckExists()'.
	  */
	protected static String matchesPSQL;

	/** A String object whose value is an SQL SELECT statement that will
	  select all records from the table corresponding to the class
	  extending DatabaseRecord.
	  */
	protected static String selectAllSQL;

	static { 
		uniqueColumnLabel = null;
		matchesPSQL = null;
		selectAllSQL = null;
	}

	/** Gets the uniqueColumnLabel String.
	  @return the uniqueColumnLabel String
	  */
	public static String getUniqueColumnLabel()
	{
		return uniqueColumnLabel;
	}

	/** Gets the matchesPSQL String.
	  @return the matchesPSQL field
	  */
	public static String getMatchesPSQL()
	{
		return matchesPSQL;
	}

	/** Gets the selectAllSQL String.
	  @return the selectAllSQL field
	  */
	public static String getSelectAllSQL()
	{
		return selectAllSQL;
	}

	/** Given a PreparedStatement object constructed with the String field
	  "matchesPSQL", this method sets the parameters in the PreparedStatement 
	  to the values needed to check if the record represented by this object 
	  exists in the database.
	  @param matches The PreparedStatement to be set.
	  @throws SQLException If a problem occurs when setting the statement.
	  */
	public abstract void setMatchesPS(PreparedStatement matches) throws 
		SQLException;

	/** Converts an instance of this interface to a String.
	  @return A String representation of this object.
	  */
	public abstract String toString();
}
