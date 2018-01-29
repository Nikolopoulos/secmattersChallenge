package com.secmatters.demo.challenge.backend.entity;

import com.secmatters.demo.challenge.backend.entity.beans.Customer;
import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CustomerTest extends EntityTestBase {

    EntityTransaction trx;

    public CustomerTest() {
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
        List<ICustomer> customers = challengeDAO.getCustomers();
        assertTrue("Resultset must be bigger", customers.size() >= 13 );
        customers = challengeDAO.findCustomerBy("customerId", 1);
        assertTrue("Unmached record", customers.size() == 1 && customers.get(0).getName().equals("Jumbo Eagle Corp"));
        customers = challengeDAO.findCustomerBy("customerId", 106);
        assertTrue("Unmached record", customers.size() == 1 && customers.get(0).getName().equals("Early CentralComp"));
        customers = challengeDAO.findCustomerBy("customerId", 722);
        assertTrue("Unmached record", customers.size() == 1 && customers.get(0).getName().equals("Big Car Parts"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddEditRemoveEntities() {
        System.out.println("addAndRemoveEntities");
        String custName = "my very unique name";
        String discountCode = "H";
        String zipCode = "95035";

        // Add new customer
        ICustomer customer = challengeDAO.addCustomer(custName, discountCode, zipCode);
        assertNotNull("Cannot add customer", customer);
        List<ICustomer> customers = challengeDAO.findCustomerBy("name", custName);
        assertTrue("Cannot find newly added record", customers.size() == 1
                && customers.get(0).equals(customer)
        );

        // Edit customer just added
        String city = "my very unique city";
        customer.setCity(city);
        challengeDAO.merge(customer);
        customers = challengeDAO.findCustomerBy("name", custName);
        assertTrue("Cannot update record", customers.size() == 1
                && customers.get(0).getCity().equals(city)
                && customers.get(0).equals(customer)
        );

        // Remove added customer
        challengeDAO.remove(customers.get(0));
        assertTrue(challengeDAO.findCustomerBy("customerId", customer.getCustomerId()).isEmpty());
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        List<ICustomer> customers = challengeDAO.getCustomers();
        ICustomer customer = new Customer();
        for (ICustomer instance: customers) {
            assertNotEquals(instance.hashCode(), customer.hashCode());
            // Get a fresh copy of the same instance
            customer = challengeDAO.findCustomerBy("customerId", instance.getCustomerId()).get(0);
            assertEquals(instance.hashCode(), customer.hashCode());
        }
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        List<ICustomer> customers = challengeDAO.getCustomers();
        ICustomer customer = new Customer();
        for (ICustomer instance: customers) {
            assertNotEquals(instance, customer);
            // Get a fresh copy of the same instance
            customer = challengeDAO.findCustomerBy("customerId", instance.getCustomerId()).get(0);
            assertEquals(instance, customer);
        }
    }
}
