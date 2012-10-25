package jfmi.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/** A SortOptionsBox is a Java Swing Box which displays sorting options to a
  user. There are two kinds of sorting options: fields and orders. Field
  options represent fields by which a client can sort a collection of objects.
  For example, if a Student class had fields "age" and "name", both of these
  would be considered field sorting options. Order sorting options refer to 
  how a sorted set of objects is displayed, e.g. ascending or descending.
  */
public class SortOptionsBox extends Box {

	// PRIVATE Instance Fields
	private SortedSet<String> fields;
	private SortedSet<String> orders;

	private Map<String, JRadioButton> fieldButtonMap;
	private Map<String, JRadioButton> orderButtonMap;

	private ButtonGroup fieldGroup;
	private ButtonGroup orderGroup;

	private Box fieldBox;
	private Box orderBox;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs a default SortOptionsBox with empty sets of fields and
	  orders.
	  */
	public SortOptionsBox()
	{
		this(new TreeSet<String>(), new TreeSet<String>());
	}

	/** Constructs an instance using the specified non-null collections of
	  fields and orders.
	  @param fields_ the field sorting options
	  @param orders_ the order sorting options
	  @throws NullPointerException if either parameter is null
	  */
	public SortOptionsBox(Collection<String> fields_, Collection<String> orders_)
	{
		super(BoxLayout.Y_AXIS);
		JRadioButton newButton;

		// Initialize the sorting option sets
		fields = new TreeSet<String>(fields_);
		orders = new TreeSet<String>(orders_);

		// Initialize the button groups
		fieldGroup = new ButtonGroup();
		orderGroup = new ButtonGroup();

		// Initialize the button maps
		fieldButtonMap = new HashMap<String, JRadioButton>();
		orderButtonMap = new HashMap<String, JRadioButton>();

		// Add buttons to the field button group and button map
		for (String field : fields) {
			newButton = new JRadioButton(field);
			newButton.setActionCommand(field);	

			if (field.equals(fields.first())) {
				newButton.setSelected(true);
			}

			fieldGroup.add(newButton);
			fieldButtonMap.put(field, newButton);
		}

		// Add buttons to the order button group and button map
		for (String order : orders) {
			newButton = new JRadioButton(order);
			newButton.setActionCommand(order);

			if (order.equals(orders.first())) {
				newButton.setSelected(true);
			}

			orderGroup.add(newButton);
			orderButtonMap.put(order, newButton);
		}

		// Create the field box and add its components
		fieldBox = Box.createVerticalBox();
		fieldBox.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel fieldBoxLabel = new JLabel("Sort By Field");
		fieldBoxLabel.setBorder(new EmptyBorder(2, 2, 2, 2));

		for (String field : fields) {
			fieldBox.add(fieldButtonMap.get(field));
		}

		// Create the order box and add its components
		orderBox = Box.createVerticalBox();
		orderBox.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel orderBoxLabel = new JLabel("Sorting Order");
		orderBoxLabel.setBorder(new EmptyBorder(2, 2, 2, 2));

		for (String order : orders) {
			orderBox.add(orderButtonMap.get(order));
		}

		// Add components to this
		add(Box.createVerticalStrut(5));
		add(fieldBox);
		add(Box.createVerticalStrut(5));
		add(orderBox);
		add(Box.createVerticalStrut(5));
	}

	/** Gets the value of the currently selected field sorting option.
	  @return the selected field; null if empty set or none selected
	  */
	public String getSelectedField()
	{
		if (fields.isEmpty()) {
			return null;
		}

	 	return fieldGroup.getSelection().getActionCommand();
	}

	/** Gets the value of the currently selected order sorting option.
	  @return the selected order; null if empty set or none selected
	  */
	public String getSelectedOrder()
	{
		if (orders.isEmpty()) {
			return null;
		}

		return orderGroup.getSelection().getActionCommand();
	}

	/** Sets the ActionListener used for the instance's components.
	  @param listener the ActionListener to be registered with the components
	  */
	public void setActionListener(ActionListener listener)
	{
		for (String field : fields) {
			fieldButtonMap.get(field).addActionListener(listener);
		}

		for (String order : orders) {
			orderButtonMap.get(order).addActionListener(listener);
		}
	}

	/** Sets the field sorting options to a sorted copy of the specified
	  Collection.
	  @param fields_ the new field options
	  */
	public void setFields(Collection<String> fields_)
	{
		fields = new TreeSet<String>(fields_);
	}

	/** Sets the order sorting options to a sorted copy of the specified
	  Collection.
	  @param orders_ the new order options
	  */
	public void setOrders(Collection<String> orders_)
	{
		orders = new TreeSet<String>(orders_);
	}


	//************************************************************
	// PRIVATE Instance Methods
	//************************************************************

}
