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
	
	/** Get a reference to the JFMIApp singleton. The singleton is created
	  if it does not already exist.
	  @return A reference to the (possible newly created) JFMIApp singleton.
	  @throws ClassNotFoundException if the driver for the database is not found
	  @throws SQLException if the database must be newly created and an SQL error
	  			occurs.
	  */
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
	  @return a reference to the instance's JFMIFrame
	  */
	public JFMIFrame getJFMIGUI()
	{
		return jfmiGUI;
	}

	/** Accessor for the jfmiDatabase field.
	  @return a reference to the instance's SQLiteDatabase
	  */
	public SQLiteDatabase getJFMIDatabase()
	{
		return jfmiDatabase;
	}

	//************************************************************	
	// PRIVATE INSTANCE Methods
	//************************************************************	

	/** Construct an instance of a JFMIApp. This constructor is private to
	maintain the singleton pattern.
	TODO move field instantiation to an init() method.
	 */
	private JFMIApp() throws 
		ClassNotFoundException,
		SQLException
	{
		jfmiDatabase = new SQLiteDatabase(DATABASE_PATH);
		tagHandler = new TagHandler(this);
		jfmiGUI = new JFMIFrame(this);
	}

}
