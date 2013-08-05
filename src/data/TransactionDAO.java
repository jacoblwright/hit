package data;

import java.io.*;
import java.sql.Connection;

/**
 * Provides an interface with methods to operate a database transaction.
 */
public interface TransactionDAO {

    /**
     * Starts a new logical transaction and sets up a new connection, which
     * can then be obtained by getConnection().
     * @pre Any previous transactions must have been ended.
     */
    public void startTransaction() throws IOException;
    
    /**
     * Ends the current logical transaction and closes the connection. If no
     * transaction failures were reported, the changes are committed;
     * otherwise, the changes are rolled back.
     * @pre There must be a transaction that has been started and has not yet
     * been ended.
     * @post The connection is closed.
     * @return true if the transaction was committed, false otherwise.
     */
    public boolean endTransaction() throws IOException;
    
    /**
     * @pre Must be preceded by a call to startTransaction().
     */
    public Connection getConnection();
    
    public void notifyTransactionFailed();
    
}
