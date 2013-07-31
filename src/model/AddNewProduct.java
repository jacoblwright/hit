package model;

/** A sub command that adds a product to a particular container.
 * 
 * @author andrew
 *
 */
public class AddNewProduct implements ILeafCommand{
	
	Product product;
	Container container;
	/** A constructor that sets the Product being added and sets the Container product
	 * is being added to.
	 * 
	 * @param product	the Product being added to container
	 * @param container		the Container to which product is being added
	 */
	public AddNewProduct(Product product, Container container)
						throws IllegalArgumentException {
		
		if (product == null || container == null){
			throw new IllegalArgumentException("null paramater");
		}
		
		this.product = product;
		this.container = container;
	}
	
	/** Adds new Product to the system in the designated Container as set in the constructor
	 * 
	 */
	public void execute(){
		Model.getInstance().getProductManager().addNewProduct(
                product, container);
	}
	
	/** Removes a Product from the system and detaches it from the container
	 * 
	 */
	public void unexecute(){
		Model.getInstance().getProductManager().deleteProductFromSystem(product);
	}

}
