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

import com.secmatters.demo.challenge.backend.entity.IMicroMarket;

@Table(name="MICRO_MARKET")  
@Entity
public class MicroMarket implements IMicroMarket<Customer>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ZIP_CODE", nullable = false)
	@Size(max = 10)
	private String zipCode;
	
	@Column(name = "RADIUS", nullable = true)
	@Size(max = 52)
	private Double radius;
	
	@Column(name = "AREA_LENGTH", nullable = true)
	@Size(max = 52)
	private Double areaLength;
	
	@Column(name = "AREA_WIDTH", nullable = true)
	@Size(max = 52)
	private Double areaWidth;

	@XmlTransient
	@OneToMany
    List<Customer> customerList;
	
	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Double getAreaLength() {
		return areaLength;
	}

	public void setAreaLength(Double areaLength) {
		this.areaLength = areaLength;
	}

	public Double getAreaWidth() {
		return areaWidth;
	}

	public void setAreaWidth(Double areaWidth) {
		this.areaWidth = areaWidth;
	}
	
	
	
}
