package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;


public class ContainerManager {
	
	/** Map where the key is the storage unit and the value is a list of Containers	*/
	private List<StorageUnit> storageUnits;
	
	/** Map where the key is the containers id and the value is the container */
	private HashMap<String, Container> idToContainerMap;
	
	/** Iterator for children of the current container
	 * @pre						none
	 * @param container			Parent conainer
	 * @return iterator
	 */
	public Iterator<Container> getAllDescendants( Container container ) {
		return null;
	}
	
	/** Iterator for all of the storageUnits
	 * @pre	none
	 * @return iterator
	 */
	public Iterator<Container> getAllStorageUnits() {
		return null;
		
	}
	
	/**Gets list of containers associated with given storage unit
	 * @pre					none
	 * @param storageUnit
	 * @return				if valid storageUnit then returns the list of productGroups associated with it.
	 */
	public List<Container> getContainerList( StorageUnit storageUnit ) {
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
	 * @throws					InvalidParemeters
	 */
	public boolean add( Container parent, Container container ) {
		return false;
		
	}
	
	/** Edits the container and all of the children containers.
	 *
	 * @pre						valid(parameters) && arguments != null 
	 * @pre						container.isStorageUnit(storageUnit)
	 * @post					if( checkCanEdit(container) ) { edit(container); }
	 * @param container			New values for container, changes container with same id
	 * @return					True upon successfully editing, otherwise false
	 */
	public boolean edit( Container container ) {
		return false;
		
	}
	
	/** Deletes the container and all of the children containers.
	 * 
	 * @pre						Container.items() == 0, recursively
	 * @post					delete(container), recursively
	 * @param container			Current container to be deleted
	 * @return					True upon successful deletion, otherwise false
	 */
	public boolean delete( Container container ) {
		return false;
	}
	
	// getIndex( StorageUnit storageUnit ) {}
	
	/**Checks to see if all of the qualifications are met to add the current container.
	 * Qualifications are set in containers.
	 * 
	 * @pre						valid(parameters) && arguments != null
	 * @pre						container.isStorageUnit(storageUnit)
	 * @post					checks to see if all qualifications are met in order to add
	 * @param container 		Current container that will be checked to see if it can be added
	 * @return					True if all of the qualifications are met and false otherwise.
	 */
	public boolean canAdd( Container container ) {
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
	public boolean canEdit( Container container ) {
		return false;
	}
	
}
