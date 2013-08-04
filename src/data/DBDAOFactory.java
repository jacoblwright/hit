package data;

import java.io.IOException;

/**
 * Instantiates and returns database implementations of the various DAOs.
 */
public class DBDAOFactory implements DAOFactory {

    @Override
    public ComponentDAO<ContainerDTO> createContainerDAO() {
        return new DBContainerDAO();
    }

    @Override
    public ComponentDAO<ProductDTO> createProductDAO() {
        return new DBProductDAO();
    }

    @Override
    public ComponentDAO<ProductToContainerDTO> createProductToContainerDAO() {
        return new DBProductToContainerDAO();
    }

    @Override
    public ComponentDAO<ItemDTO> createItemDAO() {
        return new DBItemDAO();
    }

    @Override
    public TransactionDAO createTransactionDAO() {
        
        try {
            return new DBTransactionDAO();
        }
        catch (IOException e) {
            
            e.printStackTrace();
            return null;
            
        }
    
    }

    @Override
    public ComprehensiveDAO createComprehensiveDAO() {
        return new DBComprehensiveDAO();
    }

}
