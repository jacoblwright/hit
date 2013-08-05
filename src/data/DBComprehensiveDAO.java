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
        
        containerManager = new ContainerManager();
        productManager = new ProductManager();
        itemManager = new ItemManager();
        
        try {
            
            Model.getInstance().getTransaction().startTransaction();
            containerManager.load();
            productManager.load();
            itemManager.load();
            Model.getInstance().getTransaction().endTransaction();
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        containerEditor = new ContainerEditor(containerManager, itemManager);
        productAndItemEditor = new ProductAndItemEditor(
                containerManager, productManager, itemManager);
        
        reportTime = new ReportTime();
        reportTime.setName(IOConfig.REMOVED_ITEMS_REPORT_TIME_NAME);
        reportTime.load();
        
        Model m = Model.getInstance();
        
        m.setContainerEditor(containerEditor);
        m.setContainerManager(containerManager);
        
        m.setProductAndItemEditor(productAndItemEditor);
        m.setProductManager(productManager);
        m.setItemManager(itemManager);
        
        m.setReportTime(reportTime);
        
    }

}
