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
	 * @pre				number.isAssociatedWith(Unit)
	 * @post			this.number = number && this.unit = unit
	 * @param number	number associated with the specified unit
	 * @param unit		various types of measurement such as count, pounds, ounces, etc...
	 * @throws IllegalArgumentException
	 */
	public void setQuantity( float number, Unit unit ) throws IllegalArgumentException {
		this.number = number;
		this.unit = unit;
	}
	
	/**Getter for number
	 * @pre 			none
	 * @return 			Returns the float value of number.
	 */
	public float getNumber() {
		return number;
	}
	
	/**Getter for Unit
	 * @pre 			none
	 * @return			Returns the unit.
	 */
	public Unit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "Quantity [number=" + number + ", unit=" + unit + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(number);
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if( !( obj instanceof Quantity ) )
		{
			return false;
		}
		return this.number == ((Quantity)obj).number &&
				( this.unit != null ? this.unit.name().equals( ((Quantity)obj).unit.name() )
						:((Quantity)obj).unit.name() == null );
	}
	
	
	
}
