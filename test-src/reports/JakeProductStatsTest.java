package reports;

import static org.junit.Assert.*;
import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.AddItemsToSU;
import model.Barcode;
import model.Container;
import model.ContainerEditor;
import model.ContainerManager;
import model.Item;
import model.ItemManager;
import model.Model;
import model.Product;
import model.ProductAndItemEditor;
import model.ProductGroup;
import model.ProductManager;
import model.Quantity;
import model.RemoveItemFromSU;
import model.StorageUnit;

import org.junit.Before;
import org.junit.Test;

import data.SerDAOFactory;
import data.SerTransactionDAO;
/**
 * 
 * @author jake
 *
 */
public class JakeProductStatsTest {

	private ContainerManager containerManager;
	private ProductManager productManager;
	private ItemManager itemManager;
	private int uniqueId;
	private Set<StorageUnit> storageUnits;
	private StorageUnit su2;
	private StorageUnit su1;
	private ProductGroup grandChildProductGroup1_1;
	private ProductGroup productGroup2_1;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Barcode bc0;
    private Barcode bc1;
    private Barcode bc2;
    private Barcode bc3;
    private Barcode bc4;
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	//		SETUP TREE STRUCTURE WITH STORAGEUNITS AND PRODUCTGROUPS
	///////////////////////////////////////////////////////////////////////////////////////
	
	@Before
	public void setUp() {
		Model.getInstance().setDAOFactory(new SerDAOFactory());
		containerManager = new ContainerManager();
		productManager = new ProductManager();
		itemManager = new ItemManager();
		Model.getInstance().setContainerManager(containerManager);
		Model.getInstance().setProductManager(productManager);
		Model.getInstance().setItemManager(itemManager);
		Model.getInstance().setContainerEditor(new ContainerEditor(containerManager, itemManager));
		Model.getInstance().setTransaction(new SerTransactionDAO());
		 Model.getInstance().setProductAndItemEditor(
	        		new ProductAndItemEditor(containerManager, productManager, itemManager));
		
		storageUnits = new TreeSet<StorageUnit>();
		uniqueId = 1;
		
		setupStorageUnit1();
		setupStorageUnit2();
		
		storageUnits.add( su1 );
		storageUnits.add( su2 );
		Model.getInstance().getContainerManager().setStorageUnits(storageUnits);
		
		grandChildProductGroup1_1.setThreeMonthSupply(new Quantity(3f, SizeUnits.Pounds));
	    productGroup2_1.setThreeMonthSupply(new Quantity(2f, SizeUnits.Count));
	    
	    bc0 = new Barcode("776");
	    bc1 = new Barcode("777");
	    bc2 = new Barcode("778");
	    bc3 = new Barcode("779");
	    bc4 = new Barcode("780");
		
		containerManager.setStorageUnits( storageUnits );
	}
	
	private void setupStorageUnit2() {
		su2 = initializeStorageUnit( "B", uniqueId++ );
		
		productGroup2_1 = initializeProductGroup( "B_a", uniqueId++, su2 );
		ProductGroup productGroup2_2 = initializeProductGroup( "B_b", uniqueId++, su2 );
		
		ProductGroup childProductGroup2_1 = initializeProductGroup( "B_a_a", uniqueId++, productGroup2_1 );
		ProductGroup childProductGroup2_2 = initializeProductGroup( "B_a_b", uniqueId++, productGroup2_1 );
		
		ProductGroup grandChildProductGroup2_1 = initializeProductGroup( "B_a_a_a", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_2 = initializeProductGroup( "B_a_a_b", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_3 = initializeProductGroup( "B_a_a_c", uniqueId++, childProductGroup2_1 );
		
		//Parent is su1
		su2.addProductGroup( productGroup2_2 );
		su2.addProductGroup( productGroup2_1 );
		
		//Parent is productGroup1_1
		productGroup2_1.addProductGroup( childProductGroup2_1 );
		productGroup2_1.addProductGroup( childProductGroup2_2 );
		
		//Parent is childProductGroup1_2
		childProductGroup2_1.addProductGroup( grandChildProductGroup2_3 );
		childProductGroup2_1.addProductGroup( grandChildProductGroup2_1 );
		childProductGroup2_1.addProductGroup( grandChildProductGroup2_2 );
		
	}

	private void setupStorageUnit1() {
		su1 = initializeStorageUnit( "A", uniqueId++ );
		
		ProductGroup productGroup1_1 = initializeProductGroup( "A_a", uniqueId++, su1 );
		ProductGroup productGroup1_2 = initializeProductGroup( "A_b", uniqueId++, su1 );
		
		ProductGroup childProductGroup1_1 = initializeProductGroup( "A_a_a", uniqueId++, productGroup1_1 );
		ProductGroup childProductGroup1_2 = initializeProductGroup( "A_a_b", uniqueId++, productGroup1_1 );
		
		grandChildProductGroup1_1 = initializeProductGroup( "A_a_b_a", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_2 = initializeProductGroup( "A_a_b_b", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_3 = initializeProductGroup( "A_a_b_c", uniqueId++, childProductGroup1_2 );
		
		//Parent is su1
		su1.addProductGroup( productGroup1_2 );
		su1.addProductGroup( productGroup1_1 );
		
		//Parent is productGroup1_1
		productGroup1_1.addProductGroup( childProductGroup1_1 );
		productGroup1_1.addProductGroup( childProductGroup1_2 );
		
		//Parent is childProductGroup1_2
		childProductGroup1_2.addProductGroup( grandChildProductGroup1_2 );
		childProductGroup1_2.addProductGroup( grandChildProductGroup1_1 );
		childProductGroup1_2.addProductGroup( grandChildProductGroup1_3 );	
	}

	private StorageUnit initializeStorageUnit( String name, int id ) {
		StorageUnit su = new StorageUnit();
		su.setName( name );
		su.setId( id );
		su.setContainer( null );
		return su;
	}
	
	private ProductGroup initializeProductGroup( String name, int id, Container container ) {
		ProductGroup pg = new ProductGroup();
		pg.setName( name );
		pg.setId( id );
		pg.setContainer( container );
		return pg;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//		                   END OF THE TREE STRUCTURE SETUP 
	///////////////////////////////////////////////////////////////////////////////////////
	
	/*

	Testing the following cases:
	
	Test 6: Test items used in a reverse age order during the reporting period
	
	Test 7: Test a product in various states during the reporting period
		a.	Test a product with no activity during the reporting period
		b.	Test a product pegged at zero during the whole reporting period
		c.	Have the product count only go up during the reporting period
		d.	Have the product count only go down during the reporting period
	
	Test 8:  Test adding products and items only before the reporting period
	
	Test 9: Test to make sure that products are sorted by description
	
	Test 10: Test a product with and without a specified 3-Month supply

	 */
	
	@Test
	public void itemsUsedInReverseAgeOrderTest() {
		System.out.println("Testing items used in reverse age order along with product increase");
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 1, 1); 
        Product beans2 = new Product("200", "Beans",
                SizeUnits.Grams, 13.2f, 10, 1);
        Date entDate5 = null;
        Date entDate4 = null;
        Date entDate3 = null;
        Date entDate2 = null;
        Date entDate1 = null;
		try {
			entDate1 = dateFormat.parse("2013/8/1");
			entDate2 = dateFormat.parse("2013/8/2");
		    entDate3 = dateFormat.parse("2013/8/3");
		    entDate4 = dateFormat.parse("2013/8/4");
		    entDate5 = dateFormat.parse("2013/8/5");
		} catch (ParseException e) {
			fail("Threw an exception in dateFormat parsing");
			e.printStackTrace();
		}
        
        // --------------------------------
        
		Item i1 = new Item(su1, apple, entDate1, bc0);
        addItemToTree(i1, su1, apple);
        
        Item i2 = new Item(su1, apple, entDate2, bc1);
        addItemToTree(i2, su1, apple);
        
        Item i3 = new Item(su2, beans, entDate3, bc2);
        addItemToTree(i3, su2, beans);
        
        Item i4 = new Item(su2, beans2, entDate4, bc3);
        addItemToTree(i4, su2, beans2);
        
        Item i5 = new Item(su1, beans2, entDate5, bc4);
        addItemToTree(i5, su1, beans2);
        
        // End product and item setup
        
        //Removing Items in Reverse Order
        RemoveItemFromSU removeItem1 = new RemoveItemFromSU(i5);
        removeItem1.execute();
        RemoveItemFromSU removeItem2 = new RemoveItemFromSU(i4);
        removeItem2.execute();
        RemoveItemFromSU removeItem3 = new RemoveItemFromSU(i3);
        removeItem3.execute();
        RemoveItemFromSU removeItem4 = new RemoveItemFromSU(i2);
        removeItem4.execute();
        RemoveItemFromSU removeItem5 = new RemoveItemFromSU(i1);
        removeItem5.execute();
       
        
        ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	assertTrue(((ProductStatRecord)record).getUsedSupply() > 1);
        	System.out.println(record.getValuesAsStrings());
        }
	}

	private void addItemToTree(Item item, StorageUnit su, Product product) {
	 List<Item> items = new ArrayList<Item>();
     items.add(item);
     Model.getInstance().getProductAndItemEditor().setNewlyAddedProduct(product);  
     AddItemsToSU addItem = new AddItemsToSU(items, su);
     addItem.execute();
		
	}


	@Test
	public void productWithNoactivityTest() {
		System.out.println("Testing products with no activity");
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 1, 1); 
        Product beans2 = new Product("200", "Beans",
                SizeUnits.Grams, 13.2f, 10, 1);
		Model.getInstance().getProductAndItemEditor().setNewlyAddedProduct(apple); 
		Model.getInstance().getProductAndItemEditor().setNewlyAddedProduct(beans); 
		Model.getInstance().getProductAndItemEditor().setNewlyAddedProduct(beans2); 
		ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	System.out.println(record.getValuesAsStrings());
        }
        assertTrue(actualRecords.isEmpty());
	}
	
	@Test
	public void productAtZeroTest() {
		System.out.println("Testing where there are no products in the system");
		ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	System.out.println(record.getValuesAsStrings());
        }
        assertTrue(actualRecords.isEmpty());
	}

	@Test
	public void productCountDecreaseTest() {
		System.out.println("Testing removing products");
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 1, 1); 
        Product beans2 = new Product("200", "Beans",
                SizeUnits.Grams, 13.2f, 10, 1);
        Date entDate5 = null;
        Date entDate4 = null;
        Date entDate3 = null;
        Date entDate2 = null;
        Date entDate1 = null;
		try {
			entDate1 = dateFormat.parse("2012/8/1");
			entDate2 = dateFormat.parse("2012/8/2");
		    entDate3 = dateFormat.parse("2012/8/3");
		    entDate4 = dateFormat.parse("2012/8/4");
		    entDate5 = dateFormat.parse("2012/8/5");
		} catch (ParseException e) {
			fail("Threw an exception in dateFormat parsing");
			e.printStackTrace();
		}
        
        // --------------------------------
        
		Item i1 = new Item(su1, apple, entDate1, bc0);
        addItemToTree(i1, su1, apple);
        
        Item i2 = new Item(su1, apple, entDate2, bc1);
        addItemToTree(i2, su1, apple);
        
        Item i3 = new Item(su2, beans, entDate3, bc2);
        addItemToTree(i3, su2, beans);
        
        Item i4 = new Item(su2, beans2, entDate4, bc3);
        addItemToTree(i4, su2, beans2);
        
        Item i5 = new Item(su1, beans2, entDate5, bc4);
        addItemToTree(i5, su1, beans2);
        
        // End product and item setup
        
        Model.getInstance().getProductAndItemEditor().removeProductFromContainer(apple, su1);
        Model.getInstance().getProductAndItemEditor().removeProductFromContainer(beans, su2);
        
        ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	assertTrue(((ProductStatRecord)record).getUsedSupply() == 0);
        	assertTrue(((ProductStatRecord)record).getAddedSupply() == 0);
        	System.out.println(record.getValuesAsStrings());
        }
	}
	
	@Test
	public void addingProductsAndItemsBeforeTest() {
		System.out.println("Testing products and items added before report period");
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 1, 1); 
        Product cherries = new Product("200", "Cherries",
                SizeUnits.Grams, 13.2f, 10, 1);
        Date entDate5 = null;
        Date entDate4 = null;
        Date entDate3 = null;
        Date entDate2 = null;
        Date entDate1 = null;
		try {
			//Dates are before report
			entDate1 = dateFormat.parse("2012/8/4");
			entDate2 = dateFormat.parse("2012/8/3");
		    entDate3 = dateFormat.parse("2012/8/3");
		    entDate4 = dateFormat.parse("2012/8/7");
		    entDate5 = dateFormat.parse("2012/8/8");
		} catch (ParseException e) {
			fail("Threw an exception in dateFormat parsing");
			e.printStackTrace();
		}
		
        // --------------------------------
        Item i4 = new Item(su2, cherries, entDate4, bc3);
        addItemToTree(i4, su2, cherries);
        
		Item i1 = new Item(su1, apple, entDate1, bc0);
        addItemToTree(i1, su1, apple);
        
        Item i3 = new Item(su2, beans, entDate3, bc2);
        addItemToTree(i3, su2, beans);
        
        Item i2 = new Item(su1, apple, entDate2, bc1);
        addItemToTree(i2, su1, apple);
        
        Item i5 = new Item(su1, cherries, entDate5, bc4);
        addItemToTree(i5, su1, cherries);
        // End product and item setup
       
        
        ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	assertTrue(((ProductStatRecord)record).getUsedSupply() == 0);
        	assertTrue(((ProductStatRecord)record).getAddedSupply() == 0);
        	System.out.println(record.getValuesAsStrings());
        }
	}
	
	@Test
	public void productsSortedByDescriptionTest() {
		System.out.println("Testing Product sorted by description");
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 1, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 1, 1); 
        Product cherries = new Product("200", "Cherries",
                SizeUnits.Grams, 13.2f, 10, 1);
        Date entDate5 = null;
        Date entDate4 = null;
        Date entDate3 = null;
        Date entDate2 = null;
        Date entDate1 = null;
		try {
			entDate1 = dateFormat.parse("2013/8/4");
			entDate2 = dateFormat.parse("2013/8/3");
		    entDate3 = dateFormat.parse("2013/8/3");
		    entDate4 = dateFormat.parse("2013/8/7");
		    entDate5 = dateFormat.parse("2013/8/8");
		} catch (ParseException e) {
			fail("Threw an exception in dateFormat parsing");
			e.printStackTrace();
		}
		
        // --------------------------------
        Item i4 = new Item(su2, cherries, entDate4, bc3);
        addItemToTree(i4, su2, cherries);
        
		Item i1 = new Item(su1, apple, entDate1, bc0);
        addItemToTree(i1, su1, apple);
        
        Item i3 = new Item(su2, beans, entDate3, bc2);
        addItemToTree(i3, su2, beans);
        
        Item i2 = new Item(su1, apple, entDate2, bc1);
        addItemToTree(i2, su1, apple);
        
        Item i5 = new Item(su1, cherries, entDate5, bc4);
        addItemToTree(i5, su1, cherries);
        // End product and item setup
       
        
        ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        List<String> actualDescriptions = new ArrayList<String>();
        for(Record record : actualRecords) {
        	actualDescriptions.add(((ProductStatRecord)record).getDescription());
        	System.out.println(record.getValuesAsStrings());
        }
        List<String> expected = new ArrayList<String>();
        expected.add("Apple");
        expected.add("Beans");
        expected.add("Cherries");
        assertEquals(expected, actualDescriptions);
        System.out.println(actualDescriptions); 
	}
	
	@Test
	public void productWithThreeMonthSupply() {
		System.out.println("Testing with three month supply");
		int supply = 4;
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 1, supply);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 1, supply++); 
        Product cherries = new Product("200", "Cherries",
                SizeUnits.Grams, 13.2f, 1, supply++);
        Date entDate5 = null;
        Date entDate4 = null;
        Date entDate3 = null;
        Date entDate2 = null;
        Date entDate1 = null;
		try {
			entDate1 = dateFormat.parse("2013/8/4");
			entDate2 = dateFormat.parse("2013/8/3");
		    entDate3 = dateFormat.parse("2013/8/3");
		    entDate4 = dateFormat.parse("2013/8/7");
		    entDate5 = dateFormat.parse("2013/8/8");
		} catch (ParseException e) {
			fail("Threw an exception in dateFormat parsing");
			e.printStackTrace();
		}
        
        // --------------------------------
        Item i4 = new Item(su2, cherries, entDate4, bc3);
        addItemToTree(i4, su2, cherries);
        
		Item i1 = new Item(su1, apple, entDate1, bc0);
        addItemToTree(i1, su1, apple);
        
        Item i3 = new Item(su2, beans, entDate3, bc2);
        addItemToTree(i3, su2, beans);
        
        Item i2 = new Item(su1, apple, entDate2, bc1);
        addItemToTree(i2, su1, apple);
        
        Item i5 = new Item(su1, cherries, entDate5, bc4);
        addItemToTree(i5, su1, cherries);
        // End product and item setup
        
        ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	assertTrue(((ProductStatRecord)record).getThreeMonthSupply() != 0);
        	System.out.println(record.getValuesAsStrings());
        }
	}
	
	@Test 
	public void productWithOutThreeMonthSupply() {
		System.out.println("Testing without three month supply");
		int supply = 0;
		//Set up for products and items
		Product apple = new Product("100", "Apple",
                SizeUnits.Kilograms, 2, 0, supply);
        Product beans = new Product("200", "Beans",
                SizeUnits.Grams, 16, 0, supply); 
        Product cherries = new Product("200", "Cherries",
                SizeUnits.Grams, 13.2f, 0, supply);
        Date entDate5 = null;
        Date entDate4 = null;
        Date entDate3 = null;
        Date entDate2 = null;
        Date entDate1 = null;
		try {
			entDate1 = dateFormat.parse("2013/8/4");
			entDate2 = dateFormat.parse("2013/8/3");
		    entDate3 = dateFormat.parse("2013/8/3");
		    entDate4 = dateFormat.parse("2013/8/7");
		    entDate5 = dateFormat.parse("2013/8/8");
		} catch (ParseException e) {
			fail("Threw an exception in dateFormat parsing");
			e.printStackTrace();
		}
        
        // --------------------------------
        Item i4 = new Item(su2, cherries, entDate4, bc3);
        addItemToTree(i4, su2, cherries);
        
		Item i1 = new Item(su1, apple, entDate1, bc0);
        addItemToTree(i1, su1, apple);
        
        Item i3 = new Item(su2, beans, entDate3, bc2);
        addItemToTree(i3, su2, beans);
        
        Item i2 = new Item(su1, apple, entDate2, bc1);
        addItemToTree(i2, su1, apple);
        
        Item i5 = new Item(su1, cherries, entDate5, bc4);
        addItemToTree(i5, su1, cherries);
        // End product and item setup
        
        ProductStatVisitor productStatVisitor = new ProductStatVisitor(1);
        List<Record> actualRecords =productStatVisitor.visitAll();
        for(Record record : actualRecords) {
        	assertTrue(((ProductStatRecord)record).getThreeMonthSupply() == 0);
        	System.out.println(record.getValuesAsStrings());
        }
	}


}
