package model;

/** A Command that iterates through its children to remove an item.
 * 
 * @author andrew
 *
 */
public class RemoveItemFromSU extends CompositeCommand{
	
	Item item;
	/** A constructor that creates a list of sub commands to remove an item.
	 * 
	 * @param item	the item being removed
	 */
	public RemoveItemFromSU(Item item){
		super();
		
		this.item = item;
		commandList.add(new RemoveItem(item));
	}

}
