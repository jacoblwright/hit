package data;

import static org.junit.Assert.*;
import model.Model;

import org.junit.Test;

import java.util.Date;
import config.IOConfig;

public class DBReportTimeDAOTest {

    @Test
    public void test1() throws Exception {
        
        Model.getInstance().setDAOFactory(new DBDAOFactory());
        Model.getInstance().initialize();
        
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

}
