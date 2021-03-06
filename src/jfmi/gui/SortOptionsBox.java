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
public class SortOptionsBox extends Box implements ActionListener {

	// PRIVATE Instance Fields
	private SortedSet<String> fields;
	private SortedSet<String> orders;

	private String lastSelectedField;
	private String lastSelectedOrder;
	private String selectedField;
	private String selectedOrder;

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

		// Initialize the selection fields
		lastSelectedField = selectedField = null;
		lastSelectedOrder = selectedOrder = null;

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
			newButton.addActionListener(this);

			if (field.equals(fields.first())) {
				newButton.setSelected(true);
				selectedField = field;
			}

			fieldGroup.add(newButton);
			fieldButtonMap.put(field, newButton);
		}

		// Add buttons to the order button group and button map
		for (String order : orders) {
			newButton = new JRadioButton(order);
			newButton.setActionCommand(order);
			newButton.addActionListener(this);

			if (order.equals(orders.first())) {
				newButton.setSelected(true);
				selectedOrder = order;
			}

			orderGroup.add(newButton);
			orderButtonMap.put(order, newButton);
		}

		// Create the field box and add its components
		JLabel fieldBoxLabel = new JLabel("Sort By Field");
		fieldBoxLabel.setBorder(new EmptyBorder(2, 2, 2, 2));

		fieldBox = Box.createVerticalBox();
		fieldBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		fieldBox.add(fieldBoxLabel);

		for (String field : fields) {
			fieldBox.add(fieldButtonMap.get(field));
		}

		// Create the order box and add its components
		JLabel orderBoxLabel = new JLabel("Sorting Order");
		orderBoxLabel.setBorder(new EmptyBorder(2, 2, 2, 2));

		orderBox = Box.createVerticalBox();
		orderBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		orderBox.add(orderBoxLabel);
		
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

	/** Adds an ActionListener used for the instance's components.
	  @param listener the ActionListener to be registered with the components
	  */
	public void addActionListener(ActionListener listener)
	{
		for (String field : fields) {
			fieldButtonMap.get(field).addActionListener(listener);
		}

		for (String order : orders) {
			orderButtonMap.get(order).addActionListener(listener);
		}
	}

	/** Checks whether the last selected field and currently selected field
	  differ.
	  @return true if the old and new field values are different
	  */
	public boolean fieldChanged()
	{
		return !getLastSelectedField().equals(selectedField);
	}

	/** Returns a reference to the last selected field. If the reference is null,
	 the empty string is returned. */
	public String getLastSelectedField()
	{
		if (lastSelectedField == null) {
			return "";
		}

		return lastSelectedField;
	}

	/** Returns a reference to the last selected order. If the reference is null,
	 the empty string is returned. */
	public String getLastSelectedOrder()
	{
		if (lastSelectedOrder == null) {
			return "";
		}

		return lastSelectedOrder;
	}

	/** Gets the value of the currently selected field sorting option.
	  @return the selected field; "" if empty set or none selected
	  */
	public String getSelectedField()
	{
		if (fields.isEmpty()) {
			return "";
		}

		return selectedField;
	}

	/** Gets the value of the currently selected order sorting option.
	  @return the selected order; "" if empty set or none selected
	  */
	public String getSelectedOrder()
	{
		if (orders.isEmpty()) {
			return "";
		}

		return selectedOrder;
	}

	/** Checks whether the last selected order and currently selected order
	  differ.
	  @return true if the old and new order values are different
	  */
	public boolean orderChanged()
	{
		return !getLastSelectedOrder().equals(selectedOrder);
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

	/** Sets the currently selected field, and updates the appropriate button.
	  @param field field to set as selected
	  @return true if the argument is valid and the field has been set
	  */
	public boolean setSelectedField(String field)
	{
		if (fieldButtonMap.containsKey(field)) {
			fieldButtonMap.get(field).setSelected(true);
			selectedField = field;

			return true;
		}

		return false;
	}

	/** Sets the currently selected order, and updates the appropriate button.
	  @param order order to set as selected
	  @return true if the argument is valid and the order has been set
	  */
	public boolean setSelectedOrder(String order)
	{
		if (orderButtonMap.containsKey(order)) {
			orderButtonMap.get(order).setSelected(true);
			selectedOrder = order;

			return true;
		}

		return false;
	}


	//************************************************************
	// IMPLEMENTATION of ActionListener 
	//************************************************************

	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();

		if (fieldButtonMap.containsValue(src)) {
			lastSelectedField = selectedField;
			selectedField = fieldGroup.getSelection().getActionCommand();

		} else if (orderButtonMap.containsValue(src)) {
			lastSelectedOrder = selectedOrder;
			selectedOrder = orderGroup.getSelection().getActionCommand();
		}
	}

}
