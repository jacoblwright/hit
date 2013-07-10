package model;

/**
*
* Item Manager Description:
*	Creates and manages Items. This involves adding, moving, and removing items.
*
*/

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
	private Map<String, Set<Item>> removedItemsByDate;
	
	private Set<Item> removedItems;
	
	private DateFormat dateFormat;
	
	/**
	* Constructs the ItemManager
	*/
	ItemManager() {
		itemsByContainer = new HashMap<Container, Set<Item>>();
		itemByTag = new HashMap<Barcode, Item>();
		removedItems = new HashSet<Item>();
		removedItemsByDate = new HashMap<String, Set<Item>>();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	}
	
	/** gets items by container and product
	 * 
	 * @param container
	 * @param product
	 * 
	 * @pre container exists, product exists
	 * @return collection of all items inside container that are of the type product
	 */
	public Collection<Item> getItems(Container container, Product product) {
		if ( container == null ){
			throw new IllegalArgumentException("param: container is null");
		}
		if ( product == null) {
			throw new IllegalArgumentException("param: product is null");
		}
		
		
		Collection<Item> ret = new HashSet<Item>();
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
	public Collection<Item> getItems(Container container) {
		if (itemsByContainer.containsKey(container)){
			return itemsByContainer.get(container);
		}
		else {
			return new HashSet<Item>(); // returns empty collection if no key found.
		}
	}
	
	/** gets all items in the system
	 * 
	 * @return Collection of all items in any storage unit
	 */
	public Collection<Item> getItems() { 
		Collection<Item> ret = new ArrayList<Item>();
		
		// iterate over all keys of itemsByContainer and appends them to return set
		
	    for (Container key : itemsByContainer.keySet()) {
	        ret.addAll(itemsByContainer.get(key));
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

		if (itemToAdd == null){
			throw new IllegalArgumentException("param itemToAdd is null");
		}
		else if (!canAddItem(itemToAdd, itemToAdd.getContainer())) {
			throw new IllegalArgumentException("Item cannot be added.");
		}
		
		// starts new collection if index does not have container key
		if (!itemsByContainer.containsKey(itemToAdd.getContainer())) {
			itemsByContainer.put(itemToAdd.getContainer(), new HashSet<Item>());
		}
		
		itemsByContainer.get(itemToAdd.getContainer()).add(itemToAdd);
		
		if (!itemByTag.containsKey(itemToAdd.getTag())){
			itemByTag.put(itemToAdd.getTag(), itemToAdd);
		}
	}

	/**
	 * can add item
	 * 
	 * @return false if item not found in Storage Unit or removedItems, false otherwise
	 */
	public boolean canAddItem(Item item, Container storageUnit){
		
		if (storageUnit == null){
			return false;
		}
		
		if (item == null){
			return false;
		}
		
		if (itemsByContainer.containsKey(storageUnit)) {
			Collection<Item> tmp = getItems(storageUnit);
			boolean result = !tmp.contains(item); // TODO: the storage unit
			result = result && !removedItems.contains(item);
			return result;
		}
		else {
			return true;
		}
	
	}
	
	/**
	* Updates indexes for the move.
	* 
	* @pre itemToMove.product exists (only) in the target container
	* @post itemToMove.container = target
	* @post updates indexes
	* 
	* @throws IllegalStateException if pre-conditions are not met
	* @throws IllegalArgumentExcpetion if itemToMove is bad
	*/
	public void moveItem(Item itemToMove, Container target) {
		
		// remove item from itemsByContainer index
		itemsByContainer.get(itemToMove.getContainer()).remove(itemToMove);
		
		// change container pointer since addItem can't
		itemToMove.setContainer(target);
		
		// add it back to the appropriate container
		addItem(itemToMove);
	}
	
	
	/** Removes the item from any container but keeps track of it in the item history.
	 * 
	 * @param itemToRemove - item to be removed
	 * 
	 * @pre itemToRemove.container != null, !removedItems.contains(itemToMove)
	 * @pre itemToMove.exitTime == null
	 * @post captures and sets itemToRemove.exitTime
	 * @post sets itemToRemove.container to null
	 * @post updates to removedItemsByDate
	 * 
	 * @throws IllegalStateException if preconditions are not met
	 * @throws IllegalArgumentException if itemToRemove is bad
	 * 
	 */
	public void removeItem(Item itemToRemove) throws IllegalArgumentException {
		
		if (itemToRemove == null) {
			throw new IllegalArgumentException("param: itemToRemove was null");
		}
		// update container index
		itemsByContainer.get(itemToRemove.getContainer()).remove(itemToRemove);
		Date exitDate = new Date();
		String exitDate_str = dateFormat.format(exitDate);
		
		itemToRemove.setExitTime(exitDate);
		itemToRemove.setContainer(null);
		
		if (removedItemsByDate.containsKey(exitDate_str)){
			removedItemsByDate.get(exitDate_str).add(itemToRemove);
		}
		else {
			Set<Item> newSet = new HashSet<Item>();
			newSet.add(itemToRemove);
			removedItemsByDate.put(exitDate_str, newSet);
		}
		
		removedItems.add(itemToRemove);
	}
	
	/**
	 * 
	 * @param oldItem - item before edit
	 * @param newItem - item after edit
	 * 
	 * @throws IllegalArgumentException
	 */
	public void editItem(Item before, Item after) throws IllegalArgumentException {
		if (canEditItem(before, after)){
			before.setEntryDate(after.getEntryDate());; // TODO: will this work?
		}
		else{
			throw new IllegalArgumentException("cannot complete item edit: after is invalid");
		}
	}
	
	
	/** Validates that the after item is valid, so that the edit will happen successfully.
	 * 
	 * @param before
	 * @param after
	 * @return true if after's only modification is the date
	 */
	public boolean canEditItem(Item before, Item after){
		
		if ( ! before.getContainer().equals(after.getContainer()) ) {
			return false;
		}
		
		else if ( ! before.getProduct().equals(after.getProduct()) ) {
			return false;
		}
		
		else if ( ! before.getTag().equals(after.getTag()) ) {
			return false;
		}
		else {
			return true;
		}
		
		
	}
	
	
	/** 
	*
	* @pre barcode exists and is unique
	* @return Item associated with tag
	* 
	* @throws IllegalArgumentException if lookup does not contain tag
	*/
	public Item getItemByTag(Barcode tag){
		return itemByTag.get(tag);
	}
	
	/** checks all the items barcode's for a match
	 * 
	 */
	public boolean isTagUnique(String barcode) {
		
	}
	
	/**
	 * @return all items removed on a specific date
	 */
	public Collection<Item> getRemovedItems(Date exitTime){
		String exitTime_str = dateFormat.format(exitTime);
		return removedItemsByDate.get(exitTime_str);
	}
	
	/**
	 * @return all items removed
	 */
	public Collection<Item> getRemovedItems(){
		return removedItems;
	}
	
}