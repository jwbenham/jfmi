package tests.jfmi.dao;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import jfmi.control.TaggedFile;
import jfmi.dao.TaggedFileDAO;
import jfmi.repo.SQLiteRepository;

/** Implements unit tests for the TaggedFileDAO class.
  */
public class TaggedFileDAOTest {
	private SQLiteRepository repo;
	private TaggedFile crudFile;

	@Before
	public void setUp()
	{
		System.out.println("setup()");

		try {
			SQLiteRepository.instance().initialize();
			repo = SQLiteRepository.instance();
		} catch (ClassNotFoundException e) {
			fail("test failed: " + e.toString());
		} catch (SQLException e) {
			fail("test failed: " + e.toString());
		}
	}

	@Test(expected= NullPointerException.class)
	public void testCreate_NullParams()
	{
		System.out.println("testCreate_NullParams()");

		TaggedFileDAO taggedFileDAO = new TaggedFileDAO();

		try {
			taggedFileDAO.create(null);
			
		} catch (SQLException e) {
			fail("test failed: " + e.toString());
		}
	}

	/* Uses crudFile. */
	@Test
	public void testCreate_NonNullParams()
	{
		System.out.println("testCreate_NullParams()");

		crudFile = new TaggedFile(0, "path/to/file", null);
		TaggedFileDAO taggedFileDAO = new TaggedFileDAO();

		try {
			boolean created = taggedFileDAO.create(crudFile);
			assertTrue(created);
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

}
