package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jfmi.app.TaggedFile;

public class TaggedFileJListCellPanel extends JPanel {

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
	public TaggedFileJListCellPanel(TaggedFile taggedFile_)
	{
		// Initialize instance
		setLayout(new BorderLayout(10, 12));
		setBorder(new MatteBorder(5, 5, 5, 5, Color.DARK_GRAY));
		setBackground(Color.WHITE);
		setTaggedFile(taggedFile_);

		// Initialize file display area
		initFileBox();

		// Initialize tag display
		initTagLabel();

		// Add content
		Box paddingBox = Box.createVerticalBox();
		paddingBox.setBorder(new EmptyBorder(3, 3, 3, 3));
		paddingBox.add(fileBox);
		paddingBox.add(Box.createVerticalStrut(10));
		paddingBox.add(tagLabel);
		paddingBox.add(Box.createVerticalStrut(10));

		contentBox = new Box(BoxLayout.Y_AXIS);
		contentBox.add(paddingBox);

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
		tagLabel = new JLabel(taggedFile.getFileTagsAsString());
		tagLabel.setFont(Styles.SS_PLAIN_12);
		tagLabel.setForeground(Styles.VIOLET);
	}

	/** Mutator of taggedFile field.
	  */
	private final void setTaggedFile(TaggedFile taggedFile_)
	{
		taggedFile = taggedFile_;
	}
}

