package jfmi.control;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

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
	private Vector<TaggedFile> taggedFiles; 
	
	private SQLiteRepository jfmiRepo;
	private TaggedFileDAO taggedFileDAO;


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

	/** Begins an interaction with the user that allows them to add new files
	  to the repository for tagging.
	  */
	public void beginAddFileInteraction()
	{
		File[] selectedFiles = jfmiGUI.displayFileChooser();
		addFilesToRepo(selectedFiles);
		readTaggedFilesFromRepo(true);
		updateGUITaggedFileJList();
	}

	/** Begins an interaction with the user that allows them to delete
	  selected files from the repository.
	  @param selectedFiles files selected by the user for deletion
	  */
	public void beginDeleteFilesInteraction(List<TaggedFile> selectedFiles)
	{
		if (jfmiGUI.getConfirmation("Confirm deletion of files.") == false) {
			return;	
		}

		deleteFilesFromRepo(selectedFiles);
		readTaggedFilesFromRepo(true);
		updateGUITaggedFileJList();
	}

	/** Starts execution of the application.
	  @return true if the application starts successfully
	  */
	public boolean start() 
	{
		if (initJFMIRepo(true) && readTaggedFilesFromRepo(true)) {
			updateGUITaggedFileJList();
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
		taggedFileDAO = new TaggedFileDAO();
	}

	/** Given an array of File objects, attempts to create TaggedFile objects
	  and insert them into the repository.
	  @param files an array of File to be newly created in the repository
	  */
	private void addFilesToRepo(File[] files)
	{
		TaggedFile newFile;

		if (files != null) {
			for (File f : files) {
				newFile = new TaggedFile();
				newFile.setFile(f);
				
				addTaggedFileToRepo(newFile, true);
			}
		}

	}

	/** Attempts to add a TaggedFile to the repository. If showErrors is
	  true, any errors which occur are displayed to the user.
	  @param file the TaggedFile to be added to the repository
	  @param showErrors if true, the user receives error messages
	  @return true if the file was added successfully
	  */
	private boolean addTaggedFileToRepo(TaggedFile file, boolean showErrors)
	{
		try {
			boolean creationSuccess = taggedFileDAO.create(file);

			if (!creationSuccess && showErrors) {
				GUIUtil.showErrorDialog(
					"The following file could not be added: " 
					+ file.getFilePath()
				);
			}

			return creationSuccess;

		} catch (SQLException e) {
			if (showErrors) {
				GUIUtil.showErrorDialog(
					"An error occurred while adding the file: " 
					+ file.getFilePath(), 
					e.toString()
				);
			}
		}

		return false;
	}

	/** Deletes the TaggedFiles in the specified list from the repository.
	  @param files list of files to be deleted
	  */
	private void deleteFilesFromRepo(List<TaggedFile> files)
	{
		if (files == null) {
			return;
		}
	
		for (TaggedFile tf : files) {
			deleteTaggedFileFromRepo(tf, true);
		}
	}

	/** Deletes a TaggedFile from the underlying repository.
	  @param file the file to delete
	  @param showErrors if true, the method displays any error messages
	  @return true if the file was deleted successfully
	  */
	private boolean deleteTaggedFileFromRepo(TaggedFile file,
											 boolean showErrors)
	{
		try {
			return taggedFileDAO.delete(file);

		} catch (SQLException e) {
			if (showErrors) {
				GUIUtil.showErrorDialog(
					"Failed to delete file: " + file.getFilePath(),
					e.toString()
				);
			}
		}

		return false;
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

	/** Constructs a new Vector<TaggedFile> for this instance from the
	  specified Collection.
	  @param collection The Collection to construct a new Vector from.
	  */
	private void setTaggedFiles(Collection<TaggedFile> collection)
	{
		taggedFiles = new Vector<TaggedFile>(collection);
	}

	/** Gets an updated list of TaggedFiles from the repository, and updates
	  the taggedFiles field.
	  @param showError if true, and an error occurs, display a message
	  @return true if the files were refreshed successfully
	  */
	private boolean readTaggedFilesFromRepo(boolean showError)
	{
		try {
			setTaggedFiles(taggedFileDAO.readAll());

		} catch (SQLException e) {
			if (showError) {
				GUIUtil.showErrorDialog(
					"Failed to refresh the list of files from the database.",
					e.toString()
				);
			}
			return false;
		}

		return true;
	}

	/** Updates this instance's GUI with the latest values in the Vector
	  of TaggedFile objects.
	  */
	private void updateGUITaggedFileJList()
	{
		jfmiGUI.setTaggedFileJListData(taggedFiles);
	}
}
