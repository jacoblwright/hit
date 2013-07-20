package gui.item;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gui.common.*;
import model.Model;
import model.Item;
import model.Barcode;

/**
 * Controller class for the edit item view.
 */
public class EditItemController extends Controller 
										implements IEditItemController {
	
	ItemData target;
	DateFormat dateFormat;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit item view
	 * @param target Item that is being edited
	 */
	public EditItemController(IView view, ItemData target) {
		super(view);
		dateFormat = new SimpleDateFormat("MM/dd/yyy");
		if (target == null) {
			getView().displayErrorMessage("You must select an item!");
			((ItemView)getView()).cancel();
			enableComponents();
			getView().enableOK(false);
			getView().enableEntryDate(false);
			
		}
		else {
			// Attach item as ItemData tag
			Barcode tag = new Barcode(target.getBarcode());
			target.setTag(getModel().getItemManager().getItemByTag(tag));
			this.target = target;
		}
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
	protected IEditItemView getView() {
		return (IEditItemView)super.getView();
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
		// enables only the entry date field
		getView().enableBarcode(false);
		getView().enableDescription(false);
		
		if (target == null) {
			getView().enableOK(false);
			getView().enableEntryDate(false);
		}
		else {
			// Create item to check validity
			Item tagalong = (Item) target.getTag();
			if (tagalong == null) {
				getView().enableOK(false);
			}
			else {
				getView().enableOK(getModel().
							getItemManager().canEditItem(tagalong, createItem(tagalong)));
			}
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
		// enters the product description, item barcode, and entry date
		
		if (target == null) {
			getView().setDescription("Unknown");
			getView().setBarcode("Unknown");
		}
		else {
		
			getView().setBarcode(target.getBarcode());
			
			Item tagalong = (Item) target.getTag();
			if (tagalong != null){
				getView().setDescription(tagalong.getProduct().getDescription());
			}
			
			getView().setEntryDate(target.getEntryDate());
		}
	}

	//
	// IEditItemController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit item view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		if ( getView().getEntryDate() == null ) {
			getView().enableOK(false);
		}
		else {
			
			if ( getView().getEntryDate().getTime() > Calendar.getInstance().getTime().getTime()){
				getView().enableOK(false);
			}
			else {
				Item before = (Item) target.getTag();
				Item after = createItem(before);
				getView().enableOK(getModel().getItemManager().canEditItem(before, after));
			}
		}
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit item view.
	 */
	@Override
	public void editItem() {
		
		// Create new item 
		Item tagalong = (Item) target.getTag();
		if (tagalong == null) {
			getView().displayErrorMessage("I'm sorry but you cannot edit this item.");
		}
		else {
//			getView().displayInformationMessage("Editing item");
			Item newItem = createItem(tagalong);
			Date entry = getView().getEntryDate();
			getModel().getProductAndItemEditor().editItem(tagalong, newItem);
		}
	}
	
	/** Creates new item copy with the view's get entry date
	 * 
	 * 
	 * @param tagalong - item used to clone
	 * @return copy of tagalong with view's entry date
	 */
	private Item createItem(Item tagalong){
		Date entryDate = getView().getEntryDate(); 
		return new Item(	
				tagalong.getContainer(), 
				tagalong.getProduct(),
				entryDate,
				tagalong.getTag()
				
			);
	}
}

