package model;

/** A Command that iterates through its children to transfer an item.
 * 
 * @author andrew
 *
 */
public class TransferItemToSU extends CompositeCommand{
	
	/** A constructor that builds a list of sub Commands to transfer an item to
	 * a new storage unit.
	 * 
	 * @param item	the item being transferred to a new storage unit
	 */
	public TransferItemToSU(Item item, StorageUnit storageUnit)
					throws IllegalArgumentException {
		super();
		
		if (item == null || storageUnit == null) {
            throw new IllegalArgumentException();
        }
		
        Container containerOfProductInSU =
                Model.getInstance().getProductAndItemEditor().
                		getContainer(item.getProduct(), storageUnit);
        //System.out.println("containerOfProductInSU:" + containerOfProductInSU);
        
        if (containerOfProductInSU == null) {
            
        	commandList.add(new CopyProduct(item.getProduct(), storageUnit));
            
        	commandList.add(new MoveItem(item, storageUnit));   

        }
        else {
        	commandList.add(new MoveItem(item, containerOfProductInSU));
        }
	}

}
