package model;
import static org.junit.Assert.*;

import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class ProductAndItemEditorTest {
    
//    private ContainerManager cman;
//    private ProductManager pman;
//    private ItemManager iman;
//    private ProductAndItemEditor Model.getInstance().getProductManager();
        
    private Set<StorageUnit> storageUnits;
    private int uniqueId;
    
    private StorageUnit su1;
    
    private ProductGroup productGroup1_1;
    private ProductGroup productGroup1_2;
    private ProductGroup childProductGroup1_1;
    private ProductGroup childProductGroup1_2;
    private ProductGroup grandChildProductGroup1_1;
    private ProductGroup grandChildProductGroup1_2;
    private ProductGroup grandChildProductGroup1_3;
    
    private StorageUnit su2;
    
    private ProductGroup productGroup2_1;
    private ProductGroup productGroup2_2;
    
    private ProductGroup childProductGroup2_1;
    private ProductGroup childProductGroup2_2;
    
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    @Before
    public void setUp() {
        
//        cman = new ContainerManager();
//        pman = new ProductManager();
//        iman = new ItemManager();
//        paie = new ProductAndItemEditor(cman, pman, iman);
//    	cman = Model.getInstance().getContainerManager();
//    	pman = Model.getInstance().getProductManager();
//    	iman = Model.getInstance().getItemManager();
//    	paie = new ProductAndItemEditor(cman, pman, iman);
        
        setupStorageUnit1();
        setupStorageUnit2();
        
    }
    
    private void setupStorageUnit1() {
        
        su1 = initializeStorageUnit( "su1", uniqueId++ );
        
        productGroup1_1 = initializeProductGroup(
                "pg1_1", uniqueId++, su1 );
        productGroup1_2 = initializeProductGroup(
                "pg1_2", uniqueId++, su1 );
        
        childProductGroup1_1 = initializeProductGroup(
                "cpg1_1", uniqueId++, productGroup1_1 );
        childProductGroup1_2 = initializeProductGroup(
                "cpg1_2", uniqueId++, productGroup1_1 );
        
        grandChildProductGroup1_1 = initializeProductGroup(
                "gcpg1_1", uniqueId++, childProductGroup1_2 );
        grandChildProductGroup1_2 = initializeProductGroup(
                "gcpg1_2", uniqueId++, childProductGroup1_2 );
        grandChildProductGroup1_3 = initializeProductGroup(
                "gcpg1_3", uniqueId++, childProductGroup1_2 );
        
        //Parent is su1
        su1.addProductGroup( productGroup1_1 );
        su1.addProductGroup( productGroup1_2 );
        
        //Parent is productGroup1_1
        productGroup1_1.addProductGroup( childProductGroup1_1 );
        productGroup1_1.addProductGroup( childProductGroup1_2 );
        
        //Parent is childProductGroup1_2
        childProductGroup1_2.addProductGroup( grandChildProductGroup1_1 );
        childProductGroup1_2.addProductGroup( grandChildProductGroup1_2 );
        childProductGroup1_2.addProductGroup( grandChildProductGroup1_3 ); 
        
    }

    private void setupStorageUnit2() {
        
        su2 = initializeStorageUnit( "su2", uniqueId++ );
        
        productGroup2_1 =
                initializeProductGroup( "pg2_1", uniqueId++, su2 );
        productGroup2_2 =
                initializeProductGroup( "pg2_2", uniqueId++, su2 );
        
        childProductGroup2_1 =
                initializeProductGroup( "cpg2_1", uniqueId++, productGroup2_1 );
        childProductGroup2_2 =
                initializeProductGroup( "cpg2_2", uniqueId++, productGroup2_1 );
        
        ProductGroup grandChildProductGroup2_1 = initializeProductGroup(
                "gcpg2_1", uniqueId++, childProductGroup2_1 );
        ProductGroup grandChildProductGroup2_2 = initializeProductGroup(
                "gcpg2_2", uniqueId++, childProductGroup2_1 );
        ProductGroup grandChildProductGroup2_3 = initializeProductGroup(
                "gcpg2_3", uniqueId++, childProductGroup2_1 );
        
        //Parent is su2
        su2.addProductGroup( productGroup2_1 );
        su2.addProductGroup( productGroup2_2 );
        
        //Parent is productGroup2_1
        productGroup2_1.addProductGroup( childProductGroup2_1 );
        productGroup2_1.addProductGroup( childProductGroup2_2 );
        
        //Parent is childProductGroup2_1
        childProductGroup2_1.addProductGroup( grandChildProductGroup2_1 );
        childProductGroup2_1.addProductGroup( grandChildProductGroup2_2 );
        childProductGroup2_1.addProductGroup( grandChildProductGroup2_3 );
       
    }

    private StorageUnit initializeStorageUnit( String name, int id ) {
        
        StorageUnit su = new StorageUnit();
        su.setName( name );
        su.setId( id );
        su.setContainer( null );
        return su;
        
    }
    
    private ProductGroup initializeProductGroup(
            String name, int id, Container container ) {
        
        ProductGroup pg = new ProductGroup();
        pg.setName( name );
        pg.setId( id );
        pg.setContainer( container );
        return pg;
    
    }

    @Test
    public void addItemTest() throws Exception {     
        
        Product apple = new Product("100", "Apple",
                SizeUnits.Count, 1, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Count, 1, 1, 1);                
        Date entDate1 = dateFormat.parse("1999/3/11");        
        Barcode bc1 = new Barcode("777");
        Barcode bc2 = new Barcode("778");
        Barcode bc3 = new Barcode("779");
        
        // --------------------------------
        
        Item i1 = new Item(null, apple, entDate1, bc1);        
        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i1, su1);
        
        assertTrue(apple.getContainers().contains(su1));
        assertEquals(su1, i1.getContainer());
        
        // --------------------------------
        
        Item i2 = new Item(grandChildProductGroup1_1, beans, entDate1, bc2);        
        Model.getInstance().getProductManager().addProductToContainer(beans, grandChildProductGroup1_1);
        Model.getInstance().getItemManager().addItem(i2);
        
        Item i3 = new Item(null, beans, entDate1, bc3);        
        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i3, su1);
        
        assertEquals(grandChildProductGroup1_1, i3.getContainer());
        
    }
    
    @Test
    public void transferItemTest1() throws Exception {     
        
        Product apple = new Product("100", "Apple",
                SizeUnits.Count, 1, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Count, 1, 1, 1);                
        Date entDate1 = dateFormat.parse("1999/3/11");
        Date entDate2 = dateFormat.parse("2000/3/11");
        Date entDate3 = dateFormat.parse("2005/3/11");
        Barcode bc1 = new Barcode("777");
        Barcode bc2 = new Barcode("800");
        Barcode bc3 = new Barcode("888");
        
        // --------------------------------
        
        Item i1 = new Item(null, apple, entDate1, bc1);      
        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i1, su1);
        
        Model.getInstance().getProductAndItemEditor().transferItemToStorageUnit(i1, su2);
        
        assertEquals(su2, i1.getContainer());
        assertTrue(Model.getInstance().getItemManager().getItems(su2).contains(i1));
        assertFalse(Model.getInstance().getItemManager().getItems(su1).contains(i1));
        
    }
    
    @Test
    public void transferItemTest2() throws Exception {     
        
        Product apple = new Product("100", "Apple",
                SizeUnits.Count, 1, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Count, 1, 1, 1);                
        Date entDate1 = dateFormat.parse("1999/3/11");
        Date entDate2 = dateFormat.parse("2000/3/11");
        Date entDate3 = dateFormat.parse("2005/3/11");
        Barcode bc1 = new Barcode("777");
        Barcode bc2 = new Barcode("800");
        Barcode bc3 = new Barcode("888");
        
        // --------------------------------
        
        Item i1 = new Item(su1, apple, entDate1, bc1);                      
        Model.getInstance().getProductManager().addProductToContainer(apple, su1);
        Model.getInstance().getItemManager().addItem(i1);
        
        Item i2 = new Item(childProductGroup2_1, apple, entDate2, bc2);      
        Model.getInstance().getProductManager().addProductToContainer(apple, childProductGroup2_1);
        Model.getInstance().getItemManager().addItem(i2);
        
        Model.getInstance().getProductAndItemEditor().transferItemToStorageUnit(i1, su2);
        
        assertEquals(childProductGroup2_1, i1.getContainer());
        
    }
    
    @Test
    public void moveItemTest() throws Exception {
        
        
        
    }

    @Test
    public void editItemTest() throws Exception {
        
        Container pc1 = new StorageUnit();
        Product p1 = new Product("123456789", "Descripshun",
                SizeUnits.Count, 1, 1, 1);
        Date expDate1 = dateFormat.parse("1999/3/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        i1.setEntryDate(dateFormat.parse("1999/1/11"));
        
        Item i2 = new Item(pc1, p1, expDate1, bc1);
        Date i2entDate = dateFormat.parse("1999/1/13");
        i2.setEntryDate(i2entDate);
        
        Model.getInstance().getProductAndItemEditor().editItem(i1, i2);
        assertTrue(i1.getEntryDate().equals(i2entDate));
        
    }
    
    @Test
    public void removeItemTest() throws Exception {
        
        StorageUnit pc1 = new StorageUnit();
        Product p1 = new Product("123456789", "Descripshun",
                SizeUnits.Count, 1, 1, 1);
        Date expDate1 = dateFormat.parse("1999/1/11");
        Barcode bc1 = new Barcode("1");
        Item i1 = new Item(pc1, p1, expDate1, bc1);
        
        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i1, pc1);
        
        Model.getInstance().getProductAndItemEditor().removeItem(i1);
        assertEquals(1, Model.getInstance().getItemManager().getRemovedItems().size());
        assertTrue(Model.getInstance().getItemManager().getRemovedItems().contains(i1));
        assertTrue(Model.getInstance().getItemManager().getRemovedItems(new Date()).size() == 1);
        assertTrue(Model.getInstance().getItemManager().getRemovedItems(new Date()).contains(i1));
        
//        assertEquals(0, Model.getInstance().getItemManager().getItems().size());
        assertFalse(Model.getInstance().getItemManager().getItems(pc1).contains(i1));
        
        assertFalse(Model.getInstance().getItemManager().canAddItem(i1, pc1));
        
    }

}
