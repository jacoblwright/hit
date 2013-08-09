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
    public void test1() throws Exception {
         
        Product p1 = new Product("upc1", "Product 1", SizeUnits.Count, 1, 1, 1);
        Product p2 = new Product("upc2", "Product 2", SizeUnits.Count, 1, 1, 1);
       
        Date d1 = dateFormat.parse("2013/12/9");
        Date d2 = dateFormat.parse("2013/1/1");
        
        Container container = new ProductGroup();
        Barcode bc1 = new Barcode("tag1");
        Barcode bc2 = new Barcode("tag2");
        
        Item i1 = new Item(container, p1, d1, bc1);
        
    }

}
