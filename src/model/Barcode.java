package model;

import java.io.*;
import java.util.Random;

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
		assert true;
		barcode =  generateBarcode();
	}
	
	/**
	 * 
	 * @return ucp-a barcode
	 */
	private String generateBarcode() {
		assert true;
		String newBarcode = "";
		Random rand = new Random();
		for( int i = 0; i < 11; i++ ) {
			newBarcode += ( rand.nextInt(10) );
		}
		 
		int oddNumber = 0;
		for( int i = 1; i < 10; i += 2 ) {
			oddNumber += Integer.valueOf( newBarcode.charAt( i ) );
		} 
		oddNumber *= 3;
		
		int evenNumber = 0;
		for( int i = 0; i < 11; i += 2 ) {
			evenNumber += Integer.valueOf( newBarcode.charAt( i ) );
		} 
		
		int num = oddNumber + evenNumber;
		num %= 10;
		int finalNumber = 10 - num;
		newBarcode += String.valueOf( finalNumber );
		return newBarcode;
	}


	/**
	 * 
	 * @param barcode must be a valid barcode
	 * 
	 * @post barcode object with valid barcode
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
		assert true;
		if(barcode.isEmpty())
			return false;
		return true;
	}

	/**
	 * 
	 * @return item.barcode
	 */
	public String getBarcode() {
		assert true;
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
		assert true;
		this.barcode = barcode;
	}

    @Override
    public boolean equals(Object obj) {
    	assert true;
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
    	assert true;
        return "Barcode [barcode=" + barcode + "]";
    }
	
}
