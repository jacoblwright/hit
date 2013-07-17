package gui.batches;

import java.util.Collection;
import java.util.TreeSet;

import model.Barcode;
import model.Item;
import model.Product;
import gui.common.Controller;
import gui.common.DataConverter;
import gui.common.IView;
import gui.common.ScannerTimer;
import gui.item.ItemData;
import gui.product.ProductData;



public class ItemBatchController extends Controller {

	protected ItemBatchController(IView view) {
		super(view);
		
		construct();
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IItemBatchView getView() {
		return (IItemBatchView)super.getView();
	}

	/**
	 * This method is called when the "Item Barcode" field is changed
	 * in the remove item batch view by the user.
	 */

	public void barcodeChanged() {
		
		if ( getView().getUseScanner() ) {
			if (! ScannerTimer.isEntered() ){
				return;
			}
			
		}
		else {
//			updateRemoveItemButton();
			
			// Get associated item
			Barcode tag = new Barcode(getView().getBarcode());
			Item found = getModel().getItemManager().getItemByTag(tag);

			if (found == null) {
				
				// TODO: spec says enable action if non empty
				//       i say enable action if item exists
				getView().enableItemAction(false); 
				
			}
			else {
				Collection<Product> tmp = new TreeSet<Product>();
				tmp.add(found.getProduct());
				ProductData[] plist = DataConverter.toProductDataArray(tmp);
				// Sets the view to display items product
				getView().setProducts(plist);
				
				// Sets the view to display all items for that product
				ItemData[] ilist = DataConverter.toItemDataArray(
						getModel().getItemManager().getItems(
								found.getContainer(), found.getProduct()));
				
				getView().setItems(ilist);
				
				// Select the item and update the button
				getView().selectProduct(plist[0]); // should only ever be size 1
				getView().selectItem(DataConverter.getItemData(found, ilist));
				getView().enableItemAction(true);
				
			}
		}
	}
	
	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 */
	
	public void done() {
		getView().close();
	}
}
