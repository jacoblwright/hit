package model;

import java.util.ArrayList;
import java.util.List;

/**Abstract Class that implements similar methods that storage unit and product groups need.
 * implements Comparable<Container> and Iterable<Container>
 * @author jake
 *
 */
public abstract class Container extends Entity implements Comparable<Container> {

	/** name- Must be non-empty */
	private String name;
	
	/** productGroups- unique ProductGroup list based on ProductGroup.name	*/
	private List<ProductGroup> productGroups;
	
	/**	container- Must be non-empty for ProductGroups */
	protected Container container;
<<<<<<< HEAD
	
	/**Unique id that is based on the name and parent id
	 * @pre			none
	 * @return		id of current container
	 */
	public int getId() {
		return 0;
	}
	
	/**
	 * @pre			Needs to be unique among other containers
	 * @post		sets the id variable
	 * @param		id unique string associated with the container
	 */
	public void setId( String id ) {
		this.id = id;
	}
=======
>>>>>>> d88d57839203df597cfb8f893a7914a686b324b2

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
	public List<ProductGroup> getProductGroups() {
		if( productGroups == null ) {
			productGroups = new ArrayList<ProductGroup>();
		}
		return productGroups;
	}

	/**Setter for productGroups
	 * @pre				productGroups != null
	 * @post			sets a list of product groups to productGroups
	 * @param productGroups
	 */
	public void setProductGroups(List<ProductGroup> productGroups) {
		this.productGroups = productGroups;
	}
	
	/**Retrieves the size of the list of productGroups
	 * @pre 			none
	 * @return			size of the list of productGroups, if productGroup == null returns 0
	 */
	public int getProductGroupsSize() {
		if( productGroups == null ) {
			productGroups = new ArrayList<ProductGroup>();
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
			productGroups = new ArrayList<ProductGroup>();
		}
		this.productGroups.add( productGroup );
	}
	
	/**Abstract Method, Checks to see if given productsName is unique among the list of ProductGroups
	 * @pre							none
	 * @param groupName				String name in question 			
	 * @return						True if all of the qualifications are met and false otherwise.
	 */
	public abstract boolean isContainerValid( Container container );
	
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
	public boolean equals(Object o) {
		return false;
	}

}
