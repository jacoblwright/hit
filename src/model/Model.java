package model;

import reports.*;
import data.*;

public class Model {
    
    private static Model instance = null;
    
    private ContainerEditor containerEditor;
    private ContainerManager containerManager;
    
    private ProductAndItemEditor productAndItemEditor;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    //private UPCDescriptionFetcher upcDescriptionFetcher;
    
    private ReportTime reportTime;
    
    private DAOFactory dAOFactory;
    private Transaction transaction;
    
    private ComprehensiveDAO comprehensiveDAO;
        
    private Model() {
        
        assert true;
        
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

    public ReportTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(ReportTime reportTime) {
        this.reportTime = reportTime;
    }

    public DAOFactory getDAOFactory() {
        return dAOFactory;
    }

    public void setDAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ComprehensiveDAO getComprehensiveDAO() {
        return comprehensiveDAO;
    }

    public void setComprehensiveDAO(ComprehensiveDAO comprehensiveDAO) {
        this.comprehensiveDAO = comprehensiveDAO;
    }
    
}
