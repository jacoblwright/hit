package gui.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ScannerTimer {
	private static Timer timer;
	
	public ScannerTimer(){
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				// do nothing
			}
		};
		timer = new Timer(1000, listener);
	}
	/**
	 * Called when barcode is changed
	 */
	public static boolean isEntered() {
		
		if ( timer.isRunning() ){
			return false;
		}
		else {
			timer.start();
			return true;
		}
		
	}

}
