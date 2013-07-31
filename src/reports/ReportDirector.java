package reports;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
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
        columnHeadings.add("Expiration Date");//fixed bug was spelled Expriation
        columnHeadings.add("Item Barcode");
        
        Visitor visitor = new ExpiredItemsVisitor();
        List<Record> records = visitor.visitAll();
        List<List<String>> recordsAsStrings = new LinkedList<List<String>>();
        for (Record record : records) {
            recordsAsStrings.add(record.getValuesAsStrings());
        }
        p.printTable(columnHeadings, recordsAsStrings);
        
        p.close(true);

    }

    public static void generateRemovedItemsReport(Date date,
            FileFormat format) throws IOException {
        
        ReportPrinter p = getPrinter(format);
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd H:mm");
        
        p.printTitle("Items Removed Since " + df.format(date));
        p.printBlankLine();
        p.printBlankLine();
        
        List<String> columnHeadings = new LinkedList<String>();
        columnHeadings.add("Description");
        columnHeadings.add("Size");
        columnHeadings.add("Product Barcode");
        columnHeadings.add("Removed");
        columnHeadings.add("Current Supply");
        
        Visitor visitor = new RemovedItemsVisitor(date);
        List<Record> records = visitor.visitAll();
        List<List<String>> recordsAsStrings = new LinkedList<List<String>>();
        for (Record record : records) {
            recordsAsStrings.add(record.getValuesAsStrings());
        }
        p.printTable(columnHeadings, recordsAsStrings);
        
        p.close(true);
        
    }

    public static void generateNMonthSupplyReport(int numOfMonths,
            FileFormat format) throws IOException {
    
        ReportPrinter p = getPrinter(format);
        
        p.printTitle("3-Month Supply Report");
        p.printBlankLine();
        p.printBlankLine();
        
        p.printHeading("Products");
        p.printBlankLine();
        
        List<String> columnHeadings = new LinkedList<String>();
        columnHeadings.add("Description");
        columnHeadings.add("Barcode");
        columnHeadings.add("3-Month Supply");
        columnHeadings.add("Current Supply");
        
        Visitor visitor = new NMonthSupplyProductVisitor(numOfMonths);
        List<Record> records = visitor.visitAll();
        List<List<String>> recordsAsStrings = new LinkedList<List<String>>();
        for (Record record : records) {
            recordsAsStrings.add(record.getValuesAsStrings());
        }
        p.printTable(columnHeadings, recordsAsStrings);
        p.printBlankLine();
        p.printBlankLine();
        
        p.printHeading("Product Groups");
        p.printBlankLine();
        
        columnHeadings.clear();
        columnHeadings.add("Product Group");
        columnHeadings.add("Storage Unit");
        columnHeadings.add("3-Month Supply");
        columnHeadings.add("Current Supply");
        
        visitor = new NMonthSupplyContainerVisitor(numOfMonths);
        records.clear();
        records = visitor.visitAll();
        recordsAsStrings.clear();
        for (Record record : records) {
            recordsAsStrings.add(record.getValuesAsStrings());
        }
        p.printTable(columnHeadings, recordsAsStrings);
        
        p.close(true);
    
    }

    public static void generateProductStatReport(int numOfMonths,
            FileFormat format) throws IOException {
    
        ReportPrinter p = getPrinter(format);
        
        p.printTitle("Product Statistics Report (" + numOfMonths + " Months)");
        p.printBlankLine();
        p.printBlankLine();
        
        List<String> columnHeadings = new LinkedList<String>();
        columnHeadings.add("Description");
        columnHeadings.add("Barcode");
        columnHeadings.add("Size");
        columnHeadings.add("3-Month Supply");
        columnHeadings.add("Supply: Cur/Avg");
        columnHeadings.add("Supply: Min/Max");
        columnHeadings.add("Supply: Used/Added");
        columnHeadings.add("Supply: Shelf Life");
        columnHeadings.add("Used Age: Avg/Max");
        columnHeadings.add("Cur Age: Avg/Max");
        
        Visitor visitor = new ProductStatVisitor(numOfMonths);
        List<Record> records = visitor.visitAll();
        List<List<String>> recordsAsStrings = new LinkedList<List<String>>();
        for (Record record : records) {
            recordsAsStrings.add(record.getValuesAsStrings());
        }
        p.printTable(columnHeadings, recordsAsStrings);
        
        p.close(true);
    
    }
    
    public static void generateNoticesReport(FileFormat format)
            throws IOException{
        
        ReportPrinter p = getPrinter(format);
        
        p.printTitle("Notices");
        p.printBlankLine();
        p.printBlankLine();
        
        p.printHeading("3-Month Supply Warnings");
        p.printBlankLine();
                
        Visitor visitor = new NoticesVisitor();
        List<Record> records = visitor.visitAll();
        for (Record record : records) {
            
            List<String> valuesAsStrings = record.getValuesAsStrings();
            for (String valueString : valuesAsStrings) {
                p.printLine(valueString);
            }
            
            p.printBlankLine();
            
        }
        
        p.close(true);
        
    }
    
    private static ReportPrinter getPrinter(FileFormat format)
            throws IOException {
        
        ReportPrinter p = null;
        
        switch (format) {

            case PDF:
                p = new PDFPrinter(IOConfig.getReportFilePDF());
                break;
            case HTML:
                p = new HTMLPrinter(IOConfig.getReportFileHTML());
                break;

        }
        
        return p;
        
    }
    
    public static int getValidMonths(String numOfMonths) {
        
    	int number = 0;
		try
		{	
			if( emptyString( numOfMonths ) ) {
				number = -1;
			}
			else {
				number = Integer.parseInt( numOfMonths );
			}
		}
		catch(NumberFormatException e)
		{
			number = -1;
		}
		if(number < 1 || number > 100) {
			number = -1;
		}
		return number;
		
    }
    
	private static boolean emptyString(String str) {
		return str.trim().length() == 0;
	}
    
}
