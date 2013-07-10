package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BarcodeTest {

	@Test
	public void constructor1test() {
		Barcode b = new Barcode();
		String barcode = b.getBarcode();
		assertNotNull( barcode );
	}

}
