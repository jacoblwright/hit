package reports;

import gui.common.SizeUnits;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Container;
import model.Model;
import model.ProductGroup;
import model.Quantity;
import model.StorageUnit;

public class NMonthSupplyContainerRecord implements 
					Record, Comparable<NMonthSupplyContainerRecord> {
	
	private Container productGroup;
	private StorageUnit storageUnit;
	private Quantity threeMonthSupply;
	private Quantity currentSupply;
	
    public Container getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(Container productGroup) {
		this.productGroup = productGroup;
	}

	public StorageUnit getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(StorageUnit storageUnit) {
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
		List<String> containerAttribute = new ArrayList<String>();
		containerAttribute.add(productGroup.getName());
		containerAttribute.add(storageUnit.getName());
		containerAttribute.add(threeMonthSupply.getQuantityString());
		containerAttribute.add(currentSupply.getQuantityString());
        return containerAttribute;        
    }

	@Override
	public int compareTo(NMonthSupplyContainerRecord arg0) {
		return this.productGroup.compareTo(arg0.productGroup);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentSupply == null) ? 0 : currentSupply.hashCode());
		result = prime * result
				+ ((productGroup == null) ? 0 : productGroup.hashCode());
		result = prime * result
				+ ((storageUnit == null) ? 0 : storageUnit.hashCode());
		result = prime
				* result
				+ ((threeMonthSupply == null) ? 0 : threeMonthSupply.hashCode());
		return result;
	}

	public void simpleInitialize(Container productGroup, float scale, Model model) {
		setProductGroup(productGroup);
		setStorageUnit(model.getContainerManager().getAncestorStorageUnit(productGroup));
		float number = ((ProductGroup) productGroup).getThreeMonthSupply().getNumber();
		Enum<SizeUnits> unit = ((ProductGroup) productGroup).getThreeMonthSupply().getUnit();
		setThreeMonthSupply(new Quantity(scale * number, unit));
	}


}
