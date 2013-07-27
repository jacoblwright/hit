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
        
        ContainerManager containerManager = new ContainerManager();
        ProductManager productManager = new ProductManager();
        ItemManager itemManager = new ItemManager();
        
        ContainerEditor containerEditor =
                new ContainerEditor(containerManager, itemManager);
        ProductAndItemEditor productAndItemEditor =
                new ProductAndItemEditor(
                        containerManager, productManager, itemManager);
        
        /*
        m.setContainerEditor(containerEditor);
        assertTrue(containerEditor == m.getContainerEditor());
        
        m.setContainerManager(containerManager);
        assertTrue(containerManager == m.getContainerManager());
        
        m.setProductAndItemEditor(productAndItemEditor);
        assertTrue(productAndItemEditor == m.getProductAndItemEditor());
        
        m.setProductManager(productManager);
        assertTrue(productManager == m.getProductManager());
        
        m.setItemManager(itemManager);
        assertTrue(itemManager == m.getItemManager());
        */
        
    }

}
