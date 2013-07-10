package model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Container;
import model.Item;
import model.Product;

import org.junit.Test;

public class ItemTest {

	@Test
	public void test() throws java.text.ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		Container c1 = new ProductGroup();
		Product p1 = new Product("1", "Descripshun", Unit.count, 1, 1, 1);
		Date ed1 = dateFormat.parse("2013/12/9");
		Barcode bc1 = new Barcode("blahblah");
		Item i1 = new Item(c1, p1, ed1, bc1);
		Item i2 = new Item(c1, p1, ed1, bc1);
		
		assertEquals(i1, i2);
		assertFalse(i1.equals(p1));
		
		// test entry date capture
		System.out.printf("%s == %s", i1.getEntryDate(), dateFormat.format(cal.getTime()));
		assertEquals(dateFormat.format(i1.getEntryDate()), dateFormat.format(cal.getTime()));

		// test expiration date set
		assertEquals(i1.getExpirationDate(), ed1);
		
		// test container get
		assertEquals(i1.getContainer().toString(), c1.toString()); // fails...
		
		// test product set
		assertEquals(i1.getProduct(), p1);
		
		// test barcode set TODO: will use barcode verifier once written.
		assertFalse(i1.getTag() == new Barcode());
		
		// Testing Setters:
		Product p2 = new Product("2", "Cookies", Unit.count, 1, 1, 1);
		i1.setProduct(p2);
		assertEquals(i1.getProduct(), p2);
		
		i1.setTag(bc1);
		assertEquals(i1.getTag(), bc1);
		
		Date et2 = dateFormat.parse("2011/8/6");
		i1.setEntryDate(et2);
		assertEquals(i1.getEntryDate(), et2);
		
		Date ex1 = timeFormat.parse("2011/11/6 01:01:01");
		i1.setExitTime(ex1);
		assertEquals(i1.getExitTime(), ex1);
		
		Date exp1 = dateFormat.parse("2011/9/12");
		i1.setExpirationDate(exp1);
		assertEquals(i1.getExpirationDate(), exp1);
		
		Container c2 = new ProductGroup();
		i1.setContainer(c2);
		assertEquals(i1.getContainer().toString(), c2.toString());
	}

}
