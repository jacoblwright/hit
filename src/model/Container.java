package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**Abstract Class that implements similar methods that storage unit and product groups need.
 * implements Comparable<Container> and Iterable<Container>
 * @author jake
 *
 */
public abstract class Container extends Entity implements Comparable<Container> {

	private static final long serialVersionUID = -7361136874079102442L;

	/** name- Must be non-empty */
	private String name;
	
	/** productGroups- unique ProductGroup list based on ProductGroup.name	*/
	protected Set<ProductGroup> productGroups;
	
	/**	container- Must be non-empty for ProductGroups */
	protected Container container;

	/**Getter for name
	 * @pre 			none
	 * @return			name of the container
	 */
	public String getName() {
		return name;
	}

	/**Setter for name
	 * @pre				none
	 * @param name		name of the container
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**Getter for list of ProductGroups
	 * @pre				none
	 * @return			list of product groups, if productGroups == null returns an empty list
	 */
	public Set<ProductGroup> getProductGroups() {
		if( productGroups == null ) {
			productGroups = new TreeSet<ProductGroup>();
		}
		return productGroups;
	}

	/**Setter for productGroups
	 * @pre				productGroups != null
	 * @post			sets a list of product groups to productGroups
	 * @param productGroups
	 */
	public void setProductGroups(Set<ProductGroup> productGroups) {
		this.productGroups = productGroups;
	}
	
	/**Retrieves the size of the list of productGroups
	 * @pre 			none
	 * @return			size of the list of productGroups, if productGroup == null returns 0
	 */
	public int getProductGroupsSize() {
		if( productGroups == null ) {
			productGroups = new TreeSet<ProductGroup>();
		}
		return productGroups.size();
	}
	
	/**Adds a single productGroup to the list of productGroups
	 * @pre						none
	 * @post					if(productGroup == null) create new ArrayList<ProductGroup>()
	 * @post					productGroups.add(productGroup)
	 * @param productGroup
	 * @throws IllegalArgumentException
	 */
	public void addProductGroup( ProductGroup productGroup ) throws IllegalArgumentException{
		if( productGroups == null ) {
			productGroups = new TreeSet<ProductGroup>();
		}
		this.productGroups.add( productGroup );
	}
	
	/**Abstract Method, Checks to see if given productsName is unique among the list of ProductGroups
	 * @pre							none			
	 * @return						True if all of the qualifications are met and false otherwise.
	 */
	public abstract boolean isContainerValid();
	
	/**Getter for container
	 * @pre							none
	 * @return						parent container
	 */
	public abstract Container getContainer();

	/**Setter for container
	 * @pre							none
	 * @param container				sets parent container
	 */
	public abstract void setContainer(Container container);
		
	/** Compares based on name
	 * @pre				container != null
	 * @return 			int 
	 */
	@Override
	public int compareTo(Container other) {
		return name.compareTo(other.name);
	}
	
	@Override
	public int hashCode() {
		int hash = HASH_BASE_PRIME;
		hash = ( hash * HASH_MULTIPLIER_PRIME ) + this.getId();
		hash = createHash( hash, this.name );
		if( container != null ) {
			hash = hash + container.name.hashCode();
		}
		hash = createHash( hash, this.productGroups );
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if( !( obj instanceof Container ) )
		{
			return false;
		}
		return this.getId() == ((Container)obj).getId() &&
				this.getName() == ((Container)obj).getName() &&
				this.getProductGroupsSize() == ((Container)obj).getProductGroupsSize() &&
				( this.container != null ? this.container.name.equals( ((Container)obj).container.name )
						:((Container)obj).container == null ) &&
				( this.productGroups != null ? this.productGroups.equals( ((Container)obj).productGroups )
						:((Container)obj).productGroups == null );
	}

	@Override
	public String toString() {
		return "Container [name=" + name + ", productGroups=" + ( productGroups != null ? productGroups.toString() : "null" )
				+ ", container=" + ( container != null ? container.name : "null" ) + "]";
	}

}
