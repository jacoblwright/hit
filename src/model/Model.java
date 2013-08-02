package model;

import java.io.File;
import java.io.IOException;

import plugins.UPCDescriptionFetcher;
import reports.*;

import config.IOConfig;

public class Model {
    
    private static Model instance = null;
    
    private ContainerEditor containerEditor;
    private ContainerManager containerManager;
    
    private ProductAndItemEditor productAndItemEditor;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    private UPCDescriptionFetcher upcDescriptionFetcher;
    
    private ReportTime reportTime;
        
    /**
     * Loads the saved object data of the managers and constructs the editors
     * using those managers.
     * @pre none
     * @post Editors and managers have been constructed using saved object data
     * files, or new managers have been constructed if the files don't exist.
     */
    private Model() {
        
        assert true;
        
        try {
            
            File dir = IOConfig.getContainerManagerFile().getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            File file = IOConfig.getContainerManagerFile();
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
            
            file = IOConfig.getProductManagerFile();
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
            
            file = IOConfig.getItemManagerFile();
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
            
            file = IOConfig.getReportTimeFile();
            ReportTime rt = null;
            if (file.exists() && file.canRead()) {
                rt = (ReportTime)Serializer.load(file);
            }
            if (rt != null) {
                reportTime = rt;
            }
            else {
                reportTime = new ReportTime();
            }            
        
        }
        catch (IOException e) {
            
            e.printStackTrace();
            
            createNewFiles();
            
        }
        catch (ClassNotFoundException e) {
            
            e.printStackTrace();
            
            createNewFiles();
            
        }
        
        this.containerEditor =
                new ContainerEditor(containerManager, itemManager);
        this.productAndItemEditor =
                new ProductAndItemEditor(containerManager, 
                        productManager, itemManager);
        
    }
    
    /**
     * Returns an instance of the singleton Model class.
     * @pre None.
     * @return an instance of the Model class; the returned reference always
     * points to the same instance.
     */
    public static Model getInstance() {
        
        if (instance == null) {            
            instance = new Model();            
        }
        
        return instance;
        
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
        
        Serializer.save(
                containerManager, IOConfig.getContainerManagerFile());
        Serializer.save(
                productManager, IOConfig.getProductManagerFile());
        Serializer.save(
                itemManager, IOConfig.getItemManagerFile());
        Serializer.save(
                reportTime, IOConfig.getReportTimeFile());
        
    }
    
    private void createNewFiles() {
        
        assert true;
        
        containerManager = new ContainerManager();
        productManager = new ProductManager();
        itemManager = new ItemManager();
        reportTime = new ReportTime();
        
    }
    
    /** Returns a UPCDescriptionFetcher object used for find the description of a upc
     * 
     * @return UPCDescriptionFetcher object
     */
    
    public UPCDescriptionFetcher getUPCDescriptionFetcher() {
    	return null;
    }
    
    public ContainerEditor getContainerEditor() {
        return containerEditor;
    }
    
    /*
    public void setContainerEditor(ContainerEditor containerEditor) {
        this.containerEditor = containerEditor;
    }
     */
    
    public ContainerManager getContainerManager() {
        return containerManager;
    }
    
    /*
    public void setContainerManager(ContainerManager containerManager) {
        this.containerManager = containerManager;
    }
    */
    
    public ProductAndItemEditor getProductAndItemEditor() {
        return productAndItemEditor;
    }
    
    /*
    public void setProductAndItemEditor(ProductAndItemEditor productAndItemEditor) {
        this.productAndItemEditor = productAndItemEditor;
    }
    */
    
    public ProductManager getProductManager() {
        return productManager;
    }
    
    /*
    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }
    */
    
    public ItemManager getItemManager() {
        return itemManager;
    }
    
    /*
    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
    */
    
    public ReportTime getReportTime() {
        return reportTime;
    }
    
}
