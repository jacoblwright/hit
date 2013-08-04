package data;

import model.*;
import java.util.*;

/**
 * Serialization implementation of ComponentDAO for Container.
 */
public class SerContainerDAO implements ComponentDAO<ContainerDTO> {

    @Override
    public void create(ContainerDTO e) {
    	return;
    }

    @Override
    public Collection<ContainerDTO> readAll() {
        return new ArrayList<ContainerDTO>( 1 );
    }

    @Override
    public void update(ContainerDTO e) {
    	return;
    }

    @Override
    public void delete(ContainerDTO e) {
    	return;
    }

}
