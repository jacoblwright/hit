package data;

import model.*;

import java.util.*;

/**
 * Data transfer object for Product to Container mapping.
 */
public class ProductToContainerDTO {
    
	private int id;
    private int productID;
    private int containerID;
    
    public void ProductToContainerDTO(Product product, Container container) {
    	this.productID = product.getId();
    	this.containerID = container.getId();
    }

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getContainerID() {
		return containerID;
	}

	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){
		return "ProductToContainerDTO [id: " + id + ", productID: " + productID + 
				", containerId: " + containerID;
	}
    
}
