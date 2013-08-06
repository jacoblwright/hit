package gui.batches;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;

import config.IOConfig;
import printers.BarcodePrinter;
import model.*;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

import java.awt.event.*;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends ItemBatchController implements
		IAddItemBatchController, ActionListener {
    
    private TextFieldTimer timer;

	Stack<List<Item>> itemBatches;
	Stack<List<Item>> redoItemBatches;
    Stack<Product> newProducts;
    Stack<Product> redoNewProducts;
	Container container;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);

		container = (Container)target.getTag();
		itemBatches = new Stack<List<Item>>();
		redoItemBatches = new Stack<List<Item>>();
		newProducts = new Stack<Product>();
		redoNewProducts = new Stack<Product>();
		setDefaultValues();
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

	protected void setDefaultValues() {
		getView().setUseScanner(true);
		resetComponents();
		
	}
	
	protected void resetComponents(){
		getView().setCount("1");
		Date date = new Date();
		getView().setEntryDate(date);
		getView().giveBarcodeFocus();
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

		super.loadValues();
		
		if (itemBatches == null || selectedProduct == null){
			return;
		}
		
		
		Collection<Item> productBatchItems = new TreeSet<Item>();
		for (Collection<Item> cur : itemBatches){
			for (Item curItem : cur){
				if (curItem.getProduct() == selectedProduct){
					productBatchItems.add(curItem);
				}
			}
		}
		
		getView().setItems(DataConverter.toItemDataArray(productBatchItems));
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
		
		getView().enableRedo(cmdHistory.canRedo());
		getView().enableUndo(cmdHistory.canUndo());
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
		try {
			if (!getView().getCount().equals("")){
				int count = Integer.parseInt(getView().getCount());
				if (count < 0){
					throw new NumberFormatException("negative number");
				}
			}
		}
		catch (NumberFormatException e){
			getView().displayErrorMessage(
					"You may only use positive whole numbers in the count.");
			getView().setCount("1");
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
//		ProductData productData = getView().getSelectedProduct();
//		Collection collection = new HashSet();
//		if(productData != null){
//			Iterator it = items.iterator();
//			while(it.hasNext()){
//				Item item = (Item)it.next();
//				if(item.getProduct().equals(productData.getTag())){
//					collection.add(item);
//				}
//			}
//			ItemData [] itemData = DataConverter.toItemDataArray(collection);
//			getView().setItems(itemData);
//		}
//		if(productData != null){
//			Collection<Item> allProductItems = new TreeSet<Item>();
//			for (Item i : getModel().getItemManager().getItems()){
//				if ( i.getProduct().equals(productData.getTag()) ) {
//					allProductItems.add(i);
//				}
//			}
//			
//			getView().setItems(DataConverter.toItemDataArray(allProductItems));
//		}
		selectedProduct = (Product) getView().getSelectedItem().getTag();
		loadValues();
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
			newProducts.push(null);
		}
		else{
			getView().displayAddProductView();
			
			Barcode barcode = new Barcode();
			barcode.setBarcode(getView().getBarcode());
			if(getModel().getProductManager().upcExists(getView().getBarcode())){
				product = getModel().getProductManager().getProductByUPC(barcode);
			}
			else {
				product = getModel().getProductAndItemEditor().getNewlyAddedProduct();
				if (product != null){
					newProducts.push(product);
				}
			}
		}
		
		if (product != null){
			List<Item> itemsToAdd = new ArrayList<Item>();
			for(int i = 0; i < Integer.parseInt(getView().getCount()); i++){
				Barcode barcode = new Barcode();
				
				Item item = new Item(null, product, getView().getEntryDate(), barcode);
				itemsToAdd.add(item);
				
				if(product.getCreationDate().after(getView().getEntryDate())){
					product.setCreationDate(getView().getEntryDate());
				}
			}
			
			Container storageUnit = getModel().getContainerManager().
					getAncestorStorageUnit(container);
			
			cmdHistory.doCommand(new AddItemsToSU(itemsToAdd, (StorageUnit) storageUnit));
			itemBatches.push(itemsToAdd);
			
			if (!products.contains(product)){
				products.add(product);
			}
			
			Collection<Product> allProducts = getModel().getProductManager().getProducts();
			Collection<Product> allProductsList = new ArrayList<Product>();
			allProductsList.addAll(allProducts);

			selectedProduct = product;
			loadValues();
			enableComponents();
//			getView().setBarcode("");
			resetComponents();
		}
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
		super.redo();
		itemBatches.push(redoItemBatches.pop());
		Product newProduct = redoNewProducts.pop();
		newProducts.push(newProduct);
		if (newProduct != null){
			products.add(newProduct);
		}
		enableComponents();
		loadValues();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
		super.undo();
		redoItemBatches.push(itemBatches.pop());
		Product newProduct = newProducts.pop();
		redoNewProducts.push(newProduct);
		if (newProduct != null){
			products.remove(newProduct);
		}
		enableComponents();
		loadValues();
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
		if(itemBatches.size() != 0){
			try{
				Collection<Item> addedItems = new TreeSet<Item>();
				while (!itemBatches.isEmpty()){
					addedItems.addAll(itemBatches.pop());
				}
				BarcodePrinter.printBarcodes(addedItems, IOConfig.getBarcodeTagsFile(), true);
			}
			catch(Exception e){
				getView().displayErrorMessage("Could Not Display PDF");
			}
		}
	}
	
	public void doAction(){
		addItem();
	}

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        
//        addItem();
//        
//    }
	
}

