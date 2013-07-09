package model;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;

public class SerializerTest {
    
    File f;
    
    @Before
    public void before() {
        
        String pathBase = getClass().getProtectionDomain().getCodeSource()
                .getLocation().getFile();
        //System.out.println("location of object file:" + pathBase);
        f = new File(pathBase + "test_object_data.hit");
        
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
        Serializer.save(orig1, f);
        String loaded1 = (String)Serializer.load(f);
        //System.out.println("expected:" + string + "\nactual:" + stringResult);
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
        Serializer.save(orig2, f);
        List<Map<Integer,String>> loaded2 =
                (List<Map<Integer, String>>) Serializer.load(f);
        //System.out.println("expected:" + list + "\nactual:" + listResult);
        assertEquals(orig2, loaded2);
        // --------------------------------
        
    }
    
    @Test
    public void saveAndLoadTest2() throws Exception {
        
        // --------------------------------
        Container orig1a = new StorageUnit();
        orig1a.setId(50);
        Serializer.save(orig1a, f);
        Container loaded1a = (StorageUnit)Serializer.load(f);
        assertEquals(orig1a, loaded1a);
        // --------------------------------
        
        // --------------------------------
        Container orig1b = new ProductGroup();
        orig1b.setId(50);
        Serializer.save(orig1b, f);
        Container loaded1b = (ProductGroup)Serializer.load(f);
        assertEquals(orig1b, loaded1b);
        // --------------------------------
        
        // --------------------------------
        //Product orig2 = new Product();
        //orig2.setId(52);
        //Serializer.save(orig2, f);
        //Product loaded2 = (Product)Serializer.load(f);
        //assertEquals(orig2, loaded2);
        // --------------------------------
          
        /*
        // --------------------------------
        Item orig3 = new Item(new StorageUnit(), loaded2,
                new Date(), new Barcode());
        orig3.setId(54);
        Serializer.save(orig3, f);
        Item loaded3 = (Item)Serializer.load(f);
        assertEquals(orig3, loaded3);
        // --------------------------------
        */
        
        
        // --------------------------------
        List<Container> orig4 = new LinkedList<Container>();
        Container c;
        for (int i = 0; i < 1; i++) {
            c = new StorageUnit();
            c.setId(i);
            c.setName("Container " + i);
            orig4.add(c);
        }
        Serializer.save(orig4, f);
        List<Container> loaded4 =
                (List<Container>)Serializer.load(f);
        assertEquals(orig4, loaded4);
        // --------------------------------
        
        
    }

}
