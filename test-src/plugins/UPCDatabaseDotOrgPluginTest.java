package plugins;

import static org.junit.Assert.*;
import upcdatabasedotorgplugin.*;

import org.junit.Test;

public class UPCDatabaseDotOrgPluginTest {

    @Test
    public void test1() {
        
        UPCDatabaseDotOrgPlugin p = new UPCDatabaseDotOrgPlugin();
        
        System.out.print("Scotch tape: ");
        System.out.println(p.getDescription("051131658110"));
        System.out.print("Peanut butter: ");
        System.out.println(p.getDescription("011110610492"));
        System.out.print("Lindt chocolate (expect not to be found): ");
        System.out.println(p.getDescription("037466066929"));
        System.out.print("Invalid upc: ");
        System.out.println(p.getDescription("abcde"));
        
    }

}