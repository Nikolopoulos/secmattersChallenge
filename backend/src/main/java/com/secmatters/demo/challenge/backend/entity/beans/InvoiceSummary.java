package com.secmatters.demo.challenge.backend.entity.beans;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.secmatters.demo.challenge.backend.entity.IInvoiceSummary;


public class InvoiceSummary implements IInvoiceSummary<Customer>{
	
	
	//The annotations don;t really match the reality here,
	//but I'm leaving them here for future reference
	//on how we could populate the table if it existed
	@Id
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
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
