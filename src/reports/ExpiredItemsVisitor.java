package reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Container;
import model.Item;
import model.Model;

public class ExpiredItemsVisitor implements Visitor {
	
	private Iterator<Container> containerTree;
	private List<Record> records;
	private Date now;
	
	public ExpiredItemsVisitor() {
		Set<Container> su = new TreeSet<Container>();
		su.addAll(getModel().getContainerManager().getRoot());
		this.containerTree = new ContainerPreorderIterator(su);
		records = new ArrayList<Record>();
		now = new Date();
	}

    @Override
    public List<Record> visitAll() {
    	while(containerTree.hasNext()) {
    		List<Record> subRecords = checkItemExpiration(containerTree.next());
    		records.addAll(subRecords);
    	}
        return records;
    }
    
    private List<Record> checkItemExpiration(Container container) {
		Collection<Item> itemList = getModel().getItemManager().getItems(container);
		List<ExpiredItemsRecord> subRecords = new ArrayList<ExpiredItemsRecord>();
		for(Item item : itemList) {
			Date d = getDateAtMidnight(item.getExpirationDate());
			Date d1 = getDateAtMidnight(now);
			if(getDateAtMidnight(item.getExpirationDate()).equals(getDateAtMidnight(now)) ||
					getDateAtMidnight(item.getExpirationDate()).before(getDateAtMidnight(now))) {
				ExpiredItemsRecord exprItemRecord = new ExpiredItemsRecord();
				exprItemRecord.initialize(item, getModel());
				subRecords.add(exprItemRecord);
			}
		}
		return sortedSubRecords(subRecords);
	}
    
    private List<Record> sortedSubRecords(List<ExpiredItemsRecord> subRecords) {
    	Collections.sort(subRecords);
		List<Record> result = new ArrayList<Record>();
		result.addAll(subRecords);
		return result;
	}

	private Date getDateAtMidnight( Date date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
    }

	private Model getModel() {
    	return Model.getInstance();
    }

}
