package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ModelTest {
    
    
    // Check to see that the files are written properly to disk.
    @Test
    public void saveTest() throws Exception {
        
        Model m = Model.getInstance();
        
        m.save();
        
    }
    
    @Test
    public void gettersAndSettersTest() {
        
        Model m = Model.getInstance();
        
        
        
    }

}
