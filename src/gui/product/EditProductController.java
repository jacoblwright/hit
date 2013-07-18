package gui.product;

import model.Product;
import gui.common.*;

/**
 * Controller class for the edit product view.
 */
public class EditProductController extends Controller 
										implements IEditProductController {
	
	ProductData product;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the edit product view
	 * @param target Product being edited
	 */
	public EditProductController(IView view, ProductData target) {
		super(view);

		product = target;
		construct();
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
	protected IEditProductView getView() {
		return (IEditProductView)super.getView();
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
		
		try{
			Integer.parseInt(getView().getShelfLife());
			Integer.parseInt(getView().getSupply());
			Float.parseFloat(getView().getSizeValue());
			if(!getView().getDescription().isEmpty())
				getView().enableOK(true);
			else getView().enableOK(false);
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
		getView().setBarcode(product.getBarcode());
		getView().setDescription(product.getDescription());
		/* These four need to be parsed */
		getView().setSizeValue(product.getCount());
		//SizeUnits su = SizeUnits.valueOf(product.getSize());
		//getView().setSizeUnit(su);
		//getView().setShelfLife(product.getShelfLife());
		//getView().setSupply(product.getSupply());
		
		enableComponents();
	}

	//
	// IEditProductController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit product view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit product view.
	 */
	@Override
	public void editProduct() {
		Product newProduct = new Product(getView().getBarcode(), getView().getDescription(), 
				getView().getSizeUnit(), Float.parseFloat(getView().getSizeValue()),Integer.
				parseInt(getView().getShelfLife()), 
				Integer.parseInt(getView().getSupply()) );
		
		System.out.println(newProduct);
		
		if(!getModel().getProductManager().isProductValid(newProduct)){
			getView().displayErrorMessage("Can't edit to invalid product.");
		}
		
		else getModel().getProductManager().editProduct((Product)product.getTag(), newProduct);
		
	}

}

