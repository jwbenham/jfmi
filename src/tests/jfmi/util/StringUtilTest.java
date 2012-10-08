package tests.jfmi.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import jfmi.util.StringUtil;

public class StringUtilTest {

	@Test public void doubleQuoteTest()
	{
		final String UNQUOTED = "doubleQuote test string";
		final String QUOTED = "\"doubleQuote test string\"";
		String result;

		result = StringUtil.doubleQuote(UNQUOTED);
		assertTrue(result.equals(QUOTED));
	}

}
