package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.dao.BudgetDao;
import com.facilvirtual.fvstoresdesk.dao.CustomerDao;
import com.facilvirtual.fvstoresdesk.dao.CustomerOnAccountOperationDao;
import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.CustomerOnAccountOperation;
import com.facilvirtual.fvstoresdesk.model.Order;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
   protected static Logger logger = LoggerFactory.getLogger("CustomerService");
   @Autowired
   private CustomerDao customerDao;
   @Autowired
   private CustomerOnAccountOperationDao customerOnAccountOperationDao;
   @Autowired
   private BudgetDao budgetDao;

   public CustomerService() {
   }

   public void saveCustomer(Customer customer) {
      this.customerDao.saveCustomer(customer);
   }

   public Customer getCustomerByCompanyName(String companyName) {
      return this.customerDao.getCustomerByCompanyName(companyName);
   }

   public List<Customer> getAllCustomers() {
      return this.customerDao.getAllCustomers();
   }

   public List<Customer> getActiveCustomers() {
      return this.customerDao.getActiveCustomers();
   }

   public List<Customer> searchCustomers(String query, boolean hideDeletedCustomers, int maxResults) {
      return this.customerDao.searchCustomers(query, hideDeletedCustomers, maxResults);
   }

   public void deleteCustomer(Long customerId) {
      this.customerDao.deleteCustomer(customerId);
   }

   public void restoreCustomer(Long customerId) {
      this.customerDao.restoreCustomer(customerId);
   }

   public Customer getCustomer(Long customerId) {
      return this.customerDao.getCustomer(customerId);
   }

   public void saveNewCustomerOnAccountOperationForOrder(Order order) {
      CustomerOnAccountOperation operation = new CustomerOnAccountOperation();
      operation.setCreationDate(order.getCreationDate());
      operation.setOperationDate(order.getSaleDate());
      operation.setLastUpdatedDate(order.getCreationDate());
      operation.setDebitType();
      operation.setDescription("Compra");
      operation.setAmount(order.getNetOnAccountAmount());
      operation.setAuthor(order.getCashier());
      operation.setCashNumber(order.getCashNumber());
      operation.setSystem(true);
      operation.setOrder(order);
      operation.setCustomer(order.getCustomer());
      this.saveCustomerOnAccountOperation(operation);
      Customer customer = order.getCustomer();
      customer.setOnAccountTotal(customer.getOnAccountTotal() - operation.getAmount());
      this.saveCustomer(customer);
   }

   public void saveCustomerOnAccountOperation(CustomerOnAccountOperation operation) {
      this.customerOnAccountOperationDao.saveCustomerOnAccountOperation(operation);
   }

   public List<CustomerOnAccountOperation> getActiveCustomerOnAccountOpsForDateRange(Customer customer, Date startDate, Date endDate) {
      return this.customerOnAccountOperationDao.getActiveCustomerOnAccountOpsForDateRange(customer, startDate, endDate);
   }

   public List<CustomerOnAccountOperation> getActiveCustomerOnAccountOps(Customer customer) {
      return this.customerOnAccountOperationDao.getActiveCustomerOnAccountOps(customer);
   }

   public CustomerOnAccountOperation getCustomerOnAccountOpByOrderId(Long orderId) {
      return this.customerOnAccountOperationDao.getCustomerOnAccountOpByOrderId(orderId);
   }

   public int getTotalBudgetsQty() {
      return this.budgetDao.getTotalBudgetsQty();
   }
}
