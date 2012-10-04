package jfmi.util;


/** Provides utility methods for working with Strings and related types.
  */
public final class StringUtil {

	/** Parses a file or directory name from the end of a delimited path.
	  @param path The path to parse.
	  @return The file or directory name specified by the full path.
	  */
	public static String parseNameFromPath(String path)
	{
		String rpath = path.replace('\\', '/');

		int lastDelim = rpath.lastIndexOf('/');

		if (lastDelim < 0) {
			return rpath;
		} else {
			return rpath.substring(lastDelim + 1);
		}
	}

}
