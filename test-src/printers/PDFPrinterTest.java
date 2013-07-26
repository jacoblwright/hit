package printers;

import java.util.*;

import config.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class PDFPrinterTest {

    @Test
    public void test() throws Exception {
        
        ReportPrinter p = new PDFPrinter(IOConfig.getReportFile());
        
        p.printTitle("Title");
        
        p.printBlankLine();
        p.printBlankLine();
        
        p.printHeading("Heading Text");
        
        p.printBlankLine();
        
        List<String> ch = new LinkedList<String>();
        ch.add("One");
        ch.add("Two");
        ch.add("Three");
        
        List<List<String>> rs = new LinkedList<List<String>>();   
        
        List<String> r1 = new LinkedList<String>();
        r1.add("1-1");
        r1.add("1-2");
        r1.add("1-3");       
        List<String> r2 = new LinkedList<String>();
        r2.add("2-1");
        r2.add("2-2");
        r2.add("2-3");
        
        rs.add(r1);
        rs.add(r2);
        
        p.printTable(ch, rs);
        
        p.printBlankLine();
        
        p.printLine("The quick brown fox jumped over the lazy dogs that were eating some salmon that were from Alaska which was a really big state but now it's really small for some reason.");
        
        p.printBlankLine();
        
        p.printLine("test");
        
        p.close(true);
        
    }

}
