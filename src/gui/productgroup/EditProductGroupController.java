package gui.productgroup;

import model.Container;
import model.ProductGroup;
import model.Quantity;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the edit product group view.
 */
public class EditProductGroupController extends Controller 
										implements IEditProductGroupController {
	
	private ProductContainerData target;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit product group view
	 * @param target Product group being edited
	 */
	public EditProductGroupController(IView view, ProductContainerData target) {
		super(view);
		this.target = target;
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
	protected IEditProductGroupView getView() {
		return (IEditProductGroupView)super.getView();
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
	// IEditProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit product group view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		Container container = setContainer();
		boolean isEnabled = getModel().getContainerManager().canEditContainer( container );
		
		getView().enableOK( isEnabled );
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit product group view.
	 */
	@Override
	public void editProductGroup() {
		Container container = setContainer();
		getModel().getContainerEditor().editContainer( (Container) target.getTag(), container );
	}
	
	private Container setContainer() {
		String name = getView().getProductGroupName();
		String value = getView().getSupplyValue();
		Enum<SizeUnits> unit = getView().getSupplyUnit();
		float number = getNumber( value );
		Quantity quantity = new Quantity( number, unit );
		
		Container result = new ProductGroup( name, quantity );
		result.setContainer( (Container) target.getTag() );
		return result;
	}
	
	private float getNumber(String value) {
		float number;
		try
		{	
			if( emptyString( value ) ) {
				number = 0;//add something for 0 values to be ok
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

