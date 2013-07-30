package model;

import gui.common.SizeUnits;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/** Product holds a number of attributes pertaining to a product including
 * the creation date, upc, description, size, shelf life, three month supply,
 * and storage unit. It also contains all the containers in which it is 
 * contained. Setters and getters are provided for each of the attributes
 * along with methods to update the containers.
 * 
 * @author Andrew Wilde
 * @version 1.0
 */

public class Product extends Entity implements Comparable<Product>{

	/** the date this Product was added to the system and is always equal to the earliest entry 
	 * 	date for any item of this product */
	private Date creationDate;
	
	/** the associated manufacturer's barcode for this Product */
	private Barcode upc;
	
	/** the associated textual description for this Product */
	private String description;
	
	/** the associated size of this Product in which Quantity has both a Unit enum and float 
	 * 	to describe the size */
	private Quantity size;
	
	/** the associated shelf life of this Product in months */
	private int shelfLife;
	
	/** the amount of this Product needed for a 3-month supply */
	private int threeMonthSupply;
	
	/** the set of containers that contain this Product */
	private Set<Container> containers;
	
	/**	A constructor for instantiating a Product
	 * 
	 * @param code	the string that will help initialize the Product's Barcode object
	 * @param desc	a non-empty string that describes the product
	 * @param u		a valid enum that identifies the type of Quantity
	 * @param num	the value associated to the Quantity that must be positive
	 * @param life	a non-negative integer associated with shelf life
	 * @param supply	a non-negative integer associated with the 3 month supply
	 * @pre	code is non-empty
	 * @pre desc is non-empty
	 * @pre	unit is valid enumeration according to Unit
	 * @pre num is one if unit == count, otherwise it must be positive non-zero
	 * @pre life is non-negative integer
	 * @pre supply is non-negative integer
	 */
	public Product(String code, String desc, Enum<SizeUnits> u, float num, int life, int supply){
		assert true;
		this.creationDate = new Date();
		setUPC(code);
		setDescription(desc);
		setSize(u, num);
		setShelfLife(life);
		setThreeMonthSupply(supply);
		containers = new TreeSet<Container>();
	}
	
	@Override
	public boolean equals(Object obj) {
		assert true;
		if ( this == obj ) {
			return true;
		}
		
		if ( !( obj instanceof Product ) ){
			return false;
		}
		
		Product product = (Product) obj;
		return this.getUPC().getBarcode().equals(product.getUPC().getBarcode());
	}
	
	/** Adds container to Set<Container> containers.
	 * @param 		container is a Container being added to containers set
	 */
	public void addContainer(Container container){
		assert true;
		containers.add(container);
	}
	
	/** Removes a container from Set<Container> containers.
	 * @pre 		container must exist in set of containers
	 * @post		removes container from set of containers
	 * @param 		container is a Container being remove from containers set
	 */
	public void removeContainer(Container container) {
		if(!containers.contains(container))
			throw new IllegalArgumentException();
		this.containers.remove(container);
	}
	
	/** Sets the Products date of creation
	 * @param 		date is a Date object being set as earliest entry date for Product
	 */
	public void setCreationDate(Date date){
		assert true;
		Date today = new Date();
		this.creationDate = date;
	}
	
	/** Creates the Product's Barcode object
	 * @param 		code is a string being assigned as Product's upc
	 */
	public void setUPC(String code){
		assert true;
		this.upc = new Barcode(code);
	}
	
	/** Sets the Product's description
	 * @param 		desc is a string being assigned as Product's description
	 */
	public void setDescription(String desc){
		assert true;
		this.description = desc;
	}
	
	/** Initializes the Quantity object that pertains to the Product
	 * @param 		unit is the enum being assigned to Quantity size
	 * @param 		number is a float assigned to Quantity size
	 */
	public void setSize(Enum<SizeUnits> unit, float number){
		assert true;
		size = new Quantity();
		size.setQuantity(number, unit);
	}
	
	/** Sets the shelf life of the Product
	 * @param 		life is a non-negative int associated with shelfLife
	 */
	public void setShelfLife(int life){
		assert true;
		this.shelfLife = life;
	}
	
	/** Sets the three month supply with an integer
	 * @param 		amt is an int associated with the three month supply
	 */
	public void setThreeMonthSupply(int amt){
		assert true;
		this.threeMonthSupply = amt;
	}
	
	/**
	 * @pre			none
	 * @return 		date of most recently added item to Product
	 */
	public Date getCreationDate(){
		assert true;
		return creationDate; 
	}
	
	/**
	 * @pre			none
	 * @return 		barcode of Product
	 */
	public Barcode getUPC(){
		assert true;
		return upc; 
	}
	
	/**
	 * @pre			none
	 * @return 		description of Product
	 */
	public String getDescription(){ 
		assert true;
		return description; 
	}
	
	/**
	 * @pre			none
	 * @return 		size of Product
	 */
	public Quantity getSize(){
		assert true;
		return size; 
	}
	
	/**
	 * @pre			none
	 * @return 		shelfLife of Product
	 */
	public int getShelfLife(){ 
		assert true;
		return shelfLife; 
	}
	
	/**
	 * @pre			none
	 * @return		threeMonthSupply of Product
	 */
	public int getThreeMonthSupply(){ 
		assert true;
		return threeMonthSupply; 
	}
	
	/**
	 * @pre			none
	 * @return		containers in which this Product currently resides
	 */
	public Set<Container> getContainers(){ 
		assert true;
		return containers; 
	}

    @Override
    public String toString() {
        /*
        return "Product [creationDate=" + creationDate + ", upc=" + upc
                + ", description=" + description + ", size=" + size
                + ", shelfLife=" + shelfLife + ", threeMonthSupply="
                + threeMonthSupply + ", containers=" + containers + "]";
        */
        return "Product [upc=" + upc + ", description=" + description +
        		", date=" + creationDate +"]"; 
    }
    
	@Override
	public int compareTo(Product other) {
		int compared = creationDate.compareTo(other.creationDate);
		if(compared < 0)
			return -1;
		else if (compared > 0)
			return 1;
		else
			return 0;
	}

}
