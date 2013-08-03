package data;

/**
 * Provides and interface of methods for obtaining each type of DAO, as well
 * as a Transaction.
 */
public interface DAOFactory {
        
    public void createContainerDAO();
    
    public void createProductDAO();
    
    public void createProductToContainerDAO();
    
    public void createItemDAO();
    
    public void createTransactionDAO();
    
    public void createComprehensiveDAO();
    
}
