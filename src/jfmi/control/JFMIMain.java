package jfmi.control;

import java.sql.SQLException;

import jfmi.gui.JFMIFrame;
import jfmi.database.SQLiteDatabase;
import jfmi.database.FileRecord;
import jfmi.database.TagRecord;
import jfmi.database.TaggingRecord;

public final class JFMIMain {

	public static void main(String[] args)
	{
		try {
			JFMIApp app = JFMIApp.getSingleton();
			app.start();
		} catch (ClassNotFoundException e) {

		} catch(Exception e) {

		}
	}

}
