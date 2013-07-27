package reports;

import java.util.*;
import java.io.*;
import config.IOConfig;
import printers.*;

public class ReportDirector {
    
    public static void generateExpiredItemsReport(PrintFormat format) 
            throws IOException {
        
        ReportPrinter p = getPrinter(format);
        
        p.printTitle("Expired Items Report");
        p.printBlankLine();
        p.printBlankLine();
        List<String> columnHeadings = new LinkedList<String>();
        columnHeadings.add("Description");
        columnHeadings.add("Storage Unit");
        columnHeadings.add("Product Group");
        columnHeadings.add("Entry Date");
        columnHeadings.add("Expriation Date");
        columnHeadings.add("Item Barcode");

    }

    public static void generateRemovedItemsReport(Date startDate,
            PrintFormat format) {
        
        
        
    }

    public static void generateNMonthSupplyReport(int numOfMonths,
            PrintFormat format) {
    
        
    
    }

    public static void generateProductStatReport(Date startDate, Date endDate,
            PrintFormat format) {
    
        
    
    }
    
    public static void generatNoticesReport(PrintFormat format) {
        
        
    }
    
    private static ReportPrinter getPrinter(PrintFormat format)
            throws IOException {
        
        ReportPrinter p = null;
        
        switch (format) {

            case PDF:
                p = new PDFPrinter(IOConfig.getReportFile());
                break;
            case HTML:
                p = new HTMLPrinter(IOConfig.getReportFile());
                break;

        }
        
        return p;
        
    }
    
}
