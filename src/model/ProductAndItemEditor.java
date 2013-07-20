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
        
        //System.out.println("PAIE:moveProduct");
        
        //System.out.println("areInSameStorageUnit:" +
                //areInSameStorageUnit(sourceContainer, targetContainer));
        
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
    
    public void moveProductWhenTreeRootIsSelected(
            Product product, Container targetContainer) {
        
        StorageUnit suOftargetContainer =
                containerManager.getAncestorStorageUnit(targetContainer);
        
        if (getContainer(product, suOftargetContainer) == null) {
            
            // product is not already within targetContainer's StorageUnit.            
            System.out.println("PAIE.moveProductWhenTreeRootIsSelected(): " +
            		"branch where product is not already within " +
            		"targetContainer's StorageUnit");
            
            productManager.addProductToContainer(product, targetContainer);            
            
        }
        else {
            
            // product is already within targetContainer's StorageUnit.
            System.out.println("PAIE.moveProductWhenTreeRootIsSelected(): " +
                    "branch where product is already within " +
                    "targetContainer's StorageUnit");
            
            Container sourceContainer =
                    getContainer(product, suOftargetContainer);
            
            moveProduct(product, sourceContainer, targetContainer);
            
        }
        
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
     * @return true if the specified Product has no associated Items in the
     * specified Container; false otherwise.
     */
    public boolean canRemoveProductFromContainer(
            Product product, Container container) {
        
        return itemManager.getItems(container, product).isEmpty();
        
    }
    
    /**
     * Removes the specified Product from the specified Container.
     * @param product the Product to be removed
     * @param container the Container from which product is to be removed
     * @pre The specified Product has no associated Items in the specified
     * Container.
     * @post The specified Product is removed from the specified Container.
     * @throws IllegalArgumentException
     */
    public void removeProductFromContainer(
            Product product, Container container) {
        
        productManager.removeProductFromContainer(product, container);
        
    }

    /**
     * @return true if the specified Product has no associated Items in the
     * system; false otherwise.
     */
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
    
    /**
     * Deletes the specified Product from the system completely.   
     * @param product the Product to be deleted
     * @pre The specified Product has no associated Items in the system.
     * @post The specified Product is deleted from the system.
     */
    public void deleteProductFromSystem(Product product) {
        
        productManager.deleteProductFromSystem(product);
        
    }

    /**
     * Adds the specified Item to the specified StorageUnit.
     * @pre item and storageUnit are not null.
     * @post If item's product already exists within storageUnit, item is added
     * to the Container in which item's product exists. If item's product does
     * not exist within storageUnit, item is added to the top level in
     * storageUnit.
     * @throws IllegalArgumentException
     */
     public void addItemToStorageUnit(Item item, StorageUnit storageUnit)
             throws IllegalArgumentException {
     
         if (item == null || storageUnit == null) {
             throw new IllegalArgumentException();
         }
         
         Container containerOfProductInSU =
                 getContainer(item.getProduct(), storageUnit);
         
         if (containerOfProductInSU == null) {   
             
             productManager.addProductToContainer(
                     item.getProduct(), storageUnit);
             
             item.setContainer(storageUnit);           
         
         }
         else {
             item.setContainer(containerOfProductInSU);
         }
         
         itemManager.addItem(item);
         
     }
     
     /**
      * Transfers the specified Item to the specified StorageUnit.
      * @pre item and storageUnit are not null.
      * @post If item's product already exists within storageUnit, item is added
      * to the Container in which item's product exists. If item's product does
      * not exist within storageUnit, item is added to the top level in
      * storageUnit. item is removed from its previous container.
      * @throws IllegalArgumentException
      */
     public void transferItemToStorageUnit(Item item, StorageUnit storageUnit)
             throws IllegalArgumentException {
     
         if (item == null || storageUnit == null) {
             throw new IllegalArgumentException();
         }
         
         Container containerOfProductInSU =
                 getContainer(item.getProduct(), storageUnit);
         //System.out.println("containerOfProductInSU:" + containerOfProductInSU);
         
         if (containerOfProductInSU == null) {   
             
             productManager.addProductToContainer(
                     item.getProduct(), storageUnit);
             
             itemManager.moveItem(item, storageUnit);   
             
         }
         else {
             itemManager.moveItem(item, containerOfProductInSU);
         }
         
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
     private boolean productExistsWithinSUOfContainer(
             Product product, Container container) {
         
         assert product != null;
         assert container != null;
         
         boolean result = false;
         
         Collection<Container> containersOfProduct = product.getContainers();
         for (Container containerOfProduct : containersOfProduct) {
             if (areInSameStorageUnit(containerOfProduct, container)) {
                 result = true;
             }
         }
         
         return result;
         
     }
     */
     
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
         
         System.out.println("--------");
         System.out.println("Collection 1: the set of Containers in this " +
         		"SU, including the SU itself:");
         System.out.println(containersInStorageUnit);
         
         Set<Container> containersOfProduct = product.getContainers();
         
         System.out.println("Collection 2: this Item's Product's set of " +
         		"Containers:");
         System.out.println(containersOfProduct);
         
         Container result = null;
         for (Container containerInStorageUnit : containersInStorageUnit) {
             
             System.out.print("Does Collection 2 contain " +
                     containerInStorageUnit + "? ");
             System.out.println(
                     containersOfProduct.contains(containerInStorageUnit));
             
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
