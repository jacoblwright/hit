package gui.product;

import java.util.Date;

import model.*;
import gui.common.*;
import gui.inventory.ProductContainerData;
import plugins.UPCDescriptionFetcher;

/**
 * Controller class for the add item view.
 */
public class AddProductController extends Controller implements
		IAddProductController {
	
	String upc;
	Container container;
	Date productDate;
	UPCDescriptionFetcher fetcher;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add product view
	 * @param barcode Barcode for the product being added
	 */
	public AddProductController(IView view, String barcode, Container cont, Date date) {
		super(view);

		upc = barcode;
		container = cont;
		productDate = date;
		fetcher = new UPCDescriptionFetcher();
		loadValues();
		enableComponents();
		construct();
		getModel().getProductAndItemEditor().setNewlyAddedProduct(null);
	}

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IAddProductView getView() {
		return (IAddProductView)super.getView();
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
		getView().enableBarcode(false);
		getView().enableDescription(true);
		getView().enableShelfLife(true);
		getView().enableSupply(true);
		
		if(getView().getSizeUnit() == SizeUnits.Count){
			getView().setSizeValue("1");
			getView().enableSizeValue(false);
		}
		else getView().enableSizeValue(true);
		
		if(getView().getBarcode() == null || getView().getBarcode().equals(""))
			getView().displayErrorMessage("Barcode should not be empty");
		else {
			Barcode tmp = new Barcode();
			String curBarcode = getView().getBarcode();
			if ( tmp.isValidBarcode(getView().getBarcode()) ){
				if (getView().getDescription().isEmpty()){
					String descr = fetcher.fetchUPCDescription(getView().getBarcode());
					if (descr != null){
						getView().setDescription(descr);
					}
				}
			}
		}
		try{
			if(getView().getDescription().isEmpty() ||
					Integer.parseInt(getView().getSupply()) < 0 || 
					Integer.parseInt(getView().getShelfLife()) < 0 ||
					Float.parseFloat(getView().getSizeValue()) <= 0
					)
				getView().enableOK(false);
			else getView().enableOK(true);
		}
		catch(NumberFormatException e){
			getView().enableOK(false);
		}
		
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
		getView().setBarcode(upc);
		getView().setShelfLife("0");
		getView().setSupply("0");
	}

	//
	// IAddProductController overrides
	//
	
	/**
	 * This method is called when any of the fields in the
	 * add product view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();

	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product view.
	 */
	@Override
	public void addProduct() {
		
		
		Product product = new Product(getView().getBarcode(), getView().getDescription(), 
				getView().getSizeUnit(), Float.parseFloat(getView().
						getSizeValue()),Integer.parseInt(getView().getShelfLife()), 
				Integer.parseInt(getView().getSupply()) );
		
		System.out.println(productDate);
		//product.setCreationDate(productDate);
		
		if(!getModel().getProductManager().isProductValid(product)){
			getView().displayErrorMessage("Can't add invalid product.");
			return;
		}
		
		try{
			getModel().getProductAndItemEditor().setNewlyAddedProduct(product);
//			getModel().getProductManager().addNewProduct(product, container);
		}
		catch (IllegalArgumentException e){
			getView().displayErrorMessage("Can't add valid product");
		}

		
	}

}

