package printers;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.Barcode;

/**
 * Provides a method to create a PDF file with images of barcodes for a
 * specified Collection of Item objects.
 */
public class BarcodePrinter {
    
    private static final int MARGINS_PT = 36;
    
    private static final int ROW_HEIGHT = 108;
    
    private static final int NUM_OF_COLUMNS = 4;
    
    private static final int MAX_DESCRIPTION_LENGTH = 50;
    
    private static final int BARCODE_Y_OFFSET = -37;
    
    /**
     * Creates a PDF file with images of barcodes for the the specified List
     * of Item objects.
     * @param items the Collection of Item objects for which a PDF file of
     * barcodes is to be created
     * @param file the file to which the PDF is to be written; if the file
     * does not exist it will be created
     * @param displayFile whether to display the PDF file after creation
     * @pre items is not null and all of the Item objects are not null;
     * file is not null and it is writable.
     * @throws IllegalArgumentException if items or any of its contained
     * Item objects are null, or if file is null.
     * @throws IOException if file is a directory, not writable, or if there
     * is a problem writing to the file.
     */
    public static void printBarcodes(Collection<Item> items, File file,
            boolean displayFile)
                    throws IllegalArgumentException, IOException {
        
        checkItems(items);
        
        checkFile(file);
        
        try {           
            
            Document document = new Document(PageSize.LETTER);
            
            PdfWriter writer = PdfWriter.getInstance(
                    document, new FileOutputStream(file));
                        
            document.open();            
            document.setMargins(
                    MARGINS_PT, MARGINS_PT, MARGINS_PT, MARGINS_PT);
            
            PdfContentByte pcb = writer.getDirectContent();
            
            PdfPTable table = new PdfPTable(NUM_OF_COLUMNS);
            table.setWidthPercentage(100);
            
            for (Item item : items) {
                
                Phrase phr = new Phrase();
                phr.add(truncateDescription(
                        item.getProduct().getDescription()) + "\n");
                
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                
                phr.add("Ent: " + df.format(item.getEntryDate()) + "\n");                
                phr.add("Exp: " + df.format(item.getExpirationDate()) + "\n");
                
                BarcodeEAN codeEAN = new BarcodeEAN();
                codeEAN.setCodeType(Barcode.UPCA);
                codeEAN.setCode(item.getTag().getBarcode());   
                Image barcode =
                        codeEAN.createImageWithBarcode(pcb, null, null);
                
                phr.add(new Chunk(barcode, 0, BARCODE_Y_OFFSET));
                
                PdfPCell cell = new PdfPCell(phr);
                cell.setFixedHeight(ROW_HEIGHT);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                table.addCell(cell);
            
            }
            
            int remainder = items.size() % NUM_OF_COLUMNS;
            if (remainder != 0) {
                
                for (int i = 0; i < NUM_OF_COLUMNS - remainder; i++) {
                    table.addCell(new PdfPCell());
                }
                
            }
            
            document.add(table);
            
            document.close();
            
            if (displayFile) {
                java.awt.Desktop.getDesktop().open(file);
            }
        
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    }
    
    private static void checkItems(Collection<Item> items) {
        
        if (items == null) {            
            throw new IllegalArgumentException(
                    "Specified Item Collection is null.");           
        }
        
        for (Item item : items) {
            if (item == null) {
                throw new IllegalArgumentException(
                        "Specified Item Collection containes null element(s).");
            }
        }

    }
    
    private static void checkFile(File file) throws IOException {
        
        //System.out.println(file);
        
        if (file == null) {
            throw new IllegalArgumentException("Specified file is null.");
        }
        
        if (file.exists() && (!file.isFile() || !file.canWrite())) {
            throw new IOException("Specified file is not valid.");
        }
        
        if (!file.exists()) {
            
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            
            file.createNewFile();
            
        }
        
    }
    
    private static String truncateDescription(String s) {
        
        if (s.length() > 75) {
            return s.substring(0, MAX_DESCRIPTION_LENGTH - 1) + "...";
        }
        else {
            return s;
        }
        
    }

}
