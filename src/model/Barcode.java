package model;

import java.io.*;

/**
 * Class used to store and validate barcodes
 * 
 * @author Nick
 *
 */
public class Barcode implements Serializable {

	/**
	 * string that holds a valid barcode
	 */
	private String barcode;
	
	/**
	 * barcode object with automatically generated unique barcode
	 */
	public Barcode() {
		barcode = "lalalBarcode";
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
		if (!isValidBarcode(barcode)) {
			throw new IllegalArgumentException();
		}
		this.barcode = barcode;
	}
	
	/**
	 * 
	 * @return if this.barcode is valid
	 */
	public boolean isValidBarcode(String barcode) {
		if(barcode.isEmpty())
			return false;
		return true;
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
