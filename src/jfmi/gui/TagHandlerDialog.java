package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.sql.SQLException;
import java.util.List;

import jfmi.control.TagHandler;
import jfmi.database.TagRecord;

/** Implements a graphical user interface for an instance of the TagHandler
  controller class.
  */
public class TagHandlerDialog extends JDialog implements ActionListener {
	// CLASS fields
	private static final String EPREFIX = "TagHandlerDialog";

	// INSTANCE fields
	private Box thisLayout;

	private JButton addTagButton;
	private JButton deleteTagsButton;
	private JButton editTagButton;
	private Box buttonBox;

	private JList tagList;
	private JScrollPane tagScroller;
	private Box listBox;

	private TagHandler tagHandler;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Constructs a TagHandlerDialog using the specified JFrame as its
	  parent frame and TagHandler as the controller this instance acts for
	  as a GUI.
	  @param parent This component's parent frame.
	  @param tagHandler_ This component's corresponding controller.
	  */
	public TagHandlerDialog(JFrame parent, TagHandler tagHandler_)
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
		thisLayout = new Box(BoxLayout.Y_AXIS);
		thisLayout.add(buttonBox);
		thisLayout.add(Box.createVerticalStrut(10));
		thisLayout.add(listBox);
		add(thisLayout, BorderLayout.CENTER);
		
		setVisible(false);
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
		Styles.setDefaultJButtonStyles(addTagButton);

		deleteTagsButton = new JButton("Delete Selected Tags");
		deleteTagsButton.addActionListener(this);
		Styles.setComponentStyles(deleteTagsButton, Styles.DEFAULT_BUTTON_FONT,
									Styles.DANGER_COLOR, null);

		editTagButton = new JButton("Edit Tag");
		editTagButton.addActionListener(this);
		Styles.setDefaultJButtonStyles(editTagButton);

		// Initialize the container
		buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(editTagButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(addTagButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(deleteTagsButton);
		buttonBox.add(Box.createHorizontalStrut(10));
	}

	/** Initialize the list of tags, contained in a scroll pane.
	  TODO: add a combo box for sorting the tags
	  @return false if a problem occurred initializing the list of tags
	  */
	private final void initListBox()
	{
		tagList = new JList();
		tagList.setLayoutOrientation(JList.VERTICAL);

		// Initialize the scrollpane
		tagScroller = new JScrollPane(tagList);

		// Initialize the box
		listBox = new Box(BoxLayout.X_AXIS);
		listBox.add(Box.createRigidArea(new Dimension(100, 100)));
		listBox.add(Box.createHorizontalStrut(10));
		listBox.add(tagScroller);
		listBox.add(Box.createHorizontalStrut(10));
	}

	/** Mutator for the tagHandler field.
	  @param tagHandler_ TagHandler to set as this instance's controller.
	  		Throws an IllegalArgumentException if null.
	  */
	private void setTagHandler(TagHandler tagHandler_)
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

	}

}
