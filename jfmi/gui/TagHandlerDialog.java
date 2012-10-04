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

public class TagHandlerDialog extends JDialog {
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
	private ActionHandler actionHandler;

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	/** Ctor: default.
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
		actionHandler = new ActionHandler();

		// Initialize buttons
		initButtonBox();

		// Initialize list of tags
		if (initListBox() == false) {
			throw new GUIException(EPREFIX + ".<init>: initListBox() failed.");
		}

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
		addTagButton.addActionListener(actionHandler);
		Styles.setDefaultJButtonStyles(addTagButton);

		deleteTagsButton = new JButton("Delete Selected Tags");
		deleteTagsButton.addActionListener(actionHandler);
		Styles.setComponentStyles(deleteTagsButton, Styles.DEFAULT_BUTTON_FONT,
									Styles.DANGER_COLOR, null);

		editTagButton = new JButton("Edit Tag");
		editTagButton.addActionListener(actionHandler);
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
	private final boolean initListBox()
	{
		// Initialize the list
		if (refreshTagRecords(true)) {
			List<TagRecord> tagRecordList = tagHandler.getTagRecordList();

			tagList = new JList(tagRecordList.toArray());
			tagList.setLayoutOrientation(JList.VERTICAL);

			// Initialize the scrollpane
			tagScroller = new JScrollPane(tagList);

			// Initialize the box
			listBox = new Box(BoxLayout.X_AXIS);
			listBox.add(Box.createRigidArea(new Dimension(100, 100)));
			listBox.add(Box.createHorizontalStrut(10));
			listBox.add(tagScroller);
			listBox.add(Box.createHorizontalStrut(10));

			return true;
		}

		return false;
	}

	/** Asks the TagHandler to refresh the list of tags from the database.
	  If this cannot be done, an error message is displayed if the
	  showErrorMessage argument is true.
	  @return false if an error occurred
	  */
	private final boolean refreshTagRecords(boolean showErrorMessage)
	{
		try {
			tagHandler.updateTagRecordList();
			return true;
			
		} catch (SQLException e) {
			GUIUtil.showErrorDialog(
				"JFMI failed to load the list of tags from the database."
			);
			return false;
		}
	}

	/** Mutator for the tagHandler field.
	  */
	private void setTagHandler(TagHandler tagHandler_)
	{
		if (tagHandler_ == null) {
			throw new IllegalArgumentException("error: tagHandler_ is null");
		}

		tagHandler = tagHandler_;
	}

	//************************************************************
	// PRIVATE CLASS ActionHandler 
	//************************************************************

	private class ActionHandler implements ActionListener {
		/** Responds to various ActionEvent events.
		  */
		public void actionPerformed(ActionEvent e)
		{
			Object src = e.getSource();

			if (src == editTagButton) {
				actionEditTagButton();	
			} else if (src == addTagButton) {
				
			} else if (src == deleteTagsButton) {

			}
		}

		/** Responds to an ActionEvent issued by the editTagButton.
		  Displays a JOptionPane showing the current value of the tag.
		  Allows the user to change the tag value or cancel. If the
		  value was changed, control is passed to the tagHandler field.
		  */
		private final void actionEditTagButton()
		{
			// Get the selected tag from the tagList
			TagRecord tagRec = (TagRecord)tagList.getSelectedValue();

			if (tagRec != null) {
				// Display an edit prompt to the user

				// If the tag value changed, pass control to fileHandler
			}
		}

	}

}
