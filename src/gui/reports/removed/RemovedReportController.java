package gui.reports.removed;

import java.text.SimpleDateFormat;
import java.util.Date;

import reports.ReportDirector;
import reports.ReportTime;

import gui.common.*;

/**
 * Controller class for the removed items report view.
 */
public class RemovedReportController extends Controller implements
		IRemovedReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the removed items report view
	 */
	public RemovedReportController(IView view) {
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
	protected IRemovedReportView getView() {
		return (IRemovedReportView) super.getView();
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
		
		if(getView().getSinceDate()){
			getView().enableSinceDateValue(true);
		}
		else getView().enableSinceDateValue(false);
			
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String result = sdf.format(getView().getSinceDateValue());
			if(getView().getSinceDateValue().after(new Date())){
				getView().enableOK(false);
			}
			else getView().enableOK(true);
		}
		catch(Exception e){
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
		getView().enableOK(true);
		getView().setSinceDateValue(new Date());
		if(ReportTime.getLastReport() == null){
			getView().enableSinceDateValue(true);
		}
		else getView().setSinceLastValue(ReportTime.getLastReport());
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * removed items report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the removed items report view.
	 */
	@Override
	public void display() {
		ReportTime.setLastReport(new Date());
		/* Evan will be altering the method parameters to match the following method calls */
		if(getView().getSinceDate())
			ReportDirector.generateRemovedItemsReport(getView().getSinceDateValue(), getView().getFormat());
		else
			ReportDirector.generateRemovedItemsReport(new Date(), getView().getFormat());
	}

}

