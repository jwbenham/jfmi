package jfmi.dao;


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
