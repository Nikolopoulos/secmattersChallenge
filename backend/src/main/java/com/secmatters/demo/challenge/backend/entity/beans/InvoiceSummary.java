package com.secmatters.demo.challenge.backend.entity.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.secmatters.demo.challenge.backend.entity.IInvoiceSummary;


@Entity(name="InvoiceSummary")
public class InvoiceSummary implements IInvoiceSummary<Customer>{
	
	@Id
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@Formula("select SUM((prod.purchaseCost * prod.Markup + prod.purchaseCost )*order.quantity + order.shippingCost) from Product prod, PurchaseOrder order where order.customerId = customerId AND product.productId = order.productId;")
	private Double totalAmount;	
	
	public Customer getCustomer() {
		return customer;
	}
		
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	
	
	
	
	
}
