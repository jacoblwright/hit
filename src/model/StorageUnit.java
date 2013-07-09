package model;

import static org.junit.Assert.*;

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
		assertTrue( true );
		return this.container;		
	}

	/**Setter for container
	 * @pre							none
	 * @param container				sets parent container to null
	 */
	public void setContainer(Container container) {
		assertTrue( true );
		this.container = null;
	}

	/**Checks to see if current container has different name than sibling containers
	 * @pre							none
	 * @post						if uniqueName { container.isValid = true }
	 */
	@Override
	public boolean isContainerValid() {
		assertTrue( true );
		return ( getName() != null ) && nonEmptyName();
	}

	/**Removes whitespace to see if the name is nonEmpty
	 * @pre							name != null
	 * @return boolean				true if name is nonEmpty, otherwise false.
	 */
	private boolean nonEmptyName() {
		assertNotNull( getName() );
		return getName().trim().length() > 0;
	}	

}
