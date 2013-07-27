package reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Model;
import model.Product;
import model.Item;

/** Prepares the records for the ReportDirector by iterating through all Products
 * 
 * @author andrew
 *
 */

public class ProductStatVisitor implements Visitor {
	
	private Model model;
	private Date reportDate;
	private Map<Product, Set<Item>> currentProductToItemsMap;
	private Map<Product, Set<Item>> removedProductToItemsMap;
	private Map<Product, Set<Item>> productToAllItemsMap;
	private Map<Product, Set<Item>> usedItemsDuringPeriod;
	
	/** Prepares four different maps:
	 * 	1) currentProductToItemsMap - Maps all items that have not been used to their product.
	 *  2) removedProductToItemsMap - Maps all items that have been removed to their product.
	 *  3) productToAllItemsMap - Maps all items, both used and non-used to their product.
	 *  4) usedItemsDuringPeriod - Maps all items used since reportDate to their product.
	 *  
	 *  Also sets the report time period and prepares the model for use.
	 * 
	 * @param timePeriod	the date from which the report will be calculated
	 */
	
	public ProductStatVisitor(int timePeriod){
		currentProductToItemsMap = new TreeMap();
		removedProductToItemsMap = new TreeMap();
		productToAllItemsMap = new TreeMap();
		usedItemsDuringPeriod = new TreeMap();	
		model = Model.getInstance();
		
		setReportDate(timePeriod);
		createCurrentProductToItemsMap();
		createRemoveProductToItemsMap();
		createProductsToAllItemsMap();
		createUsedItemsMap();
	}
	
	/** Sets the appropriate date from which the report will be calculated
	 * 
	 * @param months	the number of months from which this report will be calculated
	 */
	public void setReportDate(int months){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -months);
		reportDate = c.getTime();
	}
	
	/** Generic method to all all Products to a Map<Product, Set<Item>
	 * 
	 * @param itemMap	the map that all products are being added to
	 */
	public void addProductsToMap(Map<Product, Set<Item>> itemMap){
		Collection<Product> productCollection = model.getProductManager().getProducts();
		Iterator<Product> it = productCollection.iterator();
		
		while(it.hasNext()){
			Product product = (Product)it.next();
			currentProductToItemsMap.put(product, new TreeSet());
		}
	}
	
	/** Maps all items to their respective product
	 * 
	 * @param itemMap	the map items are being mapped to
	 * @param iter	the iterator for the items (starts at the beginning)
	 */
	public void addItemsToMap(Map<Product, Set<Item>> itemMap, Iterator<Item> iter){
		while(iter.hasNext()){
			Item item = (Item)iter.next();
			if(itemMap.containsKey(item.getProduct())){
				itemMap.get(item.getProduct()).add(item);
			}
		}
	}
	
	/** Creates the currentProductToItemsMap
	 * 
	 */
	public void createCurrentProductToItemsMap(){
		addProductsToMap(currentProductToItemsMap);
		
		Collection<Item>  itemCollection = model.getItemManager().getItems();
		Iterator<Item> it = itemCollection.iterator();
		
		addItemsToMap(currentProductToItemsMap, it);		
	}
	
	/** Creates the removedProductToItemsMap
	 * 
	 */
	public void createRemoveProductToItemsMap(){
		addProductsToMap(removedProductToItemsMap);
		
		Collection<Item> itemCollection = model.getItemManager().getRemovedItems();
		Iterator<Item> it = itemCollection.iterator();
		
		addItemsToMap(removedProductToItemsMap, it);
	}
	
	/** Creates the createProductsToAllItemsMap
	 * 
	 */
	public void createProductsToAllItemsMap(){
		addProductsToMap(productToAllItemsMap);
		
		Collection<Item> itemCollection = model.getItemManager().getRemovedItems();
		Iterator<Item> it = itemCollection.iterator();
		addItemsToMap(productToAllItemsMap, it);
		
		Collection<Item>  itemCollection2 = model.getItemManager().getItems();
		Iterator<Item> it2 = itemCollection2.iterator();
		addItemsToMap(currentProductToItemsMap, it2);
	}
	
	/** Creates the usedItemsDuringPeriod
	 * 
	 */
	public void createUsedItemsMap(){
		Collection<Item> items = model.getItemManager().getRemovedItems();
		Iterator<Item> it = items.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(item.getExitTime().before(reportDate))
				continue;
			else if(usedItemsDuringPeriod.containsKey(item.getProduct())){
				usedItemsDuringPeriod.get(item.getProduct()).add(item);
			}
			else {
				Set<Item> productItems = new TreeSet<Item>();
				productItems.add(item);
				usedItemsDuringPeriod.put(item.getProduct(), productItems);
			}
		}
	}
	
	/** Returns the number of items currently associated to Product that have not been used
	 * 
	 * @param product	the Product with the items
	 * @return	the amount of items
	 */
	public int getCurrentItemAmount(Product product){
		return currentProductToItemsMap.get(product).size();
	}
	
	/** Returns the number of items that were created on or before a particular date
	 * for a designated product
	 * 
	 * @param date	
	 * @param product
	 * @return
	 */
	public int getItemsCreatedOnAndBeforeDate(Date date, Product product){
		
		Set<Item> items = productToAllItemsMap.get(product);
		if(items == null) return 0;
		Iterator<Item> it = items.iterator();
		
		int count = 0;
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(!item.getEntryDate().after(date))
				count++;
		}
		
		return count;
	}
	
	public int getItemsRemovedOnAndBeforeDate(Date date, Product product){
		
		Set<Item> items = productToAllItemsMap.get(product);
		if(items == null) return 0;
		Iterator<Item> it = items.iterator();
		
		int count = 0;
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(!item.getEntryDate().before(date))
				count++;
		}
		return count;
	}
	
	public int getItemsRemovedDuringReportPeriod(Product product){
		Set<Item> items = removedProductToItemsMap.get(product);
		if(items == null) return 0;
		Iterator<Item> it = items.iterator();
		
		int count = 0;
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(!item.getExitTime().before(reportDate))
				count++;
		}
		return count;
	}
	
	public int getItemsAddedDuringReportPeriod(Product product){
		Set<Item> items = productToAllItemsMap.get(product);
		if(items == null) return 0;
		Iterator<Item> it = items.iterator();
		
		int count = 0;
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(!item.getEntryDate().before(reportDate))
				count++;
		}
		return count;
	}
	
	public int getMinimumSupply(Product product){
		Date date = new Date();
		int minimum = getCurrentItemAmount(product);
		
		while(date.after(reportDate)){
			int dayAmount = getDayTotal(date, product);
			if(dayAmount < minimum)
				minimum = dayAmount;
			date = getPreviousDay(date);
		}

		return minimum;
	}
	
	public int getMaximumSupply(Product product){
		Date date = new Date();
		int maximum = getCurrentItemAmount(product);
		
		while(date.after(reportDate)){
			int dayAmount = getDayTotal(date, product);
			if(dayAmount > maximum)
				maximum = dayAmount;
			date = getPreviousDay(date);
		}

		return maximum;
	}
	
	public int getDayTotal(Date date, Product product){
		return getItemsCreatedOnAndBeforeDate(date, product) - 
				getItemsRemovedOnAndBeforeDate(date, product);
	}
	
	public Date getPreviousDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		return date;
	}
	
	public double getUsedAverage(Product product){
		Set<Item> items = usedItemsDuringPeriod.get(product);
		if(items == null) return 0;
		int runningTotal = 0;
		int dayTotal = 0;
		Iterator<Item> it = items.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			runningTotal += daysBetween(item.getEntryDate(), item.getExitTime());
			dayTotal++;
		}
		
		return runningTotal / dayTotal;
	}
	
	public double getCurrentItemsAverage(Product product){
		Set<Item> items = currentProductToItemsMap.get(product);
		int runningTotal = 0;
		int dayTotal = 0;
		Iterator<Item> it = items.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			runningTotal += daysBetween(item.getEntryDate(), new Date());
			dayTotal++;
		}
		
		return runningTotal / dayTotal;
	}
	
	public int getMaximumAge(Product product, Map<Product, Set<Item>> itemMap){
		Set<Item> items = itemMap.get(product);
		if(items == null) return 0;
		int maximum = 0;
		int itemAge;
		Iterator<Item> it = items.iterator();
		
		while(it.hasNext()){
			Item item = (Item)it.next();
			if(item.getExitTime() != null){
				itemAge = daysBetween(item.getEntryDate(), item.getExitTime());
			}
			else {
				itemAge = daysBetween(item.getEntryDate(), new Date());
			}
			if(itemAge > maximum){
				maximum = itemAge;
			}
		}
		
		return maximum;
	}
	
	public double getSupplyAverage(Product product){
		Date date = new Date();
		double runningTotal = 0;
		int dayTotal = 0;
		
		while (!date.before(reportDate)){
			runningTotal += getDayTotal(date, product);
			dayTotal++;
			date = getPreviousDay(date);
		}
		
		return runningTotal / dayTotal;
	}
	
	public int daysBetween(Date d1, Date d2){
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24)) + 1;
	}
	
    @Override
    public List<Record> visitAll() {
    	List<Record> recordList = new ArrayList<Record>();
    	
    	Collection<Product> productCollection = model.getProductManager().getProducts();
    	Iterator<Product> it = productCollection.iterator();
    	while(it.hasNext()){
    		Product product = (Product)it.next();
    		ProductStatRecord record = new ProductStatRecord();
    		
    		record.setDescription(product.getDescription());
    		record.setBarcode(product.getUPC().getBarcode());
    		record.setSize(product.getSize());
    		record.setThreeMonthSupply(product.getThreeMonthSupply());
    		
    		record.setCurrentSupply(getCurrentItemAmount(product));
    		record.setAverageSupply(getSupplyAverage(product));
    		
    		record.setMaximumSupply(getMaximumSupply(product));
    		record.setMinimumSupply(getMinimumSupply(product));
    		
    		record.setUsedSupply(getItemsRemovedDuringReportPeriod(product));
    		record.setAddedSupply(getItemsAddedDuringReportPeriod(product));
    		
    		record.setShelfLife(product.getShelfLife());
    		
    		record.setUsedAge(getUsedAverage(product));
    		record.setMaximumAge(getMaximumAge(product, usedItemsDuringPeriod));
    		
    		record.setCurrentAverageSupply(getCurrentItemsAverage(product));
    		record.setMaximumCurrentSupply(getMaximumAge(product, currentProductToItemsMap));
    		
    		recordList.add(record);
    	}
    	
        return recordList;
    }

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Map<Product, Set<Item>> getCurrentProductToItemsMap() {
		return currentProductToItemsMap;
	}

	public void setCurrentProductToItemsMap(
			Map<Product, Set<Item>> currentProductToItemsMap) {
		this.currentProductToItemsMap = currentProductToItemsMap;
	}

	public Map<Product, Set<Item>> getRemovedProductToItemsMap() {
		return removedProductToItemsMap;
	}

	public void setRemovedProductToItemsMap(
			Map<Product, Set<Item>> removedProductToItemsMap) {
		this.removedProductToItemsMap = removedProductToItemsMap;
	}

	public Map<Product, Set<Item>> getProductToAllItemsMap() {
		return productToAllItemsMap;
	}

	public void setProductToAllItemsMap(Map<Product, Set<Item>> productToAllItemsMap) {
		this.productToAllItemsMap = productToAllItemsMap;
	}

	public Map<Product, Set<Item>> getUsedItemsDuringPeriod() {
		return usedItemsDuringPeriod;
	}

	public void setUsedItemsDuringPeriod(
			Map<Product, Set<Item>> usedItemsDuringPeriod) {
		this.usedItemsDuringPeriod = usedItemsDuringPeriod;
	}
    
    

}
