package model;

public class ContainerEditor {
	
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
}
