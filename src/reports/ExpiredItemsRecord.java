package reports;

import java.text.SimpleDateFormat;
import java.util.*;

import model.Barcode;
import model.Container;
import model.Item;
import model.Model;
import model.ProductGroup;
import model.StorageUnit;

public class ExpiredItemsRecord implements Record, Comparable<ExpiredItemsRecord> {

	private String description;
	private StorageUnit storageUnit;
	private Container productGroup;
	Date entry;
	Date expire;
	Barcode tag;
	
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StorageUnit getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(StorageUnit storageUnit) {
		this.storageUnit = storageUnit;
	}

	public Container getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(Container productGroup) {
		this.productGroup = productGroup;
	}

	public Date getEntry() {
		return entry;
	}

	public void setEntry(Date entry) {
		this.entry = entry;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public Barcode getTag() {
		return tag;
	}

	public void setTag(Barcode tag) {
		this.tag = tag;
	}

	@Override
    public List<String> getValuesAsStrings() {
		List<String> result = new ArrayList<String>();
		result.add(description);
		result.add(storageUnit.getName());
		result.add(productGroup.getName());
		String entryStr = new SimpleDateFormat("MM-dd-yyyy").format(entry);
		result.add(entryStr);
		String expireStr = new SimpleDateFormat("MM-dd-yyyy").format(expire);
		result.add(expireStr);
		result.add(tag.getBarcode());
        return result;        
    }
	
	public void initialize(Item item, Model model) {
		description = item.getProduct().getDescription();
		storageUnit = model.getContainerManager().getAncestorStorageUnit(item.getContainer());
		if(item.getContainer().getContainer() != null) {
			productGroup = item.getContainer();
		}
		else {
			ProductGroup pg = new ProductGroup();
			pg.setName("");
			productGroup = pg;
		}
		entry = item.getEntryDate();
		expire = item.getExpirationDate();
		tag = item.getTag();
	}

	@Override
	public int compareTo(ExpiredItemsRecord o) {
		if( !this.description.equals(o.description) ) {
			return this.description.compareTo(o.description);
		}
		else {
			return this.entry.compareTo(o.entry);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		result = prime * result + ((expire == null) ? 0 : expire.hashCode());
		result = prime * result
				+ ((productGroup == null) ? 0 : productGroup.hashCode());
		result = prime * result
				+ ((storageUnit == null) ? 0 : storageUnit.hashCode());
		result = prime * result + ((tag == null) ? 0 : Integer.valueOf(tag.getBarcode()));
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
		ExpiredItemsRecord other = (ExpiredItemsRecord) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
			return false;
		if (expire == null) {
			if (other.expire != null)
				return false;
		} else if (!expire.equals(other.expire))
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
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}
	
}
