package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jfmi.control.TaggedFile;
import jfmi.database.TaggingRecord;

public class TagListCellPanel extends JPanel {

	private TaggedFile taggedFile;

	private JLabel fileNameLabel;
	private JLabel filePathLabel;
	private Box fileBox;

	private JLabel tagLabel;

	private Box contentBox;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Ctor: TaggedFile.
	  */
	public TagListCellPanel(TaggedFile taggedFile_)
	{
		// Initialize instance
		setLayout(new BorderLayout(10, 12));
		setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
		setBackground(Color.DARK_GRAY);
		setTaggedFile(taggedFile_);

		// Initialize file display area
		initFileBox();

		// Initialize tag display
		initTagLabel();

		// Add content
		contentBox = new Box(BoxLayout.Y_AXIS);
		contentBox.add(fileBox);
		contentBox.add(Box.createVerticalStrut(10));
		contentBox.add(tagLabel);
		contentBox.add(Box.createVerticalStrut(10));

		add(contentBox, BorderLayout.CENTER);
	}

	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Initialize the file information labels and the containing Box.
	  */
	private final void initFileBox()
	{
		// Initialize labels
		String fname = "";
		String fpath = "";

		if (taggedFile != null) {
			fname = taggedFile.getFileName();
			fpath = taggedFile.getFilePath();
		}
	
		fileNameLabel = new JLabel(fname);
		Styles.setHeaderStyles(fileNameLabel, 3);

		filePathLabel = new JLabel(fpath);
		Styles.setHeaderStyles(filePathLabel, 4);

		// Add the labels to the fileBox field
		fileBox = new Box(BoxLayout.Y_AXIS);
		fileBox.add(fileNameLabel);
		fileBox.add(filePathLabel);
	}

	/** Initialize the list of tags (in a JLabel).
	  */
	private final void initTagLabel()
	{
		tagLabel = new JLabel(taggedFile.getTagsAsString());
		tagLabel.setFont(Styles.SS_PLAIN_12);
		tagLabel.setForeground(Color.ORANGE);
	}

	/** Mutator of taggedFile field.
	  */
	private final void setTaggedFile(TaggedFile taggedFile_)
	{
		taggedFile = taggedFile_;
	}


}

