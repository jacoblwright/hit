package model;

public class ContainerEditor {
    
    private ContainerManager containerManager;
    private ItemManager itemManager;
	
    /**
     * Constructs the editor with the specified managers.
     * @param containerManager the containerManager that this editor will use
     * @param itemManager the ItemManager that this editor will use
     * @pre containerManager and itemManager must not be null.
     * @post This editor has been initialized using the specified managers.
     */
    public ContainerEditor(ContainerManager containerManager,
            ItemManager itemManager) {
        
        assert containerManager != null;
        assert itemManager != null;
        
        this.containerManager = containerManager;
        this.itemManager = itemManager;
        
    }
    
	/** 
	 * Adds the container under the parent container.
	 * @pre valid(parameters) && arguments != null
	 * @pre container.isParent(parent)
	 * @pre container.isStorageUnit(storageUnit)
	 * @post if( checkCanAdd(container) ) { add(container); }
	 * @param parent parent container to the current container
	 * @param container Current container to be deleted
	 * @return True upon successfully adding, otherwise false
	 */
	public void addContainer(Container parent, Container container) {
		
	    
	    
	}
	
	/**
	 * Edits the container and all of the children containers.
	 * @pre valid(parameters) && arguments != null 
	 * @pre container.isStorageUnit(storageUnit)
	 * @post if(checkCanEdit(container)) { edit(container); }
	 * @param container new values for container, changes container with same id
	 * @throws IllegalArgumentException
	 */
	public void editContainer(Container container)
	        throws IllegalArgumentException {
		
	    containerManager.editContainer(container);
	    
	}
	
	/**
	 * Deletes the container and all of the children containers.
	 * @pre	Container.items() == 0, recursively
	 * @post delete(container), recursively
	 * @param container to be deleted
	 * @throws IllegalArgumentException
	 */
	public void deleteContainer(Container container)
	        throws IllegalStateException {
	
	    if (!itemManager.getItems(container).isEmpty()) {
	        throw new IllegalArgumentException(
	                "Attempted to delete a nonempty container.");
	    }
	    
	    containerManager.deleteContainer(container);
	    
	}
	
}
