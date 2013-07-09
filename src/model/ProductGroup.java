package model;

import static org.junit.Assert.*;

/**ProductGroup is used to group similar Products together.  
 * ex.  If you have 4 different kinds of toothpaste 
 * then you can make a ProductGroup named toothpaste.
 * 
 * @author jake
 * @invariant	ProductGroup.contains( Container.size( 1 ) )
 * @invariant	ProductGroup.container != null
 * @invariant	ChildProductGroup.name() == Unique
 * 
 */
public class ProductGroup extends Container {
	
	private static final long serialVersionUID = -4392414123711461754L;
	/**	threeMonthSupply- zero means unspecified, default 0 and Unspecified used for ProductGroups*/
	private Quantity threeMonthSupply;
	
	/**	For Quantity threeMonthSupply variable a 0 means unspecified	*/
	private float unspecified = 0;
	
	/**Initializes threeMonthSupply to unspecified
	 * @pre				none
	 * @post			threeMonthSupply.Initialize()
	 */
	public ProductGroup() {
		assert true;
		setName("Untitled");
		threeMonthSupply = new Quantity();
		threeMonthSupply.setQuantity( unspecified, Unit.unspecified );
	}
	
	/**Getter for container
	 * @pre							none
	 * @return						parent container
	 */
	public Container getContainer() {
		assert true;
		return container;		
	}

	/**Setter for container
	 * @pre							none
	 * @param container				sets parent container
	 */
	public void setContainer(Container container) {
		assert true;
		this.container = container;
	}

	/**Getter for threeMonthSupply
	 * @pre							none
	 * @return						three month supply for everything in 
	 */
	public Quantity getThreeMonthSupply() {
		assert true;
		return threeMonthSupply;
	}

	/**Setter for threeMonthSupply
	 * @pre							none
	 * @param threeMonthSupply		adds threeMonthSupply if it is properly formatted
	 */
	public void setThreeMonthSupply(Quantity threeMonthSupply) {
		assert true;
		this.threeMonthSupply = threeMonthSupply;
	}

	/**Checks to see if threeMonthSupply meets all of the specified qualifications defined by class that extend Container
	 * Rule- if Unit == count then number must be an integer value
	 * otherwise number can be a float.
	 * @pre							none
	 * @return						true if threeMonthSupply is valid, otherwise false
	 */
	public boolean canAddThreeMonthSupply() {
		assert true;
		if( threeMonthSupply != null ) {
			if( threeMonthSupply.getUnit().equals( Unit.count ) ) {
				return threeMonthSupply.getNumber() == Math.round( threeMonthSupply.getNumber() );
			}
		}
		if( threeMonthSupply.getNumber() < 0 ) {
			return false;
		}
		return true;
	}

	/**Checks to see if current container has different name than sibling containers
	 * @pre							none
	 * @post						if uniqueName & nonEmptyName & threeMonthSupply.isValid { container.isValid = true }
	 * @param container				current container to be validated
	 */
	@Override
	public boolean isContainerValid() {
		assert true;
		return nonEmptyName() && canAddThreeMonthSupply();
	}
	
	/**Removes whitespace to see if the name is nonEmpty
	 * @pre							name != null
	 * @return boolean				true if name is nonEmpty, otherwise false.
	 */
	private boolean nonEmptyName() {
		return getName().trim().length() > 0;
	}

	/**Creates a unique hashcode for this object
	 * @pre					none
	 * @return int			unique Integer
	 */
	@Override
	public int hashCode() {
		assert true;
		int hash = super.hashCode();
		hash = createHash( hash, threeMonthSupply );
		return hash;
	}

	/**checks equality between two objects
	 * @pre						none
	 * @param obj				obj in question fo equality		
	 * @return boolean 			if this == obj return true, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		assert true;
		return super.equals(obj) &&  
				this.threeMonthSupply.equals( ((ProductGroup)obj).threeMonthSupply );
	}

	/**Creates the string version of this object
	 * @pre 				none	
	 * @return String		this.toString()
	 */
	@Override
	public String toString() {
		assert true;
		return super.toString() + " ProductGroup [threeMonthSupply=" + threeMonthSupply + "]";
	}
	
	@Override
	public int compareTo(Container other) throws IllegalArgumentException {
		return super.compareTo( other );
	}
}
