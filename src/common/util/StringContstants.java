package common.util;

import java.io.File;

public class StringContstants {
    
    public static String dataDir;
    
    static {
        
        dataDir = System.getProperty("user.home") + File.separator +
                "hit_data";
        
    }

}
