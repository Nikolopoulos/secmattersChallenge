package com.secmatters.demo.challenge.backend.entity;

import com.secmatters.demo.challenge.backend.entity.beans.Product;
import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProductTest extends EntityTestBase {

    EntityTransaction trx;

    public ProductTest() {
    }

    // Each test is run in a separate transaction
    @Before
    public void setUp() {
        //Get a new transaction
        trx = challengeDAO.getTransaction();
        trx.begin();
    }

    @After
    public void tearDown() {
        if (trx != null && trx.isActive()) {
            trx.commit();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReadExistingEntities() {
        System.out.println("readExistinEntities");
        List<IProduct> products = challengeDAO.getProducts();
        assertTrue("Resultset must be bigger", products.size() >= 30 );
        // It seems that bigdecimal fields cannot be correcly fetched. They loose the decimal part
        products = challengeDAO.findProductBy("productId", 980001);
        assertTrue("Unmached record", products.size() == 1 && products.get(0).getQuantityOnHand() == 800000 && products.get(0).getDescription().equals("Identity Server"));
        products = challengeDAO.findProductBy("productId", 980601);
        assertTrue("Unmached record", products.size() == 1 && products.get(0).getQuantityOnHand() == 2000 && products.get(0).getDescription().equals("300Mhz Pentium Computer"));
        products = challengeDAO.findProductBy("productId", 984666);
        assertTrue("Unmached record", products.size() == 1 && products.get(0).getQuantityOnHand() == 25 && products.get(0).getDescription().equals("Flat screen Monitor"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddEditRemoveEntities() {
        System.out.println("addAndRemoveEntities");
        int manufacturerId = 19955656;
        String productCode = "SW";

        // Add new product
        IProduct product = challengeDAO.addProduct(manufacturerId, productCode);
        assertNotNull("Cannot add Product", product);
        List<IProduct> products = challengeDAO.findProductBy("productId", product.getProductId());
        assertTrue("Cannot find newly added record", products.size() == 1
                && products.get(0).equals(product)
        );

        // Edit product just added
        int quantity = 123;
        product.setDescription("my very unique description");
        product.setQuantityOnHand(quantity);
        challengeDAO.merge(product);
        products = challengeDAO.findProductBy("productId", product.getProductId());
        assertTrue("Cannot update record", products.size() == 1
                && products.get(0).getDescription().equals("my very unique description")
                && products.get(0).getQuantityOnHand() == quantity
                && products.get(0).equals(product)
        );

        // Remove added product
        challengeDAO.remove(products.get(0));
        assertTrue(challengeDAO.findProductBy("productId", product.getProductId()).isEmpty());
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        List<IProduct> products = challengeDAO.getProducts();
        IProduct product = new Product();
        for (IProduct instance: products) {
            assertNotEquals(instance.hashCode(), product.hashCode());
            // Get a fresh copy of the same instance
            product = challengeDAO.findProductBy("productId", instance.getProductId()).get(0);
            assertEquals(instance.hashCode(), product.hashCode());
        }
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        List<IProduct> products = challengeDAO.getProducts();
        IProduct product = new Product();
        for (IProduct instance: products) {
            assertNotEquals(instance, product);
            // Get a fresh copy of the same instance
            product = challengeDAO.findProductBy("productId", instance.getProductId()).get(0);
            assertEquals(instance, product);
        }
    }
}
