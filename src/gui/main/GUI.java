
package gui.main;

import javax.swing.*;

import data.DBDAOFactory;
import data.SerComprehensiveDAO;
import data.SerDAOFactory;

import java.awt.event.*;
import java.io.IOException;

import gui.common.*;
import gui.inventory.*;
import gui.reports.expired.*;
import gui.reports.supply.*;
import gui.reports.notices.*;
import gui.reports.productstats.*;
import gui.reports.removed.*;
import model.*;

@SuppressWarnings("serial")
public final class GUI extends JFrame implements IMainView {
	
	private IMainController _controller;
	private JMenuBar _menuBar;
	private SessionMenu _sessionMenu;
	private ReportsMenu _reportsMenu;
	private InventoryView _inventoryView;

	private GUI(String[] args) {
		super("Home Inventory Tracker");		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				_sessionMenu.exit();
			}			
		});	
		
		createMenus();
		createInventoryView();

		_controller = new MainController(this);

		_sessionMenu.setController(_controller);
		_reportsMenu.setController(_controller);

		display();
	}

	private void createMenus() {
		_sessionMenu = new SessionMenu(this);
		_sessionMenu.setFont(View.createFont(_sessionMenu.getFont(), View.MenuFontSize));
		
		_reportsMenu = new ReportsMenu(this);
		_reportsMenu.setFont(View.createFont(_reportsMenu.getFont(), View.MenuFontSize));

		_menuBar = new JMenuBar();
		_menuBar.setFont(View.createFont(_menuBar.getFont(), View.MenuFontSize));
		
		_menuBar.add(_sessionMenu);
		_menuBar.add(_reportsMenu);

		setJMenuBar(_menuBar);
	}

	private void createInventoryView() {
		_inventoryView = new InventoryView(this);
		setContentPane(_inventoryView);
	}

	private void display() {
        pack();
        setVisible(true);
	}
	
	public IMainController getController() {
		return _controller;
	}

    //
    // IView overrides
    //

	@Override
	public void displayInformationMessage(String message) {
		displayMessage(message, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void displayWarningMessage(String message) {
		displayMessage(message, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void displayErrorMessage(String message) {
		displayMessage(message, JOptionPane.ERROR_MESSAGE);
	}

	private void displayMessage(final String message, final int messageType) {

		// NOTE: Calling JOptionPane.showMessageDialog from an InputVerifier 
		// does not work (Swing's keyboard focus handling goes bonkers), so 
		// here we call JOptionPane.showMessageDialog using SwingUtilities.invokeLater 
		// to circumvent this problem in the case displayErrorMessage is
		// invoked from an InputVerifier.
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(GUI.this, message, "Inventory Tracker", 
						messageType);
			}
		});
	}

    //
    // IMainView overrides
    //

	@Override
	public void displayExpiredReportView() {
		DialogBox dialog = new DialogBox(this, "Expired Items Report");
		dialog.display(new ExpiredReportView(this, dialog), false);
	}

	@Override
	public void displaySupplyReportView() {
		DialogBox dialog = new DialogBox(this, "N-Month Supply Report");
		dialog.display(new SupplyReportView(this, dialog), false);
	}

	@Override
	public void displayProductReportView() {
		DialogBox dialog = new DialogBox(this, "Product Statistics Report");
		dialog.display(new ProductStatsReportView(this, dialog), false);
	}

	@Override
	public void displayNoticesReportView() {
		DialogBox dialog = new DialogBox(this, "Notices Report");
		dialog.display(new NoticesReportView(this, dialog), false);
	}

	@Override
	public void displayRemovedReportView() {
		DialogBox dialog = new DialogBox(this, "Removed Items Report");
		dialog.display(new RemovedReportView(this, dialog), false);
		
	}
	
	//
	// Main
	//
	
    public static void main(final String[] args) {

        // --------------------------------
        if (args.length > 0 && args[0].equals("-sql")) {
            System.out.println("Setting to database mode.");
            Model.getInstance().setDAOFactory(new DBDAOFactory());
        }
        else {
            System.out.println("Setting to serialization mode (default).");
            Model.getInstance().setDAOFactory(new SerDAOFactory());            
        }
        
        try {
            Model.getInstance().initialize();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // --------------------------------
        
     	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

 		SwingUtilities.invokeLater(
			new Runnable() {
            	public void run() {
                	new GUI(args);
            	}
        	}
		);
    }

}

