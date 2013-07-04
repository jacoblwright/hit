package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**Serializes and Deserializes objects
 * 
 * @author jake
 *
 */
public class Serializer {
	
	/**Serializes an object to a byte[]
	 * @pre	none
	 * @param obj
	 * @return byte[]
	 * @throws IOException
	 */
	 private static byte[] serialize(Object obj) throws IOException {
	       ByteArrayOutputStream b = new ByteArrayOutputStream();
	       ObjectOutputStream o = new ObjectOutputStream(b);
	       o.writeObject(obj);
	       return b.toByteArray();
	  }

	 /**Deserializes a byte[] to an object
	  * @pre nones
	  * @param bytes
	  * @return	object
	  * @throws IOException
	  * @throws ClassNotFoundException
	  */
     private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
         ByteArrayInputStream b = new ByteArrayInputStream(bytes);
         ObjectInputStream o = new ObjectInputStream(b);
         return o.readObject();
     }
     
     /** Given a file it reads it and loads the state of the core object model
      * @pre						valid file
      * @post						loadState(file)
      * @throws IOException
      */
     public void load( File file) throws IOException {
    	 
     }
     
     /** Given a file it writes the state of the core object model there
      * @pre						valid file
      * @post						saveState(file)
      * @throws IOException
      */
     public void save( File file ) throws IOException {
    	 
     }
     
     
}
