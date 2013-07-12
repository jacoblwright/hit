package model;

import java.util.Date;

/**	The physical instance of a Product.
*	It describes the product it is associated with, as well as the product container
*	that holds the item.
*
*/

public class Item extends Entity implements Comparable<Item> {
	
	/** Points to the product that describes the item. */
	private Product product;
	
	/** unique barcode for the item. NOT associated with the product's manufacturers barcode */
	private Barcode tag;
	
	/** date the item entered */
	private Date entryDate;
	
	/** time the item was removed */
	private Date exitDate;
	
	/** date the item was removed */
	private Date exitTime;
	
	/** expiration date of the item */
	private Date expirationDate;
	
	/** pointer to the product container that holds the item */
	private Container container;
	
	
	/** Builds Item instance and initializes members to default values
	*	entry date is captured, sets expires, sets container, barcode generated automatically
	*
	*
	* @param container - associated product container
	* @param product - associated product
	* @param expirationDate - date the item expires
	* @param tag - associated tag barcode
	*
	* @pre _container != null, _prod != null
	*
	*/
	public Item(Container container, Product product, Date expirationDate, Barcode tag)  {
		assert container != null;
		assert product != null;
		assert expirationDate != null;
		assert tag != null;
		
		this.container = container;
		this.product = product;
		this.expirationDate = expirationDate;
		this.tag = tag; // generates unique barcode on initliazation
		this.entryDate = new Date();
		
	}

	/** Gets the item's product.
	 * 
	 * @return the item's product
	 */
	public Product getProduct() {
		assert true;
		
		return product;
	}

	/** Sets the item's product.
	 * 
	 * @param product
	 *
	 */
	public void setProduct(Product product) {
		assert true;
		
		this.product = product;
	}

	/** Gets the item's tag barcode
	 * 
	 * @return this.tag
	 */
	public Barcode getTag() {
		assert true;
		
		return tag;
	}

	/** Sets the item's unique tag barcode
	 * 
	 * @param tag
	 * 
	 * @pre must be unique to the product upc
	 * @post sets item tag to param tag
	 */
	public void setTag(Barcode tag) {
		assert true;
		
		this.tag = tag;
	}
	/** Sets the item's entry date
	 * 
	 * sets entry date
	 */
	public void setEntryDate(Date entryDate) {
		assert true;
		
		this.entryDate = entryDate;
	}
	/** Gets the entry date of the item.
	 * 
	 * @return entry date of the item
	 */
	public Date getEntryDate() {
		assert true;
		
		return entryDate;
	}

	/** Gets the exit time of the item.
	 * 
	 * @return the exit time of the item
	 */
	public Date getExitTime() {
		assert true;
		
		return exitTime;
	}

	/**
	 * Sets the exit time of the item.
	 * 
	 * @param exitTime
	 * 
	 * @pre must also be sure that the item has been added to removed items
	 * @pre the container is null
	 * @post sets item.exitTime
	 */
	public void setExitTime(Date exitTime) {
		assert true;
		
		this.exitTime = exitTime;
	}

	/** Gets the item's expiration date.
	 * 
	 * @return the date the item expires
	 */
	public Date getExpirationDate() {
		assert true;
		
		return expirationDate;
	}

	/**
	 * Set the item's expiration date.
	 * 
	 * @param expirationDate
	 */
	public void setExpirationDate(Date expirationDate) {
		assert true;
		
		this.expirationDate = expirationDate;
	}

	/** Gets the item's container.
	 * 
	 * @return the items Container (storage unit, or product group
	 */
	public Container getContainer() {
		assert true;
		
		return container;
	}

	/** Sets item's container.
	 * 
	 * @param container
	 * 
	 * @pre if param container is null
	 * @pre item also needs to be added to removedItems
	 * @pre needs the exit date set
	 * @post sets this.container
	 */
	public void setContainer(Container container) {
		assert true;
		
		this.container = container;
	}

	@Override
	public boolean equals(Object obj) {
		assert true;
		
		if ( this == obj ) {
			return true;
		}
		
		if ( !( obj instanceof Item ) ){
			return false;
		}
		
		Item item = (Item) obj;
		return this.getTag().getBarcode() == item.getTag().getBarcode();
	}
	
	@Override
	public int compareTo(Item other) {
	    
	    return entryDate.compareTo(other.entryDate);
	    
	}

	@Override
	public String toString() {
		return "Item [product=" + product + ", tag=" + tag + ", entryDate="
				+ entryDate + ", exitDate=" + exitDate + ", exitTime="
				+ exitTime + ", expirationDate=" + expirationDate
				+ ", container=" + container + "]";
	}
	

	
	
	


}