package reports;

import java.util.ArrayList;
import java.util.List;

import model.Quantity;

public class NMonthSupplyContainerRecord implements Record {
	
	String productGroup;
	String storageUnit;
	Quantity threeMonthSupply;
	Quantity currentSupply;
	
	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}

	public Quantity getThreeMonthSupply() {
		return threeMonthSupply;
	}

	public void setThreeMonthSupply(Quantity threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	public Quantity getCurrentSupply() {
		return currentSupply;
	}

	public void setCurrentSupply(Quantity currentSupply) {
		this.currentSupply = currentSupply;
	}

    @Override
    public List<String> getValuesAsStrings() {
    	List<String> containerAttributes = new ArrayList();
    	containerAttributes.add(productGroup);
    	containerAttributes.add(storageUnit);
    	containerAttributes.add(threeMonthSupply.getNumber() + " " + 
    								threeMonthSupply.getUnit().toString());
    	containerAttributes.add(currentSupply.getNumber() + " " + 
    								currentSupply.getUnit().toString());
        return containerAttributes;
    }

}
