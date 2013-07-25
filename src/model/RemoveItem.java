package model;

/** The sub command that removes an item from its  container
 * 
 * @author andrew
 *
 */
public class RemoveItem implements ILeafCommand{

	Item item;
	Container prevContainer;
	/** A constructor that sets the Item being removed
	 * 
	 * @param item	the Item being removed from its container
	 */
	public RemoveItem(Item item) throws IllegalArgumentException{
		this.item = item;
		
		if (item.getContainer() == null){
			throw new IllegalArgumentException(
					"item container already null before removing");
		}
		
		this.prevContainer = item.getContainer();
	}
	
	/** Removes the item from the container
	 * 
	 */
	public void execute(){
		Model.getInstance().getItemManager().removeItem(item);
	}
	
	/** Adds the item back to its container
	 * 
	 */
	public void unexecute(){
		
	}
}
