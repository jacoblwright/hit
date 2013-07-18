package gui.inventory;

import gui.common.*;
import gui.item.*;
import gui.product.*;

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
	private ProductContainerData selectedContainerData;
	private ProductData selectedProductData;
	private ItemData selectedItemData;

	/**
	 * Constructor.
	 *  
	 * @param view Reference to the inventory view
	 */
	public InventoryController(IInventoryView view) {
		super(view);
		getModel().getContainerManager().addObserver( this );
		

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
		}
		loadContextPanel( selectedContainerData );
		
	}

	private void loadContextPanel( ProductContainerData selectedContainer ) {
		if( selectedContainer != null ) {
			if( selectedContainer.getTag() == null ) {
				allStorageUnitsSelected();
			}
			else if( selectedContainer.getTag() instanceof StorageUnit ) {
				storageUnitSelected();
			}
			else if( selectedContainer.getTag() instanceof ProductGroup ) {
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
		ProductContainerData pcd = getView().getSelectedProductContainer();
		getModel().getContainerEditor().deleteContainer( (Container) pcd.getTag() );
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
		if(productContainerData !=  null){
			Collection col = getModel().getProductManager().getProducts((Container)productContainerData.getTag());
			if (col == null) return;
			ProductData[] productArray = DataConverter.toProductDataArray(col);
			for(int i = 0; i < productArray.length; i++){
				try{
					Collection itemCol = getModel().getItemManager().getItems((Container)productContainerData.getTag(), (Product)productArray[i].getTag());
					productArray[i].setCount(Integer.toString(itemCol.size()));
				}
				catch(NullPointerException e){
					
				}
			}
			getView().setProducts(productArray);
		}
		else getView().setProducts(null);
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void productSelectionChanged() {
		
		ProductData productData = getView().getSelectedProduct();
		if(productData != null){
			Collection col = getModel().getItemManager().getItems((Container)getView().getSelectedProductContainer().getTag(), (Product)productData.getTag());
			ItemData[] itemArray = DataConverter.toItemDataArray(col);
			getView().setItems(itemArray);
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
		if(getView().getSelectedProduct() != null){
			ProductData productData = getView().getSelectedProduct();
			Product product = (Product)productData.getTag();

			if(getModel().getProductAndItemEditor().canDeleteProductFromSystem(product))
				return true;
		}
		return false;
	}

	/**
	 * This method is called when the user selects the "Delete Product" menu item.
	 */
	@Override
	public void deleteProduct() {
		if(canDeleteProduct()){
			ProductData productData = getView().getSelectedProduct();
			Product product = (Product)productData.getTag();
			ProductContainerData productContainerData = getView().getSelectedProductContainer();
			Container container = (Container)productContainerData.getTag();
			try{
				getModel().getProductManager().removeProductFromContainer(product, container);
			}
			catch (IllegalArgumentException e){
				getView().displayErrorMessage("Can't Delete Product");
			}
		}
		else{
			getView().displayErrorMessage("Can't Delete Product");
		}
		
	}

	/**
	 * Returns true if and only if the "Edit Item" menu item should be enabled.
	 */
	@Override
	public boolean canEditItem() {
		return true;
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
		getModel().getItemManager().removeItem(item);
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
		
		Product product = (Product)productData.getTag();
		Container container = (Container)containerData.getTag();
		
		getModel().getProductManager().addProductToContainer(product, container);
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
			productSelectionChanged();
		}
		else if( changeType.equals(ChangeType.PRODUCT) ) {
			selectedProductData = (ProductData) ((ChangeObject)arg1).getSelectedData();
			productContainerSelectionChanged();
		}
	}

	public void debugInit(){

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date ed1;
		try {
			ed1 = dateFormat.parse("2013/11/9");
		}
		catch (ParseException e){
			ed1 = null;
		}

		
		Container su1 = new StorageUnit("SU1");
		
		getModel().getContainerEditor().addContainer(null, su1);

		Product p1 = new Product("1", "Descripshun", SizeUnits.Count, 1, 1, 1);
		if (!getModel().getProductManager().getProducts().contains(p1)){
			getModel().getProductManager().addNewProduct(p1, su1);
		}		
		Collection<Item> toAdd = new TreeSet<Item>();
		Barcode bc1 = new Barcode("1");
		Item i1 = new Item(su1, p1, ed1, bc1);
		toAdd.add(i1);
		
		Barcode bc2 = new Barcode("2");
		Item i2 = new Item(su1, p1, ed1, bc2);
		toAdd.add(i2);
		
		for (Item i : toAdd) {
			if ( getModel().getItemManager().canAddItem(i, i.getContainer()) ){
				getModel().getProductAndItemEditor().addItemToStorageUnit(i,(StorageUnit) su1);
			}
		}
		

	}
}

