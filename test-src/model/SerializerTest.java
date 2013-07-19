package model;

import gui.common.SizeUnits;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;

public class SerializerTest {
    
    File file;
    
    @Before
    public void before() {
        
        String saveLocation = System.getProperty("user.home")
                + File.separator + "hit_data" + File.separator;
        File dir = new File(saveLocation);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(saveLocation + "test_object_data.hit");
        
    }
    
    /*
     * For save() and load() it is very difficult to make a meaningful test
     * with either of the methods in isolation, so in this case I feel it is
     * justified to implement a test that uses them in conjunction.
     */
    @Test
    public void saveAndLoadTest1() throws Exception {
        
        // --------------------------------
        String orig1 = "String object to be saved";
        Serializer.save(orig1, file);
        String loaded1 = (String)Serializer.load(file);
        //System.out.println("expected:" + orig1 + "\nactual:" + loaded1);
        assertEquals(orig1, loaded1);
        // --------------------------------
        
        // --------------------------------
        List<Map<Integer,String>> orig2 =
                new LinkedList<Map<Integer,String>>();
        Map<Integer, String> map;
        for (int i = 0; i < 100; i++) {
            map = new HashMap<Integer,String>();
            map.put(i, "n" + i);
            map.put(i + 1, "n" + 1 + 1);
            orig2.add(map);
        }
        Serializer.save(orig2, file);
        List<Map<Integer,String>> loaded2 =
                (List<Map<Integer, String>>) Serializer.load(file);
        //System.out.println("expected:" + list + "\nactual:" + listResult);
        assertEquals(orig2, loaded2);
        // --------------------------------
        
    }
    
    @Test
    public void saveAndLoadTest2() throws Exception {
        
        // --------------------------------
        Container orig1a = new StorageUnit();
        orig1a.setId(50);
        Serializer.save(orig1a, file);
        Container loaded1a = (StorageUnit)Serializer.load(file);
        assertEquals(orig1a.toString(), loaded1a.toString());
        // --------------------------------

        // --------------------------------
        Container orig1b = new ProductGroup();
        Serializer.save(orig1b, file);
        Container loaded1b = (ProductGroup)Serializer.load(file);
        assertEquals(orig1b.toString(), loaded1b.toString());
        // --------------------------------
        
        // --------------------------------
        Product orig2 = new Product(
                "12345", "Description", SizeUnits.Count, 1, 8, 8);
        Serializer.save(orig2, file);
        Product loaded2 = (Product)Serializer.load(file);
        assertEquals(orig2.toString(), loaded2.toString());
        // --------------------------------
          
        // --------------------------------
        Item orig3 = new Item(new StorageUnit(), loaded2,
                new Date(), new Barcode());
        orig3.setId(54);
        Serializer.save(orig3, file);
        Item loaded3 = (Item)Serializer.load(file);
        assertEquals(orig3.toString(), loaded3.toString());
        // --------------------------------
        
    }

}
