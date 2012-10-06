package jfmi.repo;


/** An AbstractRepository defines the base services a data repository should
  offer to the application. Furthermore, it provides an application access
  to a singleton AbstractRepository subclass implementation for persisting
  Objects.
  */
public abstract class AbstractRepository {

	// PRIVATE CLASS Fields
	private static AbstractRepository repository;

	//************************************************************
	// PUBLIC CLASS Methods
	//************************************************************

	public static AbstractRepository getInstance()
	{
		if (repository == null) {
			repository = new SQLiteRepository("./dao.db");
		}

		return repository;
	}

	//************************************************************
	// PUBLIC INSTANCE Methods
	//************************************************************

	public abstract void initialize() throws Exception;
}
