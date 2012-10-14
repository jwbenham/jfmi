package jfmi.control;

import java.util.Collection;
import java.sql.SQLException;

import jfmi.app.FileTag;
import jfmi.app.FileTagging;
import jfmi.dao.FileTaggingDAO;
import jfmi.gui.GUIUtil;

/** A TaggingHandler handles application logic concerned with adding, removing,
  or updating taggings in the database. Taggings represent a unique tagging
  of a file with a tag and optional comment.
  */
public class FileTaggingHandler {

	// PRIVATE INSTANCE Fields
	private JFMIApp jfmiApp;
	private FileTaggingDAO fileTaggingDAO;


	//************************************************************	
	// PUBLIC CLASS methods
	//************************************************************	

	/** Constructs a new FileTaggingHandler associated with the specified
	  JFMIApp.
	  @param jfmiApp_ the JFMIApp to associate this handler with
	  @throws IllegalArgumentException if jfmiApp_ is null
	  */
	public FileTaggingHandler(JFMIApp jfmiApp_)
	{
		init(jfmiApp_);
	}

	/** Creates the specified tagging in the repository.
	  @param tagging the FileTagging to add to the repository
	  @param showErrors if true, errors will be displayed
	  @return true if no errors occurred
	  */
	public boolean addTaggingToRepo(FileTagging tagging, boolean showErrors)
	{
		try {
			boolean created = fileTaggingDAO.create(tagging);

			if (!created && showErrors) {
				GUIUtil.showErrorDialog(
					"Could not create the tagging in the repository."
					+ " This may be because it already exists."
				);
			}
			
			return created;
		} catch (SQLException e) {
			if (showErrors) {
				GUIUtil.showErrorDialog(
					"A repository error occurred while updating a tagging.",
					e.toString()
				);
			}
		}

		return false;
	}

	/** Creates a Collection of FileTaggings in the repository.
	  @param taggings the Collection of FileTaggings to create
	  @param showErrors if true, errors will be displayed
	  @return true if no errors occurred
	  */
	public boolean addTaggingsToRepo(Collection<FileTagging> taggings,
									 boolean showErrors)
	{
		if (taggings == null) {
			return true;
		}

		boolean allAdded = true;

		for (FileTagging ft : taggings) {
			if (addTaggingToRepo(ft, showErrors) == false) {
				allAdded = false;
			}
		}

		return allAdded;
	}

	/** Deletes a subset of FileTaggings from the underlying repository,
	  identified by the specified FileTag collection.
	  @param tags a collection of tags by which to target taggings for deletion
	  @param showErrors if true, error messages will be displayed
	  @return true if no problems occurred deleting the taggings
	  */
	public boolean deleteTaggingsByTags(Collection<FileTag> tags,
										boolean showErrors)
	{
		boolean deleteSuccessful = true;

		for (FileTag tag : tags) {
			if (deleteTaggingsByTag(tag, showErrors) == false) {
				deleteSuccessful = false;
			}
		}

		return deleteSuccessful;
	}

	/** Deletes all FileTagging records which contain the specified tag from
	  the underlying repository.
	  @param tag the tag by which to identify records for deletion
	  @param showErrors if true, error messages will be displayed
	  @return true if no errors occurred
	  */
	public boolean deleteTaggingsByTag(FileTag tag, boolean showErrors)
	{
		try {
			fileTaggingDAO.deleteByTag(tag.getTag().toString());
			return true;

		} catch (SQLException e) {
			if (showErrors) {
				GUIUtil.showErrorDialog(
					"An error occurred while accessing the repository to delete"
					+ " file taggings with tag \"" + tag.getTag() + "\".",
					e.toString()
				);
			}
		}

		return false;
	}	

	/** Deletes a FileTagging from the repository.
	  @param tagging the FileTagging to remove
	  @param showErrors if true, errors are displayed
	  @return true if no errors occurred
	  */
	public boolean deleteTaggingFromRepo(FileTagging tagging, 
										 boolean showErrors)
	{
		try {
			fileTaggingDAO.delete(tagging);
			return true;

		} catch (SQLException e) {
			if (showErrors) {
				GUIUtil.showErrorDialog(
					"A repository error occurred while deleting a tagging.",
					e.toString()
				);
			}
		}

		return false;
	}

	/** Removes a Collection of FileTaggings from the repository.
	  @param taggings a Collection of FileTaggings to remove
	  @param showErrors if true, errors are displayed
	  @return true if no errors occurred
	  */
	public boolean deleteTaggingsFromRepo(Collection<FileTagging> taggings,
										  boolean showErrors)
	{
		if (taggings == null) {
			return true;
		}

		boolean allGone = true;

		for (FileTagging ft : taggings) {
			if (deleteTaggingFromRepo(ft, showErrors) == false) {
				allGone = false;
			}
		}

		return allGone;
	}

	/** Associates this handler with the specified JFMIApp.
	  @param jfmiApp_ the JFMIApp to associate this handler with
	  */
	public void setJFMIApp(JFMIApp jfmiApp_)
	{
		if (jfmiApp_ == null) {
			throw new IllegalArgumentException("jfmiApp_ cannot be null");
		}

		jfmiApp = jfmiApp_;
	}

	/** Updates a FileTagging in the repository.
	  @param tagging the FileTagging to update
	  @param showErrors if true, errors are displayed to the user
	  @return true if no errors occurred
	  */
	public boolean updateFileTaggingInRepo(
		FileTagging tagging, 
		boolean showErrors
	)
	{
		try {
			boolean updated;
		    updated = fileTaggingDAO.update(tagging, tagging.getTaggingId());

			if (!updated && showErrors) {
				GUIUtil.showErrorDialog(
					"Failed to update the file tagging in the repository."
				);
			}

			return updated;

		} catch (SQLException e) {
			if (showErrors) {
				GUIUtil.showErrorDialog(
					"An repository error occurred while updating the file"
					+ " tagging.",
					e.toString()
				);
			}
		}

		return false;
	}

	/** Updates a Collection of FileTaggings in the repository.
	  @param taggings a Collection<FileTagging> to update
	  @param showErrors if true, errors are displayed to the user
	  @return true if no errors occurred
	  */
	public boolean updateFileTaggingsInRepo(
		Collection<FileTagging> taggings,
		boolean showErrors
	)
	{
		if (taggings == null) {
			return true;
		}

		boolean taggingsAreUpdated = true;

		for (FileTagging ft : taggings) {
			if (updateFileTaggingInRepo(ft, showErrors) == false) {
				taggingsAreUpdated = false;
			}
		}		

		return taggingsAreUpdated;
	}


	//************************************************************	
	// PRIVATE CLASS methods
	//************************************************************	

	/** Initialize the handler.
	  @param jfmiApp_ the JFMIApp to associate this handler with
	  @throws IllegalArgumentException if jfmiApp_ is null
	  */
	private final void init(JFMIApp jfmiApp_)
	{
		setJFMIApp(jfmiApp_);
		fileTaggingDAO = new FileTaggingDAO();
	}

}
