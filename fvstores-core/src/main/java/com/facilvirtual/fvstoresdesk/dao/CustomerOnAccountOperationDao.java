package com.facilvirtual.fvstoresdesk.dao;

import com.facilvirtual.fvstoresdesk.model.Customer;
import com.facilvirtual.fvstoresdesk.model.CustomerOnAccountOperation;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerOnAccountOperationDao extends AbstractFVDao {
   protected static Logger logger = LoggerFactory.getLogger("CustomerOnAccountOperationDao");
   @Autowired
   private SessionFactory sessionFactory;

   public CustomerOnAccountOperationDao() {
   }

   public void saveCustomerOnAccountOperation(CustomerOnAccountOperation customerOnAccountOperation) {
      this.getSession().saveOrUpdate(customerOnAccountOperation);
   }

   public List<CustomerOnAccountOperation> getActiveCustomerOnAccountOpsForDateRange(Customer customer, Date startDate, Date endDate) {
      List<CustomerOnAccountOperation> operations = this.getSession().createCriteria(CustomerOnAccountOperation.class).add(Restrictions.between("operationDate", startDate, endDate)).add(Restrictions.eq("active", true)).add(Restrictions.eq("customer.id", customer.getId())).addOrder(Order.asc("operationDate")).list();
      return operations;
   }

   public List<CustomerOnAccountOperation> getActiveCustomerOnAccountOps(Customer customer) {
      List<CustomerOnAccountOperation> operations = this.getSession().createCriteria(CustomerOnAccountOperation.class).add(Restrictions.eq("active", true)).add(Restrictions.eq("customer.id", customer.getId())).addOrder(Order.asc("operationDate")).list();
      return operations;
   }

   public CustomerOnAccountOperation getCustomerOnAccountOpByOrderId(Long orderId) {
      CustomerOnAccountOperation customerOnAccountOp = null;

      try {
         Criteria crit = this.getSession().createCriteria(CustomerOnAccountOperation.class);
         crit.add(Restrictions.eq("order.id", orderId));
         crit.setMaxResults(1);
         customerOnAccountOp = (CustomerOnAccountOperation)crit.uniqueResult();
      } catch (Exception var4) {
         logger.error("Error al recuperar movimiento de cta. cte. de cliente para venta: " + orderId);
         logger.error(var4.getMessage());
         //logger.error(var4);
      }

      return customerOnAccountOp;
   }
}
