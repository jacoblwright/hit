package model;

import java.io.Serializable;

/** Quantity holds a Unit and a number value associated with that Unit
 * ex. unit = 'count' & number = 1, or unit = 'pounds' & number = 2.4.
 * 
 * @author jake
 * @invariant	Quantity.alwaysExists(number, unit)
 */
public class Quantity implements Serializable {
	
	private static final long serialVersionUID = 3907772370880513015L;

	/**	number associated with unit */
	private float number;
	
	/** unit that H.I.T. measures such as count, pounds, ounces, grams, etc... */
	private Unit unit;

	/**Sets the number and unit variables.
	 * 
	 * @pre				unit != null
	 * @post			this.number = number && this.unit = unit
	 * @param number	number associated with the specified unit
	 * @param unit		various types of measurement such as count, pounds, ounces, etc...
	 * @throws IllegalArgumentException
	 */
	public void setQuantity( float number, Unit unit ) throws IllegalArgumentException {
		if( unit == null ) {
			throw new IllegalArgumentException();
		}
		this.number = number;
		this.unit = unit;
	}
	
	/**Getter for number
	 * @pre 			none
	 * @return 			Returns the float value of number.
	 */
	public float getNumber() {
		assert true;
		return number;
	}
	
	/**Getter for Unit
	 * @pre 			none
	 * @return			Returns the unit.
	 */
	public Unit getUnit() {
		assert true;
		return unit;
	}

	/**Creates the string version of this object
	 * @pre 				none	
	 * @return String		this.toString()
	 */
	@Override
	public String toString() {
		assert true;
		return "Quantity [number=" + number + ", unit=" + unit + "]";
	}

	/**Creates a unique hashcode for this object
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
	
	/**checks equality between two objects
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
}
