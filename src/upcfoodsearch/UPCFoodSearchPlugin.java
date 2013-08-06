package upcfoodsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import plugins.AutoIdentityPlugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class UPCFoodSearchPlugin implements AutoIdentityPlugin{

	public String getDescription(String upc) {
		
		String descr = null;
		String url = "http://www.upcfoodsearch.com/upc-";
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
			
			return parseDescription(page);
		}
		
		catch (MalformedURLException e) {
				descr = null;
		} 
		catch (IOException e) {
				descr = null;
		}
		return null;
	}
	
	public String parseDescription(String page){
		String regexString = "<div class=\"blockTitleL cen\"><h1>(.*?)</h1></div>";
		Pattern pattern = Pattern.compile(regexString);
		Matcher matcher = pattern.matcher(page);

		if (matcher.find()) {
		  if(matcher.group(1).equals("Not Found"))
				  return null;
		  else {
			  return matcher.group(1);
		  }
		}
		else return null;
	}

}
