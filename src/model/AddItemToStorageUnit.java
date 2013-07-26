package model;

/** A Command that iterates through its children to add an item.
 * 
 * 
 * @author andrew
 *
 */
public class AddItemToStorageUnit extends CompositeCommand{
	
	/** A constructor that builds a list of sub Commands to add an item.
	 * 
	 * @param item	The item being added to the system
	 */
	public AddItemToStorageUnit(Item item){}
	
}
