package tests.jfmi.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import jfmi.util.StringUtil;

public class StringUtilTest {

	@Test
	public void parseNameFromPathTest()
	{
		System.out.println("parseNameFromPathTest()");

		final String PATH = "some/path/filename";
		final String NOPATH = "filename";
		String result;

		result = StringUtil.parseNameFromPath(PATH);
		assertTrue(result.equals(NOPATH));

		result = StringUtil.parseNameFromPath(NOPATH);
		assertTrue(result.equals(NOPATH));
	}

	@Test 
	public void doubleQuoteTest()
	{
		System.out.println("doubleQuoteTest()");

		final String UNQUOTED = "doubleQuote test string";
		final String QUOTED = "\"doubleQuote test string\"";
		String result;

		result = StringUtil.doubleQuote(UNQUOTED);
		assertTrue(result.equals(QUOTED));
	}

}
