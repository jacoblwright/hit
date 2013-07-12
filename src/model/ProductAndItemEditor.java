package model;

import java.util.*;

public class ProductAndItemEditor {
    
    private ContainerManager containerManager;
    private ProductManager productManager;
    private ItemManager itemManager;
    
    /**
     * Constructs the editor with the specified managers.
     * @param productManager the ProductManager that this editor will use
     * @param itemManager the ItemManager that this editor will use
     * @pre productManager and itemManager must not be null.
     * @post This editor has been initialized using the specified managers.
     */
    public ProductAndItemEditor(ContainerManager containerManager,
            ProductManager productManager,
            ItemManager itemManager) {
        
        assert containerManager != null;
        assert productManager != null;
        assert itemManager != null;
        
        this.containerManager = containerManager;
        this.productManager = productManager;
        this.itemManager = itemManager;
        
    }
    
    /**
     * Adds a new product to the system.
     * @pre same as those for ProductManager.addNewProduct()
     * @post same as those for ProductManager.addNewProduct()
     */
    public void addNewProduct(Product product, Container container) {
        
        StorageUnit storageUnit =
                containerManager.getAncestorStorageUnit(container);
        
        productManager.addNewProduct(product, container, storageUnit);
        
    }
    
    /*
     * Adds the specified Product to the specified Container.
     * @pre same as those for ProductManager.addProductToContainer()
     * @post same as those for ProductManager.addProductToContainer()
     
    public void addProductToContainer(Product product, Container container) {
        
        StorageUnit storageUnit =
                containerManager.getAncestorStorageUnit(container);
        
        productManager.addProductToContainer(product, container, storageUnit);
        
    }
    */
    
    /**
     * Edits a Product by replacing an older Product with a newer Product.
     * @pre same as those for ProductManager.editProduct()
     * @post same as those for ProductManager.editProduct()
     */
    public void editProduct(Product oldProduct, Product newProduct) {
        
        productManager.editProduct(oldProduct, newProduct);
        
    }
    
    /**
     * Moves a product to a new container as long as that product is not
     * already located in that storage unit.
     * @pre product is not already in the container it is being moved into.
     * @post product is moved from the source container to the target
     * container, and all of the Item of that Product in the source container
     * are moved to the target container.
     * @param product the Product to be moved
     * @param source the source Container
     * @param target the target Container
     * @throws IllegalArgumentException
     */
    public void moveProduct(Product product,
            Container source, Container target) {
        
        StorageUnit sourceSU = containerManager.getAncestorStorageUnit(source);
        StorageUnit targetSU = containerManager.getAncestorStorageUnit(target);
        
        productManager.moveProduct(
                product, source, target, sourceSU, targetSU);
        
        Collection<Item> items = itemManager.getItems(source, product);
        for (Item item : items) {
            
            itemManager.moveItem(item, target);
            
        }
        
    }
    
    public boolean canDeleteProduct(Product product) {
        
        // Check to see if any items in this container have this product
        // as an attribute
        
        return false;
        
    }
    
    /** Deletes a Product from the set all of products.
     * @pre same as those for ProductManager.deleteProduct()
     * @post same as those for ProductManager.deleteProduct()
     * @throws IllegalArgumentException()
     */
    public void deleteProduct(Product product, Container container) {
        
        productManager.deleteProduct(product, container);
        
    }

    /**
     * Adds an Item to the system.
     * @pre same as those for ItemManager.addItem()
     * @post same as those for ItemManager.addItem()
     * @throws IllegalArgumentException()
     */
     public void addItem(Item itemToAdd) {
     
         itemManager.addItem(itemToAdd);
         
     }
    
     /**
      * Edits an Item.
      * @pre same as those for ItemManager.editItem()
      * @post same as those for ItemManager.editItem()
      * @throws IllegalArgumentException() 
      */
     public void editItem(Item oldItem, Item newItem) {
    
         itemManager.editItem(oldItem, newItem);
         
     }

     /**
      * Moves and Item.
      * @pre same as those for ItemManager.moveItem()
      * @post same as those for ItemManager.moveItem()
      * @throws IllegalArgumentException()
      */
     public void moveItem(Item itemToMove, Container target) {
     
         itemManager.moveItem(itemToMove, target);
         
     }
     
     /**
      * Removes the item from all containers but keeps track of it in the item
      * history.
      * @pre same as those for ItemManager.removeItem()
      * @post same as those for ItemManager.removeItem()
      * @throws IllegalArgumentException()
      */
     public void removeItem(Item itemToRemove) {
     
         itemManager.removeItem(itemToRemove);
         
     }

}
