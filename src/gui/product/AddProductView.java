package gui.product;

import java.util.Date;

import model.Container;
import gui.common.*;
import gui.main.GUI;


@SuppressWarnings("serial")
public class AddProductView extends ProductView implements IAddProductView {

	public AddProductView(GUI parent, DialogBox dialog, String barcode, 
			Container productContainer, Date date){
		super(parent, dialog);
		
		construct();		

		_controller = new AddProductController(this, barcode, productContainer, date);
	}

	@Override
	public IAddProductController getController() {
		return (IAddProductController)super.getController();
	}

	@Override
	protected void valuesChanged() {
		getController().valuesChanged();
	}

	@Override
	protected void cancel() {
		return;
	}

	@Override
	protected void ok() {
		getController().addProduct();
	}

}


