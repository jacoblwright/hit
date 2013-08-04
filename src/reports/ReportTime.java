package reports;

import java.io.Serializable;
import java.util.Date;

public class ReportTime implements Serializable {
	
    private String name;
	private Date lastReport = null;
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastReport(Date date){
		lastReport = date;
	}
	
	public Date getLastReport(){
		return lastReport;
	}

}
