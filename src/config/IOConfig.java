package config;

import java.io.File;

public class IOConfig {
    
    private final static String OBJECT_DATA_DIR_NAME = "hit_data";
    private final static String C_MANAGER_FILENAME = "cm.hit";
    private final static String P_MANAGER_FILENAME = "pm.hit";
    private final static String I_MANAGER_FILENAME = "im.hit";
    
    private final static String PRINTER_OUTPUT_DIR_NAME = "hit_printer_output";
    private final static String BARCODE_TAGS_FILENAME = "barcode_tags.pdf";
    
    private static File containerManagerFile;
    private static File productManagerFile;
    private static File itemManagerFile;
    
    private static File barcodeTagsFile;
    
    static {
        
        containerManagerFile = new File(
                System.getProperty("user.home") + File.separator +
                OBJECT_DATA_DIR_NAME + File.separator +
                C_MANAGER_FILENAME);
        productManagerFile = new File(
                System.getProperty("user.home") + File.separator +
                OBJECT_DATA_DIR_NAME + File.separator +
                P_MANAGER_FILENAME);
        itemManagerFile = new File(
                System.getProperty("user.home") + File.separator +
                OBJECT_DATA_DIR_NAME + File.separator +
                I_MANAGER_FILENAME);
        
        barcodeTagsFile = new File(
                System.getProperty("user.home") + File.separator +
                PRINTER_OUTPUT_DIR_NAME + File.separator +
                BARCODE_TAGS_FILENAME);                
        
    }

    public static File getContainerManagerFile() {
        return containerManagerFile;
    }

    public static File getProductManagerFile() {
        return productManagerFile;
    }

    public static File getItemManagerFile() {
        return itemManagerFile;
    }
    
    public static File getBarcodeTagsFile() {
        return barcodeTagsFile;
    }

}
