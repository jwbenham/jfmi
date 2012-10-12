package jfmi.control;

import java.util.TreeSet;

import jfmi.control.TaggedFile;
import jfmi.control.FileTagging;

/** An EditedTaggedFile represents a TaggedFile that is being edited by the
  user, but which has not been saved to the repository yet. It keeps track of
  the TaggedFile being edited, as well as the taggings that have been added
  to and removed from the TaggedFile.
  */
public class EditedTaggedFile {

	// PRIVATE INSTANCE Fields
	private TaggedFile editedFile;
	private TreeSet<FileTagging> addedTaggings;
	private TreeSet<FileTagging> removedTaggings;
	private TreeSet<FileTagging> updatedTaggings;


	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a default EditedTaggedFile with default TaggedFile, and
	  empty sets of added, removed, and updated taggings.
	  */
	public EditedTaggedFile() {
		editedFile = new TaggedFile();
		addedTaggings = new TreeSet<FileTagging>();
		removedTaggings = new TreeSet<FileTagging>();
		updatedTaggings = new TreeSet<FileTagging>();
	}

	/** Constructs an EditedTaggedFile which wraps the specified TaggedFile
	  instance.
	  @param editedFile_ the file for this instance to wrap
	  */
	public EditedTaggedFile(TaggedFile editedFile_)
	{
		this(
			editedFile_, 
			new TreeSet<FileTagging>(), 
			new TreeSet<FileTagging>(),
			new TreeSet<FileTagging>()
			);
	}

	/** Constructs an EditedTaggedFile which wraps the specified TaggedFile,
	  and has the specified sets of added, removed, and updated taggings. It
	  is up to the client object to ensure that the sets of taggings are
	  consistent with each other and with the TaggedFile.
	  @param editedFile_ the file to wrap
	  @param added taggings which are to be added to the file
	  @param removed taggings which are to be removed from the file
	  @param updated current taggings of the file which are to be updated
	  */
	public EditedTaggedFile(TaggedFile editedFile_,
							TreeSet<FileTagging> added,
							TreeSet<FileTagging> removed,
							TreeSet<FileTagging> updated)
	{
		setEditedFile(editedFile_);
		setAddedTaggings(added);
		setRemovedTaggings(removed);
	}

	/** Provides access to the wrapped TaggedFile.
	  @return the file wrapped by this instance
	  */
	public TaggedFile getEditedFile()
	{
		return editedFile;
	}

	/** Provides access to the taggings currently saved for the wrapped file.
	  @return the wrapped file's currently saved taggings
	  */
	public TreeSet<FileTagging> getSavedTaggings()
	{
		return editedFile.getFileTaggings();
	}

	/** Provides access to the set of taggings added to the file.
	  @return a reference to the file's added taggings
	  */
	public TreeSet<FileTagging> getAddedTaggings()
	{
		return addedTaggings;
	}

	/** Provides access to the set of taggings to be removed from the file.
	  @return a reference to the set of taggings to be removed
	  */
	public TreeSet<FileTagging> getRemovedTaggings()
	{
		return removedTaggings;
	}
	
	/** Provides access to the taggings to be updated for this file.
	  @return a reference to the taggings to be updated
	  */
	public TreeSet<FileTagging> getUpdatedTaggings()
	{
		return updatedTaggings;
	}

	/** Returns the *working* set of taggings associated with a file. The
	  *working* set includes:
	  - All taggings currently associated with the file in the repository,
	  minus the ones that the user has marked for removal
	  - All taggings the user has added to the file during the editing session
	  @return the edited file's working set of taggings
	  */
	public TreeSet<FileTagging> getWorkingTaggings()
	{
		TreeSet<FileTagging> current = new TreeSet<FileTagging>();

		/* Get the taggings that are currently saved, add them to the
		   current set, and remove all of those that have been removed
		   since editing began. */
		TreeSet<FileTagging> saved = editedFile.getFileTaggings();

		if (saved != null && !saved.isEmpty()) {
			current.addAll(saved);

			if (removedTaggings != null && !removedTaggings.isEmpty()) {
				for (FileTagging rem : removedTaggings) {
					current.remove(rem);
				}
			}
		}

		/* Add the taggings that have been added since editing. */
		if (addedTaggings != null && !addedTaggings.isEmpty()) {
			current.addAll(addedTaggings);
		}

		return current;
	}

	/** Tries to make a FileTagging a member of the set of taggings to be
	  added to the file. This fails if the specified FileTagging is already
	  in the repository, or already in the added set.
	  @param addMe the FileTagging to be added
	  @return true if the argument if was a valid tagging to be added
	  */
	public boolean assignAdded() 
	{
		// Check if already saved - exit if true
		// Check if already added - exit if true
		// Add it to added
		// Remove it from removed if it exists
		return false;
	}

	/** Assigns a FileTagging to the set of taggings to be removed from the 
	  file. This method will remove the argument from the added and updated sets
	  if it is present in either of them.
	  @param removeMe the FileTagging to be removed 
	  @return true if the argument was not already in the removed set
	  */
	public boolean assignRemoved()
	{
		// Check if already in removed - exit if true
		// Check if in added
			// if true, remove from added
		// Else check if in saved
			// if true, add to removed
				// if in updated, remove it
		return false;
	}

	/** Tries to assign a FileTagging to the set of taggings to be updated.
	  If the tagging has already been added to the set for updates, it replaces
	  the tagging currently there. If the tagging has been assigned to the
	  set for removal, it is removed from that set and added to the set for
	  updating. If the tagging is in the set for addition, it replaces the 
	  current value in that set. If the tagging is in neither of the sets of saved 
	  or added taggings, it cannot be updated.
	  @param updateMe the FileTagging to be updated
	  @return true if the the tagging has been assigned for updating
	  */
	public boolean assignUpdated()
	{
		// If tagging is in saved taggings
			// If needed, remove it from removed
	   		// If in updated, replace it, else add it
			// return true	
		// Else if tagging is in added taggings
			// remove it and insert the argument tagging
			// return true

		return false;
	}

	/** Sets the wrapped TaggedFile.
	  @param editedFile_ the file to wrap for editing meta-information
	  */
	public void setEditedFile(TaggedFile editedFile_)
	{
		editedFile = editedFile_;
	}

	/** Sets the set of taggings to be added.
	  @param added the set of FileTaggings to be added
	  */
	public void setAddedTaggings(TreeSet<FileTagging> added)
	{
		addedTaggings = added;
	}

	/** Sets the set of taggings to be removed.
	  @param added the set of FileTaggings to be removed
	  */
	public void setRemovedTaggings(TreeSet<FileTagging> removed)
	{
		removedTaggings = removed;
	}
	
	/** Sets the set of taggings to be updated.
	  @param added the set of FileTaggings to be updated
	  */
	public void setUpdatedTaggings(TreeSet<FileTagging> updated)
	{
		updatedTaggings = updated;
	}

}
