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
    public void createProductDAO() {
    }

    @Override
    public void createProductToContainerDAO() {
    }

    @Override
    public void createItemDAO() {
    }

    @Override
    public void createTransactionDAO() {
    }

    @Override
    public void createComprehensiveDAO() {
    }

}
