package gui.reports.supply;

import java.io.IOException;

import reports.ReportDirector;
import gui.common.*;

/**
 * Controller class for the N-month supply report view.
 */
	public class SupplyReportController extends Controller implements
		ISupplyReportController {
/*---	STUDENT-INCLUDE-BEGIN

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the N-month supply report view
	 */	
	public SupplyReportController(IView view) {
		super(view);
		
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
	protected ISupplyReportView getView() {
		return (ISupplyReportView)super.getView();
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
		try{
			int months = Integer.parseInt(getView().getMonths());
			if( months > 0 && months <= 100)
				getView().enableOK(true);
			else getView().enableOK(false);
		}
		catch(IllegalArgumentException e){
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
		getView().setMonths("3");
		getView().enableOK(true);
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * N-month supply report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the N-month supply report view.
	 */
	@Override
	public void display() {
		try{
			ReportDirector.generateNMonthSupplyReport(Integer.parseInt(getView().getMonths()), 
														getView().getFormat());
		}
		catch(IOException e){
			getView().displayErrorMessage("Illegal Month Value");
		}
		
	}
	
	private int getNumber(String value) throws NumberFormatException {
		int number;
		if( emptyString( value ) ) {
			throw new IllegalArgumentException();
		}
		else {
			number = Integer.parseInt( value );
		}
		return number;
	}
	
	private boolean emptyString( String str) {
		return str.trim().length() == 0;
	}

}

