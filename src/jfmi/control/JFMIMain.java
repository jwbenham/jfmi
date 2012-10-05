package jfmi.control;

import java.sql.SQLException;

import jfmi.gui.JFMIFrame;
import jfmi.database.SQLiteDatabase;
import jfmi.database.FileRecord;
import jfmi.database.TagRecord;
import jfmi.database.TaggingRecord;

/** A skeleton class used to launch a JFMIApp instance.
  */
public final class JFMIMain {

	/** Application entry point.
	  @param args Command-line arguments - these are ignored.
	  */
	public static void main(String[] args)
	{
		JFMIApp app = JFMIApp.getSingleton();
		app.start();
	}

}
