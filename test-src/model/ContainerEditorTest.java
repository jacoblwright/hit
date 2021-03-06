package model;

import static org.junit.Assert.*;

import gui.common.SizeUnits;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class ContainerEditorTest {

    private ContainerEditor containerEditor;
    
    private ContainerManager containerManager;
    private ItemManager itemManager;
    
    private int uniqueId;
    private Set<StorageUnit> storageUnits;
    
    // These are used for the various tests
    private StorageUnit su1;
    
    private ProductGroup productGroup1_1;
    private ProductGroup productGroup1_2;
    private ProductGroup childProductGroup1_1;
    private ProductGroup childProductGroup1_2;
    private ProductGroup grandChildProductGroup1_1;
    private ProductGroup grandChildProductGroup1_2;
    private ProductGroup grandChildProductGroup1_3;
    
    private StorageUnit su2;
    
    private ProductGroup productGroup2_1;
    private ProductGroup productGroup2_2;
    
    private ProductGroup childProductGroup2_1;
    private ProductGroup childProductGroup2_2;
    
    @Before
    public void setUp() {
        
        containerManager = new ContainerManager();
        itemManager = new ItemManager();
        
        containerEditor = new ContainerEditor(containerManager, itemManager);
        
        storageUnits = new TreeSet<StorageUnit>();
        uniqueId = 1;
        
        setupStorageUnit1();
        setupStorageUnit2();
        
        storageUnits.add( su1 );
        storageUnits.add( su2 );
        
        containerManager.setStorageUnits( storageUnits );
        
    }
    
    private void setupStorageUnit1() {
        
        su1 = initializeStorageUnit( "su1", uniqueId++ );
        
        productGroup1_1 = initializeProductGroup(
                "pg1_1", uniqueId++, su1 );
        productGroup1_2 = initializeProductGroup(
                "pg1_2", uniqueId++, su1 );
        
        childProductGroup1_1 = initializeProductGroup(
                "cpg1_1", uniqueId++, productGroup1_1 );
        childProductGroup1_2 = initializeProductGroup(
                "cpg1_2", uniqueId++, productGroup1_1 );
        
        grandChildProductGroup1_1 = initializeProductGroup(
                "gcpg1_1", uniqueId++, childProductGroup1_2 );
        grandChildProductGroup1_2 = initializeProductGroup(
                "gcpg1_2", uniqueId++, childProductGroup1_2 );
        grandChildProductGroup1_3 = initializeProductGroup(
                "gcpg1_3", uniqueId++, childProductGroup1_2 );
        
        //Parent is su1
        su1.addProductGroup( productGroup1_1 );
        su1.addProductGroup( productGroup1_2 );
        
        //Parent is productGroup1_1
        productGroup1_1.addProductGroup( childProductGroup1_1 );
        productGroup1_1.addProductGroup( childProductGroup1_2 );
        
        //Parent is childProductGroup1_2
        childProductGroup1_2.addProductGroup( grandChildProductGroup1_1 );
        childProductGroup1_2.addProductGroup( grandChildProductGroup1_2 );
        childProductGroup1_2.addProductGroup( grandChildProductGroup1_3 ); 
        
    }

    private void setupStorageUnit2() {
        
        su2 = initializeStorageUnit( "su2", uniqueId++ );
        
        productGroup2_1 =
                initializeProductGroup( "pg2_1", uniqueId++, su2 );
        productGroup2_2 =
                initializeProductGroup( "pg2_2", uniqueId++, su2 );
        
        childProductGroup2_1 =
                initializeProductGroup( "cpg2_1", uniqueId++, productGroup2_1 );
        childProductGroup2_2 =
                initializeProductGroup( "cpg2_2", uniqueId++, productGroup2_1 );
        
        ProductGroup grandChildProductGroup2_1 = initializeProductGroup(
                "gcpg2_1", uniqueId++, childProductGroup2_1 );
        ProductGroup grandChildProductGroup2_2 = initializeProductGroup(
                "gcpg2_2", uniqueId++, childProductGroup2_1 );
        ProductGroup grandChildProductGroup2_3 = initializeProductGroup(
                "gcpg2_3", uniqueId++, childProductGroup2_1 );
        
        //Parent is su2
        su2.addProductGroup( productGroup2_1 );
        su2.addProductGroup( productGroup2_2 );
        
        //Parent is productGroup2_1
        productGroup2_1.addProductGroup( childProductGroup2_1 );
        productGroup2_1.addProductGroup( childProductGroup2_2 );
        
        //Parent is childProductGroup2_1
        childProductGroup2_1.addProductGroup( grandChildProductGroup2_1 );
        childProductGroup2_1.addProductGroup( grandChildProductGroup2_2 );
        childProductGroup2_1.addProductGroup( grandChildProductGroup2_3 );
       
    }

    private StorageUnit initializeStorageUnit( String name, int id ) {
        StorageUnit su = new StorageUnit();
        su.setName( name );
        su.setId( id );
        su.setContainer( null );
        return su;
    }
    
    private ProductGroup initializeProductGroup(
            String name, int id, Container container ) {
        ProductGroup pg = new ProductGroup();
        pg.setName( name );
        pg.setId( id );
        pg.setContainer( container );
        return pg;
    }
    
    @Test
    public void addContainerTest() {
        
        //Add storage Unit normally
        StorageUnit newStorageUnit = initializeStorageUnit(
                "newStorageUnit", uniqueId++ );
        containerEditor.addContainer( null, newStorageUnit );
        
        //Add product group normally
        containerEditor.addContainer( newStorageUnit, initializeProductGroup(
                "newProductGroup", uniqueId++, newStorageUnit ) );       
    
    }
    
    @Test
    public void editContainerTest() {
        
        //Edit StorageUnit
        Container newStorageUnit = initializeStorageUnit(
                "Editsu1", uniqueId++ );
        containerEditor.editContainer(su2, newStorageUnit );
        
        //Edit ProductGroup
        ProductGroup newProductGroup = initializeProductGroup(
                "Editedcpg2_2", uniqueId++, su2 );
        Quantity q = new Quantity();
        q.setQuantity( 2.5f, SizeUnits.FluidOunces );
        newProductGroup.setThreeMonthSupply( q );
        containerEditor.editContainer( 
                childProductGroup2_2, newProductGroup );
    
    }
    
    @Test
    public void canDeleteContainerTest() {                
        
        Product p1 = new Product("12345", "d", SizeUnits.Grams, 3, 8, 8);
        Item i1 = new Item(
                grandChildProductGroup1_1, p1, new Date(), new Barcode());
        itemManager.addItem(i1);
        
        assertFalse(containerEditor.canDeleteContainer(su1));
        assertFalse(containerEditor.canDeleteContainer(productGroup1_1));
        assertFalse(containerEditor.canDeleteContainer(childProductGroup1_2));
        assertFalse(containerEditor.canDeleteContainer(
                grandChildProductGroup1_1));
        
        assertTrue(containerEditor.canDeleteContainer(productGroup1_2));
        assertTrue(containerEditor.canDeleteContainer(childProductGroup1_1));
        assertTrue(containerEditor.canDeleteContainer(
                grandChildProductGroup1_2));
        
        assertTrue(containerEditor.canDeleteContainer(su2));
        assertTrue(containerEditor.canDeleteContainer(productGroup2_1));
        
        Product p2 = new Product("12345", "d", SizeUnits.Grams, 3, 8, 8);
        Item i2 = new Item(
                grandChildProductGroup1_2, p1, new Date(), new Barcode());
        itemManager.addItem(i2);
        
        assertFalse(containerEditor.canDeleteContainer(
                grandChildProductGroup1_2));
        
        Product p3 = new Product("12345", "d", SizeUnits.Grams, 3, 8, 8);
        Item i3 = new Item(
                su2, p1, new Date(), new Barcode());
        itemManager.addItem(i3);
        
        assertFalse(containerEditor.canDeleteContainer(su2));
        
    }
    
    @Test
    public void deleteContainerTest() {
        //Delete a storageUnit
        containerEditor.deleteContainer( su1 );
        
        //Delete a productGroup
        containerEditor.deleteContainer( productGroup1_1 );
    }

}
