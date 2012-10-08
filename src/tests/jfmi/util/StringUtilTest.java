package tests.jfmi.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import jfmi.util.StringUtil;

/** Implements unit tests for the jfmi.util.StringUtil class.
  */
public class StringUtilTest {

	@Test
	public void testParseNameFromPath_NonNullParams()
	{
		System.out.println("testParseNameFromPath_NonNullParams()");

		final String PATH = "some/path/filename";
		final String NOPATH = "filename";
		String result;

		result = StringUtil.parseNameFromPath(PATH);
		assertTrue(result.equals(NOPATH));

		result = StringUtil.parseNameFromPath(NOPATH);
		assertTrue(result.equals(NOPATH));
	}

	@Test(expected= NullPointerException.class)
	public void testParseNameFromPath_NullParams()
	{
		System.out.println("testParseNameFromPath_NullParams()");
		StringUtil.parseNameFromPath(null);
	}

	@Test 
	public void testDoubleQuote_NonNullParams()
	{
		System.out.println("testDoubleQuote_NonNullParams()");

		final String UNQUOTED = "doubleQuote test string";
		final String QUOTED = "\"doubleQuote test string\"";
		String result;

		result = StringUtil.doubleQuote(UNQUOTED);
		assertTrue(result.equals(QUOTED));
	}

	@Test 
	public void testDoubleQuote_NullParams()
	{
		System.out.println("testDoubleQuote_NullParams()");
		
		final String expected = "\"null\"";
		String actual = StringUtil.doubleQuote(null);

		assertEquals(expected, actual);
	}

}
