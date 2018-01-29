package com.secmatters.demo.challenge.backend.entity.beans;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.secmatters.demo.challenge.backend.entity.IProduct;

@Table(name="PRODUCT")  
@Entity
public class Product implements IProduct<PurchaseOrder, Manufacturer, ProductCode>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", nullable = false)
	@Size(max = 10)
	private Integer productId;

	@OneToOne
	@JoinColumn(name="MANUFACTURER_ID", referencedColumnName="MANUFACTURER_ID", nullable = false)
	@Size(max = 2)
	private Manufacturer manufacturerId;
	
	@OneToOne
	@JoinColumn(name="PRODUCT_CODE", referencedColumnName="PROD_CODE", nullable = false)
	@Size(max = 10)
	private ProductCode productCode;
	
	@Column(name = "PURCHASE_COST", nullable = true)
	@Digits(integer=12, fraction=2)
	private BigDecimal purchaseCost;
	
	@Column(name = "QUANTITY_ON_HAND", nullable = true)
	@Size(max = 10)
	private Integer quantityOnHand;
	
	@Column(name = "MARKUP", nullable = true)
	@Digits(integer=4, fraction=2)
	private BigDecimal markup;
	
	@Column(name = "AVAILABLE", nullable = true)
	@Size(max = 5)
	private String available;
	
	@Column(name = "DESCRIPTION", nullable = true)
	@Size(max = 50)
	private String Description;
	
	@XmlTransient
	@OneToMany(mappedBy="productId")
    List<PurchaseOrder> purchaseOrderList;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Manufacturer getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Manufacturer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public ProductCode getProductCode() {
		return productCode;
	}

	public void setProductCode(ProductCode productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public Integer getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(Integer quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public List<PurchaseOrder> getPurchaseOrderList() {
		return purchaseOrderList;
	}

	public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
		this.purchaseOrderList = purchaseOrderList;
	}

	
	
}
