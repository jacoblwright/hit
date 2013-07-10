package model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ProductManagerTest {

	@Test
	public void constructorTest() {
		ProductManager pmanager = new ProductManager();		
		assertEquals(0, pmanager.getProductsByContainer().size());
		assertEquals(0, pmanager.getAllProductsByUPC().size());
	}
	
	@Test
	public void addNewProductTest(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description1", Unit.count, 1,1,1);
		Container productGroup = new ProductGroup();
		Container storageUnit = new StorageUnit();
		pmanager.addNewProduct(prod1, productGroup, (StorageUnit)storageUnit);
		pmanager.addNewProduct(prod2, productGroup, (StorageUnit)storageUnit);
		assertEquals(1, pmanager.getProductsByContainer().size());
	}
	
	@Test (expected = IllegalArgumentException.class )
	public void addNewProductTestForUniqueness(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description1", Unit.count, 1,1,1);
		Container productGroup = new ProductGroup();
		productGroup.setName("Group1");
		Container productGroup2 = new ProductGroup();
		productGroup2.setName("Group2");
		Container storageUnit = new StorageUnit();
		storageUnit.setName("Storage1");
		
		pmanager.addNewProduct(prod1, productGroup, (StorageUnit)storageUnit);	
		pmanager.addProductToContainer(prod1, productGroup2, (StorageUnit)storageUnit);
		
		pmanager.addNewProduct(prod1, (StorageUnit)storageUnit, (StorageUnit)storageUnit);
	}
	
	@Test
	public void editProductTest(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description2", Unit.gallons, 5,1,1);
		
		pmanager.editProduct(prod1, prod2);
		assertEquals("Description2", prod1.getDescription());
		assertEquals(Unit.gallons, prod1.getSize().getUnit());
		assertEquals(5, (int)prod1.getSize().getNumber());
		
	}
	
	@Test
	public void deleteProductTest(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description1", Unit.count, 1,1,1);
		Container productGroup = new ProductGroup();
		Container storageUnit = new StorageUnit();
		pmanager.addNewProduct(prod1, productGroup, (StorageUnit)storageUnit);
		pmanager.deleteProduct(prod1, productGroup);
		Set tempSet = (HashSet)pmanager.getProductsByContainer().get(productGroup);
		assertEquals(0, tempSet.size());
	}
	
	@Test
	public void addProductContainerTest(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description1", Unit.count, 1,1,1);
		Container productGroup = new ProductGroup();
		Container storageUnit = new StorageUnit();
		
		pmanager.addProductToContainer(prod1, productGroup, (StorageUnit) storageUnit);
		pmanager.addProductToContainer(prod2, productGroup, (StorageUnit) storageUnit);
		Set tempSet = (HashSet)pmanager.getProductsByContainer().get(productGroup);
		assertEquals(tempSet.size(), 2);
	}
	
	@Test
	public void moveProductTest(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Container productGroup = new ProductGroup();
		Container productGroup2 = new ProductGroup();
		Container storageUnit = new StorageUnit();
		Container storageUnit2 = new StorageUnit();
		
		productGroup.setName("one");
		
		prod1.addContainer(productGroup);
		prod1.addContainer(storageUnit);

		pmanager.moveProduct(prod1, productGroup, productGroup2);
		
		Set<Container> tempSet = prod1.getContainers();
		assertTrue(prod1.getContainers().contains(productGroup2));
		assertFalse(prod1.getContainers().contains(productGroup));
		
	}
	
	@Test
	public void canAddProductToContainerTest(){
		ProductManager pmanager = new ProductManager();
		Product prod1 = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		Product prod2 = new Product("UPC2", "Description1", Unit.count, 1,1,1);
		Container storageUnit = new StorageUnit();
		Container storageUnit2 = new StorageUnit();
		Container productGroup = new ProductGroup();
		
		pmanager.addNewProduct(prod1, productGroup, (StorageUnit)storageUnit);
		assertFalse(pmanager.canAddProductToContainer(prod1, productGroup));
		assertTrue(pmanager.canAddProductToContainer(prod2, productGroup));
	}
	
	@Test(expected = IllegalArgumentException.class )
	public void isProductValidTest(){
		ProductManager pmanager = new ProductManager();
		Product p = new Product("UPC1", "Description1", Unit.count, 1,1,1);
		assertTrue(pmanager.isProductValid(p));
		Product p2 = new Product("", "Description1", Unit.count, 1,1,1);
		assertFalse(pmanager.isProductValid(p2));
	}
	

}
