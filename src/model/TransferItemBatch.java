package model;

/** A command that performs an item transfer within the system.
 * 
 * @author andrew
 *
 */
public class TransferItemBatch implements Command{
	
	/** A constructor that builds a list of sub Commands to transfer an item to
	 * a new storage unit.
	 * 
	 * @param item	the item being transferred to a new storage unit
	 */
	public TransferItemBatch(Item item){}

}
