package jfmi.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import jfmi.app.TaggedFile;
import jfmi.gui.TaggedFileJListCellPanel;

public class TaggedFileJListRenderer implements ListCellRenderer<TaggedFile> {

	/** Ctor: default.
	  */
	public TaggedFileJListRenderer()
	{
	}

	/** Returns a component (a TaggedFileJListCellPanel extends JPanel) which
	  is responsible for displaying a TaggedFile instance.
	  */
	public Component getListCellRendererComponent(
		JList<? extends TaggedFile> list,
		TaggedFile value,
		int index,
		boolean isSelected,
		boolean cellHasFocus
	)
	{
		TaggedFileJListCellPanel newPanel;
	    newPanel = new TaggedFileJListCellPanel(value);
		
		if (isSelected) {
			newPanel.setBackground(new Color(237, 240, 240)); 
		} 

		if (cellHasFocus) {
			newPanel.setBackground(new Color(228, 238, 239));
		}

		return newPanel;
	}

}
