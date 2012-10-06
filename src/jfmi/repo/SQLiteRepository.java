package jfmi.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/** Implements an SQLite-based repository.
  */
public class SQLiteRepository extends AbstractRepository {

	// PRIVATE INSTANCE Fields
	private String repoPath;
	private String repoURL;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs an SQLiteRepository using the specified path.
	  @param path the file path to an SQLite database
	  */
	public SQLiteRepository(String path)
	{
		setRepoPath(path);
	}

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

	/** Sets the database URL for the SQLiteRepository.
	  @param url value to set the repoURL field to
	  */
	private void setRepoURL(String url)
	{
		repoURL = url;
	}
}
