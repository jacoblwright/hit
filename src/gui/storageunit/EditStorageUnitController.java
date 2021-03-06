package gui.storageunit;

import java.io.IOException;

import model.Container;
import model.StorageUnit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the edit storage unit view.
 */
public class EditStorageUnitController extends Controller 
										implements IEditStorageUnitController {
	
	private ProductContainerData target;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit storage unit view
	 * @param target The storage unit being edited
	 */
	public EditStorageUnitController(IView view, ProductContainerData target) {
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
	protected IEditStorageUnitView getView() {
		return (IEditStorageUnitView)super.getView();
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
		getView().setStorageUnitName( target.getName() );
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
	// IEditStorageUnitController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit storage unit view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		String name = getView().getStorageUnitName();
		Container container = new StorageUnit( name );
		boolean isEnabled = getModel().getContainerManager().canEditContainer( container );
		if( name.equals( target.getName() ) ) {
			isEnabled = true;
		}
		
		getView().enableOK( isEnabled );
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit storage unit view.
	 */
	@Override
	public void editStorageUnit() {
		try {
			String name = getView().getStorageUnitName();
			if( !name.equals( target.getName() ) ) {
				Container newContainer = new StorageUnit( name );
				Container oldContainer = (Container) target.getTag();
				getModel().getTransaction().startTransaction();
				getModel().getContainerEditor().editContainer( oldContainer, newContainer );
				getModel().getTransaction().endTransaction();
			}
		} catch(IOException e) {
			// Talk with team about what to do here.
		}
	}

}

