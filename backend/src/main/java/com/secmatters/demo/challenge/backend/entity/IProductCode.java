package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Interface that maps to the PRODUCT_CODE table
 * @param <T> The bean that maps to the referenced PRODUCT table
 */public interface IProductCode<T extends IProduct> extends Serializable {

    String getDescription();

    Character getDiscountCode();

    String getProdCode();

    @XmlTransient
    List<T> getProductList();

    void setDescription(String description);

    void setDiscountCode(Character discountCode);

    void setProdCode(String prodCode);

    void setProductList(List<T> productList);

}
