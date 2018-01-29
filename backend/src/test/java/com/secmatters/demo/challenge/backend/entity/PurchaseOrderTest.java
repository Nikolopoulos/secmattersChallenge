package com.secmatters.demo.challenge.backend.entity;

import com.secmatters.demo.challenge.backend.entity.beans.PurchaseOrder;
import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PurchaseOrderTest extends EntityTestBase {

    EntityTransaction trx;

    public PurchaseOrderTest() {
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
        List<IPurchaseOrder> purchaseOrders = challengeDAO.getPurchaseOrders();
        assertTrue("Resultset must be bigger", purchaseOrders.size() >= 15 );
        purchaseOrders = challengeDAO.findPurchaseOrderBy("orderNum", 10398001);
        assertTrue("Unmached record", purchaseOrders.size() == 1 && purchaseOrders.get(0).getQuantity() == 10 && purchaseOrders.get(0).getFreightCompany().equals("Poney Express"));
        purchaseOrders = challengeDAO.findPurchaseOrderBy("orderNum", 10398008);
        assertTrue("Unmached record", purchaseOrders.size() == 1 && purchaseOrders.get(0).getQuantity() == 500 && purchaseOrders.get(0).getFreightCompany().equals("Slow Snail"));
        purchaseOrders = challengeDAO.findPurchaseOrderBy("orderNum", 20598101);
        assertTrue("Unmached record", purchaseOrders.size() == 1 && purchaseOrders.get(0).getQuantity() == 250 && purchaseOrders.get(0).getFreightCompany().equals("Coastal Freight"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddEditRemoveEntities() {
        System.out.println("addAndRemoveEntities");
        int customerId = 25;
        int productId = 975789;

        // Add new purchaseOrder
        IPurchaseOrder purchaseOrder = challengeDAO.addPurchaseOrder(customerId, productId);
        assertNotNull("Cannot add PurchaseOrder", purchaseOrder);
        List<IPurchaseOrder> purchaseOrders = challengeDAO.findPurchaseOrderBy("orderNum", purchaseOrder.getOrderNum());
        assertTrue("Cannot find newly added record", purchaseOrders.size() == 1
                && purchaseOrders.get(0).equals(purchaseOrder)
        );

        // Edit purchaseOrder just added
        short quantity = 123;
        purchaseOrder.setQuantity(quantity);
        challengeDAO.merge(purchaseOrder);
        purchaseOrders = challengeDAO.findPurchaseOrderBy("orderNum", purchaseOrder.getOrderNum());
        assertTrue("Cannot update record", purchaseOrders.size() == 1
                && purchaseOrders.get(0).getQuantity().equals(quantity)
                && purchaseOrders.get(0).equals(purchaseOrder)
        );

        // Remove added purchaseOrder
        challengeDAO.remove(purchaseOrders.get(0));
        assertTrue(challengeDAO.findPurchaseOrderBy("orderNum", purchaseOrder.getOrderNum()).isEmpty());
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        List<IPurchaseOrder> purchaseOrders = challengeDAO.getPurchaseOrders();
        IPurchaseOrder purchaseOrder = new PurchaseOrder();
        for (IPurchaseOrder instance: purchaseOrders) {
            assertNotEquals(instance.hashCode(), purchaseOrder.hashCode());
            // Get a fresh copy of the same instance
            purchaseOrder = challengeDAO.findPurchaseOrderBy("orderNum", instance.getOrderNum()).get(0);
            assertEquals(instance.hashCode(), purchaseOrder.hashCode());
        }
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        List<IPurchaseOrder> purchaseOrders = challengeDAO.getPurchaseOrders();
        IPurchaseOrder purchaseOrder = new PurchaseOrder();
        for (IPurchaseOrder instance: purchaseOrders) {
            assertNotEquals(instance, purchaseOrder);
            // Get a fresh copy of the same instance
            purchaseOrder = challengeDAO.findPurchaseOrderBy("orderNum", instance.getOrderNum()).get(0);
            assertEquals(instance, purchaseOrder);
        }
    }
}
