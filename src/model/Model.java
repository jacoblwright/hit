package model;

import java.io.File;
import java.io.IOException;

public class Model {
    
    private final String C_MANAGER_DATA_PATH = "cm.hit";
    private final String P_MANAGER_DATA_PATH = "pm.hit";
    private final String I_MANAGER_DATA_PATH = "im.hit";
    
    private String saveLocation;
    
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
            
            saveLocation = System.getProperty("user.home") + File.separator;
            
            /*
            saveLocation = getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getFile();
            */
            
            File f = new File(saveLocation + C_MANAGER_DATA_PATH);
            ContainerManager cm = null;
            if (f.exists() && f.canRead()) {
                cm = (ContainerManager)Serializer.load(f);
            }
            if (cm != null) {
                containerManager = cm;
            }
            else {
                containerManager = new ContainerManager();
            }
            
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        System.out.println(containerManager);
        
        /*
        this.containerEditor =
                new ContainerEditor(containerManager, itemManager);
        this.productAndItemEditor =
                new ProductAndItemEditor(containerManager, 
                        productManager, itemManager);
        */
        
    }
    
    /**
     * Saves the state of the managers to disk in the same location as this
     * class file.
     * @pre none
     * @post The state of the managers are saved to disc in the same location
     * as this class file.
     * @throws IOException if there was a problem with saving the state of
     * the managers. 
     */
    public void save() throws IOException {
        
        System.out.println(saveLocation);
        
        Serializer.save(
                containerManager, new File(saveLocation + C_MANAGER_DATA_PATH));
        /*
        Serializer.save(
                productManager, new File(saveLocation + P_MANAGER_DATA_PATH));
        Serializer.save(
                itemManager, new File(saveLocation + I_MANAGER_DATA_PATH));
        */
        
    }
    
    public ContainerEditor getContainerEditor() {
        return containerEditor;
    }

    public void setContainerEditor(ContainerEditor containerEditor) {
        this.containerEditor = containerEditor;
    }

    public ContainerManager getContainerManager() {
        return containerManager;
    }
    
    public void setContainerManager(ContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    public ProductAndItemEditor getProductAndItemEditor() {
        return productAndItemEditor;
    }

    public void setProductAndItemEditor(ProductAndItemEditor productAndItemEditor) {
        this.productAndItemEditor = productAndItemEditor;
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
