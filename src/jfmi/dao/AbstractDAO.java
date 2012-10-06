package jfmi.dao;


/** The AbstractDAO defines the base services which should be provided by
  all data access objects.
  */
public abstract class AbstractDAO<T, U> {

	// create
	public abstract void create(T createMe);

	// read
	public abstract T readById(U id);

	// update
	public abstract void update(T updateMe);

	// delete
	public abstract void delete(T deleteMe);

}
