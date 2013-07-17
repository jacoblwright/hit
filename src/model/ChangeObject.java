package model;

public class ChangeObject {
	private ChangeType changeType;
	private Object selectedData;
	
	public ChangeType getChangeType() {
		return changeType;
	}
	
	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}
	
	public Object getSelectedData() {
		return selectedData;
	}
	
	public void setSelectedData( Object obj ) {
		this.selectedData = obj;
	}
	
}
