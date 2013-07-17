package model;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

public class ProductGroupTest {

	private int ID = 3;
	private int DIFF_ID = 2;
	private String NAME = "name1";
	private String DIFF_NAME = "name2";
	private float FLOAT = 2.3f;
	private Unit UNIT_COUNT = Unit.count;
	private Unit UNIT_PINTS = Unit.pints;
	private float unspecified = 0;
	
	@Test
	public void constructorTest() {
		Quantity expectedResult = new Quantity();
		expectedResult.setQuantity( unspecified, Unit.unspecified );
		
		ProductGroup pg = new ProductGroup();
		assertEquals( expectedResult, pg.getThreeMonthSupply() );
	}
	
	@Test
	public void productGroupTest() {
		ProductGroup pg1 = new ProductGroup();
		ProductGroup pg2 = null;
		
		assertFalse( pg1.equals( pg2 ) );
		
		pg2 = new ProductGroup();
		assertTrue( pg1.equals( pg2 ) );
		assertTrue( pg1.hashCode() == pg2.hashCode() );

		initializeProductGroup( pg1 );
		loadProductGroup( pg1, pg2 );
		
		
		assertTrue( pg1.equals( pg2 ) );
		assertTrue( pg1.hashCode() == pg2.hashCode() );
		
		pg2.setName( DIFF_NAME );
		assertFalse( pg1.equals( pg2 ) );
		assertFalse( pg1.hashCode() == pg2.hashCode() );
	}
	
	private void initializeProductGroup( ProductGroup pg1 ) {
		StorageUnit parentSU = new StorageUnit();
		parentSU.setId( DIFF_ID ); 
		parentSU.setName( DIFF_NAME );
		parentSU.setContainer( new StorageUnit() );
		TreeSet<ProductGroup> children = new TreeSet<ProductGroup>();
		children.add( pg1 );
		parentSU.setProductGroups( children );
		
		pg1.setId( ID );
		pg1.setName( NAME );
		pg1.setContainer( parentSU );
		pg1.setProductGroups( new TreeSet<ProductGroup>() );
	}
	
	private void loadProductGroup( ProductGroup pg1, ProductGroup pg2 ) {
		pg2.setId( pg1.getId() );
		pg2.setName( pg1.getName() );
		pg2.setContainer( pg1.getContainer() );
		pg2.setProductGroups( pg1.getProductGroups() );
	}
	
	@Test
	public void toStringTest() {
		
		ProductGroup pg1 = new ProductGroup();
		initializeProductGroup( pg1 );
		
		String expectedResult = pg1.toString();
		assertTrue( expectedResult.equals( pg1.toString() ) );
		
	}
	
	@Test
	public void isContainerValidTest() {
		
		ProductGroup pg1 = new ProductGroup();
		assertTrue( pg1.isContainerValid() );
		
		pg1.setName( "\t\n" );
		assertFalse( pg1.isContainerValid() );
		
		pg1.setName( DIFF_NAME );
		assertTrue( pg1.isContainerValid() );
		
		Quantity q = new Quantity();
		q.setQuantity( FLOAT, UNIT_PINTS );
		pg1.setThreeMonthSupply( q );
		assertTrue( pg1.isContainerValid() );
		
		q.setQuantity( FLOAT, UNIT_COUNT );
		assertFalse( pg1.isContainerValid() );
		
		q.setQuantity( -FLOAT, UNIT_PINTS );
		assertFalse( pg1.isContainerValid() );
		
	}

}
