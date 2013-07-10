package model;

import java.io.File;
import java.io.IOException;

public class Model {
    
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
    public Model() {
        
        assert true;
        
        try {
            
            File f;
            String pathBase = getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getFile();
            
            f = new File(pathBase + C_MANAGER_DATA_PATH);
            if (f.exists() && f.canRead()) {
                this.containerManager = (ContainerManager)Serializer.load(f);
            }
            else {
                this.containerManager = new ContainerManager();
            }
            
            f = new File(pathBase + P_MANAGER_DATA_PATH);
            if (f.exists() && f.canRead()) {
                this.productManager = (ProductManager)Serializer.load(f);
            }
            else {
                this.productManager = new ProductManager();
            }
            
            f = new File(pathBase + I_MANAGER_DATA_PATH);
            if (f.exists() && f.canRead()) {
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

    public ContainerManager getContainerManager() {
        return containerManager;
    }

    public void setContainerManager(ContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
    
}
