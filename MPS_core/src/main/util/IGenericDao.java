package main.util;

import java.util.List;


public interface IGenericDao <T> {

    /** Persist the newInstance object into database */
    public void create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    public T read(int id);

    /** Save changes made to a persistent object.  */
    public T update(T transientObject);

    /** Remove an object from persistent storage in the database */
    public void delete(T persistentObject);
    
    /** Retrieve all objects of Type T */
    public List<T> readAll();
}
