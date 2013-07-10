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
     * @pre same as those for ProductManager.addProduct()
     * @post same as those for ProductManager.addProduct()
     */
    public void addProduct(Product product) {
        
//        productManager.addProduct(product);
        
    }
    
    /**
     * Edits a Product by replacing an older Product with a newer Product.
     * @pre same as those for ProductManager.editProduct()
     * @post same as those for ProductManager.editProduct()
     */
    public void editProduct(Product oldProduct, Product newProduct) {
        
        //productManager.editProduct(oldProduct, newProduct);
        
    }
    
    /**
     * Moves a product to a new container as long as that product is not
     * already located in that storage unit.
     * @pre product is not already in the container it is being moved into.
     * @post product is moved from the source container to the target
     * container, and all of the Item of that Product in the source container
     * are moved to the target container.
     * @param product the Product to be moved
     * @param before the source Container
     * @param after the target Container
     * @throws IllegalArgumentException
     */
    public void moveProduct(Product product,
            Container before, Container after) {
        
//        productManager.moveProduct(product, before, after);
        
        Collection<Item> items = itemManager.getItems(after, product);
        for (Item item : items) {
            
            itemManager.moveItem(item, after);
            
        }
        
    }
    
    /** Deletes a Product from the set all of products.
     * @pre same as those for ProductManager.deleteProduct()
     * @post same as those for ProductManager.deleteProduct()
     * @throws IllegalArgumentException()
     */
    public void deleteProduct(Product product) {
        
//        productManager.deleteProduct(product);
        
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
