package jfmi.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

/** A ListSelectionBox is a simply Swing Box which contains two lists. One
  list contains items for the user to select, the second contains items the
  user has already selected. The class provides methods for clients to 
  check which items are selected, set the list of items to be used, etc.
  */
public class ListSelectionBox extends Box implements ActionListener {
	// Private Instance Fields
	private SortedSet<String> fullSet;
	private SortedSet<String> selectedSet;

	private JButton addButton;
	private JButton removeButton;

	private MutableListModel<String> fullModel;
	private JList<String> fullList;
	private MutableListModel<String> selectedModel;
	private JList<String> selectedList;

	private JScrollPane fullScroller;
	private JScrollPane selectedScroller;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs a default ListSelectionBox with empty item lists.
	  */
	public ListSelectionBox()
	{
		this(null, null); 
	}

	/** Constructs a ListSelectionBox with the specified set of items available
	  for selection.
	  @param full the set of items to be listed for selection
	  */
	public ListSelectionBox(SortedSet<String> full)
	{
		this(full, null);
	}

	/** Constructs a ListSelectionBox with the specified set of selectable items,
	  and the specified set of selected items.
	  @param full the full set of selectable items
	  @param selected the set of already selected items
	  */
	public ListSelectionBox(SortedSet<String> full, SortedSet<String> selected)
	{
		// Initialize instance
		super(BoxLayout.X_AXIS);

		// Initialize item sets
		if (full == null) {
			fullSet = new TreeSet<String>();
		} else {
			fullSet = full;
		}

		if (selected == null) {
			selectedSet = new TreeSet<String>();
		} else {
			selectedSet = selected;
		}

		// Initialize buttons
		addButton = new JButton("Add >>");
		addButton.addActionListener(this);

		removeButton = new JButton("<< Remove");
		removeButton.addActionListener(this);

		// Initialize lists
		fullModel = new MutableListModel<String>(fullSet);
		fullList = new JList<String>(fullModel);

		selectedModel = new MutableListModel<String>(selectedSet);
		selectedList = new JList<String>(fullModel);

		// Initialize scrollpanes
		fullScroller = new JScrollPane(fullList);
		selectedScroller = new JScrollPane(selectedList);

		// Add components
		Box fullBox = Box.createVerticalBox();
		fullBox.add(fullScroller);
		fullBox.add(addButton);

		Box selectedBox = Box.createVerticalBox();
		selectedBox.add(selectedScroller);
		selectedBox.add(removeButton);

		add(fullBox);
		add(Box.createHorizontalStrut(10));
		add(selectedBox);
	}


	//************************************************************
	// IMPLEMENTATION of ActionListener
	//************************************************************

	/** Specifies what action to perform when an ActionEvent is fired.
	  @param e the ActionEvent to respond to
	  */
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();

		if (src == addButton) {
			addButtonAction();
		} else if (src == removeButton) {
			removeButtonAction();
		}
	}


	//************************************************************
	// PRIVATE Instance Methods
	//************************************************************

	/** Specifies what happens when the user clicks the addButton.
	  */
	private void addButtonAction()
	{
		String value = fullList.getSelectedValue();

		if (value == null) {
			return;
		}

		fullModel.remove(value);
		fullSet.remove(value);	

		selectedModel.add(value);
		selectedSet.add(value);
	}

	/** Specifies what happens when the user clicks the removeButton.
	  */
	private void removeButtonAction()
	{
		String value = selectedList.getSelectedValue();

		if (value == null) {
			return;
		}

		selectedModel.remove(value);
		selectedSet.remove(value);

		fullModel.add(value);
		fullSet.add(value);
	}

}
