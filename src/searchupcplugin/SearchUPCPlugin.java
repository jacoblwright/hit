package searchupcplugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import plugins.AutoIdentityPlugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class SearchUPCPlugin implements AutoIdentityPlugin {

	private String cvsSplitBy = ",";
	private String urlStr = "http://www.searchupc.com/handlers/upcsearch.ashx?" +
			"request_type=1&access_token=67716161-6472-44C5-9460-DC76BE74C549&upc=";
	public String getDescription(String upc) {
		String upcBarcode = null;
		
		try {
			URL url = new URL(urlStr + upc);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
	 
			BufferedReader br = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String buffer;
	 
			List<String> descriptions = new ArrayList<String>();
			while ((buffer = br.readLine()) != null) {
				String[] product = buffer.split(cvsSplitBy);
				descriptions.add(product[0]);
			}
			br.close();
			
			if(descriptions.size() > 1) {
				upcBarcode = removeQuotes(descriptions.get(1));
			}
			
		} catch (MalformedURLException e) {
			upcBarcode = null;
		} catch (IOException e) {
			upcBarcode = null;
		}
		
		return upcBarcode;
	}
	
	private String removeQuotes(String description) {
		return description.substring(1,description.length());
	}

}


