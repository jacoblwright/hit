package gui.inventory;

import gui.common.*;
import gui.item.*;
import gui.product.*;

import java.io.IOException;
// Just for debug testing
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.*;

import model.*;

/**
 * Controller class for inventory view.
 */
public class InventoryController extends Controller 
									implements IInventoryController, Observer {
	private final String EMPTY = "";
	private ProductData selectedProductData;
	private ItemData selectedItemData;
	private ProductContainerData selectedContainerData;

	/**
	 * Constructor.
	 *  
	 * @param view Reference to the inventory view
	 */
	public InventoryController(IInventoryView view) {
		super(view);
		getModel().getContainerManager().addObserver( this );
		getModel().getProductManager().addObserver( this );
		getModel().getItemManager().addObserver( this );
	
		construct();
		
	//		debugInit();
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IInventoryView getView() {
		return (IInventoryView)super.getView();
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
		Set<StorageUnit> storageUnits = getModel().getContainerManager().getRoot();
		ProductContainerData root = new ProductContainerData();
		for( StorageUnit container : storageUnits ) {
			ProductContainerData child = new ProductContainerData();
			child.setProductContainer( container );
			root.addChild( child );
		}
		getView().setProductContainers(root);
		
		if( selectedContainerData != null ) {	
			getView().selectProductContainer( selectedContainerData );
			productContainerSelectionChanged();
			productSelectionChanged();
		}
		loadContextPanel( selectedContainerData );
		
	}

	private void loadContextPanel( ProductContainerData selectedContainer ) {
		if( selectedContainer != null ) {
			if( selectedContainer.getTag() == null ) {
				allStorageUnitsSelected();
			}
			else if( ((Container) selectedContainer.getTag()).getContainer() == null ) {
				storageUnitSelected();
			}
			else if( ((Container) selectedContainer.getTag()).getContainer() != null ) {
				productGroupSelected();
			}
			else {
				setContextPanel( EMPTY, EMPTY, EMPTY );
			}
		}
		else {
			setContextPanel( EMPTY, EMPTY, EMPTY );
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
		return;
	}
	
	//
	// IInventoryController overrides
	//

	/**
	 * Returns true if and only if the "Add Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canAddStorageUnit() {
		return true;
	}
	
	/**
	 * Returns true if and only if the "Add Items" menu item should be enabled.
	 */
	@Override
	public boolean canAddItems() {
		return true;
	}
	
	/**
	 * Returns true if and only if the "Transfer Items" menu item should be enabled.
	 */
	@Override
	public boolean canTransferItems() {
		return true;
	}
	
	/**
	 * Returns true if and only if the "Remove Items" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItems() {
		return true;
	}

	/**
	 * Returns true if and only if the "Delete Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteStorageUnit() {
		ProductContainerData pcd = getView().getSelectedProductContainer();
		return getModel().getContainerEditor().canDeleteContainer( (Container) pcd.getTag() );
	}
	
	/**
	 * This method is called when the user selects the "Delete Storage Unit" menu item.
	 */
	@Override
	public void deleteStorageUnit() {
		try {
			ProductContainerData container = getView().getSelectedProductContainer();
			getModel().getTransaction().startTransaction();
			getModel().getContainerEditor().deleteContainer( (Container) container.getTag() );
			getModel().getTransaction().startTransaction();
		} catch(IOException e) {
			//Ask team what to do here?
		}
	}

	/**
	 * Returns true if and only if the "Edit Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canEditStorageUnit() {
		return true;
	}

	/**
	 * Returns true if and only if the "Add Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canAddProductGroup() {
		return true;
	}

	/**
	 * Returns true if and only if the "Delete Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProductGroup() {
		return canDeleteStorageUnit();
	}

	/**
	 * Returns true if and only if the "Edit Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canEditProductGroup() {
		return true;
	}
	
	/**
	 * This method is called when the user selects the "Delete Product Group" menu item.
	 */
	@Override
	public void deleteProductGroup() {
		deleteStorageUnit();
	}

	private Random rand = new Random();
	
	private String getRandomBarcode() {
		Random rand = new Random();
		StringBuilder barcode = new StringBuilder();
		for (int i = 0; i < 12; ++i) {
			barcode.append(((Integer)rand.nextInt(10)).toString());
		}
		return barcode.toString();
	}

	/**
	 * This method is called when the selected item container changes.
	 */
	@Override
	public void productContainerSelectionChanged() {

		ProductContainerData productContainerData = getView().getSelectedProductContainer();
		ItemData selectedItem = getView().getSelectedItem();
		
		ProductData[] productArray;
		ItemData[] itemArray = new ItemData[0];
		ProductData selectedProduct = getView().getSelectedProduct();
		if(productContainerData != null && productContainerData.getTag() == null){
			Collection<Product> col = getModel().getProductManager().getProducts();
			productArray = DataConverter.toProductDataArray(col);
			setProductsDataSize(productArray, productContainerData);
			
		}
		
		else if(productContainerData != null && productContainerData.getTag() != null){
			Collection<Product> col = getModel().getProductManager().
					getProducts((Container)productContainerData.getTag());
			productArray = DataConverter.toProductDataArray(col);
			setProductsDataSize(productArray, productContainerData);
			if (selectedProduct != null){
				if ( selectedProduct.hasTag()){
					itemArray = DataConverter.toItemDataArray(
							getModel().getItemManager().getItems(
									(Container) productContainerData.getTag(), 
									(Product) selectedProduct.getTag()));
				}
			}
			
		}
		else{
			productArray = new ProductData[0];
		}
		getView().setProducts(productArray);
		if ( selectedProduct != null){
			if (selectedProduct.hasTag()){
				getView().selectProduct(
						DataConverter.getProductData((Product) 
								selectedProduct.getTag(), productArray));
			}
		}
		
		getView().setItems(itemArray);
		if (selectedItem != null ){
			if (selectedItem.hasTag()){
				getView().selectItem(
						DataConverter.getItemData((Item) selectedItem.getTag(), itemArray));
				ItemData tmp = getView().getSelectedItem();
				getView();
			}
		}
	}

	public void setProductsDataSize(ProductData[] productArray, 
			ProductContainerData productContainerData){
		
		for(int i = 0; i < productArray.length; i++){
			/* Set the count for the product */
			try{
				if(productContainerData.getTag() == null){
					Collection<Item> itemCol = getModel().getItemManager().getItems();
					Iterator it = itemCol.iterator();
					int count = 0;
					while(it.hasNext()){
						Item item = (Item)it.next();
						if(item.getProduct().equals(productArray[i].getTag())){
							count++;
						}
					}
					productArray[i].setCount(Integer.toString(count));
				}
				else {
					Collection<Item> itemCol = getModel().getItemManager().
							getItems((Container)productContainerData.getTag(), 
									(Product)productArray[i].getTag());
					
					productArray[i].setCount(Integer.toString(itemCol.size()));
				}
			}
			catch(NullPointerException e){ }
		}
	}
	
	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void productSelectionChanged() {
		
		ProductData productData = getView().getSelectedProduct();
		
		Container productContainer = (Container)getView().getSelectedProductContainer().getTag();
		
		if(productData != null && productData.getTag() != null && productContainer != null){
			ItemData selectedItemData = getView().getSelectedItem();
			Collection<Item> col = getModel().getItemManager().getItems(productContainer, 
					(Product)productData.getTag());
			ItemData[] itemArray = DataConverter.toItemDataArray(col);
			getView().setItems(itemArray);
			if (selectedItemData != null){
				if (selectedItemData.hasTag()){
					getView().selectItem(
							DataConverter.getItemData((Item) 
									selectedItemData.getTag(), itemArray));
				}
			}
			
		}
		
		else if (productContainer == null && productData != null && productData.getTag() != null){
			Collection<Item> col = getModel().getItemManager().getItems();
			Collection<Item> itemCollection = new ArrayList<Item>();
			Iterator<Item> it = col.iterator();
			
			while(it.hasNext()){
				Item item = (Item)it.next();
				
				if(item.getProduct().equals(productData.getTag())){
					itemCollection.add(item);
					
				}
			}
			ItemData[] itemArray = DataConverter.toItemDataArray(itemCollection);
			getView().setItems(itemArray);
		}
		
		if ( selectedItemData != null ){
			getView().selectItem(selectedItemData);
		}
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void itemSelectionChanged() {
		return;
	}

	/**
	 * Returns true if and only if the "Delete Product" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProduct() {
		
		ProductData productData = getView().getSelectedProduct();
		if (productData == null) {
		    return false;
		}
		
		Product product = (Product)productData.getTag();
		
		ProductContainerData selected = getView().getSelectedProductContainer();
		if (selected.getTag() == null) {
		
		    System.out.println("IventoryController.canDeleteProduct():" +
		    		"canDeleteProductFromSystem() branch");
		    
		    return getModel().getProductAndItemEditor().
		            canDeleteProductFromSystem(product);
		    
		}
		else {
		    
		    System.out.println("IventoryController.canDeleteProduct():" +
                    "canRemoveProductFromContainer() branch");
		    
		    Container container = (Container)getView().
		            getSelectedProductContainer().getTag();
		    
		    return getModel().getProductAndItemEditor().
		            canRemoveProductFromContainer(product, container);
		    
		}
		
	}

	/**
	 * This method is called when the user selects the "Delete Product" menu item.
	 */
	@Override
	public void deleteProduct() {
	    
	    /* 
	     * MODIFIED FOR BUG FIX 07/18 23:10 EM
	     * The code was checking whether productContainerData was null rather
	     * than checking whether productContainerData.getTag() was null
	     * in order to determine whether the tree root is selected. The result
	     * was that product deletion wasn't working at all.
	     */
		ProductData productData = getView().getSelectedProduct();
		Product product = (Product)productData.getTag();
		
		ProductContainerData productContainerData =
		        getView().getSelectedProductContainer();
		
		try{
		    
			if (productContainerData.getTag() == null) {			   
				getModel().getProductManager().deleteProductFromSystem(product);
			}
			else {
			    
			    Container container = (Container)productContainerData.getTag();
			    getModel().getProductManager().removeProductFromContainer(
			            product, container);
			    
			}
			    
			productContainerSelectionChanged();
			
		}
		catch (IllegalArgumentException e){
			getView().displayErrorMessage("Can't Delete Product");
		}
		
	}

	/**
	 * Returns true if and only if the "Edit Item" menu item should be enabled.
	 */
	@Override
	public boolean canEditItem() {
		return getView().getSelectedItem() != null;
	}

	/**
	 * This method is called when the user selects the "Edit Item" menu item.
	 */
	@Override
	public void editItem() {
		getView().displayEditItemView();
	}

	/**
	 * Returns true if and only if the "Remove Item" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItem() {
		if(getView().getSelectedItem() != null){
			return true;
		}
		else return false;
	}

	/**
	 * This method is called when the user selects the "Remove Item" menu item.
	 */
	@Override
	public void removeItem() {
		Item item = (Item) getView().getSelectedItem().getTag();
		if(item != null)
			getModel().getItemManager().removeItem(item);
//		productSelectionChanged();
	}

	/**
	 * Returns true if and only if the "Edit Product" menu item should be enabled.
	 */
	@Override
	public boolean canEditProduct() {
		if(getView().getSelectedProduct() != null){
			return true;
		}
		return false;
	}

	/**
	 * This method is called when the user selects the "Add Product Group" menu item.
	 */
	@Override
	public void addProductGroup() {
		getView().displayAddProductGroupView();
	}
	
	/**
	 * This method is called when the user selects the "Add Items" menu item.
	 */
	@Override
	public void addItems() {
		getView().displayAddItemBatchView();
	}
	
	/**
	 * This method is called when the user selects the "Transfer Items" menu item.
	 */
	@Override
	public void transferItems() {
		getView().displayTransferItemBatchView();
	}
	
	/**
	 * This method is called when the user selects the "Remove Items" menu item.
	 */
	@Override
	public void removeItems() {
		getView().displayRemoveItemBatchView();
	}

	/**
	 * This method is called when the user selects the "Add Storage Unit" menu item.
	 */
	@Override
	public void addStorageUnit() {
		getView().displayAddStorageUnitView();
	}

	/**
	 * This method is called when the user selects the "Edit Product Group" menu item.
	 */
	@Override
	public void editProductGroup() {
		getView().displayEditProductGroupView();
	}

	/**
	 * This method is called when the user selects the "Edit Storage Unit" menu item.
	 */
	@Override
	public void editStorageUnit() {
		getView().displayEditStorageUnitView();
	}

	/**
	 * This method is called when the user selects the "Edit Product" menu item.
	 */
	@Override
	public void editProduct() {
		getView().displayEditProductView();
	}
	
	/**
	 * This method is called when the user drags a product into a
	 * product container.
	 * 
	 * @param productData Product dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void addProductToContainer(ProductData productData, 
										ProductContainerData containerData) {
	    
	    /* 
	     * MODIFIED FOR BUG FIX 07/19 11:36 EM
	     * Added functionality for the case of moving a Product when the
	     * tree root is selected.
	     */	    
		Product product = (Product)productData.getTag();
		
		ProductContainerData selectedProductContainerData =
                getView().getSelectedProductContainer();
		
		Container targetContainer = (Container)containerData.getTag();
		
		ProductAndItemEditor paie = getModel().getProductAndItemEditor();
		
		if (selectedProductContainerData.getTag() == null) {

		    // This branch is when tree root is selected.
		    System.out.println("InventoryController.addProductToContainer():" +
		    		" branch where tree root is selected");
		    
		    paie.moveProductWhenTreeRootIsSelected(
		            product, targetContainer);
		    
		}
		else {
		
		    // This branch is when a particular container is selected.
		    System.out.println("InventoryController.addProductToContainer():" +
                    " branch where a particular container is selected");
		    
		    Container sourceContainer = 
		            (Container)getView().getSelectedProductContainer().getTag();

		    paie.moveProduct(
		            product, sourceContainer, targetContainer);

		}
		
		productContainerSelectionChanged();
	}

	/**
	 * This method is called when the user drags an item into
	 * a product container.
	 * 
	 * @param itemData Item dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void moveItemToContainer(ItemData itemData,
									ProductContainerData containerData) {
		Container targetContainer = (Container) containerData.getTag();
		Item itemToMove = (Item) itemData.getTag();
		if ( (targetContainer != null) && itemToMove != null ){
			getModel().getProductAndItemEditor().moveItem(itemToMove, targetContainer);
		}
	}
	
	@Override
	public void allStorageUnitsSelected() {
		setContextPanel( "All", EMPTY, EMPTY );
	}

	@Override
	public void storageUnitSelected() {
		Container container = (Container) getView().getSelectedProductContainer().getTag();	
		setContextPanel( container.getName(),EMPTY,EMPTY );
		}

	@Override
	public void productGroupSelected() {
		Container container = (Container) getView().getSelectedProductContainer().getTag();
		Container unit = getModel().getContainerManager().getAncestorStorageUnit(container);
		Quantity quantity = ( (ProductGroup) container).getThreeMonthSupply(); 
		if( quantity.getNumber() == 0 ) {
			setContextPanel( unit.getName(), container.getName(), EMPTY );
		}
		else {
			String supply;
			if( Math.round( quantity.getNumber() ) == quantity.getNumber() ) {
				supply = String.valueOf( Math.round( quantity.getNumber() ) );
			}
			else {
				supply = String.valueOf( quantity.getNumber() );
			}
			supply += " " + quantity.getUnit().name();
			setContextPanel( unit.getName(), container.getName(), supply );
		}
	}
	
	private void setContextPanel(String unit, String group, String supply) {
		getView().setContextUnit( unit );
		getView().setContextGroup( group );
		getView().setContextSupply( supply );
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		ChangeType changeType = ((ChangeObject) arg1).getChangeType();
		if( changeType.equals(ChangeType.CONTAINER) ) {
			selectedContainerData = (ProductContainerData) ((ChangeObject)arg1).getSelectedData();
			loadValues();
		}
		else if( changeType.equals(ChangeType.ITEM) ) {
			selectedItemData = (ItemData) ((ChangeObject)arg1).getSelectedData();
			productContainerSelectionChanged();
			productSelectionChanged();
			itemSelectionChanged();
		}
		else if( changeType.equals(ChangeType.PRODUCT) ) {
			selectedProductData = (ProductData) ((ChangeObject)arg1).getSelectedData();
			productContainerSelectionChanged();
		}
	}

//	public void debugInit(){
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		Date ed1;
//		try {
//			ed1 = dateFormat.parse("2013/11/9");
//		}
//		catch (ParseException e){
//			ed1 = null;
//		}
//
//		
//		Container su1 = new StorageUnit("SU1");
//		
//		getModel().getContainerEditor().addContainer(null, su1);
//
//		Product p1 = new Product("1", "Descripshun", SizeUnits.Count, 1, 1, 1);
//		if (!getModel().getProductManager().getProducts().contains(p1)){
//			getModel().getProductManager().addNewProduct(p1, su1);
//		}		
//		Collection<Item> toAdd = new TreeSet<Item>();
//		Barcode bc1 = new Barcode("1");
//		Item i1 = new Item(su1, p1, ed1, bc1);
//		toAdd.add(i1);
//		
//		Barcode bc2 = new Barcode("2");
//		Item i2 = new Item(su1, p1, ed1, bc2);
//		toAdd.add(i2);
//		
//		for (Item i : toAdd) {
//			if ( getModel().getItemManager().canAddItem(i, i.getContainer()) ){
//				getModel().getProductAndItemEditor().addItemToStorageUnit(i,(StorageUnit) su1);
//			}
//		}
//		
//
//	}
}

