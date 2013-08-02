package data;

import java.sql.Connection;

/**
 * Provides an interface with methods to operate a database transaction.
 */
public interface Transaction {

    public void startTransaction();
    
    public void endTransaction();
    
    public Connection getConnection();
    
}
