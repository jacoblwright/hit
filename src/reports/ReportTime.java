package reports;

import java.io.Serializable;
import java.util.Date;

public class ReportTime implements Serializable{
	
	Date lastReport = null;
	
	public void setLastReport(Date date){
		lastReport = date;
	}
	
	public Date getLastReport(){
		return lastReport;
	}

}
