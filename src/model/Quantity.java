package model;

import gui.common.SizeUnits;

import java.io.*;

/** Quantity holds a Unit and a number value associated with that Unit.
 * ex. unit = 'count' & number = 1, or unit = 'pounds' & number = 2.4.
 * 
 * @author jake
 * @invariant	Quantity.alwaysExists(number, unit)
 */
public class Quantity implements Serializable {
	

	/**	number associated with unit */
	private float number;
	
	/** unit that H.I.T. measures such as count, pounds, ounces, grams, etc... */
	private Enum<SizeUnits> unit;

	public Quantity(){}
	
	public Quantity(Float number, Enum<SizeUnits> unit) {
		this.number = number;
		this.unit = unit;
	}

	/**Sets the number and unit variables.
	 * 
	 * @pre				unit != null
	 * @post			this.number = number && this.unit = unit
	 * @param number	number associated with the specified unit
	 * @param unit		various types of measurement such as count, pounds, ounces, etc...
	 * @throws IllegalArgumentException
	 */
	public void setQuantity( float number, Enum<SizeUnits> unit ) throws IllegalArgumentException {
		if( unit == null ) {
			throw new IllegalArgumentException();
		}
		this.number = number;
		this.unit = unit;
	}
	
	/**Getter for number.
	 * @pre 			none
	 * @return 			Returns the float value of number.
	 */
	public float getNumber() {
		assert true;
		return number;
	}
	
	/**Getter for Unit.
	 * @pre 			none
	 * @return			Returns the unit.
	 */
	public Enum<SizeUnits> getUnit() {
		assert true;
		return unit;
	}
	
	public String getQuantityString() {
		if( Math.round( number ) == number ) {
			String numberStr = String.valueOf( Math.round( number ) );
			return numberStr + unit.name().toLowerCase();
		}
		return number + unit.name().toLowerCase();
	}
	
	public boolean isUnspecified() {
		if(number <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean isVolume() {
		if(!isUnspecified()) {
			if(unit.equals(SizeUnits.FluidOunces) ||
					unit.equals(SizeUnits.Gallons) ||
					unit.equals(SizeUnits.Liters) ||
					unit.equals(SizeUnits.Pints) ||
					unit.equals(SizeUnits.Quarts) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWeight() {
		if(!isUnspecified()) {
			if(unit.equals(SizeUnits.Grams) ||
					unit.equals(SizeUnits.Kilograms) ||
					unit.equals(SizeUnits.Ounces) ||
					unit.equals(SizeUnits.Pounds) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCount() {
		if(!isUnspecified() && unit.equals(SizeUnits.Count)) {
			return true;
		}
		return false;
	}

	/**Creates the string version of this object.
	 * @pre 				none	
	 * @return String		this.toString()
	 */
	@Override
	public String toString() {
		assert true;
		return "Quantity [number=" + number + ", unit=" + unit + "]";
	}

	/**Creates a unique hashcode for this object.
	 * @pre					none
	 * @return int			unique Integer
	 */
	@Override
	public int hashCode() {
		assert true;
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(number);
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}
	
	/**checks equality between two objects.
	 * @pre						none
	 * @param obj				obj in question fo equality		
	 * @return boolean 			if this == obj return true, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		assert true;
		if ( this == obj ) {
			return true;
		}
		if( !( obj instanceof Quantity ) )
		{
			return false;
		}
		return this.number == ((Quantity)obj).number &&
				( this.unit != null ? this.unit.name().equals( ((Quantity)obj).unit.name() )
						:((Quantity)obj).unit == null );
	}

	public float convertTo(Enum<SizeUnits> otherUnit) {
		
		return 0;
	}
}
