package printers;

import java.util.*;
import java.io.*;

public interface ReportPrinter {
    
    public void printTitle(String title) throws IOException;
    
    public void printHeading(String heading) throws IOException;
    
    public void printLine(String line) throws IOException;
    
    public void printTable(List<String> columnHeadings,
            List<List<String>> records) throws IOException;

    public void printBlankLine() throws IOException;
    
    public void close(boolean displayFile) throws IOException;
    
}
