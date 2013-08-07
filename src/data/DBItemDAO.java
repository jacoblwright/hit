package data;

import java.util.ArrayList;
import java.sql.*;
import java.util.Collection;

import model.Model;

/**
 * Database implementation of ComponentDAO for Item.
 */
public class DBItemDAO implements ComponentDAO<ItemDTO> {

    @Override
    public void create(ItemDTO e) {
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try{
    		
    		Connection con = Model.getInstance().getTransaction().getConnection();
    		String sql = "INSERT INTO item " +
    				"(containerId, productId, tag, entryDate, exitTime) VALUES (?, ?, ?, ?, ?)";
    		pstmt = con.prepareStatement(sql);
    		
    		
    		Integer containerId = e.getContainerId();
    		if(containerId == null) {
    			pstmt.setNull(1, Types.INTEGER);
    		}
    		else {
    			pstmt.setInt(1, containerId); 
    		}
    		pstmt.setInt(2, e.getProductId());
    		pstmt.setString(3, e.getTag()); 
    		pstmt.setDate(4, new java.sql.Date(e.getEntryDate().getTime()));
    		if (e.getExitTime() != null){
    			pstmt.setDate(5, new java.sql.Date(e.getEntryDate().getTime()));
    		}
    		else {
    			pstmt.setNull(5, Types.INTEGER);
    		}
    		
    		if (pstmt.executeUpdate() == 1){
    			stmt = con.createStatement();
		    	rs = stmt.executeQuery("select last_insert_rowid()"); 
		    	rs.next();
		    	int id = rs.getInt(1);
		    	e.setId(id);
    		}  
	    	else {
	    		Model.getInstance().getTransaction().notifyTransactionFailed();
	    	}
    	}
    	catch (SQLException err) { 
    		Model.getInstance().getTransaction().notifyTransactionFailed();
    		err.printStackTrace();
    	}
    	finally {
    		close(rs, pstmt, stmt);
    	}
    	
    	
    	
    }

    @Override
    public Collection<ItemDTO> readAll() {
        Collection<ItemDTO> ret = new ArrayList<ItemDTO>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
        	Connection con = Model.getInstance().getTransaction().getConnection();
        	
	        String sql_str = "SELECT * FROM item";
	        stmt = con.prepareStatement(sql_str);
	        rs = stmt.executeQuery();
	        while (rs.next()){
	        	ItemDTO trans = new ItemDTO();
	        	trans.setId(rs.getInt("id"));
	        	trans.setContainerId(rs.getInt("containerID"));
	        	trans.setProductId(rs.getInt("productID"));
	        	trans.setTag(rs.getString("tag"));
	        	trans.setEntryDate(new java.util.Date(rs.getDate("entryDate").getTime()));
	        	if (rs.getDate("exitTime") != null){
	        		trans.setExitTime(new java.util.Date(rs.getDate("exitTime").getTime()));
	        	}
	        	else {
	        		trans.setExitTime(null);
	        	}
	        	
	        	ret.add(trans);
	        }
        }
        catch (SQLException e) { 
    		Model.getInstance().getTransaction().notifyTransactionFailed();
    		e.printStackTrace();
    	}
    	finally {
    		close(rs, stmt, null);
    	}
        return ret;
    }

    @Override
    public void update(ItemDTO e) {
    	PreparedStatement pstmt = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	try{
    		Connection con = Model.getInstance().getTransaction().getConnection();
    		String sql = "UPDATE item SET " +
    				"productId = ?, containerId = ?, tag = ?, entryDate = ?, exitTime = ? " +
    				"WHERE id = ?";
    		pstmt = con.prepareStatement(sql);
    		pstmt.setInt(1, e.getProductId());
    		pstmt.setInt(2, e.getContainerId());
    		pstmt.setString(3, e.getTag());
    		pstmt.setDate(4, new java.sql.Date(e.getEntryDate().getTime()));
    		pstmt.setDate(5, new java.sql.Date(e.getExitTime().getTime()));
    		pstmt.setInt(6, e.getId());
    		pstmt.executeUpdate();
    	}
    	catch (SQLException err) { 
    		Model.getInstance().getTransaction().notifyTransactionFailed();
    		err.printStackTrace();
    	}
    	finally {
    		close(rs, pstmt, stmt);
    	}
    	
    	
    }

    @Override
    public void delete(ItemDTO e) {
    	PreparedStatement pstmt = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	try{
    		Connection con = Model.getInstance().getTransaction().getConnection();
    		String sql = "DELTE FROM item WHERE id = ?";
    		pstmt = con.prepareStatement(sql);
    		pstmt.setInt(1, e.getId());
    		if (pstmt.executeUpdate() != 1) {
	    		Model.getInstance().getTransaction().notifyTransactionFailed();
	    	}
    	}
    	catch (SQLException err) { 
    		Model.getInstance().getTransaction().notifyTransactionFailed();
    		err.printStackTrace();
    	}
    	finally {
    		close(rs, pstmt, stmt);
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
			Model.getInstance().getTransaction().notifyTransactionFailed();
			e.printStackTrace();
		}
	}
}
