package data;

import model.*;
import java.util.*;

/**
 * Data transfer object for Product to Container mapping.
 */
public class ProductToContainerDTO {
    
    private Product product;
    private Set<Container> containers;
    
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Set<Container> getContainers() {
        return containers;
    }
    public void setContainers(Set<Container> containers) {
        this.containers = containers;
    }

}
