package reports;

import static org.junit.Assert.*;
import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import model.Quantity;
import model.StorageUnit;

import org.junit.Before;
import org.junit.Test;

public class NoticesVisitorTest {

	private ContainerManager containerManager;
	private int uniqueId;
	private Set<StorageUnit> storageUnits;
	private StorageUnit su2;
	private StorageUnit su1;
	private ProductGroup grandChildProductGroup1_1;
	private ProductGroup productGroup2_1;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	@Before
	public void setUp() throws ParseException {
		
		containerManager = new ContainerManager();
		storageUnits = new TreeSet<StorageUnit>();
		uniqueId = 1;
		
		setupStorageUnit1();
		setupStorageUnit2();
		
		storageUnits.add( su1 );
		storageUnits.add( su2 );
		Model.getInstance().getContainerManager().setStorageUnits(storageUnits);
		
		containerManager.setStorageUnits( storageUnits );
        Product apple = new Product("100", "Apple",
                SizeUnits.Gallons, 2, 100, 1);
        Product beans = new Product("200", "Beans",
                SizeUnits.Liters, 16, 1, 1); 
        Product beans2 = new Product("200", "Beans2",
                SizeUnits.Count, 13.2f, 10, 1);
        Date entDate3 = dateFormat.parse("2013/6/27");
        Date entDate2 = dateFormat.parse("2012/2/2");
        Date entDate1 = dateFormat.parse("2012/3/11");     
        Barcode bc0 = new Barcode("776");
        Barcode bc1 = new Barcode("777");
        Barcode bc2 = new Barcode("778");
        Barcode bc3 = new Barcode("779");
        Barcode bc4 = new Barcode("780");
        
        // --------------------------------
        
        Item i0 = new Item(null, apple, entDate3, bc0);        
        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i0, su1);
        
        // --------------------------------
        grandChildProductGroup1_1.setThreeMonthSupply(new Quantity(3f, SizeUnits.Pounds));
        productGroup2_1.setThreeMonthSupply(new Quantity(2f, SizeUnits.FluidOunces));
        
        Item i1 = new Item(grandChildProductGroup1_1, apple, entDate1, bc1);        
        Model.getInstance().getProductManager().addProductToContainer(apple, grandChildProductGroup1_1);
        Model.getInstance().getItemManager().addItem(i1);
        
        Item i2 = new Item(grandChildProductGroup1_1, beans, entDate1, bc2);        
        Model.getInstance().getProductManager().addProductToContainer(beans, grandChildProductGroup1_1);
        Model.getInstance().getItemManager().addItem(i2);
        
        Item i3 = new Item(null, beans2, entDate1, bc3);        
        Model.getInstance().getProductAndItemEditor().addItemToStorageUnit(i3, su1);
        
        Item i4 = new Item(productGroup2_1, beans2, entDate2,bc4);
        Model.getInstance().getProductManager().addProductToContainer(beans2, productGroup2_1);
        Model.getInstance().getItemManager().addItem(i4);
		
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
	
	@Test
	public void noticesVisitorTest() {
		NoticesVisitor nv = new NoticesVisitor();
		List<Record> records = nv.visitAll();
		
		for(Record record : records) {
			System.out.println(record.getValuesAsStrings());
		}
		
		assertTrue(!records.isEmpty());
	}

}
