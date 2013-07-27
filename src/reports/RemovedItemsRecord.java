package reports;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Model;
import model.Product;
import model.Quantity;

public class RemovedItemsRecord implements Record, Comparable<RemovedItemsRecord> {
	
	private Model model;
	private String description;
	private Quantity size;
	private String barcode;
	private int removedItems;
	private int currentSupply;
	
	private DecimalFormat df = new DecimalFormat("0.##");
	
	public void setDescription(String desc){
		this.description = desc;
	}
	
	public void setSize(Quantity s){
		this.size = s;
	}
	
	public void setBarcode(String code){
		this.barcode = code;
	}
	
	public void setRemovedItems(int items){
		this.removedItems = items;
	}
	
	public void setCurrentSupply(int currentItems){
		currentSupply = currentItems;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Quantity getSize(){
		return size;
	}
	
	public String getBarcode(){
		return barcode;
	}
	
	public int getRemovedItems(){
		return removedItems;
	}
	
	public int getCurrentSupply(){
		return currentSupply;
	}

    @Override
    public List<String> getValuesAsStrings() {
    	List<String> itemAttributes = new ArrayList();
    	itemAttributes.add(description);
    	itemAttributes.add(df.format(size.getNumber()) + " " + size.getUnit().toString());
    	itemAttributes.add(barcode);
    	itemAttributes.add(Integer.toString(removedItems));
    	itemAttributes.add(Integer.toString(currentSupply));
        return itemAttributes;
    }

	@Override
	public int compareTo(RemovedItemsRecord o) {
		return this.getDescription().compareTo(o.getDescription());
	}
	
	public String toString(){
		return "RemovedItemsRecord [description = " + description +
				", Size = " + df.format(size.getNumber()) + " " + size.getUnit().toString() +
				", Barcode = " + barcode +
				", Removed = " + Integer.toString(removedItems) +
				", CurrentSupply = " + Integer.toString(currentSupply) + "\n";
	}

}
