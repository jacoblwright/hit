package model;

import gui.inventory.ProductContainerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.*;

import data.ComponentDAO;
import data.ContainerDTO;

@SuppressWarnings("serial")
public class ContainerManager extends Observable implements Serializable {
	
	/** Map where the key is the storage unit and the value is a list of Containers	*/
	private Set<StorageUnit> storageUnits;
	
	private Map<Integer, Container> idToContainer = new TreeMap<Integer, Container>();
	
	private int uniqueId;
	
	private ComponentDAO<ContainerDTO> containerDAO;
	
	/**initializes uniqueId to 0
	 * @pre none
	 * @post uniqueId = 0;
	 */
	public ContainerManager() {
		assert true;
		uniqueId = 0;
		storageUnits = new TreeSet<StorageUnit>();
		containerDAO = Model.getInstance().getDAOFactory().createContainerDAO();
	}
	
	/** Returns all of the productGroup lists of the current container recursively.
	 * @pre 									none
	 * @param container	
	 * @return Set<container>  					list of the descendent containers
	 * 											if Null returns empty set
	 */
	public Set<Container> getDescendents( Container container ) {
		assert true;
		if( container == null ) {
			return new TreeSet<Container>();
		}
		TreeSet<Container> result = new TreeSet<Container>();
		recursivelyGetDescendents( container, result );
		return result;
	}
	
	public Container getContainerById(int id) {
		return idToContainer.get(id);
	}
	
	/**Recursively gets all of the productGroups from the given container.
	 * @pre none
	 * @param container
	 * @param pgList
	 */
	private void recursivelyGetDescendents(
	        Container container, Set<Container> pgList ) {
	    
		assert true;
		Set<ProductGroup> productGroupList = container.getProductGroups();
		pgList.addAll( productGroupList );
		if( productGroupList.isEmpty() ) {
			return ;
		}
		for( ProductGroup productGroup : productGroupList ) {
			recursivelyGetDescendents( productGroup, pgList );
		}
	}


	/** Used for testing and reinitializing the tree, easy way to set up tree.
	 * @pre none
	 * @post sets storageUnits
	 * @param storageUnits Set<StorageUnit>
	 */
	public void setStorageUnits( Set<StorageUnit> storageUnits ) {
		assert true;
		this.storageUnits = storageUnits;
	}
	
	/** Returns a list of all the storage units.
	 * @pre						none
	 * @return iterator			
	 */
	public Set<StorageUnit> getRoot() {
		assert true;
		if( storageUnits == null ) {
			storageUnits = new TreeSet<StorageUnit>();
		}
		return storageUnits;	
	}
	
	/**Given a container the StorageUnit of that container.
	 * @pre non
	 * @param container
	 * @return returns StorageUnit 
	 */
	public StorageUnit getAncestorStorageUnit( Container container ) {
		assert true;
		while( container.container != null ) {
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
	 * @throws IllegalArgumentException 		if parent == null and child != storageUnit or
	 * 											container.canAdd == false
	 */
	public void addContainer( Container parent, Container container ) 
			throws IllegalArgumentException {
		if( parent == null ) {
			if( container == null || container instanceof ProductGroup ) {
				throw new IllegalArgumentException();
			}
		}
		if( !canAddContainer( container ) ) {
			throw new IllegalArgumentException();
		}
		addContainerToTree( parent, container );
	}
	
	/** Initializes container and adds it to the parent.
	 * Also adds container to list of ProductGroup if instance of ProductGroup,
	 * otherwise parent is null.
	 * @pre 							container != null
	 * @param parent
	 * @param container
	 */
	private void addContainerToTree(Container parent, Container container) {
		assert container != null;
		container.setContainer( parent );
		//container.setId( uniqueId++ );
		if( container instanceof ProductGroup ) {
			parent.addProductGroup( ( ProductGroup ) container );
		}
		else {
			storageUnits.add( (StorageUnit) container );
		}
		ContainerDTO containerDTO = new ContainerDTO(container);
		containerDAO.create(containerDTO);
		container.setId(containerDTO.getId());
		idToContainer.put(container.getId(), container);
		ChangeObject hint = getHintObject( container );
		setChanged();
		notifyObservers( hint );
		
	}

	/** Edits the container and all of the children containers.
	 * @pre						parameters != null
	 * @pre						canEditContainer( newContainer ) == true
	 * @post					if( checkCanEdit(container) ) { edit(container); }
	 * @param oldContainer		The original container
	 * @param newContainer		The new container values
	 * @throws IllegalArgumentException		if oldContainer == null or newContainer == null or
	 * 										newContainer.canEdit == false
	 */
	public void editContainer( Container oldContainer, Container newContainer ) 
			throws IllegalArgumentException {
		
		if( oldContainer == null || 
				newContainer == null || 
				!canEditContainer( newContainer ) ) {
			throw new IllegalArgumentException();
		}
		
		System.out.println(" ------ Editing container --------");
		System.out.println("Before: " + oldContainer + "\nAfter:" + newContainer + "");
		
		if( oldContainer instanceof ProductGroup ) {
			Container parent = oldContainer.getContainer();	//added for bug fix
			parent.getProductGroups().remove( (ProductGroup) oldContainer ); 
			oldContainer.setName( newContainer.getName() );
			((ProductGroup) oldContainer).setThreeMonthSupply( 
					((ProductGroup) newContainer).getThreeMonthSupply() );
			parent.getProductGroups().add( (ProductGroup) oldContainer );
		}
		else {
			storageUnits.remove( oldContainer );
			oldContainer.setName( newContainer.getName() );
			storageUnits.add( (StorageUnit) oldContainer ); //added for bug fix
		}
		
		containerDAO.update(new ContainerDTO(oldContainer));
		ChangeObject hint = getHintObject( oldContainer );
		setChanged();
		notifyObservers( hint );
	}
	
	/** Deletes the container and all of the children containers.
	 * 
	 * @pre						Container.items() == 0, recursively, see condition below
	 * 							***(can't assert that items == 0, have to trust)***
	 * @pre 					container.exists() == true
	 * @post					delete(container), recursively
	 * @param container			Current container to be deleted
	 * @throws IllegalArgumentException 			if container.container == null && 
	 * 												container instanceof ProductGroup
	 */
	public void deleteContainer( Container container ) throws IllegalArgumentException {
		
		Container parent = container.getContainer();
		if( container instanceof ProductGroup ) {
			if( parent == null ) {
				throw new IllegalArgumentException();
			}
			Set<ProductGroup> productGroupList = container.getContainer().getProductGroups();
			productGroupList.remove( container );
		}
		else {
			storageUnits.remove( container );
		}
		ChangeObject hint;
		if( parent == null || parent.getProductGroups().isEmpty() ) {
			hint = getHintObject( parent );
		}
		else {
			hint = getHintObject( parent.getProductGroups().iterator().next() );
		}
		
		containerDAO.delete(new ContainerDTO(container));
		idToContainer.remove(container.getId());
		setChanged();
		notifyObservers( hint );
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
		assert true;
		return isValidContainer( container );
	}
	
	/**Checks to see if all of the qualifications are met to edit the current container.
	 * Qualifications are the same as adding to a container.
	 * @pre						none
	 * @post					checks to see if all qualifications are met in order to edit
	 * @param container 		Current container that will be checked to see if it can be added
	 * @return					True if all of the qualifications are met and false otherwise.
	 */
	public boolean canEditContainer( Container container ) {
		assert true;
		return isValidContainer( container );
	}
	
	/**Abstract Method, 
	 * Checks to see if given productsName is unique among the list of ProductGroups.
	 * @pre							container != null
	 * @param groupName				String name in question 			
	 * @return						True if all of the qualifications are met and false otherwise.
	 */
	private boolean isUniqueProductGroupName( Container container ) {
		assert container != null;
		if( container.getName() == null ) {
			return false;
		}
 		for( ProductGroup productGroup : container.getContainer().getProductGroups() ) {
			if( container.getName().equals( productGroup.getName() ) ) {
				return false;
			}
		}
		return true;
	}
	
	/**Compares storage name with Set<StorageUnit>  for uniqueness.
	 * @pre							container != null
	 * @param container
	 * @return boolean				true if container.name == Unique, otherwise false.
	 */
	private boolean isUniqueStorageUnitName( Container container ) {
		assert container != null;
		for( StorageUnit storageUnit : storageUnits ){
			if( container.getName().equals( storageUnit.getName() ) ) {
				return false;
			}
		}
		return true;
	}
	
	/**Checks for uniqueness and if container is valid.
	 * 
	 * @param container
	 * @return						true if valid, otherwise false
	 */
	private boolean isValidContainer( Container container ) {
		assert true;
		if( container == null ) {
				return false;
		}
		if( container instanceof ProductGroup ) {
			return isUniqueProductGroupName( container ) && container.isContainerValid();
		}
		else {
			return isUniqueStorageUnitName( container ) && container.isContainerValid();
		}
	}
	
	public void load() {
		Collection<ContainerDTO> productContainers = containerDAO.readAll();
		TreeMap<Integer, Set<ProductGroup>> parentToChild = 
				new TreeMap<Integer, Set<ProductGroup>>();
		for(ContainerDTO containerDTO : productContainers) {
			if(containerDTO.getContainerId() == -1) {
				StorageUnit storageUnit = new StorageUnit().storageUnitConverter(containerDTO);
				storageUnits.add(storageUnit);
				idToContainer.put(storageUnit.getId(), storageUnit);
			}
			else {
				addToMap(containerDTO, parentToChild);
			}
		}
		setProductGroups(parentToChild, null);
	}
	
	private void addToMap(ContainerDTO containerDTO,
			TreeMap<Integer, Set<ProductGroup>> parentToChild) {
		ProductGroup productGroup = new ProductGroup().productGroupConverter(containerDTO);
		if(parentToChild.containsKey(containerDTO.getContainerId())) {
			parentToChild.get(containerDTO.getContainerId()).add(productGroup);
		}
		else {
			Set<ProductGroup> productGroups = new TreeSet<ProductGroup>();
			productGroups.add(productGroup);
			parentToChild.put(containerDTO.getContainerId(), productGroups);
		}
		idToContainer.put(productGroup.getId(), productGroup);
		
	}
	
	private void setProductGroups(
			TreeMap<Integer, Set<ProductGroup>> parentToChild, Set<ProductGroup> containers) {
		if(containers == null) {
			for(StorageUnit storageUnit : storageUnits) {
				Set<ProductGroup> children = parentToChild.get(storageUnit.getId());
				setChildren(parentToChild, children, storageUnit);
			}
		}
		else {
		    for(Container container : containers) {
		        Set<ProductGroup> children = parentToChild.get(container.getId());
		        setChildren(parentToChild, children, container);
		    }
		}
	}
	
	private void setChildren(
			TreeMap<Integer, Set<ProductGroup>> parentToChild,
			Set<ProductGroup> children, 
			Container container) {
		if(children != null) {
			for(Container child : children) {
				child.setContainer(container);
			}
			container.setProductGroups(children);
			setProductGroups(parentToChild, children);
		}
	}


	private ChangeObject getHintObject( Container container ) {
		ChangeObject result = new ChangeObject();
		result.setChangeType( ChangeType.CONTAINER );
		if( container == null ) {
			return result;
		}
		
		ProductContainerData productContainerData = new ProductContainerData();
		productContainerData.setProductContainer( container );
		result.setSelectedData( productContainerData );
		return result;
	}
}
