package model;

/** A Command that iterates through its children to add an item.
 * 
 * 
 * @author andrew
 *
 */
public class AddItemToSU extends CompositeCommand{
	
	/** A constructor that builds a list of sub Commands to add an item.
	 * 
	 * @param item	The item being added to the system
	 */
	public AddItemToSU(Item item, StorageUnit storageUnit) 
					throws IllegalArgumentException{

		super();
		
		if (item == null || storageUnit == null) {
            throw new IllegalArgumentException();
        }
		
		
        Container containerOfProductInSU =
                Model.getInstance().getProductAndItemEditor().
                	getContainer(item.getProduct(), storageUnit);
        
        Container targetContainer;
        if (containerOfProductInSU == null) {   
            
        	// append addProduct command
        	commandList.add(new AddNewProduct(item.getProduct(), storageUnit));
        	targetContainer = storageUnit;
        
        }
        else {
            targetContainer = containerOfProductInSU;
        }
        item.setContainer(targetContainer);
        commandList.add(new AddItem(item, targetContainer));		
	}
	
}
