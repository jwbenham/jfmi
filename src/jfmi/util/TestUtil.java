package jfmi.util;

import jfmi.control.TaggedFile;
import jfmi.database.FileRecord;
import jfmi.database.TaggingRecord;

/** A utility class used for testing/generating "fake" data.
  */
public final class TestUtil {

	/** Generates an array of generic TaggedFiles.
	  @return An array of TaggedFile objects.
	  */
	public static TaggedFile[] getArrayOfTaggedFile()
	{
		TaggingRecord[] tags = {
			new TaggingRecord(-1, "tag1", "comment1"),
			new TaggingRecord(-1, "tag2", "comment2"),
			new TaggingRecord(-1, "tag3", "comment3"),
			new TaggingRecord(-1, "tag4", "comment4")
		};

		TaggedFile[] arr = {
			new TaggedFile(
					new FileRecord(0, "/path/file0"), tags
					),
			new TaggedFile(
					new FileRecord(1, "/path/file1"), tags
					),
			new TaggedFile(
					new FileRecord(2, "/path/file2"), tags
					),
			new TaggedFile(
					new FileRecord(3, "/path/file3"), tags
					),
			new TaggedFile(
					new FileRecord(4, "/path/file4"), tags
					)
		};

		return arr;
	}	

}
