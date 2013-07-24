package model;

/** A sub command that moves an item to a new storage unit
 * 
 * @author andrew
 *
 */
public class MoveItem extends LeafCommand{
	
	/** A constructor that sets the item that is being moved and the container it is
	 * being moved to.
	 * 
	 * @param item	the item being moved
	 * @param container	the container the item is being move into
	 */
	public MoveItem(Item item){}

	/** Moves the item to its new container
	 * 
	 */
	public void execute(){}
	
	/** Moves the item back to its old container
	 * 
	 */
	public void unexecute(){}
}
