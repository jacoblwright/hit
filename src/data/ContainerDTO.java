package data;

import gui.common.SizeUnits;
import model.*;

/**
 * A data transfer object for the Container entity.
 */

public class ContainerDTO {
    
   
	private int id;
    private String name;
    private Integer containerId;
    private float number;
    private Enum<SizeUnits> unit;
    
    public ContainerDTO(){}
    
    public ContainerDTO( Container container ) {
    	this.id = container.getId();
    	this.name = container.getName();
    	if( container.getContainer() != null ) {
    		this.containerId = container.getContainer().getId();
    		Quantity threeMonthSupply = ((ProductGroup)container).getThreeMonthSupply();
    		number = threeMonthSupply.getNumber();
    		unit = threeMonthSupply.getUnit();
    	}
    	else {
    		this.containerId = -1;
    		this.number = 0f;
    		this.unit = SizeUnits.Unspecified;
    	}
    }
   
    public int getId() {
		return id;
	}
	
    public void setId(int id) {
		this.id = id;
	}
	
    public String getName() {
		return name;
	}
	
    public void setName(String name) {
		this.name = name;
	}
	
    public Integer getContainerId() {
		return containerId;
	}
	
    public void setContainerId(Integer containerId) {
		this.containerId = containerId;
	}
	
    public float getNumber() {
		return number;
	}
	
    public void setNumber(float number) {
		this.number = number;
	}
	
    public Enum<SizeUnits> getUnit() {
		return unit;
	}
    
    public String getUnitStr() {
    	return unit.name();
    }
	
    public void setUnit(Enum<SizeUnits> unit) {
		this.unit = unit;
	}
    
    public void setUnit(String unit) {
    	this.unit = SizeUnits.valueOf(unit);
    }
}
