package printers;

import java.util.List;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PDFPrinter extends Printer implements ReportPrinter {

    private final int MARGINS = 36;
    
    private File file;
    
    private Document document;
    
    public PDFPrinter(File file) throws IOException {
        
        checkFile(file);
        
        this.file = file;
        
        document = new Document(PageSize.LETTER);
        document.open();            
        document.setMargins(
                MARGINS, MARGINS, MARGINS, MARGINS);
        
    }
    
    @Override
    public void printTitle(String title) throws IOException {
        
        Font font = FontFactory.getFont("Times-Roman", 24, Font.BOLD);
        Paragraph p = new Paragraph(title, font);
        p.setAlignment(Element.ALIGN_CENTER);
        
        try {            
            document.add(p);        
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    }        

    @Override
    public void printHeading(String heading) throws IOException {
        
        Font font = FontFactory.getFont("Times-Roman", 16, Font.BOLD);
        Paragraph p = new Paragraph(heading, font);
        p.setAlignment(Element.ALIGN_LEFT);
        
        try {                     
            document.add(p);        
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    }

    @Override
    public void printLine(String line) throws IOException {
        
        Paragraph p = new Paragraph(line);
        
        try {            
            document.add(p);        
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    }

    @Override
    public void printTable(List<String> columnHeadings,
            List<List<String>> records) throws IOException {
        
        //System.out.println(columnHeadings);
        //System.out.println(records);
        
        PdfPTable table = new PdfPTable(columnHeadings.size());
        table.setWidthPercentage(100);
        
        for (String columnHeading : columnHeadings) {
            
            Font font = FontFactory.getFont("Arial", 12, Font.BOLD);
            Phrase phrase = new Phrase(columnHeading, font);
            PdfPCell cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            
        }
        
        for (List<String> values : records) {
            
            for (String value : values) {

                Phrase phrase = new Phrase(value);
                PdfPCell cell = new PdfPCell(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

            }
            
        }
        
        try {
            document.add(table);
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    }

    @Override
    public void printBlankLine() throws IOException {
        
        Paragraph p = new Paragraph("\n");
        
        try {                       
            document.add(p);        
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        
    }

    @Override
    public void close(boolean displayFile) throws IOException {
        
        document.close();
        
        if (displayFile) {
            java.awt.Desktop.getDesktop().open(file);
        }
        
    }

}
