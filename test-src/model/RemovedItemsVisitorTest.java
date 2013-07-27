package model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import reports.RemovedItemsVisitor;

public class RemovedItemsVisitorTest {

	@Test
	public void test() {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -3);
		
		Model model = Model.getInstance();
		
		RemovedItemsVisitor riv = new RemovedItemsVisitor(c.getTime());
	}

}
