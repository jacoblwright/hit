package data;

/**
 * Instantiates and returns database implementations of the various DAOs.
 */
public class DBDAOFactory implements DAOFactory {

    @Override
    public ComponentDAO<ContainerDTO> createContainerDAO() {
        return null;
    }

    @Override
    public ComponentDAO<ProductDTO> createProductDAO() {
        return null;
    }

    @Override
    public ComponentDAO<ProductToContainerDTO> createProductToContainerDAO() {
        return null;
    }

    @Override
    public ComponentDAO<ItemDTO> createItemDAO() {
        return null;
    }

    @Override
    public TransactionDAO createTransactionDAO() {
        return null;
    }

    @Override
    public ComprehensiveDAO createComprehensiveDAO() {
        return null;
    }

    

}
