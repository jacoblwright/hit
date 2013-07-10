package model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Manages alterations to all the Products in the system and handles passing the products 
 * 	structure to higher level classes. 
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
	public ProductManager(){
		productsByContainer = new HashMap<Container, Set<Product>>();
		productByUPC = new HashMap<Barcode, Product>();
	}
	
	/** Adds a product to the system
	 * @pre 	product must not already exist in the map
	 * @post	product is added to the set
	 */
	public void addNewProduct(Product product, Container container, StorageUnit storageUnit) 
			throws IllegalArgumentException{
		
		/* Add product to Barcode map */
		if(!isProductUnique(product)){
			throw new IllegalArgumentException();
		}
		
		/* Add first to container */
		addProductToContainer(product, container, storageUnit);
		
		
		productByUPC.put(product.getUPC(), product);
	}
	
	public void addProductToContainer(Product product, Container container,StorageUnit storageUnit) 
			throws IllegalArgumentException{
		
		if(!canAddProductToContainer(product, storageUnit)){
			throw new IllegalArgumentException();
		}
		
		/* Adds Container to the Product */
		product.addContainer(container);
		/* Add Product to Container Map */
		if(productsByContainer.containsKey(container)){
			productsByContainer.get(container).add(product);
		}
		else {
			Set<Product> productSet = new HashSet<Product>();
			productSet.add(product);
			productsByContainer.put(container, productSet);
		}
	}
	
	public void editProduct(Product before, Product after) throws IllegalArgumentException{
		before.setDescription(after.getDescription());
		before.setShelfLife(after.getShelfLife());
		before.setSize(after.getSize().getUnit(), after.getSize().getNumber());
		before.setThreeMonthSupply(after.getThreeMonthSupply());
	}
	
	/** Deletes a Product from the set all of products.
	 * @pre 	product must exist in the set
	 * @pre		product must not have any Items attached to it
	 * @post	removes the Product from the set
	 */
	public void deleteProduct(Product product, Container container) throws IllegalArgumentException{
		/* It will be previously verified if the product can indeed be deleted with the 
		 * canDeleteProduct method */
		if(!productsByContainer.containsKey(container))
			throw new IllegalArgumentException();
		Set tempSet = (HashSet)productsByContainer.get(container);
		if(!tempSet.contains(product))
			throw new IllegalArgumentException();
		
		/* Remove container from product */
		product.removeContainer(container);
		/* Remove product from container */
		tempSet.remove(product);
	}
	
	/** Moves a product to a new container as long as that product is not already located in that 
	 * 	storage unit.
	 * @pre 		product is not already in the container it is being moved into
	 * @param 		product	the product being moved
	 * @param 		before	the container before the product was moved
	 * @param 		after	the new container that the product is being move to
	 */
	public void moveProduct(Product product, Container before, Container after, 
			StorageUnit beforeStor, StorageUnit afterStor) throws IllegalStateException{
		if(beforeStor == afterStor){
			product.removeContainer(before);
			product.addContainer(after);
		}
		else{
			product.addContainer(after);
		}
	}
	
	/** Returns true if a Product can be added to a particular storage unit. This will return 
	 * true if product is not already contained within that particular storage unit.
	 * 
	 * @param 		product is the Product being examined if it is currently already in a particular
	 * 				 storage unit
	 * @param 		storage Product checks whether storage is already contained in 
	 * 				productsByContainer			
	 * @return		true if the product can be added to the storage, false otherwise
	 */
	public boolean canAddProductToContainer(Product product, StorageUnit storage){
		if(product.getContainers().contains(storage)){
			return false;
		}
		return true;
	}
	
	/** Return true if after is a valid Product, false otherwise. A valid Product contains a Barcode
	 * that contains a non-empty upc, a non-empty description, a Quantity that has a
	 * 
	 * @param 		before the value of Product before the edits
	 * @param 		after the value of Product after the edits
	 * @return		return true if after is a valid Product, false otherwise
	 */
	public boolean isProductValid(Product product){
		
		/* Is the date before today */
		Date date = new Date();
		if(product.getCreationDate().after(date))
			return false;
		
		/* Is the product's barcode non-empty */
		if(product.getUPC().getBarcode().isEmpty())
			return false;
		
		/* Is the product's quantity valid */
		if(!isQuantityValid(product.getSize()))
			return false;
		
		/* Is the product's shelf life above zero */
		if(product.getShelfLife() < 0)
			return false;
		
		if(product.getThreeMonthSupply() < 0)
			return false;
		
		return true; 
	}
	
	public boolean isProductUnique(Product product){
		/* Is the UPC Unique */
		if(productByUPC.containsValue(product))
			return false;
		
		return true;
	}
	
	/** Return true if qty has a valid number according to the specified Unit. If the Unit
	 * is COUNT, then the Quantity number must be 1. If the Quantity Unit is any
	 * other enum besides COUNT, number can be any positive float. 
	 * 
	 * @param 		qty is the Quantity being validated
	 * @return		returns true if Quantity is valid
	 */
	public boolean isQuantityValid(Quantity qty){
		
	    
		// If enum is COUNT, then check to see if the value is 1
		if(qty.getUnit().equals(Unit.count) && qty.getNumber() != 1){
			return false;
		}
		//If enum is anything but COUNT, check that value is greater than zero
		else if (qty.getNumber() <= 0 ){
			return false;
		}
		
		return true; 
	}
	
	/** Returns a Collection of all Products in a particular container.
	 * 
	 * @param 		container all Products contained within container should be returned
	 * @return		returns all the Products associated with this particular container
	 */
	public Collection getProducts(Container container) throws IllegalArgumentException{
		if(!productsByContainer.containsKey(container))
			throw new IllegalArgumentException();
		
		return productsByContainer.get(container); 
		
	}
	
	/** Returns a Collection of all Products.
	 * 
	 * @return returns all Products in system
	 */
	public Collection getProducts(){ 
		return productByUPC.values();
	}
	
	/** Returns a Product that pertains to a particular Barcode.
	 * 
	 * @param barcode
	 * @return returns the Product associate to barcode
	 */
	public Product getProductByBarcode(Barcode barcode) throws IllegalArgumentException{ 
		if(!productByUPC.containsKey(barcode))
			throw new IllegalArgumentException();
		
		return productByUPC.get(barcode); 
	}
	
	public boolean isUPCUnique(String s){
		Iterator it = productByUPC.entrySet().iterator();
		while(it.hasNext()){
			Barcode code = (Barcode) it.next();
			if(s.equals(code.getBarcode()))
				return false;
		}
		return true;
	}
	
	public Map getAllProductsByUPC(){
		return productByUPC;
	}
	
	public Map getProductsByContainer(){
		return productsByContainer;
	}

}
