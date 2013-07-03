package model;
/**
 * Class used to store and validate barcodes
 * 
 * @author Nick
 *
 */
public class Barcode {

	/**
	 * string that holds a valid barcode
	 */
	private String barcode;
	
	/**
	 * @return barcode object with automatically generated unique barcode
	 */
	public Barcode() {
		
	}
	
	/**
	 * 
	 * @param barcode must be a valid barcode
	 * 
	 * @return barcode object with valid barcode
	 * 
	 * @throws IllegalArgumentException
	 */
	public Barcode (String barcode) throws IllegalArgumentException {
		
	}
	
	/**
	 * 
	 * @return if this.barcode is valid
	 */
	public boolean isValid() {
		return false;
	}
	
	/**
	 * 
	 * @return if the given barcode is unique to the system
	 */
	public boolean isUnique() {
		return false;
	}

	/**
	 * 
	 * @return item.barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * 
	 * @param barcode
	 * 
	 * @pre param barcode must be valid
	 * @post this.barcode = barcode
	 * @throws IllegalArgumentException if barcode argument is invalid
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	
	
	
	
}
