package model;

/** Quantity holds a Unit and a number value associated with that Unit
 * ex. unit = 'count' & number = 1, or unit = 'pounds' & number = 2.4.
 * 
 * @author jake
 * @invariant	Quantity.alwaysExists(number, unit)
 */
public class Quantity {
	
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
	public float number() {
		return number;
	}
	
	/**Getter for Unit
	 * @pre 			none
	 * @return			Returns the unit.
	 */
	public Unit unit() {
		return unit;
	}
	

}
