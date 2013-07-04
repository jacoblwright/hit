package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


public class ContainerManager {
	
	/** Map where the key is the storage unit and the value is a list of Containers	*/
	private List<StorageUnit> storageUnits;
	
	/** Map where the key is the containers id and the value is the container */
	private HashMap<String, Container> containersById;
	
	/** Returns a list of all the storage units
	 * @pre						none
	 * @return iterator			
	 */
	public Set<StorageUnit> getRoot() {
		return null;	
	}
	
	/**Given a container the StorageUnit of that container
	 * @pre non
	 * @param container
	 * @return returns StorageUnit 
	 */
	public StorageUnit getAncestorStorageUnit( Container container ) {
		return null;
	}
	
	/** Adds the container under the parent container.
	 * 
	 * @pre						valid(parameters) && arguments != null
	 * @pre						container.isParent(parent)
	 * @pre						container.isStorageUnit(storageUnit)
	 * @post					if( checkCanAdd(container) ) { add(container); }
	 * @param parent			parent container to the current container
	 * @param container			Current container to be deleted
	 * @return					True upon successfully adding, otherwise false
	 * @throws IllegalArgumentException
	 */
	public void addContainer( Container parent, Container container ) throws IllegalArgumentException {
		
	}
	
	/** Edits the container and all of the children containers.
	 *
	 * @pre						valid(parameters) && arguments != null 
	 * @pre						container.isStorageUnit(storageUnit)
	 * @post					if( checkCanEdit(container) ) { edit(container); }
	 * @param container			New values for container, changes container with same id
	 * @throws IllegalArgumentException
	 */
	public void editContainer( Container container ) throws IllegalArgumentException {
		
	}
	
	/** Deletes the container and all of the children containers.
	 * 
	 * @pre						Container.items() == 0, recursively
	 * @post					delete(container), recursively
	 * @param container			Current container to be deleted
	 * @throws IllegalArgumentException
	 */
	public void deleteContainer( Container container ) throws IllegalArgumentException {
	
	}
	
	/**Checks to see if all of the qualifications are met to add the current container.
	 * Qualifications are set in containers.
	 * 
	 * @pre						valid(parameters) && arguments != null
	 * @pre						container.isStorageUnit(storageUnit)
	 * @post					checks to see if all qualifications are met in order to add
	 * @param container 		Current container that will be checked to see if it can be added
	 * @return					True if all of the qualifications are met and false otherwise.
	 */
	public boolean canAddContainer( Container container ) {
		return false;
	}
	
	/**Checks to see if all of the qualifications are met to edit the current container.
	 * Qualifications are set in containers.
	 * @pre						valid(parameters) && arguments != null
	 * @pre						container.isStorageUnit(storageUnit)
	 * @post					checks to see if all qualifications are met in order to edit
	 * @param container 		Current container that will be checked to see if it can be added
	 * @return					True if all of the qualifications are met and false otherwise.
	 */
	public boolean canEditContainer( Container container ) {
		return false;
	}
	
}
