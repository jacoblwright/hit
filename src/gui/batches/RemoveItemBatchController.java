package gui.batches;

import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import gui.common.*;
import gui.item.ItemData;
import gui.product.*;
import model.Model;
import model.Item;
import model.Barcode;
import model.Product;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
		IRemoveItemBatchController {

	Set<Item> items;
	Set<Product> products;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the remove item batch view.
	 */
	public RemoveItemBatchController(IView view) {
		super(view);

		items = new TreeSet<Item>();
		products = new TreeSet<Product>();
		
		construct();
		

	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IRemoveItemBatchView getView() {
		return (IRemoveItemBatchView)super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		getView().enableRedo(false);
		getView().enableUndo(false);
		getView().enableItemAction(false);
	}

	/**
	 * This method is called when the "Item Barcode" field is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void barcodeChanged() {
		// Get associated item
		Barcode tag = new Barcode(getView().getBarcode());
		Item found = Model.getInstance().getItemManager().getItemByTag(tag);
		
		if (found != null) { //TODO: check java api for returning items that do not exist
		
			// Get associated product
			Product product = found.getProduct();
		
			// add them to the stored sets
			items.add(found);
			products.add(product);
		}
		
		getView().setItems(DataConverter.toItemDataArray(items));
		
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void useScannerChanged() {
		
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the remove item batch view.
	 */
	@Override
	public void selectedProductChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "Remove Item" button
	 * in the remove item batch view.
	 */
	@Override
	public void removeItem() {
//		editor.removeItem();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the remove item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}
	
	
	
	
	
	
}

