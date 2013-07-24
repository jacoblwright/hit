package reports;

import java.util.*;
import model.*;

/**
 * Iterates over the tree structure of Container represented by the
 * Set passed to the constructor, using a preorder traversal.
 */
public class ContainerPreorderIterator implements Iterator<Container> {

    public ContainerPreorderIterator(Set<Container> containerTree) {
                       
    }
    
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Container next() {
        return null;
    }

    @Override
    public void remove() {}

}
