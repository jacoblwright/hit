package model;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

public class StorageUnitTest {

	private int ID = 3;
	private int DIFF_ID = 2;
	private String NAME = "name1";
	private String DIFF_NAME = "name2";
	
	@Test
	public void storageUnitTest() {
		StorageUnit su1 = new StorageUnit();
		StorageUnit su2 = null;
		
		assertFalse( su1.equals( su2 ) );
		
		su2 = new StorageUnit();
		assertTrue( su1.equals( su2 ) );
		assertTrue( su1.hashCode() == su2.hashCode() );

		initializeStorageUnit( su1 );
		loadStorageUnit( su1, su2 );
		
		assertTrue( su1.equals( su2 ) );
		assertTrue( su1.hashCode() == su2.hashCode() );
		
		su2.setName( DIFF_NAME );
		assertFalse( su1.equals( su2 ) );
		assertFalse( su1.hashCode() == su2.hashCode() );
	}
	
	private void initializeStorageUnit( StorageUnit su1 ) {
		su1.setId( ID );
		su1.setName( NAME );
		su1.setContainer( new StorageUnit() );
		su1.setProductGroups( new TreeSet<ProductGroup>() );
	}
	
	private void loadStorageUnit( StorageUnit su1, StorageUnit su2 ) {
		su2.setId( su1.getId() );
		su2.setName( su1.getName() );
		su2.setContainer( su1.getContainer() );
		su2.setProductGroups( su1.getProductGroups() );
	}
	
	@Test
	public void toStringTest() {
		
		StorageUnit su1 = new StorageUnit();
		su1.setId( ID );
		su1.setName( NAME );
		su1.setContainer( new StorageUnit() );
		su1.setProductGroups( new TreeSet<ProductGroup>() );
		
		String expectedResult = "Container [name=name1, productGroups=[], container=null]";
		assertTrue( expectedResult.equals( su1.toString() ) );
		
	}
	
	@Test
	public void isContainerValidTest() {
		
		StorageUnit su1 = new StorageUnit();
		assertTrue( su1.isContainerValid() );
		
		su1.setName("");
		assertFalse( su1.isContainerValid() );
		
		su1.setName( "\t\n" );
		assertFalse( su1.isContainerValid() );
		
		su1.setName( NAME );
		assertTrue( su1.isContainerValid() );
		
	}

}
