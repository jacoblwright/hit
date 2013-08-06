package plugins;

import static org.junit.Assert.*;

import org.junit.Test;
import upcdatabasecom.*;

public class upcdatabasePluginTest {

	@Test
	public void testValid() {
		AutoIdentityPlugin pi = new upcdatabasePlugin();
		System.out.print(pi.getDescription("0049000047790"));
		assertEquals("?Heco En Mexico? Coca-Cola made in Mexico",pi.getDescription("0049000047790"));
		
		
	}

	@Test
	public void testInvalid() {
		AutoIdentityPlugin pi = new upcdatabasePlugin();
		System.out.print(">>>" + pi.getDescription("0"));
		assertEquals(null,pi.getDescription("0"));
		
		
	}

}
