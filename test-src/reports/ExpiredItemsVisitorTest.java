package reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Barcode;
import model.Container;
import model.ContainerManager;
import model.Item;
import model.Model;
import model.Product;
import model.ProductGroup;
import model.StorageUnit;

import org.junit.Before;
import org.junit.Test;

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
//		containerManager = new ContainerManager();
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
//        Product apple = new Product("100", "Apple",
//                SizeUnits.Count, 1, 100, 1);
//        Product beans = new Product("200", "Beans",
//                SizeUnits.Count, 1, 1, 1); 
//        Product beans2 = new Product("200", "Beans",
//                SizeUnits.Count, 1, 10, 1);
//        Date entDate3 = dateFormat.parse("2013/6/27");
//        Date entDate2 = dateFormat.parse("2012/2/2");
//        Date entDate1 = dateFormat.parse("2012/3/11");        
//        Barcode bc1 = new Barcode("777");
//        Barcode bc2 = new Barcode("778");
//        Barcode bc3 = new Barcode("779");
//        Barcode bc4 = new Barcode("780");
//        
//        // --------------------------------
//        
//        Item i1 = new Item(null, apple, entDate3, bc1);        
//        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i1, su1);
//        
//        assertTrue(apple.getContainers().contains(su1));
//        assertEquals(su1, i1.getContainer());
//        
//        // --------------------------------
//        
//        Item i2 = new Item(grandChildProductGroup1_1, beans, entDate1, bc2);        
//        Model.getInstance().getProductManager().addProductToContainer(beans, grandChildProductGroup1_1);
//        Model.getInstance().getItemManager().addItem(i2);
//        
//        Item i3 = new Item(null, beans2, entDate1, bc3);        
//        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i3, su1);
//        
//        Item i4 = new Item(grandChildProductGroup1_1, beans2, entDate2,bc4);
//        Model.getInstance().getProductManager().addProductToContainer(beans, grandChildProductGroup1_1);
//        Model.getInstance().getItemManager().addItem(i4);
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
