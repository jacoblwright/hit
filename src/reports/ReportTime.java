package reports;

import java.io.Serializable;
import java.util.Date;

public class ReportTime implements Serializable{
	
	static Date lastReport = null;
	
	public static void setLastReport(Date date){
		lastReport = date;
	}
	
	public static Date getLastReport(){
		return lastReport;
	}

}
