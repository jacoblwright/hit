package upcdatabasedotorgplugin;

import net.xeoh.plugins.base.Plugin;

/** Provides an interface for all plugins used for auto identification
 * 
 * @author andrew
 *
 */
public interface AutoIdentityPlugin extends Plugin {
	
	/** Find a string representing a barcode
	 * 
	 * @param upc	the upc that is being searched for
	 * @return	the description of the upc
	 */
	public String getDescription(String upc);

}
