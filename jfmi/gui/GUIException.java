package jfmi.gui;


/** A general exception thrown by classes in the jfmi.gui package.
  It is not a checked exception, as typically a GUIException indicates
  an unfixable error.
  */
public class GUIException extends RuntimeException {
	/** Constructs a new GUIException, letting the RunTimeException
	  default constructor set the detail message to null.
	  */
	public GUIException()
	{

	}

	/** Constructs a new GUIException, with the detail message provided.
	  */
	public GUIException(String message) {
		super(message);
	}
}
