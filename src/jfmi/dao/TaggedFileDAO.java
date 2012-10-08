package jfmi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jfmi.control.TaggedFile;
import jfmi.repo.SQLiteRepository;
import jfmi.util.StringUtil;


/** A TaggedFileDAO provides data access for storing TaggedFile objects
  in an underlying database.
  */
public class TaggedFileDAO extends AbstractDAO<TaggedFile, Integer> {

	// PUBLIC CLASS Fields
	public static final String TABLE_NAME = "main.TaggedFile";

	// PRIVATE CLASS Fields
	private static final String CREATE_PSQL;
	private static final String READ_BY_ID_PSQL;
	private static final String UPDATE_PSQL;
	private static final String DELETE_PSQL;

	static {
		CREATE_PSQL = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?)";
		READ_BY_ID_PSQL = "SELECT * FROM " + TABLE_NAME + " WHERE fileId = ? ";
		UPDATE_PSQL = "UPDATE " + TABLE_NAME + " SET path = ? WHERE fileId = ? ";
		DELETE_PSQL = "DELETE FROM " + TABLE_NAME + " WHERE fileId = ? ";
	}
		

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Creates a new TaggedFile record in the underlying database. If the
	  createMe parameter is null, a no-op occurs.
	  @param createMe a TaggedFile instance containing the necessary information
					  to replicate it in the database
	  @param true if the record was created successfully
	  */
	public boolean create(TaggedFile createMe) throws SQLException
	{
		if (createMe == null) {
			return false;
		}

		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(CREATE_PSQL);

			try {
				ps.setInt(1, createMe.getFileId());
				ps.setString(2, createMe.getFilePath());

				return ps.executeUpdate() == 1;	// 1 row should be created
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}


	/** Retrieves the information necessary to create a TaggedFile object
	  from the relevant database tables.
	  @param id the file id of the record to search for
	  @return a new TaggedFile if the read was successful, null otherwise
	  @throws SQLException if there is a problem with the database
	  */
	public TaggedFile readById(Integer id) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(READ_BY_ID_PSQL);

			try {
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();

				try {
					TaggedFile result = null;

					if (rs.next()) {
						result = new TaggedFile();
						result.setFileId(id);
						result.setFilePath(rs.getString("path"));	
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

	/** Update the specified TaggedFile's corresponding record in the database,
	  if it exists.
	  @param updateMe the TaggedFile whose record will be updated
	  @return true if the record existed and was updated successfully
	  @throws SQLException if a database error occurs
	  */
	public boolean update(TaggedFile updateMe) throws SQLException
	{
		if (updateMe == null) {
			return false;
		}

		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_PSQL);

			try {
				ps.setString(1, updateMe.getFilePath());
				ps.setInt(2, updateMe.getFileId());

				return ps.executeUpdate() == 1;	// 1 row should be updated
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Delete the specified TaggedFile's corresponding record from the 
	  database if it exists.
	  @param deleteMe the TaggedFile whose record should be deleted
	  @return true if the record was deleted, or did not exist
	  @throws SQLException if a database error occurs
	  */
	public boolean delete(TaggedFile deleteMe) throws SQLException
	{
		if (deleteMe == null) {
			return false;
		}

		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_PSQL);

			try {
				ps.setInt(1, deleteMe.getFileId());
				int rowCount = ps.executeUpdate();

				return rowCount == 0 || rowCount == 1;
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

}
