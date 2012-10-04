package jfmi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import jfmi.control.JFMIApp;
import jfmi.control.TaggedFile;
import jfmi.gui.TagListRenderer;
import jfmi.util.TestUtil;

class ContentPanel extends JPanel implements ActionListener {

	// Button related
	private Box buttonBox;	// Holds buttons
	private JButton buttonManageTags;

	// List related
	private JScrollPane tagScroller;	// Holds the tagList
	private JList tagList;

	// Controller related
	private JFMIApp jfmiApp;

	// ctor: default
	public ContentPanel(JFMIApp mapp)
	{ // Initialize ContentPanel
		setLayout(new BorderLayout());
		setJFMIApp(mapp);

		// Initialize the buttons and the buttonBox which contains them
		initButtonBox();

		// Initialize the tagList and the scrollPane which contains it
		initTagScroller();

		// Initialize ButtonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		buttonPanel.add(buttonBox);

		// Add Components
		add(buttonPanel, BorderLayout.WEST);
		add(tagScroller, BorderLayout.CENTER);
	}

	/** Initialize the buttonBox field.
	  This method should only be called after initButtons().
	  */
	private final void initButtonBox()
	{
		// Initialize buttons
		buttonManageTags = new JButton("Manage Tags");
		Styles.setDefaultJButtonStyles(buttonManageTags);
		buttonManageTags.addActionListener(this);

		// Initialize buttonBox
		buttonBox = new Box(BoxLayout.Y_AXIS);

		buttonBox.add(Box.createVerticalStrut(50));

		buttonBox.add(buttonManageTags);
		buttonBox.add(Box.createVerticalStrut(50));
	}

	/** Initialize the scrollPane field
	  */
	private final void initTagScroller()
	{
		// Initialize the list of tags
		TaggedFile[] data = TestUtil.getArrayOfTaggedFile();

		tagList = new JList(data);
		tagList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tagList.setLayoutOrientation(JList.VERTICAL);
		tagList.setCellRenderer(new TagListRenderer());

		// Initialize the scroll pane
		tagScroller = new JScrollPane(tagList);
	}

	/** Mutator for the jfmiApp field. 
	  */
	private final void setJFMIApp(JFMIApp mapp)
	{
		if (mapp == null) {
			throw new IllegalArgumentException("jfmiApp field cannot be null");
		}

		jfmiApp = mapp;
	}

	//************************************************************
	// IMPLEMENTATION ActionListener
	//************************************************************

	/** Responds to various ActionEvent events.
	  */
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();

		if (src == buttonManageTags) {
			jfmiApp.manageTags();
		}
	}
}
