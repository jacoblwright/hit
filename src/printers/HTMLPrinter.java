package printers;

import java.io.*;
import java.util.List;

public class HTMLPrinter implements ReportPrinter {
    
    public HTMLPrinter(File file) {
        
        
        
    }

    @Override
    public void printTitle(String title) throws IOException {
    }

    @Override
    public void printHeading(String heading) throws IOException {
    }

    @Override
    public void printLine(String line) throws IOException {
    }

    @Override
    public void printTable(List<String> columnHeadings,
            List<List<String>> records) throws IOException {
    }

    @Override
    public void printBlankLine() throws IOException {
    }

    @Override
    public void close(boolean displayFile) throws IOException {
    }

}
