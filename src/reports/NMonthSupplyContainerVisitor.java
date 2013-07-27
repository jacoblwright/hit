package reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Container;
import model.Model;
import model.ProductGroup;

public class NMonthSupplyContainerVisitor implements Visitor {

	private Iterator<Container> containerTree;
	private final int numMonths;
	private List<Record> records;
	private float scale;
	
	public NMonthSupplyContainerVisitor(Iterator<Container> containerTree, int numMonths) {
		this.containerTree = containerTree;
		this.numMonths = numMonths;
		records = new ArrayList<Record>();
		scale = numMonths / 3;
	}
	
    @Override
    public List<Record> visitAll() {
        while(containerTree.hasNext()) {
        	Record subRecord = getNMonthSupply(containerTree.next()); 
        	if(subRecord != null) {
        		records.add(subRecord);
        	}
        }
    	return records;
    }

	private Record getNMonthSupply(Container container) {
		if(container.getContainer() != null &&
				!((ProductGroup) container).getThreeMonthSupply().isUnspecified()) {
			Set<Container> containerSet = new TreeSet<Container>();
			containerSet.add(container);
			Iterator<Container> containerIter = new ContainerPreorderIterator(containerSet);
			return checkNMonthSupply(containerIter);
		}
		return null;
	}
	
	private Record checkNMonthSupply(Iterator<Container> containerIter) {
		// TODO Auto-generated method stub
		return null;
	}

	private Model getModel() {
    	return Model.getInstance();
    }

}
