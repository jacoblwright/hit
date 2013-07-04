package model;

public class ProductAndItemManager {
    
    /*
    + addItem(Item): void
    + removeItem(Item): void
    + moveItem(Item, Container): void
    + editItem(Item, Item): void
    */
    
    /** Adds a product to the system
     * @pre     product must not already exist in the set
     * @post    product is added to the set
     */
    public void addProduct(Product product) {}
    
    /** Deletes a Product from the set all of products.
     * @pre     product must exist in the set
     * @pre     product must not have any Items attached to it
     * @post    removes the Product from the set
     */
    public void deleteProduct(Product product) {}
    
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

}
