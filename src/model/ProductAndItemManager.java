package model;

import java.util.Date;

public class ProductAndItemManager {
    
    /** Adds a product to the system
     * @pre     product must not already exist in the set
     * @post    product is added to the set
     */
    public void addProduct(Product product) {}
    
    /** Edits a Product by updating by replacing an older Product with a newer Product.
     * @pre     oldProduct must exist in the set of products
     * @post    replaces the oldProduct with the new Product
     */
    public void editProduct(Product newProduct, Product oldProduct) {}
    
    /** Moves a product to a new container as long as that product is not already located in that storage unit.
     * @pre         product is not already in the container it is being moved into
     * @param       product the product being moved
     * @param       before  the container before the product was moved
     * @param       after   the new container that the product is being move to
     */
    public void moveProduct(Product product, Container before, Container after) {}
    
    /** Deletes a Product from the set all of products.
     * @pre     product must exist in the set
     * @pre     product must not have any Items attached to it
     * @post    removes the Product from the set
     */
    public void deleteProduct(Product product) {}

    /**
     * 
     * @pre canAddItem() == true
     * @post itemToAdd.container = container, itemToAdd.prodcut = product, itemToAdd.expirationDate = expirationDate
     * 
     * @throws IllegalArgumentException if !canAddItem()
     */
     public void addItem(Item itemToAdd, Date expirationDate) throws IllegalArgumentException {
     
     }
    
    /**
      * 
      * @param oldItem - item before edit
      * @param newItem - item after edit
      * 
      * @throws IllegalArgumentException
      */
     public void editItem(Item oldItem, Item newItem) throws IllegalArgumentException {
    
     }

    /**
     * Updates indexes for the move.
     * 
     * @pre itemToMove.product exists (only) in the target container
     * @post itemToMove.container = target, itemToMove.storageUnit = target's storage unit
     * 
     * @throws IllegalStateException if pre-conditions are not met
     * @throws IllegalArgumentExcpetion if itemToMove is bad
     */
     public void moveItem(Item itemToMove, Container target) throws IllegalStateException, IllegalArgumentException {
     
     }
     
     /** Removes the item from any container but keeps track of it in the item history.
      * 
      * @param itemToRemove - item to be removed
      * 
      * @pre itemToRemove.container != null, !removedItems.contains(itemToMove), itemToMove.exitTime == null
      * @post captures and sets itemToRemove.exitTime, sets itemToRemove.container to null, adds to removedItems, updates indexItemsByRemovalDate
      * 
      * @throws IllegalStateException if preconditions are not met
      * @throws IllegalArgumentException if itemToRemove is bad
      * 
      */
     public void removeItem(Item itemToRemove) throws IllegalStateException, IllegalArgumentException {
     
     }

}
