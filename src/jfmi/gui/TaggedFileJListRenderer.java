package jfmi.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import jfmi.control.TaggedFile;
import jfmi.gui.TaggedFileJListCellPanel;

public class TaggedFileJListRenderer implements ListCellRenderer {

	/** Ctor: default.
	  */
	public TaggedFileJListRenderer()
	{
	}

	/** Returns a component (a TaggedFileJListCellPanel extends JPanel) which
	  is responsible for displaying a TaggedFile instance.
	  */
	public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean isSelected,
		boolean cellHasFocus
	)
	{
		TaggedFileJListCellPanel newPanel;
	    newPanel = new TaggedFileJListCellPanel((TaggedFile)value);
		
		if (isSelected) {
			newPanel.setBackground(Styles.PALEST_GREEN); 
		} 

		if (cellHasFocus) {
			newPanel.setBackground(Styles.PALE_GREEN);
		}

		return newPanel;
	}

}
