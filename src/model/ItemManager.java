package model;

/**
*
* Item Manager Description:
*	Creates and manages Items. This involves adding, moving, and removing items.
*
*/

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.Set;

public class ItemManager {

	/**
	 * Index for quick item lookup by container.
	 */
	private Map<Container, Set<Item>> itemsByContainer;
	
	/**
	* Index for quick item lookup from the tag.
	*/
	private Map<Barcode, Item> itemByTag;
	
	/**
	 * Indexes the items that were removed on a specific date.
	 */
	private Map<Date, Set<Item>> removedItems;
	
	/**
	* Constructs the ItemManager
	*/
	ItemManager() {
		itemsByContainer = new HashMap();
		itemByTag = new HashMap();
		removedItems = new HashMap();
	}
	
	/** gets items by container and product
	 * 
	 * @param container
	 * @param product
	 * 
	 * @pre container exists, product exists
	 * @return collection of all items inside container that are of the type product
	 */
	public Collection getItems(Container container, Product product) {
		Collection ret = new HashSet();
		for(Iterator<Item> i = getItems(container).iterator(); i.hasNext(); ){
			Item item = i.next();
			if (item.getProduct() == product) {
				ret.add(item);
			}
		}
		return ret;
	}
	
	/** gets items by container
	 * 
	 * @param container
	 * 
	 * @pre container exists
	 * @return collection of all items inside that container
	 */
	public Collection getItems(Container container) {
		return itemsByContainer.get(container);
	}
	
	/** gets all items in the system
	 * 
	 * @return Collection of all items in any storage unit
	 */
	public Collection getItems() { 
		Collection ret = new HashSet();
		
		// iterate over all keys of itemsByContainer and appends them to return set
		Iterator it = itemsByContainer.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        ret.add(pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return ret;
	}
	
	/** updates item manager indexes
	* 
	* @pre canAddItem() == true
	* @post updates itemByTag
	* @post updates itemByContainer
	* 
	* @throws IllegalArgumentException if !canAddItem()
	*/
	public void addItem(Item itemToAdd) throws IllegalArgumentException {
		if (!canAddItem(itemToAdd, itemToAdd.getContainer())) {
			throw new IllegalArgumentException("Item cannot be added.");
		}
		
		itemsByContainer.get(itemToAdd.getContainer()).add(itemToAdd);
		itemByTag.put(itemToAdd.getTag(), itemToAdd);
	}

	/**
	 * can add item
	 * 
	 * @return false if item not found in Storage Unit, false otherwise
	 */
	public boolean canAddItem(Item item, Container storageUnit){
		return getItems(storageUnit).contains(item);
	}
	
	/**
	* Updates indexes for the move.
	* 
	* @pre itemToMove.product exists (only) in the target container
	* @post itemToMove.container = target, itemToMove.storageUnit = target's storage unit
	* 
	* @throws IllegalStateException if pre-conditions are not met
	* @throws IllegalArgumentExcpetion if itemToMove is bad
	*/
	public void moveItem(Item itemToMove, Container target) throws IllegalStateException, IllegalArgumentException {
	
	}
	
	
	/** Removes the item from any container but keeps track of it in the item history.
	 * 
	 * @param itemToRemove - item to be removed
	 * 
	 * @pre itemToRemove.container != null, !removedItems.contains(itemToMove), itemToMove.exitTime == null
	 * @post captures and sets itemToRemove.exitTime, sets itemToRemove.container to null, adds to removedItems, updates indexItemsByRemovalDate
	 * 
	 * @throws IllegalStateException if preconditions are not met
	 * @throws IllegalArgumentException if itemToRemove is bad
	 * 
	 */
	public void removeItem(Item itemToRemove) throws IllegalStateException, IllegalArgumentException {
	
	}
	
	/**
	 * 
	 * @param oldItem - item before edit
	 * @param newItem - item after edit
	 * 
	 * @throws IllegalArgumentException
	 */
	public void editItem(Item oldItem, Item newItem) throws IllegalArgumentException {
	
	}
	
	
	/** Validates that the after item is valid, so that the edit will happen successfully.
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public boolean canEditItem(Item before, Item after){
		return false;
	}
	
	
	/** 
	*
	* @pre barcode exists and is unique
	* @return Item associated with tag
	* 
	* @throws IllegalArgumentException if lookup does not contain tag
	*/
	public Iterator<Item> getItemByTag(Barcode tag){
		return null;
	}
	
	/**
	 * @return all items removed on a specific date
	 */
	public Iterator<Item> getRemovedItems(Date exitTime){
		return null;
	}
	
	
}