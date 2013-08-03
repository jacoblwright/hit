package data;

import java.sql.Connection;

/**
 * Serialization implementation of Transaction.
 */
public class SerTransactionDAO implements TransactionDAO {

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
