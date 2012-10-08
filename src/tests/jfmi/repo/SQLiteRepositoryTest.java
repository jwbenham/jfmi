package tests.jfmi.repo;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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

}
