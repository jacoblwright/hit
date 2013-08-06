package model;

import static org.junit.Assert.*;

import gui.common.SizeUnits;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;

import data.ContainerDTO;

public class ContainerManagerTest {

	private ContainerManager containerManager;
	private int uniqueId;
	private Set<StorageUnit> storageUnits;
	
	// These are used for the various tests
	private ProductGroup productGroup1_1;
	private ProductGroup childProductGroup2_2;
	private ProductGroup grandChildProductGroup1_3;
	
	private StorageUnit su1;
	private StorageUnit su2;
	
	private Collection<ContainerDTO> containersDTO;
	
	@Before
	public void setUp() {
		
		containerManager = new ContainerManager();
		storageUnits = new TreeSet<StorageUnit>();
		uniqueId = 1;
		
		setupStorageUnit1();
		setupStorageUnit2();
		
		storageUnits.add( su1 );
		storageUnits.add( su2 );
		containerManager.setStorageUnits( storageUnits );
		
	}
	
	private void setupStorageUnit2() {
		su2 = initializeStorageUnit( "su2", uniqueId++ );
		
		ProductGroup productGroup2_1 = initializeProductGroup( "pg2_1", uniqueId++, su2 );
		ProductGroup productGroup2_2 = initializeProductGroup( "pg2_2", uniqueId++, su2 );
		
		ProductGroup childProductGroup2_1 = initializeProductGroup( "cpg2_1", uniqueId++, productGroup2_1 );
		childProductGroup2_2 = initializeProductGroup( "cpg2_2", uniqueId++, productGroup2_1 );
		
		ProductGroup grandChildProductGroup2_1 = initializeProductGroup( "gcpg2_1", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_2 = initializeProductGroup( "gcpg2_2", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_3 = initializeProductGroup( "gcpg2_3", uniqueId++, childProductGroup2_1 );
		
		//Parent is su1
		su2.addProductGroup( productGroup2_1 );
		su2.addProductGroup( productGroup2_2 );
		
		//Parent is productGroup1_1
		productGroup2_1.addProductGroup( childProductGroup2_1 );
		productGroup2_1.addProductGroup( childProductGroup2_2 );
		
		//Parent is childProductGroup1_2
		productGroup2_1.addProductGroup( grandChildProductGroup2_1 );
		productGroup2_1.addProductGroup( grandChildProductGroup2_2 );
		productGroup2_1.addProductGroup( grandChildProductGroup2_3 );
	}

	private void setupStorageUnit1() {
		su1 = initializeStorageUnit( "su1", uniqueId++ );
		
		productGroup1_1 = initializeProductGroup( "pg1_1", uniqueId++, su1 );
		ProductGroup productGroup1_2 = initializeProductGroup( "pg1_2", uniqueId++, su1 );
		
		ProductGroup childProductGroup1_1 = initializeProductGroup( "cpg1_1", uniqueId++, productGroup1_1 );
		ProductGroup childProductGroup1_2 = initializeProductGroup( "cpg1_2", uniqueId++, productGroup1_1 );
		
		ProductGroup grandChildProductGroup1_1 = initializeProductGroup( "gcpg1_1", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_2 = initializeProductGroup( "gcpg1_2", uniqueId++, childProductGroup1_2 );
		grandChildProductGroup1_3 = initializeProductGroup( "gcpg1_3", uniqueId++, childProductGroup1_2 );
		
		//Parent is su1
		su1.addProductGroup( productGroup1_1 );
		su1.addProductGroup( productGroup1_2 );
		
		//Parent is productGroup1_1
		productGroup1_1.addProductGroup( childProductGroup1_1 );
		productGroup1_1.addProductGroup( childProductGroup1_2 );
		
		//Parent is childProductGroup1_2
		productGroup1_2.addProductGroup( grandChildProductGroup1_1 );
		productGroup1_2.addProductGroup( grandChildProductGroup1_2 );
		productGroup1_2.addProductGroup( grandChildProductGroup1_3 );	
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
	public void getRootTest() {
		//Tests under Normal conditions
		assertEquals( containerManager.getRoot(), storageUnits );
		
		//Tests for if StorageUnits == null
		containerManager.setStorageUnits( null );
		assertNotNull( containerManager.getRoot() );
	}
	
	@Test
	public void getAncestorStorageUnitTest() {
		assertEquals( containerManager.getAncestorStorageUnit(grandChildProductGroup1_3), su1 );
		assertEquals( containerManager.getAncestorStorageUnit(childProductGroup2_2), su2 );
	}
	
	@Test
	public void addContainerTest() {
		//Add storage Unit normally
		StorageUnit newStorageUnit = initializeStorageUnit( "newStorageUnit", uniqueId++ );
		containerManager.addContainer( null, newStorageUnit );
		
		//Add product group normally
		containerManager.addContainer( newStorageUnit, initializeProductGroup( "newProductGroup", uniqueId++, newStorageUnit ) );		
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void addContainerThrowsTest() {
		int throwCount = 0;
		try {
			//Adding duplicate name without seeing if you can add it
			containerManager.addContainer( null, su1 );
		} catch( IllegalArgumentException e ) {
			throwCount++;
		}
		try {
			//Adding productGroup with its parent container null
			Container c = initializeProductGroup( "newProductGroup", uniqueId++, su1 );
			containerManager.addContainer( null, c );
		} catch( IllegalArgumentException e ) {
			throwCount++;
		}
		assertEquals( throwCount, 2 );
		//Adding when both parameters are null
		containerManager.addContainer( null, null );
	}
	
	@Test
	public void editContainerTest() {
		//Edit StorageUnit
		Container newStorageUnit = initializeStorageUnit( "Editsu1", uniqueId++ );
		containerManager.editContainer(su2,  newStorageUnit );
		
		//Edit ProductGroup
		ProductGroup newProductGroup = initializeProductGroup( "Editedcpg2_2", uniqueId++, su2 );
		Quantity q = new Quantity();
		q.setQuantity( 2.5f, SizeUnits.FluidOunces );
		newProductGroup.setThreeMonthSupply( q );
		containerManager.editContainer( childProductGroup2_2, newProductGroup );
	
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void editContainerThrowsTest() {
		int throwCount = 0;
		try {
			//Editing with oldContainer null
			containerManager.editContainer( null, childProductGroup2_2 );
		} catch( IllegalArgumentException e ) {
			throwCount++;
		}
		try {
			//Editing with newContainer null
			containerManager.editContainer( childProductGroup2_2, null );
		} catch( IllegalArgumentException e ) {
			throwCount++;
		}
		assertEquals( throwCount, 2 );
		//Editing with different parents
		containerManager.editContainer(childProductGroup2_2, childProductGroup2_2 );
		
	}

	@Test
	public void deleteContainerTest() {
		//Delete a storageUnit
		containerManager.deleteContainer( su1 );
		
		//Delete a productGroup
		containerManager.deleteContainer( productGroup1_1 );
	}
	
	@Test (expected = IllegalArgumentException.class )
	public void deleteContainerThrowsTest() {
		
		//Delete a productGroup that doesn't exist
		containerManager.deleteContainer( new ProductGroup() ); // maybe get rid of it
	}
	
	@Test
	public void canAddAndEditContainerFailTest() {
		
		//Add & Edit fails because name isn't unique
		ProductGroup newProductGroup = initializeProductGroup( "pg2_1", uniqueId++, su2 );
		assertFalse( containerManager.canEditContainer( newProductGroup ) );
		assertFalse( containerManager.canAddContainer( newProductGroup ) );
		
		//Add & Edit fails because count value isn't an integer
		newProductGroup = initializeProductGroup( "Editedcpg2_2", uniqueId++, su2 );
		Quantity q = new Quantity();
		q.setQuantity( 2.5f, SizeUnits.Count );
		newProductGroup.setThreeMonthSupply( q );
		assertFalse( containerManager.canEditContainer( newProductGroup ) );
		assertFalse( containerManager.canAddContainer(newProductGroup) );
	}
	@Test
	public void canAddAndEditContainerTest() {
		
		//Add & Edit normally
		ProductGroup newProductGroup = initializeProductGroup( "pg2_5", uniqueId++, su2 );
		assertTrue( containerManager.canEditContainer( newProductGroup ) );
		assertTrue( containerManager.canAddContainer( newProductGroup ) );
		
		//Add & Edit normally
		newProductGroup = initializeProductGroup( "Editedcpg2_2", uniqueId++, su2 );
		Quantity q = new Quantity();
		q.setQuantity( 2.0f, SizeUnits.Count );
		newProductGroup.setThreeMonthSupply( q );
		assertTrue( containerManager.canEditContainer( newProductGroup ) );
		assertTrue( containerManager.canAddContainer(newProductGroup) );
	}
	
	@Test
	public void getDescendentTest() {
		Set<Container> pgList = containerManager.getDescendents( su1 );
		assertNotNull( pgList );
		
		containerManager.getDescendents( new StorageUnit() );
	}
	
//	@Test( expected = IllegalArgumentException.class )
//	public void getDescendentThrowsTest() {
//		Set<Container> pgList = containerManager.getDescendents( null );
//	}
}
