package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
	 public static byte[] serialize(Object obj) throws IOException {
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
     public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
         ByteArrayInputStream b = new ByteArrayInputStream(bytes);
         ObjectInputStream o = new ObjectInputStream(b);
         return o.readObject();
     }
}
