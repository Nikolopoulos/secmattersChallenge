package com.secmatters.demo.challenge.backend.entity;

/**
 * Interface that maps to a bean for joining results from
 * CUSTOMER, PRODUCT and PRCHASE_ORDER beans
 * @param <T> The bean that maps to the CUSTOMER table
 */

public interface IInvoiceSummary<T extends ICustomer> {

    ICustomer getCustomer();
    Double getTotalAmount();

}
