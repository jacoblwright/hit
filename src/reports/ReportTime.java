package reports;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import model.Model;
import data.*;

public class ReportTime implements Serializable {
	
    private String name;
	private Date lastReport;
	
	private ComponentDAO<ReportTimeDTO> rtDAO;
	
	public ReportTime(String name) {
	    
	    this.name = name;
	    lastReport = null;
	    
	    rtDAO = Model.getInstance().getDAOFactory().createReportTimeDAO();
	    
	}
	
	public void load() {
        
        TransactionDAO t = Model.getInstance().getTransaction();
        try {
            
            t.startTransaction();
            
            Collection<ReportTimeDTO> results = rtDAO.readAll();
            boolean recordFound = false;
            for (ReportTimeDTO result : results) {
                if (result.getName().equals(name)) {
                    recordFound = true;
                    lastReport = result.getReportTime();
                }
            }
            if (!recordFound) {
                ReportTimeDTO rtDTO = new ReportTimeDTO();
                rtDTO.setName(name);
                rtDAO.create(rtDTO);
            }
            
            t.endTransaction();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastReport(Date date){
        
		lastReport = date;
		
		TransactionDAO t = Model.getInstance().getTransaction();
        try {
            
            t.startTransaction();
            
            ReportTimeDTO rtDTO = new ReportTimeDTO();
            rtDTO.setName(name);
            rtDTO.setReportTime(lastReport);
            rtDAO.update(rtDTO);
            
            t.endTransaction();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public Date getLastReport(){
		return lastReport;
	}

}
