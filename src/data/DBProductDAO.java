package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import model.Model;

/**
 * Database implementation of ComponentDAO for Product.
 */
public class DBProductDAO implements ComponentDAO<ProductDTO> {

	private Connection connection;
	
    @Override
    public void create(ProductDTO t) {
    	System.out.println(t);
    	setConnection();
    	PreparedStatement stmt = null; 
    	Statement keyStmt = null; 
    	ResultSet keyRS = null;
    	
    	try {
    		String sql = "INSERT INTO product " +
    				"(creationDate, upc, description, number, unit, " +
    				"shelfLife, threeMonthSupply) " +
    				"VALUES (?, ?, ?, ?, ?, ?, ?)"; 
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
    public Collection<ProductDTO> readAll() {
    	setConnection();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Collection<ProductDTO> products = new ArrayList<ProductDTO>();
    	try {
    		String sql = "SELECT * FROM product;";
    		stmt = connection.prepareStatement(sql);
    	    rs = stmt.executeQuery();
    	    while (rs.next()) {
    	    	ProductDTO prodDTO = new ProductDTO();
    	    	prodDTO.setId(rs.getInt("id"));
    	    	prodDTO.setDescription(rs.getString("description"));
    	    	prodDTO.setCreationDate(rs.getDate("creationDate"));
    	    	prodDTO.setUpc(rs.getString("upc"));
    	    	prodDTO.setNumber(rs.getFloat("number"));
    	    	prodDTO.setUnit(rs.getString("unit"));
    	    	prodDTO.setShelfLife(rs.getInt("shelfLife"));
    	    	prodDTO.setThreeMonthSupply(rs.getInt("threeMonthSupply"));
    	    	products.add(prodDTO);
    	    } 
    	} 
    	catch (SQLException e) {
    		getTransaction().notifyTransactionFailed();
    		e.printStackTrace();
    	}
    	finally {
    		close(rs, stmt, null);
    	}
        return products;
    }

    @Override
    public void update(ProductDTO t) {
    	setConnection();
    	PreparedStatement stmt = null;
    	try {
    		String sql = "UPDATE product " +
    				"SET creationDate = ?, upc = ?, description = ?, " +
    				"number = ?, unit = ?, shelfLife = ?, " +
    				"threeMonthSupply = ? " + "WHERE id = ?";
    		stmt = connection.prepareStatement(sql);
    		initializePreparedStatement(stmt, t);
    	
	    	if (stmt.executeUpdate() != 1) {
	    		getTransaction().notifyTransactionFailed();
	    	}
    	}
    	catch (SQLException e) {
    		getTransaction().notifyTransactionFailed();
    		e.printStackTrace();
    	} 
    	finally {
    		close(null, stmt, null);
    	}
    }

    @Override
    public void delete(ProductDTO t) {
    	setConnection();
    	PreparedStatement stmt = null;
    	try {
    		String sql = "DELETE FROM product " +
    				"WHERE id = ?";
    		stmt = connection.prepareStatement(sql);
    		stmt.setInt(1, t.getId());
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
    
    private void initializePreparedStatement(PreparedStatement stmt, ProductDTO t)
    		throws SQLException {
    	
    	stmt.setDate(1, (Date) t.getCreationDate());
    	stmt.setString(2, t.getUpc());
    	stmt.setString(3, t.getDescription());
    	stmt.setFloat(4, t.getNumber());
    	stmt.setString(5, t.getUnitStr());
    	stmt.setInt(6, t.getShelfLife());
    	stmt.setInt(7, t.getThreeMonthSupply());
    	
    }
    
    private void setConnection() {
		connection = getTransaction().getConnection();
	}
    
    private TransactionDAO getTransaction() {
    	return Model.getInstance().getTransaction();
    }

}
