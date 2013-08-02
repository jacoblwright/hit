package data;

import model.*;
import java.util.*;

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
