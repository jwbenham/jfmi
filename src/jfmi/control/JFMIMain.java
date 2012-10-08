package jfmi.control;

/** A skeleton class used to launch a JFMIApp instance.
  */
public final class JFMIMain {

	/** Application entry point.
	  @param args Command-line arguments - these are ignored.
	  */
	public static void main(String[] args)
	{
		JFMIApp app = JFMIApp.instance();
		app.start();
	}

}
