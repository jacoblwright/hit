package gui.batches;

import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import gui.common.*;
import gui.item.ItemData;
import gui.product.*;
import model.*;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends ItemBatchController implements
		IRemoveItemBatchController {

	Collection<Item> items;
	Collection<Product> products;
	
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
	
//	/**
//	 * Returns a reference to the view for this controller.
//	 */
//	@Override
//	protected IRemoveItemBatchView getView() {
//		return (IRemoveItemBatchView)super.getView();
//	}
		
		/**
		 * Returns a reference to the view for this controller.
		 */
		@Override
		protected IRemoveItemBatchView getView() {
			return (IRemoveItemBatchView)super.getView();
		}
	
//	/**
//	 * Loads data into the controller's view.
//	 * 
//	 *  {@pre None}
//	 *  
//	 *  {@post The controller has loaded data into its view}
//	 */
//	@Override
//	protected void loadValues() {
//	}

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
		getView().setUseScanner(true);
		getView().enableRedo(cmdHistory.canRedo());
		getView().enableUndo(cmdHistory.canUndo());
		getView().enableItemAction(false);
	}

//	/**
//	 * This method is called when the "Item Barcode" field is changed
//	 * in the remove item batch view by the user.
//	 */
//	@Override
//	public void barcodeChanged() {
//		super.barcodeChanged();
//	}

	/**
	 * This method is called when the "Use Barcode Scanner" setting is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void useScannerChanged() {
		
	}
	
	/**
	 * This method is called when the user clicks the "Remove Item" button
	 * in the remove item batch view.
	 */
	@Override
	public void removeItem() {
		if (selectedItem != null){
			if ( selectedItem.getContainer() == null ) {
				getView().displayWarningMessage("The given item has already been removed!");
			}
			else {
				cmdHistory.doCommand(new RemoveItemFromSU(selectedItem));
			}

//			// updates itself with new values
//			getView().setItems(DataConverter.toItemDataArray(
//							getModel().getItemManager().getItems(
//									previousContainer, 
//									selectedItem.getProduct())));
		}
		else {
			getView().displayErrorMessage("Could not remove item!");
		}

		enableComponents();
		loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	@Override
	public void redo() {
		super.redo();
		enableComponents();
		loadValues();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	@Override
	public void undo() {
		super.undo();
		enableComponents();
		loadValues();
	}

	
	@Override
	public void selectedProductChanged() {
		
	}
	
	
	/**
	 * This method is called when the user clicks the "Done" button
	 * in the transfer item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}
	
	public void doAction(){
		removeItem();
	}
	
}

