package model;

/** A sub command that copies a product to a particular container.
 * 
 * @author andrew
 *
 */
public class CopyProduct implements Command{

	/** A constructor that sets the product being copied to container
	 * 
	 * @param product	the Product being copied into container
	 * @param container	the Container that product is being copied into
	 */
	public CopyProduct(Product product, Container container){}
	
	/** Adds product to container
	 * 
	 */
	public void execute(){}
	
	/** Removes product from container
	 * 
	 */
	public void unexecute(){}
	
}
