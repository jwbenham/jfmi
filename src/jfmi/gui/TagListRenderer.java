package jfmi.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import jfmi.control.TaggedFile;
import jfmi.gui.TagListCellPanel;

public class TagListRenderer implements ListCellRenderer {

	/** Ctor: default.
	  */
	public TagListRenderer()
	{
	}

	/** Returns a component (a TagListCellPanel extends JPanel) which
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
		TagListCellPanel newPanel = new TagListCellPanel((TaggedFile)value);
		
		if (isSelected) {
			newPanel.setBackground(Styles.DARKER_GRAY);
		}

		if (cellHasFocus) {
			newPanel.setBackground(Color.BLACK);
		}

		return newPanel;
	}

}
