package data;

/**
 * Instantiates and returns serialization implementations of the various DAO.
 */
public class SerDAOFactory implements DAOFactory {

    @Override
    public ComponentDAO<ContainerDTO> createContainerDAO() {
        return new SerContainerDAO();
    }

    @Override
    public ComponentDAO<ProductDTO> createProductDAO() {
        return new SerProductDAO();
    }

    @Override
    public ComponentDAO<ProductToContainerDTO> createProductToContainerDAO() {
        return new SerProductToContainerDAO();
    }

    @Override
    public ComponentDAO<ItemDTO> createItemDAO() {
        return new SerItemDAO();
    }

    @Override
    public TransactionDAO createTransactionDAO() {
        return new SerTransactionDAO();
    }

    @Override
    public ComprehensiveDAO createComprehensiveDAO() {
        return new SerComprehensiveDAO();
    }

}
