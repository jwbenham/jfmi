package jfmi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jfmi.app.FileTag;
import jfmi.app.TaggedFile;
import static jfmi.app.TaggedFileSorters.SQLPrimaryKeySorter;
import jfmi.app.FileTagging;
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
	private static final String READ_BY_TAGS_SQL;
	private static final String READ_ALL_SQL;
	private static final String UPDATE_PSQL;
	private static final String DELETE_PSQL;
	private static final String DELETE_ALL_SQL;

	static {
		CREATE_PSQL = "INSERT INTO " + TABLE_NAME + "(path) VALUES(?)";
		READ_BY_ID_PSQL = "SELECT * FROM " + TABLE_NAME + " WHERE fileId = ? ";
		READ_BY_TAGS_SQL = "SELECT file.fileId, file.path "
		  				+ " FROM " + TABLE_NAME + " file, "
						+ FileTaggingDAO.TABLE_NAME + " t "
						+ " WHERE file.fileId = t.fileId ";
		READ_ALL_SQL = "SELECT * FROM " + TABLE_NAME;
		UPDATE_PSQL = "UPDATE " + TABLE_NAME 
					+ " SET fileId = ?, path = ? WHERE fileId = ? ";
		DELETE_PSQL = "DELETE FROM " + TABLE_NAME + " WHERE fileId = ? ";
		DELETE_ALL_SQL = "DELETE FROM " + TABLE_NAME;
	}
		

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Creates a new TaggedFile record in the underlying database.
	  @param createMe a TaggedFile instance containing the necessary information
					  to replicate it in the database
	  @return true if the record was created successfully
	  @throws SQLException if a problem occurs working with the database
	  */
	public boolean create(TaggedFile createMe) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(CREATE_PSQL);

			try {
				ps.setString(1, createMe.getFilePath());

				return ps.executeUpdate() == 1;	// 1 row should be created
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Reads all TaggedFile records from the database.
	  @return a set of retrieved TaggedFile records
	  @throws SQLException if a problem occurs working with the database
	  */
	public SortedSet<TaggedFile> readAll() throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			Statement stmt = conn.createStatement();

			try {
				ResultSet rs = stmt.executeQuery(READ_ALL_SQL);

				try {
					FileTaggingDAO tDAO = new FileTaggingDAO();
					SortedSet<TaggedFile> set;
				    set = new TreeSet<TaggedFile>(new SQLPrimaryKeySorter());
					TaggedFile next = null;

					while (rs.next()) {
						next = new TaggedFile();
						next.setFileId(rs.getInt("fileId"));
						next.setFilePath(rs.getString("path"));

						SortedSet<FileTagging> tSet;
					    tSet = tDAO.readByFileId(next.getFileId());
						next.setFileTaggings(tSet);

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

	/** Retrieves the information necessary to create a TaggedFile object
	  from the relevant database tables.
	  @param id the file id of the record to search for
	  @return a new TaggedFile if the read was successful, null otherwise
	  @throws SQLException if a problem occurs working with the database
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

						FileTaggingDAO taggingDAO = new FileTaggingDAO();

						result.setFileTaggings(taggingDAO.readByFileId(id));
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

	/** Returns a sorted set of TaggedFile objects which have been tagged with
	  any of the specified tags.
	  @param tags the tag values by which to select TaggedFile records
	  @return a sorted set of results
	  @throws SQLException if a problem occurs working with the database
	  */
	public SortedSet<TaggedFile> readByTags(Set<FileTag> tags) 
		throws SQLException
	{
		String query = getReadByTagsQuery(tags);
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			Statement stmt = conn.createStatement();

			try {
				ResultSet rs = stmt.executeQuery(query);

				try {
					
					return readFromResultSet(rs);

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

	/** Reads table fields from a ResultSet, creating a new TaggedFile for
	  each row, and returning a sorted set of all created TaggedFiles. This
	  method assumes that the ResultSet cursor is set one position before
	  the row to start reading from. The method attempts to read all fields
	  from the ResultSet. If a field is not present, the corresponding object's
	  field is left at the default value. If no fields are present in a row,
	  no object is created for the row.
	  @param rs the ResultSet to read records from
	  @return a sorted set of TaggedFiles
	  @throws SQLException if a problem occurs working with the database
	  */
	public SortedSet<TaggedFile> readFromResultSet(ResultSet rs)
		throws SQLException
	{
		FileTaggingDAO taggingDAO = new FileTaggingDAO();
		SortedSet<FileTagging> taggings = null;
		SortedSet<TaggedFile> files = new TreeSet<TaggedFile>();
		TaggedFile file = null;
		boolean aFieldIsSet = false;

		while (rs.next()) {
			if (file == null) {
				file = new TaggedFile();
			}

			try {
				file.setFileId(rs.getInt("fileId"));
				aFieldIsSet = true;

				taggings = taggingDAO.readByFileId(rs.getInt("fileId"));	
				file.setFileTaggings(taggings);
			} catch (SQLException e) {
			}

			try {
				file.setFilePath(rs.getString("path"));
				aFieldIsSet = true;
			} catch (SQLException e) {
			}

			if (aFieldIsSet) {
				files.add(file);
				file = null;
			}
		} 

		return files;
	}

	/** Updates the specified TaggedFile's corresponding record in the database,
	  if it exists.
	  @param updateMe the TaggedFile whose information will update the record 
	  @param id the id used to choose which record is updated
	  @return true if the record existed and was updated successfully
	  @throws SQLException if a problem occurs working with the database
	  */
	public boolean update(TaggedFile updateMe, Integer id) throws SQLException
	{
		Connection conn = SQLiteRepository.instance().getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_PSQL);

			try {
				ps.setInt(1, updateMe.getFileId());
				ps.setString(2, updateMe.getFilePath());
				ps.setInt(3, id);

				return ps.executeUpdate() == 1;	// 1 row should be updated
				
			} finally {
				SQLiteRepository.closeQuietly(ps);				
			}

		} finally {
			SQLiteRepository.closeQuietly(conn);
		}
	}

	/** Deletes the specified TaggedFile's corresponding record from the 
	  database if it exists.
	  @param deleteMe the TaggedFile whose record should be deleted
	  @return true if the record was deleted, or did not exist
	  @throws SQLException if a problem occurs working with the database
	  */
	public boolean delete(TaggedFile deleteMe) throws SQLException
	{
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

	/** Deletes all TaggedFile records from the database.
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


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Creates an SQL query used by the readByTags() method.
	  @param tags a set of FileTags to be used in the query
	  @return the created SQL query string, or null if tags is null
	  */
	private String getReadByTagsQuery(Set<FileTag> tags)
	{
		Iterator<FileTag> it = tags.iterator();
		String tag;
		StringBuilder sql = new StringBuilder(READ_BY_TAGS_SQL);

		if (it.hasNext()) {
			tag = StringUtil.doubleQuote(it.next().getTag());
			sql.append(" AND ( t.tag = " + tag);
		} else {
			return null;
		}

		while (it.hasNext()) {
			tag = StringUtil.doubleQuote(it.next().getTag());
			sql.append(", OR t.tag = " + tag);
		}

		sql.append(" )");
		return sql.toString();
	}

}
