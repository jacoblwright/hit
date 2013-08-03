package data;

import java.io.*;

/**
 * Provides an interface for saving and loading all of the object model data
 * in the system.
 */
public interface ComprehensiveDAO {
    
    /**
     * Saves all of the object model data in the system.
     * @throws IOException if there was a problem in saving
     */
    public void save() throws IOException;
    
    /**
     * Loads into the object model all of the saved data.
     * @throws IOException if there was a problem in loading.
     */
    public void load() throws IOException;

}
