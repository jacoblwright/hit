package plugins;

import static org.junit.Assert.*;

import org.junit.Test;

import upcfoodsearch.UPCFoodSearchPlugin;

public class UPCFoodSearchPluginTest {

	@Test
	public void getDescriptionTest() {
		UPCFoodSearchPlugin search = new UPCFoodSearchPlugin();
		System.out.println(search.getDescription("041789002175"));	
	}

}
