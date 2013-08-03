package config;

import java.io.File;

public class IOConfig {
    
    private final static String DATA_DIR_NAME = "hit_data";
    private final static String C_MANAGER_FILENAME = "cm.hit";
    private final static String P_MANAGER_FILENAME = "pm.hit";
    private final static String I_MANAGER_FILENAME = "im.hit";
    private final static String REPORT_TIME_FILENAME = "rt.hit";
    
    private final static String PRINTER_OUTPUT_DIR_NAME = "hit_printer_output";
    private final static String BARCODE_TAGS_FILENAME = "barcode_tags.pdf";
    private final static String REPORT_FILENAME_PDF = "report.pdf";   
    private final static String REPORT_FILENAME_HTML = "report.html";   
    
    private static File databaseFile;
    
    private static File containerManagerFile;
    private static File productManagerFile;
    private static File itemManagerFile;
    private static File reportTimeFile;
    
    private static File barcodeTagsFile;    
    private static File reportFilePDF;        
    private static File reportFileHTML;
    
    public static final String DATABASE_FILE_PATH =
            System.getProperty("user.home") + File.separator +
            DATA_DIR_NAME + File.separator +
            "hit.sqlite";
    public static final String REMOVED_ITEMS_REPORT_TIME_NAME =
            "removed_items";
    
    static {
        
        databaseFile = new File(DATABASE_FILE_PATH);
        
        containerManagerFile = new File(
                System.getProperty("user.home") + File.separator +
                DATA_DIR_NAME + File.separator +
                C_MANAGER_FILENAME);
        productManagerFile = new File(
                System.getProperty("user.home") + File.separator +
                DATA_DIR_NAME + File.separator +
                P_MANAGER_FILENAME);
        itemManagerFile = new File(
                System.getProperty("user.home") + File.separator +
                DATA_DIR_NAME + File.separator +
                I_MANAGER_FILENAME);
        reportTimeFile = new File(
                System.getProperty("user.home") + File.separator +
                DATA_DIR_NAME + File.separator +
                REPORT_TIME_FILENAME);
        
        barcodeTagsFile = new File(
                System.getProperty("user.home") + File.separator +
                PRINTER_OUTPUT_DIR_NAME + File.separator +
                BARCODE_TAGS_FILENAME);      
        reportFilePDF = new File(
                System.getProperty("user.home") + File.separator +
                PRINTER_OUTPUT_DIR_NAME + File.separator +
                REPORT_FILENAME_PDF);
        reportFileHTML = new File(
                System.getProperty("user.home") + File.separator +
                PRINTER_OUTPUT_DIR_NAME + File.separator +
                REPORT_FILENAME_HTML);
        
    }

    public static File getDatabaseFile() {
        return databaseFile;
    }

    public static void setDatabaseFile(File databaseFile) {
        IOConfig.databaseFile = databaseFile;
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
    
    public static File getReportTimeFile() {
        return reportTimeFile;
    }
    
    public static File getBarcodeTagsFile() {
        return barcodeTagsFile;
    }
    
    public static File getReportFilePDF() {
        return reportFilePDF;
    }
    
    public static File getReportFileHTML() {
        return reportFileHTML;
    }

}
