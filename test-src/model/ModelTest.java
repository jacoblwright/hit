package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ModelTest {

    @Test
    public void gettersAndSettersTest() {
        
        Model m = new Model();
        
        ContainerManager containerManager = new ContainerManager();
        ProductManager productManager = new ProductManager();
        ItemManager itemManager = new ItemManager();
        
        ContainerEditor containerEditor =
                new ContainerEditor(containerManager, itemManager);
        ProductAndItemEditor productAndItemEditor =
                new ProductAndItemEditor(
                        containerManager, productManager, itemManager);
        
        m.setContainerEditor(containerEditor);
        assertTrue(containerEditor == m.getContainerEditor());
        
        m.setContainerEditor(containerEditor);
        assertTrue(containerEditor == m.getContainerEditor());
        
        m.setContainerEditor(containerEditor);
        assertTrue(containerEditor == m.getContainerEditor());
        
        m.setContainerEditor(containerEditor);
        assertTrue(containerEditor == m.getContainerEditor());
        
        m.setContainerEditor(containerEditor);
        assertTrue(containerEditor == m.getContainerEditor());
        
    }

}
