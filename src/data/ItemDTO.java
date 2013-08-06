package data;

import java.util.Date;

import model.*;

/**
 * Data transfer object for Item.
 */
public class ItemDTO {
    
	
    private int id; 
	private int productId; 
    private int containerId;
	private String tag; 
	private Date entryDate; 
    private Date exitTime;

    public ItemDTO(){}
    
    public ItemDTO(Item item){
    	this.id = item.getId();
    	this.productId = item.getProduct().getId();
    	this.containerId = item.getContainer().getId();
    	this.tag = item.getTag().getBarcode();
    	this.entryDate = item.getEntryDate();
    	this.exitTime = item.getExitTime();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getContainerId() {
		return containerId;
	}

	public void setContainerId(int containerId) {
		this.containerId = containerId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
    
	/** Creates a new item with a null container and product pointer which must be set later
	 * 
	 * @return item where container == null and product == null
	 */
    public Item createItem(){
    	Item newItem = new Item(null, null, this.entryDate, new Barcode(this.tag));
    	newItem.setExitTime(this.exitTime);
    	newItem.setId(this.id);
    	return newItem;
    }
    
}
