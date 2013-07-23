package model;

/** A Command that adds an item to the system.
 * 
 * 
 * @author andrew
 *
 */
public class AddItemBatch implements Command{
	
	/** A constructor that builds a list of sub Commands to add an item.
	 * 
	 * @param item	The item being added to the system
	 */
	public AddItemBatch(Item item){}
	
}
