package model;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class ProductAndItemEditorTest {
    
    private ContainerManager cman;
    private ProductManager pman;
    private ItemManager iman;
    private ProductAndItemEditor paie;
    
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    @Before
    public void setUp() {
        
        cman = new ContainerManager();
        pman = new ProductManager();
        iman = new ItemManager();
        paie = new ProductAndItemEditor(cman, pman, iman); 
        
    }

    @Test
    public void addItemTest() throws Exception {     
        
        Container pc1 = new StorageUnit();
        Product p1 = new Product(
                "123456789", "Descripshun", Unit.count, 1, 1, 1);

        Date expDate1 = dateFormat.parse("1999/1/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        
        paie.addItem(i1);
        
        assertEquals(iman.getItemByTag(bc1), i1);
        assertTrue(iman.getItems().contains(i1));
        assertTrue(iman.getItems().size() == 1);
        assertTrue(iman.getItems(pc1).size() == 1);
        assertTrue(iman.getItems(pc1).contains(i1));
        assertEquals(iman.getItems(pc1, p1).size(), 1);
        assertTrue(iman.getItems(pc1, p1).contains(i1));      
        assertFalse(iman.canAddItem(i1, pc1));
        
    }
    
    @Test
    public void editItemTest() throws Exception {
        
        Container pc1 = new StorageUnit();
        Product p1 = new Product("123456789", "Descripshun",
                Unit.count, 1, 1, 1);
        Date expDate1 = dateFormat.parse("1999/3/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        i1.setEntryDate(dateFormat.parse("1999/1/11"));
        
        Item i2 = new Item(pc1, p1, expDate1, bc1);
        Date i2entDate = dateFormat.parse("1999/1/13");
        i2.setEntryDate(i2entDate);
        
        paie.editItem(i1, i2);
        assertTrue(i1.getEntryDate().equals(i2entDate));
        
    }
    
    @Test
    public void moveItemTest() throws Exception {
        
        Container pc1 = new StorageUnit();
        Product p1 = new Product("123456789", "Descripshun", Unit.count, 1, 1, 1);
        Date expDate1 = dateFormat.parse("1999/1/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        
        paie.addItem(i1);
        
        Container pc2 = new ProductGroup();
        
        assertEquals(i1.getContainer(), pc1);
        paie.moveItem(i1, pc2);
        assertEquals(i1.getContainer(), pc2);
        assertFalse(iman.getItems(pc1).contains(i1));
        assertTrue(iman.getItems(pc2).contains(i1));
        
    }
    
    @Test
    public void removeItemTest() throws Exception {
        
        Container pc1 = new StorageUnit();
        Product p1 = new Product("123456789", "Descripshun",
                Unit.count, 1, 1, 1);
        Date expDate1 = dateFormat.parse("1999/1/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        
        paie.addItem(i1);
        
        paie.removeItem(i1);
        assertEquals(1, iman.getRemovedItems().size());
        assertTrue(iman.getRemovedItems().contains(i1));
        assertTrue(iman.getRemovedItems(new Date()).size() == 1);
        assertTrue(iman.getRemovedItems(new Date()).contains(i1));
        
        assertTrue(iman.getItems().size() == 0);
        assertFalse(iman.getItems(pc1).contains(i1));
        
        assertFalse(iman.canAddItem(i1, pc1));
        
    }

}
