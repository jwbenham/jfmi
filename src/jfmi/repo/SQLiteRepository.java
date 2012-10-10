package jfmi.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;


/** Implements an SQLite-based repository.
  */
public class SQLiteRepository extends AbstractRepository {
	// PRIVATE CLASS Fields
	private static SQLiteRepository singleton;

	// PRIVATE INSTANCE Fields
	private String repoPath;
	private String repoURL;
	private boolean initialized;

	private SQLiteConfig sqliteConfig;

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	/** Retrieves the singleton instance of the SQLiteRepository.
	  @return the singleton instance
	  */
	public static SQLiteRepository instance()
	{
		if (singleton == null) {
		 	singleton = new SQLiteRepository("");
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
	  @return a Connection for the database, or null if not initialized
	  @throws SQLException if a connection can not be established
	  */
	public Connection getConnection() throws SQLException 
	{
		return DriverManager.getConnection(repoURL, sqliteConfig.toProperties());
	}

	/** Retrieves the path of the SQLite repository.
	  @return the String value of the repository path
	  */
	public String getRepoPath()
	{
		return repoPath;
	}

	/** Retrieves the url of the SQLite repository.
	  @return the String value of the repository url
	  */
	public String getRepoURL()
	{
		return repoURL;
	}

	/** Initializes the SQLiteRepository by attempting to load the 
	  SQLite driver. The path to the repository should be set before
	  calling this method.
	  @throws ClassNotFoundException If the driver cannot be loaded.
	  @throws SQLException If table creation is necessary, and fails.
	  */
	public void initialize() throws ClassNotFoundException, SQLException
	{
		if (!isInitialized()) {
			Class.forName("org.sqlite.JDBC");
			createTablesIfNecessary();

			setInitialized(true);
		}
	}	

	/** Indicates whether or not the repository has been initialized.
	  @return true if the repository is initialized
	  */
	public boolean isInitialized()
	{
		return initialized;
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
	private SQLiteRepository(String path)
	{
		setRepoPath(path);
		setInitialized(false);

		sqliteConfig = new SQLiteConfig();
		sqliteConfig.enforceForeignKeys(true);
	}

	/** Creates the SQLite database tables if necessary.
	  @throws SQLException if an error occurs while working with the database
	  */
	private void createTablesIfNecessary() throws SQLException
	{
		Connection conn = getConnection();	

		try {
			Statement stmt = conn.createStatement();

			try {
				String taggedFile;
				taggedFile = "CREATE TABLE IF NOT EXISTS main.TaggedFile ("
					+ "fileId INTEGER "
					+ " CONSTRAINT fileId_is_pk PRIMARY KEY ASC AUTOINCREMENT "
					+ " CONSTRAINT fileId_nonnegative CHECK (fileId >= 0), "
					+ " path TEXT NOT NULL UNIQUE "
					+ " )";

				String fileTag;
				fileTag = "CREATE TABLE IF NOT EXISTS main.FileTag ("
					+ " tag TEXT NOT NULL," 
					+ " CONSTRAINT tag_is_pk PRIMARY KEY (tag)"
					+ " )";

				String fileTagging;
				fileTagging = "CREATE TABLE IF NOT EXISTS main.FileTagging ("
					+ " taggingId INTEGER "
					+ " CONSTRAINT taggingId_is_pk PRIMARY KEY ASC AUTOINCREMENT"
					+ " CONSTRAINT taggingId_nonnegative CHECK (taggingId >= 0),"
					+ " fileId INTEGER NOT NULL,"
					+ " tag TEXT NOT NULL,"
					+ " comment TEXT,"
					+ " CONSTRAINT fileId_is_fk FOREIGN KEY(fileId)"
				    + " REFERENCES TaggedFile(fileId)"
					+ " ON DELETE CASCADE ON UPDATE CASCADE,"
					+ " CONSTRAINT tag_is_fk FOREIGN KEY(tag) "
					+ " REFERENCES FileTag(tag)"
					+ " ON DELETE CASCADE ON UPDATE CASCADE"
					+ " )";

				stmt.executeUpdate(taggedFile);
				stmt.executeUpdate(fileTag);
				stmt.executeUpdate(fileTagging);

			} finally {
				closeQuietly(stmt);
			}

		} finally {
			closeQuietly(conn);
		}
	}

	/** Sets the initialization status of the instance.
	  @param init boolean value indicating if the database is initialized
	  */
	public void setInitialized(boolean init)
	{
		initialized = init;
	}

	/** Sets the database URL for the SQLiteRepository.
	  @param url value to set the repoURL field to
	  */
	private void setRepoURL(String url)
	{
		repoURL = url;
	}
}
