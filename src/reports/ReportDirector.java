package reports;

import java.util.*;
import java.io.*;
import model.*;
import config.IOConfig;
import printers.*;
import gui.common.*;

public class ReportDirector {
    
    public static void generateExpiredItemsReport(FileFormat format) 
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
        
        Set<Container> containerRoot = new HashSet<Container>();
        containerRoot.addAll(
                Model.getInstance().getContainerManager().getRoot());
        Iterator<Container> itr = new ContainerPreorderIterator(containerRoot);
        Visitor visitor = new ExpiredItemsVisitor(itr);
        List<Record> records = visitor.visitAll();
        List<List<String>> recordsAsStrings = new LinkedList<List<String>>();
        for (Record record : records) {
            recordsAsStrings.add(record.getValuesAsStrings());
        }
        p.printTable(columnHeadings, recordsAsStrings);
        
        p.close(true);

    }

    public static void generateRemovedItemsReport(Date startDate,
            FileFormat format) {
        
        
        
    }

    public static void generateNMonthSupplyReport(int numOfMonths,
            FileFormat format) {
    
        
    
    }

    public static void generateProductStatReport(Date startDate, Date endDate,
            FileFormat format) {
    
        
    
    }
    
    public static void generatNoticesReport(FileFormat format) {
        
        
    }
    
    private static ReportPrinter getPrinter(FileFormat format)
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
