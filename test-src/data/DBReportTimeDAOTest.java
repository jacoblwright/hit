package data;

import static org.junit.Assert.*;
import model.Model;

import org.junit.Test;

import java.util.Date;
import java.util.*;
import config.IOConfig;

public class DBReportTimeDAOTest {

    @Test
    public void test1() throws Exception {
        
        DAOFactory factory = new DBDAOFactory();
        Model.getInstance().setDAOFactory(factory);
        Model.getInstance().setTransaction(factory.createTransactionDAO());
        
        TransactionDAO t = Model.getInstance().getTransaction();
        ((DBTransactionDAO)t).clear();  
        
        ComponentDAO<ReportTimeDTO> dao = new DBReportTimeDAO();
        
        ReportTimeDTO rtDTO = new ReportTimeDTO();
        rtDTO.setName(IOConfig.REMOVED_ITEMS_REPORT_TIME_NAME);
        rtDTO.setReportTime(new Date());
        
        t.startTransaction();        
        dao.create(rtDTO);
        t.endTransaction();
                
        t.startTransaction();
        ReportTimeDTO actual = dao.readAll().iterator().next();
        t.endTransaction();
        
        //System.out.println(rtDTO.getReportTime().getTime());
        //System.out.println(actual.getReportTime().getTime());
        assertEquals(rtDTO, actual);
        
        rtDTO.setReportTime(new Date());
        
        t.startTransaction();
        dao.update(rtDTO);
        t.endTransaction();
        
        t.startTransaction();
        actual = dao.readAll().iterator().next();
        t.endTransaction();
        
        //System.out.println(rtDTO.getReportTime().getTime());
        //System.out.println(actual.getReportTime().getTime());
        assertEquals(rtDTO, actual);
        
    }
    
    @Test
    public void test2() throws Exception {
        
        DAOFactory factory = new DBDAOFactory();
        Model.getInstance().setDAOFactory(factory);
        Model.getInstance().setTransaction(factory.createTransactionDAO());
        
        TransactionDAO t = Model.getInstance().getTransaction();
        ((DBTransactionDAO)t).clear();  
        
        ComponentDAO<ReportTimeDTO> dao = new DBReportTimeDAO();
        
        ReportTimeDTO rtDTO = new ReportTimeDTO();
        rtDTO.setName(IOConfig.REMOVED_ITEMS_REPORT_TIME_NAME);
        rtDTO.setReportTime(new Date());
        
        t.startTransaction();        
        dao.create(rtDTO);
        t.notifyTransactionFailed();
        t.endTransaction();
                
        t.startTransaction();
        Collection<ReportTimeDTO> records = dao.readAll();
        t.endTransaction();
        
        System.out.println(records.size());
        assertEquals(0, records.size());
        
    }

}
