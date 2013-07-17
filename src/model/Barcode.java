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
	 * Creates a Barcode object with automatically generated barcode.
	 */
	public Barcode() {
	    
		assert true;
		
		barcode =  generateBarcode();
		
	}
	
	/**
	 * @return a String representing a valid UPC-A barcode
	 */
	private String generateBarcode() {
		
	    Random rand = new Random();
	    
	    int[] digits = new int[11];
	    for (int i = 0; i < digits.length; i++) {
	        digits[i] = rand.nextInt(10);
	    }
	    
        // Adds the 1st, 3rd, 5th, 7th, 9th, and 11th digits (the nth digit in
        // the traditional (1-based index) sense).
	    int checkDigit = digits[0] + digits[2] + digits[4] + digits[6] +
	            digits[8] + digits[10];
	    
	    checkDigit *= 3;
	    
	    // Adds to result the 2nd, 4th, 6th, 8th, and 10th digits.
	    checkDigit += digits[1] + digits[3] + digits[5] + digits[7]
	            + digits[9];
	    
	    checkDigit %= 10;
	    
	    checkDigit = 10 - checkDigit;
	    if (checkDigit == 10) {
	        checkDigit = 0;
	    }
	    
	    String resultString = "";
	    for (int i = 0; i < digits.length; i++) {
	        resultString += digits[i];
	    }
	    
	    resultString += checkDigit;
	    
	    return resultString;
	    
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
