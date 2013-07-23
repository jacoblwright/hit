package model;

/** The sub command that removes an item from its  container
 * 
 * @author andrew
 *
 */
public class RemoveItem extends Command{

	/** A constructor that sets the Item being removed
	 * 
	 * @param item	the Item being removed from its container
	 */
	public RemoveItem(Item item){}
	
	/** Removes the item from the container
	 * 
	 */
	public void execute(){}
	
	/** Adds the item back to its container
	 * 
	 */
	public void unexecute(){}
}
