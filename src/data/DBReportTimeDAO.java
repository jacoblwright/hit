package data;

import java.sql.*;
import java.util.*;

import model.Model;

public class DBReportTimeDAO implements ComponentDAO<ReportTimeDTO> {

    @Override
    public void create(ReportTimeDTO rtDTO) {
        
        try {
        
        Connection connection =
                Model.getInstance().getTransaction().getConnection();
        
        String sql = "INSERT INTO reportTime (name, reportTime) " +
                "VALUES (?, ?);";
        PreparedStatement s = connection.prepareStatement(sql);
        s.setString(1, rtDTO.getName());
        s.setDate(2, new java.sql.Date(rtDTO.getReportTime().getTime()));
        s.executeUpdate();
        
        }
        catch (SQLException e) {
            Model.getInstance().getTransaction().notifyTransactionFailed();
        }
        
    }

    @Override
    public Collection<ReportTimeDTO> readAll() {
        return null;
    }

    @Override
    public void update(ReportTimeDTO e) {
    }

    @Override
    public void delete(ReportTimeDTO e) {
    }

}
