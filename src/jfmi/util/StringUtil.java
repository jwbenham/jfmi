package jfmi.util;


public final class StringUtil {

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
