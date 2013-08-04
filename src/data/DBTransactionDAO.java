package data;

import java.io.*;
import java.sql.*;

import config.IOConfig;

/**
 * Database implementation of Transaction.
 */
public class DBTransactionDAO implements TransactionDAO {
    
    private Connection connection;
    
    private boolean transactionHasFailed;
    
    public DBTransactionDAO() throws IOException {
               
        try {
            
            Class.forName("org.sqlite.JDBC");

            File databaseFile = IOConfig.getDatabaseFile();
            if (!(databaseFile.exists() && databaseFile.isFile())) {

                initializeTables();

            }
        
        }
        catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage());
        }
        catch (SQLException e) {
            throw new IOException(e.getMessage());
        }
        
        connection = null;
        
    }

    @Override
    public void startTransaction() throws IOException {
        
        if (connection != null) {
            throw new IllegalStateException(
                    "Transaction has not been ended.");
        }
        
        transactionHasFailed = false;
        
        try {

            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + IOConfig.DATABASE_FILE_PATH);
            connection.setAutoCommit(false);
                        
        }
        catch (SQLException e) {
            
            connection = null;
            throw new IOException(e.getMessage());
        
        }
        
    }

    @Override
    public boolean endTransaction() throws IOException {
        
        if (connection == null) {
            throw new IllegalStateException(
                    "Transaction has not been started.");            
        }
        
        boolean transactionCommitted = false;
        
        try {
            
            if (transactionHasFailed) {
                
                connection.rollback();
                transactionCommitted = false;
                
            }
            else {
                
                connection.commit();
                transactionCommitted = true;
            
            }
            
        }
        catch (SQLException e) {            
            throw new IOException(e.getMessage());            
        }
        finally { 
            
            try {
                connection.close();
            }
            catch (SQLException e) {
                throw new IOException(e.getMessage());
            }   
            
            connection = null;
            
        }
        
        return transactionCommitted;
        
    }

    @Override
    public Connection getConnection() {
        
        if (connection == null) {
            throw new IllegalStateException("A transation must be started " +
            		"before getting a connection.");
        }
        
        return connection;
        
    }

    @Override
    public void notifyTransactionFailed() {
        transactionHasFailed = true;        
    }
    
    /**
     * Reinitializes the database for testing purposes.
     */
    public void clear() {
        
        try {
            initializeTables();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    
    }    
    
    private void initializeTables() throws SQLException {
        
        try {
            
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + IOConfig.DATABASE_FILE_PATH);
            connection.setAutoCommit(false);

            Statement s = connection.createStatement();

            s.executeUpdate("DROP TABLE IF EXISTS container;");
            s.executeUpdate("DROP TABLE IF EXISTS product;");
            s.executeUpdate("DROP TABLE IF EXISTS productToContainer;");
            s.executeUpdate("DROP TABLE IF EXISTS item;");                       
            s.executeUpdate("DROP TABLE IF EXISTS reportTime;");

            s.executeUpdate("CREATE TABLE container " + 
                    "(" + 
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                    "name VARCHAR(255) NOT NULL, " + 
                    "containerId INTEGER, " + 
                    "number FLOAT, " + 
                    "unit VARCHAR(255)" + 
                    ");");

            s.executeUpdate("CREATE TABLE product " + 
                    "(" + 
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                    "creationDate DATETIME NOT NULL, " + 
                    "upc VARCHAR(255) NOT NULL, " + 
                    "description VARCHAR(255) NOT NULL, " + 
                    "number FLOAT NOT NULL, " + 
                    "unit VARCHAR(255) NOT NULL, " + 
                    "shelfLife INTEGER NOT NULL, " + 
                    "threeMonthSupply INTEGER NOT NULL " + 
                    ");");

            s.executeUpdate("CREATE TABLE productToContainer " + 
                    "(" + 
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                    "productId INTEGER NOT NULL , " + 
                    "containerId INTEGER NOT NULL" + 
                    ");");

            s.executeUpdate("CREATE TABLE item " + 
                    "(" + 
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                    "productId INTEGER NOT NULL, " + 
                    "containerId INTEGER, " + 
                    "tag VARCHAR(255) NOT NULL, " + 
                    "entryDate DATETIME NOT NULL, " + 
                    "exitTime DATETIME" + 
                    ");");

            s.executeUpdate("CREATE TABLE reportTime " + 
                    "(" + 
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                    "name VARCHAR(255) NOT NULL, " + 
                    "reportTime DATETIME" + 
                    ");");

            connection.commit();

        }
        finally {
            
            connection.close();
            
            connection = null;
            
        }
        
    }
    
}
