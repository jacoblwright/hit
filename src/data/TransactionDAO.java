package data;

import java.sql.Connection;

/**
 * Provides an interface with methods to operate a database transaction.
 */
public interface TransactionDAO {

    public void startTransaction();
    
    public void endTransaction();
    
    public Connection getConnection();
    
    public void notifyTransactionFailed();
    
}
