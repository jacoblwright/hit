package model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * performs operations that are shared between items and products, such as moving items
 * 
 * Wraps all of the item manager, and product manager methods
 * 
 * @author Nick
 *
 */

public class ProductItemCoordinator {

	/**
	 * index used for fast item lookup by container
	 */
	private Map<Container, Pair> indexPairsByContainer;
	
	/**
	 * builds a ProdcutItemCoordinator
	 */
	public ProductItemCoordinator() {
		
	}
	
	/**
	 * 
	 * @param _container 
	 * @return iterator on all items inside that container
	 */
	public Iterator<Item> getItems(Container _container) {
		return null;
	}
	
	/**
	 * 
	 * @param _container 
	 * @return iterator on all products inside that container
	 */	
	public Iterator<Product> getProducts(Container _container) {
		return null;
	}
	
	/**
	 * 
	 * @param product
	 * 
	 * wraps item manager's method
	 */
	public void addProduct(Product product) {
	
	}
	
	/**
	 * 
	 * @param product
	 * 
	 * wraps item manager's method
	 */	
	public void moveProduct(Product product) {
		
	}
	
	/**
	 * 
	 * @param oldProduct
	 * @param newProduct
	 * 
	 * wraps item manager's method
	 */
	public void editProduct(Product oldProduct, Product newProduct) {
		
	}
	
	/**
	 * 
	 * @param productToRemove
	 * 
	 * wraps item manager's method
	 */
	public void removeProduct(Product productToRemove) {
		
	}
	
	/**
	 * 
	 * @param items
	 * 
	 * wraps item manager's method
	 */
	public void addItem(List<Item> items) {
		
	}
	
	/**
	 * 
	 * @param items
	 * 
	 * checks if product and dependent items need to be moved before calling item manager's method
	 */
	public void moveItem(List<Item> items) {
		
	}
	/**
	 * 
	 * @param before
	 * @param after
	 * 
	 * wraps item manager's method
	 */
	public void editItem(Item before, Item after) {
		
	}
	
	
}
