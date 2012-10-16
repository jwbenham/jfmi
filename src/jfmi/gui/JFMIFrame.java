package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
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
import java.util.List;
import java.util.Vector;

import jfmi.app.TaggedFile;
import jfmi.control.JFMIApp;
import jfmi.control.TaggedFileHandler;
import jfmi.control.FileTagHandler;
import jfmi.control.FileTaggingHandler;


/** A JFMIFrame acts as the parent Swing Component for the JFMI application's
  GUI. All other graphical components treat a JFMIFrame as their parent.
  */
public class JFMIFrame extends JFrame implements ActionListener {
	private static final String FRAME_TITLE = "JFMI";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);

	private JPanel contentPanel;

	// Button related
	private Box buttonBox;	// Holds buttons
	private JButton manageTagsButton;
	private JButton addFileButton;
	private JButton editFileButton;
	private JButton viewFileButton;
	private JButton deleteFilesButton;

	// List related
	private JScrollPane taggedFileScroller;	// Holds the taggedFileJList
	private JList<TaggedFile> taggedFileJList;

	// Controller related
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

		taggedFileJList.setListData(vector);	
	}


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

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
		JLabel tagsLabel = new JLabel("File Tags");
		tagsLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		tagsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		manageTagsButton = new JButton("Manage Tags..");
		manageTagsButton.addActionListener(this);
		manageTagsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(manageTagsButton);

		JLabel filesLabel = new JLabel("Tagged Files");
		filesLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		filesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		addFileButton = new JButton("Add New File");
		addFileButton.addActionListener(this);
		addFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(addFileButton);

		editFileButton = new JButton("Edit File");
		editFileButton.addActionListener(this);
		editFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(editFileButton);

		viewFileButton = new JButton("View File");
		viewFileButton.addActionListener(this);
		viewFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(viewFileButton);

		deleteFilesButton = new JButton("Delete Selected Files");
		deleteFilesButton.addActionListener(this);
		deleteFilesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setDefaultJButtonStyles(deleteFilesButton);
		deleteFilesButton.setForeground(Styles.DANGER_COLOR);

		// Initialize buttonBox
		buttonBox = new Box(BoxLayout.Y_AXIS);
		buttonBox.add(Box.createVerticalStrut(10));
		buttonBox.add(tagsLabel);
		buttonBox.add(Box.createVerticalStrut(10));
		buttonBox.add(manageTagsButton);
		buttonBox.add(Box.createVerticalStrut(50));

		buttonBox.add(filesLabel);
		buttonBox.add(Box.createVerticalStrut(10));
		buttonBox.add(addFileButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(editFileButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(viewFileButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(deleteFilesButton);
		buttonBox.add(Box.createVerticalStrut(50));
	}

	/** Initialize the scrollPane field.
	  */
	private final void initTagScroller()
	{
		// Instantiate the taggedFileJList
		taggedFileJList = new JList<TaggedFile>();
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

		} else if (src == viewFileButton) {

		} else if (src == deleteFilesButton) {

			List<TaggedFile> files = taggedFileJList.getSelectedValuesList();
			jfmiApp.getFileHandler().beginDeleteFiles(files);

		}
	}

}
