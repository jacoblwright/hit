package model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/** Product holds a number of attributes pertaining to a product including
 * the creation date, upc, description, size, shelf life, three month supply,
 * and storage unit. It also contains all the containers in which it is 
 * contained. Setters and getters are provided for each of the attributes
 * along with methods to update the containers.
 * 
 * @author Andrew Wilde
 * @version 1.0
 */

public class Product {

	/** the date this Product was added to the system and is always equal to the earliest entry date for any item of this product */
	private Date creationDate;
	
	/** the associated manufacturer's barcode for this Product */
	private Barcode upc;
	
	/** the associated textual description for this Product */
	private String description;
	
	/** the associated size of this Product in which Quantity has both a Unit enum and float to describe the size */
	private Quantity size;
	
	/** the associated shelf life of this Product in months */
	private int shelfLife;
	
	/** the amount of this Product needed for a 3-month supply */
	private int threeMonthSupply;
	
	/** the set of containers that contain this Product */
	private Set<Container> containers;
	

	
	/** Simple constructor for Product. Date will be initially set to today's date. */
	public Product(){
		creationDate = new Date();
	}
	
	/**
	 * @pre			container may not share the same storageUnit with any other current Container in containers
	 * @post		adds container to set of containers
	 * @param 		container is a Container being added to containers set
	 */
	public void addContainer(Container container){
		/* This check is performed by canAddProduct in ProductManager */
		containers.add(container);
	}
	
	/**
	 * @pre 		container must exist in set of containers
	 * @pre			no items may currently exist in Product
	 * @post		removes container from set of containers
	 * @param 		container is a Container being remove from containers set
	 */
	public void removeContainer(Container container) {
		if(!containers.contains(container))
			throw new IllegalArgumentException();
		
		containers.remove(container);
	}
	
	/**
	 * @pre			date must be before today's date
	 * @post		sets the Product's date
	 * @param 		date is a Date object being set as earliest entry date for Product
	 */
	public void setCreationDate(Date date){
		Date today = new Date();
		if(date.after(today))
			throw new IllegalArgumentException();
		
		creationDate = date;
	}
	
	/**
	 * @pre			code must be a non-empty String
	 * @post		sets Product's Barcode's upc
	 * @param 		code is a non-empty string being assigned as Product's upc
	 */
	public void setUPC(String code){
		if(code.isEmpty())
			throw new IllegalArgumentException();
		
		upc = new Barcode(code);
	}
	
	/**
	 * @pre			desc must be a non-empty String
	 * @post		sets the description of the product
	 * @param 		desc is a non-empty string being assigned as Product's description
	 */
	public void setDescription(String desc){
		if(desc.isEmpty())
			throw new IllegalArgumentException();
		
		description = desc;
	}
	
	/**
	 * @pre			number must be greater than zero
	 * @pre			unit must be either COUNT, POUND, OUNCE, GRAM, KILOGRAM, GALLON, QUART, PINT, FLUID_OUNCE, or LITER
	 * @post		sets the size of the Product
	 * @param 		unit is the enum being assigned to Quantity size
	 * @param 		number is a non-zero float assigned to Quantity size
	 */
	public void setSize(Unit unit, float number){
		/* Enum should be checked on unit creation */
		if(number < 0){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * @pre			life must be a non-negative integer
	 * @post		sets the shelfLife of the Product
	 * @param 		life is a non-negative int associated with shelfLife
	 */
	public void setShelfLife(int life){
		if(life <= 0)
			throw new IllegalArgumentException();
		
		shelfLife = life;
	}
	
	/**
	 * @pre			amt must be a non-negative integer
	 * @post		sets the number of Product needed for threeMonthSupply
	 * @param 		amt is an int associated with the three month supply
	 */
	public void setThreeMonthSupply(int amt){
		if(amt <= 0)
			throw new IllegalArgumentException();
		
		threeMonthSupply = amt;
	}
	
	/**
	 * @pre			none
	 * @return 		date of most recently added item to Product
	 */
	public Date getCreationDate(){
		return creationDate; 
	}
	
	/**
	 * @pre			none
	 * @return 		barcode of Product
	 */
	public Barcode getUPC(){ 
		return upc; 
	}
	
	/**
	 * @pre			none
	 * @return 		description of Product
	 */
	public String getDescription(){ 
		return description; 
	}
	
	/**
	 * @pre			none
	 * @return 		size of Product
	 */
	public Quantity getSize(){
		return size; 
	}
	
	/**
	 * @pre			none
	 * @return 		shelfLife of Product
	 */
	public int getShelfLife(){ 
		return shelfLife; 
	}
	
	/**
	 * @pre			none
	 * @return		threeMonthSupply of Product
	 */
	public int getThreeMonthSupply(){ 
		return threeMonthSupply; 
	}
	
	/**
	 * @pre			none
	 * @return		containers in which this Product currently resides
	 */
	public Set<Container> getContainers(){ 
		return containers; 
	}
	

}
