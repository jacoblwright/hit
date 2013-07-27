package model;

/** A Command that iterates through its children to transfer an item.
 * 
 * @author andrew
 *
 */
public class TransferItemToStorageUnit extends CompositeCommand{
	
	/** A constructor that builds a list of sub Commands to transfer an item to
	 * a new storage unit.
	 * 
	 * @param item	the item being transferred to a new storage unit
	 */
	public TransferItemToStorageUnit(Item item){}

}
