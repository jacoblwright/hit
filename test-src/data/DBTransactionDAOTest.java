package data;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.*;

public class DBTransactionDAOTest {

    @Test
    public void test1() throws Exception {
        
        TransactionDAO t = new DBTransactionDAO();
        ((DBTransactionDAO)t).clear();
        
        try {
            t.getConnection();
            fail();
        }
        catch (IllegalStateException e) {}
        
        try {
            t.endTransaction();
            fail();
        }
        catch (IllegalStateException e) {}
        
        t.startTransaction();
        
        try {
            t.startTransaction();
            fail();
        }
        catch (IllegalStateException e) {}
                
        Connection c = t.getConnection();
        
        assertFalse(c.isClosed());
        
        t.endTransaction();
        
        assertTrue(c.isClosed());
        
        try {
            t.getConnection();
            fail();
        }
        catch (IllegalStateException e) {}
        
    }
    
    @Test
    public void test2() throws Exception {
        
        TransactionDAO t = new DBTransactionDAO();
        ((DBTransactionDAO)t).clear();
     
        t.startTransaction();
        Connection c = t.getConnection();
        assertTrue(t.endTransaction());
        
        t.startTransaction();
        c = t.getConnection();
        t.notifyTransactionFailed();
        assertFalse(t.endTransaction());
        
    }

}
