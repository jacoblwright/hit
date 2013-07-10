package model;

import java.util.*;

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
	 * Adds the specified Container under the specified parent Container.
	 * @pre same as that of ContainerManager.addContainer()
	 * @post same as that of ContainerManager.addContainer()
	 * @param parent parent container to the container to be added
	 * @param container the container to be added
	 * @throws IllegalArgumentException
	 */
	public void addContainer(Container parent, Container container) {
		
	    containerManager.addContainer(parent, container);
	    
	}
	
	/**
	 * Replaces the specified old Container with the specified new Container.
	 * @pre same as that of ContainerManager.editContainer()
	 * @post same as that of ContainerManager.editContainer()
	 * @param oldContainer the Container to be replaced
	 * @param newContainer the new Container
	 * @throws IllegalArgumentException
	 */
	public void editContainer(Container oldContainer, Container newContainer)
	        throws IllegalArgumentException {
		
	    containerManager.editContainer(oldContainer, newContainer);
	    
	}
	
	/**
	 * Deletes the container and all of the children containers.
	 * @pre	Container.items().size() 0, recursively
	 * @post delete(container), recursively
	 * @param Container to be deleted
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
	
	/**
	 * @param container
	 * @return true if the specified container is non-null and contains no
	 * items, including nested subcontainers; false otherwise
	 */
	private boolean canDeleteContainer(Container container) {
	    
	    boolean result = true;
	    
	    Set<Container> containerAndItsDescendents =
	            containerManager.getDescendents(container);
	    containerAndItsDescendents.add(container);
	    
	    Collection<Item> items = itemManager.getItems();
	    for (Item item : items) {
	        
	        if (containerAndItsDescendents.contains(item.getContainer())) {
	            result = false;
	        }
	        
	    }
	    
	    return result;
	    
	}
	
}
