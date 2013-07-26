package reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Item;
import model.Model;
import model.Product;

public class RemovedItemsVisitor implements Visitor {
	
	Model model;
	Date reportDate;
	List<Record> removedItemsList;
	Map<Product, Integer> removedItemsFromProductMap;
	Map<Product, Integer> currentItemMap;
	
	public RemovedItemsVisitor(Date date){
		reportDate = date;
		
		model = Model.getInstance();
		
		removedItemsList = new ArrayList();
		
		removedItemsFromProductMap = new TreeMap();
		currentItemMap = new TreeMap();
		
    	createItemMap(currentItemMap, model.getItemManager().getItems()); 
    	createItemMap(removedItemsFromProductMap, model.getItemManager().getRemovedItems());
	}
	
	public void createItemMap(Map itemMap, Collection collection){
		Iterator<Item> it = collection.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(itemMap.containsKey(item.getProduct())){
				int count = (Integer)itemMap.get(item.getProduct()) + 1;
				itemMap.remove(item.getProduct());
				itemMap.put(item.getProduct(), count);
			}
			else
				itemMap.put(item.getProduct(), 1);
		}
	}

    @Override
    public List<Record> visitAll() {
    	
    	List<Product> alreadyAddedList = new ArrayList();
    	
    	Collection<Item> collection = model.getItemManager().getRemovedItems();
    	Iterator<Item> it = collection.iterator();
    	
    	while(it.hasNext()){
    		Item item = (Item)it.next();
    		if(!alreadyAddedList.contains(item.getProduct())){
    			RemovedItemsRecord record = new RemovedItemsRecord();
    			record.setDescription(item.getProduct().getDescription());
    			record.setSize(item.getProduct().getSize());
    			record.setBarcode(item.getProduct().getUPC().getBarcode());
    			record.setRemovedItems(removedItemsFromProductMap.get(item.getProduct()));
    			record.setCurrentSupply(currentItemMap.get(item.getProduct()));
    			
    			removedItemsList.add(record);
    			alreadyAddedList.add(item.getProduct());
    		}
    			
    	}

        return removedItemsList;
    }

}
