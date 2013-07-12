package printers;

import java.util.*;
import java.io.*;
import model.*;

/**
 * Provides a method to create a PDF file with images of barcodes for a
 * specified List of Item objects.
 */
public class BarcodePrinter {
    
    /**
     * Creates an instance of BarcodePrinter.
     */
    public BarcodePrinter() {
        
        assert true;
        
    }
    
    /**
     * Creates a PDF file with images of barcodes for the the specified List
     * of Item objects.
     * @param items the List of Item objects for which a PDF file of barcodes
     * is to be created
     * @param file the file to which the PDF is to be written; if the file
     * does not exist it will be created
     * @pre items is not null, and all of the Item objects are not null;
     * file is not null and it is writable.
     * @throws IllegalArgumentException if items or any of its contained
     * Item objects are null, or if file is null.
     * @throws IOException if file is not writable or if there is a problem
     * writing to the file.
     */
    public void printBarcodes(List<Item> items, File file)
        throws IllegalArgumentException, IOException {
        
        
        
    }

}
