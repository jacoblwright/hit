package data;

/**
 * Provides and interface of methods for obtaining each type of DAO, as well
 * as a Transaction.
 */
public interface DAOFactory {
        
    public ComponentDAO<ContainerDTO> createContainerDAO();
    
    public ComponentDAO<ProductDTO> createProductDAO();
    
    public ComponentDAO<ProductToContainerDTO> createProductToContainerDAO();
    
    public ComponentDAO<ItemDTO> createItemDAO();
    
    public ComponentDAO<ReportTimeDTO> createReportTimeDAO();
    
    public TransactionDAO createTransactionDAO();
    
    public ComprehensiveDAO createComprehensiveDAO();
    
}
