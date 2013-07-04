package model;

/**
*
* Item Description:
*	The physical instance of a Product.
*	It describes the product it is associated with, as well as the product container
*	that holds the item.
*
*/
import java.util.Date;
public class Item {
	
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
	
	
	/**
	*
	* @param _container - associated product container
	* @param _expires - date the product expires
	*
	* @pre _container != null, _prod != null
	* 
	* Builds Item instance and initializes members to default values
	*	entry date is captured, sets expires, sets container, barcode generated automatically
	*
	*/
	public Item(Container container, Product product, Date expirationDate) {
		
	}

	/**
	 * 
	 * @return the item's product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * 
	 * @param product
	 *
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 
	 * @return this.tag
	 */
	public Barcode getTag() {
		return tag;
	}

	/**
	 * 
	 * @param tag - must be unique to the product upc
	 */
	public void setTag(Barcode tag) {
		this.tag = tag;
	}

	/**
	 * 
	 * @return entry date of the item
	 */
	public Date getEntryDate() {
		return entryDate;
	}

	/**
	 * 
	 * @return the exit time of the item
	 */
	public Date getExitTime() {
		return exitTime;
	}

	/**
	 * sets the exit time of the item
	 * 
	 * @param exitTime
	 * 
	 * @pre must also be sure that the item has been added to removed items, and the container is null
	 * @post sets item.exitTime
	 */
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	/**
	 * 
	 * @return the date the item expires
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * set the expiration date
	 * 
	 * @param expirationDate
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * 
	 * @return the items Container (storage unit, or product group
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * 
	 * @param container
	 * 
	 * @pre if param container is null, item also needs to be added to removedItems, and needs the exit date set
	 * @post sets this.container
	 */
	public void setContainer(Container container) {
		this.container = container;
	}

	
	
	


}