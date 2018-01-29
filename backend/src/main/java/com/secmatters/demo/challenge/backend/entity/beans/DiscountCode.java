package com.secmatters.demo.challenge.backend.entity.beans;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.secmatters.demo.challenge.backend.entity.IDiscountCode;

@Table(name="DISCOUNT_CODE")  
@Entity
public class DiscountCode implements IDiscountCode<Customer>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DISCOUNT_CODE", nullable = false)
	@Size(max = 1)
	private String discountCode;
	
	@Column(name = "RATE", nullable = true)
	@Digits(integer=4, fraction=2)
	private BigDecimal rate;
	
	@XmlTransient
	@OneToMany//(mappedBy="customerId")
    List<Customer> customerList;

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	
	
	

}
