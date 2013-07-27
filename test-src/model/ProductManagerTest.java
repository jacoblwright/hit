package model;

import static org.junit.Assert.*;

import gui.common.SizeUnits;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ProductManagerTest {

	@Test
	public void constructorTest() {
		ProductManager pmanager = new ProductManager();		
		assertEquals(0, pmanager.getProductsByContainer().size());
		assertEquals(0, pmanager.getProductsByUPC().size());
		assertFalse(pmanager.getProductsByUPC().size() == 1);
	}
	
	@Test ( expected = IllegalArgumentException.class )
	public void UPCExistsTest(){
		ProductManager pmanager = new ProductManager();	
		Product product = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		Product product2 = new Product("upc2", "test", SizeUnits.Count, 1, 1, 1);
		Product product3 = new Product("upc2", "test", SizeUnits.Count, 1, 1, 1);
		Container container = new ProductGroup();
		
		pmanager.addNewProduct(product2, container);
		pmanager.addNewProduct(product, container);
		
		assertTrue(pmanager.upcExists("upc"));
		assertTrue(pmanager.upcExists("upc2"));
		assertFalse(pmanager.upcExists("badUPC"));
		assertFalse(pmanager.upcExists("upc3"));
		
		pmanager.addNewProduct(product3, container);
	}
	
	@Test
	public void isQuantityValidTest(){
		ProductManager pmanager = new ProductManager();
		Quantity qty = new Quantity();
		qty.setQuantity(1, SizeUnits.Count);
		assertTrue(pmanager.isQuantityValid(qty));
		
		qty.setQuantity(2, SizeUnits.Count);
		assertFalse(pmanager.isQuantityValid(qty));
		
		qty.setQuantity((float)0.1, SizeUnits.Count);
		assertFalse(pmanager.isQuantityValid(qty));
		
		qty.setQuantity(-1, SizeUnits.Count);
		assertFalse(pmanager.isQuantityValid(qty));
		
		qty.setQuantity(0, SizeUnits.Count);
		assertFalse(pmanager.isQuantityValid(qty));
		
		qty.setQuantity(0, SizeUnits.Liters);
		assertFalse(pmanager.isQuantityValid(qty));
		
		qty.setQuantity((float)1.1, SizeUnits.Liters);
		assertTrue(pmanager.isQuantityValid(qty));
		
		qty.setQuantity((float)-1, SizeUnits.Liters);
		assertFalse(pmanager.isQuantityValid(qty));
	}
	
	@Test
	public void isProductValidTest() throws java.text.ParseException{
		ProductManager pmanager = new ProductManager();
		Product goodProduct = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		Product goodProduct2 = new Product("upc2", "test", SizeUnits.Gallons, 2, 1, 1);
		Product goodProduct3 = new Product("upc2", "test", SizeUnits.Liters, (float)0.001, 0, 0);
		Product goodProduct4 = new Product("12345", "anything", SizeUnits.Pints, 100000, 10000, 10000);
		
		assertTrue(pmanager.isProductValid(goodProduct));
		assertTrue(pmanager.isProductValid(goodProduct2));
		assertTrue(pmanager.isProductValid(goodProduct3));
		assertTrue(pmanager.isProductValid(goodProduct4));
		
		Product badProduct2 = new Product("upc2", "", SizeUnits.Gallons, 2, 1, 1);
		Product badProduct3 = new Product("upc2", "test", SizeUnits.Count, 2, 0, 0);
		Product badProduct4 = new Product("12345", "anything", SizeUnits.Pints, 0, 10000, 10000);
		Product badProduct5 = new Product("12345", "anything", SizeUnits.Pints, 0, -1, 10000);
		Product badProduct6 = new Product("12345", "anything", SizeUnits.Pints, 0, 0, -1);
		Product badProduct7 = new Product("12345", "anything", SizeUnits.Count, 0, 0, -1);
		
		assertFalse(pmanager.isProductValid(badProduct2));
		assertFalse(pmanager.isProductValid(badProduct3));
		assertFalse(pmanager.isProductValid(badProduct4));
		assertFalse(pmanager.isProductValid(badProduct5));
		assertFalse(pmanager.isProductValid(badProduct6));
		assertFalse(pmanager.isProductValid(badProduct7));
		
		/* Set good product with bad date then good date*/
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date badDate = dateFormat.parse("2013/12/9");
		Date goodDate = dateFormat.parse("2012/12/9");
		goodProduct.setCreationDate(badDate);
		assertFalse(pmanager.isProductValid(goodProduct));
		goodProduct.setCreationDate(goodDate);
		assertTrue(pmanager.isProductValid(goodProduct));

	}
	
	@Test
	public void addNewProductTest(){
		ProductManager pmanager = new ProductManager();	
		
		Product product = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		Product product2 = new Product("upc2", "test", SizeUnits.Count, 1, 1, 1);
		Product product3 = new Product("upc3", "test", SizeUnits.Count, 1, 1, 1);
		Product product4 = new Product("upc4", "test", SizeUnits.Count, 1, 1, 1);
		Product product5 = new Product("upc5", "test", SizeUnits.Count, 1, 1, 1);
		
		Container container = new ProductGroup();
		container.setName("Container1");
		Container container2 = new ProductGroup();
		container2.setName("Container2");
		
		pmanager.addNewProduct(product, container);
		pmanager.addNewProduct(product2, container);
		pmanager.addNewProduct(product3, container2);
		pmanager.addNewProduct(product4, container2);
		pmanager.addNewProduct(product5, container2);
		
		Map testMap = pmanager.getProductsByContainer();
		
		/* Size of Map should be two since there are only two added containers */
		assertEquals(2, testMap.size());
		
		/* container2 should be size 3, container should be size 2 */
		HashSet container2Set = (HashSet)testMap.get(container2);
		HashSet containerSet = (HashSet)testMap.get(container);
		assertEquals(3, container2Set.size());
		assertEquals(2, containerSet.size());
		
		/* Barcode Map should be 5 */
		assertEquals(5, pmanager.getProductsByUPC().size());
	}
	
	@Test
	public void editProductTest(){
		ProductManager pmanager = new ProductManager();	
		
		Product oldProduct = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		Date date = oldProduct.getCreationDate();
		Product newProduct = new Product("upc2", "test2", SizeUnits.Liters, 2, 3, 4);
		
		pmanager.editProduct(oldProduct, newProduct);
		assertEquals(oldProduct.getUPC().getBarcode(), "upc");
		assertEquals(newProduct.getDescription(), oldProduct.getDescription());
		assertEquals(newProduct.getSize().getUnit(), oldProduct.getSize().getUnit());
		assertEquals((int)newProduct.getSize().getNumber(), (int)oldProduct.getSize().getNumber());
		assertEquals(newProduct.getShelfLife(), oldProduct.getShelfLife());
		assertEquals(newProduct.getThreeMonthSupply(), oldProduct.getThreeMonthSupply());
		assertEquals(date, oldProduct.getCreationDate());
	}
	
	@Test
	public void moveProductTest(){
		ProductManager pmanager = new ProductManager();	
		Container oldContainer = new ProductGroup();
		oldContainer.setName("one");
		Container newContainer = new ProductGroup();
		newContainer.setName("two");
		
		Product product = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		pmanager.addNewProduct(product, oldContainer);
		assertTrue(product.getContainers().contains(oldContainer));
		Set tempSet = (HashSet)pmanager.getProductsByContainer().get(oldContainer);
		assertTrue(tempSet.contains(product));
		
		pmanager.moveProduct(product, oldContainer, newContainer);
		assertFalse(product.getContainers().contains(oldContainer));
		assertFalse(tempSet.contains(product));
		
		Set tempSet2 = (HashSet)pmanager.getProductsByContainer().get(newContainer);
		assertTrue(tempSet2.contains(product));
		assertTrue(product.getContainers().contains(newContainer));
		assertFalse(product.getContainers().contains(oldContainer));
		
		pmanager.moveProduct(product, newContainer, newContainer);
		assertTrue(tempSet2.contains(product));
		assertTrue(product.getContainers().contains(newContainer));	
	}
	/* This one tests actual mapping */
	@Test ( expected = IllegalArgumentException.class )
	public void addAndRemoveProductToContainerTest(){
		ProductManager pmanager = new ProductManager();
		Product product = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		Product product2 = new Product("upc2", "test2", SizeUnits.Count, 1, 1, 1);
		Container container = new ProductGroup();
		container.setId(1);
		Container container2 = new ProductGroup();
		container.setId(2);
		Container container3 = new StorageUnit();
		container.setId(3);
		
		/* Add Testing */
		
		pmanager.addProductToContainer(product, container);
		Set tempSet = (HashSet)pmanager.getProductsByContainer().get(container);
		assertTrue(tempSet.contains(product));
		
		pmanager.addProductToContainer(product, container2);
		Set tempSet2 = (HashSet)pmanager.getProductsByContainer().get(container2);
		assertTrue(tempSet2.contains(product));
		
		pmanager.addProductToContainer(product, container3);
		Set tempSet3 = (HashSet)pmanager.getProductsByContainer().get(container3);
		assertTrue(tempSet3.contains(product));
		
		pmanager.addProductToContainer(product2, container3);
		assertTrue(tempSet3.size() == 2);
		assertTrue(tempSet3.contains(product2));
		
		/* Remove Testing */
		
		pmanager.removeProductFromContainer(product, container);
		assertTrue(tempSet.size() == 0);
		
		pmanager.removeProductFromContainer(product2, container3);
		assertTrue(tempSet3.contains(product));
		assertFalse(tempSet3.contains(product2));
		
		/* Should throw exception */
		pmanager.removeProductFromContainer(product2, container3);
		
	}
	
	@Test
	public void GettersTest(){
		ProductManager pmanager = new ProductManager();
		Container container = new ProductGroup();
		Product product = new Product("upc", "test", SizeUnits.Count, 1, 1, 1);
		Product product2 = new Product("upc2", "test2", SizeUnits.Count, 1, 1, 1);
		Product product3 = new Product("upc3", "test3", SizeUnits.Count, 1, 1, 1);
		
		pmanager.addNewProduct(product2, container);
		pmanager.addNewProduct(product, container);	
		
		/* Get Products By Container */
		Collection collection = pmanager.getProducts(container);
		assertTrue(collection.size() == 2);
		assertTrue(collection.contains(product));
		assertTrue(collection.contains(product2));
		assertTrue(product.getContainers().contains(container));
		assertTrue(product2.getContainers().contains(container));
		assertTrue(pmanager.getProducts(container).size() == 2);
		assertTrue(pmanager.getProducts(container).contains(product));
		assertTrue(pmanager.getProducts(container).contains(product2));
		assertFalse(pmanager.getProducts(container).contains(product3));
		assertTrue(pmanager.getProductsByUPC().size() == 2);
		assertTrue(pmanager.getProducts().contains(product));
		assertTrue(pmanager.getProducts().contains(product2));
		assertFalse(pmanager.getProducts().contains(product3));
		
		pmanager.removeProductFromContainer(product2, container);
		assertFalse(product2.getContainers().contains(container));
		
		pmanager.removeProductFromContainer(product, container);
		assertFalse(product.getContainers().contains(container));
		
		/* Get Products By UPC */
		assertTrue(pmanager.getProducts(container).size() == 0);
		assertTrue(pmanager.getProductsByUPC().size() == 2);
		Barcode upc = new Barcode("upc");
		Barcode upc2 = new Barcode("upc2");
		assertTrue(pmanager.getProductByUPC(upc) == product);
		assertTrue(pmanager.getProductByUPC(upc2) == product2);
	}
}