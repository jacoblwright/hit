package printers;

import java.io.File;
import java.io.IOException;

public abstract class Printer {
    
    public static void checkFile(File file) throws IOException {
        
        if (file == null) {
            throw new IllegalArgumentException("Specified file is null.");
        }
        
        if (file.exists() && (!file.isFile() || !file.canWrite())) {
            throw new IOException("Specified file is not valid.");
        }
        
        if (!file.exists()) {
            
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            
            file.createNewFile();
            
        }
        
    }

}
