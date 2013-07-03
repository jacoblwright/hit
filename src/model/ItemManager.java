/**
*
* Item Manager Description:
*	Creates and manages Items. This involves adding, moving, and removing items.
*
*/

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Date;

public class ItemManager {

	/**
	* contains all the current items in the system
	*/
	private List<Item> items;

	/**
	* index for quick item lookup from barcode
	*/
	private Map<Barcode, Item> indexItemByTag;
	
	/**
	* index for quick item lookup from barcode
	*/
	private Map<Barcode, Item> indexItemByUPC;
	
	
//	/**
//	* stores the items that have been removed
//	*/
//	private List<Item> removedItems;
	
	/**
	 * indexes the items that were removed on a specific date
	 */
	private Map<Date, List<Item>> itemsRemovedOn;
	
	/**
	* @returns item manger
	*/
	ItemManager() {
	
	}
	
	/**
	* creates item instance and provides date it expires
	* 
	* @pre item does not exist in another storage unit nor in current storage unit, itemToAdd.entryDate == null
	* @post !items.isEmpty(), itemToAdd.container = container, itemToAdd.prodcut = product, itemToAdd.expirationDate = expirationDate
	* 
	* @throws IllegalStateException if pre-conditions are not met
	*/
	public void addItem(Item itemToAdd, Container container, Product product, Date expirationDate) throws IllegalStateException {
	
	}
	
	/**
	* Places item into desired Product Container, updates indexes.
	* 
	* @pre itemToMove.product exists (only) in the target container
	* @post itemToMove.container = target, itemToMove.storageUnit = target's storage unit
	* 
	* @throws IllegalStateException if pre-conditions are not met
	*/
	public void moveItem(Item itemToMove, Container target) throws IllegalStateException {
	
	}
	
	
	/**
	 * 
	 * @param itemToMove - item to be removed
	 * 
	 * @pre itemToRemove.container != null, !removedItems.contains(itemToMove), itemToMove.exitTime == null
	 * @post captures and sets itemToRemove.exitTime, sets itemToRemove.container to null, adds to removedItems, updates indexItemsByRemovalDate
	 */
	public void removeItem(Item itemToRemove) {
	
	}
	
	/**
	 * 
	 * @param oldItem - item before edit
	 * @param newItem - item after edit
	 */
	public void editItem(Item oldItem, Item newItem){
	
	}
	
	/**
	 * can edit item
	 */
	public boolean canEditItem(Item oldItem, Item newItem){
		
	}
	
	
//	/**
//	* gets items associated with that upc barcode
//	*
//	* @pre barcode exists
//	*/
//	public Iterator<Item> getItemsByUPC(Barcode upc){
//	
//	}
	
	/**
	* gets item by tag barcode
	*
	* @pre barcode exists
	*/
	public Iterator<Item> getItemByTag(Barcode tag){
	
	}
	
	/**
	 * @return all the removed items
	 */
	public Iterator<Item> getAllRemovedItems(){
	
	}
	
	/**
	 * @return all items removed on a specific date
	 */
	public Iterator<Item> getItemsRemovedOn(Date exitTime){
		
	}
}