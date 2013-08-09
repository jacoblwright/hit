package reports;

import static org.junit.Assert.*;
import gui.common.SizeUnits;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import data.SerDAOFactory;

import java.text.*;
import java.util.*;

import model.*;

/**
 * @author evan
 */
public class ProductStatVisitorTestEvan {

    private static Model m;
    
    private static DateFormat dateFormat;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        
        m = Model.getInstance();
        m.setDAOFactory(new SerDAOFactory());
        m.initialize();
        
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        
    }
    
    @Before
    public void before() {
        
        m.clear();
        
    }
    
    @Test
    /**
     * - Items: enter in ST, end in DST.
     * - Period: start in ST, end in DST.
     */
    public void testI() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Date d1 = dateFormat.parse("2013/1/1");
        Date d2 = dateFormat.parse("2013/7/1");
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(8);        
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(2, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(180, (int)psr.getUsedAge());
        assertEquals(180, (int)psr.getMaximumAge());
        
    }
    
    @Test
    /**
     * - Items: enter in ST, end in DST.
     * - Period: start in DST, end in DST.
     */
    public void testII() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Date d1 = dateFormat.parse("2013/1/1");
        Date d2 = dateFormat.parse("2013/7/1");
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(4);        
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(0, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(180, (int)psr.getUsedAge());
        assertEquals(180, (int)psr.getMaximumAge());
        
    }
    
    @Test
    /**
     * - Items: enter in DST, end in DST.
     * - Period: start in ST, end in DST.
     */
    public void testIII() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Date d1 = dateFormat.parse("2013/6/1");
        Date d2 = dateFormat.parse("2013/7/1");
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(8);        
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(2, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(30, (int)psr.getUsedAge());
        assertEquals(30, (int)psr.getMaximumAge());
        
    }

    @Test
    /**
     * - Items: enter in DST, end in DST.
     * - Period: start in DST, end in DST.
     */
    public void testIV() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Date d1 = dateFormat.parse("2013/6/1");
        Date d2 = dateFormat.parse("2013/7/1");
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(4);        
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(2, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(30, (int)psr.getUsedAge());
        assertEquals(30, (int)psr.getMaximumAge());
        
    }
    
    @Test
    /**
     * - Product added in the middle of the reporting period.
     */
    public void testV() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Date d1 = dateFormat.parse("2013/8/6");
        Date d2 = dateFormat.parse("2013/8/8");
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(2);
        
        //System.out.println(((ProductStatVisitor)visitor).getReportDate());
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(2, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(2, (int)psr.getUsedAge());
        assertEquals(2, (int)psr.getMaximumAge());
        
    }
    
    @Test
    /**
     * - Product added on the first day of the reporting period.
     */
    public void testVI() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date d1 = c.getTime();
        //System.out.println(d1);
        Date d2 = dateFormat.parse("2013/8/8");
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(1);
        
        //System.out.println(((ProductStatVisitor)visitor).getReportDate());
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(2, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(29, (int)psr.getUsedAge());
        assertEquals(29, (int)psr.getMaximumAge());
        
    }


    @Test
    /**
     * - Product added on the last day of the reporting period.
     */
    public void testVII() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 6, 3);
       
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date d1 = c.getTime();
        //System.out.println(d1);
        Date d2 = new Date();
        
        p1.setCreationDate(d1);
        
        Container container = new ProductGroup();
        Barcode tag1 = new Barcode("tag1");
        Barcode tag2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, tag1);
        Item i2 = new Item(container, p1, d1, tag2);
        
        m.getProductManager().addNewProduct(p1, container);
        
        m.getItemManager().addItem(i1);
        m.getItemManager().addItem(i2);
        
        m.getItemManager().removeItem(i1);
        i1.setExitTime(d2);
        m.getItemManager().removeItem(i2);
        i2.setExitTime(d2);
        
        Visitor visitor = new ProductStatVisitor(2);
        
        //System.out.println(((ProductStatVisitor)visitor).getReportDate());
        
        List<Record> records = visitor.visitAll();
        Iterator<Record> itr = records.iterator();
        
        ProductStatRecord psr = (ProductStatRecord)itr.next();
        
        /*
        System.out.println(psr.getCurrentSupply());
        System.out.println(psr.getAverageSupply());
        System.out.println(psr.getMinimumSupply());
        System.out.println(psr.getMaximumSupply());
        System.out.println(psr.getUsedSupply());
        System.out.println(psr.getAddedSupply());
        System.out.println(psr.getShelfLife());
        System.out.println(psr.getUsedAge());
        System.out.println(psr.getMaximumAge());
        */
        
        assertEquals(0, psr.getCurrentSupply());
        //assertEquals(0, (int)psr.getAverageSupply());
        assertEquals(0, psr.getMinimumSupply());
        assertEquals(2, psr.getMaximumSupply());
        assertEquals(2, psr.getUsedSupply());
        assertEquals(2, psr.getAddedSupply());
        assertEquals(6, psr.getShelfLife());
        assertEquals(31, (int)psr.getUsedAge());
        assertEquals(31, (int)psr.getMaximumAge());
        
    }
    
    @Test
    /**
     * Getters and setters
     */
    public void testVIII() {
        
        ProductStatVisitor visitor = new ProductStatVisitor(1);
        
        Map<Product, Set<Item>> currentProductToItemsMap =
                new HashMap<Product, Set<Item>>();
        visitor.setCurrentProductToItemsMap(currentProductToItemsMap);
        assertEquals(currentProductToItemsMap,
                visitor.getCurrentProductToItemsMap());
        
        Map<Product, Set<Item>> productToAllItemsMap =
                new HashMap<Product, Set<Item>>();
        visitor.setProductToAllItemsMap(productToAllItemsMap);
        assertEquals(productToAllItemsMap, visitor.getProductToAllItemsMap());
        
        Map<Product, Set<Item>> removedProductToItemsMap =
                new HashMap<Product, Set<Item>>();
        visitor.setRemovedProductToItemsMap(removedProductToItemsMap);
        assertEquals(removedProductToItemsMap,
                visitor.getRemovedProductToItemsMap());
        
        Date reportDate = new Date();
        visitor.setReportDate(reportDate);
        assertEquals(reportDate, visitor.getReportDate());
        
        Map<Product, Set<Item>> usedItemsDuringPeriod =
                new HashMap<Product, Set<Item>>();
        visitor.setUsedItemsDuringPeriod(usedItemsDuringPeriod);
        assertEquals(usedItemsDuringPeriod, visitor.getUsedItemsDuringPeriod());
        
    }
    
}
