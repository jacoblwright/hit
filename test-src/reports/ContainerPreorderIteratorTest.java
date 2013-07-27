package reports;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import model.Container;
import model.ContainerManager;
import model.ProductGroup;
import model.StorageUnit;

import org.junit.Before;
import org.junit.Test;

public class ContainerPreorderIteratorTest {
	private ContainerManager containerManager;
	private int uniqueId;
	private Set<StorageUnit> storageUnits;
	private StorageUnit su2;
	private StorageUnit su1;
	
	@Before
	public void setUp() {
		
		containerManager = new ContainerManager();
		storageUnits = new TreeSet<StorageUnit>();
		uniqueId = 1;
		
		setupStorageUnit1();
		setupStorageUnit2();
		
		storageUnits.add( su1 );
		storageUnits.add( su2 );
		containerManager.setStorageUnits( storageUnits );
		
	}
	
	private void setupStorageUnit2() {
		su2 = initializeStorageUnit( "B", uniqueId++ );
		
		ProductGroup productGroup2_1 = initializeProductGroup( "B_a", uniqueId++, su2 );
		ProductGroup productGroup2_2 = initializeProductGroup( "B_b", uniqueId++, su2 );
		
		ProductGroup childProductGroup2_1 = initializeProductGroup( "B_a_a", uniqueId++, productGroup2_1 );
		ProductGroup childProductGroup2_2 = initializeProductGroup( "B_a_b", uniqueId++, productGroup2_1 );
		
		ProductGroup grandChildProductGroup2_1 = initializeProductGroup( "B_a_a_a", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_2 = initializeProductGroup( "B_a_a_b", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_3 = initializeProductGroup( "B_a_a_c", uniqueId++, childProductGroup2_1 );
		
		//Parent is su1
		su2.addProductGroup( productGroup2_2 );
		su2.addProductGroup( productGroup2_1 );
		
		//Parent is productGroup1_1
		productGroup2_1.addProductGroup( childProductGroup2_1 );
		productGroup2_1.addProductGroup( childProductGroup2_2 );
		
		//Parent is childProductGroup1_2
		childProductGroup2_1.addProductGroup( grandChildProductGroup2_3 );
		childProductGroup2_1.addProductGroup( grandChildProductGroup2_1 );
		childProductGroup2_1.addProductGroup( grandChildProductGroup2_2 );
		
	}

	private void setupStorageUnit1() {
		su1 = initializeStorageUnit( "A", uniqueId++ );
		
		ProductGroup productGroup1_1 = initializeProductGroup( "A_a", uniqueId++, su1 );
		ProductGroup productGroup1_2 = initializeProductGroup( "A_b", uniqueId++, su1 );
		
		ProductGroup childProductGroup1_1 = initializeProductGroup( "A_a_a", uniqueId++, productGroup1_1 );
		ProductGroup childProductGroup1_2 = initializeProductGroup( "A_a_b", uniqueId++, productGroup1_1 );
		
		ProductGroup grandChildProductGroup1_1 = initializeProductGroup( "A_a_b_a", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_2 = initializeProductGroup( "A_a_b_b", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_3 = initializeProductGroup( "A_a_b_c", uniqueId++, childProductGroup1_2 );
		
		//Parent is su1
		su1.addProductGroup( productGroup1_2 );
		su1.addProductGroup( productGroup1_1 );
		
		//Parent is productGroup1_1
		productGroup1_1.addProductGroup( childProductGroup1_1 );
		productGroup1_1.addProductGroup( childProductGroup1_2 );
		
		//Parent is childProductGroup1_2
		childProductGroup1_2.addProductGroup( grandChildProductGroup1_2 );
		childProductGroup1_2.addProductGroup( grandChildProductGroup1_1 );
		childProductGroup1_2.addProductGroup( grandChildProductGroup1_3 );	
	}

	private StorageUnit initializeStorageUnit( String name, int id ) {
		StorageUnit su = new StorageUnit();
		su.setName( name );
		su.setId( id );
		su.setContainer( null );
		return su;
	}
	
	private ProductGroup initializeProductGroup( String name, int id, Container container ) {
		ProductGroup pg = new ProductGroup();
		pg.setName( name );
		pg.setId( id );
		pg.setContainer( container );
		return pg;
	}
	
	@Test
	public void test() {
		Set<Container> su = new TreeSet<Container>();
		su.addAll(storageUnits);
		Iterator<Container> cpt = new ContainerPreorderIterator(su);
		String results = "";
		while(cpt.hasNext()) {
			String name = cpt.next().getName();
			results += " " + name;
			System.out.println(name);
		}
		String expected = " A A_a A_a_a A_a_b A_a_b_a A_a_b_b A_a_b_c A_b" +
					" B B_a B_a_a B_a_a_a B_a_a_b B_a_a_c B_a_b B_b";
		assertEquals(results, expected);
	}

}
