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
            
            /*
            saveLocation = getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getFile() + File.separator +
                    "hit_data" + File.separator;
            */
            
            saveLocation = System.getProperty("user.home") + File.separator +
                    "hit_data" + File.separator;
            File dir = new File(saveLocation);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            File file = new File(saveLocation + C_MANAGER_DATA_PATH);
            ContainerManager cm = null;
            if (file.exists() && file.canRead()) {
                cm = (ContainerManager)Serializer.load(file);
            }
            if (cm != null) {
                containerManager = cm;
            }
            else {
                containerManager = new ContainerManager();
            }
            
            file = new File(saveLocation + P_MANAGER_DATA_PATH);
            ProductManager pm = null;
            if (file.exists() && file.canRead()) {
                pm = (ProductManager)Serializer.load(file);
            }
            if (pm != null) {
                productManager = pm;
            }
            else {
                productManager = new ProductManager();
            }
            
            file = new File(saveLocation + I_MANAGER_DATA_PATH);
            ItemManager im = null;
            if (file.exists() && file.canRead()) {
                im = (ItemManager)Serializer.load(file);
            }
            if (im != null) {
                itemManager = im;
            }
            else {
                itemManager = new ItemManager();
            }
            
        
        }
        catch (IOException e) {
            
            e.printStackTrace();
            
            containerManager = new ContainerManager();
            productManager = new ProductManager();
            itemManager = new ItemManager();
            
        }
        catch (ClassNotFoundException e) {
            
            e.printStackTrace();
            
            containerManager = new ContainerManager();
            productManager = new ProductManager();
            itemManager = new ItemManager();
            
        }
        
        this.containerEditor =
                new ContainerEditor(containerManager, itemManager);
        this.productAndItemEditor =
                new ProductAndItemEditor(containerManager, 
                        productManager, itemManager);
        
    }
    
    /**
     * Saves the state of the managers to disk in the same location as this
     * class file.
     * @pre None.
     * @post The state of the managers are saved to disc in the same location
     * as this class file.
     * @throws IOException if there was a problem with saving the state of
     * the managers. 
     */
    public void save() throws IOException {
        
        assert true;
        
        //System.out.println(saveLocation);
        
        Serializer.save(
                containerManager, new File(saveLocation + C_MANAGER_DATA_PATH));
        Serializer.save(
                productManager, new File(saveLocation + P_MANAGER_DATA_PATH));
        Serializer.save(
                itemManager, new File(saveLocation + I_MANAGER_DATA_PATH));
        
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
