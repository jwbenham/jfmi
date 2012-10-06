package jfmi.control;

import java.sql.SQLException;

import jfmi.gui.JFMIFrame;
import jfmi.database.*;

/** A skeleton class used to launch a JFMIApp instance.
  */
public final class JFMIMain {

	/** Application entry point.
	  @param args Command-line arguments - these are ignored.
	  */
	public static void main(String[] args)
	{
		JFMIApp app = JFMIApp.getSingleton();
		//app.start();

		DatabaseRecord file = new FileRecord();
		DatabaseRecord tag = new TagRecord();
		DatabaseRecord tagging = new TaggingRecord();

		System.out.println("FileRecord:");
		System.out.println("Should be: " + FileRecord.getMatchesPSQL());
		System.out.println("Reality: " + file.getMatchesPSQL());
		System.out.println();

		System.out.println("TagRecord:");
		System.out.println("Should be: " + TagRecord.getMatchesPSQL());
		System.out.println("Reality: " + tag.getMatchesPSQL());
		System.out.println();

		System.out.println("TaggingRecord:");
		System.out.println("Should be: " + TaggingRecord.getMatchesPSQL());
		System.out.println("Reality: " + tagging.getMatchesPSQL());
	}

}
