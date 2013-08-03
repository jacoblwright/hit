package data;

import model.*;
import java.io.*;
import reports.ReportTime;
import config.*;

/**
 * Serialization implementation of ComprehensiveDAO.
 */
public class SerComprehensiveDAO implements ComprehensiveDAO {
    
    private ContainerEditor containerEditor;
    private ContainerManager containerManager;
    
    private ProductAndItemEditor productAndItemEditor;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    private ReportTime reportTime;

    /**
     * Saves the state of the managers to disk.
     * @pre None.
     * @post The state of the managers are saved to disc.
     * @throws IOException if there was a problem with saving the state of
     * the managers. 
     */
    @Override
    public void save() throws IOException {
        
        Model m = Model.getInstance();
        
        Serializer.save(
                m.getContainerManager(), IOConfig.getContainerManagerFile());
        Serializer.save(
                m.getProductManager(), IOConfig.getProductManagerFile());
        Serializer.save(
                m.getItemManager(), IOConfig.getItemManagerFile());
        Serializer.save(
                m.getReportTime(), IOConfig.getReportTimeFile());
        
    }

    /**
     * Loads the saved object data of the managers and constructs the editors
     * using those managers.
     * @pre none
     * @post Editors and managers have been constructed using saved object data
     * files, or new managers have been constructed if the files don't exist.
     */
    @Override
    public void load() {
        
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
            
            initialize();
            
        }
        catch (ClassNotFoundException e) {
            
            e.printStackTrace();
            
            initialize();
            
        }
        
        containerEditor =
                new ContainerEditor(containerManager, itemManager);
        productAndItemEditor =
                new ProductAndItemEditor(containerManager, 
                        productManager, itemManager);
        
        Model m = Model.getInstance();
        
        m.setContainerEditor(containerEditor);
        m.setContainerManager(containerManager);
        
        m.setProductAndItemEditor(productAndItemEditor);
        m.setProductManager(productManager);
        m.setItemManager(itemManager);
        
        m.setReportTime(reportTime);
        
    }
    
    private void initialize() {
        
        containerManager = new ContainerManager();
        productManager = new ProductManager();
        itemManager = new ItemManager();
        reportTime = new ReportTime();
        reportTime.setName("removed_items");
        
    }

}
