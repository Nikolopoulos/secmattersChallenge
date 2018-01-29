package com.secmatters.demo.challenge.backend.entity.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.secmatters.demo.challenge.backend.entity.ICustomer;
import com.secmatters.demo.challenge.backend.entity.IDiscountCode;
import com.secmatters.demo.challenge.backend.entity.IMicroMarket;

@Table(name="CUSTOMER")  
@Entity
public class Customer implements ICustomer<PurchaseOrder,DiscountCode,MicroMarket>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID", nullable = false)
	@Size(max = 10)
	private int customerId;
	
	@ManyToOne
	@JoinColumn(name="DISCOUNT_CODE", referencedColumnName="DISCOUNT_CODE", nullable = false)
	@Size(max = 2)
	private DiscountCode discountCode;
	
	@OneToOne
	@JoinColumn(name = "ZIP",referencedColumnName="ZIP_CODE",nullable = false)
	@Size(max = 10)
	private MicroMarket zip;
	
	@Column(name="NAME", nullable = true)
	@Size(max = 30)
	private String name;
	
	@Column(name="ADDRESSLINE1", nullable = true)
	@Size(max = 30)
	private String address1;
	
	@Column(name="ADDRESSLINE2", nullable = true)
	@Size(max = 30)
	private String address2;
	
	@Column(name="CITY", nullable = true)
	@Size(max = 25)
	private String city;
	
	@Column(name="STATE", nullable = true)
	@Size(max = 2)
	private String state;
	
	@Column(name="PHONE", nullable = true)
	@Size(max = 12)
	private String phone;
	
	@Column(name="FAX", nullable = true)
	@Size(max = 12)
	private String fax;
	
	@Column(name="EMAIL", nullable = true)
	@Size(max = 40)
	private String email;
	
	@Column(name="CREDIT_LIMIT", nullable = true)
	@Size(max = 10)
	private Integer creditLimit;
	
	@XmlTransient
	@OneToMany(mappedBy="customerId")
	List<PurchaseOrder> purchaseOrderList;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public DiscountCode getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(DiscountCode discountCode) {
		this.discountCode = discountCode;
	}

	public MicroMarket getZip() {
		return zip;
	}

	public void setZip(MicroMarket zip) {
		this.zip = zip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressline1() {
		return address1;
	}

	public void setAddressline1(String address1) {
		this.address1 = address1;
	}

	public String getAddressline2() {
		return address2;
	}

	public void setAddressline2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Integer creditLimit) {
		this.creditLimit = creditLimit;
	}

	public List getPurchaseOrderList() {
		return purchaseOrderList;
	}

	public void setPurchaseOrderList(List purchaseOrderList) {
		this.purchaseOrderList = purchaseOrderList;
		
	}
	
	
	
	
}
