package jfmi.gui;

import java.awt.Dimension;
import javax.swing.JFrame;

import jfmi.control.JFMIApp;

public class JFMIFrame extends JFrame {
	private static final String FRAME_TITLE = "JFMI";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);

	private ContentPanel contentPanel;

	private JFMIApp jfmiApp;

	/** Ctor: default.
	  */
	public JFMIFrame(JFMIApp mapp)
	{
		// Initialize instance
		super(FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_DIMENSION);
		setJFMIApp(mapp);

		// Initialize contentPanel field
		contentPanel = new ContentPanel(jfmiApp);
		setContentPane(contentPanel);

		// Do not display initially
		setVisible(false);
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


}
