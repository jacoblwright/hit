package reports;

import java.util.ArrayList;
import java.util.List;

import model.Quantity;

public class NMonthSupplyProductRecord implements Record, Comparable<NMonthSupplyProductRecord> {
	
	private String description;
	private String barcode;
	private double NMonthSupply;
	private int currentSupply;

	public void setDescription(String desc){
		this.description = desc;
	}
	
	public void setBarcode(String code){
		this.barcode = code;
	}
	
	public void setNMonthSupply(double supply){
		this.NMonthSupply = supply;
	}
	
	public void setCurrentSupply(int supply){
		this.currentSupply = supply;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getBarcode(){
		return barcode;
	}
	
	public double getNMonthSupply(){
		return NMonthSupply;
	}
	
	public int getCurrentSupply(){
		return currentSupply;
	}
    @Override
    public List<String> getValuesAsStrings() {
    	List<String> productAttributes = new ArrayList<String>();
    	productAttributes.add(description);
    	productAttributes.add(barcode);
    	productAttributes.add(NMonthSupply + " count");
    	productAttributes.add(currentSupply + " count");
        return productAttributes;
    }

	@Override
	public int compareTo(NMonthSupplyProductRecord o) {
		return this.getDescription().compareTo(o.getDescription());
	}

}
