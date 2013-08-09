package reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import model.StorageUnit;

import org.junit.Before;
import org.junit.Test;

import data.SerDAOFactory;
import data.SerTransactionDAO;

public class ExpiredItemsVisitorTest {
	
//	private ContainerManager containerManager;
//	private int uniqueId;
//	private Set<StorageUnit> storageUnits;
//	private StorageUnit su2;
//	private StorageUnit su1;
//	private ProductGroup grandChildProductGroup1_1;
//	 private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//	
//	@Before
//	public void setUp() throws ParseException {
//		
//		Model.getInstance().setDAOFactory(new SerDAOFactory());
//		containerManager = new ContainerManager();
//		ProductManager productManager = new ProductManager();
//		ItemManager itemManager = new ItemManager();
//		Model.getInstance().setContainerManager(containerManager);
//		Model.getInstance().setProductManager(productManager);
//		Model.getInstance().setItemManager(itemManager);
//		Model.getInstance().setContainerEditor(new ContainerEditor(containerManager, itemManager));
//		Model.getInstance().setTransaction(new SerTransactionDAO());
//		 Model.getInstance().setProductAndItemEditor(
//	        		new ProductAndItemEditor(containerManager, productManager, itemManager));
//		storageUnits = new TreeSet<StorageUnit>();
//		uniqueId = 1;
//		
//		setupStorageUnit1();
//		setupStorageUnit2();
//		
//		storageUnits.add( su1 );
//		storageUnits.add( su2 );
//		Model.getInstance().getContainerManager().setStorageUnits(storageUnits);
//		
//		containerManager.setStorageUnits( storageUnits );
//          
//        Barcode bc0 = new Barcode("776");
//        Barcode bc1 = new Barcode("777");
//        Barcode bc2 = new Barcode("778");
//        Barcode bc3 = new Barcode("779");
//        Barcode bc4 = new Barcode("780");
//        
//		int supply = 0;
//		//Set up for products and items
//		Product apple = new Product("100", "Apple",
//                SizeUnits.Kilograms, 2, 0, supply);
//        Product beans = new Product("200", "Beans",
//                SizeUnits.Grams, 16, 0, supply); 
//        Product cherries = new Product("200", "Cherries",
//                SizeUnits.Grams, 13.2f, 0, supply);
//        Date entDate5 = null;
//        Date entDate4 = null;
//        Date entDate3 = null;
//        Date entDate2 = null;
//        Date entDate1 = null;
//		try {
//			entDate1 = dateFormat.parse("2013/8/4");
//			entDate2 = dateFormat.parse("2013/8/3");
//		    entDate3 = dateFormat.parse("2013/8/3");
//		    entDate4 = dateFormat.parse("2013/8/7");
//		    entDate5 = dateFormat.parse("2013/8/8");
//		} catch (ParseException e) {
//			fail("Threw an exception in dateFormat parsing");
//			e.printStackTrace();
//		}
//        
//        // --------------------------------
//        Item i4 = new Item(su2, cherries, entDate4, bc3);
//        addItemToTree(i4, su2, cherries);
//        
//		Item i1 = new Item(su1, apple, entDate1, bc0);
//        addItemToTree(i1, su1, apple);
//        
//        Item i3 = new Item(su2, beans, entDate3, bc2);
//        addItemToTree(i3, su2, beans);
//        
//        Item i2 = new Item(su1, apple, entDate2, bc1);
//        addItemToTree(i2, su1, apple);
//        
//        Item i5 = new Item(su1, cherries, entDate5, bc4);
//        addItemToTree(i5, su1, cherries);
//        // End product and item setup
//
//		
//	}
//	
//    private void addItemToTree(Item item, StorageUnit su, Product product) {
//		 List<Item> items = new ArrayList<Item>();
//	     items.add(item);
//	     Model.getInstance().getProductAndItemEditor().setNewlyAddedProduct(product);  
//	     AddItemsToSU addItem = new AddItemsToSU(items, su);
//	     addItem.execute();
//			
//	}
//	
//	private void setupStorageUnit2() {
//		su2 = initializeStorageUnit( "B", uniqueId++ );
//		
//		ProductGroup productGroup2_1 = initializeProductGroup( "B_a", uniqueId++, su2 );
//		ProductGroup productGroup2_2 = initializeProductGroup( "B_b", uniqueId++, su2 );
//		
//		ProductGroup childProductGroup2_1 = initializeProductGroup( "B_a_a", uniqueId++, productGroup2_1 );
//		ProductGroup childProductGroup2_2 = initializeProductGroup( "B_a_b", uniqueId++, productGroup2_1 );
//		
//		ProductGroup grandChildProductGroup2_1 = initializeProductGroup( "B_a_a_a", uniqueId++, childProductGroup2_1 );
//		ProductGroup grandChildProductGroup2_2 = initializeProductGroup( "B_a_a_b", uniqueId++, childProductGroup2_1 );
//		ProductGroup grandChildProductGroup2_3 = initializeProductGroup( "B_a_a_c", uniqueId++, childProductGroup2_1 );
//		
//		//Parent is su1
//		su2.addProductGroup( productGroup2_2 );
//		su2.addProductGroup( productGroup2_1 );
//		
//		//Parent is productGroup1_1
//		productGroup2_1.addProductGroup( childProductGroup2_1 );
//		productGroup2_1.addProductGroup( childProductGroup2_2 );
//		
//		//Parent is childProductGroup1_2
//		childProductGroup2_1.addProductGroup( grandChildProductGroup2_3 );
//		childProductGroup2_1.addProductGroup( grandChildProductGroup2_1 );
//		childProductGroup2_1.addProductGroup( grandChildProductGroup2_2 );
//		
//	}
//
//	private void setupStorageUnit1() {
//		su1 = initializeStorageUnit( "A", uniqueId++ );
//		
//		ProductGroup productGroup1_1 = initializeProductGroup( "A_a", uniqueId++, su1 );
//		ProductGroup productGroup1_2 = initializeProductGroup( "A_b", uniqueId++, su1 );
//		
//		ProductGroup childProductGroup1_1 = initializeProductGroup( "A_a_a", uniqueId++, productGroup1_1 );
//		ProductGroup childProductGroup1_2 = initializeProductGroup( "A_a_b", uniqueId++, productGroup1_1 );
//		
//		grandChildProductGroup1_1 = initializeProductGroup( "A_a_b_a", uniqueId++, childProductGroup1_2 );
//		ProductGroup grandChildProductGroup1_2 = initializeProductGroup( "A_a_b_b", uniqueId++, childProductGroup1_2 );
//		ProductGroup grandChildProductGroup1_3 = initializeProductGroup( "A_a_b_c", uniqueId++, childProductGroup1_2 );
//		
//		//Parent is su1
//		su1.addProductGroup( productGroup1_2 );
//		su1.addProductGroup( productGroup1_1 );
//		
//		//Parent is productGroup1_1
//		productGroup1_1.addProductGroup( childProductGroup1_1 );
//		productGroup1_1.addProductGroup( childProductGroup1_2 );
//		
//		//Parent is childProductGroup1_2
//		childProductGroup1_2.addProductGroup( grandChildProductGroup1_2 );
//		childProductGroup1_2.addProductGroup( grandChildProductGroup1_1 );
//		childProductGroup1_2.addProductGroup( grandChildProductGroup1_3 );	
//	}
//
//	private StorageUnit initializeStorageUnit( String name, int id ) {
//		StorageUnit su = new StorageUnit();
//		su.setName( name );
//		su.setId( id );
//		su.setContainer( null );
//		return su;
//	}
//	
//	private ProductGroup initializeProductGroup( String name, int id, Container container ) {
//		ProductGroup pg = new ProductGroup();
//		pg.setName( name );
//		pg.setId( id );
//		pg.setContainer( container );
//		return pg;
//	}
//	
//	@Test
//	public void expiredItemsVisitorTest() {
//		
//		ExpiredItemsVisitor eiv = new ExpiredItemsVisitor();
//		List<Record> records = eiv.visitAll();
//		
//		for(Record record : records) {
//			System.out.println(record.getValuesAsStrings());
//		}
//		
//		assertEquals(records.size(), 3);
//	}
}
