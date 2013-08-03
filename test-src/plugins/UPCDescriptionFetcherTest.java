package plugins;

import static org.junit.Assert.*;
import org.junit.Test;

public class UPCDescriptionFetcherTest {

	@Test
	public void upcDescriptionFetcherTest() {
		UPCDescriptionFetcher fetcher = new UPCDescriptionFetcher();
		String description = fetcher.fetchUPCDescription("123456789012");
		
		//assertTrue(description.equals("Hello World"));
	}
}
