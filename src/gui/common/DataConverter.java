package gui.common;

import java.util.Collection;
import java.util.Iterator;

import model.Model;
import model.Item;
import model.Product;
import gui.item.*;
import gui.product.ProductData;

public class DataConverter {
	/** Provides functionality to the various controllers to convert model objects to 
	 *  the data from our collections to the view's Data arrays.
	 * 
	 * @param col
	 * @return
	 */
	
	public static ItemData[] toItemDataArray(Collection<Item> col) {
		ItemData[] ret = new ItemData[col.size()];
		int i = 0;
		Iterator<Item> iter = col.iterator();
		while (iter.hasNext()) {
			ret[i] = new ItemData(iter.next());
			i++;
		}
		return ret;
	}
	
	public static ItemData getItemData(Item itemToFind, ItemData[] items){
		for (int i = 0; i < items.length; i++){
			if ( ((Item)items[i].getTag()).equals(itemToFind) ){
				return items[i];
			}
		}
		return null;
	}
	
	// partial implementation for testing's sake
	public static ProductData[] toProductDataArray(Collection<Product> col) {
		ProductData[] ret = new ProductData[col.size()];
		int i = 0;
		Iterator<Product> iter = col.iterator();
		while (iter.hasNext()) {
			Product cur = iter.next();
			ret[i] = new ProductData();
			ret[i].setDescription(cur.getDescription());
			ret[i].setTag(cur);
			
		}
		return ret;
	}
	
}
