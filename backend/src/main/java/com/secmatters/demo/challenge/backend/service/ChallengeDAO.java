package com.secmatters.demo.challenge.backend.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.Session;

import com.secmatters.demo.challenge.backend.entity.ICustomer;
import com.secmatters.demo.challenge.backend.entity.IDiscountCode;
import com.secmatters.demo.challenge.backend.entity.IInvoiceSummary;
import com.secmatters.demo.challenge.backend.entity.IMicroMarket;
import com.secmatters.demo.challenge.backend.entity.IProduct;
import com.secmatters.demo.challenge.backend.entity.IPurchaseOrder;
import com.secmatters.demo.challenge.backend.entity.beans.Customer;
import com.secmatters.demo.challenge.backend.entity.beans.DiscountCode;
import com.secmatters.demo.challenge.backend.entity.beans.InvoiceSummary;
import com.secmatters.demo.challenge.backend.entity.beans.Manufacturer;
import com.secmatters.demo.challenge.backend.entity.beans.MicroMarket;
import com.secmatters.demo.challenge.backend.entity.beans.Product;
import com.secmatters.demo.challenge.backend.entity.beans.ProductCode;
import com.secmatters.demo.challenge.backend.entity.beans.PurchaseOrder;

public class ChallengeDAO implements IChallengeDAO{

	private String PERSISTENCE_UNIT_NAME;
	
	
	EntityManagerFactory emf;
	EntityManager em;

	public ChallengeDAO(String PERSISTENCE_UNIT_NAME)
	{
		this.PERSISTENCE_UNIT_NAME = PERSISTENCE_UNIT_NAME;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();
	}
	
	
	
	public ICustomer addCustomer(String name, String discountCode, String zipCode) {
		Customer cust = new Customer();
		cust.setCustomerId(-1);
		cust.setName(name);
		cust.setDiscountCode(findDiscountCodeBy("discountCode", discountCode));
		cust.setZip(findMicroMarketBy("zipCode", zipCode));
		
		merge(cust);
		return em.find(Customer.class, cust.getCustomerId());
	}

	
	public IProduct addProduct(int manufacturerId, String productCode) {
		Product prod = new Product();
		prod.setProductId(-1);
		prod.setManufacturerId(findManufacturerBy("manufacturerId", manufacturerId));
		prod.setProductCode(findProductCodeBy("prodCode", productCode));
		merge(prod);
		
		return em.find(Product.class, prod.getProductId());
	}

	
	public IPurchaseOrder addPurchaseOrder(int customerId, int productId) {
		PurchaseOrder order = new PurchaseOrder();
		order.setOrderNum(-1);
		order.setCustomerId((Customer)findCustomerBy("customerId", customerId).get(0));
		order.setProductId((Product)findProductBy("productId", productId).get(0));
		
		merge(order);
		
		return (em.find(PurchaseOrder.class, order.getOrderNum()));
	}

	
	public void close() {
		em.close(); 
		emf.close();
	}

	public DiscountCode findDiscountCodeBy(String fieldName, Object val) {
		return (DiscountCode) em.createQuery(
			    "SELECT d FROM DiscountCode d WHERE d."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList().get(0);
		
	}
	
	public MicroMarket findMicroMarketBy(String fieldName, Object val) {
		return (MicroMarket) em.createQuery(
			    "SELECT m FROM MicroMarket m WHERE m."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList().get(0);
		
	}
	
	public Manufacturer findManufacturerBy(String fieldName, Object val) {
		return (Manufacturer) em.createQuery(
			    "SELECT m FROM Manufacturer m WHERE m."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList().get(0);
		
	}
	
	public ProductCode findProductCodeBy(String fieldName, Object val) {
		return (ProductCode) em.createQuery(
			    "SELECT p FROM ProductCode p WHERE p."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList().get(0);
		
	}
	
	public List<ICustomer> findCustomerBy(String fieldName, Object val) {
		
		return em.createQuery(
			    "SELECT c FROM Customer c WHERE c."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList();
	}

	
	public List<IProduct> findProductBy(String fieldName, Object val) {
		return em.createQuery(
			    "SELECT p FROM Product p WHERE p."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList();
	}

	
	public List<IPurchaseOrder> findPurchaseOrderBy(String fieldName, Object val) {
		return em.createQuery(
			    "SELECT p FROM PurchaseOrder p WHERE p."+fieldName+" = :val")
			    .setParameter("val", val)
			    .getResultList();
	}

	
	public Connection getConnection() {
		/*em.getTransaction().begin();
		
		
		Connection conn = em.unwrap(java.sql.Connection.class);
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DEBUG OUTPUT OF CONN IS: "+conn);
		System.out.println("DEBUG OUTPUT OF CONN ISOPEN: "+em.isOpen());*/
		Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", "app");
	    connectionProps.put("password", "app");
	    try {
			conn = DriverManager.getConnection("jdbc:derby:memory:sample;create=true",connectionProps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("DEBUG OUTPUT OF CONN IS: "+conn);
	    return conn;
	}

	
	public List<ICustomer> getCustomers() {
		return   em
                .createQuery("Select c from Customer c")
                .getResultList();
	}

	
	public List<ICustomer> getCustomers(String sortedBy, boolean ascending) {
		return   em
                .createQuery("Select c from Customer c order by c."+sortedBy + (ascending?" asc":""))
                .getResultList();
	}

	
	public List<IInvoiceSummary> getInvoiceSummary() {
		return   em
                .createQuery("Select i from InvoiceSummary i")
                .getResultList();
	}

	
	public List<IMicroMarket> getMicroMarkets() {
		return   em
                .createQuery("Select m from MicroMarket m")
                .getResultList();
	}

	
	public List<IDiscountCode> getDiscountCode() {
		return   em
                .createQuery("Select d from DiscountCode d")
                .getResultList();
	}

	
	public List<IProduct> getProducts() {
		return   em
                .createQuery("Select p from Product p")
                .getResultList();
	}

	
	public List<IPurchaseOrder> getPurchaseOrders() {
		return   em
                .createQuery("Select p from PurchaseOrder p")
                .getResultList();
	}

	
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}

	
	public void merge(ICustomer customer) {
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive())
			tx.begin();
		
		Customer cust = em.find(Customer.class,customer.getCustomerId());
		if(cust != null) {
			em.remove(cust);
		}
		em.persist(customer);
		
		em.flush();
		tx.commit();
		
	}

	
	public void merge(IProduct product) {
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive())
			tx.begin();
		Product prod = em.find(Product.class,product.getProductId());
		if(prod != null) {
			em.remove(prod);
		}
		em.persist(product);
		
		em.flush();
		tx.commit();
		
		
	}

	
	public void merge(IPurchaseOrder purchaseOrder) {
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive())
			tx.begin();
		PurchaseOrder order = em.find(PurchaseOrder.class,purchaseOrder.getOrderNum());
		if(order != null) {
			em.remove(order);
		}
		em.persist(purchaseOrder);
		
		em.flush();
		tx.commit();
		
		
	}

	
	public void remove(ICustomer customer) {
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive())
			tx.begin();
		Customer cust = em.find(Customer.class,customer.getCustomerId());
		if(cust != null) {
			em.remove(cust);
		}
		
		em.flush();
		tx.commit();
		
		
	}

	
	public void remove(IProduct product) {
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive())
			tx.begin();
		Product prod = em.find(Product.class,product.getProductId());
		if(prod != null) {
			em.remove(prod);
		}
		
		em.flush();
		tx.commit();
		
		
	}

	
	public void remove(IPurchaseOrder purchaseOrder) {
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive())
			tx.begin();
		PurchaseOrder order = em.find(PurchaseOrder.class,purchaseOrder.getOrderNum());
		if(order != null) {
			em.remove(order);
		}
		
		em.flush();
		tx.commit();
		
		
	}



}
