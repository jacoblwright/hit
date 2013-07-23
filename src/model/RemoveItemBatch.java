package model;

/** A Command that removes an item from the Product and Container, but not the system.
 * 
 * @author andrew
 *
 */
public class RemoveItemBatch extends Command{
	
	/** A constructor that creates a list of sub commands to remove an item.
	 * 
	 * @param item	the item being removed
	 */
	public RemoveItemBatch(Item item){}

}
