package model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class ItemManagerTest {
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	@Test
	public void testAddingAndGetting() throws ParseException {
		
	    /*
		ItemManager man = new ItemManager();
		
		Container pc1 = new StorageUnit();
		Product p1 = new Product();
		Date expDate1 = dateFormat.parse("1999/1/11");
		Barcode bc1 = new Barcode("1");
		Item i1 = new Item(pc1, p1, expDate1, bc1);
		
		assertTrue(i1.getContainer() != null);
		assertTrue(man.canAddItem(i1, pc1));
		man.addItem(i1);
		assertEquals(man.getItemByTag(bc1), i1);
		assertTrue(man.getItems().contains(i1));
		assertTrue(man.getItems().size() == 1);
		assertTrue(man.getItems(pc1).size() == 1);
		assertTrue(man.getItems(pc1).contains(i1));
		assertTrue(man.getItems(pc1, p1).contains(i1));
		
		assertFalse(man.canAddItem(i1, pc1));
		
		Product p2 = new Product();
		Date expDate2 = dateFormat.parse("1999/3/11");
		Barcode bc2 = new Barcode("2");
		Item i2 = new Item(pc1, p2, expDate2, bc2);
		
		assertTrue(man.canAddItem(i2, pc1));
		
		man.addItem(i2);
		
		assertEquals(man.getItemByTag(bc2), i2);
		assertTrue(man.getItems().size() == 2);
		assertTrue(man.getItems().contains(i2));
		assertTrue(man.getItems(pc1).size() == 2);
		assertTrue(man.getItems(pc1).contains(i2));
		assertTrue(man.getItems(pc1, p2).contains(i2));
		
		assertFalse(man.canAddItem(i2, pc1));
		try {
			man.addItem(i2);
			assertTrue(false);
		}
		catch ( IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			man.addItem(null);
			assertTrue(false);
		}
		catch ( IllegalArgumentException e) {
			assertTrue(true);
		}
		
		
			
		assertFalse(man.canAddItem(i2, null));
		
		assertFalse(man.canAddItem(null, pc1));
		*/
		
	}
	
	@Test
	public void testAddingAndRemoving() throws ParseException{
		
	    /*
		ItemManager man = new ItemManager();
		
		Container pc1 = new StorageUnit();
		Product p1 = new Product();
		Date expDate1 = dateFormat.parse("1999/1/11");
		Barcode bc1 = new Barcode("1");
		Item i1 = new Item(pc1, p1, expDate1, bc1);
		
		man.addItem(i1);
		
		man.removeItem(i1);
		assertTrue(man.getRemovedItems(new Date()).size() == 1);
		assertTrue(man.getRemovedItems(new Date()).contains(i1));
		assertTrue(man.getItems().size() == 0);
		assertFalse(man.getItems(pc1).contains(i1));
		
		assertFalse(man.canAddItem(i1, pc1));
	}
	
	@Test
	public void testMovingItems() throws ParseException {
		ItemManager man = new ItemManager();
		
		Container pc1 = new StorageUnit();
		Product p1 = new Product();
		Date expDate1 = dateFormat.parse("1999/1/11");
		Barcode bc1 = new Barcode("1");
		Item i1 = new Item(pc1, p1, expDate1, bc1);
		
		man.addItem(i1);
		
		Container pc2 = new ProductGroup();
		
		assertEquals(i1.getContainer(), pc1);
		man.moveItem(i1, pc2);
		assertEquals(i1.getContainer(), pc2);
		assertFalse(man.getItems(pc1).contains(i1));
		assertTrue(man.getItems(pc2).contains(i1));
		*/
		
	}
	
	@Test
	public void testEditingItems() throws ParseException {
	    
	    /*
		ItemManager man = new ItemManager();
		
		Container pc1 = new StorageUnit();
		Product p1 = new Product();
		Date expDate1 = dateFormat.parse("1999/3/11");
		Barcode bc1 = new Barcode("1");
		Item i1 = new Item(pc1, p1, expDate1, bc1);
		i1.setEntryDate(dateFormat.parse("1999/1/11"));
		
		Item i2 = new Item(pc1, p1, expDate1, bc1);
		Date i2entDate = dateFormat.parse("1999/1/13");
		i2.setEntryDate( i2entDate);
		
		assertTrue(man.canEditItem(i1, i2));
		
		Container pc2 = new ProductGroup();
		Item i3 = new Item(pc2, p1, expDate1, bc1);
		
//		assertFalse(man.canEditItem(i1, i3));
		
		Product p2 = new Product();
		Item i4 = new Item(pc1, p2, expDate1, bc1);
		
//		assertFalse(man.canEditItem(i1, i4));
		
		Barcode bc2 = new Barcode("2");
		Item i5 = new Item(pc1, p2, expDate1, bc2);
		
//		assertFalse(man.canEditItem(i1, i5));
		
		man.editItem(i1, i2);
		assertTrue(i1.getEntryDate().equals(i2entDate));
		*/
		
	}

}
