package upcdatabasecom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import plugins.AutoIdentityPlugin;

@PluginImplementation
public class upcdatabasePlugin implements AutoIdentityPlugin {

	private String url;
	
	public upcdatabasePlugin() {
		url = "http://www.upcdatabase.com/item/";
	}
	
	@Override
	public String getDescription(String upc) {
		
			String descr = null;
			try {
				URL urlin = new URL(url + upc);
				HttpURLConnection con = (HttpURLConnection) urlin.openConnection();
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
		 
				BufferedReader br = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String buffer;
				String page = "";
				while ((buffer = br.readLine()) != null) {
					page += buffer;
				}
				br.close();
				
				String descSearch = "<tr><td>Description</td><td></td><td>";
				int start = page.indexOf(descSearch);
				System.out.println(start);
				if (start > 0){
					start += descSearch.length();
					int last = page.indexOf("</td></tr>", start);
					descr = page.substring(start, last);
				}
				else{
					descr = null;
				}
				
				
			} catch (MalformedURLException e) {
				descr = null;
			} catch (IOException e) {
				descr = null;
			}
			
			return descr;
	
	}

}
