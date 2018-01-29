package com.secmatters.demo.challenge.backend.entity.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.secmatters.demo.challenge.backend.entity.IProductCode;

@Table(name="PRODUCT_CODE")  
@Entity
public class ProductCode implements IProductCode<Product>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROD_CODE", nullable = false)
	@Size(max = 2)
	private String prodCode;
	
	@Column(name = "DISCOUNT_CODE", nullable = false)
	@Size(max = 1)
	private Character discountCode;
	
	@Column(name = "Description", nullable = true)
	@Size(max = 10)
	private String description;
	
	@XmlTransient
	@OneToOne(mappedBy="productId")
    private List<Product> productList;

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public Character getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(Character discountCode) {
		this.discountCode = discountCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}
