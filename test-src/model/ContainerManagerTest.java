package model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;

public class ContainerManagerTest {

	private ContainerManager containerManager;
	private Map<Integer, Container> containersById;
	private int uniqueId;
	private Set<StorageUnit> storageUnits;
	
	@Before
	public void setUp() {
		containerManager = new ContainerManager();
		containersById = new HashMap<Integer, Container>();
		uniqueId = 1;
		
		//////////////////////////////////////////////////////////////
		//				Setup StorageUnit 1
		//////////////////////////////////////////////////////////////
		StorageUnit su1 = initializeStorageUnit( "su1", uniqueId++ );
		
		ProductGroup productGroup1_1 = initializeProductGroup( "pg1_1", uniqueId++, su1 );
		ProductGroup productGroup1_2 = initializeProductGroup( "pg1_2", uniqueId++, su1 );
		
		ProductGroup childProductGroup1_1 = initializeProductGroup( "cpg1_1", uniqueId++, productGroup1_1 );
		ProductGroup childProductGroup1_2 = initializeProductGroup( "cpg1_2", uniqueId++, productGroup1_1 );
		
		ProductGroup grandChildProductGroup1_1 = initializeProductGroup( "gcpg1_1", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_2 = initializeProductGroup( "gcpg1_2", uniqueId++, childProductGroup1_2 );
		ProductGroup grandChildProductGroup1_3 = initializeProductGroup( "gcpg1_3", uniqueId++, childProductGroup1_2 );
		
		//Parent is su1
		su1.addProductGroup( productGroup1_1 );
		su1.addProductGroup( productGroup1_2 );
		
		//Parent is productGroup1_1
		productGroup1_1.addProductGroup( childProductGroup1_1 );
		productGroup1_1.addProductGroup( childProductGroup1_2 );
		
		//Parent is childProductGroup1_2
		productGroup1_2.addProductGroup( grandChildProductGroup1_1 );
		productGroup1_2.addProductGroup( grandChildProductGroup1_2 );
		productGroup1_2.addProductGroup( grandChildProductGroup1_3 );
		
		
		//////////////////////////////////////////////////////////////
		//				Setup StorageUnit 2
		//////////////////////////////////////////////////////////////
		StorageUnit su2 = initializeStorageUnit( "su2", uniqueId++ );
		
		ProductGroup productGroup2_1 = initializeProductGroup( "pg2_1", uniqueId++, su2 );
		ProductGroup productGroup2_2 = initializeProductGroup( "pg2_2", uniqueId++, su2 );
		
		ProductGroup childProductGroup2_1 = initializeProductGroup( "cpg2_1", uniqueId++, productGroup2_1 );
		ProductGroup childProductGroup2_2 = initializeProductGroup( "cpg2_2", uniqueId++, productGroup2_1 );
		
		ProductGroup grandChildProductGroup2_1 = initializeProductGroup( "gcpg2_1", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_2 = initializeProductGroup( "gcpg2_2", uniqueId++, childProductGroup2_1 );
		ProductGroup grandChildProductGroup2_3 = initializeProductGroup( "gcpg2_3", uniqueId++, childProductGroup2_1 );
		
		//Parent is su1
		su2.addProductGroup( productGroup2_1 );
		su2.addProductGroup( productGroup2_2 );
		
		//Parent is productGroup1_1
		productGroup2_1.addProductGroup( childProductGroup2_1 );
		productGroup2_1.addProductGroup( childProductGroup2_2 );
		
		//Parent is childProductGroup1_2
		productGroup2_1.addProductGroup( grandChildProductGroup2_1 );
		productGroup2_1.addProductGroup( grandChildProductGroup2_2 );
		productGroup2_1.addProductGroup( grandChildProductGroup2_3 );
		
		/////////////////////////////////////////////////////////////
		//				Setup Tree
		/////////////////////////////////////////////////////////////
		
		storageUnits = new TreeSet<StorageUnit>();
		storageUnits.add( su1 );
		storageUnits.add( su2 );
		containerManager.setStorageUnits( storageUnits );
		
		containerManager.setContainersById( containersById );
		
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
	public void getRootTest() {
		//Tests under Normal conditions
		assertEquals( containerManager.getRoot(), storageUnits );
		
		//Tests for if StorageUnits == null
		containerManager.setStorageUnits( null );
		assertNotNull( containerManager.getRoot() );
	}
	
	@Test
	public void getAncestorStorageUnitTest() {
		
	}
	
	@Test
	public void addContainerTest() {
		
	}
	
	@Test 
	public void editContainerTest() {
		
	}

	@Test
	public void deleteContainerTest() {
		
	}
	
	@Test
	public void canAddAndEditContainerTest() {
		
	}
	
}
