package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuantityTest {

	private float FLOAT = 3.3f;
	private float INT_NUMBER = 3.0f;
	private Unit UNIT = Unit.fluidOunces;
	
	@Test
	public void quantityTest() {

		Quantity quantity1 = new Quantity();
		Quantity quantity2 = null;
		
		assertFalse( quantity1.equals( quantity2 ) );
		
		quantity2 = new Quantity();
		assertTrue( quantity1.equals( quantity2 ) );
		assertTrue( quantity1.hashCode() == quantity2.hashCode() );
		
		quantity1.setQuantity( FLOAT, UNIT );
		quantity2.setQuantity( quantity1.getNumber(), quantity1.getUnit() );
		assertTrue( quantity1.equals( quantity2 ) );
		assertTrue( quantity1.hashCode() == quantity2.hashCode() );
		
		quantity2.setQuantity( INT_NUMBER, UNIT );
		assertFalse( quantity1.equals( quantity2 ) );
		assertFalse( quantity1.hashCode() == quantity2.hashCode() );
	
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void setQuantityThrowTest() {
		Quantity quantity1 = new Quantity();
		quantity1.setQuantity( FLOAT, null );
	}
	
	@Test
	public void toStringTest() {
		
		Quantity quantity1 = new Quantity();
		quantity1.setQuantity( FLOAT, UNIT );
		String expectedResult = "Quantity [number=3.3, unit=fluidOunces]";
		assertTrue( expectedResult.equals( quantity1.toString() ) );
	}

}
