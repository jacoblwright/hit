package data;

/**
 * Instantiates and returns database implementations of the various DAOs.
 */
public class DBDAOFactory implements DAOFactory {

    @Override
    public ComponentDAO<ContainerDTO> createContainerDAO() {
    	return new DBContainerDAO();
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
