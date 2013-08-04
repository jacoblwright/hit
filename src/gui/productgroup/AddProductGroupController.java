package gui.productgroup;

import java.io.IOException;

import model.Container;
import model.ProductGroup;
import model.Quantity;
import model.StorageUnit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the add product group view.
 */
public class AddProductGroupController extends Controller implements
		IAddProductGroupController {
	
	ProductContainerData parent;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add product group view
	 * @param container Product container to which the new product group is being added
	 */
	public AddProductGroupController(IView view, ProductContainerData container) {
		super(view);
		this.parent = container;
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
	protected IAddProductGroupView getView() {
		return (IAddProductGroupView)super.getView();
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
		getView().setSupplyValue( "0" );
		getView().enableOK( false );
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
		
	}

	//
	// IAddProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add product group view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		Container container = setContainer();
 		boolean isEnabled = getModel().getContainerManager().canAddContainer( container );
		
		getView().enableOK( isEnabled );
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product group view.
	 * @throws IOException 
	 */
	@Override
	public void addProductGroup() {
		Container container = setContainer();
		try {
			getModel().getTransaction().startTransaction();
			getModel().getContainerEditor().addContainer((Container) parent.getTag(), container);
			getModel().getTransaction().endTransaction();
		} catch(IOException e) {
			//talk with team to see what to do here.
		}
		
	}
	
	private Container setContainer() {
		String name = getView().getProductGroupName();
		String value = getView().getSupplyValue();
		Enum<SizeUnits> unit = getView().getSupplyUnit();
		float number = getNumber( value );
		Quantity quantity = new Quantity( number, unit );
		
		Container result = new ProductGroup( name, quantity );
		result.setContainer( (Container) parent.getTag() );
		return result;
	}
	
	private float getNumber(String value) {
		float number;
		try
		{	
			if( emptyString( value ) ) {
				number = -1;
			}
			else {
				number = Float.parseFloat( value );
			}
		}
		catch(NumberFormatException e)
		{
			number = -1;
		}
		return number;
	}

	private boolean emptyString( String str) {
		return str.trim().length() == 0;
	}

}

