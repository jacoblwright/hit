package reports;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gui.common.SizeUnits;

import model.Barcode;
import model.Container;
import model.ContainerManager;
import model.Item;
import model.ItemManager;
import model.Model;
import model.Product;
import model.ProductGroup;
import model.ProductManager;
import model.StorageUnit;

import org.junit.*;

import data.DAOFactory;
import data.SerDAOFactory;

import reports.ProductStatRecord;
import reports.ProductStatVisitor;
import reports.Record;

public class ProductStatVisitorTestAndy {

	private ProductStatVisitor productVisitor;
	
	Model model;
	
	ProductManager pm;
	ItemManager im;
	ContainerManager cm;
	
	private Date oneYear;
	private Date leapYear;
	private Date oneMonth;
	Format f;
	private Product p1, p2, p3;
	private Item i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, 
					i11, i12, i13, i14, i15, i16, i17, i18, i19, i20;
	private Container su1, su2, su3, pg1, pg2, pg3;
	
	private DecimalFormat df;
	
	private void setUp(){
		model = Model.getInstance();
		Model.getInstance().setDAOFactory(new SerDAOFactory());
		pm = new ProductManager();
		im = new ItemManager();
		cm = new ContainerManager();
		model.setProductManager(pm);
		model.setContainerManager(cm);
		model.setItemManager(im);
		
		df = new DecimalFormat("0.#");
		f = new SimpleDateFormat("dd-MMMM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -1);
		oneYear = cal.getTime();
		
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -4);
		leapYear = cal.getTime();
		
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		oneMonth = cal.getTime();
		
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		Date oneDayAgo = cal.getTime();
		
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		Date oneWeekAgo = cal.getTime();
		
		su1 = new StorageUnit();
		su2 = new StorageUnit();
		pg1 = new ProductGroup();
		pg2 = new ProductGroup();
		pg3 = new ProductGroup();
		
		
		p1 = new Product("barcode1", "desc1", SizeUnits.Count, 1, 0, 0);
		p2 = new Product("barcode2", "desc2", SizeUnits.FluidOunces, (float) 1.25, 1, 1);
		p3 = new Product("barcode3", "desc3", SizeUnits.Pounds, 1, 5, 5);
		
		/* Product #1 */
		
		i1 = new Item(su1, p1, new Date(), new Barcode());
		i2 = new Item(su1, p1, new Date(), new Barcode());
		i3 = new Item(pg1, p1, oneDayAgo, new Barcode());
		i4 = new Item(pg3, p1, oneDayAgo, new Barcode());
		i5 = new Item(su1, p1, oneWeekAgo, new Barcode());
		i6 = new Item(su1, p1, oneMonth, new Barcode());
		i7 = new Item(pg1, p1, oneYear, new Barcode());
		i8 = new Item(pg2, p1, oneYear, new Barcode());
		
		/* Product #2 */
		
		i9 = new Item(pg1, p2, new Date(), new Barcode());
		i10 = new Item(su2, p2, new Date(), new Barcode());
		i11 = new Item(pg1, p2, oneWeekAgo, new Barcode());
		i12 = new Item(pg2, p2, oneWeekAgo, new Barcode());
		i13 = new Item(pg3, p2, oneMonth, new Barcode());
		i14 = new Item(su1, p2, oneMonth, new Barcode());
		
		/* Product #3 */
		
		i15 = new Item(su2, p3, oneDayAgo, new Barcode());
		i16 = new Item(su2, p3, oneDayAgo, new Barcode());
		i17 = new Item(su2, p3, oneMonth, new Barcode());
		i18 = new Item(su2, p3, oneMonth, new Barcode());
		i19 = new Item(su2, p3, leapYear, new Barcode());
		i20 = new Item(su2, p3, leapYear, new Barcode());	
	}
	
	public int getDaysBetweenDates(Date date, Date date2){
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date2);
		calendar2.setTime(date);
		long diff = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
		return (int) (diff / (24 * 60 * 60 * 1000) );
	}
	
	@Test
	public void ItemAddedAndUsedSameDayTest(){
		setUp();
		pm.addNewProduct(p1, su1);
		im.addItem(i1);
		im.addItem(i2);
		im.removeItem(i1);
		im.removeItem(i2);
		p1.setCreationDate(i2.getEntryDate());
		
		int months = 1;
		ProductStatVisitor psv = new ProductStatVisitor(months);
		List<Record> records = psv.visitAll();
		
		ProductStatRecord record = (ProductStatRecord)records.get(0);	
		System.out.println(record);
		assertEquals("barcode1", record.getBarcode());
		assertEquals("desc1", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Count, record.getSize().getUnit());
		assertEquals(0, record.getThreeMonthSupply());
		
		assertEquals(0, record.getCurrentSupply());
		assertEquals("0", df.format(record.getCurrentAverageSupply()));
		
		assertEquals(0, record.getMinimumSupply());
		assertEquals(0, record.getMaximumSupply());
		
		assertEquals(2, record.getUsedSupply());
		assertEquals(2, record.getAddedSupply());
		
		assertEquals(0, record.getShelfLife());
		
		assertEquals("0", df.format(record.getUsedAge()));
		assertEquals("0", df.format(record.getMaximumAge()));
		
		assertEquals("0", df.format(record.getAverageSupply()));
		assertEquals((double)0, record.getMaximumCurrentSupply(), 0.0000001);

	}
	
	@Test
	public void calendarTransitionTests(){
		setUp();
		pm.addNewProduct(p3, su2);
		p3.setCreationDate(leapYear);
		im.addItem(i15);
		im.addItem(i16);
		im.addItem(i17);
		im.addItem(i18);
		im.addItem(i19);
		im.addItem(i20);
		int months = 1;
		ProductStatVisitor psv = new ProductStatVisitor(months);
		List<Record> records = psv.visitAll();

		/* Month Changes */
		ProductStatRecord record = (ProductStatRecord)records.get(0);		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(6, record.getCurrentSupply());
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals("0.0", Double.toString(record.getUsedAge()));
		assertEquals("0.0", Double.toString(record.getMaximumAge()));
		assertEquals(5, record.getShelfLife());
		assertEquals(0, record.getUsedSupply());
		assertEquals(4, record.getAddedSupply());
		assertEquals("4.1", df.format(record.getAverageSupply()));
		assertEquals(4, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
		
		/* Year Changes */
		months = 12;
		psv = new ProductStatVisitor(months);
		records = psv.visitAll();
		record = (ProductStatRecord)records.get(0);	
		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(6, record.getCurrentSupply());
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals("0.0", Double.toString(record.getUsedAge()));
		assertEquals("0.0", Double.toString(record.getMaximumAge()));
		assertEquals(5, record.getShelfLife());
		assertEquals(0, record.getUsedSupply());
		assertEquals(4, record.getAddedSupply());
		assertEquals("2.2", df.format(record.getAverageSupply()));
		assertEquals(2, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
		
		/* Leap Year Coverage */
		months = 50;
		psv = new ProductStatVisitor(months);
		records = psv.visitAll();
		record = (ProductStatRecord)records.get(0);	
		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(6, record.getCurrentSupply());
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals("0.0", Double.toString(record.getUsedAge()));
		assertEquals("0.0", Double.toString(record.getMaximumAge()));
		assertEquals(5, record.getShelfLife());
		assertEquals(0, record.getUsedSupply());
		assertEquals(6, record.getAddedSupply());
		assertEquals("2", df.format(record.getAverageSupply()));
		assertEquals(2, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
		
		/* Month Changes with Items Removed */
		im.removeItem(i15);
		im.removeItem(i17);
		im.removeItem(i19);
		
		months = 1;
		psv = new ProductStatVisitor(months);
		records = psv.visitAll();
		record = (ProductStatRecord)records.get(0);	
		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(3, record.getCurrentSupply());
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals("497.7", df.format(record.getUsedAge()));
		assertEquals("1461.0", Double.toString(record.getMaximumAge()));
		assertEquals(5, record.getShelfLife());
		assertEquals(3, record.getUsedSupply());
		assertEquals(4, record.getAddedSupply());
		assertEquals("4", df.format(record.getAverageSupply()));
		assertEquals(3, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
		
		months = 12;
		psv = new ProductStatVisitor(months);
		records = psv.visitAll();
		record = (ProductStatRecord)records.get(0);	
		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(3, record.getCurrentSupply());
		assertEquals("2.2", df.format(record.getAverageSupply()));
		assertEquals(2, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
		assertEquals(3, record.getUsedSupply());
		assertEquals(4, record.getAddedSupply());
		assertEquals(5, record.getShelfLife());
		assertEquals("497.7", df.format(record.getUsedAge()));
		assertEquals("1461.0", Double.toString(record.getMaximumAge()));
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		
		months = 50;
		psv = new ProductStatVisitor(months);
		records = psv.visitAll();
		record = (ProductStatRecord)records.get(0);	
		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(3, record.getCurrentSupply());
		assertEquals("2", df.format(record.getAverageSupply()));
		assertEquals(2, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
		assertEquals(3, record.getUsedSupply());
		assertEquals(6, record.getAddedSupply());
		assertEquals(5, record.getShelfLife());
		assertEquals("497.7", df.format(record.getUsedAge()));
		assertEquals("1461.0", Double.toString(record.getMaximumAge()));
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		
		record.getValuesAsStrings();
	}
	
	@Test
	public void reportDateTest(){
		int months = 1;
		ProductStatVisitor psv = new ProductStatVisitor(months);
		Date reportDate = psv.getReportDate();
		
		int daysBetween = getDaysBetweenDates(reportDate,new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		
		assertEquals(getDaysBetweenDates(cal.getTime(), new Date()), daysBetween);
		
		months = 2;
		psv = new ProductStatVisitor(months);
		reportDate = psv.getReportDate();
		
		daysBetween = getDaysBetweenDates(reportDate,new Date());
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -2);
		
		assertEquals(getDaysBetweenDates(cal.getTime(), new Date()), daysBetween);
		
		months = 12;
		psv = new ProductStatVisitor(months);
		reportDate = psv.getReportDate();
		
		daysBetween = getDaysBetweenDates(reportDate,new Date());
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -12);
		
		assertEquals(getDaysBetweenDates(cal.getTime(), new Date()), daysBetween);
		
		months = 50;
		psv = new ProductStatVisitor(months);
		reportDate = psv.getReportDate();
		
		daysBetween = getDaysBetweenDates(reportDate,new Date());
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -50);
		
		assertEquals(getDaysBetweenDates(cal.getTime(), new Date()), daysBetween);
	}
	
	@Test
	public void OneAndZeroProductsTest(){
		setUp();
		
		/* Zero Products */
		int months = 1;
		ProductStatVisitor psv = new ProductStatVisitor(months);
		assertEquals(psv.visitAll().size(), 0);
		
		/* One Product */
		pm.addNewProduct(p3, su2);
		p3.setCreationDate(oneMonth);
		im.addItem(i15);
		im.addItem(i16);
		im.addItem(i17);
		im.addItem(i18);
		im.addItem(i19);
		im.addItem(i20);
		psv = new ProductStatVisitor(months);
		assertEquals(psv.visitAll().size(), 1);
		
		List<Record> records = psv.visitAll();

		ProductStatRecord record = (ProductStatRecord)records.get(0);		
		assertEquals("barcode3", record.getBarcode());
		assertEquals("desc3", record.getDescription());
		assertEquals((float)1.0, record.getSize().getNumber(), 0.000001);
		assertEquals(SizeUnits.Pounds, record.getSize().getUnit());
		assertEquals(5, record.getThreeMonthSupply());
		assertEquals(6, record.getCurrentSupply());
		assertEquals((double)1461, record.getMaximumCurrentSupply(), 0.0000001);
		assertEquals("497.7", df.format(record.getCurrentAverageSupply()));
		assertEquals("0.0", Double.toString(record.getUsedAge()));
		assertEquals("0.0", Double.toString(record.getMaximumAge()));
		assertEquals(5, record.getShelfLife());
		assertEquals(0, record.getUsedSupply());
		assertEquals(4, record.getAddedSupply());
		assertEquals("4.1", df.format(record.getAverageSupply()));
		assertEquals(4, record.getMinimumSupply());
		assertEquals(6, record.getMaximumSupply());
	}

}
