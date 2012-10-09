package jfmi.control;

import java.sql.SQLException;

import jfmi.dao.TaggedFileDAO;
import jfmi.gui.JFMIFrame;
import jfmi.gui.GUIUtil;
import jfmi.repo.SQLiteRepository;

/** JFMIApp is the primary application controller class.
  */
public final class JFMIApp {
	// Class fields
	private static JFMIApp singleton = null;
	private static final String DB_PATH = "./jfmi.db";

	// Instance fields
	private JFMIFrame jfmiGUI;
	
	private SQLiteRepository jfmiRepo;

	private TaggedFileHandler fileHandler;
	private FileTagHandler tagHandler;
	private FileTaggingHandler taggingHandler;


	//************************************************************	
	// PUBLIC CLASS methods
	//************************************************************	
	
	/** Get a reference to the JFMIApp singleton. The singleton is created
	  if it does not already exist.
	  @return A reference to the (possible newly created) JFMIApp singleton.
	  */
	public static JFMIApp instance()
	{
		if (singleton == null) {
			singleton = new JFMIApp();
		}

		return singleton;
	}

	//************************************************************	
	// PUBLIC INSTANCE Methods
	//************************************************************	

	/** Provides access to the application file handler.
	  @return a reference to the application file handler
	  */
	public TaggedFileHandler getFileHandler()
	{
		return fileHandler;
	}

	/** Provides access to the application tag handler.
	  @return a reference to the application tag handler
	  */
	public FileTagHandler getTagHandler()
	{
		return tagHandler;
	}

	/** Provides access to the application tagging handler.
	  @return a reference to the application tagging handler
	  */
	public FileTaggingHandler getTaggingHandler()
	{
		return taggingHandler;
	}

	/** Starts execution of the application.
	  @return true if the application starts successfully
	  */
	public boolean start() 
	{
		if (initJFMIRepo(true) && fileHandler.updateDataAndGUI(true)) {
			jfmiGUI.setVisible(true);
			return true;
		} 

		GUIUtil.showErrorDialog(
			"An error occurred which prevented the application from starting."
		);

		return false;
	}

	/** Accessor for jfmiGUI field.
	  @return a reference to the instance's JFMIFrame
	  */
	public JFMIFrame getJFMIGUI()
	{
		return jfmiGUI;
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
		jfmiGUI = new JFMIFrame(this);

		fileHandler = new TaggedFileHandler(this);
		fileHandler.setJFMIGUI(jfmiGUI);
		tagHandler = new FileTagHandler(this);
		taggingHandler = new FileTaggingHandler(this);
	}

	/** Tries to initialize an SQLiteRepository object associated with the
	  SQLite database located at DB_PATH. If an error occurs while initializing
	  repository, an error message is displayed if "showError" is true.
	  @param showError if true, and an error occurs, display a message
	  @return true if the repository was successfully initialized
	  */
	private boolean initJFMIRepo(boolean showError)
	{
		try {
			jfmiRepo = SQLiteRepository.instance();
			jfmiRepo.setRepoPath(DB_PATH);
			jfmiRepo.initialize();

			return true;

		} catch (ClassNotFoundException e) {
			if (showError) {
				GUIUtil.showErrorDialog(
						"The application failed to load the SQLite database"
						+ "driver.", 
						e.getMessage()
				);
			}
		} catch (SQLException e) {
			if (showError) {
				GUIUtil.showErrorDialog(
						 "An error occurred while attempting to access the "
						+ "application database at: " + "\n" + DB_PATH,
						e.getMessage()
				);
			}
		}

		return false;
	}

}
