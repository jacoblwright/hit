package plugins;

import static org.junit.Assert.*;
import org.junit.Test;

public class UPCDescriptionFetcherTest {

	@Test
	public void upcDescriptionFetcherTest() {
	    
		UPCDescriptionFetcher fetcher = new UPCDescriptionFetcher();
		
		String description = fetcher.fetchUPCDescription("0040000011040");
		assertNotNull(description);
		
		String description2 = fetcher.fetchUPCDescription("076183164198");
		assertNotNull(description2);
		
	}
	
}
