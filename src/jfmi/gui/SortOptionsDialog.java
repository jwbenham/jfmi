package jfmi.gui;

import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

/** A SortOptionsDialog provides a JDialog wrapper frame for a SortOptionsBox.
  It provides methods for clients to display the options to the user, and 
  determine what action the user took (e.g. changed an option selection).
  */
public class SortOptionsDialog extends JDialog {
	
	// PRIVATE Instance Fields
	JButton confirmButton;

	SortOptionsBox optionsBox;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs a new SortOptionsDialog with the specified parent frame,
	  the specified ActionListener for a confirmation click, and the specified
	  sort options box.
	  @param parent this dialog's parent JFrame
	  @param confirmListener ActionListener to alert when user confirms
	  @param opBox the SortOptionsBox this instance wraps
	  @throws NullPointerException if opBox is null
	  */
	public SortOptionsDialog(
			JFrame parent, 
			ActionListener confirmListener,
			SortOptionsBox opBox
	)
	{
		super(parent, "Sorting Options", true);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setVisible(false);

		if (opBox == null) {
			throw new NullPointerException("opBox cannot be null");
		}
		optionsBox = opBox;

		confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(confirmListener);

		Box contentBox = Box.createVerticalBox();
		contentBox.add(optionsBox);
		contentBox.add(Box.createVerticalStrut(5));
		contentBox.add(confirmButton);

		add(contentBox);
		pack();
	}

	/** Checks whether the last selected field and currently selected field
	  differ.
	  @return true if the old and new field values are different
	  */
	public boolean fieldChanged()
	{
		return optionsBox.fieldChanged();
	}

	/** Accesses the button used to confirm a selection.
	  @return a reference to the button which generates a confirming ActionEvent
	  */
	public JButton getConfirmButton()
	{
		return confirmButton;
	}

	/** Returns a possible null reference to the last selected field. */
	public String getLastSelectedField()
	{
		return optionsBox.getLastSelectedField();
	}

	/** Returns a possible null reference to the last selected order. */
	public String getLastSelectedOrder()
	{
		return optionsBox.getLastSelectedOrder();
	}

	/** Gets the value of the currently selected field sorting option.
	  @return the selected field; null if empty set or none selected
	  */
	public String getSelectedField()
	{
		return optionsBox.getSelectedField();
	}

	/** Gets the value of the currently selected order sorting option.
	  @return the selected order; null if empty set or none selected
	  */
	public String getSelectedOrder()
	{
		return optionsBox.getSelectedOrder();
	}
	
	/** Checks whether the last selected order and currently selected order
	  differ.
	  @return true if the old and new order values are different
	  */
	public boolean orderChanged()
	{
		return optionsBox.orderChanged();
	}
}

