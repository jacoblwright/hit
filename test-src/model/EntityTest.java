package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntityTest {

	private int ID = 3;
	private int DIFF_ID = 2;
	
	@Test
	public void entityTest() {
		
		Entity entity1 = new StorageUnit();
		Entity entity2 = null;
		
		assertFalse( entity1.equals( entity2 ) );
		
		entity2 = new StorageUnit();
		assertTrue( entity1.equals( entity2 ) );
		assertTrue( entity1.hashCode() == entity2.hashCode() );
		
		entity1.setId( ID );
		entity2.setId( entity1.getId() );
		assertTrue( entity1.equals( entity2 ) );
		assertTrue( entity1.hashCode() == entity2.hashCode() );
		
		entity2.setId( DIFF_ID );
//		assertFalse( entity1.equals( entity2 ) );
		assertFalse( entity1.hashCode() == entity2.hashCode() );
	
	}
	
//	@Test
//	public void toStringTest() {
//		
//		Entity entity1 = new StorageUnit();
//		entity1.setId( ID );
//		String expectedResult = "Container [name=Untitled, productGroups=null, container=null]";
//		assertEquals( expectedResult, entity1.toString() );
//		
//	}

}
