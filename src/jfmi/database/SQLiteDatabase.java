package jfmi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;


/**
  An SQLiteDatabase object handles interation with an SQLite database. The
  class contains information for how to create the database tables if they
  do not already exist. This class handles the opening and closing of
  Connection, Statement, ResultSet, and other java.sql objects, and it
  handles the execution of statements. Note that the SQL used to generate
  most statements comes from the class representing the table that is to be
  worked with.
  */
public class SQLiteDatabase {
	public static final String TBL_FILES = "main.files";
	public static final String TBL_TAGGINGS = "main.taggings";
	public static final String TBL_TAGS = "main.tags";

	private String dbpath;
	private String dburl;

	//************************************************************	
	// PUBLIC INSTANCE Methods
	//************************************************************	

	/** Constructs an SQLiteDatabase object using the given path to an sqlite 
	  database file.
	  @param dbpath_ The path to the existing database, or where it should be
	  		created.
	  @throws ClassNotFoundException If the driver cannot be loaded.
	  @throws SQLiteException If table creation is necessary, and fails.
	  */
	public SQLiteDatabase(String dbpath_) throws
		ClassNotFoundException,
		SQLException
	{
		try {
			Class.forName("org.sqlite.JDBC");
			dbpath = dbpath_;	
			dburl = "jdbc:sqlite:" + dbpath; 

			createTablesIfNeeded();

		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(
				"SQLiteDatabase(): failed to load JDBC driver for sqlite.\n"
				+ e.getMessage()
			);

		} catch (SQLException e) {
			throw new SQLException(
				"SQLiteDatabase(): failed to update/create tables.\n"
				+ e.getMessage()
			);
		}
	}


	/** Queries the database to check if a particular record exists.
	  @param record DatabaseRecord whose existence will be checked.
	  @return True if exists.
	  @throws SQLException in the event of a database error
	  */
	public boolean recordExists(DatabaseRecord record) throws SQLException
	{
		if (record == null) {
			throw new NullPointerException(
				"SQLiteDatabase.recordExists(): record arg is null"
			);
		}

		Connection conn = DriverManager.getConnection(dburl);

		try {
			String checkExistsSQL = record.getPSCheckExistsSQL();
			PreparedStatement pstmt = conn.prepareStatement(checkExistsSQL);	

			try {
				record.setPSCheckExists(pstmt);
				ResultSet rs = pstmt.executeQuery();

				try {
					return rs.next(); // returns true if results exist
				} finally {
					closeResultSetIgnoreEx(rs);
				}

			} finally {
				closeStatementIgnoreEx(pstmt);
			}

		} finally {
			closeConnectionIgnoreEx(conn);	
		}
	}

	/** Returns a list of TagRecords. An SQL SELECT query is run to
	  retrieve a list of tags contained by the database.
	  @return List<TagRecord>, which is empty if there were no tags. 
	  @throws SQLException in the event of a database error
	  */
	public List<TagRecord> getAllTagRecords() throws SQLException
	{
		Connection conn = DriverManager.getConnection(dburl);
		
		try {
			Statement stmt = conn.createStatement();

			try {
				String selectTagsSQL = TagRecord.getAllTagsSQL();
				ResultSet rs = stmt.executeQuery(selectTagsSQL);

				try {
					// Iterate over the ResultSet, adding the tags to the list
					LinkedList<TagRecord> tags = new LinkedList<TagRecord>();

					while (rs.next()) {
						tags.add(new TagRecord(rs.getString("tag")));
					}
					
					return tags;

				} finally {
					closeResultSetIgnoreEx(rs);
				}

			} finally {
				closeStatementIgnoreEx(stmt);
			}

		} finally {
			closeConnectionIgnoreEx(conn);
		}
	}
	


	//************************************************************	
	// PRIVATE Methods
	//************************************************************	

	/** Creates the database tables if necessary (new database).
	  @throws SQLException in the event of a database error
	  */
	private void createTablesIfNeeded() throws SQLException
	{
		String sql = null;
		Connection conn = DriverManager.getConnection(dburl);

		try {
			Statement stmt = conn.createStatement();

			try {
				sql = "CREATE TABLE IF NOT EXISTS " + TBL_FILES + " ("
					+ "fileid INTEGER NOT NULL CONSTRAINT fileid_nonnegative "
					+ "CHECK (fileid >= 0), "
					+ "path TEXT NOT NULL UNIQUE, "
					+ "CONSTRAINT filed_is_pk PRIMARY KEY (fileid))";
				stmt.executeUpdate(sql);

				sql = "CREATE TABLE IF NOT EXISTS " + TBL_TAGS + " ( "
					+ "tag TEXT NOT NULL, "
					+ "CONSTRAINT tag_is_pk PRIMARY KEY (tag) "
					+ ")";
				stmt.executeUpdate(sql);
				
				sql = "CREATE TABLE IF NOT EXISTS " + TBL_TAGGINGS + " ( "
					+ "fileid INTEGER NOT NULL, "
					+ "tag TEXT NOT NULL, "
					+ "comment TEXT, "
					+ "CONSTRAINT fileid_tag_are_pk PRIMARY KEY(fileid, tag), "
					+ "CONSTRAINT fileid_is_fk FOREIGN KEY(fileid) REFERENCES "
					+ "files(fileid), "
					+ "CONSTRAINT tag_is_fk FOREIGN KEY(tag) "
					+ "REFERENCES tags(tag) "
					+ ")";
				stmt.executeUpdate(sql);

			} finally {
				closeStatementIgnoreEx(stmt);
			}	

		} finally {
			closeConnectionIgnoreEx(conn);
		}
	}

	/** Closes a Connection, ignoring any thrown exception.
	  @param conn The Connection to be closed.
	  */
	private void closeConnectionIgnoreEx(Connection conn)
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// do nothing
		}
	}

	/** Closes a Statement, ignoring any thrown exception.
	  @param s The Statement to be closed.
	  */
	private void closeStatementIgnoreEx(Statement s)
	{
		try { 
			s.close();
		} catch (SQLException e) {
			// do nothing
		}
	}
	
	/** Closes a ResultSet, ignoring any thrown exception.
	  @param rs The ResultSet to be closed.
	  */
	private void closeResultSetIgnoreEx(ResultSet rs)
	{
		try { 
			rs.close();
		} catch (SQLException e) {
			// do nothing
		}
	}
}
