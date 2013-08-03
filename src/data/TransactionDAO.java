package data;

import java.io.*;
import java.sql.Connection;

/**
 * Provides an interface with methods to operate a database transaction.
 */
public interface TransactionDAO {

    public void startTransaction() throws IOException;
    
    public void endTransaction() throws IOException;
    
    public Connection getConnection();
    
    public void notifyTransactionFailed();
    
}
