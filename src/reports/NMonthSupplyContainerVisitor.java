package reports;

import gui.common.SizeUnits;

import java.util.ArrayList;
import java.util.Collection;
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
	private List<Record> records;
	private float scale;
	private Converter converter;
	
	public NMonthSupplyContainerVisitor(int numMonths) {
		scale = 0;
		Set<Container> su = new TreeSet<Container>();
		su.addAll(getModel().getContainerManager().getRoot());
		containerTree = new ContainerPreorderIterator(su);
		
		converter = new Converter();
		scale = numMonths / 3f;
	}
	
    @Override
    public List<Record> visitAll() {
    	records = new ArrayList<Record>();
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
			return checkNMonthSupply(container, containerIter);
		}
		return null;
	}
	
	private Record checkNMonthSupply(Container container, Iterator<Container> containerIter) {
		NMonthSupplyContainerRecord record = new NMonthSupplyContainerRecord();
		ProductGroup productGroup = (ProductGroup) container;
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
		
		while(container.hasNext()) {
			ProductGroup pGroup = (ProductGroup) container.next();
			Collection<Item> items = getModel().getItemManager().getItems(pGroup);
			currentSupply += getSupplyRecursively(
					items, productGroup.getThreeMonthSupply().getUnit(), count, 
					productGroup.getThreeMonthSupply().isVolume());
		}
		return currentSupply;
	}
	
	private float getSupplyRecursively(
			Collection<Item> items, Enum<SizeUnits> unit, boolean count, boolean volume) {
		float currentSupply = 0;
		if(count) {
			currentSupply += getCountSupply(items);
		}
		else {
			currentSupply += getConvertedSupply(items, unit, volume);
		}
		return currentSupply;
	}
	
	private float getConvertedSupply(Collection<Item> items, Enum<SizeUnits> unit, boolean volume) {
		float currentSupply = 0;
		for(Item item : items) {
			//checks if the units are of the same type, volume or weight
			try {
				if(volume) {
					currentSupply += converter.convertVolume(
							item.getProduct().getSize(), unit);
				}
				else {
					currentSupply += converter.convertWeight(
							item.getProduct().getSize(), unit);
				}
			} catch(IllegalArgumentException e) {
				System.out.println(
						"Item = " + item.getProduct().getDescription() + " is inconsistent");
			}
		}
		return currentSupply;
	}

	private float getCountSupply(Collection<Item> items) {
		int supplyCount = 0;
		for(Item item : items) {
			if(item.getProduct().getShelfLife() != 0) {
				supplyCount++;
			}
		}
		return supplyCount;
	}

	private Model getModel() {
    	return Model.getInstance();
    }

}
