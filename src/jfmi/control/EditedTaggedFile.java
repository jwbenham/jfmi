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

	public EditedTaggedFile() {
		editedFile = new TaggedFile();
		addedTaggings = new TreeSet<FileTagging>();
		removedTaggings = new TreeSet<FileTagging>();
		updatedTaggings = new TreeSet<FileTagging>();
	}

	public EditedTaggedFile(TaggedFile editedFile_)
	{
		this(
			editedFile_, 
			new TreeSet<FileTagging>(), 
			new TreeSet<FileTagging>(),
			new TreeSet<FileTagging>()
			);
	}

	public EditedTaggedFile(TaggedFile editedFile_,
							TreeSet<FileTagging> added,
							TreeSet<FileTagging> removed,
							TreeSet<FileTagging> updated)
	{
		setEditedFile(editedFile_);
		setAddedTaggings(added);
		setRemovedTaggings(removed);
	}

	

	public TaggedFile getEditedFile()
	{
		return editedFile;
	}

	public TreeSet<FileTagging> getSavedTaggings()
	{
		return editedFile.getFileTaggings();
	}

	public TreeSet<FileTagging> getAddedTaggings()
	{
		return addedTaggings;
	}

	public TreeSet<FileTagging> getRemovedTaggings()
	{
		return removedTaggings;
	}
	
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

	public void setEditedFile(TaggedFile editedFile_)
	{
		editedFile = editedFile_;
	}

	public void setAddedTaggings(TreeSet<FileTagging> added)
	{
		addedTaggings = added;
	}

	public void setRemovedTaggings(TreeSet<FileTagging> removed)
	{
		removedTaggings = removed;
	}
	
	public void setUpdatedTaggings(TreeSet<FileTagging> updated)
	{
		updatedTaggings = updated;
	}

}
