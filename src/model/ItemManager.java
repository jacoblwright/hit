package model;


import gui.item.ItemData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.*;

/**
 * Manages all of the item manipulations, and indexing.
 * 
 * @author Nick
 *
 */
@SuppressWarnings("serial")
public class ItemManager extends Observable implements Serializable {

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
		itemsByContainer = new TreeMap<Container, Set<Item>>();
		itemByTag = new TreeMap<Barcode, Item>();
		removedItems = new TreeSet<Item>();
		removedItemsByDate = new HashMap<String, Set<Item>>();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	}
	
	/** Gets all items in the given container and is associated with the given product.
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
		
	
		Collection<Item> ret = new TreeSet<Item>();
		for(Item item : getItems(container)){
			if (item.getProduct().equals(product)) {
				ret.add(item);
			}
		}
		return ret;
	}
	
	/** Gets items in the given container.
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
			return new TreeSet<Item>(); // returns empty collection if no key found.
		}
	}
	
	/** Gets all the items in the system.
	 * 
	 * @return Collection of all items in any storage unit
	 */
	public Collection<Item> getItems() { 
		Collection<Item> ret = new ArrayList<Item>();
		
		// iterate over all keys of itemsByContainer and appends them to return set
		
	    for (Container key : itemsByContainer.keySet()) {
	    	Collection<Item> tmp = itemsByContainer.get(key);
	    	if ( tmp != null ){
	    		ret.addAll(tmp);
	    	}
	    }
		return ret;
	}
	
	/** Updates the item manager indexes.
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
			itemsByContainer.put(itemToAdd.getContainer(), new TreeSet<Item>());
		}
		
		itemsByContainer.get(itemToAdd.getContainer()).add(itemToAdd);
		
		if (!itemByTag.containsKey(itemToAdd.getTag())){
			itemByTag.put(itemToAdd.getTag(), itemToAdd);
		}
		
		notify(null);
	}

	/**
	 * Checks whether or not the item can be added.
	 * 
	 * @return false if item not found in Storage Unit or removedItems, false otherwise
	 */
	public boolean canAddItem(Item item, Container container){
		
		if (container == null){
			return false;
		}
		
		if (item == null){
			return false;
		}
		
		if (itemsByContainer.containsKey(container)) {
			Collection<Item> tmp = getItems(container);
			/*
			System.out.println(item);
			System.out.println(container);
			System.out.println(!tmp.contains(item));
			System.out.println(!removedItems.contains(item));
			System.out.println(getItemByTag(item.getTag()) == null);
			System.out.println(getItemByTag(item.getTag()));
			System.out.println("----");
			*/
			return 	!tmp.contains(item) &&
					!removedItems.contains(item);
		}
		else {
			return true;
		}
	
	}
	
/**
* Updates indexes from the move.
* 
* @pre itemToMove.product exists (only) in the target container
* @post itemToMove.container = target
* @post updates indexes
* 
*/
public void moveItem(Item itemToMove, Container target) {
	
	// remove item from itemsByContainer index
	Collection<Item> tmp = itemsByContainer.get(itemToMove.getContainer());
	tmp.remove(itemToMove);
	
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
			Set<Item> newSet = new TreeSet<Item>();
			newSet.add(itemToRemove);
			removedItemsByDate.put(exitDate_str, newSet);
		}
		
		removedItems.add(itemToRemove);
		
		notify(null);
	}
	
	/** Change only to the item's entry date.
	 * 
	 * @param before - item before edit
	 * @param after - item after edit
	 * 
	 * @throws IllegalArgumentException
	 */
	public void editItem(Item before, Item after) throws IllegalArgumentException {
		if (canEditItem(before, after)){
			before.setEntryDate(after.getEntryDate());
		}
		else{
			throw new IllegalArgumentException("cannot complete item edit: after is invalid");
		}
		
		notify(after);
	}
	
	
	/** Checks that only the entry date is being changed.
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
	
	
	/**  Returns the item associated with the given tag.
	*
	* @pre barcode is unique
	* @return Item associated with tag
	* 
	* @throws IllegalArgumentException if lookup does not contain tag
	*/
	public Item getItemByTag(Barcode tag){
		return itemByTag.get(tag);
	}
	
	/** Checks all the items barcode's for a match.
	 * 
	 * @param barcode - string representing a valid barcode
	 * @return boolean whether or not it is unique
	 */
	public boolean doesTagExist(String barcode) {
		
		for (Item item : getItems()){
			if (item.getTag().getBarcode() == barcode){
				return false;
			}
		}
		return true;
	}
	
	/** Gets all items removed on the day of the time given. 
	 * 
	 * @return all items removed on a specific date
	 */
	public Collection<Item> getRemovedItems(Date exitTime){
		String exitTime_str = dateFormat.format(exitTime);
		return removedItemsByDate.get(exitTime_str);
	}
	
	/** Gets all the removed item's ever.
	 * 
	 * @return all items removed
	 */
	public Collection<Item> getRemovedItems(){
		return removedItems;
	}
	
	private void notify(Item changer) {
		ChangeObject hint = new ChangeObject();
		hint.setChangeType(ChangeType.ITEM);
		if (changer != null){
			hint.setSelectedData(new ItemData(changer));
		}
		setChanged();
		notifyObservers(hint);
	}
	
//	public void clearAll(){
//		itemsByContainer.clear();
//		itemByTag.clear();
//		removedItemsByDate.clear();
//		removedItems.clear();
//	}
	
}