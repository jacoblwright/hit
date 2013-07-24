package reports;

import java.util.*;
import model.*;

public class NoticesRecord implements Record {
    
    private Container productGroup;
    private Quantity threeMonthSupplyQuantity;
    private List<Product> productsWithQuantityMismatch;

    @Override
    public List<String> getValuesAsStrings() {
        
        // In progress --Evan
        
        /*
        List<String> lines = new LinkedList<String>();
        
        String line = "";
        line += "Product group " + productGroup.getName() + ": ";
        
        
        return lines;
        */
        
        return null;
        
    }

    public Container getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(Container productGroup) {
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

}
