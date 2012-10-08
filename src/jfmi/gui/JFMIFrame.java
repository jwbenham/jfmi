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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.util.Vector;

import jfmi.control.JFMIApp;
import jfmi.control.TaggedFile;


/** A JFMIFrame acts as the parent Swing Component for the JFMI application's
  GUI. All other graphical components treat a JFMIFrame as their parent.
  */
public class JFMIFrame extends JFrame implements ActionListener {
	private static final String FRAME_TITLE = "JFMI";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);

	private JPanel contentPanel;

	// Button related
	private Box buttonBox;	// Holds buttons
	private JButton buttonManageTags;

	// List related
	private JScrollPane taggedFileScroller;	// Holds the taggedFileJList
	private JList taggedFileJList;

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
		buttonManageTags = new JButton("Manage Tags");
		Styles.setDefaultJButtonStyles(buttonManageTags);
		buttonManageTags.addActionListener(this);

		// Initialize buttonBox
		buttonBox = new Box(BoxLayout.Y_AXIS);
		buttonBox.add(Box.createVerticalStrut(50));
		buttonBox.add(buttonManageTags);
		buttonBox.add(Box.createVerticalStrut(50));
	}

	/** Initialize the scrollPane field.
	  */
	private final void initTagScroller()
	{
		// Instantiate the taggedFileJList
		taggedFileJList = new JList();
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

		if (src == buttonManageTags) {
			// TODO
		}
	}

}
