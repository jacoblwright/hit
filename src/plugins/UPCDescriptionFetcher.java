package plugins;

import gui.main.GUI;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Collection;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

//import net.xeoh.plugins.base.util.PluginManagerUtil;

/** Class used to find the description of a UPC
 * 
 * @author andrew
 *
 */
public class UPCDescriptionFetcher {
	
	/** Used for managing plugins */
	//private PluginManagerUtil pluginManager;
	
	/** Grabs the upc description for a collection of plugins
	 * 
	 * @param upc 	the upc being search for
	 * @return	the description of the upc
	 */
	public String fetchUPCDescription(String upc){
		String upcDescription = null;
		PluginManager pm = PluginManagerFactory.createPluginManager();
		PluginManagerUtil pluginManager = new PluginManagerUtil(pm);
		String searchPath = getCurrentDirectory() + "/plugins/";
		System.out.println("Checking plugin path: " + searchPath);
		pluginManager.addPluginsFrom(new File( searchPath ).toURI());
		Collection<AutoIdentityPlugin> plugins = 
				pluginManager.getPlugins(AutoIdentityPlugin.class);
		System.out.println("plugin size: " +plugins.size());
		for(AutoIdentityPlugin plugin : plugins) {
			upcDescription = plugin.getDescription(upc);
			System.out.println(upcDescription);
			if(upcDescription != null && !upcDescription.isEmpty()) {
				return upcDescription;
			}
		}
		return upcDescription;
	}
	
	public String getCurrentDirectory() {
		CodeSource codeSource = GUI.class.getProtectionDomain().getCodeSource();
		File jarFile = null;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jarDir = jarFile.getParentFile().getPath();
		return jarDir;
	}

}
