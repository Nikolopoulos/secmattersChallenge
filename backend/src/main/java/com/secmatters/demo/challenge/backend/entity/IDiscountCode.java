package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Interface that maps to the DISCOUNT_CODE table
 * @param <T> The bean that maps to the referenced CUSTOMER table
 */
public interface IDiscountCode<T extends ICustomer> extends Serializable {

    @XmlTransient
    List<T> getCustomerList();

    String getDiscountCode();

    BigDecimal getRate();

    void setCustomerList(List<T> customerList);

    void setDiscountCode(String discountCode);

    void setRate(BigDecimal rate);

}
