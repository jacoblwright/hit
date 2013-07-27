package model;

/** A Command that iterates through its children to remove an item.
 * 
 * @author andrew
 *
 */
public class RemoveItemFromStorageUnit extends CompositeCommand{
	
	/** A constructor that creates a list of sub commands to remove an item.
	 * 
	 * @param item	the item being removed
	 */
	public RemoveItemFromStorageUnit(Item item){}

}
