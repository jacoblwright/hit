package model;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author jake
 * @invariant			StorageUnit.container == null
 */
public class StorageUnit extends Container {

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
	 * @return						null
	 */
	public Container getContainer() {
		return null;		
	}

	/**Setter for container
	 * @pre							none
	 * @param container				sets parent container to null
	 */
	public void setContainer(Container container) {
		this.container = null;
	}

	/**Checks to see if current container has different name than sibling containers
	 * @pre							none
	 * @post						if uniqueName { container.isValid = true }
	 * @param container				current container to be validated
	 */
	@Override
	public boolean isContainerValid(Container container) {
		// TODO Auto-generated method stub
		return false;
	}

}
