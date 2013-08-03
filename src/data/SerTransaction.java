package data;

import java.sql.Connection;

/**
 * Serialization implementation of Transaction.
 */
public class SerTransaction implements Transaction {

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
    
}
