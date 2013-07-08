package model;


/**
 * 
 * @author jake
 * @invariant			StorageUnit.container == null
 */
public class StorageUnit extends Container {

	private static final long serialVersionUID = 8934631064019112473L;
	
	/**Getter for container
	 * @pre							none
	 * @return						null
	 */
	public Container getContainer() {
		return this.container;		
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
	 */
	@Override
	public boolean isContainerValid() {
		return ( getName() != null ) && nonEmptyName();
	}

	private boolean nonEmptyName() {
		
		return getName().trim().length() > 0;
	}	

}
