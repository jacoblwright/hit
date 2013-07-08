package model;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;


public class ContainerManager {
	
	/** Map where the key is the storage unit and the value is a list of Containers	*/
	private Set<StorageUnit> storageUnits;
	
	/**
	 * Used for testing and reinitializing the tree, easy way to set up tree
	 * @param storageUnits Set<StorageUnit>
	 */
	public void setStorageUnits( Set<StorageUnit> storageUnits ) {
		this.storageUnits = storageUnits;
	}
	
	/** Map where the key is the containers id and the value is the container */
	private Map<Integer, Container> containersById;
	
	/**
	 * Used for testing and reinitializing the tree structure
	 * @param containersById Map<Integer, Container>
	 */
	public void setContainersById( Map<Integer, Container> containersById ) {
		this.containersById = containersById;
	}
	
	/** Returns a list of all the storage units
	 * @pre						none
	 * @return iterator			
	 */
	public Set<StorageUnit> getRoot() {
		assertTrue( true );
		if( storageUnits == null ) {
			storageUnits = new TreeSet<StorageUnit>();
		}
		return storageUnits;	
	}
	
	/**Given a container the StorageUnit of that container
	 * @pre non
	 * @param container
	 * @return returns StorageUnit 
	 */
	public StorageUnit getAncestorStorageUnit( Container container ) {
		assertTrue( true );
		while( container.container.getName() != null ) {
			container = container.container;
		}
		return (StorageUnit) container;
	}
	
	/** Adds the container under the parent container.
	 * 
	 * @pre						parameters != null & container.canAdd == true
	 * @post					if( checkCanAdd(container) ) { add(container); }
	 * @param parent			parent container to the current container
	 * @param container			Current container to be deleted
	 * @return					True upon successfully adding, otherwise false
	 * @throws IllegalArgumentException 		if parent == null and child != storageUnit or
	 * 											container.canAdd == false
	 */
	public void addContainer( Container parent, Container container ) 
			throws IllegalArgumentException {
		if( parent == null ) {		//possibly add && container instanceof StorageUnit
			if( !storageUnits.contains( container ) || !canAddContainer( container ) ) {		// test both su and pg
				throw new IllegalArgumentException();
			}
		}
		if( canAddContainer( container ) ) {
			addContainerToTree( parent, container );
		}		
	}
	
	private void addContainerToTree(Container parent, Container container) {
		container.setContainer( parent );
		container.setId( generateUniqueId( container ) );
		containersById.put(container.getId(), container );
		if( container instanceof ProductGroup ) {
			parent.addProductGroup( ( ProductGroup ) container );
		}
		else {
			storageUnits.add( (StorageUnit) container );
		}
	}

	private int generateUniqueId( Container container ) {
		int id = container.hashCode();
		while( !isIdUnique( id ) ) {
			id += 1;
		}
		return id;
	}

	/** Edits the container and all of the children containers.
	 *
	 * @pre						oldContainer.id == newContainer.id unless oldContainer = StorageUnit
	 * @pre						canEditContainer( newContainer ) == true
	 * @post					if( checkCanEdit(container) ) { edit(container); }
	 * @param container			New values for container, changes container with same id
	 * @throws IllegalArgumentException		if oldContainer == null or newContainer == null or
	 * 										newContainer.canEdit == false
	 */
	public void editContainer( Container oldContainer, Container newContainer ) 
			throws IllegalArgumentException {
		if( oldContainer == null || 
				newContainer == null || 
				!canEditContainer( newContainer ) || 
				oldContainer.getId() != newContainer.getId() ||
				!containersById.containsKey( oldContainer.getId() ) ) {
			throw new IllegalArgumentException();
		}
		
		if( oldContainer instanceof ProductGroup ) {
			oldContainer.setName( newContainer.getName() );
			((ProductGroup) oldContainer).setThreeMonthSupply( 
					((ProductGroup) newContainer).getThreeMonthSupply() );
		}
		else {
			oldContainer.setName( newContainer.getName() );
		}
	}
	
	/** Deletes the container and all of the children containers.
	 * 
	 * @pre						Container.items() == 0, recursively, see condition below
	 * 							***(can't assert that items == 0, have to trust)***
	 * @pre 					container.exists() == true
	 * @post					delete(container), recursively
	 * @param container			Current container to be deleted
	 * @throws IllegalArgumentException 			if container.exists() == false then throw
	 */
	public void deleteContainer( Container container ) throws IllegalArgumentException {
		if( containersById.containsKey(container.getId() ) ||
				!containersById.containsKey( container.getId() ) ) {
			throw new IllegalArgumentException();
		}
		removeContainerRecursively( container );
		if( container instanceof ProductGroup ) {
			Set<ProductGroup> productGroupList = container.getContainer().getProductGroups();
			productGroupList.remove( container );
		}
		else {
			storageUnits.remove( container );		// make sure this runs since storageUnits is (StorageUnit) and not (Container)
		}
	}
	
	private void removeContainerRecursively(Container container) {
		assertNotNull( container );
		Queue<ProductGroup> q = new LinkedList<ProductGroup>();
		Set<ProductGroup> productGroupList = container.getProductGroups();
		for( ProductGroup productGroup : productGroupList ) {
			removeContainerRecursively( productGroup );
		}
		if( productGroupList.size() == 0 ) {
			containersById.remove( container.getId() );
			return;
		}		
	}

	/**Checks to see if all of the qualifications are met to add the current container.
	 * Qualifications are set in containers.
	 * 
	 * @pre						none
	 * @post					checks to see if all qualifications are met in order to add
	 * @param container 		Current container that will be checked to see if it can be added
	 * @return					True if all of the qualifications are met and false otherwise.
	 */
	public boolean canAddContainer( Container container ) {
		assertTrue( true );
		return isValidContainer( container );
	}
	
	/**Checks to see if all of the qualifications are met to edit the current container.
	 * Qualifications are the same as adding to a container
	 * @pre						none
	 * @post					checks to see if all qualifications are met in order to edit
	 * @param container 		Current container that will be checked to see if it can be added
	 * @return					True if all of the qualifications are met and false otherwise.
	 */
	public boolean canEditContainer( Container container ) {
		assertTrue( true );
		return isValidContainer( container );
	}
	
	private boolean isIdUnique( int id ) {
		assertTrue( true );
		return containersById.containsKey( id );
	}
	
	/**Abstract Method, Checks to see if given productsName is unique among the list of ProductGroups
	 * @pre							none
	 * @param groupName				String name in question 			
	 * @return						True if all of the qualifications are met and false otherwise.
	 */
	private boolean isUniqueProductGroupName( Container container ) {
		assertTrue( true );
		if( container.getName() == null ) {
			return false;
		}
		for( ProductGroup productGroup : container.getContainer().productGroups ) {
			if( container.getName().equals( productGroup.getName() ) ) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isUniqueStorageUnitName( Container container ) {
		assertTrue( true );
		for( StorageUnit storageUnit : storageUnits ){
			if( container.getName().equals( storageUnit.getName() ) ) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isValidContainer( Container container ) {
		assertTrue( true );
		if( container == null || 
			    container.container == null || 
			    !containersById.containsKey( container.container.getId() ) ) {
				return false;
		}
		if( container instanceof ProductGroup ) {
			return isUniqueProductGroupName( container ) && container.isContainerValid();
		}
		else {
			return isUniqueStorageUnitName( container ) && container.isContainerValid();
		}
	}
}
