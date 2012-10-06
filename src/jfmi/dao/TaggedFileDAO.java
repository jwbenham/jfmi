package jfmi.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jfmi.control.TaggedFile;
import jfmi.repo.AbstractRepository;
import jfmi.util.StringUtil;


/** A TaggedFileDAO provides data access for storing TaggedFile objects
  in an underlying database.
  */
public class TaggedFileDAO extends AbstractDAO<TaggedFile, Integer> {

	// PUBLIC CLASS Fields
	public static final String TABLE_NAME = "main.TaggedFile";

	// PRIVATE CLASS Fields
	private static final String CREATE_SQL;

	static {
		CREATE_SQL = "INSERT INTO " + TABLE_NAME + " VALUES( "
					+ createMe.getFileId() + ", "
					+ StringUtil.doubleQuote(createMe.getPath()) 
					+ " ) ";
	}
		

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Creates a new TaggedFile record in the underlying database. If the
	  createMe parameter is null, a no-op occurs.
	  @param createMe a TaggedFile instance containing the necessary information
					  to replicate it in the database
	  */
	public void create(TaggedFile createMe)
	{
		if (createMe == null) {
			return;
		}

		Connection conn = SQLiteRepository.instance().getConnection();
		

	}

	// read
	public TaggedFile readById(Integer id)
	{

		return new TaggedFile();
	}

	// update
	public void update(TaggedFile updateMe)
	{
		if (updateMe == null) {
			return;
		}
	}

	// delete
	public void delete(TaggedFile deleteMe)
	{
		if (deleteMe == null) {
			return;
		}
	}
}
