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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Barcode other = (Barcode) obj;
        if (barcode == null) {
            if (other.barcode != null) {
                return false;
            }
        }
        else if (!barcode.equals(other.barcode)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Barcode [barcode=" + barcode + "]";
    }
	
}
