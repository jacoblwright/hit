package printers;

import java.io.*;
import java.util.List;

public class HTMLPrinter extends Printer implements ReportPrinter {
    
    private File file;
    private StringBuilder b;
    
    public HTMLPrinter(File file) throws IOException {
        
        checkFile(file);
        
        this.file = file;
        
        b = new StringBuilder();
        b.append("<html>\n<body>\n");
        
    }

    @Override
    public void printTitle(String title) throws IOException {
        
        b.append("<center><span style=\"font-size:24;font-weight:bold;\">" +
        		title + "</span></center><br>\n");
        
    }

    @Override
    public void printHeading(String heading) throws IOException {
        
        b.append("<span style=\"font-size:18;font-weight:bold;\">" +
                heading + "</span><br>\n");
        
    }

    @Override
    public void printLine(String line) throws IOException {
        
        b.append(line + "<br>\n");
        
    }

    @Override
    public void printTable(List<String> columnHeadings,
            List<List<String>> records) throws IOException {
        
        b.append("<table border=\"2\">\n");
        b.append("<tr>\n");
        for (String columnHeading : columnHeadings) {
            b.append("<th>" + columnHeading + "</th>");
        }
        b.append("</tr>\n");
        for (List<String> record : records) {
            b.append("<tr>\n");
            for (String value : record) {
                b.append("<td>" + value + "</td>");
            }
            b.append("</tr>\n");
        }                
        b.append("</table>\n");
        
    }

    @Override
    public void printBlankLine() throws IOException {
        
        b.append("<br>\n");
        
    }

    @Override
    public void close(boolean displayFile) throws IOException {
        
        b.append("</body>\n</html>\n");
        
        String html = b.toString();
        PrintWriter pw = new PrintWriter(file);
        pw.print(html);
        pw.close();
        
        if (displayFile) {
            java.awt.Desktop.getDesktop().open(file);
        }
        
    }

}
