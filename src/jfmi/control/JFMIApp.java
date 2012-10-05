package jfmi.control;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Arrays;
import java.util.Vector;

import jfmi.database.SQLiteDatabase;
import jfmi.gui.JFMIFrame;
import jfmi.gui.GUIUtil;
import jfmi.util.TestUtil;

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

	private Vector<TaggedFile> taggedFileVector; 


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
	public static JFMIApp getSingleton()
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
	  @return true if the application starts successfully
	  */
	public boolean start() 
	{
		if (initJFMIDatabase()) {
			refreshTaggedFileVectorFromDatabase();
			//sortTaggedFileVector();
			updateGUITaggedFileJList();

			jfmiGUI.setVisible(true);

			return true;

		} else {
			GUIUtil.showErrorDialog(
				"The application cannot start because the"
				+ " initialization of the database failed."
			);

			return false;
		}
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
	 maintain the singleton pattern. Initializing the database is deferred
	 until start() is called.
	 */
	private JFMIApp()
	{
		tagHandler = new TagHandler(this);
		jfmiGUI = new JFMIFrame(this);
	}

	/** Tries to construct an SQLiteDatabase object associated with the
	  SQLite database located at DATABASE_PATH. If the SQLiteDatabase
	  constructor throws and exception, an error message is displayed.
	  @return true if the database was successfully initialized
	  */
	private boolean initJFMIDatabase()
	{
		try {
			jfmiDatabase = new SQLiteDatabase(DATABASE_PATH);
			return true;

		} catch (ClassNotFoundException e) {
			GUIUtil.showErrorDialog(
					"The application failed to load the "
					+ "SQLite database driver."
					+ "\n\nDetails:\n" + e.getMessage()
			);
		} catch (SQLException e) {
			GUIUtil.showErrorDialog(
					 "An error occurred while attempting to access the "
					+ "application database at: "
					+ "\n" + DATABASE_PATH
					+ "\n\nDetails:\n" + e.getMessage()
			);
		}

		return false;
	}

	/** Constructs a new Vector<TaggedFile> for this instance from the
	  specified Collection.
	  @param collection The Collection to construct a new Vector from.
	  */
	private void setTaggedFileVector(Collection<TaggedFile> collection)
	{
		taggedFileVector = new Vector<TaggedFile>(collection);
	}

	/** Gets an updated list of TaggedFiles from the database, and updates
	  the taggedFileVector.
	  */
	private boolean refreshTaggedFileVectorFromDatabase()
	{
		// TODO: implement
		taggedFileVector = new Vector<TaggedFile>(
									Arrays.asList(
										TestUtil.getArrayOfTaggedFile()
									)
								);
		return false;
	}

	/** Updates this instance's GUI with the latest values in the Vector
	  of TaggedFile objects.
	  */
	private void updateGUITaggedFileJList()
	{
		jfmiGUI.setTaggedFileJListData(taggedFileVector);
	}
}
