package com.secmatters.demo.challenge.backend.entity;

import java.util.List;
import javax.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.secmatters.demo.challenge.backend.entity.EntityTestBase.challengeDAO;

public class ReportTest extends EntityTestBase {

    EntityTransaction trx;

    public ReportTest() {
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

    @Test
    public void testInvoiceSummary() {
        List<IInvoiceSummary> invoiceSummary = challengeDAO.getInvoiceSummary();
        boolean found = false;
        for (IInvoiceSummary invoice: invoiceSummary) {
            if (invoice.getCustomer().getCustomerId().equals(2)) {
                Assert.assertEquals("Invoicing for customer id 2 must totalize 203495 (truncated)",
                        203495l, invoice.getTotalAmount().longValue());
                found = true;
            }
        }
        Assert.assertTrue(found);
    }
}
