package model;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class ProductAndItemEditorTest {
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Test
    public void addItemTest() throws Exception {
        
        ContainerManager cman = new ContainerManager();
        ProductManager pman = new ProductManager();
        ItemManager man = new ItemManager();
        ProductAndItemEditor paie=
                new ProductAndItemEditor(
                        cman, pman, man);        
        
        Container pc1 = new StorageUnit();
        Product p1 = new Product(
                "123456789", "Descripshun", Unit.count, 1, 1, 1);

        Date expDate1 = dateFormat.parse("1999/1/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        
        assertFalse(i1.toString() == "");
        
        assertTrue(i1.getContainer() != null);
        assertTrue(man.canAddItem(i1, pc1));
        assertTrue(man.getItems(pc1).size() == 0);
        
        paie.addItem(i1);
        
        assertEquals(man.getItemByTag(bc1), i1);
        assertTrue(man.getItems().contains(i1));
        assertTrue(man.getItems().size() == 1);
        assertTrue(man.getItems(pc1).size() == 1);
        assertTrue(man.getItems(pc1).contains(i1));
        assertEquals(man.getItems(pc1, p1).size(), 1);
        assertTrue(man.getItems(pc1, p1).contains(i1));
        
        assertFalse(man.canAddItem(i1, pc1));
        
    }

}
