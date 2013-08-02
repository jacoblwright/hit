package data;

import java.sql.Connection;

public interface Transaction {

    public void startTransaction();
    
    public void endTransaction();
    
    public Connection getConnection();
    
}
