package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Interface that maps to the MICRO_MARKET table
 * @param <T> The bean that maps to the referenced CUSTOMER table
 */
public interface IMicroMarket<T extends ICustomer> extends Serializable {

    Double getAreaLength();

    Double getAreaWidth();

    @XmlTransient
    List<T> getCustomerList();

    Double getRadius();

    String getZipCode();

    void setAreaLength(Double areaLength);

    void setAreaWidth(Double areaWidth);

    void setCustomerList(List<T> customerList);

    void setRadius(Double radius);

    void setZipCode(String zipCode);

}
