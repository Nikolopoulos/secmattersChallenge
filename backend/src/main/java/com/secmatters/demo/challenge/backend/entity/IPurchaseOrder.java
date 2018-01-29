package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Interface that maps to the PURCHASE_ORDER table
 * @param <T> The bean that maps to the referenced CUSTOMER table
 * @param <S> The bean that maps to the referenced PRODUCT table
 */public interface IPurchaseOrder<T extends ICustomer, S extends IProduct> extends Serializable {

    T getCustomerId();

    String getFreightCompany();

    Integer getOrderNum();

    S getProductId();

    Short getQuantity();

    Date getSalesDate();

    BigDecimal getShippingCost();

    Date getShippingDate();

    void setCustomerId(T customerId);

    void setFreightCompany(String freightCompany);

    void setOrderNum(Integer orderNum);

    void setProductId(S productId);

    void setQuantity(Short quantity);

    void setSalesDate(Date salesDate);

    void setShippingCost(BigDecimal shippingCost);

    void setShippingDate(Date shippingDate);

}
