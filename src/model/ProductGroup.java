package model;

import java.util.Iterator;

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
	
	/**	threeMonthSupply- zero means unspecified, default 0 and Unspecified used for ProductGroups*/
	protected Quantity threeMonthSupply;

	/**Abstract Method, Checks to see if given productsName is unique among the list of ProductGroups
	 * @pre							none
	 * @param groupName				String name in question 			
	 * @return						True if all of the qualifications are met and false otherwise.
	 */
	public boolean isUniqueProductGroupName( String groupName ) {
		return false;
	}
	
	/**Getter for container
	 * @pre							none
	 * @return						parent container
	 */
	public Container getContainer() {
		return container;		
	}

	/**Setter for container
	 * @pre							none
	 * @param container				sets parent container
	 */
	public void setContainer(Container container) {
		
	}

	/**Getter for threeMonthSupply
	 * @pre							none
	 * @return						three month supply for everything in 
	 */
	public Quantity getThreeMonthSupply() {
		return null;
	}

	/**Setter for threeMonthSupply
	 * @pre							none
	 * @param threeMonthSupply		adds threeMonthSupply if it is properly formatted
	 */
	public void setThreeMonthSupply(Quantity threeMonthSupply) {
		
	}

	/**Checks to see if threeMonthSupply meets all of the specified qualifications defined by class that extend Container
	 * Rule- if Unit == count then number must be an integer value
	 * otherwise number can be a float.
	 * @pre							none
	 * @param threeMonthSupply		threeMonthSupply in question
	 * @return						true if threeMonthSupply is valid, otherwise false
	 */
	public boolean canAddThreeMonthSupply( Quantity threeMonthSupply ) {
		return false;
	}

	@Override
	public Iterator<Container> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**Checks to see if current container has different name than sibling containers
	 * @pre							none
	 * @post						if uniqueName & threeMonthSupply.isValid { container.isValid = true }
	 * @param container				current container to be validated
	 */
	@Override
	public boolean isContainerValid(Container container) {
		// TODO Auto-generated method stub
		return false;
	}
}
