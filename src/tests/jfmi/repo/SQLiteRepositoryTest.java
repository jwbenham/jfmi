package tests.jfmi.repo;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import jfmi.repo.SQLiteRepository;

/** Implements unit tests for the jfmi.repo.SQLiteRepository class.
  */
public class SQLiteRepositoryTest {

	@Test
	public void testInstance()
	{
		System.out.println("testInstance()");
		assertTrue(SQLiteRepository.instance() == SQLiteRepository.instance());
	}

	@Test
	public void testSetRepoPath_NonNullParams()
	{
		System.out.println("testSetRepoPath_NonNullParams()");

		final String path = "testpath/repo.db";
		SQLiteRepository.instance().setRepoPath(path);

		String actual = SQLiteRepository.instance().getRepoPath();
		assertEquals(path, actual);
	}

	@Test
	public void testSetRepoPath_NullParams()
	{
		System.out.println("testSetRepoPath_NullParams()");
		
		final String expected = "";
		SQLiteRepository.instance().setRepoPath(null);

		String actual = SQLiteRepository.instance().getRepoPath();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetRepoURL()
	{
		System.out.println("testGetRepoURL()");

		final String path = "test/path/repo.db";
		final String expected = "jdbc:sqlite:" + path;

		SQLiteRepository.instance().setRepoPath(path);
		String actual = SQLiteRepository.instance().getRepoURL();

		assertEquals(expected, actual);
	}

	@Test
	public void testInitialize()
	{
		System.out.println("testInitialize()");

		try {
			SQLiteRepository.instance().initialize();
		} catch (ClassNotFoundException e) {
			fail("test failed: " + e.toString());
		} catch (SQLException e) {
			fail("test failed: " + e.toString());
		}
		
		assertTrue(SQLiteRepository.instance().isInitialized());
	}

}
