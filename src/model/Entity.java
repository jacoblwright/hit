package model;

import java.io.Serializable;
import static org.junit.Assert.*;

/**Entity is the base model class that has an id and implements Serializable.
 * 
 * @author jake
 *
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -3691241088688260075L;
	protected static final int HASH_MULTIPLIER_PRIME = 31;
	protected static final int HASH_BASE_PRIME = 7;
	
	/**Id */
	private int id;
	
	/**Gets Id
	 * @pre none
	 * @post getId()
	 * @return id
	 */
	public int getId() {
		assert true;
		return id;
	}

	/**Sets id
	 * @pre none
	 * @post this.id = id
	 * @param id
	 */
	public void setId(int id) {
		assert true;
		this.id = id;
	}
	
	/**Creates the string version of this object
	 * @pre 				none	
	 * @return String		this.toString()
	 */
	@Override
	public String toString() {
		assert true;
		return "Entity [id=" + id + "]";
	}

	/**Creates a unique hashcode for this object
	 * @pre					none
	 * @return int			unique Integer
	 */
	@Override
	public int hashCode() {
		assert true;
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**checks equality between two objects
	 * @pre						none
	 * @param obj				obj in question fo equality		
	 * @return boolean 			if this == obj return true, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		assert true;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/** Creates a hashCode based on a previous hash and an object
	 * @pre 				none
	 * @param hash			currentHash
	 * @param obj			object that needs to be hashed
	 * @return				new hash based on old hash and object passed in
	 */
	protected int createHash( int hash, Object obj ) {
		assert true;
		return ( hash * HASH_MULTIPLIER_PRIME ) + ( obj != null ? obj.hashCode() : 0 );
	}
}
