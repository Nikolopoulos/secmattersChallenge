package com.secmatters.demo.challenge.backend.entity.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.secmatters.demo.challenge.backend.entity.IManufacturer;

@Table(name="MANUFACTURER")  
@Entity
public class Manufacturer implements IManufacturer<Product>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MANUFACTURER_ID", nullable = false)
	@Size(max = 10)
	private Integer manufacturerId;
	
	@Column(name = "NAME", nullable = true)
	@Size(max = 30)
	private String name;
	
	@Column(name = "ADDRESSLINE1", nullable = true)
	@Size(max = 30)
	private String addressLine1;
	
	@Column(name = "ADDRESSLINE2", nullable = true)
	@Size(max = 30)
	private String addressLine2;
	
	@Column(name = "CITY", nullable = true)
	@Size(max = 25)
	private String city;
	
	@Column(name = "STATE", nullable = true)
	@Size(max = 2)
	private String state;
	
	@Column(name = "ZIP", nullable = true)
	@Size(max = 10)
	private String zip;
	
	@Column(name = "PHONE", nullable = true)
	@Size(max = 12)
	private String phone;
	
	@Column(name = "FAX", nullable = true)
	@Size(max = 12)
	private String fax;
	
	@Column(name = "EMAIL", nullable = true)
	@Size(max = 40)
	private String email;
	
	@Column(name = "REP", nullable = true)
	@Size(max = 30)
	private String rep;
	
	@XmlTransient
	@OneToMany//(mappedBy="productId")
    List<Product> productList;
	
	public Integer getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressline1() {
		return addressLine1;
	}
	public void setAddressline1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressline2() {
		return addressLine2;
	}
	public void setAddressline2(String addressLine2) {
		this.addressLine2 = addressLine2;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
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
	public String getRep() {
		return rep;
	}
	public void setRep(String rep) {
		this.rep = rep;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}
