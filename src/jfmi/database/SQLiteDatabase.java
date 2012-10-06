package jfmi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;

import jfmi.control.TaggedFile;
import jfmi.gui.GUIUtil;

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
	/** Name of the database table that holds file records. */
	public static final String TBL_FILES = "main.files";
	/** Name of the database table that holds tagging records. */
	public static final String TBL_TAGGINGS = "main.taggings";
	/** Name of the database table that holds tag records. */
	public static final String TBL_TAGS = "main.tags";

	// Instance fields
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

	/** Retrieves a single table record from the database, converts it to a 
	  Java object, and uses calls the specified target's setFromRecord()
	  method to fill in the object with the retrieved data.
	  @param target a DatabaseRecord that has had its primary key field(s)
	  		set, and will return a String from the instance method
			getMatchesPSQL() that can be used to query for the entire record
	  @param converter a RecordConverter for the target
	  @return true if a matching database record was found
	  @throws SQLException in the event of a database error
	  */
	public boolean getDatabaseRecord(
		DatabaseRecord target,
		RecordConverter converter
	) throws SQLException
	{
		Connection conn = DriverManager.getConnection(dburl);

		try {
			String matchesSQL = target.getMatchesPSQL();
			GUIUtil.debug("getDatabaseRecord: target = " 
					+ target.toString());
			GUIUtil.debug("getDatabaseRecord: " + matchesSQL);
			PreparedStatement ps = conn.prepareStatement(matchesSQL.toString());

			try {
				target.setMatchesPS(ps);
				ResultSet rs = ps.executeQuery();

				try {
					
					if (rs.next()) {
						target.setFromDatabaseRecord(
							(DatabaseRecord)converter.convertToObject(rs)
						);	

						return true;

					} else {
						return false;
					}

				} finally {
					closeResultSetIgnoreEx(rs);
				}

			} finally {
				closeStatementIgnoreEx(ps);
			}

		} finally {
			closeConnectionIgnoreEx(conn);	
		}

	}

	/** Queries the database to check if a particular record exists.
	  @param record DatabaseRecord whose existence will be checked.
	  @return true if exists
	  @throws SQLException in the event of a database error
	  */
	public boolean recordExists(DatabaseRecord record) throws SQLException
	{
		Connection conn = DriverManager.getConnection(dburl);

		try {
			String matchesPSQL = record.getMatchesPSQL();
			PreparedStatement ps = conn.prepareStatement(matchesPSQL.toString());	
			try {
				record.setMatchesPS(ps);
				ResultSet rs = ps.executeQuery();

				try {
					return rs.next(); // returns true if results exist
				} finally {
					closeResultSetIgnoreEx(rs);
				}

			} finally {
				closeStatementIgnoreEx(ps);
			}

		} finally {
			closeConnectionIgnoreEx(conn);	
		}
	}

	/** Gets a list of all the records in a table, using the specified
	  SQL SELECT statement, and the specified RecordConverter to convert the
	  ResultSet rows into Java objects. This method only applies to 
	  DatabaseRecords - i.e. classes which mirror an actual database table.
	  @param selectAllSQL SQL SELECT statement which will return all records
	  			in the target table
	  @param converter a RecordConverter to convert any results to objects
	  @return a list of converted results
	  @throws SQLException in the event of a database error
	  */
	public List<DatabaseRecord> getAllRecords(
		String selectAllSQL,
		RecordConverter converter
	) throws SQLException
	{
		Connection conn = DriverManager.getConnection(dburl);

		try {
			Statement stmt = conn.createStatement();

			try {
				ResultSet rs = stmt.executeQuery(selectAllSQL.toString());

				try {
					LinkedList<DatabaseRecord> records;
				   	records = new LinkedList<DatabaseRecord>();

					while (rs.next()) {
						records.add(
							(DatabaseRecord)converter.convertToObject(rs)
						);
					}

					return records;

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

	/** Queries the database and returns a list of TaggedFiles. The database
	  is first queried for all records in the file table. For each file
	  record's fileid, getTaggedFile(fileid) is used to retrieve a TaggedFile
	  constructed from all the taggings associated with that fileid.
	  @return a non-null list of TaggedFile objects - empty if none found
	  @throws SQLException in the event of a database error
	  */
	public List<TaggedFile> getAllTaggedFiles() throws SQLException
	{
		Connection conn = DriverManager.getConnection(dburl);

		try {
			Statement stmt = conn.createStatement();

			try {
				String selectAllFiles = FileRecord.getSelectAllSQL();
				ResultSet rs = stmt.executeQuery(selectAllFiles.toString());

				try {
					LinkedList<TaggedFile> list = new LinkedList<TaggedFile>();
					TaggedFile taggedFile = null;

					while (rs.next()) {
						taggedFile = getTaggedFile(rs.getInt("fileid"));	

						if (taggedFile != null) {
							list.add(taggedFile);
						}
					}

					return list;

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

	/** Given the specified file id, this method queries the database for
	  all the taggings associated with the file, and creates a new TaggedFile
	  from the results. Note that a file that is not associated with any
	  tags is still retrieved by this method - its list of TaggingRecords will
	  just be empty.
	  @param fileid this determines which taggings are retrieved
	  @return a new TaggedFile with its taggings if they exist, and with none
	  		otherwise; null is returned if no file with the file id exists
	  @throws SQLException in the event of a database error
	  */
	public TaggedFile getTaggedFile(int fileid) throws SQLException
	{
		Connection conn = DriverManager.getConnection(dburl);

		try {
			String selectPSQL = TaggedFile.getSelectPSQL();
			PreparedStatement ps = conn.prepareStatement(selectPSQL.toString());

			try {
				TaggedFile.setSelectPS(ps, fileid);
				ResultSet rs = ps.executeQuery();

				try {
					TaggedFile taggedFile = null;
					TaggedFileRecordConverter converter; 
					converter = new TaggedFileRecordConverter();

					if (rs.next()) {	
						// file has tags associated with it
						taggedFile = (TaggedFile)converter.convertToObject(rs);
						
					} else {	
						// file has no tags associated with it
						FileRecord f =  new FileRecord();
						f.setFileid(fileid);
						
						GUIUtil.debug("calling getDatabaseRecord: file = " 
								+ f.toString());
						if (getDatabaseRecord(f, new FileRecordConverter())) {
							taggedFile = new TaggedFile();
							taggedFile.setFileRecord(f);
						}
					}

					return taggedFile;

				} finally {
					closeResultSetIgnoreEx(rs);
				}

			} finally {
				closeStatementIgnoreEx(ps);
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
