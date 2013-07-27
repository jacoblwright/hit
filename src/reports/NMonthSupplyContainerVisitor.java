package reports;

import gui.common.SizeUnits;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Container;
import model.Item;
import model.Model;
import model.ProductGroup;
import model.Quantity;

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
		NMonthSupplyContainerRecord record = new NMonthSupplyContainerRecord();
		ProductGroup productGroup = (ProductGroup) containerIter.next();
		record.simpleInitialize(productGroup, scale, getModel());
	    float currentSupply = 0;
		
		if(productGroup.getThreeMonthSupply().isCount()) {
			currentSupply = getSupply(productGroup, containerIter, true);
		}
		else {
			currentSupply = getSupply(productGroup, containerIter, false);
		}
		if(currentSupply < record.getThreeMonthSupply().getNumber()) {
			
			record.setCurrentSupply(
					new Quantity( currentSupply, record.getThreeMonthSupply().getUnit()));
			return record;
		}
		return null;
	}

	private float getSupply(ProductGroup productGroup, 
			Iterator<Container> container, boolean count) {
		float currentSupply = 0;
		List<Item> items = 
				(List<Item>) getModel().getItemManager().getItems(productGroup);
		currentSupply += getSupplyRecursively(
				items, productGroup.getThreeMonthSupply().getUnit(), count, 
				productGroup.getThreeMonthSupply().isVolume());
		
		while(container.hasNext()) {
			ProductGroup pGroup = (ProductGroup) container.next();
			List<Item> items1 = (List<Item>) getModel().getItemManager().getItems(pGroup);
			currentSupply += getSupplyRecursively(
					items1, productGroup.getThreeMonthSupply().getUnit(), count, 
					productGroup.getThreeMonthSupply().isVolume());
		}
		return currentSupply;
	}
	
	private float getSupplyRecursively(
			List<Item> items, Enum<SizeUnits> unit, boolean count, boolean volume) {
		float currentSupply = 0;
		if(count) {
			currentSupply += getCountSupply(items);
		}
		else {
			currentSupply += getConvertedSupply(items, unit, volume);
		}
		return currentSupply;
	}
	
	private float getConvertedSupply(List<Item> items, Enum<SizeUnits> unit, boolean volume) {
		float currentSupply = 0;
		for(Item item : items) {
			//checks if the units are of the same type, volume or weight
			if(item.getProduct().getSize().getUnit().equals(unit)) {
				try {
					if(volume) {
						currentSupply += Converter.convertVolume(
								item.getProduct().getSize(), unit);
					}
					else {
						currentSupply += Converter.convertWeight(
								item.getProduct().getSize(), unit);
					}
				} catch(IllegalArgumentException e) {
					System.out.println(
							"Item = " + item.getProduct().getDescription() + " is inconsistent");
				}
			}
		}
		return currentSupply;
	}

	private float getCountSupply(List<Item> items) {
		return items.size();
	}

	private Model getModel() {
    	return Model.getInstance();
    }

}
