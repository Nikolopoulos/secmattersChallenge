package com.secmatters.demo.challenge.backend.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Interface that maps to the PRODUCT table
 * @param <T> The bean that maps to the referenced PURCHASE_ORDER table
 * @param <S> The bean that maps to the referenced MANUFACTURER table
 * @param <U> The bean that maps to the referenced PRODUCT_CODE table
 */
public interface IProduct<T extends IPurchaseOrder, S extends IManufacturer, U extends IProductCode> extends Serializable {

    String getAvailable();

    String getDescription();

    S getManufacturerId();

    BigDecimal getMarkup();

    U getProductCode();

    Integer getProductId();

    BigDecimal getPurchaseCost();

    @XmlTransient
    List<T> getPurchaseOrderList();

    Integer getQuantityOnHand();

    void setAvailable(String available);

    void setDescription(String description);

    void setManufacturerId(S manufacturerId);

    void setMarkup(BigDecimal markup);

    void setProductCode(U productCode);

    void setProductId(Integer productId);

    void setPurchaseCost(BigDecimal purchaseCost);

    void setPurchaseOrderList(List<T> purchaseOrderList);

    void setQuantityOnHand(Integer quantityOnHand);

}
