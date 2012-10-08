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

	static {
		CREATE_PSQL = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?)";
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
				ps.setString(2, createMe.getPath());

				return ps.executeUpdate() == 1;	// 1 row should be created
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	// read
	public TaggedFile readById(Integer id) throws SQLException
	{

		return new TaggedFile();
	}

	// update
	public void update(TaggedFile updateMe) throws SQLException
	{
		if (updateMe == null) {
			return;
		}
	}

	// delete
	public void delete(TaggedFile deleteMe) throws SQLException
	{
		if (deleteMe == null) {
			return;
		}
	}
}
