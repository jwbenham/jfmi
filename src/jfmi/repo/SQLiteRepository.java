package jfmi.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/** Implements an SQLite-based repository.
  */
public class SQLiteRepository extends AbstractRepository {
	// PRIVATE CLASS Fields
	private static SQLiteRepository singleton;

	// PRIVATE INSTANCE Fields
	private String repoPath;
	private String repoURL;

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	/** Retrieves the singleton instance of the SQLiteRepository.
	  @return the singleton instance
	  */
	public static SQLiteRepository instance()
	{
		if (singleton == null) {
		 	singleton = new SQLiteRepository("./dao.db");
		}

		return singleton;
	}

	/** Closes a Connection object, ignoring any exceptions.
	  @param conn the Connection object to close
	  */
	public static void closeQuietly(Connection conn)
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	/** Closes a ResultSet object, ignoring any exceptions.
	  @param rs the ResultSet object to close
	  */
	public static void closeQuietly(ResultSet rs)
	{
		try {
			rs.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	/** Closes a Statement object, ignoring any exceptions.
	  @param stmt the Statement object to close
	  */
	public static void closeQuietly(Statement stmt)
	{
		try {
			stmt.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Returns a new Connection for the database.
	  @throws SQLException if a connection can not be established
	  */
	public Connection getConnection() throws SQLException 
	{
		return DriverManager.getConnection(repoURL);
	}

	/** Initializes the SQLiteRepository by attempting to load the 
	  SQLite driver.
	  @throws ClassNotFoundException If the driver cannot be loaded.
	  @throws SQLiteException If table creation is necessary, and fails.
	  */
	public void initialize() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
	}	

	/** Sets the path to an SQLite database.
	  @param path the file path to set repoPath to
	  */
	public void setRepoPath(String path)
	{
		repoPath = path;

		if (repoPath == null) {
			repoPath = "";
		}	

		setRepoURL("jdbc:sqlite:" + repoPath);
	}

	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Constructs an SQLiteRepository using the specified path.
	  @param path the file path to an SQLite database
	  */
	public SQLiteRepository(String path)
	{
		setRepoPath(path);
	}

	/** Sets the database URL for the SQLiteRepository.
	  @param url value to set the repoURL field to
	  */
	private void setRepoURL(String url)
	{
		repoURL = url;
	}
}
