package model;

import java.util.*;

public class ProductAndItemEditor {
    
    private ProductManager productManager;
    private ItemManager itemManager;
    
    /**
     * Constructs the editor with the specified managers.
     * @param productManager the ProductManager that this editor will use
     * @param itemManager the ItemManager that this editor will use
     * @pre productManager and itemManager must not be null.
     * @post This editor has been initialized using the specified managers.
     */
    public ProductAndItemEditor(ProductManager productManager,
            ItemManager itemManager) {
        
        assert productManager != null;
        assert itemManager != null;
        
        this.productManager = productManager;
        this.itemManager = itemManager;
        
    }
    
    /**
     * Adds a product to the system.
     * @param product the product to be added
     * @pre product must not already exist in the system.
     * @post product has been added to the system.
     */
    public void addProduct(Product product) {
        
        productManager.addProduct(product);
        
    }
    
    /**
     * Edits a Product by replacing an older Product with a newer Product.
     * @pre oldProduct must exist in the system.
     * @post oldProduct has been replaced with the newProduct.
     */
    public void editProduct(Product oldProduct, Product newProduct) {
        
        productManager.editProduct(oldProduct, newProduct);
        
    }
    
    /**
     * Moves a product to a new container as long as that product is not
     * already located in that storage unit.
     * @pre product is not already in the container it is being moved into.
     * @param product the product being moved
     * @param before the container before the product was moved
     * @param after the new container that the product is being moved to
     */
    public void moveProduct(Product product,
            Container before, Container after) {
        
        productManager.moveProduct(product, before, after);
        
    }
    
    /** Deletes a Product from the set all of products.
     * @pre product must exist in the set
     * @pre product must not have any Items attached to it
     * @post removes the Product from the set
     */
    public void deleteProduct(Product product) {
        
        productManager.deleteProduct(product);
        
    }

    /**
     * @pre canAddItem() == true
     * @post itemToAdd.container = container, itemToAdd.prodcut = product,
     * itemToAdd.expirationDate = expirationDate
     */
     public void addItem(Item itemToAdd, Date expirationDate) {
     
         itemManager.addItem(itemToAdd, expirationDate);
         
     }
    
    /**
      * @param oldItem - item before edit
      * @param newItem - item after edit 
      */
     public void editItem(Item oldItem, Item newItem) {
    
         itemManager.editItem(oldItem, newItem);
         
     }

    /**
     * Updates indexes for the move.
     * @pre itemToMove.product exists (only) in the target container
     * @post itemToMove.container = target, itemToMove.storageUnit = target's storage unit
     */
     public void moveItem(Item itemToMove, Container target) {
     
         itemManager.moveItem(itemToMove, target);
         
     }
     
     /**
      * Removes the item from any container but keeps track of it in the item
      * history.
      * @param itemToRemove - item to be removed.
      * @pre itemToRemove.container != null,
      * !removedItems.contains(itemToMove), itemToMove.exitTime == null
      * @post captures and sets itemToRemove.exitTime,
      * sets itemToRemove.container to null, adds to removedItems,
      * updates indexItemsByRemovalDate
      */
     public void removeItem(Item itemToRemove) {
     
         itemManager.removeItem(itemToRemove);
         
     }

}
