package data;

import java.sql.Connection;

/**
 * Database implementation of Transaction.
 */
public class DBTransactionDAO implements TransactionDAO {

    @Override
    public void startTransaction() {
    }

    @Override
    public void endTransaction() {
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void notifyTransactionFailed() {
    }
    
}
