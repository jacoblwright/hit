package model;

import java.util.Iterator;
import java.util.Set;

/** Manages alterations to all the Products in the system and handles passing the products structure to higher level classes 
 * 
 * @author andrew
 * @version 1.0
 */

public class ProductManager {
	
	/** the set of all products in the system */
	private Set<Product> products;
	
	/** returns the iterator to the set of all products in the system
	 * @pre none
	 * @return an iterator to the set of all products in the system
	 */
	public Iterator<Product> getAllProducts(){ return null; }
	
	/** Adds a product to the system
	 * @pre 	product must not already exist in the set
	 * @post	product is added to the set
	 */
	public void addProduct(Product product){}
	
	/** deletes a Product from the set all of products
	 * @pre 	product must exist in the set
	 * @pre		product must not have any Items attached to it
	 * @post	removes the Product from the set
	 */
	public void deleteProduct(Product product){}
	
	/** edits a Product by updating by replacing an older Product with a newer Product
	 * @pre		oldProduct must exist in the set of products
	 * @post	replaces the oldProduct with the new Product
	 */
	public void editProduct(Product newProduct, Product oldProduct){}

}
