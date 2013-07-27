package reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Container;
import model.Model;
import model.Product;
import model.ProductGroup;

public class NoticesVisitor implements Visitor {

	private Iterator<Container> containerTree;
	private List<Record> records;
	
	public NoticesVisitor() {
		Set<Container> su = new TreeSet<Container>();
		su.addAll(getModel().getContainerManager().getRoot());
		containerTree = new ContainerPreorderIterator(su);
	}
	
    @Override
    public List<Record> visitAll() {
    	records = new ArrayList<Record>();
    	while(containerTree.hasNext()) {
        	Record subRecord = getNotices(containerTree.next()); 
        	if(subRecord != null) {
        		records.add(subRecord);
        	}
        }
    	return records;
    }
    
    private Record getNotices(Container container) {
    	if(container.getContainer() != null &&
				!((ProductGroup) container).getThreeMonthSupply().isUnspecified() &&
				!((ProductGroup) container).getThreeMonthSupply().isCount()) {
			return checkForNotices(
					container, ((ProductGroup) container).getThreeMonthSupply().isVolume());
		}
		return null;
	}

	private Record checkForNotices(Container container, boolean volume) {
		List<Product> mismatchedProducts = new ArrayList<Product>();
		Collection<Product> products = getModel().getProductManager().getProducts(container);
		for( Product product : products) {
			System.out.println("DESCRIPTION " + product.getDescription());
			if((product.getSize().isVolume() != volume) || product.getSize().isCount()) {
				System.out.println("MISMATCHED: DESCRIPTION " + product.getDescription());
				mismatchedProducts.add(product);
			}
		}
		if(!mismatchedProducts.isEmpty()) {
			NoticesRecord record = new NoticesRecord();
			record.setProductGroup((ProductGroup) container);
			record.setThreeMonthSupplyQuantity(((ProductGroup) container).getThreeMonthSupply());
			record.setProductsWithQuantityMismatch(mismatchedProducts);
			return record;
		}
		return null;
	}

	private Model getModel() {
    	return Model.getInstance();
    }

}
