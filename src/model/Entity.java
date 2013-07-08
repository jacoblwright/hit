package model;

import java.io.Serializable;

/**Entity is the base model class that has an id and implements Serializable.
 * 
 * @author jake
 *
 */
public class Entity implements Serializable {

	private static final long serialVersionUID = -3691241088688260075L;

	protected static final int HASH_MULTIPLIER_PRIME = 31;
	protected static final int HASH_BASE_PRIME = 7;
	
	/**Unique Id */
	private int id;
	
	/**Gets Id
	 * @pre none
	 * @post getId()
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**Sets id
	 * @pre none
	 * @post this.id = id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
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
	
	protected int createHash( int hash, Object obj )
	{
		return ( hash * HASH_MULTIPLIER_PRIME ) + ( obj != null ? obj.hashCode() : 0 );
	}
}
