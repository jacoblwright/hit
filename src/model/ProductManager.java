package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/** Manages alterations to all the Products in the system and handles passing the products structure to higher level classes. 
 * 
 * @author Andrew Wilde
 * @version 1.0
 */

public class ProductManager {
	
	/** Maps containers to sets of products. */
	private Map <Container, Set<Product>> productsByContainer;
	
	/** Maps a barcode to a product. */
	private Map <Barcode, Product> productByUPC;
	
	/** Constructs the ProductManager. */ 
	public ProductManager(){}
	
	/** Adds a product to the system
	 * @pre 	product must not already exist in the map
	 * @post	product is added to the set
	 */
	public void addProduct(Product product) throws IllegalArgumentException{}
	
	/** Deletes a Product from the set all of products.
	 * @pre 	product must exist in the set
	 * @pre		product must not have any Items attached to it
	 * @post	removes the Product from the set
	 */
	public void deleteProduct(Product product) throws IllegalArgumentException{}
	
	/** Edits a Product by updating by replacing an older Product with a newer Product.
	 * @pre		oldProduct must exist in the set of products
	 * @post	replaces the oldProduct with the new Product
	 */
	public void editProduct(Product newProduct, Product oldProduct) throws IllegalArgumentException{}
	
	/** Moves a product to a new container as long as that product is not already located in that storage unit.
	 * @pre 		product is not already in the container it is being moved into
	 * @param 		product	the product being moved
	 * @param 		before	the container before the product was moved
	 * @param 		after	the new container that the product is being move to
	 */
	public void moveProduct(Product product, Container before, Container after) throws IllegalStateException{}
	
	/** Returns true if a Product can be added to a particular storage unit. This will return 
	 * true if product is not already contained within that particular storage unit.
	 * 
	 * @param 		product is the Product being examined if it is currently already in a particular storage unit
	 * @param 		storage Product checks whether storage is already contained in productsByContainer			
	 * @return		true if the product can be added to the storage, false otherwise
	 */
	public boolean canAddProduct(Product product, StorageUnit storage){ return false; }
	
	/** Return true if after is a valid Product, false otherwise. A valid Product contains a Barcode
	 * that contains a non-empty upc, a non-empty description, a Quantity that has a
	 * 
	 * @param 		before the value of Product before the edits
	 * @param 		after the value of Product after the edits
	 * @return		return true if after is a valid Product, false otherwise
	 */
	public boolean isProductValid(Product before, Product after){ return false; }
	
	/** Return true if qty has a valid number according to the specified Unit. If the Unit
	 * is COUNT, then the Quantity number must be 1. If the Quantity Unit is any
	 * other enum besides COUNT, number can be any positive float. 
	 * 
	 * @param 		qty is the Quantity being validated
	 * @return		returns true if Quantity is valid
	 */
	public boolean isQuantityValid(Quantity qty){ return false; }
	
	/** Determines if a Product has no associated Items and can therefore be removed.
	 * 
	 * @param 		product is the product being deleted from the system
	 * @param 		container is the Container from which product is being removed
	 * @return		return true if product can be deleted, false otherwise
	 */
	public boolean canDeleteProduct(Product product, Container container){ return false; }
	
	/** Returns a Collection of all Products in a particular container.
	 * 
	 * @param 		container all Products contained within container should be returned
	 * @return		returns all the Products associated with this particular container
	 */
	public Collection getProducts(Container container) throws IllegalArgumentException{ return null; }
	
	/** Returns a Collection of all Products.
	 * 
	 * @return returns all Products in system
	 */
	public Collection getProducts(){ return null; }
	
	/** Returns a Product that pertains to a particular Barcode.
	 * 
	 * @param barcode
	 * @return returns the Product associate to barcode
	 */
	public Product getProductByBarcode(Barcode barcode) throws IllegalArgumentException{ return null; }
	

}
