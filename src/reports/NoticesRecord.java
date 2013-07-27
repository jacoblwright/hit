package reports;

import java.util.*;
import model.*;

public class NoticesRecord implements Record {
    
    private ProductGroup productGroup;
    private Quantity threeMonthSupplyQuantity;
    private List<Product> productsWithQuantityMismatch;

    @Override
    public List<String> getValuesAsStrings() {
 
        List<String> lines = new LinkedList<String>();
        String line;
        
        StorageUnit storageUnit = Model.getInstance().getContainerManager().
                getAncestorStorageUnit(productGroup);
        
        line = "Product group " + productGroup.getName() + " " +
                "in storage unit " + storageUnit.getName() + " " +
                "has a three-month supply (" +
                productGroup.getThreeMonthSupply().getQuantityString() + ") " +
                "that is inconsistent with the following products:";
        lines.add(line);
        
        for (Product product : productsWithQuantityMismatch) {
            
            line = "- Product " + product.getDescription() + " " +
                    "(size: " + product.getSize().getQuantityString() + ") " +
                    "in product group " +
                    Model.getInstance().getProductAndItemEditor().
                    getContainer(product, storageUnit).getName();
            lines.add(line);
            
        }
                
        return lines;
        
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public Quantity getThreeMonthSupplyQuantity() {
        return threeMonthSupplyQuantity;
    }

    public void setThreeMonthSupplyQuantity(Quantity threeMonthSupplyQuantity) {
        this.threeMonthSupplyQuantity = threeMonthSupplyQuantity;
    }

    public List<Product> getProductsWithQuantityMismatch() {
        return productsWithQuantityMismatch;
    }

    public void setProductsWithQuantityMismatch(
            List<Product> productsWithQuantityMismatch) {
        this.productsWithQuantityMismatch = productsWithQuantityMismatch;
    }
    
    public void addProductsWithQuantityMismatch(Product mismatchProduct) {
    	if(productsWithQuantityMismatch == null) {
    		productsWithQuantityMismatch = new ArrayList<Product>();
    	}
    	productsWithQuantityMismatch.add(mismatchProduct);
    }

}
