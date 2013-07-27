package reports;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import gui.common.SizeUnits;

import org.junit.*;

import reports.ProductStatVisitor;

public class ProductStatVisitorTest {

	private ProductStatVisitor productVisitor;
	
	@Test public void constructorTest(){
		int months = 3;
		productVisitor = new ProductStatVisitor(months);
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -3);
		
		//assertTrue(productVisitor.getReportDate().equals(c.getTime()));
		
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		
		//assertTrue(productVisitor.getPreviousDay(new Date()).equals(c.getTime()));	
		
		System.out.println(productVisitor.visitAll());
	}

}
