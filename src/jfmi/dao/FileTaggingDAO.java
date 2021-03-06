package jfmi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import jfmi.app.FileTagging;
import static jfmi.app.FileTaggingSorters.SQLPrimaryKeySorter;
import jfmi.repo.SQLiteRepository;


/** A FileTaggingDAO provides data access for storing FileTagging objects
  in an underlying database.
  */
public class FileTaggingDAO extends AbstractDAO<FileTagging, Integer> {

	// PUBLIC CLASS Fields
	public static final String TABLE_NAME = "main.FileTagging";

	// PRIVATE CLASS Fields
	private static final String CREATE_PSQL;
	private static final String READ_BY_ID_PSQL;
	private static final String READ_BY_FILEID_PSQL;
	private static final String READ_ALL_SQL;
	private static final String UPDATE_PSQL;
	private static final String DELETE_PSQL;
	private static final String DELETE_BY_TAG_PSQL;
	private static final String DELETE_ALL_SQL;

	static {
		CREATE_PSQL = "INSERT INTO " + TABLE_NAME + "(fileId, tag, comment)"
		   			+ " VALUES(?, ?, ?)";

		READ_BY_ID_PSQL = "SELECT * FROM " + TABLE_NAME 
						+ " WHERE taggingId = ? ";

		READ_BY_FILEID_PSQL = "SELECT * FROM " + TABLE_NAME 
							+ " WHERE fileId = ? ";

		READ_ALL_SQL = "SELECT * FROM " + TABLE_NAME;

		UPDATE_PSQL = "UPDATE " + TABLE_NAME 
					+ " SET taggingId = ?, fileId = ?, tag = ?, comment = ? " 
					+ " WHERE taggingId = ? ";

		DELETE_PSQL = "DELETE FROM " + TABLE_NAME + " WHERE taggingId = ? ";

		DELETE_BY_TAG_PSQL = "DELETE FROM " + TABLE_NAME + " WHERE tag = ? ";

		DELETE_ALL_SQL = "DELETE FROM " + TABLE_NAME;
	}
		

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Creates a new FileTagging record in the underlying database.
	  @param createMe a FileTagging instance containing the necessary information
					  to replicate it in the database
	  @return true if the record was created successfully
	  @throws SQLException if a problem occurs working with the database
	  */
	public boolean create(FileTagging createMe) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(CREATE_PSQL);

			try {
				ps.setInt(1, createMe.getFileId());
				ps.setString(2, createMe.getTag());
				ps.setString(3, createMe.getComment());

				return ps.executeUpdate() == 1;	// 1 row should be created
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Reads all FileTagging records from the database.
	  @return a set of retrieved FileTagging records
	  @throws SQLException if a problem occurs working with the database
	  */
	public SortedSet<FileTagging> readAll() throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			Statement stmt = conn.createStatement();

			try {
				ResultSet rs = stmt.executeQuery(READ_ALL_SQL);

				try {
					SortedSet<FileTagging> set;
				    set = new TreeSet<FileTagging>(new SQLPrimaryKeySorter());
					FileTagging next = null;

					while (rs.next()) {
						next = new FileTagging();
						next.setTaggingId(rs.getInt("taggingId"));
						next.setFileId(rs.getInt("fileId"));
						next.setTag(rs.getString("tag"));
						next.setComment(rs.getString("comment"));

						set.add(next);
					}

					return set;

				} finally {
					SQLiteRepository.closeQuietly(rs);
				}

			} finally {
				SQLiteRepository.closeQuietly(stmt);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Retrieves the information necessary to create a FileTagging object
	  from the relevant database tables.
	  @param id the file id of the record to search for
	  @return a new FileTagging if the read was successful, null otherwise
	  @throws SQLException if a problem occurs working with the database
	  */
	public FileTagging readById(Integer id) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(READ_BY_ID_PSQL);

			try {
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();

				try {
					FileTagging result = null;

					if (rs.next()) {
						result = new FileTagging();
						result.setTaggingId(id);
						result.setFileId(rs.getInt("fileId"));
						result.setTag(rs.getString("tag"));
						result.setComment(rs.getString("comment"));
					}

					return result;

				} finally {
					SQLiteRepository.closeQuietly(rs);
				}

			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Reads all FileTagging records from the database whose fileId column
	  matches the specified file id.
	  @param fileId file id to match records to
	  @return a set of records that contained the specified file id
	  @throws SQLException if a problem occurs working with the database
	  */
	public SortedSet<FileTagging> readByFileId(int fileId) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();	

		try {
			PreparedStatement ps = conn.prepareStatement(READ_BY_FILEID_PSQL);

			try {
				ps.setInt(1, fileId);
				ResultSet rs = ps.executeQuery();

				try {
					SortedSet<FileTagging> set;
				    set = new TreeSet<FileTagging>(new SQLPrimaryKeySorter());
					FileTagging next;

					while (rs.next()) {
						next = new FileTagging();
						next.setTaggingId(rs.getInt("taggingId"));
						next.setFileId(fileId);
						next.setTag(rs.getString("tag"));
						next.setComment(rs.getString("comment"));

						set.add(next);
					}

					return set;

				} finally {
					SQLiteRepository.closeQuietly(rs);
				}

			} finally {
				SQLiteRepository.closeQuietly(ps);
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		} 
	}

	/** Reads table fields from a ResultSet, creating a new FileTagging for
	  each row, and returning a sorted set of all created FileTaggings. This
	  method assumes that the ResultSet cursor is set one position before
	  the row to start reading from. The method attempts to read all fields
	  from the ResultSet. If a field is not present, the corresponding object's
	  field is left at the default value. If no fields are present in a row,
	  no object is created for the row.
	  @param rs the ResultSet to read records from
	  @return a sorted set of FileTaggings
	  @throws SQLException if a problem occurs working with the database
	  */
	public SortedSet<FileTagging> readFromResultSet(ResultSet rs)
		throws SQLException
	{
		SortedSet<FileTagging> taggings = new TreeSet<FileTagging>();
		FileTagging tagging = null;
		boolean aFieldIsSet = false;

		while (rs.next()) {
			if (tagging == null) {
				tagging = new FileTagging();
			}

			try {
				tagging.setTaggingId(rs.getInt("taggingId"));
				aFieldIsSet = true;
			} catch (SQLException e) {
			}

			try {
				tagging.setFileId(rs.getInt("fileId"));
				aFieldIsSet = true;
			} catch (SQLException e) {
			}

			try {
				tagging.setTag(rs.getString("tag"));
				aFieldIsSet = true;
			} catch (SQLException e) {
			}

			try {
				tagging.setComment(rs.getString("comment"));
				aFieldIsSet = true;
			} catch (SQLException e) {
			}

			if (aFieldIsSet) {
				taggings.add(tagging);
				tagging = null;
			}
		}

		return taggings;
	}

	/** Updates the specified FileTagging's corresponding record in the database,
	  if it exists.
	  @param updateMe the FileTagging whose information will update the record 
	  @param id the id used to choose which record is updated
	  @return true if the record existed and was updated successfully
	  @throws SQLException if a problem occurs working with the database
	  */
	public boolean update(FileTagging updateMe, Integer id) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_PSQL);

			try {
				ps.setInt(1, updateMe.getTaggingId());
				ps.setInt(2, updateMe.getFileId());
				ps.setString(3, updateMe.getTag());
				ps.setString(4, updateMe.getComment());
				ps.setInt(5, id);

				return ps.executeUpdate() == 1;	// 1 row should be updated
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Deletes the specified FileTagging's corresponding record from the 
	  database if it exists.
	  @param deleteMe the FileTagging whose record should be deleted
	  @return true if the record was deleted, or did not exist
	  @throws SQLException if a problem occurs working with the database
	  */
	public boolean delete(FileTagging deleteMe) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_PSQL);

			try {
				ps.setInt(1, deleteMe.getTaggingId());
				int rowCount = ps.executeUpdate();

				return rowCount == 0 || rowCount == 1;
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Deletes a FileTaggings from the database based on the specified
	  tag value.
	  @param tag the tag string value to identify target records by
	  @throws SQLException if a problem occurs working with the database
	  */
	public void deleteByTag(String tag) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_BY_TAG_PSQL);

			try {
				ps.setString(1, tag.toString());
				ps.executeUpdate();

			} finally {
				SQLiteRepository.closeQuietly(ps);
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}	
	}

	/** Deletes all FileTagging records from the database.
	  @throws SQLException if a problem occurs working with the database
	  */
	public void deleteAll() throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			Statement stmt = conn.createStatement();

			try {
				stmt.executeUpdate(DELETE_ALL_SQL);
				
			} finally {
				SQLiteRepository.closeQuietly(stmt);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

}
