package model;

import java.io.*;

//import org.apache.commons.io.*;

import sun.misc.IOUtils;

/**
 * Serializes and deserializes objects
 * @author jake
 */
public class Serializer {
	
	/**
	 * Given a file it writes the state of the core object model there
      * @pre valid file
      * @post saveState(file)
      * @throws IOException
      */
     public static void save(Object object, File file) throws IOException {
         
         OutputStream os = new FileOutputStream(file);
//    	 IOUtils.write(serialize(object), os);
    	 os.close();
         
     }

     /**
      * Given a file it reads it and loads the state of the core object model
      * @pre valid file
      * @post loadState(file)
      * @throws IOException
     * @throws ClassNotFoundException 
      */
     public static Object load(File file)
             throws IOException, ClassNotFoundException {
    	 
         InputStream is = new FileInputStream(file);
//         Object object = deserialize(IOUtils.toByteArray(is));
         Object object = new Object();
         is.close();
         
         return object;
         
     }
     
     /**
     * Serializes an object to a byte[]
     * @pre	none
     * @param obj
     * @return byte[]
     * @throws IOException
     */
     private static byte[] serialize(Object obj) throws IOException {
         
         assert true;
         
         ByteArrayOutputStream b = new ByteArrayOutputStream();
         ObjectOutputStream o = new ObjectOutputStream(b);
         o.writeObject(obj);
         return b.toByteArray();
    
     }

    /**Deserializes a byte[] to an object
      * @pre none
      * @param bytes
      * @return	object
      * @throws IOException
      * @throws ClassNotFoundException
      */
     private static Object deserialize(byte[] bytes)
             throws IOException, ClassNotFoundException {
         
         assert true;
         
         ByteArrayInputStream b = new ByteArrayInputStream(bytes);
         ObjectInputStream o = new ObjectInputStream(b);
         return o.readObject();
    
     }
       
}
