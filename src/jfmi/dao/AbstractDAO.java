package jfmi.dao;


/** The AbstractDAO defines the base services which should be provided by
  all data access objects.
  */
public abstract class AbstractDAO<T, U> {

	// create
	public abstract boolean create(T createMe) throws Exception;

	// read
	public abstract T readById(U id) throws Exception;

	// update
	public abstract boolean update(T updateMe, U id) throws Exception;

	// delete
	public abstract boolean delete(T deleteMe) throws Exception;

}
