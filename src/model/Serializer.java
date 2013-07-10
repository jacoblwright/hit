package model;

import java.io.*;
import org.apache.commons.io.*;

/**
 * Serializes and deserializes objects.
 */
public class Serializer {
	
	/**
	 * Writes the state of the specified object to the specified file.
      * @pre file is valid
      * @post saveState(file)
      * @throws IOException
      */
     public static void save(Object object, File file) throws IOException {
         
         
         OutputStream os = new FileOutputStream(file);
    	 IOUtils.write(serialize(object), os);
    	 os.close();
         
         
     }

     /**
      * Loads the object data in the specified File.
      * @pre file is valid
      * @post loadState(file)
      * @return an object whose data was stored in the specified File.
      * @throws ClassNotFoundException 
      */
     public static Object load(File file)
             throws IOException, ClassNotFoundException {
    	 
         
         InputStream is = new FileInputStream(file);
         Object object = deserialize(IOUtils.toByteArray(is));
         is.close();
         

return null;
         
         
//         return null;
         
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
