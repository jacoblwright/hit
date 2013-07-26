package model;

import java.util.Collection;
import java.util.List;

/** A Command that iterates through its children to add an item.
 * 
 * 
 * @author andrew
 *
 */
public class AddItemsToSU extends CompositeCommand{
	
	/** A constructor that builds a list of sub Commands to add an item.
	 * 
	 * @param items	The list of items being added to the system
	 * 
	 * @pre all items must have the same reference to a specific product
	 */
	public AddItemsToSU(List<Item> items, StorageUnit storageUnit) 
					throws IllegalArgumentException{

		super();
		
		if (items == null || storageUnit == null || items.size() == 0) {
            throw new IllegalArgumentException();
        }
		
        Container containerOfProductInSU =
                Model.getInstance().getProductAndItemEditor().
                	getContainer(items.get(0).getProduct(), storageUnit);
        
        Container targetContainer;
        if (containerOfProductInSU == null) {   
            
        	// append addProduct command
        	commandList.add(new AddNewProduct(items.get(0).getProduct(), storageUnit));
        	targetContainer = storageUnit;
        
        }
        else {
            targetContainer = containerOfProductInSU;
        }
        
        addItemCollection(items, targetContainer);
//        commandList.add(new AddItem(item, targetContainer));		
	}
	
	private void addItemCollection(Collection<Item> items, Container targetContainer){
		for (Item i : items){
			i.setContainer(targetContainer);
			commandList.add(new AddItem(i, targetContainer));
		}
	}
	
}
