package model;

/** A sub command that copies a product to a particular container.
 * 
 * @author andrew
 *
 */
public class CopyProduct implements ILeafCommand{

	Product product;
	Container container;
	
	/** A constructor that sets the product being copied to container
	 * 
	 * @param product	the Product being copied into container
	 * @param container	the Container that product is being copied into
	 */
	public CopyProduct(Product product, Container container)
				throws IllegalArgumentException{
		
		if (product == null || container == null){
			throw new IllegalArgumentException("null param");
		}
			
		this.product = product;
		this.container = container;
	}
	
	/** Adds product to container
	 * 
	 */
	public void execute(){
		Model.getInstance().getProductManager().addProductToContainer(
                product, container);
	}
	
	/** Removes product from container
	 * 
	 */
	public void unexecute(){
		Model.getInstance().getProductManager().removeProductFromContainer(product, container);
	}
	
}
