package reports;

import java.util.ArrayList;
import java.util.Calendar;
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
		if(date == null){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.YEAR, -20);
			reportDate = c.getTime();
		}
		else reportDate = date;
		
		model = Model.getInstance();
		
		removedItemsList = new ArrayList<Record>();
		
		removedItemsFromProductMap = new TreeMap<Product, Integer>();
		currentItemMap = new TreeMap<Product, Integer>();
		
    	createItemMap(currentItemMap, model.getItemManager().getItems()); 
    	createItemMap(removedItemsFromProductMap, model.getItemManager().getRemovedItems());
	}
	
	public void createItemMap(Map<Product, Integer> itemMap, Collection<Item> collection){
		Iterator<Item> it = collection.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			
			/* Item was removed since last report */
			if(item.getExitTime() != null && item.getExitTime().before(reportDate) ||
					!model.getProductManager().upcExists(item.getProduct().getUPC().getBarcode()))
				continue;
			
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
    	
    	List<Product> alreadyAddedList = new ArrayList<Product>();
    	
    	Collection<Item> collection = model.getItemManager().getRemovedItems();
    	Iterator<Item> it = collection.iterator();
    	
    	while(it.hasNext()){
    		Item item = (Item)it.next();
    		
			/* If the product barcode is no longer in the system, skip it.
			 * If the product has already been added, skip it. */
			if(!model.getProductManager().upcExists(item.getProduct().getUPC().getBarcode())
					|| alreadyAddedList.contains(item.getProduct())
					|| item.getExitTime().before(reportDate))
				continue;
    		
			RemovedItemsRecord record = new RemovedItemsRecord();
			record.setDescription(item.getProduct().getDescription());
			record.setSize(item.getProduct().getSize());
			record.setBarcode(item.getProduct().getUPC().getBarcode());
			record.setRemovedItems(removedItemsFromProductMap.get(item.getProduct()));
			if(currentItemMap.get(item.getProduct()) == null){
				record.setCurrentSupply(0);
			}
			else record.setCurrentSupply(currentItemMap.get(item.getProduct()));
    			
			removedItemsList.add(record);
			alreadyAddedList.add(item.getProduct());   			
    	}

        return removedItemsList;
    }

}
