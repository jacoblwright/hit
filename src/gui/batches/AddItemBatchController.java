package gui.batches;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import config.IOConfig;

import printers.BarcodePrinter;

import model.Barcode;
import model.Container;
import model.Item;
import model.Product;
import model.StorageUnit;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import java.awt.event.*;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController, ActionListener {
    
    private TextFieldTimer timer;

	Container container;
	Collection<Item> items;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		items = new HashSet();
		container = (Container)target.getTag();
		construct();
		
		timer = new TextFieldTimer(this);
		
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IAddItemBatchView getView() {
		return (IAddItemBatchView) super.getView();
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
		getView().setCount("1");
		getView().setUseScanner(true);
		Date date = new Date();
		getView().setEntryDate(date);
		
		if(container !=  null){
			Collection col = getModel().getProductManager().getProducts(container);
			if (col == null) return;
			ProductData[] productArray = DataConverter.toProductDataArray(col);
			for(int i = 0; i < productArray.length; i++){
				try{
					Collection itemCol = getModel().getItemManager().getItems(container, (Product)productArray[i].getTag());
					productArray[i].setCount(Integer.toString(itemCol.size()));
				}
				catch(NullPointerException e){
					
				}
			}
			getView().setProducts(productArray);
		}
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
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String result = sdf.format(getView().getEntryDate());
			Date today = new Date();
			
			if(!getView().getBarcode().isEmpty() && getView().getUseScanner() == false && 
					Integer.parseInt(getView().getCount()) > 0 && 
					!getView().getEntryDate().after(today)){
				getView().enableItemAction(true);
			}
			else getView().enableItemAction(false);
		}
		catch(IllegalArgumentException e){
			getView().enableItemAction(false);
		}

		
		getView().enableRedo(false);
		getView().enableUndo(false);
	}

	/**
	 * This method is called when the "Entry Date" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void entryDateChanged() {
		if(getView().getEntryDate() != null)
			enableComponents();
		else 
			getView().enableItemAction(false);
	}

	/**
	 * This method is called when the "Count" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void countChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the "Product Barcode" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void barcodeChanged() {
		if(getView().getUseScanner() == true && !getView().getBarcode().isEmpty()){
			timer.start();
		}
		enableComponents();
	}

	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
		getView().setBarcode("");
		enableComponents();
	}

	/**
	 * This method is called when the selected product changes
	 * in the add item batch view.
	 */
	@Override
	public void selectedProductChanged() {
		ProductData productData = getView().getSelectedProduct();
		if(productData != null){
			Collection col = getModel().getItemManager().getItems(container, (Product)productData.getTag());
			ItemData[] itemArray = DataConverter.toItemDataArray(col);
			getView().setItems(itemArray);
		}
	}

	/**
	 * This method is called when the user clicks the "Add Item" button
	 * in the add item batch view.
	 */
	@Override
	public void addItem() {
		Product product;
		if(getModel().getProductManager().upcExists(getView().getBarcode())){
			Barcode code = new Barcode();
			code.setBarcode(getView().getBarcode());
			product = getModel().getProductManager().getProductByUPC(code);
		}
		else{
			getView().displayAddProductView();
			Barcode barcode = new Barcode();
			barcode.setBarcode(getView().getBarcode());
			product = getModel().getProductManager().getProductByUPC(barcode);
		}
		
		for(int i = 0; i < Integer.parseInt(getView().getCount()); i++){
			Barcode barcode = new Barcode();
			Container storageUnit = getModel().getContainerManager().
					getAncestorStorageUnit(container);
			Item item = new Item(null, product, getView().getEntryDate(), barcode);
			getModel().getProductAndItemEditor().addItemToStorageUnit(item, (StorageUnit)storageUnit);
			items.add(item);
		}
		
		loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@Override
	public void done(){
		getView().close();
		if(items.size() != 0){
			try{
				BarcodePrinter.printBarcodes(items, IOConfig.getBarcodeTagsFile(), true);
			}
			catch(Exception e){
				getView().displayErrorMessage("Could Not Display PDF");
			}
		}
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        
        addItem();
        
    }
	
}

