package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import model.Model;

/**
 * Database implementation of ComponentDAO for Product to Container mapping.
 */
public class DBProductToContainerDAO
        implements ComponentDAO<ProductToContainerDTO> {
	
	private Connection connection;

    @Override
    public void create(ProductToContainerDTO t) {
    	System.out.println(t);
    	setConnection();
    	PreparedStatement stmt = null; 
    	Statement keyStmt = null; 
    	ResultSet keyRS = null;
    	try {
    		String sql = "INSERT INTO productToContainer " +
    				"(productId, containerId) VALUES (?, ?)"; 
    		stmt = connection.prepareStatement(sql);
    		initializePreparedStatement(stmt, t);
    		
	    	if (stmt.executeUpdate() == 1) {
		    	keyStmt = connection.createStatement();
		    	keyRS = keyStmt.executeQuery("select last_insert_rowid()"); 
		    	keyRS.next();
		    	int id = keyRS.getInt(1);
		    	t.setId(id);
	    	}  
	    	else {
	    		getTransaction().notifyTransactionFailed();
	    	}
    	}
    	catch (SQLException e) { 
    		getTransaction().notifyTransactionFailed();
    		e.printStackTrace();
    	}
    	finally {
    		close(keyRS, stmt, keyStmt);
    	}
    }

    @Override
    public Collection<ProductToContainerDTO> readAll() {
    	setConnection();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Collection<ProductToContainerDTO> pairs = 
    						new ArrayList<ProductToContainerDTO>();
    	try {
    		String sql = "SELECT * FROM productToContainer;";
    		stmt = connection.prepareStatement(sql);
    	    rs = stmt.executeQuery();
    	    while (rs.next()) {
    	    	ProductToContainerDTO pair = new ProductToContainerDTO();
    	    	pair.setId(rs.getInt("id"));
    	    	pair.setProductID(rs.getInt("productId"));
    	    	pair.setContainerID(rs.getInt("containerId"));
    	    	pairs.add(pair);
    	    } 
    	} 
    	catch (SQLException e) {
    		getTransaction().notifyTransactionFailed();
    		e.printStackTrace();
    	}
    	finally {
    		close(rs, stmt, null);
    	}
        return pairs;
    }

    @Override
    public void update(ProductToContainerDTO t) {
    	/* This will act in the same way that adding and deleting would */
    }

    @Override
    public void delete(ProductToContainerDTO t) {
    	setConnection();
    	PreparedStatement stmt = null;
    	try {
    		String sql = "DELETE FROM productToContainer " +
    				"WHERE productId = ? AND containerId = ?";
    		stmt = connection.prepareStatement(sql);
    		initializePreparedStatement(stmt, t);
	    	if (stmt.executeUpdate() != 1) {
	    		getTransaction().notifyTransactionFailed();
	    	}
    	}
    	catch (SQLException sqlE) {
    		getTransaction().notifyTransactionFailed();
    		sqlE.printStackTrace();
    	} 
    	finally {
    		close(null, stmt, null);
    	}
    }
    
    private void close(ResultSet rs, PreparedStatement preStmt, Statement stmt) {
		try {
			if (rs != null) {
				rs.close();
			}
	    	if (preStmt != null) {
	    		preStmt.close();
	    	}
	    	if (stmt != null) {
	    		stmt.close();
	    	}
		}
		catch (SQLException e) {
			getTransaction().notifyTransactionFailed();
			e.printStackTrace();
		}
	}
    
    private void initializePreparedStatement(PreparedStatement stmt, 
    						ProductToContainerDTO t)throws SQLException {
    	stmt.setInt(1, t.getProductID());
    	stmt.setInt(2, t.getContainerID());
    }
    
    private void setConnection() {
		connection = getTransaction().getConnection();
	}
    
    private TransactionDAO getTransaction() {
    	return Model.getInstance().getTransaction();
    }
    
}
