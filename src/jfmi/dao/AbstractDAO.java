package jfmi.dao;

import java.util.TreeSet;

/** The AbstractDAO defines the base services which should be provided by
  all data access objects.
  */
public abstract class AbstractDAO<T, U> {

	/** Stores the specified T instance. 
	  @param createMe the new T to store
	  @return true if a new storage record was created successfully
	  @throws Exception if an error occurs
	  */
	public abstract boolean create(T createMe) throws Exception;

	/** Reads the stored information of the T instance with the specified id.
	  @param id the id of the target instance
	  @return a new T instance if the id was valid, else null
	  @throws Exception if an error occurs
	  */
	public abstract T readById(U id) throws Exception;

	/** Reads all storage records of type T.
	  @return a list of all retrieved records
	  @throws Exception if an error occurs
	  */
	public abstract TreeSet<T> readAll() throws Exception;

	/** Updates the storage record with specified id to match the information
	  contained in the specified T instance.
	  @param updateMe contains the new information to be used as the update
	  @param id the id of the storage record to update
	  @return true if the storage record was found and updated
	  @throws Exception if an error occurs
	  */
	public abstract boolean update(T updateMe, U id) throws Exception;

	/** Deletes the storage record of the specified T instance.
	  @param deleteMe the T instance whose storage record will be deleted
	  @return true if the record was deleted, or did not exist
	  @throws Exception if an error occurs
	  */
	public abstract boolean delete(T deleteMe) throws Exception;

	/** Deletes all storage records of type T.
	  @throws Exception if an error occurs
	  */
	public abstract void deleteAll() throws Exception;


}
