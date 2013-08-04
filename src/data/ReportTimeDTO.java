package data;

import java.util.*;

public class ReportTimeDTO {
    
    private String name;
    private Date reportTime;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getReportTime() {
        return reportTime;
    }
    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ReportTimeDTO other = (ReportTimeDTO) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (reportTime == null) {
            if (other.reportTime != null) {
                return false;
            }
        }
        else if (!reportTime.equals(other.reportTime)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ReportTimeDTO [name=" + name + ", reportTime=" + reportTime
                + "]";
    }

}
