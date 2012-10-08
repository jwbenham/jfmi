package jfmi.repo;


/** An AbstractRepository defines the base services a data repository should
  offer to the application. Furthermore, it provides an application access
  to a singleton AbstractRepository subclass implementation for persisting
  Objects.
  */
public abstract class AbstractRepository {

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	public abstract void initialize() throws Exception;
}
