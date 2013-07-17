package model;

import gui.common.SizeUnits;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import printers.BarcodePrinter;
import org.junit.Test;

import config.IOConfig;

public class BarcodePrinterTest {

    @Test
    public void test() throws Exception {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Container c1 = new ProductGroup();
        Product p1 = new Product(
                "1", "TopValu Mentsuyu 1000 mL",
                SizeUnits.Count, 1, 1, 1);
=======
                "1", "TopValu Mentsuyu 1000 mL",
                Unit.count, 1, 1, 1);
>>>>>>> 478dd73402c3347bacba7fb15d98782ac6fd896e
=======
                "1", "TopValu Mentsuyu 1000 mL",
                SizeUnits.Count, 1, 1, 1);
>>>>>>> bbf1f945c567cab1bada366b72f7e2276604dda1
        Date ed1 = dateFormat.parse("2017/10/28");
        Barcode bc1;

        List<Item> items = new LinkedList<Item>();
        for (int i = 1; i <= 97; i++) {
            
            bc1 = new Barcode(String.format("%012d", i));
            
            Item item = new Item(c1, p1, ed1, bc1);
            items.add(item);
            
        }
        
        BarcodePrinter.printBarcodes(
                items, IOConfig.getBarcodeTagsFile(), true);
        
    }

}
