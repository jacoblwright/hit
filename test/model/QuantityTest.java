package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuantityTest {

	private float NUMBER = 3.3f;
	private float DIFF_NUMBER = 3.0f;
	private Unit UNIT = Unit.fluidOunces;
	
	@Test
	public void testQuantity() {

		Quantity quantity1 = new Quantity();
		Quantity quantity2 = null;
		
		assertFalse( quantity1.equals( quantity2 ) );
		
		quantity2 = new Quantity();
		assertTrue( quantity1.equals( quantity2 ) );
		assertTrue( quantity1.hashCode() == quantity2.hashCode() );
		
		quantity1.setQuantity( NUMBER, UNIT );
		quantity2.setQuantity( quantity1.getNumber(), quantity1.getUnit() );
		assertTrue( quantity1.equals( quantity2 ) );
		assertTrue( quantity1.hashCode() == quantity2.hashCode() );
		
		quantity2.setQuantity( DIFF_NUMBER, UNIT );
		assertFalse( quantity1.equals( quantity2 ) );
		assertFalse( quantity1.hashCode() == quantity2.hashCode() );
	
	}
	
	@Test
	public void testToString() {
		
		Quantity quantity1 = new Quantity();
		quantity1.setQuantity( NUMBER, UNIT );
		String expectedResult = "Quantity [number=3.3, unit=fluidOunces]";
		assertTrue( expectedResult.equals( quantity1.toString() ) );
	}

}
