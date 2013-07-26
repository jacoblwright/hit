package model;

/** A sub command that moves an item to a new storage unit
 * 
 * @author andrew
 *
 */
public class MoveItem implements ILeafCommand{
	
	Item item;
	Container target;
	Container previous;
	
	/** A constructor that sets the item that is being moved and the container it is
	 * being moved to.
	 * 
	 * @param item	the item being moved
	 * @param container	the container the item is being move into
	 */
	public MoveItem(Item item, Container target)
				throws IllegalArgumentException {
		if (item == null || target == null){
			throw new IllegalArgumentException("null param");
		}
		
		this.item = item;
		this.previous = item.getContainer();
		this.target = target;
	}

	/** Moves the item to its new container
	 * 
	 */
	public void execute(){
		Model.getInstance().getItemManager().moveItem(item, target);
	}
	
	/** Moves the item back to its old container
	 * 
	 */
	public void unexecute(){
		Model.getInstance().getItemManager().moveItem(item, previous);
	}
}
