package reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Model;
import model.Product;
import model.Item;

public class NMonthSupplyProductVisitor implements Visitor {
	
	private float months;
	private Model model;
	private Map <Product, Set<Item>> productItemsMap;
	
	public NMonthSupplyProductVisitor(int timePeriod){
		months = timePeriod;
		model = Model.getInstance();
		productItemsMap = new TreeMap();
		addProductsToMap();
		addItemsToMap();
	}
	
	public void addProductsToMap(){
		Collection collection = model.getProductManager().getProducts();
		Iterator it = collection.iterator();
		
		while(it.hasNext()){
			Product product = (Product)it.next();
			if(product.getThreeMonthSupply() == 0)
				continue;
			productItemsMap.put(product, new TreeSet());
		}
	}
	
	public void addItemsToMap(){
		Collection collection = model.getItemManager().getItems();
		Iterator it = collection.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(productItemsMap.containsKey(item.getProduct())){
				productItemsMap.get(item.getProduct()).add(item);
			}
		}
	}
	
	public boolean hasThreeMonthSupply(Product product) throws IllegalArgumentException{
		if(!productItemsMap.containsKey(product))
			throw new IllegalArgumentException();
		
		int onHand = 0;
		if(productItemsMap.get(product) != null )
			onHand = productItemsMap.get(product).size();
		
		if(onHand < getNMonthSupplyAmount(product))
			return false;
		
		else return true;
	}
	
	public float getNMonthSupplyAmount(Product product){
		return (months / 3) * product.getThreeMonthSupply();
	}

    @Override
    public List<Record> visitAll() {
    	List<NMonthSupplyProductRecord> recordList = new ArrayList();
    	Collection collection = productItemsMap.keySet();
    	Iterator it = collection.iterator();
    	
    	while(it.hasNext()){
    		Product product = (Product)it.next();
    		
    		if(hasThreeMonthSupply(product))
    			continue;
    		
    		NMonthSupplyProductRecord record = new NMonthSupplyProductRecord();
    		record.setDescription(product.getDescription());
    		record.setBarcode(product.getUPC().getBarcode());
    		record.setNMonthSupply(getNMonthSupplyAmount(product));
    		if(productItemsMap.get(product) != null){
    			record.setCurrentSupply(productItemsMap.get(product).size());
    		}
    		else record.setCurrentSupply(0);	
    		
    		recordList.add(record);
    	}
    	Collections.sort(recordList);
    	List<Record> records = new ArrayList<Record>();
    	records.addAll(recordList);
        return records;
    }

}
