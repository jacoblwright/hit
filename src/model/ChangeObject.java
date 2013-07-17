package model;

import gui.inventory.ProductContainerData;
import gui.item.ItemData;
import gui.product.ProductData;

public class ChangeObject {
	private ChangeType changeType;
	private ProductContainerData productContainerData;
	private ProductData productData;
	private ItemData itemData;
	
	public ChangeType getChangeType() {
		return changeType;
	}
	
	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}
	
	public ProductContainerData getProductContainerData() {
		return productContainerData;
	}
	
	public void setProductContainerData(ProductContainerData productContainerData) {
		this.productContainerData = productContainerData;
	}
	
	public ProductData getProductData() {
		return productData;
	}
	
	public void setProductData(ProductData productData) {
		this.productData = productData;
	}
	
	public ItemData getItemData() {
		return itemData;
	}
	
	public void setItemData(ItemData itemData) {
		this.itemData = itemData;
	}
}
