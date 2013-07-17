package gui.common;

import java.util.Collection;
import java.util.Iterator;

import model.Item;
import model.Product;
import gui.item.*;
import gui.product.*;

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
		}
		return ret;
	}
	
	public static ProductData[] toProductDataArray(Collection<Product> col) {
		
		ProductData[] ret = new ProductData[col.size()];
		int i = 0;
		Iterator<Product> iter = col.iterator();
		while (iter.hasNext()) {
			ret[i] = new ProductData(iter.next());
		}
		return ret;
	}
}
