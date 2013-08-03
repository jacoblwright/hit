package model;

import reports.*;
import data.*;
import plugins.*;
import java.io.*;

public class Model {
    
    private static Model instance = null;
    
    private ContainerEditor containerEditor;
    private ContainerManager containerManager;
    
    private ProductAndItemEditor productAndItemEditor;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    private ReportTime reportTime;
    
    private DAOFactory dAOFactory;
    private TransactionDAO transaction;
    
    private ComprehensiveDAO comprehensiveDAO;
    
    private UPCDescriptionFetcher upcDescriptionFetcher;
    
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
    
    public void initialize() throws IOException {
        
        if (dAOFactory == null) {
            throw new IllegalStateException("DAOFactory has not been set.");
        }
        
        transaction = dAOFactory.createTransactionDAO();
        comprehensiveDAO = dAOFactory.createComprehensiveDAO();
        
        comprehensiveDAO.load();
        
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

    public TransactionDAO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDAO transaction) {
        this.transaction = transaction;
    }

    public ComprehensiveDAO getComprehensiveDAO() {
        return comprehensiveDAO;
    }

    public void setComprehensiveDAO(ComprehensiveDAO comprehensiveDAO) {
        this.comprehensiveDAO = comprehensiveDAO;
    }
    
}
