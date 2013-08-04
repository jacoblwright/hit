package data;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


import model.Model;

/**
 * Database implementation of ComponentDAO for Container.
 */
public class DBContainerDAO implements ComponentDAO<ContainerDTO> {
	
//	CREATE TABLE IF NOT EXISTS container
//	(
//		id INT  NOT NULL AUTO_INCREMENT,
//		name VARCHAR(255) NOT NULL ,
//		containerId INT NULL,
//		number FLOAT NULL,
//		unit VARCHAR(255) NULL,
//		PRIMARY KEY (id)
//	);
	
	private Connection connection;

    @Override
    public void create(ContainerDTO t) {
    	setConnection();
    	PreparedStatement stmt = null; 
    	Statement keyStmt = null; 
    	ResultSet keyRS = null;
    	try {
    		String sql = "INSERT INTO container " +
    				"(name, containerId, number, unit) VALUES (?, ?, ?,?)"; 
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
    public Collection<ContainerDTO> readAll() {
    	setConnection();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Collection<ContainerDTO> containers = new ArrayList<ContainerDTO>();
    	try {
    		String sql = "SELECT * FROM container;";
    		stmt = connection.prepareStatement(sql);
    	    rs = stmt.executeQuery();
    	    while (rs.next()) {
    	    	ContainerDTO contDTO = new ContainerDTO();
    	    	contDTO.setId(rs.getInt("id"));
    	    	contDTO.setName(rs.getString("name"));
    	    	contDTO.setContainerId(rs.getInt("containerId"));
    	    	contDTO.setNumber(rs.getFloat("number"));
    	    	contDTO.setUnit(rs.getString("unit"));
    	    	containers.add(contDTO);
    	    } 
    	} 
    	catch (SQLException e) {
    		getTransaction().notifyTransactionFailed();
    		e.printStackTrace();
    	}
    	finally {
    		close(rs, stmt, null);
    	}
        return containers;
    }

	@Override
    public void update(ContainerDTO t) {
    	setConnection();
    	PreparedStatement stmt = null;
    	try {
    		String sql = "UPDATE container " +
    				"SET name = ?, containerId = ?, number = ?, unit = ? " +
    				"WHERE id = ?";
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
    public void delete(ContainerDTO e) {
    	setConnection();
    	PreparedStatement stmt = null;
    	try {
    		String sql = "DELETE FROM container " +
    				"WHERE id = ?";
    		stmt = connection.prepareStatement(sql);
    		stmt.setInt(1, e.getId());
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
	    		stmt.close();
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
    
    private void initializePreparedStatement(PreparedStatement stmt, ContainerDTO t)
    		throws SQLException {
    	stmt.setString(1, t.getName()); 
		
		Integer containerId = t.getContainerId();
		if(containerId == null) {
			stmt.setNull(2, Types.INTEGER);
		}
		else {
			stmt.setInt(2, containerId); 
		}
		
		stmt.setFloat(3, t.getNumber()); 
		stmt.setString(4, t.getUnitStr());
		stmt.setInt(5, t.getId());
    }
    
    private void setConnection() {
		connection = getTransaction().getConnection();
	}
    
    private TransactionDAO getTransaction() {
    	return Model.getInstance().getTransaction();
    }

}
