package gui.batches;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

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

    ProductData[] productDataProducts;
	Container container;
	Collection<Item> items;
	Collection<Product> products;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		items = new HashSet();
		products = new HashSet();
		productDataProducts = new ProductData[0];
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
		
		ProductData[] productData = DataConverter.toProductDataArray(products);
		
		ProductData selectProduct = null;
		for(int i = 0; i < productData.length; i++){
			int count = 0;
			Product product = (Product)productData[i].getTag();
			
			/* Find selected product */
			if(productData[i].getBarcode().equals(getView().getBarcode())){
				selectProduct = productData[i];
			}
			
			Iterator it2 = items.iterator();
			while(it2.hasNext()){
				Item item = (Item)it2.next();
				if(item.getProduct().equals(product)){
					count++;
				}
			}
			productData[i].setCount(Integer.toString(count));
		}
		getView().setProducts(productData);
		getView().selectProduct(selectProduct);
		selectedProductChanged();
		getView().setCount("1");
		getView().setUseScanner(true);
		Date date = new Date();
		getView().setEntryDate(date);
		getView().setBarcode("");
		
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
		if(getView().getUseScanner()){
			getView().setBarcode("");
		}
		enableComponents();
	}

	/**
	 * This method is called when the selected product changes
	 * in the add item batch view.
	 */
	@Override
	public void selectedProductChanged() {
		ProductData productData = getView().getSelectedProduct();
		Collection collection = new HashSet();
		if(productData != null){
			Iterator it = items.iterator();
			while(it.hasNext()){
				Item item = (Item)it.next();
				if(item.getProduct().equals(productData.getTag())){
					collection.add(item);
				}
			}
			ItemData [] itemData = DataConverter.toItemDataArray(collection);
			getView().setItems(itemData);
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
			if(getModel().getProductManager().upcExists(getView().getBarcode())){
				product = getModel().getProductManager().getProductByUPC(barcode);
			}
			else {
				loadValues();
				return;
			}
		}
		
		for(int i = 0; i < Integer.parseInt(getView().getCount()); i++){
			Barcode barcode = new Barcode();
			Container storageUnit = getModel().getContainerManager().
					getAncestorStorageUnit(container);
			Item item = new Item(null, product, getView().getEntryDate(), barcode);
			getModel().getProductAndItemEditor().
			addItemToStorageUnit(item, (StorageUnit)storageUnit);
			items.add(item);
		}
		products.add(product);
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

