package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import jfmi.app.TaggedFile;
import jfmi.app.TaggedFileSorters;
import jfmi.control.JFMIApp;
import jfmi.control.TaggedFileHandler;
import jfmi.control.FileTagHandler;
import jfmi.control.FileTaggingHandler;


/** A JFMIFrame acts as the parent Swing Component for the JFMI application's
  GUI. All other graphical components treat a JFMIFrame as their parent.
  */
public class JFMIFrame extends JFrame implements ActionListener {

	// PRIVATE Class Fields
	private static final String FRAME_TITLE = "JFMI";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);

	// PRIVATE Instance Fields
	private JPanel contentPanel;

	private SortOptionsDialog sortDialog;
	private SortOptionsBox fileSortBox;
	private SortedSet<String> sortFields;
	private SortedSet<String> sortOrders;

	private Box buttonBox;
	private JButton manageTagsButton;
	private JButton addFileButton;
	private JButton editFileButton;
	private JButton showFileButton;
	private JButton deleteFilesButton;

	private JButton sortButton;

	private JScrollPane taggedFileScroller;	
	private JList<TaggedFile> taggedFileJList;
	private MutableListModel<TaggedFile> listModel;

	private JFMIApp jfmiApp;


	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a JFMIFrame with the specified JFMIApp as its controller.
	  @param jfmiApp_ JFMIApp that will act as this instance's controller.
	  */
	public JFMIFrame(JFMIApp jfmiApp_)
	{
		// Initialize instance
		super(FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_DIMENSION);
		setJFMIApp(jfmiApp_);

		// Initialize sorting components
		initSortDialog();
	
		// Initialize child components
		initContentPanel();
		setContentPane(contentPanel);
		initButtonBox();
		initTagScroller();

		add(buttonBox, BorderLayout.WEST);
		add(taggedFileScroller, BorderLayout.CENTER);

		// Do not display initially
		setVisible(false);
	}

	/** Prompts a user to confirm an action with the specified message, and 
	  returns the user's decision.
	  @param confirmMsg the message to display to the user
	  @return true if the user confirmed
	  */
	public boolean getConfirmation(String confirmMsg)
	{
		int choice = JOptionPane.showConfirmDialog(this, confirmMsg,
											"Please Provide Confirmation",
											JOptionPane.OK_CANCEL_OPTION);

		return choice == JOptionPane.OK_OPTION;
	}

	/** Sets the list data of the taggedFileJList list. Passing a null
	  reference to the method causes an exception to be thrown.
	  @param vector Non-null Vector<TaggedFile> to be used as the list's data
	  @throws IllegalArgumentException if vector is null
	  */
	public void setTaggedFileJListData(Vector<TaggedFile> vector)
	{
		if (vector == null) {
			throw new IllegalArgumentException("vector cannot be null");
		}

		// Set up the model and apply it to the list
		listModel = new MutableListModel<TaggedFile>(vector);
		taggedFileJList.setModel(listModel);	

		// Sort the list
		sortTaggedFileJList(new TaggedFileSorters.FileNameSorter());
	}

	/** Sorts the displayed list of TaggedFiles using the specified Comparator.
	  @param c the Comparator to sort the files with
	 */ 
	public void sortTaggedFileJList(Comparator<TaggedFile> c)
	{
		listModel.sort(c);
	}


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Handles sorting logic when the user confirms the sort option dialog. */
	private void handleSortConfirm()
	{
		Comparator<TaggedFile> c = null;

		String field = sortDialog.getSelectedField();
		String order = sortDialog.getSelectedOrder();

		if (sortDialog.fieldChanged()) {
			if (field.equals("Name")) {
				c = new TaggedFileSorters.FileNameSorter();
			} else {
				c = new TaggedFileSorters.FilePathSorter();
			}

			if (order.equals("Descending (Z-A)")) {
				c = Collections.reverseOrder(c);
			}

			sortTaggedFileJList(c);
		}

		if (c == null && sortDialog.orderChanged()) {	
			listModel.reverse();	
		}
	}

	/** Initializes the contentPanel field.
	  */
	private final void initContentPanel()
	{
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
	}

	/** Initialize the buttonBox field.
	  */
	private final void initButtonBox()
	{
		// Initialize buttons
		JLabel tagsLabel = new JLabel("Tag Options");
		tagsLabel.setForeground(Color.DARK_GRAY);
		tagsLabel.setFont(Styles.SS_PLAIN_16);
		tagsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		manageTagsButton = new JButton("Manage Tags..");
		manageTagsButton.addActionListener(this);
		manageTagsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(manageTagsButton);

		JLabel filesLabel = new JLabel("File Options");
		filesLabel.setForeground(Color.DARK_GRAY);
		filesLabel.setFont(Styles.SS_PLAIN_16);
		filesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		addFileButton = new JButton("Add New File");
		addFileButton.addActionListener(this);
		addFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(addFileButton);

		editFileButton = new JButton("Edit File");
		editFileButton.addActionListener(this);
		editFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(editFileButton);

		showFileButton = new JButton("Show In Directory");
		showFileButton.addActionListener(this);
		showFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(showFileButton);

		deleteFilesButton = new JButton("Delete Selected Files");
		deleteFilesButton.addActionListener(this);
		deleteFilesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(deleteFilesButton);
		deleteFilesButton.setForeground(Styles.DANGER_COLOR);

		JLabel sortLabel = new JLabel("Sort Options");
		sortLabel.setForeground(Color.DARK_GRAY);
		sortLabel.setFont(Styles.SS_PLAIN_16);
		sortLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		sortButton = new JButton("Sorting Options");
		sortButton.addActionListener(this);
		sortButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(sortButton);

		// Initialize buttonBox
		buttonBox = new Box(BoxLayout.Y_AXIS);
		buttonBox.setOpaque(true);

		buttonBox.add(Box.createVerticalStrut(10));
		buttonBox.add(tagsLabel);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(manageTagsButton);
		buttonBox.add(Box.createVerticalStrut(50));

		buttonBox.add(filesLabel);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(addFileButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(editFileButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(showFileButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(deleteFilesButton);
		buttonBox.add(Box.createVerticalStrut(50));

		buttonBox.add(sortLabel);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(sortButton);
	}

	/** Initializes the sorting dialog and its contained sorting box.
	  */
	private final void initSortDialog()
	{
		// Initialize the option sets
		sortFields = new TreeSet<String>();
		sortFields.add("Name");
		sortFields.add("Path");
		
		sortOrders = new TreeSet<String>();
		sortOrders.add("Ascending (A-Z)");
		sortOrders.add("Descending (Z-A)");

		// Initialize sorting box
		fileSortBox = new SortOptionsBox(sortFields, sortOrders);

		// Initialize sorting dialog
		sortDialog = new SortOptionsDialog(this, this, fileSortBox);
		sortDialog.setVisible(false);
	}

	/** Initialize the scrollPane field.
	  */
	private final void initTagScroller()
	{
		// Instantiate the taggedFileJList
		listModel = new MutableListModel<TaggedFile>();
		taggedFileJList = new JList<TaggedFile>(listModel);
		taggedFileJList.setLayoutOrientation(JList.VERTICAL);
		taggedFileJList.setCellRenderer(new TaggedFileJListRenderer());

		// Initialize the scroll pane
		taggedFileScroller = new JScrollPane(taggedFileJList);
	}

	/** Mutator for the jfmiApp field. 
	  @param jfmiApp_ JFMIApp instance to set as this instance's controller.
	  */
	private final void setJFMIApp(JFMIApp jfmiApp_)
	{
		if (jfmiApp_ == null) {
			throw new IllegalArgumentException("jfmiApp field cannot be null");
		}

		jfmiApp = jfmiApp_;
	}


	//************************************************************
	// IMPLEMENTATION ActionListener
	//************************************************************

	/** Responds to various ActionEvent events.
	  @param e An ActionEvent generated by the user on some child component of
	  		JFMIFrame.
	  */
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();

		if (src == manageTagsButton) {
			jfmiApp.getTagHandler().beginManageTags();

		} else if (src == addFileButton) {
			jfmiApp.getFileHandler().beginAddFile();

		} else if (src == editFileButton) {
			TaggedFile selectedFile = taggedFileJList.getSelectedValue();
			
			if (selectedFile != null) {
				jfmiApp.getFileHandler().beginEditFile(selectedFile);
			} else {
				GUIUtil.showAlert("No file selected.");
			}

		} else if (src == showFileButton) {
			TaggedFile selectedFile = taggedFileJList.getSelectedValue();
			
			if (selectedFile != null) {
				jfmiApp.getFileHandler().beginShowInDirectory(selectedFile);
			} else {
				GUIUtil.showAlert("No file selected.");
			}

		} else if (src == deleteFilesButton) {
			List<TaggedFile> files = taggedFileJList.getSelectedValuesList();
			jfmiApp.getFileHandler().beginDeleteFiles(files);

		} else if (src == sortButton) {
			sortDialog.setVisible(true);

		} else if (src == sortDialog.getConfirmButton()) {
			sortDialog.setVisible(false);
			handleSortConfirm();
		}
	}

}
