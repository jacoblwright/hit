package gui.product;

import java.awt.EventQueue;
import java.io.IOException;
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
	String searchMessage;
	
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
		searchMessage = "I'm searching, geez.";
		
		getView().setDescription(searchMessage);
		getView().enableDescription(false);
		
		getView().setBarcode(barcode);
		getView().enableOK(false);
		
		setDefaultValues();
		setDefaultEnable(false);
		getView().enableBarcode(false);
		loadValues();
		getModel().getProductAndItemEditor().setNewlyAddedProduct(null);
		
	}

	private void setDefaultValues() {
		getView().setShelfLife("0");
		getView().setSupply("0");
		getView().setSizeValue("1");
		getView().setSizeUnit(SizeUnits.Count);
	}

	private void setDefaultEnable(boolean b) {
		getView().enableDescription(b);
		getView().enableSizeUnit(b);
		getView().enableShelfLife(b);
		getView().enableSupply(b);
		getView().enableSizeValue(b);
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
		getView().enableSizeUnit(true);
		getView().enableBarcode(false);
		getView().enableShelfLife(true);
		getView().enableSupply(true);

		getView().setBarcode(upc);
		if(getView().getSizeUnit() == SizeUnits.Count){
			getView().setSizeValue("1");
			getView().enableSizeValue(false);
		}
		else getView().enableSizeValue(true);


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
		getView().enableOK(false);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				searchForDescription();
			}
		});
		thread.start();
		getView().setDescription("Auto-Identifying - Please Wait");
		
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
		
		if(!getModel().getProductManager().isProductValid(product)){
			getView().displayErrorMessage("Can't add invalid product.");
			return;
		}
		
		try{
			getModel().getTransaction().startTransaction();
			getModel().getProductAndItemEditor().setNewlyAddedProduct(product);
			getModel().getTransaction().endTransaction();
//			getModel().getProductManager().addNewProduct(product, container);
		}
		catch (IllegalArgumentException e){
			getView().displayErrorMessage("Can't add valid product");
		}
		catch (IOException e){
			getView().displayErrorMessage("Input Error");
		}
	}
	
	public void searchForDescription(){
		
		if(getView().getBarcode() == null || 
				getView().getBarcode().equals("") ||
				getView().getBarcode().equals(searchMessage))
		{
			getView().displayErrorMessage("Barcode should not be empty");
		}
		else {
			Barcode tmp = new Barcode();

			if ( tmp.isValidBarcode(getView().getBarcode()) ){

				String descr = getModel().getUpcDescriptionFetcher().fetchUPCDescription(
						getView().getBarcode());
				
				if (descr != null){
					getView().setDescription(descr);
					getView().enableOK(true);
				}
				else {
					getView().setDescription("");
					getView().enableOK(false);
				}
				getView().enableDescription(true);
				enableComponents();
			}
		}
	}

}

