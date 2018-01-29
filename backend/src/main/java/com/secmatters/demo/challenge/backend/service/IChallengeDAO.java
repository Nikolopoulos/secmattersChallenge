package com.secmatters.demo.challenge.backend.service;

import com.secmatters.demo.challenge.backend.entity.*;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityTransaction;

public interface IChallengeDAO {

    /**
     * Create and merge a new customer entity in the database with the minimal
 required fields.
     *
     * @param name The customer name (not null)
     * @param discountCode An existing discount code (not null)
     * @param zipCode An existing MicroMarket zip code (not null)
     * @return The newly persisted customer or null if any of the above fields
     * violate any foreign key constraint
     */
    ICustomer addCustomer(String name, String discountCode, String zipCode);

    /**
     * Create and merge a new product entity in the database with the minimal
 required fields.
     *
     * @param manufacturerId An existing manufacturer id
     * @param productCode An existing product code (not null)
     * @return The newly persisted product or null if any of the above fields
     * violate any foreign key constraint
     */
    IProduct addProduct(int manufacturerId, String productCode);

    /**
     * Create and merge a new purchaseOrder entity in the database with the
 minimal required fields.
     *
     * @param customerId An existing customer id
     * @param productId An existing product id
     * @return The newly persisted purchaseOrder or null if any of the above
     * fields violate any foreign key constraint
     */
    IPurchaseOrder addPurchaseOrder(int customerId, int productId);

    void close();

    /**
     * Return all the customer entities filtered by the predicate fieldName =
     * val
     *
     * @param fieldName The field name in the customer table where the filter is
     * applied to
     * @param val The value the entities are filtered by
     * @return
     */
    List<ICustomer> findCustomerBy(String fieldName, Object val);

    /**
     * Return all the product entities filtered by the predicate fieldName = val
     *
     * @param fieldName The field name in the product table where the filter is
     * applied to
     * @param val The value the entities are filtered by
     * @return
     */
    List<IProduct> findProductBy(String fieldName, Object val);

    /**
     * Return all the purchaseOrder entities filtered by the predicate fieldName
     * = val
     *
     * @param fieldName The field name in the purchaseOrder table where the
     * filter is applied to
     * @param val The value the entities are filtered by
     * @return
     */
    List<IPurchaseOrder> findPurchaseOrderBy(String fieldName, Object val);

    /**
     * The connection must be obtained from
     * a pool otherwise a single connection once closed becomes unusable in a
     * single task.
     *
     * @return The database connection
     */
    Connection getConnection();

    /**
     * Return all the existing entities for the customer table
     *
     * @return
     */
    List<ICustomer> getCustomers();

    /**
     * Return all the existing entities for the customer table sorted by the
     * given field
     *
     * @param sortedBy The sorting field
     * @param ascending Whether or not the order will be ascending
     * @return
     */
    public List<ICustomer> getCustomers(String sortedBy, boolean ascending);

    /**
     * Return the total invoiced amount for each customer.
     * The price of the order is the sum of the product purchase cost augmented
     * by the product markup (in percentage)
     * @return
     */
    List<IInvoiceSummary> getInvoiceSummary();


    List<IMicroMarket> getMicroMarkets();

    List<IDiscountCode> getDiscountCode();

    /**
     * Return all the existing entities for the product table
     *
     * @return
     */
    List<IProduct> getProducts();

    /**
     * Return all the existing entities for the purchaseOrder table
     *
     * @return
     */
    List<IPurchaseOrder> getPurchaseOrders();

    /**
     * Initiate and return a new transaction bound to this DAO state
     *
     * @return
     */
    EntityTransaction getTransaction();

    /**
     * Persist a customer entity in the database
     *
     * @param customer The entity to merge, either update if exists or add if
 not exists
     */
    void merge(ICustomer customer);

    /**
     * Persist a product entity in the database
     *
     * @param product The entity to merge, either update if exists or add if
 not exists
     */
    void merge(IProduct product);

    /**
     * Persist a product entity in the database
     *
     * @param purchaseOrder The entity to merge, either update if exists or
 add if not exists
     */
    void merge(IPurchaseOrder purchaseOrder);

    /**
     * Remove an existing customer entity from the database
     *
     * @param customer
     */
    void remove(ICustomer customer);

    /**
     * Remove an existing customer entity from the database
     *
     * @param product
     */
    void remove(IProduct product);

    /**
     * Remove an existing purchaseOrder entity from the database
     *
     * @param purchaseOrder
     */
    void remove(IPurchaseOrder purchaseOrder);

}
