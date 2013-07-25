package model;


/** A sub command that adds an item to the system.
 * 
 * @author andrew
 *
 */
public class AddItem implements ILeafCommand{
	
	Item item;
	Container target;
	/** A constructor that sets the item being added to the system.
	 * 
	 * @param item	the Item being added to the system.
	 */
	public AddItem(Item item, Container target)
				throws IllegalArgumentException {
		if (item == null || target == null){
			throw new IllegalArgumentException("null param");
		}
		
		this.item = item;
		this.target = target;
	}
	
	/** Adds item to the system
	 * 
	 */
	public void execute(){
		Model.getInstance().getItemManager().addItem(item);
	}
	
	/** Removes item from the system
	 * 
	 */
	public void unexecute(){}

}
