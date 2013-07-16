package model;

import static org.junit.Assert.*;

import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class ProductTest {

	public void setterGetterTest() throws ParseException {
		
		Container container = new ProductGroup();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date ed1 = dateFormat.parse("2013/12/9");
		Date ed2 = dateFormat.parse("2013/1/1");
		Barcode bc1 = new Barcode("Tag");
		Product prod1 = new Product("UPC1", "Description1", SizeUnits.Count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description2", SizeUnits.Count, 1,1,1);
		Item i1 = new Item(container, prod1, ed1, bc1);
		
		/* Testing Setters and Getters */
		prod1.setDescription("Changed");
		prod1.setUPC("ChangedUPC");
		prod1.setSize(SizeUnits.FluidOunces, 12);
		prod1.setShelfLife(10);
		prod1.setThreeMonthSupply(15);
		prod1.setCreationDate(ed2);
		assertEquals("Changed", prod1.getDescription());
		assertEquals("ChangedUPC", prod1.getUPC().getBarcode());
		assertEquals(10, prod1.getShelfLife());
		assertEquals(15, prod1.getThreeMonthSupply());
		assertEquals(SizeUnits.FluidOunces, prod1.getSize().getUnit());
		assertEquals(12, (int)prod1.getSize().getNumber());
		
		
		prod1.addContainer(container);
		assertEquals(1, prod1.getContainers().size());
		prod1.removeContainer(container);
		assertEquals(0, prod1.getContainers().size());
		
		
		assertFalse(prod1.equals(prod2));
		assertFalse(prod1.equals(i1));
		assertTrue(prod1.equals(prod1));
		
	}

	@Test
	public void addContainerTest() {
		
		Container container = new ProductGroup();
		container.setName("pg1");
		Container container2 = new ProductGroup();
		container.setName("pg2");
		Container container3 = new StorageUnit();
		container3.setName("su1");
		Container container4 = new StorageUnit();
		container4.setName("su2");
		Product prod1 = new Product("UPC1", "Description1", SizeUnits.Count, 1,1,1);
		
		prod1.addContainer(container);
		assertEquals(1, prod1.getContainers().size());
		prod1.addContainer(container3);
		assertEquals(2, prod1.getContainers().size());
		prod1.addContainer(container2);
		prod1.addContainer(container4);
		assertEquals(4, prod1.getContainers().size());
	}
	
	@Test(expected = IllegalArgumentException.class )
	public void removeContainerTest(){

		Container container = new ProductGroup();
		container.setName("pg1");
		Container container2 = new ProductGroup();
		container.setName("pg2");
		Container container3 = new StorageUnit();
		container3.setName("su1");
		Container container4 = new StorageUnit();
		container4.setName("su2");
		Product prod1 = new Product("UPC1", "Description1", SizeUnits.Count, 1,1,1);
		
		prod1.addContainer(container);
		prod1.addContainer(container3);
		prod1.addContainer(container2);
		prod1.addContainer(container4);
		prod1.removeContainer(container4);
		prod1.removeContainer(container3);
		prod1.removeContainer(container2);
		prod1.removeContainer(container);
		assertEquals(0, prod1.getContainers().size());
		
		
		/* Removing a container not in the set should throw an exception */
		prod1.removeContainer(container);
		
	}
	
	@Test
	public void constructorTest(){
		Product prod1 = new Product("UPC1", "Description1", SizeUnits.Count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description1", SizeUnits.FluidOunces, 12, 1, 1);
	}
	
}
