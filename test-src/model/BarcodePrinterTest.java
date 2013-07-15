package model;

import static org.junit.Assert.*;

import java.util.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import common.util.StringContstants;
import printers.BarcodePrinter;
import org.junit.Test;

public class BarcodePrinterTest {

    @Test
    public void test() throws Exception {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Container c1 = new ProductGroup();
        Product p1 = new Product(
                "1", "TopValu Mentsuyu 1000 mL asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf ",
                Unit.count, 1, 1, 1);
        Date ed1 = dateFormat.parse("2017/10/28");
        Barcode bc1;

        List<Item> items = new LinkedList<Item>();
        for (int i = 1; i <= 97; i++) {
            
            bc1 = new Barcode(String.format("%012d", i));
            
            Item item = new Item(c1, p1, ed1, bc1);
            items.add(item);
            
        }
        
        File file = new File(StringContstants.dataDir +
                File.separator + "barcode_tags.pdf");
        
        BarcodePrinter bc = new BarcodePrinter();
        
        bc.printBarcodes(items, file, true);
        
    }

}
