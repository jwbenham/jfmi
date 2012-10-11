package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jfmi.control.FileTag;
import jfmi.control.FileTagHandler;
import jfmi.control.FileTagging;
import jfmi.control.TaggedFile;
import jfmi.control.TaggedFileHandler;

/** A TaggedFileViewPanel displays all information contained in a TaggedFile
  to the user, and allows the user to communicate changes to a
  TaggedFileHandler.
  */
public class TaggedFileViewDialog extends JDialog 
	implements ActionListener, ListSelectionListener {

	// PRIVATE INSTANCE Fields
	private JLabel fileNameLabel;
	private JLabel filePathLabel;

	private JButton changePathButton;
	private JButton addTagButton;
	private JButton removeTagButton;
	private JButton saveFileButton;

	private JList<FileTag> tagJList;
	private JList<FileTagging> taggingJList;
	private JTextArea commentArea;

	private Box fileInfoBox;
	private Box fileTaggingBox;
	private Box saveFileBox;

	private FileTagHandler tagHandler;
	private TaggedFileHandler fileHandler;
	private TaggedFile displayedFile;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a TaggedFileViewPanel.
	  @param parent a JFrame to server as this dialog's owner
	  @param fileHandler_ file handler to associate with this instance
	  @param tagHandler_ tag handler to associate with this instance
	  @throws IllegalArgumentException if fileHandler_ is null
	  */
	public TaggedFileViewDialog(
		JFrame parent, 
		TaggedFileHandler fileHandler_,
		FileTagHandler tagHandler_
	)
	{
		super(parent, "File Viewer", true);

		// Initialize fields and child components
		init(fileHandler_, tagHandler_);
		initFileInfoBox();
		initFileTaggingBox();
		initSaveFileBox();

		// Add child components
		add(fileInfoBox, BorderLayout.NORTH);
		add(fileTaggingBox, BorderLayout.CENTER);
		add(saveFileBox, BorderLayout.SOUTH);

		// Not visible initially	
		setVisible(false);
	}

	/** Sets the TaggedFile whose information this instance is displaying.
	  @param file the TaggedFile to display
	  @throws IllegalArgumentException if file is null
	  */
	public void setDisplayedFile(TaggedFile file)
	{
		if (file == null) {
			throw new IllegalArgumentException("file cannot be null");
		}

		displayedFile = file;
	}

	/** Sets this object's associated TaggedFileHandler.
	  @param fileHandler_ the file handler to associate with this instance
	  @throws IllegalArgumentException if fileHandler_ is null
	  */
	public void setFileHandler(TaggedFileHandler fileHandler_)
	{
		if (fileHandler_ == null) {
			throw new IllegalArgumentException("fileHandler_ cannot be null");
		}

		fileHandler = fileHandler_;
	}

	/** Sets this object's associated FileTagHandler.
	  @param tagHandler_ the tag handler to associate with this instance
	  @throws IllegalArgumentException if tagHandler_ is null
	  */
	public void setTagHandler(FileTagHandler tagHandler_)
	{
		if (tagHandler_ == null) {
			throw new IllegalArgumentException("tagHandler_ cannot be null");
		}

		tagHandler = tagHandler_;
	}

	/** Updates the information displayed by all of this instance's components.
	  */
	public void updateDisplay()
	{
		updateFileInfo();
		updateTagJList();
		updateTaggingJList();
		updateCommentArea();
	}

	/** Tells this instance to display the specified file, and updates the
	  information contained in all of the instance's components accordingly.
	  @param file the file to display
	  @throws IllegalArgumentException if file is null
	  */
	public void updateDisplayedFile(TaggedFile file)
	{
		setDisplayedFile(file);
		updateDisplay();
	}

	/** Updates the displayed file name and path with information from the 
	  instance's FileTag.
	  */
	public void updateFileInfo()
	{
		fileNameLabel.setText(displayedFile.getFileName());
		filePathLabel.setText(displayedFile.getFilePath());
	}

	/** Updates the displayed list of tags.
	  */
	public void updateTagJList()
	{
		if (tagHandler.readFileTagDataFromRepo(true)) {

			FileTag[] tags = tagHandler.getFileTagDataAsArray();
			if (tags != null) {
				tagJList.setListData(tags);
			}
		}
	}

	/** Updates the displayed file's tags with information from the instance's 
	  FileTag.
	  */
	public void updateTaggingJList()
	{
		FileTagging[] taggings = displayedFile.getFileTaggingsAsArray();

		if (taggings != null) {
			taggingJList.setListData(taggings);
			taggingJList.setSelectedIndex(0);
		}
	}

	/** Updates the displayed file's selected tag's comment with information 
	  from the instance's FileTag.
	  */
	public void updateCommentArea()
	{
		FileTagging selectedTagging = taggingJList.getSelectedValue();

		if (selectedTagging == null) {
			return;
		}

		String comment = selectedTagging.getComment();
		if (comment == null || comment.equals("")) {
			commentArea.setText("<No comment>");
		} else {
			commentArea.setText(comment);
		}
	}


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Initializes a TaggedFileViewPanel instance.
	  @param fileHandler_ file handler to associate with this instance
	  @throws IllegalArgumentException if fileHandler_ is null
	  */
	private final void init(TaggedFileHandler fileHandler_,
							FileTagHandler tagHandler_ )
	{
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		setFileHandler(fileHandler_);	
		setTagHandler(tagHandler_);
		Styles.setAllSizes(this, new Dimension(600, 400));
	}

	/** Initializes the file information box, which displays the file path
	  information.
	  */
	private final void initFileInfoBox()
	{
		// Set up left part of fileInfoBox
		fileNameLabel = new JLabel("No file loaded");

		filePathLabel = new JLabel("");

		Box leftBox = Box.createVerticalBox();
		leftBox.setBorder(new EmptyBorder(2, 2, 2, 2));
		leftBox.add(fileNameLabel);
		leftBox.add(Box.createVerticalStrut(5));
		leftBox.add(filePathLabel);

		// Set up right part of fileInfoBox
		changePathButton = new JButton("Change Path");
		changePathButton.addActionListener(this);

		Box rightBox = Box.createVerticalBox();
		rightBox.setBorder(new EmptyBorder(2, 2, 2, 2));
		rightBox.add(changePathButton);

		// Set up fileInfoBox
		fileInfoBox = Box.createHorizontalBox();
		fileInfoBox.setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		fileInfoBox.add(leftBox);
		fileInfoBox.add(rightBox);
	}

	/** Initializes the file tagging box, which displays information on the
	  file's tags and comments.
	  */
	private final void initFileTaggingBox()
	{
		// Set up left part of box
		JLabel fileTagLabel = new JLabel("File Tags");
		fileTagLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		tagJList = new JList<FileTag>();
		tagJList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane allTagScroller = new JScrollPane(tagJList);
		allTagScroller.setAlignmentX(Component.LEFT_ALIGNMENT);

		taggingJList = new JList<FileTagging>();		
		taggingJList.addListSelectionListener(this);
		taggingJList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane fileTagScroller = new JScrollPane(taggingJList);
		fileTagScroller.setAlignmentX(Component.LEFT_ALIGNMENT);

		Box listBox = Box.createHorizontalBox();
		listBox.add(allTagScroller);
		listBox.add(fileTagScroller);

		addTagButton = new JButton("Add Tag");
		addTagButton.addActionListener(this);

		removeTagButton = new JButton("Remove Tag");
		removeTagButton.addActionListener(this);
		removeTagButton.setForeground(Styles.DANGER_COLOR);
		
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonBox.setMaximumSize(new Dimension(300, addTagButton.getHeight()));
		buttonBox.add(addTagButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(removeTagButton);

		Box leftBox = Box.createVerticalBox();
		leftBox.setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		leftBox.setMaximumSize(new Dimension(600, 800));
		leftBox.add(fileTagLabel);
		leftBox.add(listBox);
		leftBox.add(buttonBox);

		// Set up right part of box
		commentArea = new JTextArea();
		JScrollPane tagCommentScroller = new JScrollPane(commentArea);

		Box rightBox = Box.createVerticalBox();
		rightBox.setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
		rightBox.add(new JLabel("Tag Comments"));
		rightBox.add(tagCommentScroller);

		// Set up fileTaggingBox
		fileTaggingBox = Box.createHorizontalBox();
		fileTaggingBox.add(leftBox);
		fileTaggingBox.add(Box.createHorizontalStrut(30));
		fileTaggingBox.add(rightBox);
	}

	/** Initializes the box containing the save button.
	  */
	private final void initSaveFileBox()
	{
		saveFileButton = new JButton("Save File");
		saveFileButton.addActionListener(this);

		saveFileBox = Box.createHorizontalBox();
		saveFileBox.setBorder(new EmptyBorder(10, 10, 10, 10));
		saveFileBox.add(saveFileButton);
	}


	//************************************************************
	// IMPLEMENTATION ActionListener 
	//************************************************************

	/** Determines what action is taken when the user generates an ActionEvent
	  on one of the components in the TaggedFileViewPanel.
	  @param e a user-generated ActionEvent
	  */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == changePathButton) {
			fileHandler.beginUpdateFilePathInteraction(displayedFile);
		} else if (source == addTagButton) {

		} else if (source == removeTagButton) {

		} else if (source == saveFileButton) {
			fileHandler.beginSaveFileInteraction(displayedFile);
		}

	}


	//************************************************************
	// IMPLEMENTATION ListSelectionListener 
	//************************************************************

	/** Determines what happens when the user generates a ListSelectionEvent
	  on the dialog's list of file tags.
	  @param e a user-generated ListSelectionEvent
	  */
	public void valueChanged(ListSelectionEvent e)
	{
		Object source = e.getSource();

		if (source == taggingJList) {
			updateCommentArea();
		}	
	}


}
