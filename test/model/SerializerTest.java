package model;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;

public class SerializerTest {
    
    /*
     * For save() and load() it is very difficult to make a meaningful test
     * with either of the methods in isolation, so in this case I feel it is
     * justified to implement a test that uses them in conjunction.
     */
    @Test
    public void saveAndLoadTest() throws Exception {
        
        System.out.println("saveAndLoadTest()");
        
        String pathBase = getClass().getProtectionDomain().getCodeSource()
                .getLocation().getFile();
        //System.out.println("location of object file:" + pathBase);
        File f = new File(pathBase + "test_object_data.hit");
        
        String originalString = "String object to be saved";
        Serializer.save(originalString, f);
        String loadedString = (String)Serializer.load(f);
        //System.out.println("expected:" + string + "\nactual:" + stringResult);
        assertEquals(originalString, loadedString);
        
        List<Map<Integer,String>> originalList =
                new ArrayList<Map<Integer,String>>();
        Map map1 = new HashMap<Integer,String>();
        map1.put(1, "one");
        map1.put(2, "two");
        Map map2 = new HashMap<Integer,String>();
        map2.put(1, "one");
        map2.put(2, "two");
        originalList.add(map1);
        originalList.add(map2);
        Serializer.save(originalList, f);
        List<Map<Integer,String>> loadedList =
                (List<Map<Integer, String>>) Serializer.load(f);
        //System.out.println("expected:" + list + "\nactual:" + listResult);
        assertEquals(originalList, loadedList);
        
    }

}
