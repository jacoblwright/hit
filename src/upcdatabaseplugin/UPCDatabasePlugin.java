package upcdatabaseplugin;

import java.io.*;
import java.net.*;
import javax.xml.stream.*;

public class UPCDatabasePlugin implements AutoIdentityPlugin {

    String urlBase = "http://api.upcdatabase.org/xml/" +
    		"00051734867f77ff379244f17e78ee84/";
    
    @Override
    public String getDescription(String upc) {
    
        BufferedReader br = null;
        String description = "";
        
        try {
            
            URL url = new URL(urlBase + upc);
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
     
            InputStream is =
                    new BufferedInputStream(connection.getInputStream());
     
            XMLStreamReader reader = XMLInputFactory.newInstance().
                    createXMLStreamReader(is);
            skipToStartTag(reader, "description");
            if (reader.hasNext()) {
                
                reader.next();
                description = reader.getText();
            
            }
            
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (FactoryConfigurationError e) {
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }        
        finally {
            
            if (br != null) {
            
                try {
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            
            }
        
        }
        
        return description;
    
    }
    
    private void skipToStartTag(XMLStreamReader reader, String tagName)
            throws XMLStreamException {              
        
        int event = reader.next();             
        while (reader.hasNext() &&
                !(event == XMLStreamConstants.START_ELEMENT &&
                reader.getLocalName().equals(tagName))) {   
            
            event = reader.next();   
            
        }
        
    }
    
}
