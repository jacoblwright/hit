package data;

/**
 * Instantiates and returns serialization implementations of the various DAO.
 */
public class SerDAOFactory implements DAOFactory {

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
