package jfmi.gui;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import jfmi.app.FileTag;
import jfmi.control.FileTagHandler;

/** Implements a graphical user interface for an instance of the FileTagHandler
  controller class.
  */
public class FileTagHandlerDialog extends JDialog implements ActionListener {
	// CLASS fields
	private static final String EPREFIX = "FileTagHandlerDialog";

	// INSTANCE fields
	private Box thisLayout;

	private JButton addTagButton;
	private JButton deleteTagsButton;
	private JButton editTagButton;
	private Box buttonBox;

	private JList<FileTag> tagJList;
	private JScrollPane tagScroller;
	private Box listBox;

	private FileTagHandler tagHandler;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a FileTagHandlerDialog using the specified JFrame as its
	  parent frame and FileTagHandler as the controller this instance acts for
	  as a GUI.
	  @param parent This component's parent frame.
	  @param tagHandler_ This component's corresponding controller.
	  */
	public FileTagHandlerDialog(JFrame parent, FileTagHandler tagHandler_)
	{
		// Initialize instance
		super(parent, "Manage Tags", true);
		Styles.setAllSizes(this, new Dimension(400, 200));
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// Initialize handlers
		setTagHandler(tagHandler_);

		// Initialize child components
		initButtonBox();
		initListBox();

		// Add components
		add(buttonBox, BorderLayout.WEST);
		add(listBox, BorderLayout.CENTER);
		
		setVisible(false);
	}

	/** Shows a confirmation dialog to the user and returns their choice.
	  @param msg Confirmation message to be displayed
	  @return true if the user confirmed
	  */
	public boolean getUserConfirmation(String msg)
	{
		int choice = JOptionPane.showConfirmDialog(this, msg, "Confirm Action",
												JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/** Sets the list of tags displayed by this dialog. If the data parameter
	  is null, the list of tags is set to be empty.
	  @param data Vector of FilTag objects
	  */
	public void setTagJListData(Vector<FileTag> data)
	{
		if (data == null) {
			tagJList.setListData(new FileTag[0]);
		} else {
			tagJList.setListData(data);
		}
	}

	/** Displays a dialog which allows the user to enter the value for a new
	  file tag.
	  @return the value entered by the user, null if user cancelled
	  */
	public String showAddTagDialog()
	{
		return showAddTagDialog("");
	}

	/** Displays a dialog which allows the user to enter the value for a new
	  file tag.
	  @param msg an additional message to be displayed to the user
	  @return the value entered by the user, null if user cancelled
	  */
	public String showAddTagDialog(String msg)
	{
		return JOptionPane.showInputDialog(this, 
											"What tag value should be added?"
											+ "\n" + msg,
											"Add Tag", 
											JOptionPane.QUESTION_MESSAGE);
	}

	/** Displays a dialog which allows the user to enter a new value for
	  a file tag.
	  @param tag the current value of the file tag in question
	  @return the value entered by the user, null if user cancelled
	  */
	public String showEditTagDialog(String tag)
	{
		return showEditTagDialog(tag, "");
	}

	/** Displays a dialog which allows the user to enter a new value for
	  a file tag.
	  @param tag the current value of the file tag in question
	  @param msg an additional message to be displayed to the user
	  @return the value entered by the user, null if user cancelled
	  */
	public String showEditTagDialog(String tag, String msg)
	{
		return JOptionPane.showInputDialog(
					this, 
					"What should the tag \"" + tag  + "\" be changed to?\n" 
					+ msg,
					"Add Tag", 
					JOptionPane.QUESTION_MESSAGE
				);
	}


	//************************************************************
	// PRIVATE INSTANCE Methods
	//************************************************************

	/** Initialize JButton components and their container.
	  */
	private final void initButtonBox()
	{
		// Initialize buttons
		addTagButton = new JButton("Add Tag");
		addTagButton.addActionListener(this);
		addTagButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setComponentStyles(addTagButton, Styles.DEFAULT_BUTTON_FONT,
									null, null);

		deleteTagsButton = new JButton("Delete Tags");
		deleteTagsButton.addActionListener(this);
		deleteTagsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setComponentStyles(deleteTagsButton, Styles.DEFAULT_BUTTON_FONT,
									Styles.DANGER_COLOR, null);

		editTagButton = new JButton("Edit Tag");
		editTagButton.addActionListener(this);
		editTagButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		Styles.setComponentStyles(editTagButton, Styles.DEFAULT_BUTTON_FONT,
									null, null);

		// Initialize the container
		buttonBox = Box.createVerticalBox();
		buttonBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonBox.add(editTagButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(addTagButton);
		buttonBox.add(Box.createVerticalStrut(5));
		buttonBox.add(deleteTagsButton);
	}

	/** Initialize the list of tags, contained in a scroll pane.
	  @return false if a problem occurred initializing the list of tags
	  */
	private final void initListBox()
	{
		tagJList = new JList<FileTag>();
		tagJList.setLayoutOrientation(JList.VERTICAL);

		// Initialize the scrollpane
		tagScroller = new JScrollPane(tagJList);

		// Initialize the box
		listBox = new Box(BoxLayout.X_AXIS);
		listBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		listBox.add(Box.createHorizontalStrut(10));
		listBox.add(tagScroller);
		listBox.add(Box.createHorizontalStrut(10));
	}

	/** Mutator for the tagHandler field.
	  @param tagHandler_ FileTagHandler to set as this instance's controller.
	  		Throws an IllegalArgumentException if null.
	  */
	private void setTagHandler(FileTagHandler tagHandler_)
	{
		if (tagHandler_ == null) {
			throw new IllegalArgumentException("error: tagHandler_ is null");
		}

		tagHandler = tagHandler_;
	}


	
	//************************************************************
	// IMPLEMENTATION ActionListener
	//************************************************************

	/** Handles action events dispatched by user activity on this object.
	  @param e An action event generated on this object.
	  */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == addTagButton) {
			tagHandler.beginAddTag();

		} else if (source == deleteTagsButton) {
			tagHandler.beginDeleteTags(
				tagJList.getSelectedValuesList()
			);

		} else if (source == editTagButton) {
			tagHandler.beginEditTag(tagJList.getSelectedValue());

		}
	}

}
