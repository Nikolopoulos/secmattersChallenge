package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Interface that maps to the MANUFACTURER table
 * @param <T> The bean that maps to the referenced PRODUCT table
 */
public interface IManufacturer<T extends IProduct> extends Serializable {

    String getAddressline1();

    String getAddressline2();

    String getCity();

    String getEmail();

    String getFax();

    Integer getManufacturerId();

    String getName();

    String getPhone();

    @XmlTransient
    List<T> getProductList();

    String getRep();

    String getState();

    String getZip();

    void setAddressline1(String addressline1);

    void setAddressline2(String addressline2);

    void setCity(String city);

    void setEmail(String email);

    void setFax(String fax);

    void setManufacturerId(Integer manufacturerId);

    void setName(String name);

    void setPhone(String phone);

    void setProductList(List<T> productList);

    void setRep(String rep);

    void setState(String state);

    void setZip(String zip);

}
