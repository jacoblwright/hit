package data;

import java.util.Collection;

/**
 * Serialization implementation of ComponentDAO for Product to Container
 * mapping.
 */
public class SerProductToContainerDAO
        implements ComponentDAO<ProductToContainerDTO> {

    @Override
    public void create(ProductToContainerDTO e) {
    }

    @Override
    public Collection<ProductToContainerDTO> readAll() {
        return null;
    }

    @Override
    public void update(ProductToContainerDTO e) {
    }

    @Override
    public void delete(ProductToContainerDTO e) {
    }
    
}
