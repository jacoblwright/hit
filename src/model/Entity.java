package model;

import java.io.Serializable;

/**Entity is the base model class that has an id and implements Serializable.
 * 
 * @author jake
 *
 */
public class Entity implements Serializable {
	
	private static final long serialVersionUID = -3691241088688260075L;
	
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
}
