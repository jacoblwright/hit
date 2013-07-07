package model;

import java.io.File;
import java.io.IOException;

public class Controller {
    
    private final String C_MANAGER_DATA_PATH = "data/cm.hit";
    private final String P_MANAGER_DATA_PATH = "data/pm.hit";
    private final String I_MANAGER_DATA_PATH = "data/im.hit";
    
    private ContainerEditor containerEditor;
    private ContainerManager containerManager;
    
    private ProductAndItemEditor productAndItemEditor;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    /**
     * Loads the saved object data of the managers and constructs the editors
     * using those managers.
     * @pre none
     * @post Editors and managers have been constructed using saved object data
     * files, or new managers have been constructed if the files don't exist.
     */
    public Controller() {
        
        assert true;
        
        try {
            
            File f;
            
            f = new File(C_MANAGER_DATA_PATH);
            if (f.exists()) {
                this.containerManager = (ContainerManager)Serializer.load(f);
            }
            else {
                this.containerManager = new ContainerManager();
            }
            
            f = new File(P_MANAGER_DATA_PATH);
            if (f.exists()) {
                this.productManager = (ProductManager)Serializer.load(f);
            }
            else {
                this.productManager = new ProductManager();
            }
            
            f = new File(I_MANAGER_DATA_PATH);
            if (f.exists()) {
                this.itemManager = (ItemManager)Serializer.load(f);
            }
            else {
                this.itemManager = new ItemManager();
            }
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        this.containerEditor =
                new ContainerEditor(containerManager, itemManager);
        this.productAndItemEditor =
                new ProductAndItemEditor(productManager, itemManager);
        
    }

    public ContainerEditor getContainerEditor() {
        return containerEditor;
    }

    public ContainerManager getContainerManager() {
        return containerManager;
    }

    public ProductAndItemEditor getProductAndItemEditor() {
        return productAndItemEditor;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
    
}
