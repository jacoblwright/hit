package gui.batches;

import model.Container;
import model.Item;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends ItemBatchController implements
		ITransferItemBatchController {
	
	
	Container container;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the transfer item batch view.
	 * @param target Reference to the storage unit to which items are being transferred.
	 */
	public TransferItemBatchController(IView view, ProductContainerData target) {
		super(view);

		container = (Container)target.getTag();
		
		construct();
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected ITransferItemBatchView getView() {
		return (ITransferItemBatchView)super.getView();
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
		getView().setUseScanner(true);
		getView().enableRedo(false);
		getView().enableUndo(false);
		getView().enableItemAction(false);
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * transfer item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the transfer item batch view.
	 */
	@Override
	public void selectedProductChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 */
	@Override
	public void transferItem() {
		
		ItemData selected = getView().getSelectedItem();
		
		if ( selected != null ) {
			Item selectedItem = (Item) selected.getTag();
			
			if (selectedItem != null ){
				
//				getView().displayInformationMessage("Moving Item " + selectedItem);
				if ( container != null ){
					getModel().getProductAndItemEditor().moveItem(selectedItem, container);
					getView().setItems(DataConverter.toItemDataArray(
							getModel().getItemManager().getItems(container, selectedItem.getProduct()))
																	);
					
				}
				else {
					getView().displayWarningMessage("Could not move item to container");
				}
				
			}
		}
		
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void undo() {
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
		transferItem();
	}

}

