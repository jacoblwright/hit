package reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Container;
import model.Quantity;
import model.StorageUnit;

public class NMonthSupplyContainerRecord implements Record, Comparable<NMonthSupplyContainerRecord> {
	
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
		List<String> result = new ArrayList<String>();
		result.add(productGroup.getName());
		result.add(storageUnit.getName());
		result.add(threeMonthSupply.getQuantityString());
		result.add(currentSupply.getQuantityString());
        return result;        
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NMonthSupplyContainerRecord other = (NMonthSupplyContainerRecord) obj;
		if (currentSupply == null) {
			if (other.currentSupply != null)
				return false;
		} else if (!currentSupply.equals(other.currentSupply))
			return false;
		if (productGroup == null) {
			if (other.productGroup != null)
				return false;
		} else if (!productGroup.equals(other.productGroup))
			return false;
		if (storageUnit == null) {
			if (other.storageUnit != null)
				return false;
		} else if (!storageUnit.equals(other.storageUnit))
			return false;
		if (threeMonthSupply == null) {
			if (other.threeMonthSupply != null)
				return false;
		} else if (!threeMonthSupply.equals(other.threeMonthSupply))
			return false;
		return true;
	}


}
