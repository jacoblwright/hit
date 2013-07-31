package gui.batches;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import model.Barcode;
import model.CommandHistory;
import model.Item;
import model.Product;
import gui.common.Controller;
import gui.common.DataConverter;
import gui.common.IView;
import gui.common.TextFieldTimer;
import gui.item.ItemData;
import gui.product.ProductData;



public abstract class ItemBatchController extends Controller implements ActionListener {

	Collection<Product> products;
	Item selectedItem;
	Product selectedProduct;
	private TextFieldTimer timer;
	
	protected CommandHistory cmdHistory;
	
	protected ItemBatchController(IView view) {
		super(view);
		
		cmdHistory = new CommandHistory();
		timer = new TextFieldTimer(this);
		products = new ArrayList<Product>();
		
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
			timer.start();
		}
		
			
		// Get associated item
		Barcode barcode = new Barcode();
		if (barcode.isValidBarcode(getView().getBarcode())){
			barcode.setBarcode(getView().getBarcode());
		}
		else {
			getView().enableItemAction(false);
			return;
		}
		Product foundProduct = null;
		if ( getModel().getProductManager().upcExists(getView().getBarcode())) {
			foundProduct = getModel().getProductManager().getProductByUPC(barcode);
		}
		Item foundItem = getModel().getItemManager().getItemByTag(barcode);
		
		if (foundProduct != null){
//			if ( !products.contains(foundProduct) ){
//				products.add(foundProduct);
//			}
			products = new ArrayList<Product>();
			products.add(foundProduct);
			selectedProduct = foundProduct;
		}
		else {
			if ( foundItem != null ) {
				selectedItem = foundItem;
				selectedProduct = selectedItem.getProduct();
				products = new ArrayList<Product>();
				products.add(selectedItem.getProduct());
			}
			else {
				selectedProduct = null;
				selectedItem = null;
			}
		}
		
		if ( !getView().getUseScanner() && ( foundItem != null || foundProduct != null ) ){
//			if ( getView().getUseScanner() ){
//				doAction();
//			}
//			else {
				loadValues();
				getView().enableItemAction(true);
//			}
		}
		
	}
	
	protected void loadValues(){
		
		if (selectedProduct != null) {
//			Collection<Product> productsInSystem = new ArrayList<Product>();
//			
//			for (Product cur : products){
//				if (getModel().getProductManager().upcExists(cur.getUPC().getBarcode())){
//					productsInSystem.add(cur);
//				}
//			}
//			
//			products = productsInSystem;
			
			ProductData[] productData = DataConverter.toProductDataArray(products);
			
			Collection<Item> pitems = getModel().getItemManager().getItems();
			
			for(int i = 0; i < productData.length; i++){
				int count = 0;
				Product product = (Product)productData[i].getTag();
				
				Iterator<Item> it2 = pitems.iterator();
				while(it2.hasNext()){
					Item item = it2.next();
					if(item.getProduct().equals(product)){
						count++;
					}
				}
				productData[i].setCount(Integer.toString(count));
			}
			
			getView().setProducts(productData);
			ItemData[] itemData = DataConverter.toItemDataArray(pitems);
			getView().setItems(itemData);
		
			getView().selectProduct(DataConverter.getProductData(selectedProduct, productData));

			if (selectedItem != null) {
				getView().selectItem(DataConverter.getItemData(selectedItem, itemData));
			}
		
		}
		
		
	}
	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	void undo(){
		if (cmdHistory.canUndo()){
			cmdHistory.undo();
		}
		loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	void redo(){
		if (cmdHistory.canRedo()){
			cmdHistory.redo();
		}
		loadValues();
		
	}
	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 */
	public void done() {
		getView().close();
	}
	
	protected void doAction(){
		// subclasses must override
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( getView().getUseScanner() ){
			doAction();
			getView().setBarcode("");
		}
	}
}
