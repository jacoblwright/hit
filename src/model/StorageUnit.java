package model;


/**
 * Storage Unit extends Container and implements the abstract methods.
 * @author jake
 * @invariant			StorageUnit.container == null
 */
public class StorageUnit extends Container {

	private static final long serialVersionUID = 5653817054706082835L;

	/**Constructor initializes name.
	 * @pre none
	 * @post setName
	 */
	public StorageUnit() {
		assert true;
		setName("Untitled");
	}
	
	/**Getter for container.
	 * @pre							none
	 * @return						null
	 */
	public Container getContainer() {
		assert true;
		return this.container;		
	}

	/**Setter for container.
	 * @pre							none
	 * @param container				sets parent container to null
	 */
	public void setContainer(Container container) {
		assert true ;
		this.container = null;
	}

	/**Checks to see if current container has different name than sibling containers.
	 * @pre							none
	 * @post						if uniqueName { container.isValid = true }
	 */
	@Override
	public boolean isContainerValid() {
		assert true;
		return nonEmptyName();
	}

	/**Removes whitespace to see if the name is nonEmpty.
	 * @pre							name != null
	 * @return boolean				true if name is nonEmpty, otherwise false.
	 */
	private boolean nonEmptyName() {
		return getName().trim().length() > 0;
	}	

}
