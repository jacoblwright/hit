package data;

import java.io.Serializable;
import java.util.*;
import java.io.*;

/**
 * Provides an interface for creating, reading, updating, and deleting
 * information, represented by the generic data transfer object T, in a
 * database.
 */
public interface ComponentDAO<T> extends Serializable {
    
    /**
     * Adds the specified DTO to the database.
     */
    public void create(T e);

    /**
     * Returns a Collection of all of the records in the appropriate table,
     * each record being represented by the DTO T.
     */
    public Collection<T> readAll();
    
    /**
     * Updates the specified DTO in the database.
     */
    public void update(T e);
    
    /**
     * Deletes the specified DTO from the database.
     */
    public void delete(T e);
    
}
