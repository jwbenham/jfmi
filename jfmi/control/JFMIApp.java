/**
  @author Joram Benham
  */

package jfmi.control;

import java.sql.SQLException;

import jfmi.database.SQLiteDatabase;
import jfmi.gui.JFMIFrame;

/** JFMIApp is the primary application controller class.
  */
public final class JFMIApp {
	// Class fields
	public static final String DATABASE_PATH = "./jfmi.db";
	private static JFMIApp singleton = null;

	// Instance fields
	private TagHandler tagHandler;

	private SQLiteDatabase jfmiDatabase;

	private JFMIFrame jfmiGUI;


	//************************************************************	
	// PUBLIC CLASS methods
	//************************************************************	
	
	/** Return a reference to the singleton JFMIApp */
	public static JFMIApp getSingleton() throws 
		ClassNotFoundException,
		SQLException
	{
		if (singleton == null) {
			singleton = new JFMIApp();
		}

		return singleton;
	}

	//************************************************************	
	// PUBLIC INSTANCE Methods
	//************************************************************	

	/** Starts execution of the application.
	  */
	public void start()
	{
		// Display the GUI
		jfmiGUI.setVisible(true);
	}

	/** Let the user manage/add/remove what tags are used by jfmi.
	  */
	public void manageTags()
	{
		tagHandler.manageTags();
	}

	/** Accessor for jfmiGUI field.
	  */
	public JFMIFrame getJFMIGUI()
	{
		return jfmiGUI;
	}

	/** Accessor for the jfmiDatabase field.
	  */
	public SQLiteDatabase getJFMIDatabase()
	{
		return jfmiDatabase;
	}

	//************************************************************	
	// PRIVATE INSTANCE Methods
	//************************************************************	

	/** Ctor: default. */
	private JFMIApp() throws 
		ClassNotFoundException,
		SQLException
	{
		jfmiDatabase = new SQLiteDatabase(DATABASE_PATH);
		tagHandler = new TagHandler(this);
		jfmiGUI = new JFMIFrame(this);
	}

}
