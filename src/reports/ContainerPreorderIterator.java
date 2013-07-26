package reports;

import java.util.*;

import model.*;

/**
 * Iterates over the tree structure of Container represented by the
 * Set passed to the constructor, using a preorder traversal.
 */
public class ContainerPreorderIterator implements Iterator<Container> {

	private Stack<Container> containerStack;
	private List<Container> containerTree;
	
    public ContainerPreorderIterator(Set<Container> containerTree) {
    	this.containerTree = new ArrayList<Container>();
    	this.containerTree.addAll(containerTree);
    	containerStack = pushContainers();
    }
    
    private Stack<Container> pushContainers() {
		Stack<Container> stack = new Stack<Container>();
		for( int i = containerTree.size() - 1; i >= 0; i-- ) {
			stack.push(containerTree.get(i));
		}
		return stack;
	}

	@Override
    public boolean hasNext() {
        return !containerStack.isEmpty();
    }

    @Override
    public Container next() {
    	Container container = containerStack.pop();
    	List<Container> children = new ArrayList<Container>();
    	children.addAll(container.getProductGroups());
    	for( int i = children.size() - 1; i >= 0; i-- ) {
    		containerStack.push(children.get(i));
    	}
        return container;
    }

    @Override
    public void remove() {}

}
