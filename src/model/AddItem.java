package model;


/** A sub command that adds an item to the system.
 * 
 * @author andrew
 *
 */
public class AddItem extends LeafCommand{
	
	/** A constructor that sets the item being added to the system.
	 * 
	 * @param item	the Item being added to the system.
	 */
	public AddItem(Item item, Container target){}
	
	/** Adds item to the system
	 * 
	 */
	public void execute(){}
	
	/** Removes item from the system
	 * 
	 */
	public void unexecute(){}

}
