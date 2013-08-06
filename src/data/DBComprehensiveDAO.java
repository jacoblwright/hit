package data;

import java.io.IOException;

import config.IOConfig;
import model.*;
import reports.ReportTime;

/**
 * Database implementation of ComprehensiveDAO.
 */
public class DBComprehensiveDAO implements ComprehensiveDAO {
    
    private ContainerEditor containerEditor;
    private ContainerManager containerManager;
    
    private ProductAndItemEditor productAndItemEditor;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    private ReportTime reportTime;

    @Override
    public void save() {
        // Does nothing, because in database mode the data has already been
        // saved by the ComponentDAO's.
    }

    @Override
    public void load() {
        
        Model m = Model.getInstance();
        
        containerManager = new ContainerManager();             
        productManager = new ProductManager();
        itemManager = new ItemManager();
        
        try {
            
            m.getTransaction().startTransaction();            
            containerManager.load();
            m.getTransaction().endTransaction();
            
            m.setContainerManager(containerManager);
            
            m.getTransaction().startTransaction();            
            //productManager.load();
            m.getTransaction().endTransaction();
            
            m.setProductManager(productManager);
            
            m.getTransaction().startTransaction();            
            //itemManager.load();
            m.getTransaction().endTransaction();
            
            m.setItemManager(itemManager);     
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        containerEditor = new ContainerEditor(containerManager, itemManager);
        
        m.setContainerEditor(containerEditor);
        
        productAndItemEditor = new ProductAndItemEditor(
                containerManager, productManager, itemManager);
        
        m.setProductAndItemEditor(productAndItemEditor);
        
        reportTime = new ReportTime(IOConfig.REMOVED_ITEMS_REPORT_TIME_NAME);
        reportTime.load();
        
        m.setReportTime(reportTime);
        
    }

}
