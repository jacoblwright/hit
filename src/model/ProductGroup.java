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
	
	private float unspecified = 0;
	
	public ProductGroup() {
		threeMonthSupply = new Quantity();
		threeMonthSupply.setQuantity( unspecified, Unit.unspecified );
	}
	
	/**Getter for container
	 * @pre							none
	 * @return						parent container
	 */
	public Container getContainer() {
		assertTrue( true );
		return container;		
	}

	/**Setter for container
	 * @pre							none
	 * @param container				sets parent container
	 */
	public void setContainer(Container container) {
		assertTrue( true );
		this.container = container;
	}

	/**Getter for threeMonthSupply
	 * @pre							none
	 * @return						three month supply for everything in 
	 */
	public Quantity getThreeMonthSupply() {
		assertTrue( true );
		return threeMonthSupply;
	}

	/**Setter for threeMonthSupply
	 * @pre							none
	 * @param threeMonthSupply		adds threeMonthSupply if it is properly formatted
	 */
	public void setThreeMonthSupply(Quantity threeMonthSupply) {
		assertTrue( true );
		this.threeMonthSupply = threeMonthSupply;
	}

	/**Checks to see if threeMonthSupply meets all of the specified qualifications defined by class that extend Container
	 * Rule- if Unit == count then number must be an integer value
	 * otherwise number can be a float.
	 * @pre							none
	 * @return						true if threeMonthSupply is valid, otherwise false
	 */
	public boolean canAddThreeMonthSupply() {
		assertTrue( true );
		if( threeMonthSupply != null ) {
			if( threeMonthSupply.getUnit().equals( Unit.count ) ) {
				return threeMonthSupply.getNumber() == Math.round( threeMonthSupply.getNumber() );
			}
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
		assertTrue( true );
		return ( getName() != null ) && nonEmptyName() && canAddThreeMonthSupply();
	}
	
	private boolean nonEmptyName() {
		
		return getName().trim().length() > 0;
	}

	@Override
	public int hashCode() {
		assertTrue( true );
		int hash = super.hashCode();
		hash = createHash( hash, threeMonthSupply );
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		assertTrue( true );
		return super.equals(obj) && 
						( this.threeMonthSupply != null ? this.threeMonthSupply.equals( ((ProductGroup)obj).threeMonthSupply )
						:((ProductGroup)obj).threeMonthSupply == null );
	}

	@Override
	public String toString() {
		assertTrue( true );
		return super.toString() + " ProductGroup [threeMonthSupply=" + threeMonthSupply + "]";
	}
	
	
}
