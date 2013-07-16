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
        
        productManager.addNewProduct(product, container);
        
    }
    
    /**
     * Edits a Product by replacing an older Product with a newer Product.
     * @pre same as those for ProductManager.editProduct()
     * @post same as those for ProductManager.editProduct()
     */
    public void editProduct(Product oldProduct, Product newProduct) {
        
        productManager.editProduct(oldProduct, newProduct);
        
    }
    
    /**
     * Moves a Product to a different Container.
     * @param product the Product to be moved
     * @param sourceContainer the source Container
     * @param targetContainer the target Container
     * @pre See pages 23-24 of the specification.
     * @post See pages 23-24 of the specification.
     * @throws IllegalArgumentException
     */
    public void moveProduct(Product product,
            Container sourceContainer, Container targetContainer) {
        
        if (areInSameStorageUnit(sourceContainer, targetContainer)) {
            
            productManager.moveProduct(
                    product, sourceContainer, targetContainer);
            
            moveItems(product, sourceContainer, targetContainer);
            
        }
        else {
            
            StorageUnit targetSU =
                    containerManager.getAncestorStorageUnit(targetContainer);         
            Container containerOfProductInTargetSU =
                    getContainer(product, targetSU);    
            
            if (containerOfProductInTargetSU == null) {
                
                productManager.addProductToContainer(product, targetContainer);
                
            }
            else {
                
                productManager.moveProduct(product,
                        containerOfProductInTargetSU, targetContainer);
                
                moveItems(product,
                        containerOfProductInTargetSU, targetContainer);
                
            }
            
        }
        
    }
    
    public boolean canRemoveProductFromContainer(
            Product product, Container container) {
        
        // Check to see if any items in this container have this product
        // as an attribute
        
        return false;
        
    }
    
    /** Deletes a Product from the set all of products.
     * @pre same as those for ProductManager.deleteProduct()
     * @post same as those for ProductManager.deleteProduct()
     * @throws IllegalArgumentException()
     */
    public void removeProductFromContainer(
            Product product, Container container) {
        
        productManager.removeProductFromContainer(product, container);
        
    }

    public boolean canDeleteProductFromSystem(Product product) {
        
        boolean result = true;
        
        Collection<Item> items = itemManager.getItems();
        for (Item item : items) {
            
            if (item.getProduct().equals(product)) {
                result = false;
            }
            
        }
        
        return result;
        
    }
    
    public void deleteProductFromSystem(Product product) {
        
        
        
    }

    /**
     * Adds an Item to the system.
     * @pre same as those for ItemManager.addItem()
     * @post same as those for ItemManager.addItem()
     * @throws IllegalArgumentException()
     */
     public void addItem(Item itemToAdd) throws IllegalArgumentException {
     
         itemManager.addItem(itemToAdd);
         
     }
    
     /**
      * Edits an Item.
      * @pre same as those for ItemManager.editItem()
      * @post same as those for ItemManager.editItem()
      * @throws IllegalArgumentException() 
      */
     public void editItem(Item oldItem, Item newItem)
             throws IllegalArgumentException {
    
         itemManager.editItem(oldItem, newItem);
         
     }

     /**
      * Moves an Item to a different Container.
      * @param itemToMove the item to move
      * @param targetContainer the Container to which the Item is to be moved
      * @pre See pages 23-24 of the specification.
      * @post See pages 23-24 of the specification.
      * @throws IllegalArgumentException()
      */
     public void moveItem(Item itemToMove, Container targetContainer)
             throws IllegalArgumentException {
     
         assert itemToMove != null;
         assert targetContainer != null;
         
         Product product = itemToMove.getProduct();
         Container sourceContainer = itemToMove.getContainer();
         
         // moveProduct takes care of moving itemToMove if sourceContainer and
         // targetContianer are in the same StorageUnit.
         moveProduct(product, sourceContainer, targetContainer);
         
         if (!areInSameStorageUnit(sourceContainer, targetContainer)) {             
             itemManager.moveItem(itemToMove, targetContainer);             
         }
         
     }
     
     /**
      * Removes the item from all containers but keeps track of it in the item
      * history.
      * @pre same as those for ItemManager.removeItem()
      * @post same as those for ItemManager.removeItem()
      * @throws IllegalArgumentException()
      */
     public void removeItem(Item itemToRemove) throws IllegalArgumentException {
     
         itemManager.removeItem(itemToRemove);
         
     }
     
     private boolean areInSameStorageUnit(Container c1, Container c2) {
         
         assert c1 != null;
         assert c2 != null;
         
         return containerManager.getAncestorStorageUnit(c1).equals(
                 containerManager.getAncestorStorageUnit(c2));
         
     }
     
     /*
      * Returns the Container that contains the specified Product in the
      * specified StorageUnit; returns null if the specified product is not
      * within the specified StorageUnit.
      */
     private Container getContainer(Product product, StorageUnit storageUnit) {
         
         assert product != null;
         assert storageUnit != null;
         
         Set<Container> containersInStorageUnit =
                 containerManager.getDescendents(storageUnit);
         containersInStorageUnit.add(storageUnit);
         
         Set<Container> containersOfProduct = product.getContainers();
         
         Container result = null;
         for (Container containerInStorageUnit : containersInStorageUnit) {      
             if (containersOfProduct.contains(containerInStorageUnit)) {
                 result = containerInStorageUnit;
             }             
         }
         
         return result;
         
     }
     
     private void moveItems(Product product,
             Container sourceContainer, Container targetContainer) {
         
         assert product != null;
         assert sourceContainer != null;
         assert targetContainer != null;
         
         Collection<Item> items =
                 itemManager.getItems(sourceContainer, product);
         
         for (Item item : items) {
             
             itemManager.moveItem(item, targetContainer);
             
         }
         
     }

}
