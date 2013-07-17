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
		debugInit();

		construct();
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
		Set<StorageUnit> storageUnits = new TreeSet<StorageUnit>();
		StorageUnit su1 = new StorageUnit( "The CS Dungeon" );
		ProductGroup pg1 = new ProductGroup( "Toothpaste" );
		su1.addProductGroup( pg1 );
		ProductGroup cpg1 = new ProductGroup( "Kids" );
		ProductGroup cpg2 = new ProductGroup( "Parents" );
		pg1.addProductGroup(cpg1);
		pg1.addProductGroup(cpg2);
		su1.addProductGroup(pg1);
		storageUnits.add(su1);
		
		getModel().getContainerManager().setStorageUnits( storageUnits );
		ProductContainerData root = new ProductContainerData();
		
		ProductContainerData basementCloset = new ProductContainerData("The CS Dungeon");
		
		ProductContainerData toothpaste = new ProductContainerData("Toothpaste");
		toothpaste.addChild(new ProductContainerData("Kids"));
		toothpaste.addChild(new ProductContainerData("Parents"));
		basementCloset.addChild(toothpaste);
		
		root.addChild(basementCloset);
		
		ProductContainerData foodStorage = new ProductContainerData("Food Storage Room");
		
		ProductContainerData soup = new ProductContainerData("Soup");
		soup.addChild(new ProductContainerData("Chicken Noodle"));
		soup.addChild(new ProductContainerData("Split Pea"));
		soup.addChild(new ProductContainerData("Tomato"));
		foodStorage.addChild(soup);
		
		root.addChild(foodStorage);
		
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
		return true;
	}
	
	/**
	 * This method is called when the user selects the "Delete Storage Unit" menu item.
	 */
	@Override
	public void deleteStorageUnit() {
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
		return true;
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
		Set products = null;
		List<ProductData> productDataList = new ArrayList<ProductData>();		
		ProductContainerData selectedContainer = getView().getSelectedProductContainer();
		/* Grab products from specific container */
		if (selectedContainer != null) {
			Map productMap = getModel().getProductManager().getProductsByContainer();
			products = (HashSet)productMap.get((Container)selectedContainer.getTag());
		}

		if(products != null){
			Iterator it = products.iterator();
			while (it.hasNext()){
				Product product = (Product)it.next();
				ProductData productData = new ProductData();			
				productData.setBarcode(product.getUPC().getBarcode());
				
				//productData.setCount(Integer.toString(getModel().getItemManager().getItems((Container)selectedContainer.getTag(), product).size()));
				productData.setDescription(product.getDescription());
				productData.setShelfLife(Integer.toString(product.getShelfLife()) + " months");
				productData.setSize(product.getSize().getNumber() + " " + product.getSize().getUnit());
				productData.setSupply(Integer.toString(product.getThreeMonthSupply()) + " count");
				
				productDataList.add(productData);
				}
		}
		
		getView().setProducts(productDataList.toArray(new ProductData[0]));
		
		getView().setItems(new ItemData[0]);
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void productSelectionChanged() {
		
		Product product = (Product)getView().getSelectedProduct().getTag();
		Container container = (Container)getView().getSelectedProductContainer().getTag();

		Collection collection = getModel().getItemManager().getItems(container, product);
		List<ItemData> itemDataList = new ArrayList<ItemData>();
		Iterator it = collection.iterator();
		if (product != null) {
			while(it.hasNext()) {
				Item item = (Item)it.next();
				ItemData itemData = new ItemData();
				itemData.setBarcode(item.getTag().getBarcode());
				itemData.setEntryDate(item.getEntryDate());
				itemData.setExpirationDate(item.getExpirationDate());
				/* Still need to instantiate these */
				//itemData.setProductGroup();
				//itemData.setStorageUnit("Some Unit");
				
				itemDataList.add(itemData);
			}
		}
		getView().setItems(itemDataList.toArray(new ItemData[0]));
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
		return true;
	}

	/**
	 * This method is called when the user selects the "Remove Item" menu item.
	 */
	@Override
	public void removeItem() {
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
		Container c1 = new StorageUnit();
		c1.setName("Storage Unit 1");
		
		Container c2 = new ProductGroup();
		c2.setName("Product Group 1");
		c2.setContainer(c1);
		
		Product p1 = new Product("1", "Descripshun", SizeUnits.Count, 1, 1, 1);
		if (!getModel().getProductManager().getProducts().contains(p1)){
			getModel().getProductManager().addNewProduct(p1, c2);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date ed1;
		try {
			ed1 = dateFormat.parse("2013/11/9");
		}
		catch (ParseException e){
			ed1 = null;
		}
		Collection<Item> toAdd = new TreeSet<Item>();
		Barcode bc1 = new Barcode("1");
		Item i1 = new Item(c2, p1, ed1, bc1);
		toAdd.add(i1);
		
		Barcode bc2 = new Barcode("2");
		Item i2 = new Item(c2, p1, ed1, bc2);
		toAdd.add(i2);
		
		for (Item i : toAdd) {
			if (!getModel().getItemManager().getItems(i.getContainer()).contains(i)){
				getModel().getItemManager().addItem(i);
			}
		}
	}
}

