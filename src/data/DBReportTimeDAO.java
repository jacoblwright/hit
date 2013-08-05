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
            if (rtDTO.getReportTime() == null) {
                s.setDate(2, null);
            }
            else {
                s.setDate(2, new java.sql.Date(
                        rtDTO.getReportTime().getTime()));
            }
            s.executeUpdate();
        
        }
        catch (SQLException e) {
            Model.getInstance().getTransaction().notifyTransactionFailed();
        }
        
    }

    @Override
    public Collection<ReportTimeDTO> readAll() {
        
        List<ReportTimeDTO> results = null;
        
        try {

            Connection connection =
                    Model.getInstance().getTransaction().getConnection();

            String sql = "SELECT name, reportTime FROM reportTime;";
            PreparedStatement s = connection.prepareStatement(sql);
            ResultSet rs = s.executeQuery();

            results = new LinkedList<ReportTimeDTO>();
            while (rs.next()) {

                ReportTimeDTO rtDTO = new ReportTimeDTO();
                rtDTO.setName(rs.getString(1));
                java.sql.Date date = rs.getDate(2);
                if (date == null) {
                    rtDTO.setReportTime(null);
                }
                else {
                    rtDTO.setReportTime(
                            new java.util.Date(rs.getDate(2).getTime()));
                }
                results.add(rtDTO);

            }
        
        }
        catch (SQLException e) {
            Model.getInstance().getTransaction().notifyTransactionFailed();
        }
        
        return results;
    }

    @Override
    public void update(ReportTimeDTO rtDTO) {
        
        try {
            
            Connection connection =
                    Model.getInstance().getTransaction().getConnection();
            
            String sql =
                    "UPDATE reportTime SET reportTime = ? WHERE name = ?";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setDate(1, new java.sql.Date(rtDTO.getReportTime().getTime()));
            s.setString(2, rtDTO.getName());
            s.executeUpdate();
            
        }
        catch (SQLException e) {
            Model.getInstance().getTransaction().notifyTransactionFailed();
        }
    }

    @Override
    public void delete(ReportTimeDTO e) {
        // Operation not supported.
    }

}
