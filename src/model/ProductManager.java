package model;

import gui.product.ProductData;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import gui.common.*;

/** Manages alterations to all the Products in the system and handles passing the Product's 
 * 	structure to higher level classes. 
 * 
 * @author Andrew Wilde
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ProductManager extends Observable implements Serializable{
	/** Maps containers to sets of products. */
	private Map <Container, Set<Product>> productsByContainer;
	
	/** Maps a barcode to a product. */
	private Map <Barcode, Product> productByUPC;
	
	/** Constructs the ProductManager by initializing a HashMap<Container, Set<Product> 
	 * and HashMap<Barcode, Product */ 
	public ProductManager(){
		assert true;
		productsByContainer = new HashMap<Container, Set<Product>>();
		productByUPC = new TreeMap<Barcode, Product>();
	}
	
	/** Adds a product to the system
	 * @pre 	product must not already exist in the map
	 * @post	product is added to the set
	 * @param product	the current product the container will be added to 
	 * @param container	the container being added to the product
	 * @throws IllegalArgumentException if the product's upc already exists
	 */
	public void addNewProduct(Product product, Container container) 
			throws IllegalArgumentException{
		
		if(upcExists(product.getUPC().getBarcode())){
			throw new IllegalArgumentException();
		}

		addProductToContainer(product, container);
		productByUPC.put(product.getUPC(), product);
		
		notify(null);
	}
	
	/** Edits a Product's description, shelf life, quantity, and three month supply
	 * 
	 * @param before	The product to be changed
	 * @param after		The product that contains the new values
	 * @pre after Product must be a valid Product
	 * @post before is changed to reflect after's attributes
	 * @throws IllegalArgumentException if the product is not valid
	 */
	public void editProduct(Product before, Product after) throws IllegalArgumentException{
		assert true;
		if(!isProductValid(after)){
			throw new IllegalArgumentException();
		}
		
		before.setDescription(after.getDescription());
		before.setShelfLife(after.getShelfLife());
		before.setSize(after.getSize().getUnit(), after.getSize().getNumber());
		before.setThreeMonthSupply(after.getThreeMonthSupply());
		
		notify(before);
	}
	
	/** Moves a product from one container to another (or simply replaces itself)
	 * @pre Product's set of containers must contain the Container before
	 * @post product will be removed from the before's mapped set and before will be removed from
	 * product's set of containers. product will be added to after's mapping and after will be 
	 * added to product's set of containers.
	 * @param 		product	the product being moved
	 * @param 		before	the container the product is being moved from
	 * @param 		after	the new container that the product is being move to
	 * @throws IllegalStateException when product does not have before in its collection 
	 * of containers
	 */
	public void moveProduct(Product product, Container before, Container after) 
			throws IllegalStateException{
	    
	    //System.out.println("PM:moveProduct");
		
		if(!product.getContainers().contains(before))
			throw new IllegalStateException();
		
		removeProductFromContainer(product, before);
		addProductToContainer(product, after);
		
		notify(product);
	}
	/** Adds product to specific container
	 * 
	 * @param product	Product being moved
	 * @param container	The container to which the product is being moved to
	 */
	public void addProductToContainer(Product product, Container container){
		
	    //System.out.println("PM:addProductToContainer");
	    
		product.addContainer(container);
		
		/* Add Product to Container Map */
		if(productsByContainer.containsKey(container)){
			boolean isContained = productsByContainer.get(container).contains(product);
			productsByContainer.get(container).add(product);
		}
		else {
			Set<Product> productSet = new TreeSet<Product>();
			productSet.add(product);
			productsByContainer.put(container, productSet);
		}
		notify(product);
	}
	
	/** Deletes Product from container's mapping in productsByContainer and removes 
	 * container from product's set of containers
	 * @pre 	product must exist in the container's mapping
	 * @post	removes the Product from the set
	 * @throws IllegalArgumentException if container does not exist in productsByContainer
	 */
	public void removeProductFromContainer(Product product, Container container) 
			throws IllegalArgumentException{
		
		if(!productsByContainer.containsKey(container))
			throw new IllegalArgumentException();
		
		product.removeContainer(container);
		
		if(!productsByContainer.get(container).contains(product))
			throw new IllegalArgumentException();

		productsByContainer.get(container).remove(product);
		notify(product);
	}
	
	public void deleteProductFromSystem(Product product) throws IllegalArgumentException{
		if(!productByUPC.containsValue(product)){
			throw new IllegalArgumentException();
		}
		productByUPC.remove(product.getUPC());
		
		Set<Container> containers = product.getContainers();
		Iterator<Container> it = containers.iterator();
		while(it.hasNext()){
			Container tempCon = (Container)it.next();
			productsByContainer.get(tempCon).remove(product);
		}
		notify(null);
	}
	
	/** Return true if after is a valid Product, false otherwise. A valid Product contains a Barcode
	 * that contains a non-empty upc, a non-empty description, a Quantity that has a
	 * 
	 * @param product	the product behing checked for validity
	 * @return		return true if product is a valid Product, false otherwise
	 */
	public boolean isProductValid(Product product){
		assert true;

		Date date = new Date();
		if(product.getCreationDate().after(date))
			return false;
		
		if(product.getUPC().getBarcode().isEmpty())
			return false;
		
		if(product.getDescription().isEmpty())
			return false;
		
		if(!isQuantityValid(product.getSize()))
			return false;
		
		if(product.getShelfLife() < 0)
			return false;
		
		if(product.getThreeMonthSupply() < 0)
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
		assert true;
	    
		// If enum is COUNT, then check to see if the value is 1
		if(qty.getUnit().equals(SizeUnits.Count) && qty.getNumber() != 1.0){
			return false;
		}
		//If enum is anything but COUNT, check that value is greater than zero
		else if (qty.getNumber() <= 0 ){
			return false;
		}
		return true; 
	}
	
	/** Checks whether the upc is currently assigned to a Product.
	 * 
	 * @param upc	the String representation of a upc
	 * @return		true if the upc exists, false otherwise
	 */
	public boolean upcExists(String upc){
		assert true;
		Iterator<Barcode> it = productByUPC.keySet().iterator();
		while(it.hasNext()){
			Barcode code = (Barcode) it.next();
			if(upc.equals(code.getBarcode()))
				return true;
		}
		return false;
	}
	
	/** Returns a Collection of all Products in a particular container.
	 * 
	 * @param 		the container being searched for Products
	 * @return		returns a collection of Products associated with this particular container
	 * @throws IllegalArgumentException if the container is not contained within the map
	 */
	public Collection<Product> getProducts(Container container){
		assert true;
		
		if (productsByContainer.get(container) != null)
			return new TreeSet<Product>(productsByContainer.get(container)); 
		else return new TreeSet<Product>();
		
	}
	
	/** Returns all Products in the system
	 * 
	 * @return returns a collection of all Products
	 */
	public Collection<Product> getProducts(){ 
		assert true;
		
		if(productByUPC.values() != null)
			return new TreeSet<Product>(productByUPC.values());
		else return null;
	}
	
	/** Returns a Product that pertains to a particular Barcode.
	 * 
	 * @param barcode	the barcode being searched for
	 * @return returns the Product associated to barcode
	 * @throws IllegalArgumentException if the barcode is not contained in the productByUPC map
	 */
	public Product getProductByUPC(Barcode barcode) throws IllegalArgumentException{ 
		
		Set<Barcode> barcodes = productByUPC.keySet();
		Iterator<Barcode> it = barcodes.iterator();
		while(it.hasNext()){
			Barcode code = (Barcode)it.next();
			if(code.equals(barcode)){
				return productByUPC.get(code);
			}
		}
		if(!it.hasNext())
			throw new IllegalArgumentException();
		
		return null;
	}
	
	/** Getter for the products mapped with a UPC
	 * 
	 * @return	HashMap of Products mapped by a Barcode
	 */
	public Map <Barcode, Product> getProductsByUPC(){
		assert true;
		return productByUPC;
	}
	
	/** Getter for the set of products mapped by a container
	 * 
	 * @return	HashMap of Products mapped by a Container
	 */
	public Map<Container, Set<Product>> getProductsByContainer(){
		assert true;
		return productsByContainer;
	}
	
	/** Notifies all observers that a Product has been changed
	 * 
	 * pre none
	 * post notifies observers of Product changes
	 */
	
	private void notify(Product changer) {
		ChangeObject hint = new ChangeObject();
		hint.setChangeType(ChangeType.PRODUCT);
		if (changer != null){
			hint.setSelectedData(new ProductData(changer));
		}
		setChanged();
		notifyObservers(hint);
	}

}
