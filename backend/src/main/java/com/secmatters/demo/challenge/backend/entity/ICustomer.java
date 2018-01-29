package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Interface that maps to the CUSTOMER table
 * @param <T> The bean that maps to the referenced PURCHASE_ORDER table
 * @param <S> The bean that maps to the referenced DISCOUNT_CODE table
 * @param <U> The bean that maps to the referenced MICRO_MARKET table
 */
public interface ICustomer<T extends IPurchaseOrder, S extends IDiscountCode, U extends IMicroMarket> extends Serializable {

    String getAddressline1();

    String getAddressline2();

    String getCity();

    Integer getCreditLimit();

    Integer getCustomerId();

    S getDiscountCode();

    String getEmail();

    String getFax();

    String getName();

    String getPhone();

    @XmlTransient
    List<T> getPurchaseOrderList();

    String getState();

    U getZip();

    void setAddressline1(String addressline1);

    void setAddressline2(String addressline2);

    void setCity(String city);

    void setCreditLimit(Integer creditLimit);

    void setCustomerId(Integer customerId);

    void setDiscountCode(S discountCode);

    void setEmail(String email);

    void setFax(String fax);

    void setName(String name);

    void setPhone(String phone);

    void setPurchaseOrderList(List<T> purchaseOrderList);

    void setState(String state);

    void setZip(U zip);

}
