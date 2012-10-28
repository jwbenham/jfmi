package jfmi.gui;

import java.awt.event.ActionListener;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

/** A FormBox displays a set of fields and entry areas.
  */
public class FormBox extends Box {

	// Private Instance Fields
	SortedMap<String, JTextField> formMap;


	//************************************************************
	// PUBLIC Instance Methods
	//************************************************************

	/** Constructs a default FormBox with an empty set of fields.
	  */
	public FormBox()
	{
		this(new TreeSet<String>());	
	}

	/** Constructs a FormBox with the specified non-null set of fields.
	  @param fields a set of Strings used to identify the fields of the form
	  @throws NullPointerException if fields is null
	  */
	public FormBox(SortedSet<String> fields)
	{
		// Initialize instance
		super(BoxLayout.Y_AXIS);

		// Initialize map of field strings to text fields
		if (fields == null) {
			throw new NullPointerException("fields is null");
		}
		formMap = new TreeMap<String, JTextField>();

		JTextField newTextField;

		for (String field : fields) {
			newTextField = new JTextField(10);
			formMap.put(field, newTextField);
		}

		// Add child components
		Box entryBox;

		for (String s : formMap.keySet()) {
			entryBox = Box.createHorizontalBox();
			entryBox.add(new JLabel(s));
			entryBox.add(Box.createHorizontalStrut(10));
			entryBox.add(formMap.get(s));

			this.add(entryBox);
			this.add(Box.createVerticalStrut(5));
		}
	}

	/** Gets the value of one of the form text fields.
	  @param field the field whose text field value will be returned
	  @return the value of the specified field - null if invalid field
	  */
	public String getFieldValue(String field)
	{
		JTextField textField = formMap.get(field);

		if (textField == null) {
			return null;
		}

		return textField.getText();
	}

	/** Returns a sorted map where the fields are the keys and their values
	  are the map values.
	  */
	public SortedMap<String, String> getFieldValueMap()
	{
		TreeMap<String, String> map = new TreeMap<String, String>();	

		for (String field : formMap.keySet()) {
			map.put(field, formMap.get(field).getText());	
		}

		return map;
	}

	/** Sets the form entry value for a form field. If the specified value is
	  null or empty, the effect is to delete the current value of the form
	  field.
	  @param field the field whose text field entry will be set
	  @param value the value to set the text field entry to
	  @return true if the field was valid
	  */
	public boolean setFieldValue(String field, String value)
	{
		JTextField textField = formMap.get(field);

		if (textField == null) {
			return false;
		}

		textField.setText(value);
		return true;
	}

}
