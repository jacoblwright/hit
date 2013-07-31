package dao;

public class DAOFactory {
    
    private static DAOType dAOType;
    
    public static ContainerDAO createContainerDAO() {
        
        switch (dAOType) {
            
            case SERIALIZATION:
                return new ContainerSerDAO();
            case DATABASE:
                return new ContainerDBDAO();
            default:
                return null;
                
        }
        
    }

    public static void setDaoType(DAOType dAOType) {
        DAOFactory.dAOType = dAOType; 
    }
    
}
