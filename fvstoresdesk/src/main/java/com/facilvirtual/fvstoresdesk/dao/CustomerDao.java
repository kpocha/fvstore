package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Customer;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("CustomerDao");

   public CustomerDao() {
   }

   public void saveCustomer(Customer customer) {
      Session session = this.getSession();
      session.saveOrUpdate(customer);
   }

   public List<Customer> getAllCustomers() {
      List<Customer> customers = this.getSession().createCriteria(Customer.class).list();
      Collections.sort(customers);
      return customers;
   }

   public Customer getCustomerByCompanyName(String companyName) {
      Session session = this.getSession();
      Customer customer = null;

      try {
         Criteria crit = session.createCriteria(Customer.class);
         crit.add(Restrictions.eq("companyName", companyName).ignoreCase());
         crit.setMaxResults(1);
         customer = (Customer)crit.uniqueResult();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return customer;
   }

   public List<Customer> searchCustomers(String query, boolean hideDeletedCustomers, int maxResults) {
      Session session = this.getSession();
      String queryStr = "from Customer cust where (upper(cust.lastName) like '%" + query.toUpperCase() + "%' or " + "upper(cust.firstName) like '%" + query.toUpperCase() + "%' or " + "upper(cust.companyName) like '%" + query.toUpperCase() + "%' or " + "upper(cust.cuit) like '%" + query.toUpperCase() + "%')";
      if (hideDeletedCustomers) {
         queryStr = queryStr + " and (cust.active) = true";
      }

      Query q = session.createQuery(queryStr);
      q.setMaxResults(maxResults);
      List<Customer> customers = q.list();
      Collections.sort(customers);
      return customers;
   }

   public List<Customer> getActiveCustomers() {
      List<Customer> customers = this.getSession().createCriteria(Customer.class).add(Restrictions.eq("active", true)).list();
      Collections.sort(customers);
      return customers;
   }

   public void deleteCustomer(Long customerId) {
      Customer customer = this.getCustomer(customerId);
      if (customer != null) {
         customer.setActive(false);
         this.saveCustomer(customer);
      }

   }

   public Customer getCustomer(Long customerId) {
      return (Customer)this.getSession().get(Customer.class, customerId);
   }

   public void restoreCustomer(Long customerId) {
      Customer customer = this.getCustomer(customerId);
      if (customer != null) {
         customer.setActive(true);
         this.saveCustomer(customer);
      }

   }
}
