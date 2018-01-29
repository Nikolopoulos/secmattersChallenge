package com.secmatters.demo.challenge.backend.entity.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.secmatters.demo.challenge.backend.entity.ICustomer;
import com.secmatters.demo.challenge.backend.entity.IProduct;
import com.secmatters.demo.challenge.backend.entity.IPurchaseOrder;

@Table(name="PURCHASE_ORDER")  
@Entity
public class PurchaseOrder implements IPurchaseOrder<Customer, Product>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ORDER_NUM", nullable=false)
	@Size(max = 10)
	private Integer orderNum;
	
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID", referencedColumnName="CUSTOMER_ID", nullable = false)
	@Size(max = 10)
	private Customer customerId;
	
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID", nullable = false)
	@Size(max = 10)
	private Product productId;
	
	@Column(name="QUANTITY", nullable=true)
	@Size(max = 5)
	private Short quantity;
	
	@Column(name="SHIPPING_COST", nullable=true)
	@Digits(integer = 12,fraction = 2)
	private BigDecimal shippingCost;
	
	@Column(name="SALES_DATE", nullable=true)
	@Size(max = 10)
	@Temporal(TemporalType.DATE)
	private Date salesDate;
	
	@Column(name="SHIPPING_DATE", nullable=true)
	@Size(max = 10)
	@Temporal(TemporalType.DATE)
	private Date shippingDate;
	
	@Column(name="FREIGHT_COMPANY", nullable=true)
	@Size(max = 30)
	private String freightCompany;

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getFreightCompany() {
		return freightCompany;
	}

	public void setFreightCompany(String freightCompany) {
		this.freightCompany = freightCompany;
	}
	
	

}
